import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

class SistemaBancoJG {
    private Map<String, Double> cuentas;
    private static final Logger logger = Logger.getLogger(SistemaBancoJG.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("banco_julio_gonzalez.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Error configurando logger: " + e.getMessage());
        }
    }

    public SistemaBancoJG() {
        this.cuentas = new HashMap<>();
        logger.info("SistemaBancoJG inicializado - Estudiante: Julio González");
    }

    public void registrarUsuario(String matricula, double saldoInicial) throws Matricula15InvalidaException {
        String estudianteInfo = "Estudiante: Julio González - Matrícula: 7567 - Operación: Registrar usuario";

        if (matricula == null || matricula.length() != 4) {
            Matricula15InvalidaException ex = new Matricula15InvalidaException(
                    "Matrícula '" + matricula + "' debe tener exactamente 4 dígitos");
            logger.severe(estudianteInfo + " - ERROR: " + ex.getMessage());
            throw ex;
        }

        if (!matricula.matches("\\d+")) {
            Matricula15InvalidaException ex = new Matricula15InvalidaException(
                    "Matrícula '" + matricula + "' debe contener solo números");
            logger.severe(estudianteInfo + " - ERROR: " + ex.getMessage());
            throw ex;
        }

        cuentas.put(matricula, saldoInicial);
        logger.info(estudianteInfo + " - ÉXITO: Usuario " + matricula + " registrado con saldo: $" + saldoInicial);
    }

    public void retirar(String matricula, double monto) throws Saldo05InsuficienteException, Usuario7567NoEncontradoException {
        String estudianteInfo = "Estudiante: Julio González - Matrícula: 7567 - Operación: Retiro";

        if (!cuentas.containsKey(matricula)) {
            Usuario7567NoEncontradoException ex = new Usuario7567NoEncontradoException(
                    "Usuario con matrícula '" + matricula + "' no encontrado");
            logger.severe(estudianteInfo + " - ERROR: " + ex.getMessage());
            throw ex;
        }

        double saldoActual = cuentas.get(matricula);
        if (saldoActual < monto) {
            Saldo05InsuficienteException ex = new Saldo05InsuficienteException(
                    "Saldo insuficiente. Saldo actual: $" + saldoActual + ", Monto solicitado: $" + monto);
            logger.warning(estudianteInfo + " - Saldo insuficiente para usuario: " + matricula);
            throw ex;
        }

        cuentas.put(matricula, saldoActual - monto);
        logger.info(estudianteInfo + " - ÉXITO: Retiro de $" + monto + " realizado. Nuevo saldo: $" + (saldoActual - monto));
    }

    public void depositar(String matricula, double monto) throws Usuario7567NoEncontradoException {
        String estudianteInfo = "Estudiante: Julio González - Matrícula: 7567 - Operación: Depósito";

        if (!cuentas.containsKey(matricula)) {
            Usuario7567NoEncontradoException ex = new Usuario7567NoEncontradoException(
                    "Usuario con matrícula '" + matricula + "' no encontrado");
            logger.severe(estudianteInfo + " - ERROR: " + ex.getMessage());
            throw ex;
        }

        double saldoActual = cuentas.get(matricula);
        cuentas.put(matricula, saldoActual + monto);
        logger.info(estudianteInfo + " - ÉXITO: Depósito de $" + monto + " realizado. Nuevo saldo: $" + (saldoActual + monto));
    }

    public void mostrarSaldo(String matricula) throws Usuario7567NoEncontradoException {
        String estudianteInfo = "Estudiante: Julio González - Matrícula: 7567 - Operación: Consultar saldo";

        if (!cuentas.containsKey(matricula)) {
            Usuario7567NoEncontradoException ex = new Usuario7567NoEncontradoException(
                    "Usuario con matrícula '" + matricula + "' no encontrado");
            logger.severe(estudianteInfo + " - ERROR: " + ex.getMessage());
            throw ex;
        }

        double saldo = cuentas.get(matricula);
        System.out.println("Saldo de usuario " + matricula + ": $" + saldo);
        logger.info(estudianteInfo + " - Consulta exitosa. Saldo: $" + saldo);
    }

    public void exportarCuentas(String nombreArchivo) {
        String estudianteInfo = "Estudiante: Julio González - Matrícula: 7567 - Operación: Exportar cuentas";

        try (FileWriter writer = new FileWriter(nombreArchivo);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write("=== EXPORTACIÓN DE CUENTAS BANCARIAS ===");
            bufferedWriter.newLine();
            bufferedWriter.write("Fecha: " + new Date());
            bufferedWriter.newLine();
            bufferedWriter.write("Estudiante: Julio González - Matrícula: 7567");
            bufferedWriter.newLine();
            bufferedWriter.write("=========================================");
            bufferedWriter.newLine();

            for (Map.Entry<String, Double> cuenta : cuentas.entrySet()) {
                String linea = String.format("Matrícula: %s - Saldo: $%.2f",
                        cuenta.getKey(), cuenta.getValue());
                bufferedWriter.write(linea);
                bufferedWriter.newLine();
            }

            logger.info(estudianteInfo + " - ÉXITO: Cuentas exportadas a " + nombreArchivo);
            System.out.println("Cuentas exportadas exitosamente a " + nombreArchivo);

        } catch (IOException e) {
            String errorMsg = "Error exportando cuentas: " + e.getMessage();
            logger.severe(estudianteInfo + " - ERROR: " + errorMsg);
            System.err.println(errorMsg);
        }
    }

    public double obtenerSaldo(String matricula) throws Usuario7567NoEncontradoException {
        if (!cuentas.containsKey(matricula)) {
            throw new Usuario7567NoEncontradoException("Usuario no encontrado");
        }
        return cuentas.get(matricula);
    }

    public void limpiarCuentas() {
        cuentas.clear();
        logger.info("Cuentas limpiadas para pruebas");
    }
}