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

class Menu {
    private List<ItemPedido> productos;

    public List<ItemPedido> getProductos() {
        return productos;
    }

    public Menu() {
        productos = new ArrayList<>();
        productos.add(new ItemPedido("Hamburguesa Clasica", 0, 12000));
        productos.add(new ItemPedido("Perro Caliente", 0, 10000));
        productos.add(new ItemPedido("Papas Fritas", 0, 8000));
        productos.add(new ItemPedido("Gaseosa", 0, 4000));
        productos.add(new ItemPedido("Nuggets", 0, 9000));
    }
    //Muestra el menu
    public void mostMenu() {
        System.out.println("\n--- MENU DE COMIDA RAPIDA ---");
        for (int i = 0; i < productos.size(); i++) {
            ItemPedido p = productos.get(i);
            System.out.println((i + 1) + ". " + p.getNomProducto() + " - $" + p.getPrecioUni());
        }
    }

    //Genera el producto del menu
    public ItemPedido selecProducto(int opcion, int cantidad) {
        if (opcion > 0 && opcion <= productos.size()) {
            ItemPedido base = productos.get(opcion - 1);
            return new ItemPedido(base.getNomProducto(), cantidad, base.getPrecioUni());
        }
        return null;
    }
}
