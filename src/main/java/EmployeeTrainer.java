public class EmployeeTrainer extends Trainer {
    private double salary;

    public EmployeeTrainer(String id, String name, ClassType speciality, double salary) {
        super(id, name, speciality);
        this.salary = salary;
    }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String getTrainerTypeInfo() {
        return "Angajat (salariu: " + String.format("%.2f", salary) + ")";
    }
}
