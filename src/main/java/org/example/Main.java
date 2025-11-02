import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Repositories.ClassTypeRepo classRepo = new Repositories.ClassTypeRepo();
    private static final Repositories.TrainerRepo trainerRepo = new Repositories.TrainerRepo();
    private static final Repositories.SubscriptionRepo subscriptionRepo = new Repositories.SubscriptionRepo();

    public static void main(String[] args) {
        seedSampleData();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addClassType(); break;
                case "2": addTrainer(); break;
                case "3": addSubscription(); break;
                case "4": listClassTypes(); break;
                case "5": listTrainers(); break;
                case "6": listSubscriptions(); break;
                case "7": generateReport(); break;
                case "8": assignTrainerToClass(); break;
                case "0": running = false; break;
                default: System.out.println("Opțiune invalidă."); break;
            }
            System.out.println();
        }
        System.out.println("La revedere!");
    }

    private static void printMenu() {
        System.out.println("=== FitZone+ Manager ===");
        System.out.println("1. Adaugă tip clasă");
        System.out.println("2. Adaugă antrenor");
        System.out.println("3. Adaugă abonament client");
        System.out.println("4. Afișează tipuri de clasă");
        System.out.println("5. Afișează antrenori");
        System.out.println("6. Afișează abonamente");
        System.out.println("7. Generează raport sumar");
        System.out.println("8. Asignează antrenor la alt tip de clasă");
        System.out.println("0. Ieșire");
        System.out.print("Alege opțiunea: ");
    }

    private static void addClassType() {
        System.out.print("Nume tip clasă: ");
        String name = scanner.nextLine().trim();
        System.out.print("Intensitate (USOR, MEDIU, GREU): ");
        String intensityStr = scanner.nextLine().trim().toUpperCase();
        Intensity intensity;
        try {
            intensity = Intensity.valueOf(intensityStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Intensitate invalidă."); return;
        }
        System.out.print("Preț bază: ");
        double basePrice;
        try {
            basePrice = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Preț invalid."); return;
        }
        ClassType ct = new ClassType(name, intensity, basePrice);
        classRepo.add(ct);
        System.out.println("Tip clasă adăugat: " + ct);
    }

    private static void addTrainer() {
        System.out.print("ID antrenor: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nume antrenor: ");
        String name = scanner.nextLine().trim();
        System.out.print("Nume tip clasă (existent): ");
        String className = scanner.nextLine().trim();
        Optional<ClassType> maybe = classRepo.findByName(className);
        if (!maybe.isPresent()) {
            System.out.println("Tip clasă inexistent. Adaugă mai întâi tipul de clasă.");
            return;
        }
        ClassType ct = maybe.get();
        System.out.print("Tip antrenor (1 = Angajat, 2 = Colaborator): ");
        String t = scanner.nextLine().trim();
        if ("1".equals(t)) {
            System.out.print("Salariu: ");
            double salary;
            try { salary = Double.parseDouble(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Valoare invalidă."); return; }
            Trainer tr = new EmployeeTrainer(id, name, ct, salary);
            trainerRepo.add(tr);
            System.out.println("Antrenor angajat adăugat: " + tr);
        } else if ("2".equals(t)) {
            System.out.print("Tarif pe oră: ");
            double rate;
            try { rate = Double.parseDouble(scanner.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Valoare invalidă."); return; }
            Trainer tr = new ExternalTrainer(id, name, ct, rate);
            trainerRepo.add(tr);
            System.out.println("Antrenor colaborator adăugat: " + tr);
        } else {
            System.out.println("Tip invalid.");
        }
    }

    private static void addSubscription() {
        System.out.print("ID abonament: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nume client: ");
        String client = scanner.nextLine().trim();
        System.out.print("Tip clasă: ");
        String className = scanner.nextLine().trim();
        Optional<ClassType> maybe = classRepo.findByName(className);
        if (!maybe.isPresent()) { System.out.println("Tip clasă inexistent."); return; }
        ClassType ct = maybe.get();
        System.out.print("Tier (1 = STANDARD, 2 = PREMIUM): ");
        String tierChoice = scanner.nextLine().trim();
        if ("1".equals(tierChoice)) {
            Subscription s = new Subscription(id, client, ct, Subscription.Tier.STANDARD, new NoDiscountStrategy());
            subscriptionRepo.add(s);
            System.out.println("Abonament adăugat: " + s);
        } else if ("2".equals(tierChoice)) {

            Subscription s = new Subscription(id, client, ct, Subscription.Tier.PREMIUM, new PremiumDiscountStrategy(0.20));
            subscriptionRepo.add(s);
            System.out.println("Abonament adăugat: " + s);
        } else {
            System.out.println("Opțiune invalidă.");
        }
    }

    private static void listClassTypes() {
        List<ClassType> list = classRepo.all();
        System.out.println("=== Tipuri de clasă ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i+1) + ". " + list.get(i));
        }
    }

    private static void listTrainers() {
        List<Trainer> list = trainerRepo.all();
        System.out.println("=== Antrenori ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i+1) + ". " + list.get(i));
        }
    }

    private static void listSubscriptions() {
        List<Subscription> list = subscriptionRepo.all();
        System.out.println("=== Abonamente ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i+1) + ". " + list.get(i));
        }
    }

    private static void generateReport() {
        String report = ReportGenerator.generate(classRepo, trainerRepo);
        System.out.println(report);
    }

    private static void assignTrainerToClass() {
        System.out.print("ID antrenor: ");
        String id = scanner.nextLine().trim();
        Optional<Trainer> maybe = trainerRepo.findById(id);
        if (!maybe.isPresent()) {
            System.out.println("Antrenor inexistent.");
            return;
        }
        Trainer tr = maybe.get();
        System.out.print("Nume tip clasă (existent): ");
        String className = scanner.nextLine().trim();
        Optional<ClassType> maybeCt = classRepo.findByName(className);
        if (!maybeCt.isPresent()) {
            System.out.println("Tip clasă inexistent.");
            return;
        }
        tr.setSpeciality(maybeCt.get());
        System.out.println("Antrenor actualizat: " + tr);
    }

    private static void seedSampleData() {

        ClassType yoga = new ClassType("Yoga", Intensity.USOR, 30.0);
        ClassType cross = new ClassType("CrossFit", Intensity.GREU, 50.0);
        ClassType pilates = new ClassType("Pilates", Intensity.MEDIU, 40.0);
        classRepo.add(yoga); classRepo.add(cross); classRepo.add(pilates);

        trainerRepo.add(new EmployeeTrainer("T1", "Andrei Pop", yoga, 2000.0));
        trainerRepo.add(new ExternalTrainer("T2", "Maria Ionescu", cross, 60.0));
        trainerRepo.add(new EmployeeTrainer("T3", "Cristina Radu", pilates, 1800.0));

        subscriptionRepo.add(new Subscription("S1", "Ion Client", yoga, Subscription.Tier.STANDARD, new NoDiscountStrategy()));
        subscriptionRepo.add(new Subscription("S2", "Ana Client", cross, Subscription.Tier.PREMIUM, new PremiumDiscountStrategy(0.20)));
    }
}
