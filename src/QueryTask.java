import java.util.concurrent.RecursiveTask;

public class QueryTask extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;
	private CensusGroup[] censusGroup;
    private int start;
    private int end;
    private Rectangle rectangle;


    public QueryTask(CensusGroup[] censusGroup, int start, int end, Rectangle rectangle) {
        this.censusGroup = censusGroup;
        this.start = start;
        this.end = end;
        this.rectangle = rectangle;
    }

    @Override
    protected Integer compute() {
        if (end - start < Constants.SEQUENTIAL_CUTOFF) {
            int queryPop = 0;
            for (int i = start; i < end; i++) {
                CensusGroup group = censusGroup[i];
                if (group == null) break;
                int x = BaseVersion.pointMapper(group.longitude, BaseVersion.getMin_X_Coordinate(), BaseVersion.getMax_X_Coordinate(), BaseVersion.columns);
                int y = BaseVersion.pointMapper(group.latitude, BaseVersion.getMin_Y_Coordinate(), BaseVersion.getMax_Y_Coordinate(), BaseVersion.rows);
                if (x >= rectangle.left && x <= rectangle.right)
                    if (y >= rectangle.bottom && y <= rectangle.top)
                        queryPop += group.population;
            }
            return queryPop;
        }

        int half = (start + end) / 2;

        QueryTask left = new QueryTask(censusGroup, start, half, rectangle);
        QueryTask right = new QueryTask(censusGroup, half, end, rectangle);

        left.fork();
        Integer rightResult = right.compute();
        Integer leftResult = left.join();

        return rightResult + leftResult;
    }
}