package br.org.mcord.alpr4j.exception;

public class InternalServerErrorException extends RuntimeException {
	private static final long serialVersionUID = 6579096029047871661L;
	
	public InternalServerErrorException(String message) {
		super(message);
	}

}
