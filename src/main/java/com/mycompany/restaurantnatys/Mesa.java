/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */
class Mesa {
    private int numeroMesa;
    private String estado;
    private Pedido pedidoActual; // Asociacion

    //Constructor
    public Mesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        this.estado = "Libre";
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void asignarPedido(Pedido p) {  //Asociacion
        this.pedidoActual = p;
        this.estado = "Ocupada";
    }

    public Pedido getPedidoActual() {
        return pedidoActual;
    }
}