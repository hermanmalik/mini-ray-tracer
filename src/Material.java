public class Material {
    public double refractive_index = 1;
    public double[] albedo = {2,0,0,0};
    public ThreeVector diffuse_color = new ThreeVector(0, 0, 0);
    public double specular_exponent = 0;

    public Material(double refractive_index, double[] albedo, ThreeVector diffuse_color, double specular_exponent){
        this.refractive_index = refractive_index;
        this.albedo = albedo;
        this.diffuse_color = diffuse_color;
        this.specular_exponent = specular_exponent;
    }
}
