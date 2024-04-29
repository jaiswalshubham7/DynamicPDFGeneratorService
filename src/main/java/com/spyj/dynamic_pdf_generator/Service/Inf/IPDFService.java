package com.spyj.dynamic_pdf_generator.Service.Inf;

import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.FailedToCreatePDFException;
import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.PDFNotFoundException;
import com.spyj.dynamic_pdf_generator.DTOs.InvoiceRequestDto;
import org.springframework.core.io.Resource;

public interface IPDFService {
    String generatePDF(InvoiceRequestDto invoiceRequestDto) throws FailedToCreatePDFException;
    Resource downloadPDF(String pdfPath) throws PDFNotFoundException;
}
