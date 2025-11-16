import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Practica9 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Path ROOT = Paths.get("./GestorArchivosGonzalez7567");

    public static void main(String[] args) {
        try {
            if (!Files.exists(ROOT)) Files.createDirectories(ROOT);
            GestorArchivos.generarArchivosPrueba(ROOT);
        } catch (IOException e) {
            System.err.println("Error creando carpeta raíz: " + e.getMessage());
        }

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opt = scanner.nextLine().trim();
            switch (opt) {
                case "1": operacionTextos(); break;
                case "2": operacionBinaria(); break;
                case "3": operacionesDirectorios(); break;
                case "4": serializacionDemo(); break;
                case "5": procesarCSV(); break;
                case "6": backupAutomaticoPrompt(); break;
                case "7": GestorArchivos.listarContenido(ROOT); break;
                case "0": salir = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
        System.out.println("Saliendo. ¡Hasta luego!");
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Practica9: GestorArchivosGonzalez7567 ---");
        System.out.println("1) Lectura/Escritura de archivos de texto");
        System.out.println("2) Lectura/Escritura de archivos binarios");
        System.out.println("3) Operaciones con directorios");
        System.out.println("4) Serialización de PersonaJSerializable (guardar/cargar)");
        System.out.println("5) Procesamiento CSV (datos estudiantiles)");
        System.out.println("6) Backup automático de archivo con timestamp");
        System.out.println("7) Listar contenido de la carpeta raíz del gestor");
        System.out.println("0) Salir");
        System.out.print("Elige una opción: ");
    }

    private static void operacionTextos() {
        System.out.println("\n--- Operaciones de texto ---");
        System.out.print("Nombre de archivo (en la carpeta del gestor): ");
        String nombre = scanner.nextLine().trim();
        Path p = ROOT.resolve(nombre);
        System.out.println("1) Escribir (sobrescribe)  2) Añadir  3) Leer");
        String o = scanner.nextLine().trim();
        try {
            if (o.equals("1")) {
                System.out.println("Ingrese el texto (una línea). Para terminar, solo pulse Enter en línea vacía:");
                List<String> lines = new ArrayList<>();
                while (true) {
                    String l = scanner.nextLine();
                    if (l.isEmpty()) break;
                    lines.add(l);
                }
                GestorArchivos.escribirTexto(p, lines, false);
                System.out.println("Archivo escrito: " + p);
            } else if (o.equals("2")) {
                System.out.println("Ingrese la línea a añadir:");
                String l = scanner.nextLine();
                GestorArchivos.escribirTexto(p, Arrays.asList(l), true);
                System.out.println("Línea añadida.");
            } else if (o.equals("3")) {
                List<String> contenido = GestorArchivos.leerTexto(p);
                System.out.println("--- Contenido de " + p.getFileName() + " ---");
                contenido.forEach(System.out::println);
            } else System.out.println("Opción no válida.");
        } catch (IOException e) {
            System.err.println("Error en operación de texto: " + e.getMessage());
        }
    }

    private static void operacionBinaria() {
        System.out.println("\n--- Operaciones binaria ---");
        System.out.print("Nombre de archivo binario (ej: backup_1505.dat): ");
        String nombre = scanner.nextLine().trim();
        Path p = ROOT.resolve(nombre);
        System.out.println("1) Guardar bytes aleatorios  2) Leer como hex/bytes");
        String o = scanner.nextLine().trim();
        try {
            if (o.equals("1")) {
                System.out.print("Número de bytes a generar: ");
                int n = Integer.parseInt(scanner.nextLine().trim());
                byte[] data = new byte[n];
                new Random().nextBytes(data);
                GestorArchivos.escribirBytes(p, data);
                System.out.println("Archivo binario guardado: " + p);
            } else if (o.equals("2")) {
                byte[] data = GestorArchivos.leerBytes(p);
                System.out.println("Tamaño: " + data.length + " bytes");
                System.out.println(Arrays.toString(Arrays.copyOf(data, Math.min(64, data.length))) + (data.length>64?" ...":""));
            } else System.out.println("Opción no válida.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error en operación binaria: " + e.getMessage());
        }
    }

    private static void operacionesDirectorios() {
        System.out.println("\n--- Operaciones de directorios ---");
        System.out.println("1) Crear subdirectorio  2) Eliminar archivo  3) Mostrar árbol");
        String o = scanner.nextLine().trim();
        try {
            if (o.equals("1")) {
                System.out.print("Nombre subdirectorio: ");
                String n = scanner.nextLine().trim();
                GestorArchivos.crearDirectorio(ROOT.resolve(n));
            } else if (o.equals("2")) {
                System.out.print("Nombre archivo a eliminar: ");
                String n = scanner.nextLine().trim();
                GestorArchivos.eliminarArchivo(ROOT.resolve(n));
            } else if (o.equals("3")) {
                GestorArchivos.mostrarArbol(ROOT);
            } else System.out.println("Opción no válida.");
        } catch (IOException e) {
            System.err.println("Error en operaciones de directorio: " + e.getMessage());
        }
    }

    private static void serializacionDemo() {
        System.out.println("\n--- Serialización PersonaJSerializable ---");
        System.out.println("1) Guardar persona  2) Cargar persona");
        String o = scanner.nextLine().trim();
        Path p = ROOT.resolve("persona_2067567.ser");
        try {
            if (o.equals("1")) {
                System.out.print("Nombre: "); String nombre = scanner.nextLine().trim();
                System.out.print("Edad: "); int edad = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("Matricula/ID: "); String id = scanner.nextLine().trim();
                PersonaJSerializable per = new PersonaJSerializable(nombre, edad, id);
                GestorArchivos.serializarPersona(p, per);
                System.out.println("Persona serializada a: " + p);
            } else if (o.equals("2")) {
                PersonaJSerializable per = GestorArchivos.deserializarPersona(p);
                if (per != null) System.out.println("Cargado: " + per);
                else System.out.println("No existe archivo de persona: " + p);
            } else System.out.println("Opción no válida.");
        } catch (IOException | ClassNotFoundException | NumberFormatException e) {
            System.err.println("Error en serialización: " + e.getMessage());
        }
    }

    private static void procesarCSV() {
        System.out.println("\n--- Procesamiento CSV (log_Gonzalez.csv) ---");
        Path csv = ROOT.resolve("log_Gonzalez.csv");
        try {
            List<Student> students = GestorArchivos.leerCSVEstudiantes(csv);
            System.out.println("Encontrados " + students.size() + " registros. Mostrar? (s/n)");
            String r = scanner.nextLine().trim();
            if (r.equalsIgnoreCase("s")) {
                students.forEach(s -> System.out.println(s));
            }
            System.out.println("Calcular promedio de calificaciones (si hay campo 'grade')? (s/n)");
            r = scanner.nextLine().trim();
            if (r.equalsIgnoreCase("s")) {
                double avg = students.stream().filter(s -> s.grade>=0).mapToDouble(s->s.grade).average().orElse(Double.NaN);
                System.out.println("Promedio: " + (Double.isNaN(avg)?"N/A":String.format("%.2f", avg)));
            }
        } catch (IOException e) {
            System.err.println("Error leyendo CSV: " + e.getMessage());
        }
    }

    private static void backupAutomaticoPrompt() {
        System.out.println("\n--- Backup automático ---");
        System.out.print("Archivo a respaldar (ruta relativa a la carpeta del gestor): ");
        String nombre = scanner.nextLine().trim();
        Path p = ROOT.resolve(nombre);
        try {
            Path destino = GestorArchivos.backupConTimestamp(p, ROOT.resolve("backups"));
            System.out.println("Backup creado en: " + destino);
        } catch (IOException e) {
            System.err.println("Error creando backup: " + e.getMessage());
        }
    }
}


