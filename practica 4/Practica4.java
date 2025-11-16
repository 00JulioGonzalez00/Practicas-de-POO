import java.util.ArrayList;
import java.util.List;

class VehiculoBaseJ {
    protected String marca;
    protected String modelo;
    protected int año;

    public VehiculoBaseJ(String marca, String modelo, int año) {
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
    }

    public String descripcion() {
        return "Vehículo genérico";
    }

    public double calcularCostoSeguro() {
        return 1000.0;
    }

    public String tipoCombustible() {
        return "Desconocido";
    }
}


class AutoG extends VehiculoBaseJ {
    private int puertas;

    public AutoG(String marca, String modelo, int año, int puertas) {
        super(marca, modelo, año);
        this.puertas = puertas;
    }

    @Override
    public String descripcion() {
        return "Auto " + marca + " " + modelo + " de " + puertas + " puertas";
    }

    @Override
    public double calcularCostoSeguro() {
        return super.calcularCostoSeguro() + 1000;
    }

    @Override
    public String tipoCombustible() {
        return "Gasolina";
    }
}


class MotocicletaG extends VehiculoBaseJ {
    private int cilindrada;

    public MotocicletaG(String marca, String modelo, int año, int cilindrada) {
        super(marca, modelo, año);
        this.cilindrada = cilindrada;
    }

    @Override
    public String descripcion() {
        return "Motocicleta " + marca + " " + modelo + " de " + cilindrada + " cc";
    }

    @Override
    public double calcularCostoSeguro() {
        return super.calcularCostoSeguro() + 300;
    }

    @Override
    public String tipoCombustible() {
        return "Gasolina";
    }
}


class CamionG extends VehiculoBaseJ {
    private double capacidadToneladas;

    public CamionG(String marca, String modelo, int año, double capacidadToneladas) {
        super(marca, modelo, año);
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public String descripcion() {
        return "Camión " + marca + " " + modelo + " con capacidad de " + capacidadToneladas + " toneladas";
    }

    @Override
    public double calcularCostoSeguro() {
        return super.calcularCostoSeguro() + 900;
    }

    @Override
    public String tipoCombustible() {
        return "Diesel";
    }
}





public class Practica4 {
    public static void main(String[] args) {
        Concesionaria7567 c = new Concesionaria7567();

        VehiculoBaseJ a1 = new AutoG("Chevrolet", "Cavalier", 2020, 4);
        VehiculoBaseJ m1 = new MotocicletaG("Italika", "250Z", 2017, 321);
        VehiculoBaseJ cam1 = new CamionG("Kenworth", "T680", 2025, 5.0);

        c.agregarVehiculo(a1);
        c.agregarVehiculo(m1);
        c.agregarVehiculo(cam1);

        c.mostrarInventario();
    }
}

