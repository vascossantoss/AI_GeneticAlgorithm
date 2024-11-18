import java.util.ArrayList;
import java.util.Random;

public class Individual {
    private ArrayList<Gene> individual;

    public Individual() {
        this.individual = new ArrayList<>(30);

        // Add 30 genes to individual
        for (int i = 0; i <= 29;i++) {
            Gene gene = new Gene(generateBinary());
            individual.add(gene);
        }
    }

    public ArrayList<Gene> getIndividual() {
        return individual;
    }

    public void setIndividual(ArrayList<Gene> individual) {
        this.individual = individual;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder().append("[ ");
        for (Gene g : individual){
            str.append(g.getN()).append(" ");
        }
        str.append("]");
        return str.toString();
    }

    private static int generateBinary() {
        Random random = new Random();
        return random.nextInt(2);
    }
}
