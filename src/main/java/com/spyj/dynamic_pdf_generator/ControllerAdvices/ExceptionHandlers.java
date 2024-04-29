package com.spyj.dynamic_pdf_generator.ControllerAdvices;

import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.FailedToCreatePDFException;
import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.PDFNotFoundException;
import com.spyj.dynamic_pdf_generator.DTOs.ExceptionDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Arrays;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(PDFNotFoundException.class)
    public ResponseEntity<ExceptionDto> handlePDFNotFoundException(PDFNotFoundException pdfNotFoundException) {
        ExceptionDto exceptionDto = new ExceptionDto(
                pdfNotFoundException.getMessage(),
                "404",
                "PDF Not Found",
                Instant.now().toString(),
                Arrays.toString(pdfNotFoundException.getStackTrace())
        );
        Logger.getLogger("Trace : " + exceptionDto.getTrace());
        return new ResponseEntity<>(
                exceptionDto, HttpStatusCode.valueOf(404)
        );
    }

    @ExceptionHandler(FailedToCreatePDFException.class)
    public ResponseEntity<ExceptionDto> handleUnableToCreatePDFException(FailedToCreatePDFException failedToCreatePDFException) {
        ExceptionDto exceptionDto = new ExceptionDto(
                failedToCreatePDFException.getMessage(),
                "409",
                "Failed to create PDF",
                Instant.now().toString(),
                Arrays.toString(failedToCreatePDFException.getStackTrace())
        );
        Logger.getLogger("Trace : " + exceptionDto.getTrace());
        return new ResponseEntity<>(
                exceptionDto, HttpStatusCode.valueOf(409)
        );
    }
}


