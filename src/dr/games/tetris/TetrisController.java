package dr.games.tetris;

import dr.games.tetris.model.Move;
import dr.games.tetris.model.TetrisGame;
import dr.games.tetris.model.GameState;
import dr.games.tetris.model.TetrisModel;

import java.awt.event.*;

public class TetrisController implements KeyListener, MouseListener {

    private TetrisGame game;
    private TetrisModel model;

    public TetrisController(TetrisGame game) {
        this.game = game;
        this.model = game.getModel();
    }

    public void moveRight() {
        game.pressKey(Move.RIGHT);
    }

    public void moveDown() {
        game.pressKey(Move.DOWN);
    }

    public void moveLeft() {
        game.pressKey(Move.LEFT);
    }

    public void pause() {
        game.pauseGame();
        model.notifyObservers();
    }

    public void restart() {
        game.reset();
    }

    public void rotate() {
        game.getModel().rotate();
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            moveLeft();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            moveRight();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            moveDown();
        } else if (keyCode == KeyEvent.VK_UP) {
            rotate();
        } else if (keyCode == KeyEvent.VK_R) {
            restart();
        } else if (keyCode == KeyEvent.VK_S) {
            if (game.getState() == GameState.MAIN_MENU) {
                game.setState(GameState.PLAYING);
            }
        } else if (keyCode == KeyEvent.VK_P || keyCode == KeyEvent.VK_ESCAPE) {
            pause();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_LEFT) {
            game.releaseKey();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (game.getState() == GameState.MAIN_MENU) {
            game.setState(GameState.PLAYING);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Implementation
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Implementation
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Implementation
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Implementation
    }

}
