import java.util.ArrayList;
import java.util.List;

class Concesionaria7567 {
    private List<VehiculoBaseJ> vehiculos;

    public Concesionaria7567() {
        vehiculos = new ArrayList<>();
    }

    public void agregarVehiculo(VehiculoBaseJ v) {
        vehiculos.add(v);
    }

    public void mostrarInventario() {
        for (VehiculoBaseJ v : vehiculos) {
            System.out.println(v.descripcion() + " | Seguro: $" + v.calcularCostoSeguro());
        }
    }
}