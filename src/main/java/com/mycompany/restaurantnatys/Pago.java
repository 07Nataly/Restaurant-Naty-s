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
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

// Polimorfismo (por interfaz)
class Pago implements Imprimible {
    private int idPago;
    private double monto;
    private String metodoPago;
    private Date fecha;

    public Pago(int idPago, double monto, String metodoPago) {
        this.idPago = idPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.fecha = new Date(); // Fecha actual del pago
    }
    
    

    // Interfaz Imprimible
    @Override
    public void impDetalle() {
        System.out.println("Pago #" + idPago + " | Monto: $" + monto +
                           " | Método: " + metodoPago + " | Fecha: " + fecha);
    }

    // ✅ Generar factura PDF única por pedido
    public void generarFactura(Pedido pedido, Mesa mesa, Cliente cliente) {
        try {
            // Crear carpeta Facturas si no existe
            java.io.File carpeta = new java.io.File("Facturas");
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            // Crear nombre único para cada factura
            String nombreArchivo = "Facturas/Factura_" +
                    cliente.getNombre().replace(" ", "_") +
                    "_Pedido" + pedido.getIdPedido() + "_" +
                    System.currentTimeMillis() + ".pdf";

            // Crear documento PDF
            Document documento = new Document(PageSize.A4, 50, 50, 80, 70); // Margen superior
            PdfWriter writer = PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();
            
            
            
            //LOGO 
            try {
                Image logo = Image.getInstance(ReporteDiario.class.getResource("/com/mycompany/restaurantnatys/logo.png"));
                logo.scaleAbsolute(90f, 90f);
                logo.setAbsolutePosition(PageSize.A4.getWidth() - 140, PageSize.A4.getHeight() - 115);
                writer.getDirectContentUnder().addImage(logo);
            } catch (Exception e) {
                System.out.println("Logo no cargado: " + e.getMessage());
            }

            // Configurar fuentes
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, new BaseColor(139, 0, 0));
            Font subtituloFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font textoFont = new Font(Font.FontFamily.HELVETICA, 11);

            // Encabezado
            Paragraph titulo = new Paragraph("RESTAURANTE NATY'S", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);

            Paragraph subtitulo = new Paragraph("“Comida con pasión y sabor auténtico”", textoFont);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(subtitulo);
            documento.add(new Paragraph(" "));
            documento.add(new LineSeparator());

            // Datos del cliente y pedido
            documento.add(new Paragraph("\nFactura N°: " + idPago, subtituloFont));
            documento.add(new Paragraph("Cliente: " + cliente.getNombre() + " - " + cliente.getDocumento(), textoFont));
            documento.add(new Paragraph("Mesa: " + mesa.getNumeroMesa(), textoFont));
            documento.add(new Paragraph("Fecha: " + fecha, textoFont));
            documento.add(new Paragraph("Método de pago: " + metodoPago, textoFont));
            documento.add(new Paragraph(" "));

            // Tabla de productos
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.addCell("Producto");
            tabla.addCell("Cantidad");
            tabla.addCell("Precio Unitario");
            tabla.addCell("Subtotal");

            for (ItemPedido item : pedido.getListaItems()) {
                tabla.addCell(item.getNomProducto());
                tabla.addCell(String.valueOf(item.getCantidad()));
                tabla.addCell(RestaurantNatys.formatoPrecio(item.getPrecioUni()));
               tabla.addCell(RestaurantNatys.formatoPrecio(item.calcularSubtotal()));
            }

            documento.add(tabla);
           

            // Total final
            documento.add(new Paragraph("\nTOTAL A PAGAR: " + RestaurantNatys.formatoPrecio(pedido.getTotal()), subtituloFont));
            documento.add(new Paragraph("\n¡Gracias por visitarnos!️", textoFont));

            documento.close();
            System.out.println("\n Factura PDF generada correctamente: " + nombreArchivo);

        } catch (Exception e) {
            System.out.println(" Error al generar la factura: " + e.getMessage());
        }
    }

    // Registro en archivo de texto (persistencia)
    public void guardarEnArchivo(Pedido pedido, Cliente cliente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registro_pedidos.txt", true))) {
            writer.write("Pedido #" + pedido.getIdPedido() + " - Cliente: " + cliente.getNombre() +
                         " - Total: $" + pedido.getTotal() + " - Método: " + metodoPago + " - Fecha: " + fecha);
            writer.newLine();
            writer.write("---------------------------------------------------------");
            writer.newLine();
            System.out.println("Pedido registrado en archivo correctamente.");
        } catch (IOException e) {
            System.out.println(" Error al guardar el pedido: " + e.getMessage());
        }
    }
}
