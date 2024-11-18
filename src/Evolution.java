import java.util.ArrayList;
import java.util.Random;

public class Evolution {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 100;
    private static final double MUTATION_RATE = 0.005;

    public static void main(String[] args) {

        Population population = generatePopulation();

        int generation = 0;

        while (generation < MAX_GENERATIONS) {
            System.out.println("Generation " + generation);

            // Evaluate population fitness
            ArrayList<Individual> ranked = evaluateFitness(population);

            // Get best individual
            Individual best = ranked.get(0);
            System.out.print("Best Individual: " + best);
            System.out.println(" Fitness: " + fitness(best) + "\n");

            // Check for termination condition
            if (fitness(best) == 7) { // 7 is the theoretical maximum for 30 genes
                break;
            }

            // Generate next population
            population = evolvePopulation(ranked);

            generation++;
        }
    }

    // Generate new population
    public static Population generatePopulation(){
        ArrayList<Individual> individualsCollection = new ArrayList<>(POPULATION_SIZE);

        for (int i = 0; i < POPULATION_SIZE; i++) {
            individualsCollection.add(new Individual());
        }

        return new Population(individualsCollection);
    }

    // Evaluate fitness of all individuals and rank them
    public static ArrayList<Individual> evaluateFitness(Population population) {
        ArrayList<Individual> ranked = new ArrayList<>(population.getPopulation());
        ranked.sort((ind1, ind2) -> fitness(ind2) - fitness(ind1));
        return ranked;
    }

    // Fitness function: Counts sequences of exactly three consecutive 1s
    public static int fitness(Individual individual) {
        ArrayList<Gene> genes = individual.getIndividual();
        int count = 0;

        for (int i = 0; i < genes.size() - 2; i++) {
            // Check for exactly 3 consecutive 1s
            if (genes.get(i).getN() == 1 && genes.get(i + 1).getN() == 1 && genes.get(i + 2).getN() == 1) {

                // Ensure it's not part of a longer sequence of 1s
                if ((i == 0 || genes.get(i - 1).getN() == 0) && (i + 3 >= genes.size() || genes.get(i + 3).getN() == 0)) { // Next gene is 0 or out of bounds or Previous gene is 0 or out of bounds
                    count++;
                    i += 2; // Move past the counted sequence
                }
            }
        }

        return count;
    }

    // Create a new population by selection, crossover, and mutation
    public static Population evolvePopulation(ArrayList<Individual> ranked) {
        ArrayList<Individual> newPopulation = new ArrayList<>();

        Random random = new Random();

        // Add elites directly to the new population (elitism)
        newPopulation.add(ranked.get(0));
        newPopulation.add(ranked.get(1));

        // Create offspring using crossover and mutation
        while (newPopulation.size() < POPULATION_SIZE) {
            Individual parent1 = ranked.get(random.nextInt(ranked.size() / 2)); // Top 50% of population
            Individual parent2 = ranked.get(random.nextInt(ranked.size() / 2));
            Individual child = crossover(parent1, parent2);
            mutate(child);
            newPopulation.add(child);
        }

        return new Population(newPopulation);
    }

    // Crossover: Single-point crossover
    public static Individual crossover(Individual parent1, Individual parent2) {
        ArrayList<Gene> childGenes = new ArrayList<>();
        Random random = new Random();
        int crossoverPoint = random.nextInt(30);

        for (int i = 0; i < crossoverPoint; i++) {
            childGenes.add(parent1.getIndividual().get(i));
        }
        for (int i = crossoverPoint; i < 30; i++) {
            childGenes.add(parent2.getIndividual().get(i));
        }

        Individual child = new Individual();
        child.setIndividual(childGenes);
        return child;
    }

    // Mutation: Randomly flips a gene with a small probability
    public static void mutate(Individual individual) {
        Random random = new Random();
        ArrayList<Gene> genes = individual.getIndividual();

        for (Gene gene : genes) {
            if (random.nextDouble() < MUTATION_RATE) {
                gene.setN(1 - gene.getN()); // Flip 0 to 1 or 1 to 0
            }
        }
    }
}
