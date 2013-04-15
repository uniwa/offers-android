package com.knkcloud.andoid.teiathcoupons.utils;

/**
 * Represents statuses from server respones and errors (getters, setters)
 * 
 * @author Karpouzis Koutsourakis Ntinopoulos
 * 
 */
public class AStatus {

	private final int code;
	private String message;

	public AStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void addMessage(String message) {
		this.message += " " + message;
	}
}
