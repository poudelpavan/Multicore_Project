
public abstract class BaseVersion {
    protected CensusData censusData;
    protected int columns;
    protected int rows;
    protected Rectangle rec_edges;
    protected float total_US_Pop;
    protected float min_X_Coordinate;
    protected float max_X_Coordinate;
    protected float min_Y_Coordinate;
    protected float max_Y_Coordinate;
    
    public abstract void init();
    public abstract Pair<Integer, Float> process(int west, int south, int east, int north);
    
    public int pointMapper(float point, float lowestPoint, float highestPoint, int no_of_Buckets) {
        if (point == highestPoint) {
            return no_of_Buckets;
        }

        float range = highestPoint - lowestPoint;
        if (range == 0) {
            return 1;
        }
        float bucket = ((point - lowestPoint) / range * no_of_Buckets) + 1;
        if (bucket == Math.round(bucket)) {
            bucket = Math.min(bucket + 1, no_of_Buckets);
        }
        return (int) bucket;
    }
	
}
