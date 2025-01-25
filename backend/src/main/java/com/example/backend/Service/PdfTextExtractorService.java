package com.example.backend.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class PdfTextExtractorService {

    public String extractTextFromPdf(String base64Pdf) throws IOException {
        byte[] decodedPdf = Base64.getDecoder().decode(base64Pdf);

        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(decodedPdf))) {
            // Create a PDFTextStripper to extract text
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            return text;
        }
    }
}
