/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */

import java.util.Scanner;

public class RestaurantNatys {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;

        while (true) {
            System.out.println("\n=== BIENVENIDO A RESTAURANT NATY'S ===");
            System.out.println("1. Realizar pedido");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opcion: ");

            // Validar que ingrese un número
            if (!sc.hasNextInt()) {
                System.out.println("️ Error: solo puede ingresar numeros (1 o 2). Intente nuevamente.\n");
                sc.nextLine(); // limpiar entrada
                continue; // volver al bucle
            }

            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            if (opcion == 1) {
                // Datos del cliente
                System.out.print("Ingrese su nombre: ");
                String nombre = sc.nextLine();
                System.out.print("Ingrese su documento: ");
                String documento = sc.nextLine();

                Cliente cliente = new Cliente(nombre, documento);
                Menu menu = new Menu();
                Mesa mesa = new Mesa(1);
                Cocina cocina = new Cocina();

                // Asociación
                cliente.mostrarInfo();
                Pedido pedido = cliente.crearPedido(menu);

                // Agregación
                mesa.asignarPedido(pedido);

                // Composición
                cocina.reciPedido(pedido);
                cocina.mostPediPends();
                cocina.marPedList(pedido.getIdPedido());

                // Validar método de pago
                String metodo;
                do {
                    System.out.print("\nMétodo de pago (Efectivo / Tarjeta): ");
                    metodo = sc.nextLine().trim();

                    if (!metodo.equalsIgnoreCase("Efectivo") && !metodo.equalsIgnoreCase("Tarjeta")) {
                        System.out.println("Error: metodo invalido. Escriba 'Efectivo' o 'Tarjeta'.");
                    }
                } while (!metodo.equalsIgnoreCase("Efectivo") && !metodo.equalsIgnoreCase("Tarjeta"));

                // Crear y procesar el pago
                Pago pago = new Pago(1, pedido.getTotal(), metodo);
                pago.generarFactura(pedido, mesa, cliente);
                pago.guardarEnArchivo(pedido, cliente); //Se guarda en archivo .txt

                System.out.println("\n Pedido completado correctamente.");
                System.out.println("====================================\n");

            } else if (opcion == 2) {
                System.out.println("Gracias por visitar NATY’S. ¡Hasta pronto!");
                break; // salir del bucle
            } else {
                System.out.println("Error: opcion invalida. Solo puede elegir 1 o 2.\n");
            }
        }
    }
}

