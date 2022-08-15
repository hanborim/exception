package com.example.exception.advice;


import com.example.exception.controller.ApiController;
import com.example.exception.dto.Error;
import com.example.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestControllerAdvice(basePackageClasses = ApiController.class)
//ApiController 특정클래스에만 지정
public class ApiControllerAdvice {


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e)
    {
        System.out.println(e.getClass().getName());
        System.out.println("---------");
        System.out.println(e.getLocalizedMessage());
        System.out.println("---------");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
    }


     @ExceptionHandler(value = MethodArgumentNotValidException.class)
     public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest)
     {

         List<Error> errorList = new ArrayList<>();
         BindingResult bindingResult = e.getBindingResult();
         bindingResult.getAllErrors().forEach(error->{
             FieldError field = (FieldError) error;
             String filedName = field.getField();
             String message = field.getDefaultMessage();
             String value = field.getRejectedValue().toString();

             System.out.println(filedName);
             System.out.println(message);
             System.out.println(value);

             Error errorMessage = new Error();
             errorMessage.setField(filedName);
             errorMessage.setMessage(message);
             errorMessage.setInvlid(value);

             errorList.add(errorMessage);



         });

         ErrorResponse errorResponse = new ErrorResponse();
         errorResponse.setErrorList(errorList);
         errorResponse.setMessage("");
         errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
         errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
         errorResponse.setResultCode("FAIL");


         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
     }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException e, HttpServletRequest httpServletRequest)
     {
         //아큐먼트 잘 넣었는데 , 그아큐먼트의 정보 가 잘못 되면 타는 로직
         //http://localhost:8080/api/user?name&age=0 , age가 0일때
         //어떠한 필드가 잘못됬는지 정보를 가지고 있음
         List<Error> errorList = new ArrayList<>();


         e.getConstraintViolations().forEach(error->{
             Stream<Path.Node> stream = StreamSupport.stream(error.getPropertyPath().spliterator(),false);
             List<Path.Node> list = stream.collect(Collectors.toList());

             String field = list.get(list.size() -1).getName();
             String message = error.getMessage();
             String invalidValue = error.getInvalidValue().toString();


             System.out.println(error);
             System.out.println("ConstraintViolationException ---");
             System.out.println(field);
             System.out.println(message);
             System.out.println(invalidValue);

             Error errorMessage = new Error();
             errorMessage.setField(field);
             errorMessage.setMessage(message);
             errorMessage.setInvlid(invalidValue);

             errorList.add(errorMessage);

         });

         ErrorResponse errorResponse = new ErrorResponse();
         errorResponse.setErrorList(errorList);
         errorResponse.setMessage("");
         errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
         errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString());
         errorResponse.setResultCode("FAIL");

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
         //errorResponse 리스펀스가 굉장히 이쁘게 들어옴
     }
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e)
     {

         //http://localhost:8080/api/user?name&age 아무값도 없을때 타는로직
         String filedName = e.getParameterName();
         String filedType = e.getParameterType();
         String invalidValue = e.getMessage();

         System.out.println(filedName);
         System.out.println(filedType);
         System.out.println(invalidValue);

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

     }


}
