abstract class EmpleadoJG {
    protected String nombre;
    protected int edad;
    protected double salarioBase;

    public EmpleadoJG(String nombre, int edad, double salarioBase) {
        this.nombre = nombre;
        this.edad = edad;
        this.salarioBase = salarioBase;
    }


    public abstract void trabajar();


    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre + ", Edad: " + edad + ", Salario: $" + salarioBase);
    }

    public double getSalarioBase() {
        return salarioBase;
    }
}