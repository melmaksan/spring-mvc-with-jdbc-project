package com.epam.esm.exeption_handling.exeptions;

import com.epam.esm.exeption_handling.MainCertificateException;

public class NoSuchDataException extends MainCertificateException {

    private static final String EXCEPTION_CODE = "1";

    public NoSuchDataException(String message, String code) {
        super(message, EXCEPTION_CODE + code);
    }
}
