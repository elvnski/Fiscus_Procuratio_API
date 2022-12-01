package com.fpapi.fiscus_procuratio_api.exceptions.handlers;

import com.fpapi.fiscus_procuratio_api.exceptions.*;
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


    @ExceptionHandler(InactiveBusinessAccountException.class)
    public ResponseEntity<ErrorMessage> inactiveBusinessAccountException(InactiveBusinessAccountException inactiveBusinessAccountException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, inactiveBusinessAccountException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(InactiveCashAccountException.class)
    public ResponseEntity<ErrorMessage> inactiveCashAccountException(InactiveCashAccountException inactiveCashAccountException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, inactiveCashAccountException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(InactiveClientAccountException.class)
    public ResponseEntity<ErrorMessage> inactiveClientAccountException(InactiveClientAccountException inactiveClientAccountException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, inactiveClientAccountException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(InactiveOwnerAccountException.class)
    public ResponseEntity<ErrorMessage> inactiveOwnerAccountException(InactiveOwnerAccountException inactiveOwnerAccountException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, inactiveOwnerAccountException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(SellingPriceNotSetException.class)
    public ResponseEntity<ErrorMessage> sellingPriceNotSetException(SellingPriceNotSetException sellingPriceNotSetException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, sellingPriceNotSetException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(ItemSoldOutException.class)
    public ResponseEntity<ErrorMessage> itemSoldOutException(ItemSoldOutException itemSoldOutException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, itemSoldOutException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }


    @ExceptionHandler(IssuedCashInvoicePaidException.class)
    public ResponseEntity<ErrorMessage> issuedCashInvoicePaidException(IssuedCashInvoicePaidException issuedCashInvoicePaidException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, issuedCashInvoicePaidException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }


    @ExceptionHandler(ReceivedCashInvoicePaidException.class)
    public ResponseEntity<ErrorMessage> receivedCashInvoicePaidException(ReceivedCashInvoicePaidException receivedCashInvoicePaidException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, receivedCashInvoicePaidException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }


    @ExceptionHandler(WrongAccountSelectionException.class)
    public ResponseEntity<ErrorMessage> wrongAccountSelectionException(WrongAccountSelectionException wrongAccountSelectionException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, wrongAccountSelectionException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }

    @ExceptionHandler(OverpaymentException.class)
    public ResponseEntity<ErrorMessage> overpaymentException(OverpaymentException overpaymentException, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, overpaymentException.getMessage());

        return ResponseEntity.status(errorMessage.getHttpStatus()).body(errorMessage);
    }

}