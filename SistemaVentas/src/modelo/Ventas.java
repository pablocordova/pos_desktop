
package modelo;

public class Ventas {
    // Variables de toda la venta
    public int idVenta; //idVenta compartido
    public int idCliente;
    public String nombre;
    public String fecha;
    public String hora;
    public String subtotal;
    public String igv;
    public String monto;
    public String gananciaTotal;
    // Variables del producto vendido
    // Esta es otra tabla pero tambien con el idVenta para saber a que venta corresponde
    public int idProducto;
    public String cantidadProducto;
    public String tipoPrecioProducto;
    public String precioProducto;
    public String gananciaProducto;
    
    public Ventas() {
        
        idVenta = 0;
        idCliente = 0;
        nombre = "";
        fecha = "";
        hora = "";
        subtotal = "";
        igv = "";
        monto = "";
        gananciaTotal = "";
        
        idProducto = 0;
        cantidadProducto = "";
        tipoPrecioProducto = "";
        precioProducto = "";
        gananciaProducto = "";
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getGananciaTotal() {
        return gananciaTotal;
    }

    public void setGananciaTotal(String gananciaTotal) {
        this.gananciaTotal = gananciaTotal;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(String cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getTipoPrecioProducto() {
        return tipoPrecioProducto;
    }

    public void setTipoPrecioProducto(String tipoPrecioProducto) {
        this.tipoPrecioProducto = tipoPrecioProducto;
    }

    public String getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(String precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getGananciaProducto() {
        return gananciaProducto;
    }

    public void setGananciaProducto(String gananciaProducto) {
        this.gananciaProducto = gananciaProducto;
    }
    
    
    
}
