public class Universidad7567 {
    private EstudianteJG[] estudiantes;
    private int cantidadEstudiantes;
    private static final int CAPACIDAD_MAXIMA = 100;

    public Universidad7567() {
        this.estudiantes = new EstudianteJG[CAPACIDAD_MAXIMA];
        this.cantidadEstudiantes = 0;
    }

    public boolean agregarEstudiante(EstudianteJG estudiante) {
        if (cantidadEstudiantes < CAPACIDAD_MAXIMA) {
            for (int i = 0; i < cantidadEstudiantes; i++) {
                if (estudiantes[i].getMatricula().equals(estudiante.getMatricula())) {
                    System.out.println("Error: Ya existe un estudiante con la matrícula " + estudiante.getMatricula());
                    return false;
                }
            }

            estudiantes[cantidadEstudiantes] = estudiante;
            cantidadEstudiantes++;
            System.out.println("Estudiante " + estudiante.getNombre() + " agregado exitosamente.");
            return true;
        } else {
            System.out.println("Error: Capacidad máxima de estudiantes alcanzada.");
            return false;
        }
    }

    public EstudianteJG buscarEstudiantePorMatricula(String matricula) {
        for (int i = 0; i < cantidadEstudiantes; i++) {
            if (estudiantes[i].getMatricula().equals(matricula)) {
                return estudiantes[i];
            }
        }
        return null;
    }

    public EstudianteJG[] buscarEstudiantesPorNombre(String nombre) {
        int contador = 0;

        for (int i = 0; i < cantidadEstudiantes; i++) {
            if (estudiantes[i].getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                contador++;
            }
        }

        if (contador == 0) {
            return null;
        }

        EstudianteJG[] encontrados = new EstudianteJG[contador];
        int index = 0;

        for (int i = 0; i < cantidadEstudiantes; i++) {
            if (estudiantes[i].getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados[index] = estudiantes[i];
                index++;
            }
        }

        return encontrados;
    }

    public void mostrarTodosEstudiantes() {
        if (cantidadEstudiantes == 0) {
            System.out.println("No hay estudiantes registrados en la universidad.");
            return;
        }

        System.out.println("\n=== LISTA DE ESTUDIANTES ===");
        System.out.println("Total de estudiantes: " + cantidadEstudiantes);
        System.out.println("----------------------------------------");

        for (int i = 0; i < cantidadEstudiantes; i++) {
            System.out.println((i + 1) + ". " + estudiantes[i].getNombre() +
                    " - " + estudiantes[i].getMatricula() +
                    " - " + estudiantes[i].getCarrera() +
                    " - Semestre: " + estudiantes[i].getSemestreActual());
        }
        System.out.println("========================================");
    }

    public void mostrarEstudiantesPorCarrera(String carrera) {
        System.out.println("\n=== ESTUDIANTES DE " + carrera.toUpperCase() + " ===");
        boolean encontrados = false;

        for (int i = 0; i < cantidadEstudiantes; i++) {
            if (estudiantes[i].getCarrera().equalsIgnoreCase(carrera)) {
                if (!encontrados) {
                    encontrados = true;
                }
                System.out.println("- " + estudiantes[i].getNombre() +
                        " (" + estudiantes[i].getMatricula() + ") - Semestre: " + estudiantes[i].getSemestreActual());
            }
        }

        if (!encontrados) {
            System.out.println("No se encontraron estudiantes en la carrera " + carrera + ".");
        }
    }

    public void mostrarEstadisticas() {
        if (cantidadEstudiantes == 0) {
            System.out.println("No hay estudiantes.");
            return;
        }

        int totalEdad = 0;
        int[] estudiantesPorSemestre = new int[13]; // Índices 1-12

        for (int i = 0; i < cantidadEstudiantes; i++) {
            totalEdad += estudiantes[i].getEdad();
            if (estudiantes[i].getSemestreActual() >= 1 && estudiantes[i].getSemestreActual() <= 12) {
                estudiantesPorSemestre[estudiantes[i].getSemestreActual()]++;
            }
        }

        double promedioEdad = (double) totalEdad / cantidadEstudiantes;

        System.out.println("\n=== ESTADÍSTICAS DE LA UNIVERSIDAD ===");
        System.out.println("Total de estudiantes: " + cantidadEstudiantes);
        System.out.printf("Edad promedio: %.2f años\n", promedioEdad);
        System.out.println("Distribución por semestres:");

        for (int i = 1; i <= 12; i++) {
            if (estudiantesPorSemestre[i] > 0) {
                System.out.println("  Semestre " + i + ": " + estudiantesPorSemestre[i] + " estudiantes.");
            }
        }
        System.out.println("=====================================");
    }

    public int getCantidadEstudiantes() {
        return cantidadEstudiantes;
    }

    public int getCapacidadMaxima() {
        return CAPACIDAD_MAXIMA;
    }
}