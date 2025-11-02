public class ClassType {
    private final String name;
    private final Intensity intensity;
    private double basePrice;

    public ClassType(String name, Intensity intensity, double basePrice) {
        this.name = name;
        this.intensity = intensity;
        this.basePrice = basePrice;
    }

    public String getName() { return name; }
    public Intensity getIntensity() { return intensity; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    @Override
    public String toString() {
        return String.format("%s (intensitate: %s, preț bază: %.2f)", name, intensity, basePrice);
    }
}
