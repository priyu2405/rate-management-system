package com.rms.api.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundExceptionHandler extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> messages;
	private Integer code = 404;

	public NotFoundExceptionHandler() {
		super();
		this.messages = new ArrayList<String>();
	}

	public NotFoundExceptionHandler(String message) {
		super(message);
		this.messages = new ArrayList<String>();
		if (StringUtils.isNotBlank(message)) {
			this.messages.add(message);
		}

	}

	public NotFoundExceptionHandler(List<String> messages) {
		super(messages.toString());
		this.messages = messages;
	}

	public NotFoundExceptionHandler(String message, Integer code) {
		super(message);
		this.messages = new ArrayList<String>();
		if (StringUtils.isNotBlank(message)) {
			this.messages.add(message);
		}

		this.code = code;
	}

	public NotFoundExceptionHandler(List<String> messages, Integer code) {
		super(messages.toString());
		this.messages = messages;
		this.code = code;
	}

}
