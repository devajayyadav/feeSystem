package com.appoitment.feesystem.service;

import com.appoitment.feesystem.entity.AppointmentDetailsDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.properties.HorizontalAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] generateAppointmentPdf(AppointmentDetailsDTO appointment) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        
        // Create a new page
        PdfPage page = pdf.addNewPage();
        Rectangle pageSize = page.getPageSize();
        
        // Define margins and content area
        float margin = 50;
        float innerMargin = 20; // margin for watermark inside the border
        Rectangle contentArea = new Rectangle(
            margin, 
            margin, 
            pageSize.getWidth() - 2 * margin, 
            pageSize.getHeight() - 2 * margin
        );
        
        // Define watermark area (inside the border with inner margin)
        Rectangle watermarkArea = new Rectangle(
            margin + innerMargin,
            margin + innerMargin,
            pageSize.getWidth() - 2 * (margin + innerMargin),
            pageSize.getHeight() - 2 * (margin + innerMargin)
        );
        
        // Draw border first
        PdfCanvas canvas = new PdfCanvas(page);
        canvas.setStrokeColor(ColorConstants.BLACK);
        canvas.setLineWidth(1);
        canvas.rectangle(contentArea.getLeft(), contentArea.getBottom(), 
                        contentArea.getWidth(), contentArea.getHeight());
        canvas.stroke();
        
        // Add watermark background only within the inner area
        Canvas watermarkCanvas = new Canvas(page, watermarkArea);
        watermarkCanvas.setFontColor(ColorConstants.LIGHT_GRAY);
        watermarkCanvas.setFontSize(30);
        watermarkCanvas.setOpacity(0.1f);
        
        // Calculate number of watermarks that will fit
        float watermarkWidth = 180; // approximate width of "INVERTIS" text
        float watermarkHeight = 40; // approximate height between rows
        
        // Calculate available space
        float availableWidth = watermarkArea.getWidth();
        float availableHeight = watermarkArea.getHeight();
        
        // Calculate number of watermarks needed
        int numColumns = (int)(availableWidth / watermarkWidth) + 1;
        int numRows = (int)(availableHeight / watermarkHeight) + 1;
        
        // Calculate spacing to distribute watermarks evenly
        float xSpacing = availableWidth / (numColumns - 1);
        float ySpacing = availableHeight / (numRows - 1);
        
        // Create repeating watermark pattern within the inner area
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                float x = watermarkArea.getLeft() + (col * xSpacing);
                float y = watermarkArea.getBottom() + (row * ySpacing);
                watermarkCanvas.showTextAligned("INVERTIS", 
                    x, y, 
                    TextAlignment.LEFT, 
                    0);
            }
        }
        watermarkCanvas.close();
        
        // Create document
        Document document = new Document(pdf);
        document.setMargins(60, 50, 50, 50);
        
        // Add header
        Paragraph header = new Paragraph("APPOINTMENT\nCONFIRMATION")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(24)
                .setBold()
                .setMarginBottom(40);
        document.add(header);
        
        // Add confirmation message
        Paragraph confirmMsg = new Paragraph(appointment.getName() + " appointment is confirmed.")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14)
                .setMarginBottom(40);
        document.add(confirmMsg);
        
        // Add appointment details with larger spacing
        addDetailLine(document, "Name:", appointment.getStudentId());
        addDetailLine(document, "Date:", appointment.getDate());
        addDetailLine(document, "Time:", appointment.getTime());
        addDetailLine(document, "with", "Dr. ");
        
        document.close();
        return baos.toByteArray();
    }
    
    private void addDetailLine(Document document, String label, String value) {
        Paragraph detail = new Paragraph()
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(20)
                .setFontSize(14);
        
        Text labelText = new Text(label + "     ")
                .setBold();
        Text valueText = new Text(value);
        
        detail.add(labelText).add(valueText);
        document.add(detail);
    }
} 