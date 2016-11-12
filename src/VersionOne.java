
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
			if (group == null) {
				continue;
			} else {
				rec_edges = rec_edges.encompass(new Rectangle(group.longitude, group.longitude, group.latitude, group.latitude));
				total_US_Pop = total_US_Pop + group.population;
			}
		}
	}

	@Override
	public Pair<Integer, Float> process(int west, int south, int east, int north) {
		// TODO Auto-generated method stub
		return null;
	}

}
