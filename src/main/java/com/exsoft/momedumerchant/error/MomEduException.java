package com.exsoft.momedumerchant.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MomEduException extends Exception {
    private int statusCode;

    public MomEduException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
