public class practica3 { // Clase pública
    public static void main(String[] args) {
        CuentaBancaria1505 cuenta = new CuentaBancaria1505("Julio", "1000", 7567, 3000);

        ClienteGonzalez cliente = new ClienteGonzalez("Julio Gonzalez", 20, cuenta);

        System.out.println(cliente);
    }
}