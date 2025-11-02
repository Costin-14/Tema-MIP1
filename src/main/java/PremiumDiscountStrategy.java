public class PremiumDiscountStrategy implements PricingStrategy {
    private final double discountRate;

    public PremiumDiscountStrategy(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * (1.0 - discountRate);
    }
}
