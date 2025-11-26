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
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Image;

public class InventarioDiario {

    public static void generarReporteDiario(Cocina cocina) {
        try {
            // ====== 1. CREAR CARPETA AUTOMÁTICAMENTE ======
            File carpeta = new File("Inventarios_Diarios");
            if (!carpeta.exists()) {
                carpeta.mkdir();
                System.out.println("Carpeta 'Inventarios_Diarios' creada automáticamente.");
            }

            // ====== 2. NOMBRE ÚNICO CON FECHA + HORA + MINUTO + SEGUNDO ======
            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String hora = new SimpleDateFormat("HH-mm-ss").format(new Date());
            String nombreArchivo = "Inventarios_Diarios/Inventario_Diario_" + fecha + "_a_las_" + hora + ".pdf";

            // ====== 3. GENERAR EL PDF ======
            Document documento = new Document(PageSize.A4, 50, 50, 90, 70); // Margen superior
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            //Logo
            try {
                Image logo = Image.getInstance(InventarioDiario.class.getResource("/com/mycompany/restaurantnatys/logo.png"));
                logo.scaleAbsolute(90f, 90f);//tamaño logo
                //posicion
                logo.setAbsolutePosition(PageSize.A4.getWidth() - 140, PageSize.A4.getHeight() - 115);
                writer.getDirectContentUnder().addImage(logo);
            } catch (Exception e) {
                System.out.println("Logo no cargado: " + e.getMessage());
            }
            
            Font titulo = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, new BaseColor(139, 0, 0));
            Font subtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.FontFamily.HELVETICA, 11);

            Paragraph t = new Paragraph("INVENTARIO DIARIO - RESTAURANT NATY'S", titulo);
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
            
            // Encabezados con estilo
            PdfPCell cell = new PdfPCell(new Paragraph("Ingrediente", subtitulo));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph("Cantidad Restante", subtitulo));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);

            // Datos del inventario
            for (int i = 0; i < cocina.ingredientesDisponibles.size(); i++) {
                tabla.addCell(cocina.ingredientesDisponibles.get(i));
                tabla.addCell(String.valueOf(cocina.cantidadesDisponibles.get(i)));
            }
            documento.add(tabla);

            documento.add(Chunk.NEWLINE);
            Paragraph cierre = new Paragraph("¡Fin del día exitoso! Gracias por tu trabajo ", 
                new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.DARK_GRAY));
            cierre.setAlignment(Element.ALIGN_CENTER);
            documento.add(cierre);

            documento.close();
            System.out.println("\n Inventario diario generado: " + nombreArchivo);
            

            
            System.out.println("\n¡INVENTARIO DIARIO GENERADO CON EXITO!"+ nombreArchivo);
            System.out.println("Guardado en: " + nombreArchivo);
            System.out.println("Total de movimientos registrados hoy: " + RegistroDiario.getMovimientos().size());

        } catch (Exception e) {
            System.out.println("Error generando reporte diario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}