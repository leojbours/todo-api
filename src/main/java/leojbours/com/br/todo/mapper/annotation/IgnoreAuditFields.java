package leojbours.com.br.todo.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.mapstruct.Mapping;

@Target(ElementType.METHOD)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "deletedAt", ignore = true)
public @interface IgnoreAuditFields {

}
