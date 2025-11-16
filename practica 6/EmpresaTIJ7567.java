import java.util.ArrayList;
import java.util.List;

class EmpresaTIJ7567 {
    private List<EmpleadoJG> empleados;

    public EmpresaTIJ7567() {
        empleados = new ArrayList<>();
    }

    public void agregarEmpleado(EmpleadoJG e) {
        empleados.add(e);
    }

    public void mostrarEmpleados() {
        for(EmpleadoJG e: empleados) {
            e.mostrarInformacion();
            e.trabajar();
            if(e instanceof Bonificable15) {
                System.out.println("Bonificación: $" + ((Bonificable15) e).calcularBonificacion());
            }
            if(e instanceof Evaluable05) {
                System.out.println("Evaluación: " + ((Evaluable05) e).evaluarDesempeno());
            }
            if(e instanceof Promovible7567) {
                System.out.println("Puede promocionarse: " + ((Promovible7567) e).puedePromocionarse());
            }
            System.out.println("---");
        }
    }
}