CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    deadline_alert_hours INT NOT NULL DEFAULT 24,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT email_unique UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS restrictions (
    id INT AUTO_INCREMENT NOT NULL,
    CONSTRAINT PK_restrictions PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS restrictions_users (
    restriction_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT PK_restrictions_users PRIMARY KEY (restriction_id, user_id),
    CONSTRAINT FK_restrictions_users_restrictions FOREIGN KEY (restriction_id) REFERENCES restrictions (id),
    CONSTRAINT FK_restrictions_users_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS teams (
    id INT AUTO_INCREMENT NOT NULL,
    CONSTRAINT PK_teams PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS teams_users (
    team_id INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT PK_teams_users PRIMARY KEY (team_id, user_id),
    CONSTRAINT FK_teams_users_teams FOREIGN KEY (team_id) REFERENCES teams (id),
    CONSTRAINT FK_teams_users_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS projects (
    id INT AUTO_INCREMENT NOT NULL,
    owner_id INT NOT NULL,
    restriction_id INT,
    team_id INT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT PK_projects PRIMARY KEY (id),
    CONSTRAINT FK_projects_users FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT FK_projects_restrictions FOREIGN KEY (restriction_id) references restrictions(id),
    CONSTRAINT FK_projects_teams FOREIGN KEY (team_id) references teams(id)
);

CREATE TABLE IF NOT EXISTS boards (
    id INT AUTO_INCREMENT NOT NULL,
    project_id INT NOT NULL,
    owner_id INT NOT NULL,
    restriction_id INT,
    team_id INT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT PK_boards PRIMARY KEY (id),
    CONSTRAINT FK_boards_projects FOREIGN KEY (project_id) REFERENCES projects(id),
    CONSTRAINT FK_boards_users FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT FK_boards_restrictions FOREIGN KEY (restriction_id) references restrictions(id),
    CONSTRAINT FK_boards_teams FOREIGN KEY (team_id) references teams(id)
);

CREATE TABLE IF NOT EXISTS stages (
    id INT AUTO_INCREMENT NOT NULL,
    board_id INT NOT NULL,
    position INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT PK_stages PRIMARY KEY (id),
    CONSTRAINT FK_stages_boards FOREIGN KEY (board_id) REFERENCES boards(id)
);

CREATE TABLE IF NOT EXISTS fellow_groups (
    id INT AUTO_INCREMENT NOT NULL,
    project_id INT NOT NULL,
    manager_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT PK_fellow_groups PRIMARY KEY (id),
    CONSTRAINT FK_fellow_groups_projects FOREIGN KEY (project_id) REFERENCES projects(id),
    CONSTRAINT FK_fellow_groups_manager FOREIGN KEY (manager_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS tasks (
    id INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    responsible_id INT,
    fellow_group_id INT,
    current_stage_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT PK_tasks PRIMARY KEY (id),
    CONSTRAINT FK_tasks_users FOREIGN KEY (responsible_id) REFERENCES users(id),
    CONSTRAINT FK_tasks_fellow_groups FOREIGN KEY (fellow_group_id) REFERENCES fellow_groups(id),
    CONSTRAINT FK_tasks_stages FOREIGN KEY (current_stage_id) REFERENCES stages(id),
    CONSTRAINT CK_tasks_responsible_or_group
        CHECK (responsible_id IS NOT NULL OR fellow_group_id IS NOT NULL)
);

-- Ao retornar deve ser registrado um evento em task_events para não perder o histórico
CREATE TABLE IF NOT EXISTS task_stage_deadlines (
    id INT AUTO_INCREMENT NOT NULL,
    task_id INT NOT NULL,
    stage_id INT NOT NULL,
    due_at TIMESTAMP NULL,
    entered_stage_at TIMESTAMP NOT NULL,
    left_stage_at TIMESTAMP,
    CONSTRAINT PK_task_stage_deadlines PRIMARY KEY (id),
    CONSTRAINT FK_task_stage_deadlines_tasks FOREIGN KEY (task_id) REFERENCES tasks (id),
    CONSTRAINT FK_task_stage_deadlines_stages FOREIGN KEY (stage_id) REFERENCES stages (id)
);

CREATE TABLE IF NOT EXISTS fellow_groups_users (
    user_id INT NOT NULL,
    group_id INT NOT NULL,
    CONSTRAINT PK_fellow_groups_users PRIMARY KEY (user_id, group_id),
    CONSTRAINT FK_fellow_groups_users_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT FK_fellow_groups_users_fellow_groups FOREIGN KEY (group_id) REFERENCES fellow_groups(id)
);

CREATE TABLE IF NOT EXISTS task_events (
    id INT AUTO_INCREMENT NOT NULL,
    task_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(2000),
    occurred_at TIMESTAMP NOT NULL,
    CONSTRAINT PK_task_events PRIMARY KEY (id),
    CONSTRAINT FK_task_events_tasks FOREIGN KEY (task_id) REFERENCES tasks(id)
);

CREATE TABLE IF NOT EXISTS transactional_outbox (
    id VARCHAR(36) NOT NULL,
    topic VARCHAR(255) NOT NULL,
    entity_id VARCHAR(32) NOT NULL,
    event_name VARCHAR(128) NOT NULL,
    event_data JSON NOT NULL,
    registered_at TIMESTAMP NOT NULL,
    processed_at TIMESTAMP,
    CONSTRAINT PK_transactional_outbox PRIMARY KEY (id)
);