public class ThreeVector {
    private final double x;
    private final double y;
    private final double z;

    public ThreeVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public ThreeVector() {
        this(0, 0, 0);
    }

    // access coordinates and norm
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    public double z() {
        return z;
    }
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    // arithmetic and normalization. returns new vector instead of modifying this one.
    public ThreeVector plus(ThreeVector other) {
        return new ThreeVector(x + other.x(), y + other.y(), z + other.z());
    }
    public ThreeVector times(double scalar) {
        return new ThreeVector(scalar * x, scalar * y, scalar * z);
    }
    public ThreeVector minus(ThreeVector other) {
        return new ThreeVector(x - other.x(), y - other.y(), z - other.z());
    }
    public ThreeVector pointwiseTimes(ThreeVector other) {
        return new ThreeVector(other.x() * x, other.y() * y, other.z() * z);
    }
    public ThreeVector cross(ThreeVector other) {
        return new ThreeVector(y * other.z() - z * other.y(), z * other.x() - x * other.z(),
                x * other.y() - y * other.x());
    }
    public ThreeVector normalized() {
        return this.times(1 / this.norm());
    }



}
