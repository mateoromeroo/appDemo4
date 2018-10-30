package pe.edu.unmsm.software.aplicacionpasteleria.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Producto {
    private int id;
    private String nombre_producto;
    private String descripcion;
    private String dato;
    private Bitmap imagen;
    private int stock;
    private double precio;
    private int calorias;
    private double grasas;
    private double carbohidratos;
    private double proteinas;
    private String nombreCategoria;
    private int cantidad;

    public Producto() {

    }

    public Producto(int id, String nombre_producto, String descripcion, Bitmap imagen, int stock, double precio, int calorias, double grasas, double carbohidratos, double proteinas, String nombreCategoria) {
        this.id = id;
        this.nombre_producto = nombre_producto;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.stock = stock;
        this.precio = precio;
        this.calorias = calorias;
        this.grasas = grasas;
        this.carbohidratos = carbohidratos;
        this.proteinas = proteinas;
        this.nombreCategoria = nombreCategoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;

        try {
            byte[] byteCode= Base64.decode(dato,Base64.DEFAULT);
            //this.imagen= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);

            int alto=100;//alto en pixeles
            int ancho=150;//ancho en pixeles

            Bitmap foto= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);
            this.imagen=Bitmap.createScaledBitmap(foto,alto,ancho,true);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    public double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public double getProteinas() {
        return proteinas;
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
