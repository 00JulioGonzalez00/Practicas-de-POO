import java.util.*;


class ComparadorPorAutor implements Comparator<Libro1505> {
    public int compare(Libro1505 l1, Libro1505 l2) {
        return l1.getAutor().compareToIgnoreCase(l2.getAutor());
    }
}

class ComparadorPorAnio implements Comparator<Libro1505> {
    public int compare(Libro1505 l1, Libro1505 l2) {
        return Integer.compare(l1.getAnio(), l2.getAnio());
    }
}

public class Practica8 {
    public static void main(String[] args) {
        BibliotecaJ7567 biblioteca = new BibliotecaJ7567();

        Libro1505 l1 = new Libro1505("Pedro Páramo", "Juan Rulfo", 1955, "Realismo Mágico");
        Libro1505 l2 = new Libro1505("La muerte de Artemio Cruz", "Carlos Fuentes", 1962, "Literatura Contemporánea");
        Libro1505 l3 = new Libro1505("Los de abajo", "Mariano Azuela", 1916, "Novela de la Revolución");
        Libro1505 l4 = new Libro1505("Aura", "Carlos Fuentes", 1962, "Novela Corta");

        biblioteca.agregarLibro(l1);
        biblioteca.agregarLibro(l2);
        biblioteca.agregarLibro(l3);
        biblioteca.agregarLibro(l4);

        biblioteca.registrarUsuario("MAT12345","Estudiante1");
        biblioteca.registrarUsuario("MAT67890","Estudiante2");

        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        while(!salir) {
            System.out.println("\n=== BibliotecaJ567 ===");
            System.out.println("1. Listar libros");
            System.out.println("2. Buscar por categoría");
            System.out.println("3. Buscar por autor");
            System.out.println("4. Reservar libro");
            System.out.println("5. Atender reserva");
            System.out.println("6. Mostrar usuarios");
            System.out.println("7. Mostrar categorías");
            System.out.println("8. Salir");
            System.out.print("Opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();
            switch(opcion) {
                case 1: biblioteca.listarLibros(); break;
                case 2:
                    System.out.print("Categoría: ");
                    String cat = sc.nextLine();
                    biblioteca.buscarPorCategoria(cat).forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Autor: ");
                    String autor = sc.nextLine();
                    biblioteca.buscarPorAutor(autor).forEach(System.out::println);
                    break;
                case 4:
                    biblioteca.reservarLibro(l1);
                    System.out.println("Libro reservado.");
                    break;
                case 5:
                    Libro1505 atendido = biblioteca.atenderReserva();
                    System.out.println(atendido != null ? "Libro atendido: " + atendido : "No hay reservas");
                    break;
                case 6: biblioteca.mostrarUsuarios(); break;
                case 7: biblioteca.mostrarCategorias(); break;
                case 8: salir = true; break;
                default: System.out.println("Opción inválida");
            }
        }
        sc.close();
    }
}