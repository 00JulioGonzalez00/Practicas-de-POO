import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.Arrays;

public class Practica11 extends Application {

    private TableView<Estudiante> tabla;
    private ObservableList<Estudiante> datos;
    private CampoGonzalezValidado tfNombre;
    private CampoGonzalezValidado tfMatricula;
    private CampoGonzalezValidado tfCarrera;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SistemaEstudiantilJ567 - Rojo, Negro y Blanco");

        MenuBar menuBar = crearMenuBar(primaryStage);

        VBox formulario = crearFormulario();

        tabla = crearTabla();
        datos = FXCollections.observableArrayList();
        tabla.setItems(datos);

        HBox botones = crearBotonesAccion();

        VBox root = new VBox(menuBar, formulario, botones, tabla);
        root.setSpacing(10);
        Scene scene = new Scene(root, 800, 600);

        cargarCSS(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void cargarCSS(Scene scene) {
        try {
            String[] cssPaths = {
                    "recursos_Gonzalez/estilos_2067567.css",
                    "/recursos_Gonzalez/estilos_2067567.css",
                    "estilos_2067567.css"
            };

            boolean cssCargado = false;
            for (String cssPath : cssPaths) {
                try {
                    java.net.URL cssUrl = getClass().getResource(cssPath);
                    if (cssUrl != null) {
                        scene.getStylesheets().add(cssUrl.toExternalForm());
                        System.out.println("CSS cargado desde: " + cssPath);
                        cssCargado = true;
                        break;
                    }
                } catch (Exception e) {
                    System.err.println("Error cargando CSS desde " + cssPath + ": " + e.getMessage());
                }
            }

            if (!cssCargado) {
                System.out.println("No se pudo cargar el CSS, usando estilos por defecto");
                aplicarEstilosDirectos(scene);
            }

        } catch (Exception e) {
            System.err.println("Error crítico cargando CSS: " + e.getMessage());
            aplicarEstilosDirectos(scene);
        }
    }

    private void aplicarEstilosDirectos(Scene scene) {
        scene.getRoot().setStyle("-fx-background-color: white;");

        aplicarEstilosComponentes();
    }

    private void aplicarEstilosComponentes() {
        if (tabla != null) {
            tabla.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;");
        }
    }

    private MenuBar crearMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: black;");

        Menu menuArchivo = new Menu("Archivo");
        menuArchivo.setStyle("-fx-text-fill: white;");

        MenuItem miSalir = new MenuItem("Salir");
        miSalir.setStyle("-fx-text-fill: black;");
        miSalir.setOnAction(e -> primaryStage.close());
        menuArchivo.getItems().add(miSalir);

        Menu menuAyuda = new Menu("Ayuda");
        menuAyuda.setStyle("-fx-text-fill: white;");

        MenuItem miAcercaDe = new MenuItem("Acerca de");
        miAcercaDe.setStyle("-fx-text-fill: black;");
        miAcercaDe.setOnAction(e -> mostrarAcercaDe());
        menuAyuda.getItems().add(miAcercaDe);

        menuBar.getMenus().addAll(menuArchivo, menuAyuda);
        return menuBar;
    }

    private VBox crearFormulario() {
        tfNombre = new CampoGonzalezValidado("Nombre", "texto");
        tfMatricula = new CampoGonzalezValidado("Matricula", "matricula");
        tfCarrera = new CampoGonzalezValidado("Carrera", "texto");

        VBox form = new VBox(10);
        form.setPadding(new Insets(10));
        form.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2px;");

        Label titulo = new Label("Datos del Estudiante:");
        titulo.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 14px;");

        HBox campos = new HBox(10, tfNombre, tfMatricula, tfCarrera);
        campos.setPadding(new Insets(5));

        form.getChildren().addAll(titulo, campos);

        return form;
    }

    private TableView<Estudiante> crearTabla() {
        TableView<Estudiante> tabla = new TableView<>();
        tabla.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;");

        TableColumn<Estudiante, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        colNombre.setPrefWidth(200);
        colNombre.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        TableColumn<Estudiante, String> colMatricula = new TableColumn<>("Matricula");
        colMatricula.setCellValueFactory(data -> data.getValue().matriculaProperty());
        colMatricula.setPrefWidth(150);
        colMatricula.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        TableColumn<Estudiante, String> colCarrera = new TableColumn<>("Carrera");
        colCarrera.setCellValueFactory(data -> data.getValue().carreraProperty());
        colCarrera.setPrefWidth(200);
        colCarrera.setStyle("-fx-background-color: black; -fx-text-fill: white;");

        tabla.getColumns().addAll(Arrays.asList(colNombre, colMatricula, colCarrera));

        return tabla;
    }

