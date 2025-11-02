public abstract class Trainer {
    private final String id;
    private final String name;
    private ClassType speciality;

    public Trainer(String id, String name, ClassType speciality) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public ClassType getSpeciality() { return speciality; }
    public void setSpeciality(ClassType speciality) { this.speciality = speciality; }

    public abstract String getTrainerTypeInfo();

    @Override
    public String toString() {
        return String.format("[%s] %s - %s - %s", id, name, getTrainerTypeInfo(), speciality);
    }
}
