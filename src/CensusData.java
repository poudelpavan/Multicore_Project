
// just a resizing array for holding the input
// fields are public for simplicity
// note array may not be full; see data_size field

public class CensusData {
	public static final int INITIAL_SIZE = 100;
	private CensusGroup[] censusGroupData;
	private int data_size;
	
	public CensusData() {
		censusGroupData = new CensusGroup[INITIAL_SIZE];
		data_size = 0;
	}
	
	public void add(int population, float latitude, float longitude) {
		if(data_size == censusGroupData.length) { // resize
			CensusGroup[] new_data = new CensusGroup[censusGroupData.length*2];
			for(int i=0; i < censusGroupData.length; ++i)
				new_data[i] = censusGroupData[i];
			censusGroupData = new_data;
		}
		CensusGroup g = new CensusGroup(population,latitude,longitude); 
		censusGroupData[data_size++] = g;
	}

	public CensusGroup getCensusGroupData(int index) {
		return censusGroupData[index];
	}

	public CensusGroup[] getCensusGroupData() {
		return censusGroupData;
	}

	public int getData_size() {
		return data_size;
	}
	
}
