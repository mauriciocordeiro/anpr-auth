package br.org.mcord.alpr4j.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ResponseError> handleUnauthorized(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 401, HttpStatus.UNAUTHORIZED, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ResponseError> handleBadRequest(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 400, HttpStatus.BAD_REQUEST, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseError> handleIllegalArgumentException(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 400, HttpStatus.BAD_REQUEST, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotImplementedException.class)
	public ResponseEntity<ResponseError> handleNotImplemented(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 501, HttpStatus.NOT_IMPLEMENTED, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.NOT_IMPLEMENTED);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ResponseError> handleNotFound(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 404, HttpStatus.NOT_FOUND, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ResponseError> handleExpiredJwtException(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 403, HttpStatus.FORBIDDEN, "Token de autenticação expirado.");		
		return new ResponseEntity<ResponseError>(err, HttpStatus.FORBIDDEN);
	}
	
	/*
	 * 500
	 */
	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<ResponseError> handleInternalServerError(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ResponseError> handleNullPointerException(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ArrayIndexOutOfBoundsException.class)
	public ResponseEntity<ResponseError> handleArrayIndexOutOfBoundsException(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(StackOverflowError.class)
	public ResponseEntity<ResponseError> handleStackOverflowError(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InterruptedException.class)
	public ResponseEntity<ResponseError> handleInterruptedError(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ResponseError> handleIOError(Exception exception, WebRequest request) throws IOException {
		ResponseError err = new ResponseError(LocalDateTime.now(), 500, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());		
		return new ResponseEntity<ResponseError>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
