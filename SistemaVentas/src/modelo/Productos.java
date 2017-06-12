
package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Productos {
    public int id;
    public String nombre;
    public String marca;
    public String categoria;
    public String costo;
    public String precio1_10;
    public String precio10_20;
    public String precio20_;
    public String cantidad;
    public FileInputStream  imagen;
    public File fileImagen;
    public InputStream inputImagen;
    
    public Productos() {
        id = 0;
        nombre = "";
        marca = "";
        categoria = "";
        costo = "";
        precio1_10 = "";
        precio10_20 = "";
        precio20_ = "";
        cantidad = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getPrecio1_10() {
        return precio1_10;
    }

    public void setPrecio1_10(String precio1_10) {
        this.precio1_10 = precio1_10;
    }

    public String getPrecio10_20() {
        return precio10_20;
    }

    public void setPrecio10_20(String precio10_20) {
        this.precio10_20 = precio10_20;
    }

    public String getPrecio20_() {
        return precio20_;
    }

    public void setPrecio20_(String precio20_) {
        this.precio20_ = precio20_;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public FileInputStream getImagen() {
        return imagen;
    }

    public void setImagen(FileInputStream imagen) {
        this.imagen = imagen;
    }

    public File getFileImagen() {
        return fileImagen;
    }

    public void setFileImagen(File fileImagen) {
        this.fileImagen = fileImagen;
    }

    public InputStream getInputImagen() {
        return inputImagen;
    }

    public void setInputImagen(InputStream inputImagen) {
        this.inputImagen = inputImagen;
    }

    
    
    
}
