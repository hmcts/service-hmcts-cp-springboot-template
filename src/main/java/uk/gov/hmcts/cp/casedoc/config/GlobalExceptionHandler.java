package uk.gov.hmcts.cp.casedoc.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalArgumentException.class)
  ProblemDetail badRequest(IllegalArgumentException ex) {
    ProblemDetail p = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    p.setDetail(ex.getMessage());
    return p;
  }
}
