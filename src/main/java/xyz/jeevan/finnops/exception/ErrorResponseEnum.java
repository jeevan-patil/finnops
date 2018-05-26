package xyz.jeevan.finnops.exception;

import org.springframework.http.HttpStatus;

public enum ErrorResponseEnum {
  GENERAL_ERROR(100, "An exception has occured while processing your request.",
      HttpStatus.INTERNAL_SERVER_ERROR), OLD_TRANSACTION_ERROR(204,
          "This is an old transaction. Can not proceed.", HttpStatus.NO_CONTENT);

  private int code;
  private String errorText;
  private HttpStatus httpStatus;

  private ErrorResponseEnum(int code, String errorText, HttpStatus httpStatus) {
    this.code = code;
    this.errorText = errorText;
    this.httpStatus = httpStatus;
  }

  public int getCode() {
    return code;
  }

  public String getErrorText() {
    return errorText;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
