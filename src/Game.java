public class Game {

    public enum GameState {
        //all unique states for game.
        START_SCREEN,
        READY,
        PLAYING,
        PAUSED,
        WON,
        LOST,
    }
    public enum Direction {
        UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1), NONE(0, 0);
        private final int dRow;
        private final int dCol;
        Direction(int dRow, int dCol) {
            this.dRow = dRow;
            this.dCol = dCol;
        }
        public int getDRow() { return dRow; }
        public int getDCol() { return dCol; }
    }
    private final Player player;
    private final Enemy[] enemies;
    private GameState gameState;
    private final MapData mapData;
//1 second duration in 60 fps for ready state
    private int readyTimer;
    private static final int READY_DURATION = 60;
    private static final double COLLISION_THRESHOLD = 0.75;
    //constructor
    public Game(Player player, Enemy[] enemies, MapData mapData) {
        this.player = player;
        this.enemies = enemies;
        this.mapData = mapData;
        this.gameState = GameState.START_SCREEN;
        this.readyTimer=0;
    }
    public void update() {
        if (gameState == GameState.READY) {
            readyTimer++;
            //starting to play
            if (readyTimer >= READY_DURATION) {
                gameState = GameState.PLAYING;
            }
        } else if (gameState == GameState.PLAYING) {
            player.update(mapData);
            for (Enemy enemy : enemies) {
                enemy.move(player, mapData);
            }
            if (checkCollision()) {
                gameState = GameState.LOST;
            }
            else if (checkWin()) {
                gameState = GameState.WON;
            }
        }
    }
    //collision algorithm
    private boolean checkCollision() {
        for (Enemy enemy : enemies) {
            double rowDiff = Math.abs(player.getVisualRow() - enemy.getVisualRow());
            double colDiff = Math.abs(player.getVisualCol() - enemy.getVisualCol());
            if (rowDiff < COLLISION_THRESHOLD && colDiff < COLLISION_THRESHOLD) {
                return true;
            }
        }
        return false;
    }
    private boolean checkWin() {
        for (int r = 0; r < mapData.getRows(); r++) {
            for (int c = 0; c < mapData.getCols(); c++) {
                if (mapData.hasPellet(r, c)) {
                    return false;
                }
            }
        }
        return true;
    }
    //reseting the game
    public void reset() {
        mapData.resetMap();
        player.reset(mapData.getPlayerStart());
        enemies[0].setPos(mapData.getPinkyStart());
        enemies[0].setVisualRow(mapData.getPinkyStart().getRow());
        enemies[0].setVisualCol(mapData.getPinkyStart().getCol());
        enemies[0].setDirection(Direction.NONE);
        enemies[1].setPos(mapData.getInkyStart());
        enemies[1].setVisualRow(mapData.getInkyStart().getRow());
        enemies[1].setVisualCol(mapData.getInkyStart().getCol());
        enemies[1].setDirection(Direction.NONE);
        enemies[2].setPos(mapData.getBlinkyStart());
        enemies[2].setVisualRow(mapData.getBlinkyStart().getRow());
        enemies[2].setVisualCol(mapData.getBlinkyStart().getCol());
        enemies[2].setDirection(Direction.NONE);
        readyTimer = 0;
        gameState = GameState.START_SCREEN;
    }
//basic methods
    public Player getPlayer() {
        return player;
    }
    public Enemy[] getEnemies() {
        return enemies;
    }
    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState state) { this.gameState = state; }
}