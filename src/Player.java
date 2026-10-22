public class Player {
    private Position pos;
    private int score;
    private Game.Direction currentDirection;
    private Game.Direction requestedDirection;//the last button pressed by user.

    private double visualRow;
    private double visualCol;

    private boolean moving;
    private static final double SPEED = 0.10;
//constructor
    public Player(Position pos) {
        this.currentDirection = Game.Direction.NONE;
        this.requestedDirection = Game.Direction.NONE;
        this.pos = pos;
        this.score = 0;
        this.visualRow = pos.getRow();
        this.visualCol = pos.getCol();
        this.moving = false;

    }
    public void update(MapData mapData) {
        approachVisual();

        //visual and logicial position is aligned
        if (isAligned()) {
            //increasing the score
            if (mapData.hasPellet(pos.getRow(), pos.getCol())) {
                mapData.removePellet(pos.getRow(), pos.getCol());
                score += 10;
            }

            // is direction valid
            int reqRow = pos.getRow() + requestedDirection.getDRow();
            int reqCol = pos.getCol() + requestedDirection.getDCol();
            if (requestedDirection != Game.Direction.NONE
                    && mapData.isValidMove(reqRow, reqCol)) {
                currentDirection = requestedDirection;
            }
            int nextRow = pos.getRow() + currentDirection.getDRow();
            int nextCol = pos.getCol() + currentDirection.getDCol();
            //is next move valid
            if (currentDirection != Game.Direction.NONE
                    && mapData.isValidMove(nextRow, nextCol)) {
                //updating the position
                pos = new Position(nextRow, nextCol);
                moving = true;
            } else {
                // it collided with the wall
                moving = false;
            }
        }
    }
//it approaches to visual position to logical position
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

    //is logical and visual position aligned
    private boolean isAligned() {
        return visualRow == pos.getRow() && visualCol == pos.getCol();
    }
   //reset method,everything will be restarted
    public void reset(Position startPos) {
        this.pos = startPos;
        this.visualRow = startPos.getRow();
        this.visualCol = startPos.getCol();
        this.currentDirection = Game.Direction.NONE;
        this.requestedDirection = Game.Direction.NONE;
        this.score = 0;
        this.moving = false;
    }
    //basic methods
    public void setRequestedDirection(Game.Direction dir) {
        this.requestedDirection = dir;
    }
    public boolean isMoving() {
        return moving;
    }
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
    public Position getPos() {
        return pos;
    }
    public Game.Direction getCurrentDirection() {
        return currentDirection;
    }
    public double getVisualRow() {
        return visualRow;
    }
    public double getVisualCol() {
        return visualCol;
    }
    public int getScore() {
        return score;
    }
    public void setPos(Position pos) { this.pos = pos; }
    public void setCurrentDirection(Game.Direction dir) { this.currentDirection = dir; }
    public void setVisualRow(double r) { this.visualRow = r; }
    public void setVisualCol(double c) { this.visualCol = c; }
    public void setScore(int s) { this.score = s; }

}
