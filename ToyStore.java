import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;

class Toy {
    int id;
    String name;
    int weight;

    public Toy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Toy{id=" + id + ", name='" + name + "', weight=" + weight + '}';
    }
}

public class ToyStore {
    private PriorityQueue<Toy> toyQueue;
    private Random random;

    public ToyStore() {
        toyQueue = new PriorityQueue<>((t1, t2) -> t2.weight - t1.weight);
        random = new Random();
    }

    public void addToy(int id, String name, int weight) {
        Toy toy = new Toy(id, name, weight);
        toyQueue.add(toy);
    }

    public Toy getRandomToy() {
        int totalWeight = toyQueue.stream().mapToInt(toy -> toy.weight).sum();
        int randomValue = random.nextInt(totalWeight);

        for (Toy toy : toyQueue) {
            randomValue -= toy.weight;
            if (randomValue < 0) {
                return toy;
            }
        }
        return null;  // Should never reach here
    }

    public void writeResultsToFile(String filename, int numberOfResults) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (int i = 0; i < numberOfResults; i++) {
                writer.write(getRandomToy().toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyStore store = new ToyStore();
        store.addToy(1, "Teddy Bear", 5);
        store.addToy(2, "Toy Car", 3);
        store.addToy(3, "Doll", 2);

        store.writeResultsToFile("toy_results.txt", 10);
    }
}