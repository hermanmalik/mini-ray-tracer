public class ThreeVector {
    private final double x;
    private final double y;
    private final double z;
    static final double eta_i = 1.f; // for snell
    static final double eta_t = 1.0;

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
    public double dot(ThreeVector other) {
        return other.x() * x + other.y() * y + other.z() * z;
    }
    public ThreeVector cross(ThreeVector other) {
        return new ThreeVector(y * other.z() - z * other.y(), z * other.x() - x * other.z(),
                x * other.y() - y * other.x());
    }
    public ThreeVector normalized() {
        return this.times(1 / this.norm());
    }

    public static ThreeVector reflect(ThreeVector I, ThreeVector N) {
        return I.minus(N.times(2.f).times(I.dot(N))); //I - N*2.f*(I*N)
    }

    public static ThreeVector refract(ThreeVector I, ThreeVector N) { // Snell's law
        double cosi = - Math.max(-1.f, Math.min(1.f, I.dot(N)));
        if (cosi < 0) {
            return refract(I, N.times(-1)); // if the ray comes from the inside the object, swap the air and the media
        }
        double eta = eta_i / eta_t;
        double k = 1 - eta*eta*(1 - cosi*cosi);
        if (k < 0) {
            return new ThreeVector(1,0,0); // total reflection, no ray to refract
        } else {
            return I.times(eta).plus(N.times(eta*cosi - Math.sqrt(k)));
        }
    }



}
