package com.epam.esm.exeption_handling;

public class MainCustomException extends RuntimeException {

    private String serviceCode;

    public MainCustomException(String message, String code) {
        super(message);
        this.serviceCode = code;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
