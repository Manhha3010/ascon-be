package com.exsoft.momedumerchant.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorHandle {
    @ExceptionHandler(MomEduException.class)
    public ResponseEntity handleMomEduException(MomEduException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleUnwantedException(Exception e) {
        log.error(ExceptionMessage.SERVER_ERROR, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(ExceptionMessage.SERVER_ERROR);
    }

}
