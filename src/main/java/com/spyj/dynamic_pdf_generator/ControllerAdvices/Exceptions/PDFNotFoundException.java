package com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions;

public class PDFNotFoundException extends Exception{
    public PDFNotFoundException(String message){
        super(message);
    }
}
