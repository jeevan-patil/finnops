package xyz.jeevan.finnops.advise;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.jeevan.finnops.domain.error.ErrorResponse;
import xyz.jeevan.finnops.exception.ApplicationException;
import xyz.jeevan.finnops.exception.ErrorResponseEnum;

@RestControllerAdvice(basePackages = {"xyz.jeevan.finnops"})
public class ExceptionHandlerAdvice {

  private static final org.slf4j.Logger log =
      org.slf4j.LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

  @ExceptionHandler({ApplicationException.class})
  public @ResponseBody ResponseEntity<ErrorResponse> handleApplicationException(
      ApplicationException ApplicationException) {
    log.warn("RuntimeException is thrown : ");

    // Create error response object.
    ErrorResponse errorResponse =
        new ErrorResponse(ApplicationException.getErrorResponse().getCode(),
            ApplicationException.getErrorResponse().getErrorText());

    log.warn(errorResponse.toString());
    // Return error response with status.
    return new ResponseEntity<>(errorResponse,
        ApplicationException.getErrorResponse().getHttpStatus());
  }

  @ExceptionHandler({Throwable.class})
  public @ResponseBody ResponseEntity<ErrorResponse> handleException(Throwable throwable) {
    log.error(throwable.getMessage(), throwable);

    ErrorResponse errorResponse = new ErrorResponse(ErrorResponseEnum.GENERAL_ERROR.getCode(),
        ErrorResponseEnum.GENERAL_ERROR.getErrorText());
    log.error(errorResponse.toString());

    return new ResponseEntity<>(errorResponse, ErrorResponseEnum.GENERAL_ERROR.getHttpStatus());
  }
}
