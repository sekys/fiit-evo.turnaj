import org.jenetics.CharacterChromosome;
import org.jenetics.CharacterGene;
import org.jenetics.Genotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.CharSeq;
import org.jenetics.util.Factory;

/**
 * Created by Seky on 25. 2. 2015.
 */
public final class Evo {
    private static final Mapa MAPA = new Mapa(4, 5);

    // Fitness funkcia
    private static Integer eval(CharSequence seq) {
        // Zisti pocet policok, ktore presiel
        int[] kroky = MAPA.vykonajKroky(seq);
        int pocet = 0;
        for (int a = 0; a < kroky.length; a++) {
            if (kroky[a] > 0) {
                pocet++;
            }
        }
        return pocet;
    }

    private static Integer eval(Genotype<CharacterGene> gt) {
        return eval(((CharacterChromosome) gt.getChromosome()));
    }

    public static void main(String[] args) {
        //int[] kroky = MAPA.vykonajKroky("HLHPHLDPDDPHHHPPDDDL");
        //MAPA.nakresli(kroky);

        // Nastav format genotypu
        Factory<Genotype<CharacterGene>> gtf =
            Genotype.of(new CharacterChromosome(new CharSeq("HDLP"), 20));

        // Vybuduj GA engine
        Engine<CharacterGene, Integer> engine = Engine
            .builder(Evo::eval, gtf)
            .populationSize(100)
            .maximalPhenotypeAge(1000)
            .build();

        // Vyber konecne vysledky
        final NumStats<Integer> stats = new NumStats();

        Genotype<CharacterGene> result = engine.stream()
            .limit(500)
            .peek(stats)
            .collect(EvolutionResult.toBestGenotype());

        System.out.println(stats);
        System.out.println("Hello World:\n" + result);
    }
}
