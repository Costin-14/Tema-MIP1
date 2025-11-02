import java.util.*;
import java.util.stream.Collectors;

public class Repositories {
    public static class ClassTypeRepo {
        private final List<ClassType> items = new ArrayList<>();
        public void add(ClassType c) { items.add(c); }
        public List<ClassType> all() { return Collections.unmodifiableList(items); }
        public Optional<ClassType> findByName(String name) {
            return items.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst();
        }
    }

    public static class TrainerRepo {
        private final List<Trainer> items = new ArrayList<>();
        public void add(Trainer t) { items.add(t); }
        public List<Trainer> all() { return Collections.unmodifiableList(items); }
        public List<Trainer> findByClassName(String className) {
            return items.stream()
                    .filter(t -> t.getSpeciality() != null && t.getSpeciality().getName().equalsIgnoreCase(className))
                    .collect(Collectors.toList());
        }
        public Optional<Trainer> findById(String id) {
            return items.stream().filter(t -> t.getId().equalsIgnoreCase(id)).findFirst();
        }
    }

    public static class SubscriptionRepo {
        private final List<Subscription> items = new ArrayList<>();
        public void add(Subscription s) { items.add(s); }
        public List<Subscription> all() { return Collections.unmodifiableList(items); }
    }
}
