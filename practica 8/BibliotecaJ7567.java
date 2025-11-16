import java.util.*;
import java.util.stream.*;

class BibliotecaJ7567 {
    private List<Libro1505> libros;
    private Map<String, String> usuarios;
    private Queue<Libro1505> reservas;

    public BibliotecaJ7567() {
        this.libros = new ArrayList<>();
        this.usuarios = new HashMap<>();
        this.reservas = new LinkedList<>();
    }

    public void agregarLibro(Libro1505 libro) {
        libros.add(libro);
    }

    public void registrarUsuario(String matricula, String nombre) {
        usuarios.put(matricula, nombre);
    }

    public List<Libro1505> buscarPorCategoria(String categoria) {
        return libros.stream()
                .filter(libro -> libro.getCategoria().toLowerCase().contains(categoria.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Libro1505> buscarPorAutor(String autor) {
        return libros.stream()
                .filter(libro -> libro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void listarLibros() {
        System.out.println("\n=== LISTA DE LIBROS ===");
        if (libros.isEmpty()) {
            System.out.println("No hay libros en la biblioteca.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    public void reservarLibro(Libro1505 libro) {
        if (libros.contains(libro)) {
            reservas.offer(libro);
            System.out.println("Libro reservado: " + libro.getTitulo());
        } else {
            System.out.println("El libro no existe en la biblioteca.");
        }
    }

    public Libro1505 atenderReserva() {
        return reservas.poll();
    }

    public void mostrarUsuarios() {
        System.out.println("\n=== USUARIOS REGISTRADOS ===");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            usuarios.forEach((matricula, nombre) ->
                    System.out.println("Matrícula: " + matricula + " - Nombre: " + nombre));
        }
    }

    public void mostrarCategorias() {
        System.out.println("\n=== CATEGORÍAS DISPONIBLES ===");
        libros.stream()
                .map(Libro1505::getCategoria)
                .distinct()
                .forEach(System.out::println);
    }
}