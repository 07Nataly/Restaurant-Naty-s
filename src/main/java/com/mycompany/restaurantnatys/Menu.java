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
import java.util.ArrayList;
import java.util.List;

class Menu {
    private List<ItemPedido> productos;
    private List<Proveedor> proveedores;

    public List<ItemPedido> getProductos() {
        return productos;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public Menu() {
        // Crear los proveedores (cada uno con sus ingredientes)
        proveedores = new ArrayList<>();

        Proveedor provCarne = new Proveedor("Carnes Don Pedro", "1001");
        provCarne.agregarIngrediente("Carne de res", 50);
        provCarne.agregarIngrediente("Tocino", 30);
        provCarne.agregarIngrediente("Salchicha",30);
       
       

        Proveedor provPan = new Proveedor("Panaderia La Delicia", "1002");
        provPan.agregarIngrediente("Pan de hamburguesa", 100);
        provPan.agregarIngrediente("Pan perro caliente", 80);

        Proveedor provPapas = new Proveedor("Papas San Jorge", "1003");
        provPapas.agregarIngrediente("Papas criollas", 120);

        Proveedor provGaseosa = new Proveedor("Bebidas El Refrescón", "1004");
        provGaseosa.agregarIngrediente("Gaseosa cola", 60);
        provGaseosa.agregarIngrediente("Gaseosa naranja", 40);

        Proveedor provPollo = new Proveedor("Avícola Santa Fe", "1005");
        provPollo.agregarIngrediente("Pollo fresco", 70);
        provPollo.agregarIngrediente("Pechugas trozadas", 50);
        
        Proveedor provQueso = new Proveedor("Proveedor de Queso","1005");
        provQueso.agregarIngrediente("Queso", 30); //AGREGADO



        // Agregamos los proveedores a la lista
        proveedores.add(provCarne);
        proveedores.add(provPan);
        proveedores.add(provPapas);
        proveedores.add(provGaseosa);
        proveedores.add(provPollo);
        proveedores.add(provQueso);

        // Crear productos del menú y asociarlos mentalmente a los proveedores
        productos = new ArrayList<>();
        productos.add(new ItemPedido("Hamburguesa Clasica (Carne de res, Pan, Queso)", 1, 12000)); // provCarne + provPan
        productos.add(new ItemPedido("Perro Caliente", 1, 10000));                                  // provPan
        productos.add(new ItemPedido("Papas Fritas (Papas criollas)", 1, 8000));                   // provPapas
        productos.add(new ItemPedido("Gaseosa (cola, naranja o manzana)", 1, 4000));                       // provGaseosa
        productos.add(new ItemPedido("Nuggets de Pollo (Pollo fresco)", 1, 9000));                 // provPollo
    }

    // Mostrar menú
    public void mostMenu() {
        System.out.println("\n--- MENU DE COMIDA RAPIDA ---");
        for (int i = 0; i < productos.size(); i++) {
            ItemPedido p = productos.get(i);
            System.out.println((i + 1) + ". " + p.getNomProducto() + " - " + RestaurantNatys.formatoPrecio(p.getPrecioUni()));
        }
    }

    // Mostrar proveedores
    public void mostrarProveedores() {
        System.out.println("\nLista de proveedores disponibles:");
        for (Proveedor p : proveedores) {
            p.mostrarInfo();
        }
    }

   public ItemPedido selecProducto(int opcion, int cantidad) {
    if (opcion > 0 && opcion <= productos.size()) {
        ItemPedido base = productos.get(opcion - 1);
        
        // 🔹 Si el producto es gaseosa, preguntar tipo
        if (base.getNomProducto().toLowerCase().contains("gaseosa")) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Seleccione tipo de gaseosa:");
            System.out.println("1. Gaseosa cola");
            System.out.println("2. Gaseosa naranja");
            System.out.println("3. Gaseosa manzana");
            int tipo = sc.nextInt();
            sc.nextLine();
            String nombreGas = switch (tipo) {
                case 1 -> "Gaseosa cola";
                case 2 -> "Gaseosa naranja";
                case 3 -> "Gaseosa manzana";
                default -> "Gaseosa cola";
            };
            return new ItemPedido(nombreGas, cantidad, base.getPrecioUni());
        }

        return new ItemPedido(base.getNomProducto(), cantidad, base.getPrecioUni());
    }
    return null;
}

}
