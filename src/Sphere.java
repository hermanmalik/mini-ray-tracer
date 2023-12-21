public class Sphere {
    public ThreeVector center;
    public double radius;
    public Material material;

    public Sphere(ThreeVector center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }
}
