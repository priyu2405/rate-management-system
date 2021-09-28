package com.rms.api.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.rms.api.model.ErrorMessage;
import com.rms.api.utils.RateApiCommonConstants;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

	@ExceptionHandler(NotFoundExceptionHandler.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage notFoundException(NotFoundExceptionHandler ex, WebRequest request) {
		int status = Response.Status.NOT_FOUND.getStatusCode();
		ErrorMessage message = new ErrorMessage(status, status, ex.getMessages());
		return message;
	}

	@ExceptionHandler(Exception.class)
	public ErrorMessage exceptionHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
		List<String> erroMessages = new ArrayList<>();
		erroMessages.add(RateApiCommonConstants.INTERNAL_SERVER_ERROR);
		ErrorMessage message = new ErrorMessage(status, status, erroMessages);
		return message;
	}

	@ExceptionHandler(DataValidationExceptionHandler.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage dataValidationException(DataValidationExceptionHandler ex, WebRequest request) {
		int status = Response.Status.BAD_REQUEST.getStatusCode();
		ErrorMessage message = new ErrorMessage(status, status, ex.getMessages());
		return message;
	}
}
