interface CalculableJ {
    double area();
    double perimetro();
}


abstract class Figura15 implements CalculableJ {
    protected String color;

    public Figura15(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }


    public abstract String descripcion();
    public abstract double area();
    public abstract double perimetro();
}


class CirculoGonzalez extends Figura15 {
    private double radio;

    public CirculoGonzalez(String color, double radio) {
        super(color);
        this.radio = radio;
    }


    public double area() {
        return Math.PI * radio * radio;
    }
    public double area(double factor) {
        return area() * factor;
    }
    public double area(int multiplicador) {
        return area() * multiplicador;
    }


    public double perimetro() {
        return 2 * Math.PI * radio;
    }
    public double perimetro(double factor) { return perimetro() * factor; }
    public double perimetro(int multiplicador) { return perimetro() * multiplicador; }

    @Override
    public String descripcion() {
        return "Círculo de color " + color + " y radio " + radio;
    }

    public double getRadio() { return radio; }
}


class RectanguloGonzalez extends Figura15 {
    private double base;
    private double altura;

    public RectanguloGonzalez(String color, double base, double altura) {
        super(color);
        this.base = base;
        this.altura = altura;
    }


    public double area() { return base * altura; }
    public double area(double factor) { return area() * factor; }
    public double area(int multiplicador) { return area() * multiplicador; }


    public double perimetro() { return 2 * (base + altura); }
    public double perimetro(double factor) { return perimetro() * factor; }
    public double perimetro(int multiplicador) { return perimetro() * multiplicador; }

    @Override
    public String descripcion() {
        return "Rectángulo de color " + color + " base " + base + " altura " + altura;
    }

    public double getBase() { return base; }
    public double getAltura() { return altura; }
}


class TrianguloGonzalez extends Figura15 {
    private double lado1, lado2, lado3;

    public TrianguloGonzalez(String color, double l1, double l2, double l3) {
        super(color);
        this.lado1 = l1; this.lado2 = l2; this.lado3 = l3;
    }

    public double area() {
        double s = (lado1 + lado2 + lado3)/2;
        return Math.sqrt(s*(s-lado1)*(s-lado2)*(s-lado3));
    }
    public double area(double factor) { return area() * factor; }
    public double area(int multiplicador) { return area() * multiplicador; }


    public double perimetro() { return lado1 + lado2 + lado3; }
    public double perimetro(double factor) { return perimetro() * factor; }
    public double perimetro(int multiplicador) { return perimetro() * multiplicador; }

    @Override
    public String descripcion() {
        return "Triángulo de color " + color + " lados: " + lado1 + ", " + lado2 + ", " + lado3;
    }

    public double[] getLados() { return new double[]{lado1, lado2, lado3}; }
}


class CalculadoraGeometrica7567 {
    public void mostrarArea(Figura15 f) {
        System.out.println(f.descripcion() + " | Área: " + f.area());
    }

    public void mostrarPerimetro(Figura15 f) {
        System.out.println(f.descripcion() + " | Perímetro: " + f.perimetro());
    }

    public void procesarArray(Figura15[] figuras) {
        for(Figura15 f: figuras) {
            mostrarArea(f);
            mostrarPerimetro(f);


            if(f instanceof CirculoGonzalez) {
                CirculoGonzalez c = (CirculoGonzalez) f;
                System.out.println("Radio del círculo: " + c.getRadio());
            } else if(f instanceof RectanguloGonzalez) {
                RectanguloGonzalez r = (RectanguloGonzalez) f;
                System.out.println("Base y altura del rectángulo: " + r.getBase() + ", " + r.getAltura());
            } else if(f instanceof TrianguloGonzalez) {
                TrianguloGonzalez t = (TrianguloGonzalez) f;
                System.out.println("Lados del triángulo: " + java.util.Arrays.toString(t.getLados()));
            }
            System.out.println("---");
        }
    }
}


public class Practica5 {
    public static void main(String[] args) {
        Figura15[] figuras = new Figura15[] {
                new CirculoGonzalez("Rojo", 9),
                new RectanguloGonzalez("Azul", 1, 6),
                new TrianguloGonzalez("Verde", 3,4,5)
        };

        CalculadoraGeometrica7567 calc = new CalculadoraGeometrica7567();
        calc.procesarArray(figuras);
    }
}