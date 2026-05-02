package com.app.exception;


import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ErrorResponse> handleValidation(
	            MethodArgumentNotValidException ex) {

	        Map<String, String> errors = new HashMap <>();

	        ex.getBindingResult().getFieldErrors().forEach(error ->
	                errors.put(error.getField(), error.getDefaultMessage())
	        );

	        return ResponseEntity
	                .badRequest()
	                .body(new ErrorResponse(
                            400,
                            "Validation Failed",
                            errors
                    ));
	    }

        @ExceptionHandler(ProductNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ErrorResponse(
                                    404,
                                    "Product Not Found",
                                    e.getMessage()
                            )
                    );
        }

        @ExceptionHandler(CategoryNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e){
         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                 .body(
                         new ErrorResponse(
                                 400,
                                 "Category Not Found",
                                 e.getMessage()
                         )
                 );
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ErrorResponse> hanleBadCredentialsException(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ErrorResponse(
                            401,
                            "Invalid Email Or Password",
                            e.getMessage()
                    )
            );
        }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> hanleAccessDeniedException(AccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponse(
                        403,
                        "Access Denied",
                        e.getMessage()
                )
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> hanleJwtException(JwtException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponse(
                        401,
                        "Invalid or Expired Token",
                        e.getMessage()
                )
        );
    }

        @ExceptionHandler(CategoryRequiredException.class)
        public ResponseEntity<ErrorResponse> handleCategoryRequiredException(CategoryRequiredException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            new ErrorResponse(
                                    404,
                                    "Category Required",
                                    e.getMessage()
                            )
                    );
        }




	    // ✅ Generic Errors
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

	        return ResponseEntity
	                .status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ErrorResponse(
                            500,
                            "something went wrong",
                            null
                    ));
	    }
}
