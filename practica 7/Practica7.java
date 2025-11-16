class ExceptionGonzalezBase extends Exception {
    public ExceptionGonzalezBase(String message) { super(message); }
}

class Matricula15InvalidaException extends ExceptionGonzalezBase {
    public Matricula15InvalidaException(String message) { super(message); }
}

class Saldo05InsuficienteException extends ExceptionGonzalezBase {
    public Saldo05InsuficienteException(String message) { super(message); }
}

class Usuario7567NoEncontradoException extends ExceptionGonzalezBase {
    public Usuario7567NoEncontradoException(String message) { super(message); }
}

class PruebasUnitariasSistemaBanco {

    public static void ejecutarTodasLasPruebas() {
        System.out.println("=== EJECUTANDO PRUEBAS UNITARIAS ===");
        System.out.println("Estudiante: Julio González");
        System.out.println("Matrícula: 2067567");
        System.out.println("====================================\n");

        testRegistroUsuarioExitoso();
        testMatriculaLongitudInvalida();
        testMatriculaNoNumerica();
        testUsuarioNoEncontrado();
        testSaldoInsuficiente();
        testRetiroExitoso();
        testDepositoExitoso();
        testOperacionesMultiples();
        testJerarquiaExcepciones();

        System.out.println("\n=== TODAS LAS PRUEBAS COMPLETADAS ===");
    }

    private static void testRegistroUsuarioExitoso() {
        try {
            SistemaBancoJG banco = new SistemaBancoJG();
            banco.registrarUsuario("1234", 1000.0);
            System.out.println("✓ testRegistroUsuarioExitoso: PASÓ");
        } catch (Exception e) {
            System.out.println("✗ testRegistroUsuarioExitoso: FALLÓ - " + e.getMessage());
        }
    }

    private static void testMatriculaLongitudInvalida() {
        try {
            SistemaBancoJG banco = new SistemaBancoJG();
            banco.registrarUsuario("567", 1000.0);
            System.out.println("✗ testMatriculaLongitudInvalida: FALLÓ - Debió lanzar excepción");
        } catch (Matricula15InvalidaException e) {
            System.out.println("✓ testMatriculaLongitudInvalida: PASÓ");
        } catch (Exception e) {
            System.out.println("✗ testMatriculaLongitudInvalida: FALLÓ - Excepción incorrecta: " + e.getClass().getSimpleName());
        }
    }

    private static void testMatriculaNoNumerica() {
        try {
            SistemaBancoJG banco = new SistemaBancoJG();
            banco.registrarUsuario("12a4", 1000.0);
            System.out.println("✗ testMatriculaNoNumerica: FALLÓ - Debió lanzar excepción");
        } catch (Matricula15InvalidaException e) {
            System.out.println("✓ testMatriculaNoNumerica: PASÓ");
        } catch (Exception e) {
            System.out.println("✗ testMatriculaNoNumerica: FALLÓ - Excepción incorrecta");
        }
    }

    private static void testUsuarioNoEncontrado() {
        try {
            SistemaBancoJG banco = new SistemaBancoJG();
            banco.retirar("9999", 100.0);
            System.out.println("✗ testUsuarioNoEncontrado: FALLÓ - Debió lanzar excepción");
        } catch (Usuario7567NoEncontradoException e) {
            System.out.println("✓ testUsuarioNoEncontrado: PASÓ");
        } catch (Exception e) {
            System.out.println("✗ testUsuarioNoEncontrado: FALLÓ - Excepción incorrecta: " + e.getClass().getSimpleName());
        }
    }

    private static void testSaldoInsuficiente() {
        try {
            SistemaBancoJG banco = new SistemaBancoJG();
            banco.registrarUsuario("5555", 100.0);
            banco.retirar("5555", 200.0);
            System.out.println("✗ testSaldoInsuficiente: FALLÓ - Debió lanzar excepción");
        } catch (Saldo05InsuficienteException e) {
            System.out.println("✓ testSaldoInsuficiente: PASÓ");
        } catch (Exception e) {
            System.out.println("✗ testSaldoInsuficiente: FALLÓ - Excepción incorrecta: " + e.getClass().getSimpleName());
        }
    }

