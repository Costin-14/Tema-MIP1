import java.util.List;

public class ReportGenerator {
    public static String generate(Repositories.ClassTypeRepo classRepo, Repositories.TrainerRepo trainerRepo) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Raport FitZone+ ===\n");
        List<ClassType> classes = classRepo.all();
        if (classes.isEmpty()) {
            sb.append("Nu exista tipuri de antrenamente.\n");
            return sb.toString();
        }
        for (ClassType ct : classes) {
            sb.append(String.format("- %s (intensitate: %s, preț bază: %.2f)\n", ct.getName(), ct.getIntensity(), ct.getBasePrice()));
            List<Trainer> trainers = trainerRepo.findByClassName(ct.getName());
            if (trainers.isEmpty()) {
                sb.append("   * Niciun antrenor disponibil pentru acest tip.\n");
            } else {
                for (Trainer tr : trainers) {
                    sb.append("   * " + tr.getName() + " - " + tr.getTrainerTypeInfo() + "\n");
                }
            }
        }
        return sb.toString();
    }
}
