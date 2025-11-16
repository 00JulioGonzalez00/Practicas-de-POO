class Libro1505 implements Comparable<Libro1505> {
    private String titulo;
    private String autor;
    private int anio;
    private String categoria;

    public Libro1505(String titulo, String autor, int anio, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.categoria = categoria;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnio() { return anio; }
    public String getCategoria() { return categoria; }

    @Override
    public int compareTo(Libro1505 otro) {
        return this.titulo.compareToIgnoreCase(otro.titulo);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d) [%s]", titulo, autor, anio, categoria);
    }
}