/**
 * Created by Seky on 25. 2. 2015.
 */
public class Mapa {
    private final static int STARTOVACIA_POZICIA = 15;
    private final int N;
    private final int M;

    /*
    0   1   2   3   4
    5   6   7   8   9
    1O  11  12  13  14
    15  16  17  18  19
    */

    public Mapa(int n, int m) {
        N = n;
        M = m;
    }

    private boolean jeZaciatokRiadka(int pos) {
        return (pos % M) == 0;
    }

    private boolean jeKoniecRiadka(int pos) {
        return (pos % M) == M - 1;
    }

    private boolean jeZaciatokStlpca(int pos) {
        return pos < M;
    }

    private boolean jeKoniecStlpca(int pos) {
        return (pos + M) >= (N * M);
    }

    private int pohyb(int pos, char typ) {
        switch (typ) {
            case 'H': {
                return pos - M;
            }
            case 'D': {
                return pos + M;
            }
            case 'P': {
                return pos + 1;
            }
            case 'L': {
                return pos - 1;
            }
            default: {
                throw new RuntimeException();
            }
        }
    }

    private boolean jeMoznyPohyb(int pos, char typ) {
        switch (typ) {
            case 'H': {
                return !jeZaciatokStlpca(pos);
            }
            case 'D': {
                return !jeKoniecStlpca(pos);
            }
            case 'P': {
                return !jeKoniecRiadka(pos);
            }
            case 'L': {
                return !jeZaciatokRiadka(pos);
            }
            default: {
                throw new RuntimeException();
            }
        }
    }

    private boolean jeOkraj(int pos) {
        return jeZaciatokRiadka(pos) || jeKoniecRiadka(pos)
            || jeZaciatokStlpca(pos) || jeKoniecStlpca(pos);
    }

    public void nakresli(int[] kroky) {
        int rozmer = N * M;
        for (int i = 0; i < rozmer; i++) {
            String point;
            if (kroky[i] == 0) {
                point = "*";
            } else {
                point = Integer.toString(kroky[i]);
            }
            System.out.print(Integer.toString(i) + "[" + point + "]" + "\t\t");
            if (jeKoniecRiadka(i)) {
                System.out.println();
            }
        }
    }

    public int[] vykonajKroky(CharSequence seq) {
        // Vykonaj pohyb panacika po mape
        int velkostGenu = seq.length();
        int vykonanychKrokov = 1;
        int aktualnaPozicia = STARTOVACIA_POZICIA;
        int[] vykonaneKroky = new int[velkostGenu];
        vykonaneKroky[aktualnaPozicia] = vykonanychKrokov;
        for (int i = 0; i < seq.length(); i++) {
            char smerPohybu = seq.charAt(i);
            if (jeMoznyPohyb(aktualnaPozicia, smerPohybu)) {
                aktualnaPozicia = pohyb(aktualnaPozicia, smerPohybu);
                vykonanychKrokov++;
                vykonaneKroky[aktualnaPozicia] = vykonanychKrokov;
            }
        }

        return vykonaneKroky;
    }
}
