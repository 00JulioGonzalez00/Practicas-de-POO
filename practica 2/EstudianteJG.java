public class EstudianteJG {
    private String nombre;
    private String matricula;
    private int edad;
    private String carrera;
    private int semestreActual;

    public EstudianteJG(String nombre, String matricula, int edad, String carrera, int semestreActual) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.edad = edad;
        this.carrera = carrera;
        this.semestreActual = semestreActual;
    }

    public EstudianteJG(String nombre, String matricula, int edad, String carrera) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.edad = edad;
        this.carrera = carrera;
        this.semestreActual = 1;
    }

    public EstudianteJG(String nombre, String matricula) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.edad = 18; // Edad por defecto
        this.carrera = "No asignada";
        this.semestreActual = 1;
    }



    public void avanzarSemestre() {
        if (this.semestreActual < 12) { // Máximo 12 semestres
            this.semestreActual++;
            System.out.println(nombre + " ha avanzado al semestre " + semestreActual);
        } else {
            System.out.println(nombre + " ya ha completado todos los semestres");
        }
    }

    public void cambiarCarrera(String nuevaCarrera) {
        String carreraAnterior = this.carrera;
        this.carrera = nuevaCarrera;
        this.semestreActual = 1;
        System.out.println(nombre + " ha cambiado de " + carreraAnterior + " a " + nuevaCarrera);
    }

    public int calcularAniosRestantes() {
        int semestresRestantes = 10 - this.semestreActual; // Asumiendo 5 años = 10 semestres
        if (semestresRestantes < 0) return 0;
        return (int) Math.ceil(semestresRestantes / 2.0);
    }

    public boolean esEstudianteRegular() {
        return this.edad >= 17 && this.edad <= 35 && this.semestreActual >= 1 && this.semestreActual <= 12;
    }

    public String generarCorreoInstitucional() {
        String[] partesNombre = this.nombre.toLowerCase().split(" ");
        String inicialNombre = partesNombre[0].substring(0, 1);
        String apellido = partesNombre.length > 1 ? partesNombre[partesNombre.length - 1] : "";
        return inicialNombre + apellido + this.matricula.substring(this.matricula.length() - 3) + "@universidad.edu.mx";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public int getSemestreActual() {
        return semestreActual;
    }

    public void setSemestreActual(int semestreActual) {
        this.semestreActual = semestreActual;
    }

    @Override
    public String toString() {
        return "EstudianteJG{" +
                "nombre='" + nombre + '\'' +
                ", matricula='" + matricula + '\'' +
                ", edad=" + edad +
                ", carrera='" + carrera + '\'' +
                ", semestreActual=" + semestreActual +
                '}';
    }

    public void mostrarInformacion() {
        System.out.println("=== INFORMACIÓN DEL ESTUDIANTE ===");
        System.out.println("Nombre: " + nombre);
        System.out.println("Matrícula: " + matricula);
        System.out.println("Edad: " + edad + " años");
        System.out.println("Carrera: " + carrera);
        System.out.println("Semestre Actual: " + semestreActual);
        System.out.println("Correo Institucional: " + generarCorreoInstitucional());
        System.out.println("Años restantes para graduación: " + calcularAniosRestantes());
        System.out.println("Estudiante regular: " + (esEstudianteRegular() ? "Sí" : "No"));
        System.out.println("=================================");
    }
}