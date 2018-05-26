package xyz.jeevan.finnops.exception;

public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 4354136566134829449L;

  private ErrorResponseEnum errorResponse;

  public ApplicationException(ErrorResponseEnum errorResponse) {
    super(errorResponse.getErrorText());
    this.errorResponse = errorResponse;
  }

  public ApplicationException(String errorMessage) {
    super(errorMessage);
  }

  public ApplicationException(ErrorResponseEnum errorResponse, Throwable throwable) {
    super(throwable);
    this.errorResponse = errorResponse;
  }

  public ErrorResponseEnum getErrorResponse() {
    return errorResponse;
  }

  public void setErrorResponse(ErrorResponseEnum errorResponse) {
    this.errorResponse = errorResponse;
  }
}
