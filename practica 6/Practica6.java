import java.util.List;
import java.util.ArrayList;

interface Bonificable15 {
    double calcularBonificacion();
}


interface Evaluable05 {
    String evaluarDesempeno();
}


interface Promovible7567 {
    boolean puedePromocionarse();
}

class GerenteGonzalez extends EmpleadoJG implements Bonificable15, Evaluable05, Promovible7567 {
    public GerenteGonzalez(String nombre, int edad, double salarioBase) {
        super(nombre, edad, salarioBase);
    }

    @Override
    public void trabajar() {
        System.out.println(nombre + " se encarga de Recursos Humanos.");
    }

    @Override
    public double calcularBonificacion() {
        return salarioBase * 0.4;
    }

    @Override
    public String evaluarDesempeno() {
        return "Excelente";
    }

    @Override
    public boolean puedePromocionarse() {
        return edad < 30;
    }
}


class DesarrolladorGonzalez extends EmpleadoJG implements Bonificable15, Evaluable05 {
    private int lineasCodigoDiarias;

    public DesarrolladorGonzalez(String nombre, int edad, double salarioBase, int lineasCodigoDiarias) {
        super(nombre, edad, salarioBase);
        this.lineasCodigoDiarias = lineasCodigoDiarias;
    }

    @Override
    public void trabajar() {
        System.out.println(nombre + " se encarga de la contabilidad.");
    }

    @Override
    public double calcularBonificacion() {
        return salarioBase * 0.1 + lineasCodigoDiarias * 0.5;
    }

    @Override
    public String evaluarDesempeno() {
        if(lineasCodigoDiarias > 100) return "Muy bueno";
        return "Bueno";
    }
}


class VendedorGonzalez extends EmpleadoJG implements Bonificable15, Promovible7567 {
    private double ventasMensuales;

    public VendedorGonzalez(String nombre, int edad, double salarioBase, double ventasMensuales) {
        super(nombre, edad, salarioBase);
        this.ventasMensuales = ventasMensuales;
    }

    @Override
    public void trabajar() {
        System.out.println(nombre + " se encarga de la programación.");
    }

    @Override
    public double calcularBonificacion() {
        return ventasMensuales * 0.1;
    }

    @Override
    public boolean puedePromocionarse() {
        return ventasMensuales > 3000;
    }
}

public class Practica6 {
    public static void main(String[] args) {
        EmpresaTIJ7567 empresa = new EmpresaTIJ7567();

        GerenteGonzalez g1 = new GerenteGonzalez("Julio", 20, 8000);
        DesarrolladorGonzalez d1 = new DesarrolladorGonzalez("Luis", 23, 4000, 120);
        VendedorGonzalez v1 = new VendedorGonzalez("Pedro", 35, 3000, 4000);

        empresa.agregarEmpleado(g1);
        empresa.agregarEmpleado(d1);
        empresa.agregarEmpleado(v1);

        empresa.mostrarEmpleados();
    }
}