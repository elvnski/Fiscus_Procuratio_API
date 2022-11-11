package com.fpapi.fiscus_procuratio_api.exceptions.handlers;

import com.fpapi.fiscus_procuratio_api.exceptions.CashOverdrawException;
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

}
