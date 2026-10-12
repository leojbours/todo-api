package leojbours.com.br.todo.util.impl;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import leojbours.com.br.todo.util.ExceptionUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ExceptionUtilImpl implements ExceptionUtil {

  @Override
  public ProblemDetail createProblemDetail(Exception e, HttpServletRequest request, HttpStatus status) {
    return createProblemDetail(e, request, "about:blank", status);
  }

  @Override
  public ProblemDetail createProblemDetail(
      Exception e,
      HttpServletRequest request,
      String typeURI,
      HttpStatus status
  ) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(status);
    problemDetail.setType(URI.create(typeURI));
    problemDetail.setTitle(status.getReasonPhrase());
    problemDetail.setInstance(URI.create(request.getRequestURI()));

    if (status.is5xxServerError()) {
      log.error("Internal error: ", e);
      problemDetail.setDetail("Internal server error");
    } else {
      problemDetail.setDetail(e.getMessage());
    }

    return problemDetail;
  }
}
