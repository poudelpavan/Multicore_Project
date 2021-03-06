
public class VersionOne extends BaseVersion {

	public VersionOne(CensusData data, int no_of_columns, int no_of_rows) {
		this.censusData = data;
		this.columns = no_of_columns;
		this.rows = no_of_rows;
	}

	@Override
	public void init() {
		rec_edges = new Rectangle(censusData.getCensusGroupData(0).longitude,
				censusData.getCensusGroupData(0).longitude, censusData.getCensusGroupData(0).latitude,
				censusData.getCensusGroupData(0).latitude);
		for (CensusGroup group : censusData.getCensusGroupData()) {
			if(group != null){
				Rectangle newRectangle = new Rectangle(group.longitude, group.longitude, group.latitude, group.latitude);
				rec_edges = rec_edges.encompass(newRectangle);
				total_US_Pop = total_US_Pop + group.population;
			}else{
				continue;
			}
		}
		calculateAxisValue();
	}

	@Override
	public Pair<Integer, Float> process(int west, int south, int east, int north) {
		int bucketPopulation = 0;
		for(CensusGroup group : censusData.getCensusGroupData())
		{
			if(group != null){
				int point_x = pointMapper(group.longitude, getMin_X_Coordinate(), getMax_X_Coordinate(), columns);
				int point_y = pointMapper(group.latitude, getMin_Y_Coordinate(), getMax_Y_Coordinate(), rows);
				if(point_y >= south && point_y <= north){
					if(point_x >= west && point_x <= east){
						bucketPopulation = bucketPopulation + group.population;
					}
				}
			}else{
				break;
			}
		}
		return new Pair<Integer, Float>(bucketPopulation, bucketPopulation/total_US_Pop);
	}

}
