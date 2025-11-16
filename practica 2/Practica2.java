public class Practica2 {
    public static void main(String[] args) {
        Universidad7567 universidad = new Universidad7567();

        System.out.println("=== SISTEMA UNIVERSITARIO ===");
        System.out.println("Creando y registrando estudiantes...\n");

        EstudianteJG estudiante1 = new EstudianteJG("Carlos Ordoñez", "1111111", 20, "LCC", 5);
        EstudianteJG estudiante2 = new EstudianteJG("Carlos Mendez", "2222222", 19, "LMAD", 5);
        EstudianteJG estudiante3 = new EstudianteJG("Julio González", "2067567", 20, "LCC", 5);
        EstudianteJG estudiante4 = new EstudianteJG("Juan Peña", "3333333", 19, "LA", 1);
        EstudianteJG estudiante5 = new EstudianteJG("Manuel García", "4444444", 20, "LF", 4);

        System.out.println("=== AGREGANDO ESTUDIANTES ===");
        universidad.agregarEstudiante(estudiante1);
        universidad.agregarEstudiante(estudiante2);
        universidad.agregarEstudiante(estudiante3);
        universidad.agregarEstudiante(estudiante4);
        universidad.agregarEstudiante(estudiante5);

        System.out.println("\n=== DEMOSTRACIÓN DE MÉTODOS DE INSTANCIA ===");

        System.out.println("\n1. Avanzando semestre:");
        estudiante2.avanzarSemestre();

        System.out.println("\n2. Cambiando carrera:");
        estudiante4.cambiarCarrera("LF");

        System.out.println("\n3. Años restantes para graduación:");
        System.out.println(estudiante1.getNombre() + ": " + estudiante1.calcularAniosRestantes() + " años");
        System.out.println(estudiante3.getNombre() + ": " + estudiante3.calcularAniosRestantes() + " años");

        System.out.println("\n4. Verificación de estudiante regular:");
        System.out.println(estudiante1.getNombre() + ": " + (estudiante1.esEstudianteRegular() ? "Sí" : "No"));

        System.out.println("\n5. Correos institucionales:");
        System.out.println(estudiante1.getNombre() + ": " + estudiante1.generarCorreoInstitucional());
        System.out.println(estudiante2.getNombre() + ": " + estudiante2.generarCorreoInstitucional());

        System.out.println("\n=== INFORMACIÓN COMPLETA DEL ESTUDIANTE 1 ===");
        estudiante1.mostrarInformacion();

        System.out.println("\n=== BÚSQUEDA DE ESTUDIANTES ===");

        String matriculaBuscada = "2067567";
        EstudianteJG encontrado = universidad.buscarEstudiantePorMatricula(matriculaBuscada);
        if (encontrado != null) {
            System.out.println("Encontrado por matrícula " + matriculaBuscada + ": " + encontrado.getNombre());
        } else {
            System.out.println("No se encontró estudiante con matrícula " + matriculaBuscada);
        }

        String nombreBuscado = "Pablo";
        EstudianteJG[] encontradosNombre = universidad.buscarEstudiantesPorNombre(nombreBuscado);
        if (encontradosNombre != null) {
            System.out.println("Encontrados " + encontradosNombre.length + " estudiantes con nombre '" + nombreBuscado + "':");
            for (EstudianteJG est : encontradosNombre) {
                System.out.println("  - " + est.getNombre() + " (" + est.getMatricula() + ")");
            }
        } else {
            System.out.println("No se encontraron estudiantes con nombre '" + nombreBuscado + "'");
        }

        System.out.println("\n=== LISTADO COMPLETO ===");
        universidad.mostrarTodosEstudiantes();

        System.out.println("\n=== ESTUDIANTES POR CARRERA ===");
        universidad.mostrarEstudiantesPorCarrera("LCC");
        universidad.mostrarEstudiantesPorCarrera("LM");

        universidad.mostrarEstadisticas();

        System.out.println("\n=== DEMOSTRACIÓN COMPLETADA ===");
    }
}