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

class Proveedor extends Persona {
    private ArrayList<String> ingredientes;
    private ArrayList<Integer> cantidades;

    public Proveedor(String nombre, String documento) {
        super(nombre, documento);
        ingredientes = new ArrayList<>();
        cantidades = new ArrayList<>();
    }

    public void agregarIngrediente(String nombreIng, int cantidad) {
        ingredientes.add(nombreIng);
        cantidades.add(cantidad);
    } 

    @Override
    public void mostrarInfo() {
        System.out.println("\nProveedor: " + nombre + " | Documento: " + documento);
        for (int i = 0; i < ingredientes.size(); i++) {
            System.out.println(" - " + ingredientes.get(i) + ": " + cantidades.get(i) + " unidades");
        }
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public ArrayList<Integer> getCantidades() {
        return cantidades;
    }
}
