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

class Cocina {
    private List<Pedido> pedPendientes;

    public Cocina() {
        pedPendientes = new ArrayList<>();
    }

    //Recibir pedido
    public void reciPedido(Pedido p) {
        pedPendientes.add(p);
        System.out.println("Pedido #" + p.getIdPedido() + " recibido en cocina.");
    }

    //Marcar pedidos listos
    public void marPedList(int idPedido) {
        for (Pedido p : pedPendientes) {
            if (p.getIdPedido() == idPedido) {
                p.cambiarEstado("Listo");
                System.out.println("Pedido #" + idPedido + " esta listo para entregar.");
            }
        }
    }
    //Mostrar pedidos pendientes
    public void mostPediPends() {
        for (Pedido p : pedPendientes) {
            System.out.println("Pedido #" + p.getIdPedido() + " - Estado: " + p.getEstado());
        }
    }
}