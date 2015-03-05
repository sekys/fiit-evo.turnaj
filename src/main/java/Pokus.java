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
            if (best.findSolution()) {
                xls.setCell(pokus, 1, krok);
                return;
            }
            Populacia novaPopulacia = populacia.vykonajTurnaj(rnd, velkostTurnaja);
            populacia = novaPopulacia;
        }
        xls.setCell(pokus, 1, 10001);
    }

    public void vykonajPokus(Integer skupiva) {
        //Zapis pocet riadkov
        int krokov = 10000;
        int pokusov = 100;
        xls = new XLS("graph" + skupiva + ".xls", pokusov, 2);
        // Zapis do stlpcov pokusy
        for (int i = 0; i < pokusov; i++) {
            xls.setCell(i, 0, i);
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
