package com.epam.esm.exeption_handling.exeptions;

import com.epam.esm.exeption_handling.MainCustomException;

public class DuplicateDataException extends MainCustomException {

    private static final String EXCEPTION_CODE = "3";

    public DuplicateDataException(String message, String code) {
        super(message, EXCEPTION_CODE + code);
    }
}
