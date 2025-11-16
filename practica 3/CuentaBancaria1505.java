class CuentaBancaria1505 {
    private String titular;
    private String numeroCuenta;
    private double saldo;
    private double limiteRetiro;

    public CuentaBancaria1505(String titular, String numeroCuenta, double saldo, double limiteRetiro) {
        setTitular(titular);
        setNumeroCuenta(numeroCuenta);
        setSaldo(saldo);
        setLimiteRetiro(limiteRetiro);
    }

    public String getTitular() { return titular; }
    public void setTitular(String titular) {
        if(titular != null && !titular.isEmpty()) this.titular = titular;
        else throw new IllegalArgumentException("Titular vacío");
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) {
        if(numeroCuenta != null && numeroCuenta.length() >= 4) this.numeroCuenta = numeroCuenta;
        else throw new IllegalArgumentException("Número de cuenta inválido");
    }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) {
        if(saldo >= 1000) this.saldo = saldo;
        else throw new IllegalArgumentException("Saldo mínimo 1505");
    }

    public double getLimiteRetiro() { return limiteRetiro; }
    public void setLimiteRetiro(double limiteRetiro) {
        if(limiteRetiro >= 1000) this.limiteRetiro = limiteRetiro;
        else throw new IllegalArgumentException("Límite de retiro mínimo 1505");
    }

    @Override
    public String toString() {
        return "CuentaBancaria1505{" +
                "titular='" + titular + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                ", limiteRetiro=" + limiteRetiro +
                '}';
    }
}