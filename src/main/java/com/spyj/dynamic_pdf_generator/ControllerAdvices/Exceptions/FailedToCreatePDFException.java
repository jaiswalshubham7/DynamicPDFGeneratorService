package com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions;

public class FailedToCreatePDFException extends Exception{
    public FailedToCreatePDFException(String message){
        super(message);
    }
}
