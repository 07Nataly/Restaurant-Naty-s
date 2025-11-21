/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */

//Clase para registro
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistroDiario {
    private static final List<String> movimientos = new ArrayList<>();
    private static Date fechaDia = new Date();

    public static void agregarMovimiento(String descripcion) {
        movimientos.add("[" + new Date() + "] " + descripcion);
    }

    public static List<String> getMovimientos() {
        return movimientos;
    }

    public static Date getFechaDia() {
        return fechaDia;
    }

    public static void limpiarDia() {
        movimientos.clear();
        fechaDia = new Date();
    }
}