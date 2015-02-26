import org.jenetics.Phenotype;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.stat.DoubleMomentStatistics;

import static java.lang.String.format;

/**
 * Created by Seky on 26. 2. 2015.
 */
public final class NumStats<N extends Number & Comparable<? super N>>
    extends EvolutionStatistics<N, DoubleMomentStatistics> {
    private XLS xls = new XLS("graf.xls");

    public NumStats() {
        _fitness = new DoubleMomentStatistics();
    }

    @Override
    public void accept(final EvolutionResult<?, N> result) {
        super.accept(result);
        xls.addRow((int) result.getGeneration(), result.getBestFitness().doubleValue());
    }

    @Override void accept(final Phenotype<?, N> pt, final long generation) {
        super.accept(pt, generation);
        double fitness = pt.getFitness().doubleValue();
        _fitness.accept(pt.getFitness().doubleValue());
    }

    @Override
    public String toString() {
        xls.write();
        return super.toString() +
            "+---------------------------------------------------------------------------+\n" +
            "|  Population statistics                                                    |\n" +
            "+---------------------------------------------------------------------------+\n" +
            format(cpattern, "Age:", p(_age)) +
            format(cpattern, "Fitness:", "") +
            format(spattern, "min  =", d(_fitness.getMin())) +
            format(spattern, "max  =", d(_fitness.getMax())) +
            format(spattern, "mean =", d(_fitness.getMean())) +
            format(spattern, "var  =", d(_fitness.getVariance())) +
            "+---------------------------------------------------------------------------+";
    }

    private static String d(final double value) {
        return format("%3.12f", value);
    }
}

