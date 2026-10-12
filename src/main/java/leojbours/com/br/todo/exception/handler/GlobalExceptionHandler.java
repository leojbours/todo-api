package leojbours.com.br.todo.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import leojbours.com.br.todo.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final ExceptionUtil exceptionUtil;

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGenericException(Exception e, HttpServletRequest request) {
    return exceptionUtil.createProblemDetail(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
