package dr.games.tetris;

import dr.games.tetris.view.TetrisFrame;
import dr.games.tetris.view.TetrisView;
import dr.games.tetris.model.TetrisModel;
import dr.games.tetris.model.TetrisGame;

public class RunTetris {

    static void run() {
        TetrisModel model = new TetrisModel();
        TetrisGame tetrisGame = new TetrisGame(model);
        TetrisView view = new TetrisView(tetrisGame);
        dr.games.tetris.TetrisController controller = new dr.games.tetris.TetrisController(tetrisGame);
        view.addKeyListener(controller);
        view.addMouseListener(controller);
        new TetrisFrame(view);
        view.requestFocusInWindow();
        tetrisGame.playGame();
    }

    public static void main(String[] args) {
        run();
    }

}
