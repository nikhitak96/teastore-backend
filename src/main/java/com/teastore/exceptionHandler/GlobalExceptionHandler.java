package com.teastore.exceptionHandler;

import java.util.Date;
import java.util.HashMap;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;



@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

	 @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<HashMap> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e, WebRequest request)
	    {
	        HashMap<String , String> response=new HashMap<>();
	        response.put("date",String.valueOf(new Date()));
	        response.put("reason", e.getMessage());
	        response.put("description", request.getDescription(false));
	        
	        return new ResponseEntity<HashMap>(response, HttpStatus.BAD_REQUEST);       
	    }
	    
	    @ExceptionHandler(NullPointerException.class)
	    public ResponseEntity<HashMap> NullPointerExceptionHandler(NullPointerException e, WebRequest request)
	    {
	        HashMap<String , String> response=new HashMap<>();
	        response.put("date",String.valueOf(new Date()));
	        response.put("reason", "Null value provided!!");
	        response.put("description", request.getDescription(false));
	        
	        return new ResponseEntity<HashMap>(response, HttpStatus.INTERNAL_SERVER_ERROR);     
	    }
	    
	    
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<HashMap> genericExceptionHandler(Exception e, WebRequest request)
	    {
	        HashMap<String , String> response=new HashMap<>();
	        response.put("date",String.valueOf(new Date()));
	        response.put("reason", e.getMessage());
	        response.put("description", request.getDescription(false));
	        
	        return new ResponseEntity<HashMap>(response, HttpStatus.INTERNAL_SERVER_ERROR);     
	    }
	    
	   

}
