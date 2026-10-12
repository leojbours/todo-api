package leojbours.com.br.todo.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public interface ExceptionUtil {
  ProblemDetail createProblemDetail(Exception e, HttpServletRequest webRequest, HttpStatus status);
  ProblemDetail createProblemDetail(Exception e, HttpServletRequest webRequest, String typeURI, HttpStatus status);
}
