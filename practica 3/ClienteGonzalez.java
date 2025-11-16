class ClienteGonzalez {
    protected String nombre;
    protected int edad;
    private CuentaBancaria1505 cuenta;

    public ClienteGonzalez(String nombre, int edad, CuentaBancaria1505 cuenta) {
        this.nombre = nombre;
        this.edad = edad;
        this.cuenta = cuenta;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public CuentaBancaria1505 getCuenta() { return cuenta; }
    public void setCuenta(CuentaBancaria1505 cuenta) { this.cuenta = cuenta; }

    @Override
    public String toString() {
        return "ClienteGonzalez{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", cuenta=" + cuenta +
                '}';
    }
}