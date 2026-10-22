public class Pinky extends Enemy {
    //constructor
    public Pinky(Position pos) {
        super(pos);
    }
//it will directly follow pac-man
    @Override
    public Position selectTarget(Player player, MapData mapData) {
        return player.getPos();
    }
}
