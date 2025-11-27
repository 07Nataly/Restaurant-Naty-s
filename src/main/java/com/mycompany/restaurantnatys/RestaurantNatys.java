/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.restaurantnatys;

/**
 *
 * @author USUARIO
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;


public class RestaurantNatys {
    
    //guarda pedidos del dia
    public static ArrayList<Pedido> pedidosDelDia = new ArrayList<>();
    //instancias 
    public static Menu menu = new Menu();
    public static Cocina cocina = new Cocina();
    
    //iniciar inventario
    static {
        cocina.inicializarInventarioDesdeMenu(menu);
    }
    
    // Método para precios
    public static String formatoPrecio(double precio) {
        return "$" + String.format("%,.0f", precio).replace(",", ".");
    }

    public static void main(String[] args) {
        
        //ventana grafica
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaInicio().setVisible(true);
            }
        });

        
        
        
        Scanner sc = new Scanner(System.in);
        int opcion = 0; //  inicializada en 0
        
        //Se guarda registro comienzo dia
        RegistroDiario.agregarMovimiento("=== INICIO DEL DÍA EN RESTAURANT NATY'S ===");

        do {
            System.out.println("==================================");
            System.out.println("BIENVENIDO A RESTAURANT NATYS");
            System.out.println("==================================");
            System.out.println("1. Hacer pedido como cliente");
            System.out.println("2. Menu administrador");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opcion: ");

            if (!sc.hasNextInt()) {
                System.out.println("Error: solo puede ingresar numeros. Intente de nuevo.\n");
                sc.nextLine();
                continue;
            }

            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("\nIngrese su nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese su documento: ");
                    String documento = sc.nextLine();

                    Cliente cliente = new Cliente(nombre, documento);
                    Mesa mesa = new Mesa(1);

                    // Crear pedido
                    Pedido pedido = cliente.crearPedido(menu);

                    // Asociaciones
                    mesa.asignarPedido(pedido);
                    cocina.reciPedido(pedido);
                    cocina.marPedList(pedido.getIdPedido(), menu);

                    // Metodo de pago
                    String metodo;
                    do {
                        System.out.print("\nMetodo de pago (Efectivo / Tarjeta): ");
                        metodo = sc.nextLine().trim();
                        if (!metodo.equalsIgnoreCase("Efectivo") && !metodo.equalsIgnoreCase("Tarjeta")) {
                            System.out.println("Metodo invalido. Intente de nuevo.");
                        }
                    } while (!metodo.equalsIgnoreCase("Efectivo") && !metodo.equalsIgnoreCase("Tarjeta"));

                    // Procesar pago y generar factura
                    Pago pago = new Pago(1, pedido.getTotal(), metodo);
                    pago.generarFactura(pedido, mesa, cliente);
                    pago.guardarEnArchivo(pedido, cliente);
                    
                    RegistroDiario.agregarMovimiento("PEDIDO COMPLETADO - Cliente: " + cliente.getNombre() + 
                        " | Total: " + RestaurantNatys.formatoPrecio(pedido.getTotal()) + 
                        " | Items: " + pedido.getListaItems().size());

                    System.out.println("\nPedido completado correctamente.\n");
                    break;

                case 2:
                    System.out.print("\nIngrese la clave de empleado: ");
                    String clave = sc.nextLine();

                    if (clave.equals("admin123")) {
                        int opcAdmin = 0;
                        do {
                            System.out.println("\n=== MENU ADMINISTRADOR ===");
                            System.out.println("1. Ver inventario");
                            System.out.println("2. Comprar ingredientes");
                            System.out.println("3. Ver proveedores");
                            System.out.println("4. Volver al menu principal");
                            System.out.print("Seleccione una opcion: ");

                            if (!sc.hasNextInt()) {
                                System.out.println("Error: debe ingresar un numero.\n");
                                sc.nextLine();
                                continue;
                            }

                            opcAdmin = sc.nextInt();
                            sc.nextLine();

                            switch (opcAdmin) {
                                case 1:
                                    cocina.mostrarInventario();
                                    break;
                                case 2:
                                    cocina.comprarIngredientes(menu);
                                    break;
                                case 3:
                                    menu.mostrarProveedores();
                                    break;
                                case 4:
                                    System.out.println("Volviendo al menu principal...\n");
                                    break;
                                default:
                                    System.out.println("Opcion invalida.\n");
                                    break;
                            }
                        } while (opcAdmin != 4);
                    } else {
                        System.out.println("Clave incorrecta. Acceso denegado.\n");
                    }
                    break;

                case 3:
                    
                    // Generar inventario diario
                    InventarioDiario.generarReporteDiario(cocina);

                    System.out.println("\nGracias por visitar Restaurant Natys. Vuelva pronto!");
                    break;
                    

                default:
                    System.out.println("Opcion invalida. Intente de nuevo.\n");
                    break;
            }

        } while (opcion != 3);

        sc.close();
    }
}
