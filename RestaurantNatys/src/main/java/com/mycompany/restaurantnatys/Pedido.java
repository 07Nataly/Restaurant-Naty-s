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

    
  class Pedido implements Imprimible {
    private int idPedido;
    private List<ItemPedido> listaItems; // Colección de productos del pedido
    private double total;
    private String estado;

    public Pedido(int idPedido) {
        this.idPedido = idPedido;
        this.listaItems = new ArrayList<>();
        this.estado = "En cocina";
    }

    public int getIdPedido() { return idPedido; }
    public List<ItemPedido> getListaItems() { return listaItems; }
    public double getTotal() { return total; }
    public String getEstado() { return estado; }

    public void agregarItem(ItemPedido item) { listaItems.add(item); }

    // Calcular el total
    public void calcularTotal() {
        total = 0;
        for (ItemPedido i : listaItems)
            total += i.calcularSubtotal();
    }

    // Polimorfismo por sobrecarga
    // Descuento 
    public void calcularTotal(double descuento) {
        calcularTotal();
        if (descuento > 0 && descuento <= 1) {
            total = total - (total * descuento);
        }
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    //Interfaz Imprimible
    @Override
    public void impDetalle() {
        System.out.println("Pedido #" + idPedido + " | Estado: " + estado + " | Total: $" + total);
    }
}