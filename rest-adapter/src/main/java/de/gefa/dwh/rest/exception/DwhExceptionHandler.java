package my.project.dwh.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import my.project.dwh.domain.exception.DwhNotFoundException;

@RestControllerAdvice(basePackages = {"my.project.dwh"})
public final class DwhExceptionHandler {

  private DwhExceptionHandler() {
  }

  @ExceptionHandler(value = DwhNotFoundException.class)
  public static ResponseEntity<DwhExceptionResponse> handleDwhNotFoundException(
      final Exception exception, final WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        DwhExceptionResponse.builder().message(exception.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
  }
}
