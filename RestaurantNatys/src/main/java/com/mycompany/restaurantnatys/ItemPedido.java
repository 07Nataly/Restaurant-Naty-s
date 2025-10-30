/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */
class ItemPedido {
    private String nomProducto;
    private int cantidad;
    private double precioUni;

    public ItemPedido(String nomProducto, int cantidad, double precioUni) {
        this.nomProducto = nomProducto;
        this.cantidad = cantidad;
        this.precioUni = precioUni;
    }

    public String getNomProducto() {
        return nomProducto;
    }
    public int getCantidad() { 
        return cantidad;
    }
    public double getPrecioUni() {
        return precioUni;
    }
    public double calcularSubtotal() {
        return cantidad * precioUni;
    }
}
  