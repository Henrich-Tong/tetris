package dr.games.tetris;

import dr.games.tetris.model.TetrisModel;
import dr.games.tetris.model.TetrisGame;
import dr.games.tetris.view.TetrisView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TetrisApplet extends JApplet {

    private TetrisModel model;
    private TetrisGame tetrisGame;
    private TetrisView view;

    //Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }

    private void createGUI() {
        model = new TetrisModel();
        tetrisGame = new TetrisGame(model);
        view = new TetrisView(tetrisGame);
        setSize(view.getPreferredSize());
        view.setOpaque(true);
        setContentPane(view);
        setFocusable(true);
        view.requestFocus();
        view.requestFocusInWindow();
        TetrisController controller = new TetrisController(tetrisGame);
        view.addKeyListener(controller);
//        dr.games.tetris.view.addMouseListener(controller);
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                view.requestFocus();
            }
        });
        tetrisGame.playGame();
        view.setVisible(true);
        setVisible(true);
    }

    @Override
    public void start() {

    }
}
