/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class ReportePedidosDiarios {

    public static void generarReportePedidosDiarios() {
        
        //crear nueva carpeta
        try {
            File carpeta = new File("Pedidos_Diarios");
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
            
            //datos del archivo
            String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String hora = new SimpleDateFormat("HH-mm-ss").format(new Date());
            String nombreArchivo = "Pedidos_Diarios/Pedidos_del_" + fecha + "_a_las_" + hora + ".pdf";
            
            //crear pdf
            Document documento = new Document(PageSize.A4, 50, 50, 90, 70);
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            //tipo de fuente
            Font titulo = new Font(Font.FontFamily.HELVETICA, 26, Font.BOLD, new BaseColor(139, 0, 0));
            Font subtitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font normal = new Font(Font.FontFamily.HELVETICA, 11);
            Font totalFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.RED);

            // Logo (opcional)
            try {
                Image logo = Image.getInstance(ReportePedidosDiarios.class.getResource("/com/mycompany/restaurantnatys/logo.png"));
                logo.scaleAbsolute(100f, 100f);
                logo.setAbsolutePosition(450, 730);
                documento.add(logo);
            } catch (Exception ignored) {}

            // Títulos con alineación corregida
            Paragraph p1 = new Paragraph("RESTAURANT NATY'S", titulo);
            p1.setAlignment(Element.ALIGN_CENTER);
            documento.add(p1);

            Paragraph p2 = new Paragraph("REPORTE DE PEDIDOS DEL DÍA", subtitulo);
            p2.setAlignment(Element.ALIGN_CENTER);
            documento.add(p2);

            //datos del pdf
            documento.add(new Paragraph("Fecha: " + new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(new Date()), normal));
            documento.add(new Paragraph("Generado: " + new SimpleDateFormat("HH:mm:ss").format(new Date()), normal));
            documento.add(Chunk.NEWLINE);

            
            if (RestaurantNatys.pedidosDelDia == null || RestaurantNatys.pedidosDelDia.isEmpty()) {
                Paragraph vacio = new Paragraph("No se registraron pedidos hoy.", normal);
                vacio.setAlignment(Element.ALIGN_CENTER);
                documento.add(vacio);
            } else {
                PdfPTable tabla = new PdfPTable(5);
                tabla.setWidthPercentage(100);
                tabla.setWidths(new float[]{18, 20, 35, 12, 15});

                BaseColor headerColor = new BaseColor(255, 153, 153);
                String[] headers = {"Cliente", "Documento", "Productos", "Cant.", "Total"};
                for (String h : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(h, subtitulo));
                    cell.setBackgroundColor(headerColor);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    tabla.addCell(cell);
                }

                double granTotal = 0;
                for (Pedido p : RestaurantNatys.pedidosDelDia) {
                    Cliente c = p.getCliente();
                    StringBuilder items = new StringBuilder();
                    int totalCant = 0;

                    for (ItemPedido ip : p.getListaItems()) {
                        items.append(ip.getNomProducto()).append(" x").append(ip.getCantidad()).append("\n");
                        totalCant += ip.getCantidad();
                    }

                    tabla.addCell(new Phrase(c.getNombre(), normal));
                    tabla.addCell(new Phrase(c.getDocumento(), normal));
                    tabla.addCell(new Phrase(items.toString(), normal));
                    
                    PdfPCell cellCant = new PdfPCell(new Phrase(String.valueOf(totalCant), normal));
                    cellCant.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(cellCant);

                    String totalTexto = RestaurantNatys.formatoPrecio(p.getTotal());
                    PdfPCell cellTotal = new PdfPCell(new Phrase(totalTexto, normal));
                    cellTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tabla.addCell(cellTotal);

                    granTotal += p.getTotal();
                }
                documento.add(tabla);

                documento.add(Chunk.NEWLINE);
                Paragraph totalDia = new Paragraph("TOTAL VENTAS DEL DÍA: " + RestaurantNatys.formatoPrecio(granTotal), totalFont);
                totalDia.setAlignment(Element.ALIGN_RIGHT);
                documento.add(totalDia);
            }

            documento.add(Chunk.NEWLINE);
            Paragraph cierre = new Paragraph("¡Gracias por un gran día en Restaurant Naty's!", normal);
            cierre.setAlignment(Element.ALIGN_CENTER);
            documento.add(cierre);

            documento.close();

            JOptionPane.showMessageDialog(null, 
                "¡Reporte generado con éxito!\nUbicación: " + nombreArchivo,
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}