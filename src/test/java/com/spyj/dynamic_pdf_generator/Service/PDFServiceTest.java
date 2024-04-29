package com.spyj.dynamic_pdf_generator.Service;

import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.FailedToCreatePDFException;
import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.PDFNotFoundException;
import com.spyj.dynamic_pdf_generator.DTOs.InvoiceRequestDto;
import com.spyj.dynamic_pdf_generator.DTOs.Item;
import com.spyj.dynamic_pdf_generator.Service.Impl.PDFService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PDFServiceTest {

    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private PDFService pdfService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGeneratePDF() throws FailedToCreatePDFException {
        InvoiceRequestDto invoiceRequestDto = new InvoiceRequestDto();
        invoiceRequestDto.setSeller("XYZ Pvt. Ltd.");
        invoiceRequestDto.setSellerGstin("29AABBCCDD121ZD");
        invoiceRequestDto.setSellerAddress("New Delhi, India");
        invoiceRequestDto.setBuyer("Vedant Computers");
        invoiceRequestDto.setBuyerGstin("29AABBCCDD131ZD");
        invoiceRequestDto.setBuyerAddress("New Delhi, India");
        Item item = new Item();
        item.setName("Product 1");
        item.setQuantity("12 Nos");
        item.setRate(123.00);
        item.setAmount(1476.00);
        invoiceRequestDto.setItems(Collections.singletonList(item));

        when(templateEngine.process(any(String.class), any(Context.class))).thenReturn("invoiceTemplate");

        String result = pdfService.generatePDF(invoiceRequestDto);
        assertEquals(result, "C:/Users/Shubham/Documents/Intellij-Projects/SpringBoot/DynamicPDFGeneratorService/src/main/resources/static" + "/" + PDFService.generateFileName(invoiceRequestDto));
    }

    @Test
    public void testDownloadPDF() throws PDFNotFoundException {
        String pdfPath = "invoice.pdf";
        Resource result = pdfService.downloadPDF(pdfPath);
        assertEquals(result, new FileSystemResource("C:/Users/Shubham/Documents/Intellij-Projects/SpringBoot/DynamicPDFGeneratorService/src/main/resources/static" + "/" + pdfPath));
    }
}