    private HBox crearBotonesAccion() {
        Button btnAgregar = new Button("Agregar");
        btnAgregar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;");
        btnAgregar.setOnMouseEntered(e -> btnAgregar.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnAgregar.setOnMouseExited(e -> btnAgregar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnAgregar.setOnAction(e -> agregarEstudiante());

        Button btnEditar = new Button("Editar");
        btnEditar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;");
        btnEditar.setOnMouseEntered(e -> btnEditar.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnEditar.setOnMouseExited(e -> btnEditar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnEditar.setOnAction(e -> editarEstudianteSeleccionado());

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;");
        btnEliminar.setOnMouseEntered(e -> btnEliminar.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnEliminar.setOnMouseExited(e -> btnEliminar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnEliminar.setOnAction(e -> eliminarEstudiante());

        Button btnLimpiar = new Button("Limpiar");
        btnLimpiar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;");
        btnLimpiar.setOnMouseEntered(e -> btnLimpiar.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnLimpiar.setOnMouseExited(e -> btnLimpiar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnLimpiar.setOnAction(e -> limpiarCampos());

        HBox botones = new HBox(10, btnAgregar, btnEditar, btnEliminar, btnLimpiar);
        botones.setPadding(new Insets(10));
        botones.setStyle("-fx-background-color: white;");

        return botones;
    }


    private void agregarEstudiante() {
        String nombre = tfNombre.getText().trim();
        String matricula = tfMatricula.getText().trim();
        String carrera = tfCarrera.getText().trim();

        if (nombre.isEmpty() || matricula.isEmpty() || carrera.isEmpty()) {
            mostrarAlerta("Error de Validación", "Todos los campos son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        if (!tfNombre.esValido() || !tfMatricula.esValido() || !tfCarrera.esValido()) {
            mostrarAlerta("Error de Validación", "Por favor, corrija los campos marcados en rojo.", Alert.AlertType.WARNING);
            return;
        }

        boolean matriculaExiste = datos.stream()
                .anyMatch(est -> est.getMatricula().equals(matricula));

        if (matriculaExiste) {
            mostrarAlerta("Error de Validación", "La matrícula ya existe en el sistema.", Alert.AlertType.WARNING);
            return;
        }

        datos.add(new Estudiante(nombre, matricula, carrera));
        limpiarCampos();
        mostrarAlerta("Éxito", "Estudiante agregado correctamente.", Alert.AlertType.INFORMATION);
    }

    private void editarEstudianteSeleccionado() {
        Estudiante seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            editarEstudiante(seleccionado);
        } else {
            mostrarAlerta("Selección Requerida", "Por favor, seleccione un estudiante para editar.", Alert.AlertType.WARNING);
        }
    }

    private void editarEstudiante(Estudiante estudiante) {
        tfNombre.setText(estudiante.getNombre());
        tfMatricula.setText(estudiante.getMatricula());
        tfCarrera.setText(estudiante.getCarrera());
        tfMatricula.setDisable(true);

        HBox botones = (HBox) ((VBox) tabla.getParent()).getChildren().get(2);
        Button btnActualizar = new Button("Actualizar");
        btnActualizar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;");
        btnActualizar.setOnMouseEntered(e -> btnActualizar.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnActualizar.setOnMouseExited(e -> btnActualizar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnActualizar.setOnAction(e -> actualizarEstudiante(estudiante));
        botones.getChildren().set(0, btnActualizar);
    }

    private void actualizarEstudiante(Estudiante estudianteOriginal) {
        String nombre = tfNombre.getText().trim();
        String carrera = tfCarrera.getText().trim();

        if (nombre.isEmpty() || carrera.isEmpty()) {
            mostrarAlerta("Error de Validación", "Nombre y Carrera son obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        if (!tfNombre.esValido() || !tfCarrera.esValido()) {
            mostrarAlerta("Error de Validación", "Por favor, corrija los campos marcados en rojo.", Alert.AlertType.WARNING);
            return;
        }

        estudianteOriginal.setNombre(nombre);
        estudianteOriginal.setCarrera(carrera);
        tabla.refresh();
        restaurarInterfazNormal();
        limpiarCampos();
        mostrarAlerta("Éxito", "Estudiante actualizado correctamente.", Alert.AlertType.INFORMATION);
    }

    private void restaurarInterfazNormal() {
        tfMatricula.setDisable(false);
        HBox botones = (HBox) ((VBox) tabla.getParent()).getChildren().get(2);
        Button btnAgregar = new Button("Agregar");
        btnAgregar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;");
        btnAgregar.setOnMouseEntered(e -> btnAgregar.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: black; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnAgregar.setOnMouseExited(e -> btnAgregar.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-border-color: red; -fx-border-width: 2px; -fx-cursor: hand;"));
        btnAgregar.setOnAction(e -> agregarEstudiante());
        botones.getChildren().set(0, btnAgregar);
    }

    private void eliminarEstudiante() {
        Estudiante seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Eliminación");
            confirmacion.setHeaderText("Eliminar Estudiante");
            confirmacion.setContentText("¿Está seguro de que desea eliminar al estudiante: " + seleccionado.getNombre() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    datos.remove(seleccionado);
                    mostrarAlerta("Éxito", "Estudiante eliminado correctamente.", Alert.AlertType.INFORMATION);
                }
            });
        } else {
            mostrarAlerta("Selección Requerida", "Por favor, seleccione un estudiante para eliminar.", Alert.AlertType.WARNING);
        }
    }

    private void limpiarCampos() {
        tfNombre.clear();
        tfMatricula.clear();
        tfCarrera.clear();
        tfNombre.requestFocus();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo, mensaje, ButtonType.OK);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void mostrarAcercaDe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("Sistema Estudiantil J567");
        alert.setContentText("Sistema de gestión estudiantil\nVersión 1.0\nDesarrollado con JavaFX\nTema: Rojo, Negro y Blanco");
        alert.showAndWait();
    }
}



class CampoGonzalezValidado extends TextField {
    private final String tipo;
    private boolean esValido;

    CampoGonzalezValidado(String placeholder, String tipo) {
        this.tipo = tipo;
        this.esValido = false;
        setPromptText(placeholder);
        this.setOnKeyReleased(this::validar);
        setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 180px;");
    }

    private void validar(KeyEvent e) {
        switch (tipo) {
            case "texto":
                esValido = !getText().matches(".*\\d.*");
                break;
            case "matricula":
                esValido = getText().matches("\\d*");
                break;
            default:
                esValido = true;
        }
        actualizarEstilo();
    }

    private void actualizarEstilo() {
        if (esValido) {
            setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 2px; -fx-pref-width: 180px;");
        } else {
            setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: red; -fx-border-width: 2px; -fx-pref-width: 180px;");
        }
    }

    public boolean esValido() {
        return esValido;
    }

    @Override
    public void clear() {
        super.clear();
        esValido = false;
        setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1px; -fx-pref-width: 180px;");
    }
}



class Estudiante {
    private final StringProperty nombre;
    private final StringProperty matricula;
    private final StringProperty carrera;

    public Estudiante(String nombre, String matricula, String carrera) {
        this.nombre = new SimpleStringProperty(nombre);
        this.matricula = new SimpleStringProperty(matricula);
        this.carrera = new SimpleStringProperty(carrera);
    }

    public String getNombre() { return nombre.get(); }
    public String getMatricula() { return matricula.get(); }
    public String getCarrera() { return carrera.get(); }

    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setMatricula(String matricula) { this.matricula.set(matricula); }
    public void setCarrera(String carrera) { this.carrera.set(carrera); }

    public StringProperty nombreProperty() { return nombre; }
    public StringProperty matriculaProperty() { return matricula; }
    public StringProperty carreraProperty() { return carrera; }

    @Override
    public String toString() {
        return String.format("Estudiante{nombre=%s, matricula=%s, carrera=%s}",
                getNombre(), getMatricula(), getCarrera());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estudiante that = (Estudiante) obj;
        return matricula.get().equals(that.matricula.get());
    }

    @Override
    public int hashCode() {
        return matricula.get().hashCode();
    }
}