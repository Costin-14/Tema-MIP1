public class Subscription {
    public enum Tier { STANDARD, PREMIUM }

    private final String id;
    private final String clientName;
    private final ClassType classType;
    private final Tier tier;
    private final PricingStrategy pricingStrategy;

    public Subscription(String id, String clientName, ClassType classType, Tier tier, PricingStrategy pricingStrategy) {
        this.id = id;
        this.clientName = clientName;
        this.classType = classType;
        this.tier = tier;
        this.pricingStrategy = pricingStrategy;
    }

    public String getId() { return id; }
    public String getClientName() { return clientName; }
    public ClassType getClassType() { return classType; }
    public Tier getTier() { return tier; }

    public double getPrice() {
        return pricingStrategy.calculatePrice(classType.getBasePrice());
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s - %s - preț: %.2f", id, clientName, classType.getName(), tier, getPrice());
    }
}
