
public abstract class BaseVersion {
    protected CensusData censusData;
    protected static int columns;
    protected static int rows;
    protected Rectangle rec_edges;
    protected float total_US_Pop;
    private static float min_X_Coordinate;
    private static float max_X_Coordinate;
    private static float min_Y_Coordinate;
    private static float max_Y_Coordinate;
    
    public abstract void init();
    public abstract Pair<Integer, Float> process(int west, int south, int east, int north);
    
    public static int pointMapper(float point, float lowestPoint, float highestPoint, int no_of_Buckets) {
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
    
    public void calculateAxisValue(){
    	min_X_Coordinate = rec_edges.left;
		max_X_Coordinate = rec_edges.right;
		min_Y_Coordinate = rec_edges.bottom;
		max_Y_Coordinate = rec_edges.top;
    }
	public static float getMin_X_Coordinate() {
		return min_X_Coordinate;
	}
	public static float getMax_X_Coordinate() {
		return max_X_Coordinate;
	}
	public static float getMin_Y_Coordinate() {
		return min_Y_Coordinate;
	}
	public static float getMax_Y_Coordinate() {
		return max_Y_Coordinate;
	}
    
    
	
}
