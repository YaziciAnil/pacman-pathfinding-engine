import java.util.ArrayList;
public abstract class Enemy {
    protected Position pos;
    protected Game.Direction direction;
    protected BFSPathFinder finder;
    protected double visualRow;
    protected double visualCol;
    //it should be less than pac-man
    private static final double SPEED = 0.067;
    //constructor
    public Enemy(Position pos){
        this.pos = pos;
        this.direction = Game.Direction.NONE;
        this.visualRow = pos.getRow();
        this.visualCol = pos.getCol();
        this.finder=new BFSPathFinder();

    }
    //alternative constructor
    public Enemy(Position pos, BFSPathFinder finder) {
        this.pos = pos;
        this.finder = finder;
        this.direction = Game.Direction.NONE;
        this.visualRow = pos.getRow();
        this.visualCol = pos.getCol();
    }

    public void move(Player player, MapData mapData) {
        approachVisual();
        if (isAligned()) {
            //determining which position target
            Position target = selectTarget(player, mapData);
            if (target == null) return;
            //calculating the shortest path
            ArrayList<Position> path = finder.getFullShortestPath(pos, target, mapData);
            //path[0]current position path[1] next position
            if (path == null || path.size() < 2) return;
            Position next = path.get(1);
            //determining the direction
            direction = getDirectionFromPositions(pos, next);
            //updating the postiion
            pos = next;
        }
    }
    //it approaches visual position to logical pos.
    private void approachVisual() {
        if (visualRow < pos.getRow())
            visualRow = Math.min(visualRow + SPEED, pos.getRow());
        else if (visualRow > pos.getRow())
            visualRow = Math.max(visualRow - SPEED, pos.getRow());

        if (visualCol < pos.getCol())
            visualCol = Math.min(visualCol + SPEED, pos.getCol());
        else if (visualCol > pos.getCol())
            visualCol = Math.max(visualCol - SPEED, pos.getCol());
    }
    //determines whether they are aligned.
    private boolean isAligned() {
        return visualRow == pos.getRow() && visualCol == pos.getCol();
    }

    //determining the position by difference.
    protected Game.Direction getDirectionFromPositions(Position from, Position to) {
        int dRow = to.getRow() - from.getRow();
        int dCol = to.getCol() - from.getCol();
        for (Game.Direction dir : Game.Direction.values()) {
            if (dir.getDRow() == dRow && dir.getDCol() == dCol) {
                return dir;
            }
        }
        return Game.Direction.NONE;
    }
    //basic methods
    public abstract Position selectTarget(Player player, MapData mapData);
    public double getVisualRow() { return visualRow; }
    public double getVisualCol() { return visualCol; }
    public void setVisualRow(double r) { this.visualRow = r; }
    public void setVisualCol(double c) { this.visualCol = c; }
    public Position getPos() { return pos; }
    public void setPos(Position pos) { this.pos = pos; }
    public Game.Direction getDirection() { return direction; }
    public void setDirection(Game.Direction dir) { this.direction = dir; }
}
