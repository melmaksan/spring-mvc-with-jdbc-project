package com.epam.esm.exeption_handling.exeptions;

import com.epam.esm.exeption_handling.MainCertificateException;

public class EmptyRequestBodyException extends MainCertificateException {

    private static final String EXCEPTION_CODE = "2";

    public EmptyRequestBodyException(String message, String code) {
        super(message, EXCEPTION_CODE + code);
    }
}
