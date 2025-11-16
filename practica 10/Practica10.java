import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

enum TipoTransaccion {
    DEPOSITO,
    RETIRO,
    TRANSFERENCIA,
    CONSULTA_SALDO
}

class Transaccion {
    private final TipoTransaccion tipo;
    private final double monto;
    private final String cuentaOrigen;
    private final String cuentaDestino;
    private final String cliente;
    private final int numeroTransaccion;
    private final long timestamp;

    public Transaccion(TipoTransaccion tipo, double monto, String cuentaOrigen,
                       String cuentaDestino, String cliente, int numeroTransaccion) {
        this.tipo = tipo;
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.cliente = cliente;
        this.numeroTransaccion = numeroTransaccion;
        this.timestamp = System.currentTimeMillis();
    }

    public TipoTransaccion getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public String getCuentaOrigen() { return cuentaOrigen; }
    public String getCuentaDestino() { return cuentaDestino; }
    public String getCliente() { return cliente; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        String info = String.format("T%d-%s-$%.2f-%s", numeroTransaccion, tipo, monto, cliente);
        if (tipo == TipoTransaccion.TRANSFERENCIA) {
            info += "->" + cuentaDestino;
        }
        return info;
    }
}

class ContadorTransacciones {
    private static final AtomicInteger totalProcesadas = new AtomicInteger(0);
    private static final AtomicInteger totalRechazadas = new AtomicInteger(0);

    public static void incrementarProcesadas() {
        totalProcesadas.incrementAndGet();
    }

    public static void incrementarRechazadas() {
        totalRechazadas.incrementAndGet();
    }

    public static int getTotalProcesadas() {
        return totalProcesadas.get();
    }

    public static int getTotalRechazadas() {
        return totalRechazadas.get();
    }

    public static void reset() {
        totalProcesadas.set(0);
        totalRechazadas.set(0);
    }
}

class BufferTransacciones {
    private final Queue<Transaccion> buffer;
    private final int capacidad;
    private volatile boolean activo = true;
    private final AtomicInteger clientesActivos = new AtomicInteger(0);

    private final Lock reentrantLock = new ReentrantLock(true);
    private final Condition noLleno = reentrantLock.newCondition();
    private final Condition noVacio = reentrantLock.newCondition();

    public BufferTransacciones(int capacidad) {
        this.capacidad = capacidad;
        this.buffer = new LinkedList<>();
    }

    public void registrarCliente() {
        clientesActivos.incrementAndGet();
    }

    public void desregistrarCliente() {
        clientesActivos.decrementAndGet();
        if (clientesActivos.get() == 0) {
            señalizarFinClientes();
        }
    }

    private void señalizarFinClientes() {
        reentrantLock.lock();
        try {
            noVacio.signalAll();
        } finally {
            reentrantLock.unlock();
        }

        synchronized (this) {
            notifyAll();
        }
    }

    public boolean agregarTransaccion(Transaccion transaccion) {
        reentrantLock.lock();
        try {
            while (buffer.size() == capacidad && activo) {
                try {
                    System.out.println("Buffer lleno. " + Thread.currentThread().getName() + " esperando...");
                    noLleno.await(100, TimeUnit.MILLISECONDS);
                    if (!activo) return false;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }
            }

            if (!activo) return false;

            buffer.offer(transaccion);
            System.out.println(transaccion + " agregada. Buffer: " + buffer.size() + "/" + capacidad);
            noVacio.signalAll();
            return true;

        } finally {
            reentrantLock.unlock();
        }
    }

