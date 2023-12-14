package com.teastore.exceptionHandler;

import java.util.HashMap;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.teastore.message.ResponseMessage;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
    
        HashMap<String, String> errorResponse=new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error)->{
                String fieldName=((FieldError)error).getField();
                String message=error.getDefaultMessage();
                
                errorResponse.put(fieldName, message);
        });
        
        return new ResponseEntity<Object>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    
	
	//image
	 @ExceptionHandler(MaxUploadSizeExceededException.class)
	  public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
	    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
	  }
 

}