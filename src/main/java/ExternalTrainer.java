public class ExternalTrainer extends Trainer {
    private double hourlyRate;

    public ExternalTrainer(String id, String name, ClassType speciality, double hourlyRate) {
        super(id, name, speciality);
        this.hourlyRate = hourlyRate;
    }

    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }

    @Override
    public String getTrainerTypeInfo() {
        return "Colaborator extern (tarif/oră: " + String.format("%.2f", hourlyRate) + ")";
    }
}
