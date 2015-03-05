import java.util.Random;

/**
 * Created by Seky on 25. 2. 2015.
 */
public final class Pokus {
    private XLS xls;
    private Random rnd = new Random();

    private void vykonajPokus(int pokus, int krokov, int velkostTurnaja) {
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

    public void vykonajPokusy() {
        //Zapis pocet riadkov
        xls = new XLS("graph.xls", 500, 101);
        int krokov = 500;
        for (int krok = 0; krok < krokov; krok++) {
            xls.setCell(krok, 0, krok);
        }
        // Zapis do stlpcov pokusy
        for (int i = 0; i < 100; i++) {
            vykonajPokus(i, 500, 2);
        }
        xls.write();

        xls = new XLS("graph2.xls", 500, 101);
        krokov = 500;
        for (int krok = 0; krok < krokov; krok++) {
            xls.setCell(krok, 0, krok);
        }
        // Zapis do stlpcov pokusy
        for (int i = 0; i < 100; i++) {
            vykonajPokus(i, 500, 3);
        }
        xls.write();

        xls = new XLS("graph3.xls", 500, 101);
        krokov = 500;
        for (int krok = 0; krok < krokov; krok++) {
            xls.setCell(krok, 0, krok);
        }
        // Zapis do stlpcov pokusy
        for (int i = 0; i < 100; i++) {
            vykonajPokus(i, 500, 5);
        }
        xls.write();



        xls = new XLS("graph4.xls", 500, 101);
        krokov = 500;
        for (int krok = 0; krok < krokov; krok++) {
            xls.setCell(krok, 0, krok);
        }
        // Zapis do stlpcov pokusy
        for (int i = 0; i < 100; i++) {
            vykonajPokus(i, 500, 10);
        }
        xls.write();
    }


    public static void main(String[] args) {
        Pokus g = new Pokus();
        g.vykonajPokusy();
    }

}
