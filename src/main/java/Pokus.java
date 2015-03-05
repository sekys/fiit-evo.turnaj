import java.util.Random;

/**
 * Created by Seky on 25. 2. 2015.
 */
public final class Pokus {
    private XLS xls;
    private Random rnd = new Random();

    private void vykonajGA(int pokus, int krokov, int velkostTurnaja) {
        int krok = 0;
        Jedinec best = null;
        Populacia populacia = Populacia.create(rnd);
        for (krok = 0; krok < krokov; krok++) {
            populacia.eval();
            best = populacia.getBest();
            xls.setCell(krok, pokus + 1, best.getFitness());
            if (best.findSolution()) {
                break;
            }
            Populacia novaPopulacia = populacia.vykonajTurnaj(rnd, velkostTurnaja);
            populacia = novaPopulacia;
        }

        // Zapis zvysne riadky s najlepsou fitness
        for (; krok < krokov; krok++) {
            xls.setCell(krok, pokus + 1, best.getFitness());
        }
    }

    public void vykonajPokus(Integer skupiva) {
        //Zapis pocet riadkov
        int krokov = 1000;
        xls = new XLS("graph" + skupiva + ".xls", krokov, 101);
        for (int krok = 0; krok < krokov; krok++) {
            xls.setCell(krok, 0, krok);
        }
        // Zapis do stlpcov pokusy
        for (int i = 0; i < 100; i++) {
            vykonajGA(i, krokov, skupiva);
        }
        xls.write();
    }

    public void vykonajPokusy() {
        vykonajPokus(2);
        vykonajPokus(3);
        vykonajPokus(5);
        vykonajPokus(10);
    }


    public static void main(String[] args) {
        Pokus g = new Pokus();
        g.vykonajPokusy();
    }

}
