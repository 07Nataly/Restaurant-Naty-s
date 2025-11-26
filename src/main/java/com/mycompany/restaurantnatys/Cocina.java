/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cocina {
    private List<Pedido> pedPendientes;
    public ArrayList<String> ingredientesDisponibles;
    public ArrayList<Integer> cantidadesDisponibles;
    public VentanaAdministrador ventanaAdmin;

    public Cocina() {
        pedPendientes = new ArrayList<>();
        ingredientesDisponibles = new ArrayList<>();
        cantidadesDisponibles = new ArrayList<>();
    }

    // Inicializa el inventario con todos los proveedores del menú
    public void inicializarInventarioDesdeMenu(Menu menu) {
        for (Proveedor prov : menu.getProveedores()) {
            recibirIngredientes(prov);
        }
        System.out.println("\nInventario inicial cargado desde los proveedores.\n");
    }

    //  Recibir pedido
    public void reciPedido(Pedido p) {
        pedPendientes.add(p);
        System.out.println("Pedido #" + p.getIdPedido() + " recibido en cocina.");
    }

    // Marcar pedido listo y descontar ingredientes
    public void marPedList(int idPedido, Menu menu) {
        for (Pedido p : pedPendientes) {
            if (p.getIdPedido() == idPedido) {
                System.out.println("\n¡Pedido #" + idPedido + " preparado y servido exitosamente!");               
                usarIngredientesPedido(p, menu);
                pedPendientes.remove(p);
                break;
            }
        }
    }

    //  Mostrar pedidos pendientes
    public void mostPediPends() {
        if (pedPendientes.isEmpty()) {
            System.out.println("No hay pedidos pendientes.");
        } else {
            for (Pedido p : pedPendientes) {
                System.out.println("Pedido #" + p.getIdPedido() + " - Estado: " + p.getEstado());
            }
        }
    }

    //  Recibir ingredientes de un proveedor
    public void recibirIngredientes(Proveedor proveedor) {
        ArrayList<String> ingProv = proveedor.getIngredientes();
        ArrayList<Integer> cantProv = proveedor.getCantidades();

        for (int i = 0; i < ingProv.size(); i++) {
            String ingrediente = ingProv.get(i);
            int cantidad = cantProv.get(i);

            int index = ingredientesDisponibles.indexOf(ingrediente);
            if (index != -1) {
                cantidadesDisponibles.set(index, cantidadesDisponibles.get(index) + cantidad);
            } else {
                ingredientesDisponibles.add(ingrediente);
                cantidadesDisponibles.add(cantidad);
            }
        }
    }

    //  Mostrar inventario (solo el administrador puede ver)
    public void mostrarInventario() {
        System.out.println("\n=== Inventario de la Cocina ===");
        if (ingredientesDisponibles.isEmpty()) {
            System.out.println("Inventario vacío. Debe comprar ingredientes a los proveedores.");
        } else {
            for (int i = 0; i < ingredientesDisponibles.size(); i++) {
                System.out.println("- " + ingredientesDisponibles.get(i) + ": " + cantidadesDisponibles.get(i) + " unidades");
            }
        }
        System.out.println("===============================\n");
    }

    //  Usar ingredientes del inventario
    public boolean usarIngrediente(String ingrediente, int cantidad) {
        int index = ingredientesDisponibles.indexOf(ingrediente);
        if (index != -1 && cantidadesDisponibles.get(index) >= cantidad) {
            cantidadesDisponibles.set(index, cantidadesDisponibles.get(index) - cantidad);
            return true;
        } else {
            System.out.println("No hay suficiente " + ingrediente + " en inventario.");
            return false;
        }
    }

    //  Preparar pedido (solo si hay ingredientes)
    public void usarIngredientesPedido(Pedido pedido, Menu menu) {
        System.out.println("\nPreparando pedido #" + pedido.getIdPedido() + "...");

        for (ItemPedido item : pedido.getListaItems()) {
            String nombre = item.getNomProducto().toLowerCase();
            int cant = item.getCantidad();

            if (nombre.contains("hamburguesa")) {
                usarIngrediente("Pan de hamburguesa", cant);
                usarIngrediente("Carne de res", cant);
                usarIngrediente("Tocino",cant);
                usarIngrediente("Queso", cant);
            }
            else if (nombre.contains("perro")) {
                usarIngrediente("Pan perro caliente", cant);
                usarIngrediente("Salchicha", cant);
            }
            else if (nombre.contains("papas")) {
                usarIngrediente("Papas a la francesa", cant);
            }
            else if (nombre.contains("gaseosa")) {
                String sabor = nombre.contains("cola") ? "Gaseosa cola" :
                              nombre.contains("manzana") ? "Gaseosa manzana" :
                              nombre.contains("naranja") ? "Gaseosa naranja" : "Gaseosa cola";
                usarIngrediente(sabor, cant);
            }
            else if (nombre.contains("nuggets") || nombre.contains("pollo")) {
                usarIngrediente("Pollo fresco", cant);
            }
        }

        // Actualiza la ventana del admin automáticamente
        java.awt.EventQueue.invokeLater(() -> {
            for (java.awt.Window w : java.awt.Window.getWindows()) {
                if (w instanceof VentanaAdministrador) {
                    ((VentanaAdministrador) w).actualizarTablaInventario();
                    ((VentanaAdministrador) w).cargarMovimientos();
                }
            }
        });
        

        System.out.println("\nInventario actualizado despues del pedido:");
        mostrarInventario();
    }

    //  Comprar ingredientes (solo el admin)
    public void comprarIngredientes(Menu menu) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nDesea comprar ingredientes a un proveedor? (s/n): ");
        String opcion = sc.nextLine();

        if (opcion.equalsIgnoreCase("s")) {
            menu.mostrarProveedores();
            System.out.print("Ingrese el numero del proveedor a contactar (1-" + menu.getProveedores().size() + "): ");
            int num = sc.nextInt();
            sc.nextLine();
            if (num >= 1 && num <= menu.getProveedores().size()) {
                recibirIngredientes(menu.getProveedores().get(num - 1));
                RegistroDiario.agregarMovimiento("ADMIN COMPRA - Proveedor: " + menu.getProveedores().get(num - 1).getNombre() + 
                    " | Ingredientes recibidos");
                System.out.println("Ingredientes comprados correctamente.");
            } else {
                System.out.println("Numero invalido. No se realizo la compra.");
            }
        } else {
            System.out.println("No se compraron ingredientes.");
        }
    }

    //Verifica si hay inventario cargado
    public boolean hayInventario() {
        return !ingredientesDisponibles.isEmpty();
    }
}
