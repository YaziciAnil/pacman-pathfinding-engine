public class Blinky extends Enemy {
    //constructor
    public Blinky(Position pos) {
        super(pos);
    }
    //it will follow the closest corner to pac-man
    @Override
    public Position selectTarget(Player player, MapData mapData) {
        Position[] corners = mapData.getCorners();
        Position closest = null;
        double minDist = Double.MAX_VALUE;
        //calculating to corner min distance away from pac-man
        for (int i = 0; i < corners.length; i++) {
            double dist = Math.abs(player.getPos().getRow() - corners[i].getRow())
                    + Math.abs(player.getPos().getCol() - corners[i].getCol());
            if (dist < minDist) {
                minDist = dist;
                closest = corners[i];
            }
        }
        return closest;
    }
}
