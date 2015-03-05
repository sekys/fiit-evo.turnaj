import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Seky on 4. 3. 2015.
 * Predstavuje skupinu jedincov.
 */
public final class Populacia extends ArrayList<Jedinec> {
    private static int SIZE = 60;

    public static Populacia create(Random rnd) {
        Populacia pop = new Populacia();
        for (int i = 0; i < SIZE; i++) {
            Jedinec j = new Jedinec();
            j.init(rnd);
            pop.add(j);
        }
        return pop;
    }

    private int pickBest() {
        Jedinec best = new Jedinec();
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            Jedinec actual = this.get(i);
            if (actual.getFitness() > best.getFitness()) {
                best = actual;
                index = i;
            }
        }
        return index;
    }

    public Jedinec getBest() {
        return get(pickBest());
    }

    public Jedinec getWorst() {
        return get(pickWorst());
    }

    private int pickWorst() {
        Jedinec best = new Jedinec(Integer.MAX_VALUE);
        int index = -1;
        for (int i = 0; i < this.size(); i++) {
            Jedinec actual = this.get(i);
            if (actual.getFitness() <= best.getFitness()) {
                best = actual;
                index = i;
            }
        }
        return index;
    }

    public void eval() {
        for (Jedinec j : this) {
            j.eval();
        }
    }

    private void turnajMutuj(Random rnd) {
        int b = pickWorst();
        Jedinec worst = (Jedinec) ((getBest()).clone());
        worst.mutate(rnd);
        set(b, worst);
    }

    public Populacia vykonajTurnaj(Random r, int velkostTurnaja) {
        List<Jedinec> pop = (Populacia) this.clone();
        Collections.shuffle(pop, r);
        int pocetSkupin = SIZE / velkostTurnaja;
        List<Populacia> skupiny = new ArrayList<Populacia>(pocetSkupin);
        for (int i = 0; i < pop.size(); i = i + velkostTurnaja) {
            Populacia skupina = new Populacia();
            for (int b = 0; b < velkostTurnaja; b++) {
                skupina.add(pop.get(i + b));
            }
            skupiny.add(skupina);
        }

        Populacia nova = new Populacia();
        for (Populacia p : skupiny) {
            p.turnajMutuj(r);
            nova.addAll(p);
        }
        return nova;
    }
}
