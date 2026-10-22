
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            // Load map data and initialize game components. This class is immutable.
            MapData mapData = loadMapData("data/map.txt");

            // Create the player based on the starting position defined in the map data. Currently this class is also half-implemented, you should complete it based on the specifications given.
            Player player = new Player(mapData.getPlayerStart());

            // Create enemies based on the starting positions defined in the map data. Currently, enemy classess are not implemented, you should implement them based on the specifications given.
            Enemy[] enemies = new Enemy[]{new Pinky(mapData.getPinkyStart()), new Inky(mapData.getInkyStart()), new Blinky(mapData.getBlinkyStart())};

            // Initialize the game with the player, enemies, and map data. You should also complete the Game class to handle game logic, state management, and interactions between the player and enemies.
            Game game = new Game(player, enemies, mapData);

            // Draw the initial game state
            // You should have a game loop here that updates the game state and calls tickAnimation() and drawGame() repeatedly, but for simplicity, we just draw the initial state.
            GameRenderer renderer = new GameRenderer(mapData, game);
            renderer.setupDraw();
            renderer.drawGame();
            while (true) {

                //for keyboard inputs
                handleInput(game);
                game.update();

                // processing the animation and drawing the game
                renderer.tickAnimation();
                renderer.drawGame();
                StdDraw.pause(16);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Map file could not be loaded.");
            e.printStackTrace();
        }
    }
    private static boolean pWasPressed = false;
    private static boolean rWasPressed = false;
    private static void handleInput(Game game) {
        if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_Q)) {
            System.exit(0);
        }
        if (game.getGameState() == Game.GameState.START_SCREEN) {
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_SPACE)) {
                game.setGameState(Game.GameState.READY);
            }
        }

        boolean pNow = StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_P);
        if (pNow && !pWasPressed) {
            if (game.getGameState() == Game.GameState.PLAYING) {
                game.setGameState(Game.GameState.PAUSED);
            } else if (game.getGameState() == Game.GameState.PAUSED) {
                game.setGameState(Game.GameState.PLAYING);
            }
        }
        pWasPressed = pNow;

        boolean rNow = StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_R);
        if (rNow && !rWasPressed) {
            if (game.getGameState() == Game.GameState.LOST
                    || game.getGameState() == Game.GameState.WON) {
                game.reset();
            }
        }
        rWasPressed = rNow;

        //only in playing state,recording as a requested direction
        if (game.getGameState() == Game.GameState.PLAYING) {
            if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_UP)) {
                game.getPlayer().setRequestedDirection(Game.Direction.UP);
            } else if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_DOWN)) {
                game.getPlayer().setRequestedDirection(Game.Direction.DOWN);
            } else if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_LEFT)) {
                game.getPlayer().setRequestedDirection(Game.Direction.LEFT);
            } else if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_RIGHT)) {
                game.getPlayer().setRequestedDirection(Game.Direction.RIGHT);
            }
        }
    }


    // Method for loading map data from a file. You should not need to modify this method.
    private static MapData loadMapData(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));

        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        scanner.nextLine();

        String[] playerLine = scanner.nextLine().split(" ");
        Position playerStart = new Position(
                Integer.parseInt(playerLine[1]),
                Integer.parseInt(playerLine[2])
        );

        String[] directEnemyLine = scanner.nextLine().split(" ");
        Position directEnemyStart = new Position(
                Integer.parseInt(directEnemyLine[1]),
                Integer.parseInt(directEnemyLine[2])
        );

        String[] randomChaseEnemyLine = scanner.nextLine().split(" ");
        Position randomChaseEnemyStart = new Position(
                Integer.parseInt(randomChaseEnemyLine[1]),
                Integer.parseInt(randomChaseEnemyLine[2])
        );

        String[] closestCornerEnemyLine = scanner.nextLine().split(" ");
        Position closestCornerEnemyStart = new Position(
                Integer.parseInt(closestCornerEnemyLine[1]),
                Integer.parseInt(closestCornerEnemyLine[2])
        );

        String[] cornerHeader = scanner.nextLine().split(" ");
        int cornerCount = Integer.parseInt(cornerHeader[1]);

        Position[] corners = new Position[cornerCount];
        for (int i = 0; i < cornerCount; i++) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            scanner.nextLine();
            corners[i] = new Position(r, c);
        }

        char[][] map = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < cols; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        scanner.close();

        return new MapData(
                map,
                playerStart,
                directEnemyStart,
                randomChaseEnemyStart,
                closestCornerEnemyStart,
                corners
        );
    }
}