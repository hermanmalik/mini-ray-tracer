import java.util.ArrayList;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int width  = 1024;
        int height = 768;
        double fov = 1.05; // 60 degrees field of view in radians
        ThreeVector[] framebuffer = new ThreeVector[width*height];
        // actual rendering loop
        IntStream.range(0, width * height).parallel().forEach(pix -> {
            double dir_x = (pix % width + 0.5) - width / 2.;
            double dir_y = -((double) pix / width + 0.5) + height / 2.; // this flips the image at the same time
            double dir_z = -height / (2. * Math.tan(fov / 2.));
            framebuffer[pix] = Scene.cast_ray(new ThreeVector(0, 0, 0), new ThreeVector(dir_x, dir_y, dir_z).normalized(), 0);
        });

        std::ofstream ofs("./out.ppm", std::ios::binary);
        ofs << "P6\n" << width << " " << height << "\n255\n";
        for (ThreeVector color : framebuffer) {
            double max = Math.max(1.f, Math.max(color.x(), Math.max(color.y(), color.z())));
            for (int chan : {0,1,2})
                ofs << (char)(255 *  color[chan]/max);
        }
    }
}