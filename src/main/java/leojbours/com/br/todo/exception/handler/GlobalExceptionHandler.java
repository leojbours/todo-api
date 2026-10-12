package leojbours.com.br.todo.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import leojbours.com.br.todo.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final ExceptionUtil exceptionUtil;

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
    if (e.getCause() instanceof ConstraintViolationException ce && ce.getKind() == ConstraintViolationException.ConstraintKind.UNIQUE) {
      String constraintName = ce.getConstraintName();
      int sep = constraintName == null ? -1 : constraintName.lastIndexOf("__");

      if (sep >= 0 && constraintName.contains("UK_")) {
        String field = constraintName.substring(sep + 2);
        return exceptionUtil.createProblemDetail(new Exception(field + " already registered!"), request, HttpStatus.CONFLICT);
      }
      log.error("Unique constraint doesn't follow naming standard: {}", constraintName);
      return exceptionUtil.createProblemDetail(new Exception("Resource already exists"), request, HttpStatus.CONFLICT);
    }

    log.warn("Untreated data integrity violation", e);
    return exceptionUtil.createProblemDetail(new Exception("Data integrity violation"), request, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGenericException(Exception e, HttpServletRequest request) {
    return exceptionUtil.createProblemDetail(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
