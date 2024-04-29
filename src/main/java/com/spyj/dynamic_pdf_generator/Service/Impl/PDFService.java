package com.spyj.dynamic_pdf_generator.Service.Impl;

import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.FailedToCreatePDFException;
import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.PDFNotFoundException;
import com.spyj.dynamic_pdf_generator.DTOs.InvoiceRequestDto;
import com.spyj.dynamic_pdf_generator.Service.Inf.IPDFService;
import com.spyj.dynamic_pdf_generator.Constant.PDFStringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PDFService implements IPDFService{

    private final TemplateEngine templateEngine;
    private final static Logger log = LoggerFactory.getLogger(PDFService.class);

    public PDFService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    @Override
    public String generatePDF(InvoiceRequestDto invoiceRequestDto) throws FailedToCreatePDFException {

        String fileName = generateFileName(invoiceRequestDto);
        String pdfFilePath = PDFStringConstants.PDF_STORAGE_LOCATION + "/" + fileName;

        // Check if the PDF file already exists
        File existingFile = new File(pdfFilePath);
        if (existingFile.exists()) {
            log.info("File already exists");
            return pdfFilePath;
        }

        Context context = new Context();
        context.setVariable("seller", invoiceRequestDto.getSeller());
        context.setVariable("sellerGstin", invoiceRequestDto.getSellerGstin());
        context.setVariable("sellerAddress", invoiceRequestDto.getSellerAddress());
        context.setVariable("buyer", invoiceRequestDto.getBuyer());
        context.setVariable("buyerGstin", invoiceRequestDto.getBuyerGstin());
        context.setVariable("buyerAddress", invoiceRequestDto.getBuyerAddress());
        context.setVariable("items", invoiceRequestDto.getItems());
        String htmlContent = templateEngine.process(PDFStringConstants.PDF_TEMPLATE, context);

        // Convert HTML content to PDF
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        try (FileOutputStream fos = new FileOutputStream(pdfFilePath)) {
            renderer.createPDF(fos);
        } catch (IOException e) {
            log.error("Failed to create PDF: " + e.getMessage());
            throw new FailedToCreatePDFException(e.getMessage());
        }
        return pdfFilePath;
    }

    @Override
    public Resource downloadPDF(String pdfPath) throws PDFNotFoundException {
        String filePath = PDFStringConstants.PDF_STORAGE_LOCATION + File.separator + pdfPath;
        File file = new File(filePath);

        if (file.exists()) {
            return new FileSystemResource(file);
        } else {
            log.error("PDF not found");
            throw new PDFNotFoundException("PDF not found");
        }
    }

    public static String generateFileName(InvoiceRequestDto invoiceRequestDto) {
        // Generate a hash of request body to ensure uniqueness
        String hash = String.valueOf(invoiceRequestDto.hashCode());
        return hash + PDFStringConstants.PDF_EXTENSION;
    }
}
