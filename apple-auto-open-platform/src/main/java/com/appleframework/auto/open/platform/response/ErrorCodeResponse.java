package com.appleframework.auto.open.platform.response;

import java.io.Serializable;

public class ErrorCodeResponse implements Serializable {
	
	private static final long serialVersionUID = 133360743945965738L;
	
	private boolean isSuccess = true;
	private String errorMessage;
	private String errorCode;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public static ErrorCodeResponse create() {
		return new ErrorCodeResponse();
	}
		
	public static ErrorCodeResponse create(String errorCode, String errorMessage) {
		ErrorCodeResponse reponse = new ErrorCodeResponse();
		reponse.setSuccess(false);
		reponse.setErrorCode(errorCode);
		reponse.setErrorMessage(errorMessage);
		return reponse;
	}
	
	public static ErrorCodeResponse create(String errorCode) {
		ErrorCodeResponse reponse = new ErrorCodeResponse();
		reponse.setSuccess(true);
		reponse.setErrorCode(errorCode);
		return reponse;
	}

}
