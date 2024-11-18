import java.util.ArrayList;

public class Population {
    private ArrayList<Individual> population;

    public Population(ArrayList<Individual> population) {
        this.population = population;
    }

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Individual> population) {
        this.population = population;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder().append("Population:\n");
        for (Individual ind : population) {
            str.append(ind.toString());
        }
        return str.toString();
    }
}
