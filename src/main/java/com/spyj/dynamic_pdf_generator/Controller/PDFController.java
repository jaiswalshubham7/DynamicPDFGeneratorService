package com.spyj.dynamic_pdf_generator.Controller;

import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.FailedToCreatePDFException;
import com.spyj.dynamic_pdf_generator.ControllerAdvices.Exceptions.PDFNotFoundException;
import com.spyj.dynamic_pdf_generator.DTOs.InvoiceRequestDto;
import com.spyj.dynamic_pdf_generator.Service.Inf.IPDFService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf")
public class PDFController {

    private final IPDFService pdfService;

    public PDFController(IPDFService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generatePDF(@RequestBody InvoiceRequestDto invoiceRequestDto) throws FailedToCreatePDFException {
        String pdfPath = pdfService.generatePDF(invoiceRequestDto);
        return ResponseEntity.ok(pdfPath);
    }

    @GetMapping("/download/{pdfPath}")
    public ResponseEntity<Resource> downloadPDF(@PathVariable String pdfPath) throws PDFNotFoundException {
        Resource pdfFile = pdfService.downloadPDF(pdfPath);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfFile);
    }
}
