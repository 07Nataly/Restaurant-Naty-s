/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */
// Clase Reporte
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporteDiario {

    public static void generarReporteDiario(Cocina cocina) {
        try {
            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String nombreArchivo = "Reporte_Diario_" + fecha + ".pdf";

            Document documento = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            Font titulo = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.RED);
            Font subtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.FontFamily.HELVETICA, 11);

            Paragraph t = new Paragraph("REPORTE DIARIO - RESTAURANT NATY'S", titulo);
            t.setAlignment(Element.ALIGN_CENTER);
            documento.add(t);

            documento.add(new Paragraph("Fecha: " + new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(new Date()), subtitulo));
            documento.add(new Paragraph("Generado a las: " + new SimpleDateFormat("HH:mm:ss").format(new Date()), normal));
            documento.add(Chunk.NEWLINE);

            // Movimientos del día
            documento.add(new Paragraph("MOVIMIENTOS DEL DÍA:", subtitulo));
            for (String mov : RegistroDiario.getMovimientos()) {
                documento.add(new Paragraph(" • " + mov, normal));
            }

            documento.add(Chunk.NEWLINE);
            documento.add(new Paragraph("INVENTARIO FINAL:", subtitulo));

            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(80);
            tabla.addCell("Ingrediente");
            tabla.addCell("Cantidad Restante");

            for (int i = 0; i < cocina.ingredientesDisponibles.size(); i++) {
                tabla.addCell(cocina.ingredientesDisponibles.get(i));
                tabla.addCell(String.valueOf(cocina.cantidadesDisponibles.get(i)));
            }
            documento.add(tabla);

            documento.add(Chunk.NEWLINE);
            Paragraph cierre = new Paragraph("¡Fin del día exitoso! Gracias por tu trabajo", new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC));
            cierre.setAlignment(Element.ALIGN_CENTER);
            documento.add(cierre);

            documento.close();
            System.out.println("\n Reporte diario generado: " + nombreArchivo);

        } catch (Exception e) {
            System.out.println("Error generando reporte diario: " + e.getMessage());
        }
    }
}