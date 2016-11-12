
public abstract class BaseVersion {
    protected CensusData censusData;
    protected int columns;
    protected int rows;
    protected Rectangle rec_edges;
    protected int total_US_Pop;
    
    public abstract void init();
    public abstract Pair<Integer, Float> process(int west, int south, int east, int north);
	
}
