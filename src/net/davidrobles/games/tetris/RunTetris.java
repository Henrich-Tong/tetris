package net.davidrobles.games.tetris;

import net.davidrobles.games.tetris.view.TetrisFrame;
import net.davidrobles.games.tetris.view.TetrisView;
import net.davidrobles.games.tetris.model.TetrisModel;
import net.davidrobles.games.tetris.model.TetrisGame;

public class RunTetris {

    static void run() {
        TetrisModel model = new TetrisModel();
        TetrisGame tetrisGame = new TetrisGame(model);
        TetrisView view = new TetrisView(tetrisGame);
        net.davidrobles.games.tetris.TetrisController controller = new net.davidrobles.games.tetris.TetrisController(tetrisGame);
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
