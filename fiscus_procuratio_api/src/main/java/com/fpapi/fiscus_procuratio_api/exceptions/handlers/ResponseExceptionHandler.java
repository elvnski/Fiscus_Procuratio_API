package com.fpapi.fiscus_procuratio_api.exceptions.handlers;

import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
import com.fpapi.fiscus_procuratio_api.exceptions.ExcessiveDiscountException;
import com.fpapi.fiscus_procuratio_api.exceptions.IllegalPricingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CashOverdrawException.class)
    public ResponseEntity<ErrorMessage> cashOverdrawException(CashOverdrawException cashOverdrawException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, cashOverdrawException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(IllegalPricingException.class)
    public ResponseEntity<ErrorMessage> illegalPricingException(IllegalPricingException illegalPricingException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FORBIDDEN, illegalPricingException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }


    @ExceptionHandler(ExcessiveDiscountException.class)
    public ResponseEntity<ErrorMessage> excessiveDiscountException(ExcessiveDiscountException excessiveDiscountException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FORBIDDEN, excessiveDiscountException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }


}