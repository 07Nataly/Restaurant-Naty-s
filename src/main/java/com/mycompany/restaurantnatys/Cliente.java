/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */


import java.util.Scanner;

class Cliente extends Persona {
    private static int contadorPedidos = 1;

    public Cliente(String nombre, String documento) {
        super(nombre, documento);
    }

    public Pedido crearPedido(Menu menu) {
        Pedido pedido = new Pedido(contadorPedidos++, this);
        Scanner sc = new Scanner(System.in);
        String continuar;

        // Realiza el pedido
        do {
            menu.mostMenu();

            int opcion;
            
            while (true) {
                System.out.print("Seleccione una opción del menú: ");

                
                while (!sc.hasNextInt()) {
                    System.out.println("⚠️ Error: debe ingresar un número válido.");
                    System.out.print("Seleccione una opción del menú: ");
                    sc.next(); // limpia entrada 
                }

                opcion = sc.nextInt();
                sc.nextLine(); // limpia buffer

                if (opcion > 0 && opcion <= menu.getProductos().size()) {
                    break; 
                } else {
                    System.out.println("Error: esa opcion no existe. Elija un número entre 1 y " + menu.getProductos().size() + ".");
                }
            }

          
            System.out.print("Ingrese la cantidad: ");
            while (!sc.hasNextInt()) {
                System.out.println("Error: debe ingresar un numero valido para la cantidad.");
                System.out.print("Ingrese la cantidad: ");
                sc.next();
            }
            int cantidad = sc.nextInt();
            sc.nextLine(); // limpia buffer

            
            ItemPedido item = menu.selecProducto(opcion, cantidad);
            pedido.agregarItem(item);

            do {
                System.out.print("¿Desea agregar otro producto? (s/n): ");
                continuar = sc.nextLine();

                if (!continuar.equalsIgnoreCase("s") && !continuar.equalsIgnoreCase("n")) {
                    System.out.println("Error: opcion no valida. Ingrese solo 's' o 'n'.");
                }
            } while (!continuar.equalsIgnoreCase("s") && !continuar.equalsIgnoreCase("n"));

        } while (continuar.equalsIgnoreCase("s"));

        pedido.calcularTotal();
        
        return pedido;
        
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Cliente: " + getNombre() + " | Documento: " + getDocumento());
    }
}
