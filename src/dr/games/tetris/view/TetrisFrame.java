package dr.games.tetris.view;

import javax.swing.*;

public class TetrisFrame extends JFrame {

    public TetrisFrame(JPanel panel) {
        getContentPane().add(panel, "Center");
        pack();
        setTitle("Tetris");
        setLocation(200, 200);
        setResizable(false);
        setVisible(true);
    }

}