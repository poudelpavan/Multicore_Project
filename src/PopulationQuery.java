
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PopulationQuery {
	// next four constants are relevant to parsing
	public static final int TOKENS_PER_LINE = 7;
	public static final int POPULATION_INDEX = 4; // zero-based indices
	public static final int LATITUDE_INDEX = 5;
	public static final int LONGITUDE_INDEX = 6;
	private static CensusData censusdata;
	private static BaseVersion version_handler;

	// parse the input file into a large array held in a CensusData object
	public static CensusData parse(String filename) {
		CensusData result = new CensusData();

		try {
			BufferedReader fileIn = new BufferedReader(new FileReader(filename));

			// Skip the first line of the file
			// After that each line has 7 comma-separated numbers (see constants
			// above)
			// We want to skip the first 4, the 5th is the population (an int)
			// and the 6th and 7th are latitude and longitude (floats)
			// If the population is 0, then the line has latitude and longitude
			// of +.,-.
			// which cannot be parsed as floats, so that's a special case
			// (we could fix this, but noisy data is a fact of life, more fun
			// to process the real data as provided by the government)

			String oneLine = fileIn.readLine(); // skip the first line

			// read each subsequent line and add relevant data to a big array
			while ((oneLine = fileIn.readLine()) != null) {
				String[] tokens = oneLine.split(",");
				if (tokens.length != TOKENS_PER_LINE)
					throw new NumberFormatException();
				int population = Integer.parseInt(tokens[POPULATION_INDEX]);
				if (population != 0)
					result.add(population, Float.parseFloat(tokens[LATITUDE_INDEX]),
							Float.parseFloat(tokens[LONGITUDE_INDEX]));
			}

			fileIn.close();
		} catch (IOException ioe) {
			System.err.println("Error opening/reading/writing input or output file.");
			System.exit(1);
		} catch (NumberFormatException nfe) {
			System.err.println(nfe.toString());
			System.err.println("Error in file format");
			System.exit(1);
		}
		return result;
	}

	// argument 1: file name for input data: pass this to parse
	// argument 2: number of x-dimension buckets
	// argument 3: number of y-dimension buckets
	// argument 4: -v1, -v2, -v3, -v4, or -v5
	public static void main(String[] args) {
		if (args.length != 4) {
			return;
		}
		String file = args[0];
		int x_co = 0;
		int y_co = 0;
		int version = 1;
		try {
			x_co = Integer.parseInt(args[1]);
			y_co = Integer.parseInt(args[2]);
			version = Integer.parseInt("" + args[3].charAt(2));
		} catch (NumberFormatException e) {
			System.out.println("Invalid Arguments: " + e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Invalid Arguments: " + e.getMessage());
		}

		init(file, x_co, y_co, version);

		System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
		Scanner scan_input = new Scanner(System.in);
		String[] input_array = scan_input.nextLine().split(" ");
		while (input_array.length == 4) {
			int west = Integer.parseInt(input_array[0]);
			int south = Integer.parseInt(input_array[1]);
			int east = Integer.parseInt(input_array[2]);
			int north = Integer.parseInt(input_array[3]);
			// validate inputs

			Pair<Integer, Float> result = process(west, south, east, north);
			System.out.println("population of rectangle: " + result.getElementA());
			System.out.println(String.format("percent of total population: %1$.2f", result.getElementB()));

			System.out.println("Please give west, south, east, north coordinates of your query rectangle:");
			input_array = scan_input.nextLine().split(" ");
		}
		scan_input.close();
	}

	public static void init(String filename, int no_of_columns, int no_of_rows, int versionNumber) {
		censusdata = parse(filename);

		switch (versionNumber) {
		case 1:
			version_handler = new VersionOne(censusdata, no_of_columns, no_of_rows);
			break;
		}
		version_handler.init();
	}

	public static Pair<Integer, Float> process(int a, int b, int c, int d) {
		return null;
	}
}