    private static void testRetiroExitoso() {
        try {
            SistemaBancoJG banco = new SistemaBancoJG();
            banco.registrarUsuario("6666", 500.0);
            banco.retirar("6666", 300.0);
            double saldo = banco.obtenerSaldo("6666");
            if (Math.abs(saldo - 200.0) < 0.001) {
                System.out.println("✓ testRetiroExitoso: PASÓ");
            } else {
                System.out.println("✗ testRetiroExitoso: FALLÓ - Saldo incorrecto: " + saldo);
            }
        } catch (Exception e) {
            System.out.println("✗ testRetiroExitoso: FALLÓ - " + e.getMessage());
        }
    }

    private static void testDepositoExitoso() {
        try {
            SistemaBancoJG banco = new SistemaBancoJG();
            banco.registrarUsuario("7777", 100.0);
            banco.depositar("7777", 50.0);
            double saldo = banco.obtenerSaldo("7777");
            if (Math.abs(saldo - 150.0) < 0.001) {
                System.out.println("✓ testDepositoExitoso: PASÓ");
            } else {
                System.out.println("✗ testDepositoExitoso: FALLÓ - Saldo incorrecto: " + saldo);
            }
        } catch (Exception e) {
            System.out.println("✗ testDepositoExitoso: FALLÓ - " + e.getMessage());
        }
    }

    private static void testOperacionesMultiples() {
        try {
            SistemaBancoJG banco = new SistemaBancoJG();
            banco.registrarUsuario("8888", 1000.0);
            banco.depositar("8888", 500.0);
            banco.retirar("8888", 300.0);
            double saldo = banco.obtenerSaldo("8888");
            if (Math.abs(saldo - 1200.0) < 0.001) {
                System.out.println("✓ testOperacionesMultiples: PASÓ");
            } else {
                System.out.println("✗ testOperacionesMultiples: FALLÓ - Saldo incorrecto: " + saldo);
            }
        } catch (Exception e) {
            System.out.println("✗ testOperacionesMultiples: FALLÓ - " + e.getMessage());
        }
    }

    private static void testJerarquiaExcepciones() {
        boolean jerarquiaCorrecta =
                (new Matricula15InvalidaException("test") instanceof ExceptionGonzalezBase) &&
                        (new Saldo05InsuficienteException("test") instanceof ExceptionGonzalezBase) &&
                        (new Usuario7567NoEncontradoException("test") instanceof ExceptionGonzalezBase);

        if (jerarquiaCorrecta) {
            System.out.println("✓ testJerarquiaExcepciones: PASÓ");
        } else {
            System.out.println("✗ testJerarquiaExcepciones: FALLÓ - Jerarquía incorrecta");
        }
    }
}

public class Practica7 {
    public static void main(String[] args) {
        System.out.println("=== PRÁCTICA 7: MANEJO DE EXCEPCIONES ===");
        System.out.println("Estudiante: Julio González");
        System.out.println("Matrícula: 2067567");
        System.out.println("=========================================\n");

        ejecutarDemoPrincipal();

        System.out.println("\n");
        PruebasUnitariasSistemaBanco.ejecutarTodasLasPruebas();

        System.out.println("\n=== PRÁCTICA COMPLETADA ===");
        System.out.println("Revisa el archivo 'banco_julio_gonzalez.log' para ver el registro detallado");
    }

    private static void ejecutarDemoPrincipal() {
        SistemaBancoJG banco = new SistemaBancoJG();

        System.out.println("1. REGISTRANDO USUARIOS:");
        try {
            banco.registrarUsuario("1234", 1000.0);
            banco.registrarUsuario("5678", 500.0);
        } catch (Matricula15InvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n2. OPERACIONES BANCARIAS:");
        try {
            banco.retirar("5678", 200.0);
            banco.mostrarSaldo("5678");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n3. EXPORTANDO CUENTAS:");
        banco.exportarCuentas("cuentas_julio_gonzalez.txt");
    }
}