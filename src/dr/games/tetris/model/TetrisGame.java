package dr.games.tetris.model;

public class TetrisGame implements Runnable {

    private TetrisModel model;
    private Thread animator;
    private Move key;
    private boolean gameRunning = true;
    private boolean paused = false;
    private GameState state = GameState.MAIN_MENU;
    private int delay = 16; // To achieve 50 - 60 FPS on my computer
    private int gameTick = 0;
    private int keyPressedTick = 0;

    public void reset() {
        gameTick = 0;
        keyPressedTick = 0;
        key = null;
        state = GameState.MAIN_MENU;
        model.reset();
        paused = false;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public TetrisGame(TetrisModel model) {
        this.model = model;
    }

    private void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void gameTick() {
        int levelSpeed = 38 - (model.getLevel() * 2);
//        if (!paused && !dr.games.tetris.model.isGameOver()) {
        if (state == GameState.PLAYING) {
            if (!paused) {               
                if (key != null) {
                    if (key == Move.DOWN && keyPressedTick % 4 == 0) {
                        model.forceMove();
                    } else if (key == Move.RIGHT && keyPressedTick % 10 == 0) {
                        model.moveRight();
                    } else if (key == Move.LEFT && keyPressedTick % 10 == 0) {
                        model.moveLeft();
                    }
                    keyPressedTick++;
                }
                if (gameTick % levelSpeed == 0 && key != Move.DOWN) {
                    model.runCycle();
                }
            }
        } else if (state == GameState.MAIN_MENU)
            model.notifyObservers();
        gameTick++;
    }

    public Move getKey() {
        return key;
    }

    public TetrisModel getModel() {
        return model;
    }

    public boolean isPaused() {
        return paused;
    }

    public void pressKey(Move key) {
        this.key = key;
    }

    public void releaseKey() {
        this.key = null;
        keyPressedTick = 0;
    }

    public void pauseGame() {
        paused = !paused;
    }

    public void playGame() {
        if (animator == null) {
            animator = new Thread(this);
        }
        animator.start();
    }

    @Override
    public void run() {
        while (gameRunning) {
            gameTick();
            delay(delay);
        }
    }

}
