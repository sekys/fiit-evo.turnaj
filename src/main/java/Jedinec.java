import java.util.Random;

/**
 * Created by Seky on 4. 3. 2015.
 */
public final class Jedinec implements Comparable<Jedinec> {
    private static final String TARGET = "Ahoj, ako sa mas?";
    private static final String CHAR_SET = "Ahoj, ako sa mas?";
    private int fitness;
    private String chromozon;

    public Jedinec() {
        this(-1);
    }

    public Jedinec(int fitness) {
        this.fitness = fitness;
    }

    /**
     * @return the fitness
     */
    public int getFitness() {
        return fitness;
    }

    public void eval() {
        fitness = 0;
        for (int a = 0; a < TARGET.length(); a++) {
            if (chromozon.charAt(a) == TARGET.charAt(a)) {
                fitness++;
            }
        }
    }

    public boolean findSolution() {
        return fitness == TARGET.length();
    }

    public void init(Random rnd) {
        int size = TARGET.length();
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            char novy = CHAR_SET.charAt(rnd.nextInt(CHAR_SET.length()));
            sb.append(novy);
        }
        chromozon = sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Jedinec{");
        sb.append("chromozon='").append(chromozon).append('\'');
        sb.append(", fitness=").append(fitness);
        sb.append('}');
        return sb.toString();
    }

    @Override public int compareTo(Jedinec o) {
        return Integer.compare(o.fitness, fitness);
    }

    public void mutate(Random rnd) {
        int position = rnd.nextInt(chromozon.length());
        char novy = CHAR_SET.charAt(rnd.nextInt(CHAR_SET.length()));
        StringBuilder sb = new StringBuilder(chromozon);
        sb.setCharAt(position, novy);
        chromozon = sb.toString();
    }

    @Override
    public Object clone() {
        Jedinec j = new Jedinec();
        j.fitness = this.fitness;
        j.chromozon = this.chromozon;
        return j;
    }
}
