
public class Main {
    public static void main(String[] args) {

        Material ivory = new Material(1.0, new double[]{0.9, 0.5, 0.1, 0}, new ThreeVector(0.4, 0.4, 0.3),   50);
        Material glass = new Material(1.5, new double[]{0, 0.9, 0.1, 0.8}, new ThreeVector(0.6, 0.7, 0.8),   125);
        Material red_rubber = new Material(1.0, new double[]{1.4, 0.3, 0, 0}, new ThreeVector(0.3, 0.1, 0.1),   10);
        Material mirror = new Material(1.0, new double[]{0, 16, 0.8, 0}, new ThreeVector(1.0, 1.0, 1.0),   1425);

        Sphere[] spheres = {
            new Sphere(new ThreeVector(-3, 0, -16), 2, ivory),
            new Sphere(new ThreeVector(-1, -1.5, -12), 2, glass),
            new Sphere(new ThreeVector(-1.5, -0.5, -18), 3, red_rubber),
            new Sphere(new ThreeVector(7, 5, -18), 4, mirror),
        };

        ThreeVector[] lights = {
            new ThreeVector(-20, 20, 20),
            new ThreeVector(30, 50, -25),
            new ThreeVector(30, 20, 30)
        };

    }
}