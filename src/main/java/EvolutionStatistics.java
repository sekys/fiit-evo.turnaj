import org.jenetics.Phenotype;
import org.jenetics.engine.EvolutionDurations;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.stat.DoubleMomentStatistics;
import org.jenetics.stat.IntMomentStatistics;
import org.jenetics.stat.LongMomentStatistics;
import org.jenetics.stat.MinMax;

import java.text.NumberFormat;
import java.time.Duration;
import java.util.function.Consumer;

import static java.lang.String.format;

/**
 * Created by Seky on 25. 2. 2015.
 */
public abstract class EvolutionStatistics<
    C extends Comparable<? super C>,
    FitnessStatistics
    >
    implements Consumer<EvolutionResult<?, C>>
{

    // The duration statistics values.
    protected final DoubleMomentStatistics
        _selectionDuration = new DoubleMomentStatistics();
    protected final DoubleMomentStatistics
        _alterDuration = new DoubleMomentStatistics();
    protected final DoubleMomentStatistics
        _evaluationDuration = new DoubleMomentStatistics();
    protected final DoubleMomentStatistics
        _evolveDuration = new DoubleMomentStatistics();

    // The evolution statistics values.
    protected final IntMomentStatistics _killed = new IntMomentStatistics();
    protected final IntMomentStatistics _invalids = new IntMomentStatistics();
    protected final IntMomentStatistics _altered = new IntMomentStatistics();

    // The population statistics values.
    protected final LongMomentStatistics _age = new LongMomentStatistics();
    protected FitnessStatistics _fitness = null;

    EvolutionStatistics() {
    }

    @Override
    public void accept(final EvolutionResult<?, C> result) {
        accept(result.getDurations());

        _killed.accept(result.getKillCount());
        _invalids.accept(result.getInvalidCount());
        _altered.accept(result.getAlterCount());

        result.getPopulation()
            .forEach(pt -> accept(pt, result.getGeneration()));
    }

    void accept(final Phenotype<?, C> pt, final long generation) {
        _age.accept(pt.getAge(generation));
    }

    // Calculate duration statistics
    private void accept(final EvolutionDurations durations) {
        final double selection =
            toSeconds(durations.getOffspringSelectionDuration()) +
                toSeconds(durations.getSurvivorsSelectionDuration());
        final double alter =
            toSeconds(durations.getOffspringAlterDuration()) +
                toSeconds(durations.getOffspringFilterDuration());

        _selectionDuration.accept(selection);
        _alterDuration.accept(alter);
        _evaluationDuration
            .accept(toSeconds(durations.getEvaluationDuration()));
        _evolveDuration
            .accept(toSeconds(durations.getEvolveDuration()));
    }

    private static double toSeconds(final Duration duration) {
        return duration.toNanos()/1_000_000_000.0;
    }

	/* *************************************************************************
	 * Evaluation timing statistics
	 * ************************************************************************/

    /**
     * Return the duration statistics needed for selecting the population, in
     * seconds.
     *
     * @return the duration statistics needed for selecting the population
     */
    public DoubleMomentStatistics getSelectionDuration() {
        return _selectionDuration;
    }

    /**
     * Return the duration statistics needed for altering the population, in
     * seconds.
     *
     * @return the duration statistics needed for altering the population
     */
    public DoubleMomentStatistics getAlterDuration() {
        return _alterDuration;
    }

    /**
     * Return the duration statistics needed for evaluating the fitness function
     * of the new individuals, in seconds.
     *
     * @return the duration statistics needed for evaluating the fitness
     *         function of the new individuals
     */
    public DoubleMomentStatistics getEvaluationDuration() {
        return _evaluationDuration;
    }

    /**
     * Return the duration statistics needed for the whole evolve step, in
     * seconds.
     *
     * @return the duration statistics needed for the whole evolve step
     */
    public DoubleMomentStatistics getEvolveDuration() {
        return _evolveDuration;
    }



	/* *************************************************************************
	 * Evolution statistics
	 * ************************************************************************/

    /**
     * Return the statistics about the killed individuals during the evolution
     * process.
     *
     * @return killed individual statistics
     */
    public IntMomentStatistics getKilled() {
        return _killed;
    }

    /**
     * Return the statistics about the invalid individuals during the evolution
     * process.
     *
     * @return invalid individual statistics
     */
    public IntMomentStatistics getInvalids() {
        return _invalids;
    }

    /**
     * Return the statistics about the altered individuals during the evolution
     * process.
     *
     * @return altered individual statistics
     */
    public IntMomentStatistics getAltered() {
        return _altered;
    }

    /**
     * Return the statistics about the individuals age.
     *
     * @return individual age statistics
     */
    public LongMomentStatistics getPhenotypeAge() {
        return _age;
    }

    /**
     * Return the minimal and maximal fitness.
     *
     * @return minimal and maximal fitness
     */
    public FitnessStatistics getFitness() {
        return _fitness;
    }

    final String cpattern = "| %22s %-51s|\n";
    final String spattern = "| %27s %-46s|\n";

    @Override
    public String toString() {
        return
            "+---------------------------------------------------------------------------+\n" +
                "|  Time statistics                                                          |\n" +
                "+---------------------------------------------------------------------------+\n" +
                format(cpattern, "Selection:", d(_selectionDuration)) +
                format(cpattern, "Altering:", d(_alterDuration)) +
                format(cpattern, "Fitness calculation:", d(_evaluationDuration)) +
                format(cpattern, "Overall execution:", d(_evolveDuration)) +
                "+---------------------------------------------------------------------------+\n" +
                "|  Evolution statistics                                                     |\n" +
                "+---------------------------------------------------------------------------+\n" +
                format(cpattern, "Generations:", i(_altered.getCount())) +
                format(cpattern, "Altered:", i(_altered)) +
                format(cpattern, "Killed:", i(_killed)) +
                format(cpattern, "Invalids:", i(_invalids));
    }

    private static String d(final DoubleMomentStatistics statistics) {
        return format(
            "sum=%3.12f s; mean=%3.12f s",
            statistics.getSum(), statistics.getMean()
        );
    }

    private static String i(final IntMomentStatistics statistics) {
        final NumberFormat nf = NumberFormat.getIntegerInstance();
        return format(
            "sum=%s; mean=%6.9f",
            nf.format(statistics.getSum()), statistics.getMean()
        );
    }

    private static String i(final long value) {
        final NumberFormat nf = NumberFormat.getIntegerInstance();
        return nf.format(value);
    }

    private static String p(final IntMomentStatistics statistics) {
        final NumberFormat nf = NumberFormat.getIntegerInstance();
        return format(
            "max=%s; mean=%6.6f; var=%6.6f",
            nf.format(statistics.getMax()),
            statistics.getMean(),
            statistics.getVariance()
        );
    }

    protected static String p(final LongMomentStatistics statistics) {
        final NumberFormat nf = NumberFormat.getIntegerInstance();
        return format(
            "max=%s; mean=%6.6f; var=%6.6f",
            nf.format(statistics.getMax()),
            statistics.getMean(),
            statistics.getVariance()
        );
    }

    private static final class Comp<
        C extends Comparable<? super C>
        >
        extends EvolutionStatistics<C, MinMax<C>>
    {
        private Comp() {
            _fitness = MinMax.of();
        }

        @Override
        public void accept(final EvolutionResult<?, C> result) {
            if (_fitness.getMax() == null) {
                _fitness = MinMax.of(result.getOptimize().ascending());
            }

            super.accept(result);
        }

        @Override
        void accept(final Phenotype<?, C> pt, final long generation) {
            super.accept(pt, generation);
            _fitness.accept(pt.getFitness());
        }

        @Override
        public String toString() {
            return super.toString() +
                "+---------------------------------------------------------------------------+\n" +
                "|  Population statistics                                                    |\n" +
                "+---------------------------------------------------------------------------+\n" +
                format(cpattern, "Age:", p(_age)) +
                format(cpattern, "Fitness", "") +
                format(spattern, "min =", _fitness.getMin()) +
                format(spattern, "max =", _fitness.getMax()) +
                "+---------------------------------------------------------------------------+";
        }
    }

}

