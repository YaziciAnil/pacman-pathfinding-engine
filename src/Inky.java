import java.util.ArrayList;
import java.util.Random;

public class Inky extends Enemy {
    //there will be random movement
    private Random random;
    //constructor
    public Inky(Position pos) {
        super(pos);
        this.random = new Random();
    }

    @Override
    public Position selectTarget(Player player, MapData mapData) {
        //it can directly follow the pac-man
        if (random.nextDouble() < 0.6) {
            return player.getPos();
        }

        // or it can choose the corner around pac-man and follow it
        int[] rowDirs = {-1, 1, 0, 0};
        int[] colDirs = { 0, 0, -1, 1};

        ArrayList<Position> neighbors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int nr = pos.getRow() + rowDirs[i];
            int nc = pos.getCol() + colDirs[i];
            if (mapData.isValidMove(nr, nc)) {
                neighbors.add(new Position(nr, nc));
            }
        }

        //if there is no valid corner follow directly player
        if (neighbors.isEmpty()) return player.getPos();
        //choosing the corner
        return neighbors.get(random.nextInt(neighbors.size()));
    }
}