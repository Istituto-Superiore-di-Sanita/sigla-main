package it.cnr.contab.web.rest.exception;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

public class RestException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private Status status;
	public RestException(Status status, String message) {
		super(message);
		this.status = status;
	}
	public Status getStatus() {
		return status;
	}

	public Map<String, Serializable> getErrorMap() {
		Map<String, Serializable> result = new HashMap<String, Serializable>();
		result.put("ERROR", getMessage());
		return result;
	}	
	
}
