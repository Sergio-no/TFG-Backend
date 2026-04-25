package org.example.tfgbackend.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.example.tfgbackend.model.Factura;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class FacturaPdfService {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarPdf(Factura f) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document doc = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            // ── Cabecera empresa ──
            Font fontTitulo = new Font(Font.HELVETICA, 22, Font.BOLD, new Color(30, 58, 95));
            Font fontSub = new Font(Font.HELVETICA, 10, Font.NORMAL, new Color(100, 100, 100));
            Font fontNormal = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);
            Font fontBold = new Font(Font.HELVETICA, 11, Font.BOLD, Color.BLACK);
            Font fontHeader = new Font(Font.HELVETICA, 11, Font.BOLD, Color.WHITE);

            Paragraph titulo = new Paragraph("AutoElite", fontTitulo);
            titulo.setAlignment(Element.ALIGN_LEFT);
            doc.add(titulo);

            Paragraph subtitulo = new Paragraph("Taller mecánico · Gestión integral", fontSub);
            subtitulo.setSpacingAfter(20);
            doc.add(subtitulo);

            // ── Número de factura ──
            Font fontFactura = new Font(Font.HELVETICA, 16, Font.BOLD, new Color(30, 78, 107));
            Paragraph numFactura = new Paragraph("Factura: " + f.getNumeroFactura(), fontFactura);
            numFactura.setAlignment(Element.ALIGN_RIGHT);
            numFactura.setSpacingAfter(20);
            doc.add(numFactura);

            // ── Datos del cliente ──
            PdfPTable datosTable = new PdfPTable(2);
            datosTable.setWidthPercentage(100);
            datosTable.setSpacingAfter(20);

            PdfPCell cellLabel, cellValue;

            // Fecha
            cellLabel = createLabelCell("Fecha:", fontBold);
            cellValue = createValueCell(f.getFecha().format(FMT), fontNormal);
            datosTable.addCell(cellLabel);
            datosTable.addCell(cellValue);

            // Cliente
            String nombreCliente = f.getCliente().getUsuario().getNombre()
                    + " " + f.getCliente().getUsuario().getApellidos();
            cellLabel = createLabelCell("Cliente:", fontBold);
            cellValue = createValueCell(nombreCliente, fontNormal);
            datosTable.addCell(cellLabel);
            datosTable.addCell(cellValue);

            // Email
            cellLabel = createLabelCell("Email:", fontBold);
            cellValue = createValueCell(f.getCliente().getUsuario().getEmail(), fontNormal);
            datosTable.addCell(cellLabel);
            datosTable.addCell(cellValue);

            // Vehículo
            String vehiculo = f.getReparacion().getVehiculo().getMarca() + " "
                    + f.getReparacion().getVehiculo().getModelo() + " · "
                    + f.getReparacion().getVehiculo().getMatricula();
            cellLabel = createLabelCell("Vehículo:", fontBold);
            cellValue = createValueCell(vehiculo, fontNormal);
            datosTable.addCell(cellLabel);
            datosTable.addCell(cellValue);

            doc.add(datosTable);

            // ── Línea separadora ──
            doc.add(new Paragraph(" "));

            // ── Tabla de conceptos ──
            PdfPTable conceptosTable = new PdfPTable(new float[]{4f, 1.5f});
            conceptosTable.setWidthPercentage(100);
            conceptosTable.setSpacingAfter(10);

            // Header
            PdfPCell headerConcepto = new PdfPCell(new Phrase("Concepto", fontHeader));
            headerConcepto.setBackgroundColor(new Color(30, 78, 107));
            headerConcepto.setPadding(10);
            headerConcepto.setBorderWidth(0);
            conceptosTable.addCell(headerConcepto);

            PdfPCell headerImporte = new PdfPCell(new Phrase("Importe", fontHeader));
            headerImporte.setBackgroundColor(new Color(30, 78, 107));
            headerImporte.setPadding(10);
            headerImporte.setBorderWidth(0);
            headerImporte.setHorizontalAlignment(Element.ALIGN_RIGHT);
            conceptosTable.addCell(headerImporte);

            // Fila de reparación
            PdfPCell concepto = new PdfPCell(new Phrase(
                    "Reparación #" + f.getReparacion().getId(), fontNormal));
            concepto.setPadding(8);
            concepto.setBorderWidth(0);
            concepto.setBorderWidthBottom(0.5f);
            concepto.setBorderColor(new Color(220, 220, 220));
            conceptosTable.addCell(concepto);

            PdfPCell importe = new PdfPCell(new Phrase(
                    f.getTotal().toPlainString() + " €", fontNormal));
            importe.setPadding(8);
            importe.setBorderWidth(0);
            importe.setBorderWidthBottom(0.5f);
            importe.setBorderColor(new Color(220, 220, 220));
            importe.setHorizontalAlignment(Element.ALIGN_RIGHT);
            conceptosTable.addCell(importe);

            doc.add(conceptosTable);

            // ── Total ──
            PdfPTable totalTable = new PdfPTable(new float[]{4f, 1.5f});
            totalTable.setWidthPercentage(100);
            totalTable.setSpacingBefore(10);
            totalTable.setSpacingAfter(20);

            Font fontTotal = new Font(Font.HELVETICA, 14, Font.BOLD, new Color(30, 78, 107));

            PdfPCell totalLabel = new PdfPCell(new Phrase("TOTAL", fontTotal));
            totalLabel.setPadding(10);
            totalLabel.setBorderWidth(0);
            totalLabel.setBackgroundColor(new Color(240, 245, 250));
            totalTable.addCell(totalLabel);

            PdfPCell totalValue = new PdfPCell(new Phrase(
                    f.getTotal().toPlainString() + " €", fontTotal));
            totalValue.setPadding(10);
            totalValue.setBorderWidth(0);
            totalValue.setBackgroundColor(new Color(240, 245, 250));
            totalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalTable.addCell(totalValue);

            doc.add(totalTable);

            // ── Estado del pago ──
            Font fontEstado;
            String textoEstado;
            if (f.isPagada()) {
                fontEstado = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(34, 139, 34));
                textoEstado = "PAGADA";
                if (f.getMetodoPago() != null) {
                    textoEstado += " · Método: " + f.getMetodoPago();
                }
                if (f.getFechaPago() != null) {
                    textoEstado += " · " + f.getFechaPago().format(FMT);
                }
            } else {
                fontEstado = new Font(Font.HELVETICA, 12, Font.BOLD, new Color(200, 50, 50));
                textoEstado = "PENDIENTE DE PAGO";
            }
            Paragraph estado = new Paragraph(textoEstado, fontEstado);
            estado.setAlignment(Element.ALIGN_CENTER);
            estado.setSpacingAfter(30);
            doc.add(estado);

            // ── Pie de página ──
            Paragraph pie = new Paragraph(
                    "AutoElite · Gracias por confiar en nuestro servicio", fontSub);
            pie.setAlignment(Element.ALIGN_CENTER);
            doc.add(pie);

        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        } finally {
            doc.close();
        }

        return baos.toByteArray();
    }

    private PdfPCell createLabelCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorderWidth(0);
        cell.setPadding(4);
        return cell;
    }

    private PdfPCell createValueCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorderWidth(0);
        cell.setPadding(4);
        return cell;
    }
}

