import java.util.ArrayList;

public class BFSPathFinder {
    //we will check the corners with this order
    private static final int[] ROW_DIRS = {-1, 1,  0, 0};
    private static final int[] COL_DIRS = { 0, 0, -1, 1};

    public ArrayList<Position> getFullShortestPath(Position start,
                                                   Position goal,
                                                   MapData mapData) {
        //we are at the target.
        if (start.equals(goal)) {
            ArrayList<Position> result = new ArrayList<>();
            result.add(start);
            return result;
        }
        int rows = mapData.getRows();
        int cols = mapData.getCols();
        //visited positions.
        boolean[][] visited = new boolean[rows][cols];
        //this is for showing where we came from to this position
        Position[][] parent = new Position[rows][cols];
        //it holds to positions which we will visit.
        Queue<Position> queue = new Queue<>();
        //enemy position is in the queue
        queue.enqueue(start);
        visited[start.getRow()][start.getCol()] = true;
        while (!queue.isEmpty()) {
            Position current = queue.dequeue();
            //it determines whether we reach the target
            if (current.equals(goal)) {
                return buildPath(parent, start, goal);
            }
            //checking the four neighbour
            for (int i = 0; i < 4; i++) {
                int newRow = current.getRow() + ROW_DIRS[i];
                int newCol = current.getCol() + COL_DIRS[i];
                //is this move valid and determines whether we visited that position before
                if (mapData.isValidMove(newRow, newCol) && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = current;
                    queue.enqueue(new Position(newRow, newCol));
                }
            }
        }

        return null;
    }
    // it will generate exact path
    private ArrayList<Position> buildPath(Position[][] parent,
                                          Position start,
                                          Position goal) {
        ArrayList<Position> path = new ArrayList<>();
        Position current = goal;
        while (!current.equals(start)) {
            path.add(0, current);
            current = parent[current.getRow()][current.getCol()];
        }
        path.add(0, start);
        return path;
    }
}
