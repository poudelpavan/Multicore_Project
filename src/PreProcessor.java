import java.util.concurrent.RecursiveTask;

public class PreProcessor extends RecursiveTask<Bucket> {
    private static final long serialVersionUID = 1L;
    private CensusGroup[] censusGroup;
    private int start;
    private int end;

    public PreProcessor(CensusGroup[] censusGroup, int start, int end) {
        this.censusGroup = censusGroup;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Bucket compute() {
        if (end - start < Constants.SEQUENTIAL_CUTOFF) {
            CensusGroup initGroup = censusGroup[start];
            Rectangle rect = new Rectangle(initGroup.longitude, initGroup.longitude, initGroup.latitude, initGroup.latitude);
            int totalPopulation = initGroup.population;
            for (int i = start + 1; i < end; i++) {
                CensusGroup group = censusGroup[i];
                rect = rect.encompass(new Rectangle(group.longitude, group.longitude, group.latitude, group.latitude));
                totalPopulation += group.population;
            }
            Bucket bucket = new Bucket(rect, totalPopulation);
            return bucket;
        }

        int half = (start + end) / 2;

        PreProcessor left = new PreProcessor(censusGroup, start, half);
        PreProcessor right = new PreProcessor(censusGroup, half, end);

        left.fork();
        Bucket rightResult = right.compute();
        Bucket leftResult = left.join();
        
        Rectangle rect = rightResult.getRectangle().encompass(leftResult.getRectangle());
        int population = rightResult.getPopulation() + leftResult.getPopulation();

        Bucket bucket = new Bucket(rect, population);
        return bucket;
    }
}