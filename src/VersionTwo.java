import java.util.concurrent.ForkJoinPool;

public class VersionTwo extends BaseVersion{
	
	private ForkJoinPool pool;
	
	public VersionTwo(CensusData data, int no_of_columns, int no_of_rows) {
		this.censusData = data;
		this.columns = no_of_columns;
		this.rows = no_of_rows;
        this.pool = new ForkJoinPool();
    }

	@Override
	public void init() {
		PreProcessor task = new PreProcessor(censusData.getCensusGroupData(), 0, censusData.getData_size());
        Bucket result = pool.invoke(task);
        this.rec_edges = result.getRectangle();
        this.total_US_Pop = result.getPopulation();
        calculateAxisValue();
	}

	@Override
	public Pair<Integer, Float> process(int west, int south, int east, int north) {
        Rectangle rect = new Rectangle(west, east, north, south);
        int bucketPopulation = pool.invoke(new QueryTask(censusData.getCensusGroupData(), 0, censusData.getData_size(), rect));
        return new Pair<Integer, Float>(bucketPopulation, bucketPopulation/total_US_Pop);
	}

}