    public Transaccion tomarTransaccion() {
        reentrantLock.lock();
        try {
            while (buffer.isEmpty() && (activo || clientesActivos.get() > 0)) {
                try {
                    boolean esperaExitosa = noVacio.await(500, TimeUnit.MILLISECONDS);
                    if (!esperaExitosa) {
                        if (!activo && buffer.isEmpty() && clientesActivos.get() == 0) {
                            return null;
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }

            if (buffer.isEmpty()) {
                return null;
            }

            Transaccion transaccion = buffer.poll();
            if (transaccion != null) {
                System.out.println(transaccion + " tomada. Buffer: " + buffer.size() + "/" + capacidad);
            }
            noLleno.signalAll();
            return transaccion;

        } finally {
            reentrantLock.unlock();
        }
    }

    public void detener() {
        activo = false;
        reentrantLock.lock();
        try {
            noLleno.signalAll();
            noVacio.signalAll();
        } finally {
            reentrantLock.unlock();
        }

        synchronized (this) {
            notifyAll();
        }
    }

    public boolean estaActivo() {
        return activo;
    }

    public int tamañoActual() {
        reentrantLock.lock();
        try {
            return buffer.size();
        } finally {
            reentrantLock.unlock();
        }
    }

    public int getClientesActivos() {
        return clientesActivos.get();
    }
}

class CajeroThread15 extends Thread {
    private final BufferTransacciones buffer;
    private volatile boolean ejecutando = true;
    private int transaccionesProcesadas = 0;

    public CajeroThread15(BufferTransacciones buffer, String nombre) {
        super(nombre);
        this.buffer = buffer;
    }

    @Override
    public void run() {
        System.out.println(getName() + " iniciado y listo para procesar transacciones");

        while (ejecutando) {
            try {
                Transaccion transaccion = buffer.tomarTransaccion();

                if (transaccion != null) {
                    procesarTransaccion(transaccion);
                    transaccionesProcesadas++;
                    ContadorTransacciones.incrementarProcesadas();

                    Thread.sleep((long) (Math.random() * 50) + 25);
                } else {
                    if (buffer.getClientesActivos() == 0 && buffer.tamañoActual() == 0) {
                        System.out.println(getName() + " no hay más transacciones. Finalizando...");
                        break;
                    }
                    Thread.sleep(100);
                }

            } catch (InterruptedException e) {
                System.out.println(getName() + " interrumpido. Finalizando...");
                break;
            } catch (Exception e) {
                System.err.println("Error en " + getName() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println(getName() + " finalizado. Transacciones procesadas: " + transaccionesProcesadas);
    }

    private void procesarTransaccion(Transaccion transaccion) throws InterruptedException {
        System.out.println(getName() + " procesando: " + transaccion);

        int tiempoProcesamiento;
        switch (transaccion.getTipo()) {
            case DEPOSITO:
                tiempoProcesamiento = 60 + (int)(Math.random() * 40);
                break;
            case RETIRO:
                tiempoProcesamiento = 80 + (int)(Math.random() * 60);
                break;
            case TRANSFERENCIA:
                tiempoProcesamiento = 120 + (int)(Math.random() * 80);
                break;
            case CONSULTA_SALDO:
                tiempoProcesamiento = 30 + (int)(Math.random() * 20);
                break;
            default:
                tiempoProcesamiento = 50;
        }

        Thread.sleep(tiempoProcesamiento);
        System.out.println(getName() + " completó: " + transaccion + " en " + tiempoProcesamiento + "ms");
    }

    public void detener() {
        ejecutando = false;
        this.interrupt();
    }
}

class ClienteRunnable05 implements Runnable {
    private final BufferTransacciones buffer;
    private final String nombreCliente;
    private final int numTransacciones;
    private int transaccionesRealizadas = 0;

    public ClienteRunnable05(BufferTransacciones buffer, String nombreCliente, int numTransacciones) {
        this.buffer = buffer;
        this.nombreCliente = nombreCliente;
        this.numTransacciones = numTransacciones;
    }

    @Override
    public void run() {
        buffer.registrarCliente();
        System.out.println(nombreCliente + " iniciado. Transacciones a realizar: " + numTransacciones);

        try {
            for (int i = 0; i < numTransacciones && buffer.estaActivo(); i++) {
                try {
                    Transaccion transaccion = generarTransaccionAleatoria(i + 1);

                    boolean exito = buffer.agregarTransaccion(transaccion);

                    if (exito) {
                        transaccionesRealizadas++;
                    } else {
                        System.out.println(nombreCliente + " no pudo agregar transacción (sistema inactivo)");
                        ContadorTransacciones.incrementarRechazadas();
                        break;
                    }

                    Thread.sleep((long) (Math.random() * 100) + 50);

                } catch (InterruptedException e) {
                    System.out.println(nombreCliente + " interrumpido");
                    break;
                } catch (Exception e) {
                    System.err.println("Error en " + nombreCliente + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } finally {
            buffer.desregistrarCliente();
        }

        System.out.println(nombreCliente + " finalizado. Transacciones realizadas: " + transaccionesRealizadas);
    }

    private Transaccion generarTransaccionAleatoria(int numeroTransaccion) {
        TipoTransaccion[] tipos = TipoTransaccion.values();
        TipoTransaccion tipo = tipos[(int) (Math.random() * tipos.length)];

        double monto = 0;
        if (tipo != TipoTransaccion.CONSULTA_SALDO) {
            monto = (Math.random() * 1000) + 10;
        }

        String cuentaOrigen = "CTA-" + (int)(Math.random() * 1000);
        String cuentaDestino = tipo == TipoTransaccion.TRANSFERENCIA ?
                "CTA-" + (int)(Math.random() * 1000) : "";

        return new Transaccion(tipo, monto, cuentaOrigen, cuentaDestino, nombreCliente, numeroTransaccion);
    }
}

class ThreadPoolGonzalez {
    private final List<Worker> hilosWorker;
    private final BlockingQueue<Runnable> colaTareas;
    private volatile boolean ejecutando;

    public ThreadPoolGonzalez(int numHilos) {
        this.hilosWorker = new ArrayList<>();
        this.colaTareas = new LinkedBlockingQueue<>();
        this.ejecutando = true;

        for (int i = 0; i < numHilos; i++) {
            Worker worker = new Worker("PoolWorker-" + (i + 1));
            worker.start();
            hilosWorker.add(worker);
        }

        System.out.println("ThreadPoolGonzalez iniciado con " + numHilos + " hilos");
    }

    public void ejecutar(Runnable tarea) {
        if (ejecutando) {
            try {
                colaTareas.put(tarea);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void detener() {
        ejecutando = false;

        for (Worker worker : hilosWorker) {
            worker.detener();
        }

        colaTareas.clear();

        System.out.println("ThreadPoolGonzalez detenido. Tareas pendientes: " + colaTareas.size());
    }

    private class Worker extends Thread {
        private volatile boolean workerActivo = true;

        public Worker(String nombre) {
            super(nombre);
        }

        public void detener() {
            workerActivo = false;
            this.interrupt();
        }

        @Override
        public void run() {
            while (workerActivo || (!colaTareas.isEmpty() && ejecutando)) {
                try {
                    Runnable tarea = colaTareas.poll(500, TimeUnit.MILLISECONDS);
                    if (tarea != null) {
                        tarea.run();
                    }
                } catch (InterruptedException e) {
                    if (!workerActivo) {
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("Error ejecutando tarea en " + getName() + ": " + e.getMessage());
                }
            }
            System.out.println(getName() + " finalizado");
        }
    }
}

public class Practica10 {
    private static final int BUFFER_SIZE = 7567;
    private static final int NUM_CAJEROS = 5;
    private static final int NUM_CLIENTES = 10; // Reducido para prueba más rápida
    private static final int NUM_TRANSACCIONES = 20; // Reducido para prueba más rápida

    public static void main(String[] args) {
        System.out.println("=== SIMULADOR DE BANCO CONCURRENTE JG ===");
        System.out.println("Tamaño del buffer: " + BUFFER_SIZE);
        System.out.println("Cajeros: " + NUM_CAJEROS + ", Clientes: " + NUM_CLIENTES);
        System.out.println("Transacciones por cliente: " + NUM_TRANSACCIONES);
        System.out.println("=============================================\n");

        ejecutarConSynchronizedYPool();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ContadorTransacciones.reset();

        ejecutarConExecutorService();

        System.out.println("\n=== SIMULACIÓN COMPLETADA ===");
    }

    private static void ejecutarConSynchronizedYPool() {
        System.out.println("\n--- EJECUCIÓN CON SINCHRONIZED Y THREADPOOLGONZALEZ ---");

        BufferTransacciones buffer = new BufferTransacciones(BUFFER_SIZE);
        ThreadPoolGonzalez pool = new ThreadPoolGonzalez(NUM_CAJEROS);

        long startTime = System.currentTimeMillis();

        List<CajeroThread15> cajeros = new ArrayList<>();
        for (int i = 1; i <= NUM_CAJEROS; i++) {
            CajeroThread15 cajero = new CajeroThread15(buffer, "Cajero-" + i);
            cajeros.add(cajero);
            pool.ejecutar(cajero);
        }

        List<Thread> clientes = new ArrayList<>();
        for (int i = 1; i <= NUM_CLIENTES; i++) {
            ClienteRunnable05 cliente = new ClienteRunnable05(buffer, "Cliente-" + i, NUM_TRANSACCIONES);
            Thread hiloCliente = new Thread(cliente);
            clientes.add(hiloCliente);
            hiloCliente.start();
        }

        System.out.println("Esperando a que terminen los clientes...");
        for (Thread cliente : clientes) {
            try {
                cliente.join();
            } catch (InterruptedException e) {
                System.err.println("Interrupción esperando cliente: " + e.getMessage());
            }
        }

        System.out.println("Todos los clientes han terminado");

        System.out.println("Esperando a que se procesen transacciones restantes...");
        int intentos = 0;
        while (buffer.tamañoActual() > 0 && intentos < 10) {
            try {
                Thread.sleep(500);
                System.out.println("Transacciones pendientes: " + buffer.tamañoActual());
                intentos++;
            } catch (InterruptedException e) {
                break;
            }
        }

        System.out.println("Deteniendo cajeros y pool...");
        for (CajeroThread15 cajero : cajeros) {
            cajero.detener();
        }

        buffer.detener();
        pool.detener();

        long endTime = System.currentTimeMillis();

        System.out.println("\n--- RESULTADOS POOL PERSONALIZADO ---");
        System.out.println("️  Tiempo total de ejecución: " + (endTime - startTime) + "ms");
        System.out.println(" Transacciones procesadas: " + ContadorTransacciones.getTotalProcesadas());
        System.out.println(" Transacciones rechazadas: " + ContadorTransacciones.getTotalRechazadas());
        System.out.println(" Transacciones en buffer al final: " + buffer.tamañoActual());

        int totalEsperado = NUM_CLIENTES * NUM_TRANSACCIONES;
        int totalReal = ContadorTransacciones.getTotalProcesadas() + ContadorTransacciones.getTotalRechazadas();
        System.out.println("Verificación: " + totalReal + "/" + totalEsperado + " transacciones manejadas");
    }

    private static void ejecutarConExecutorService() {
        System.out.println("\n---  EJECUCIÓN CON EXECUTOR SERVICE ---");

        BufferTransacciones buffer = new BufferTransacciones(BUFFER_SIZE);
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CAJEROS + NUM_CLIENTES);

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= NUM_CAJEROS; i++) {
            executor.execute(new CajeroThread15(buffer, "Cajero-ES-" + i));
        }

        for (int i = 1; i <= NUM_CLIENTES; i++) {
            executor.execute(new ClienteRunnable05(buffer, "Cliente-ES-" + i, NUM_TRANSACCIONES));
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println("Timeout en ExecutorService - forzando cierre");
                executor.shutdownNow();
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.err.println("ExecutorService no pudo terminar");
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupción en ExecutorService: " + e.getMessage());
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        buffer.detener();

        long endTime = System.currentTimeMillis();

        System.out.println("\n---  RESULTADOS EXECUTOR SERVICE ---");
        System.out.println(" Tiempo de ejecución: " + (endTime - startTime) + "ms");
        System.out.println(" Transacciones procesadas: " + ContadorTransacciones.getTotalProcesadas());
        System.out.println(" Transacciones rechazadas: " + ContadorTransacciones.getTotalRechazadas());
        System.out.println(" Transacciones en buffer al final: " + buffer.tamañoActual());
    }
}