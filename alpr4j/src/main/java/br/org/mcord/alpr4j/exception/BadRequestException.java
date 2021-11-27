package br.org.mcord.alpr4j.exception;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = -4225504394612584139L;
	
	public BadRequestException(String message) {
		super(message);
	}

}
