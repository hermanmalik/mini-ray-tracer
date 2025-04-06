public class Scene {

    static Material ivory = new Material(1.0, new double[]{0.9, 0.5, 0.1, 0}, new ThreeVector(0.4, 0.4, 0.3),   50);
    static Material glass = new Material(1.5, new double[]{0, 0.9, 0.1, 0.8}, new ThreeVector(0.6, 0.7, 0.8),   125);
    static Material red_rubber = new Material(1.0, new double[]{1.4, 0.3, 0, 0}, new ThreeVector(0.3, 0.1, 0.1),   10);
    static Material mirror = new Material(1.0, new double[]{0, 16, 0.8, 0}, new ThreeVector(1.0, 1.0, 1.0),   1425);

    static Sphere[] spheres = {
            new Sphere(new ThreeVector(-3, 0, -16), 2, ivory),
            new Sphere(new ThreeVector(-1, -1.5, -12), 2, glass),
            new Sphere(new ThreeVector(-1.5, -0.5, -18), 3, red_rubber),
            new Sphere(new ThreeVector(7, 5, -18), 4, mirror),
    };

    static ThreeVector[] lights = {
            new ThreeVector(-20, 20, 20),
            new ThreeVector(30, 50, -25),
            new ThreeVector(30, 20, 30)
    };


    static double ray_sphere_intersect(ThreeVector orig, ThreeVector dir, Sphere s) { // returns -1 if no intersection
        ThreeVector L = s.center.minus(orig);
        double tca = L.dot(dir);
        double d2 = L.dot(L) - tca*tca;
        if (d2 > s.radius * s.radius) return -1;
        double thc = Math.sqrt(s.radius * s.radius - d2);
        double t0 = tca - thc, t1 = tca + thc;
        if (t0 > .001) return t0;  // offset the original point by .001 to avoid occlusion by the object itself
        if (t1 > .001) return t1;
        return -1;
    }

    private static class Quartet {
        public Boolean bool;
        public ThreeVector vec1;
        public ThreeVector vec2;
        public Material mat;
        Quartet(Boolean b, ThreeVector v1, ThreeVector v2, Material m) {
            bool = b;
            vec1 = v1;
            vec2 = v2;
            mat = m;
        }
    }

    static Quartet scene_intersect(ThreeVector orig, ThreeVector dir) {
        ThreeVector pt;
        ThreeVector N;
        Material material;
        double nearest_dist = 1e10;

        if (Math.abs(dir.y()) > .001) { // intersect the ray with the checkerboard, avoid division by zero
            double d = -(orig.y() + 4) / dir.y(); // the checkerboard plane has equation y = -4
            ThreeVector p = orig.plus(dir.times(d));
            if (d > .001 && d < nearest_dist && Math.abs(p.x()) < 10 && p.z() <- 10 && p.z() > -30) {
                nearest_dist = d;
                pt = p;
                N = new ThreeVector(0, 1, 0);
                if ((Math.floor(.5 * pt.x() + 1000) + Math.floor(.5 * pt.z())) == 1) { // originally was (expr) & 1, did I break it?
                    material.diffuse_color = new ThreeVector(.3, .3, .3);
                } else {
                    material.diffuse_color = new ThreeVector(.3, .2, .1);
                }
            }
        }

        for (Sphere s : spheres) { // intersect the ray with all spheres
            double dist = ray_sphere_intersect(orig, dir, s);
            if (dist < 0 || dist > nearest_dist) continue;
            nearest_dist = dist;
            pt = orig.plus(dir.times(nearest_dist));
            N = (pt.minus(s.center)).normalized();
            material = s.material;
        }
        return new Quartet(nearest_dist < 1000, pt, N, material);
    }

    static ThreeVector cast_ray(ThreeVector orig, ThreeVector dir, int depth) {
        // int depth = 0;
        Quartet auto = scene_intersect(orig, dir);
        boolean hit = auto.bool;
        ThreeVector point = auto.vec1;
        ThreeVector N = auto.vec2;
        Material material = auto.mat;

        if (depth > 4 || !hit) {
            return new ThreeVector(0.2, 0.7, 0.8); // background color
        }
        ThreeVector reflect_dir = ThreeVector.reflect(dir, N).normalized();
        ThreeVector refract_dir = ThreeVector.refract(dir, N, material.refractive_index).normalized(); // should change eta_t?
        ThreeVector reflect_color = cast_ray(point, reflect_dir, depth + 1);
        ThreeVector refract_color = cast_ray(point, refract_dir, depth + 1);

        double diffuse_light_intensity = 0, specular_light_intensity = 0;
        for (ThreeVector light : lights) { // checking if the point lies in the shadow of the light
            ThreeVector light_dir = (light.minus(point)).normalized();
            Quartet auto2 = scene_intersect(point, light_dir);
            boolean hit2 = auto2.bool;
            ThreeVector shadow_pt = auto2.vec1;
            if (hit2 && (shadow_pt.minus(point)).norm() < (light.minus(point)).norm()) continue;
            diffuse_light_intensity  += Math.max(0.f, light_dir.dot(N));
            specular_light_intensity += Math.pow(Math.max(0.f, -ThreeVector.reflect(light_dir.times(-1), N).dot(dir), material.specular_exponent);
        }
        return material.diffuse_color.times(diffuse_light_intensity).times(material.albedo[0])
                .plus(new ThreeVector(1., 1., 1.).times(specular_light_intensity).times(material.albedo[1]))
                .plus(reflect_color.times(material.albedo[2]))
                .plus(refract_color.times(material.albedo[3]));
    }
}