class GestorArchivos {
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public static List<String> leerTexto(Path p) throws IOException {
        if (!Files.exists(p)) return Collections.emptyList();
        return Files.readAllLines(p, StandardCharsets.UTF_8);
    }

    public static void escribirTexto(Path p, List<String> lines, boolean append) throws IOException {
        if (!Files.exists(p.getParent())) Files.createDirectories(p.getParent());
        OpenOption opt = append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE;
        if (!append) opt = StandardOpenOption.CREATE; // sobrescribe
        Files.write(p, lines, StandardCharsets.UTF_8, opt);
    }

    public static void escribirBytes(Path p, byte[] data) throws IOException {
        if (!Files.exists(p.getParent())) Files.createDirectories(p.getParent());
        Files.write(p, data);
    }

    public static byte[] leerBytes(Path p) throws IOException {
        if (!Files.exists(p)) return new byte[0];
        return Files.readAllBytes(p);
    }

    public static void crearDirectorio(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
            System.out.println("Directorio creado: " + dir);
        } else System.out.println("Ya existe: " + dir);
    }

    public static void eliminarArchivo(Path p) throws IOException {
        if (Files.exists(p)) {
            Files.delete(p);
            System.out.println("Archivo eliminado: " + p);
        } else System.out.println("No existe: " + p);
    }

    public static void listarContenido(Path root) {
        try {
            System.out.println("Contenido de: " + root.toAbsolutePath());
            Files.walk(root, 1).forEach(x -> System.out.println(x.getFileName()));
        } catch (IOException e) {
            System.err.println("Error listando: " + e.getMessage());
        }
    }

    public static void mostrarArbol(Path root) throws IOException {
        Files.walk(root).forEach(p -> System.out.println(root.relativize(p)));
    }

    public static void serializarPersona(Path p, PersonaJSerializable per) throws IOException {
        if (!Files.exists(p.getParent())) Files.createDirectories(p.getParent());
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(p))) {
            oos.writeObject(per);
        }
    }

    public static PersonaJSerializable deserializarPersona(Path p) throws IOException, ClassNotFoundException {
        if (!Files.exists(p)) return null;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(p))) {
            return (PersonaJSerializable) ois.readObject();
        }
    }

    public static Path backupConTimestamp(Path fuente, Path carpetaBackups) throws IOException {
        if (!Files.exists(fuente)) throw new NoSuchFileException("Fuente no encontrada: " + fuente);
        if (!Files.exists(carpetaBackups)) Files.createDirectories(carpetaBackups);
        String ts = LocalDateTime.now().format(TS);
        String nombre = fuente.getFileName().toString();
        Path destino = carpetaBackups.resolve(nombre + ".backup." + ts);
        Files.copy(fuente, destino, StandardCopyOption.REPLACE_EXISTING);
        return destino;
    }

    public static List<Student> leerCSVEstudiantes(Path csv) throws IOException {
        List<Student> out = new ArrayList<>();
        if (!Files.exists(csv)) return out;
        List<String> lines = Files.readAllLines(csv, StandardCharsets.UTF_8);
        boolean first = true;
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",");
            if (first && parts.length>0 && parts[0].toLowerCase().contains("id")) { first = false; continue; }
            first = false;
            String id = parts.length>0?parts[0].trim():"";
            String name = parts.length>1?parts[1].trim():"";
            double grade = -1;
            if (parts.length>2) {
                try { grade = Double.parseDouble(parts[2].trim()); } catch (NumberFormatException ignored) {}
            }
            out.add(new Student(id, name, grade));
        }
        return out;
    }

    public static void generarArchivosPrueba(Path root) throws IOException {
        if (!Files.exists(root)) Files.createDirectories(root);
        Path t = root.resolve("datos_2067567.txt");
        if (!Files.exists(t)) Files.write(t, Arrays.asList("Este es un archivo de prueba.", "Usuario: Gonzalez7567"), StandardCharsets.UTF_8);
        Path b = root.resolve("backup_1505.dat");
        if (!Files.exists(b)) Files.write(b, new byte[]{0,1,2,3,4,5,6,7,8,9});
        Path c = root.resolve("log_Gonzalez.csv");
        if (!Files.exists(c)) Files.write(c, Arrays.asList("id,name,grade", "A001,Juan Peña,9.0", "A002,Manuel García,9.5"), StandardCharsets.UTF_8);
    }
}


class PersonaJSerializable implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private int edad;
    private String id;

    public PersonaJSerializable(String nombre, int edad, String id) {
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getId() { return id; }

    @Override
    public String toString() {
        return String.format("Persona[nombre=%s, edad=%d, id=%s]", nombre, edad, id);
    }
}

class Student {
    String id;
    String name;
    double grade = -1;
    public Student(String id, String name, double grade) { this.id=id; this.name=name; this.grade=grade; }
    @Override
    public String toString() { return String.format("Student[id=%s, name=%s, grade=%s]", id, name, grade<0?"N/A":grade);
    }
}