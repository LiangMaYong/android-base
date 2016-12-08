package com.liangmayong.base.compat.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

/**
 * HttpError
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class HttpError {
	// URL Error
	public static final String URL_ERROR = "URL_ERROR";
	// Socket closed, servers, DNS error will produce the error.
	public static final String NETWORK_ERROR = "NETWORK_ERROR";
	// Coding format error
	public static final String PARSE_ERROR = "PARSE_ERROR";
	// A mistake of the server's call, most likely 4 or 5 xx xx HTTP status
	// code.
	public static final String SERVER_ERROR = "SERVER_ERROR";
	// Connect to the server timed out
	public static final String TIMEOUT_ERROR = "TIMEOUT_ERROR";
	// Connect to the server failed
	public static final String UNKOWN_ERROR = "UNKOWN_ERROR";

	private Exception exception;

	public Exception getException() {
		return exception;
	}

	HttpError(Exception exception) {
		this.exception = exception;
	}

	/**
	 * Returns appropriate message which is to be displayed to the user against
	 * the specified error object.
	 * @return ErrorMessage
	 */
	public String getErrorMessage() {
		if (exception instanceof MalformedURLException) {
			return URL_ERROR;
		} else if (exception instanceof SocketTimeoutException) {
			return TIMEOUT_ERROR;
		} else if (exception instanceof UnsupportedEncodingException) {
			return PARSE_ERROR;
		} else if (exception instanceof HttpServiceError) {
			return SERVER_ERROR;
		} else if (exception instanceof IOException) {
			return NETWORK_ERROR;
		}
		return UNKOWN_ERROR;
	}
}
