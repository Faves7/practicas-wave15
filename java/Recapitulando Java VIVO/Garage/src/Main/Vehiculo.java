package Main;

public class Vehiculo {
    String modelo;
    String marca;
    Integer costo;


    public Vehiculo(String marca, String modelo, Integer costo) {
        this.modelo = modelo;
        this.marca = marca;
        this.costo = costo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public String toString() {
        return "Marca: " + this.marca + "Modelo: " + this.modelo + "Costo: " + this.costo;
    }

}
