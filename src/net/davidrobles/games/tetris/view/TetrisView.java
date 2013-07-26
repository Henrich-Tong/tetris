package net.davidrobles.games.tetris.view;

import net.davidrobles.games.tetris.model.*;
import static net.davidrobles.games.tetris.model.BlockType.*;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TetrisView extends JPanel implements TetrisModelObserver {

    private TetrisGame game;
    private TetrisModel model;
    private static final Dimension size = new Dimension(441, 498);
    private static final int OFFSET_LEFT = 144;
    private static final int OFFSET_TOP = 80;
    private static final int BLOCK_SIZE = 16;
    private static final int BLOCK_SIZE_NEXT = 14;
    private static final Font scoreFont = new Font("sansserif", Font.BOLD, 20);
    private static final Font levelFont = new Font("sansserif", Font.BOLD, 40);
    private static final Font gameOverFont = new Font("sansserif", Font.BOLD, 40);
    private static final Color scoreColor = Color.WHITE;
    private static final Color levelColor = Color.WHITE;
    private static final Color gameOverColor = Color.RED;
    private FontMetrics scoreFM;
    private FontMetrics levelFM;
    private static final Color PURPLE_COLOR = new Color(160, 32, 240);
    private Graphics2D dbg;
    private Image dbImage;
    private BufferedImage bgImage;
    private Image blocksBG;

    public TetrisView(TetrisGame game) {
        this.game = game;
        this.model = game.getModel();
        scoreFM = getFontMetrics(scoreFont);
        levelFM = getFontMetrics(levelFont);
        model.registerObserver(this);
        setPreferredSize(size);
        loadImages();
    }

    private Color blockTypeColor(BlockType blockType) {
        switch (blockType) {
            case I: return Color.CYAN;
            case J: return Color.BLUE;
            case L: return Color.ORANGE;
            case O: return Color.YELLOW;
            case S: return Color.GREEN;
            case T: return PURPLE_COLOR;
            case Z: return Color.RED;
            default: throw new AssertionError();
        }
    }

    private void gameRender() {
        if (dbImage == null) {
            dbImage = createImage(size.width, size.height);
            dbg = (Graphics2D) dbImage.getGraphics();
        }
        if (game.getState() == GameState.MAIN_MENU) {
//            // Dark Color
            dbg.setColor(Color.BLACK);
            dbg.fill3DRect(0, 0, dbImage.getWidth(null), dbImage.getHeight(null), true);
//            // Game over message
            dbg.setFont(gameOverFont);
//            dbg.setColor(scoreColor);
            dbg.setColor(Color.BLUE);
            dbg.drawString("Click to Play", 52, 252);
//            dbg.setFont(gameOverFont);
//            dbg.setColor(gameOverColor);
//            dbg.drawString("G A M E  O V E R", 50, 250);
////            dbg.fill3DRect(100, 300, 100, 40, true);
        } else {


        // Draw background
        dbg.drawImage(bgImage, 0, 0, null);
        if (!game.isPaused()) {
            // Draw current piece
            Piece piece = model.getCurrentShape();
            if (piece != null) {
                for (int i = 0; i < piece.getWidth(); i++) {
                    for (int j = 0; j < piece.getHeight(); j++) {
                        if (piece.getBlock(i, j)) {
                            dbg.setColor(blockTypeColor(piece.getBlockType()));
                            dbg.fill3DRect((piece.getX() + i) * BLOCK_SIZE + OFFSET_LEFT,
                                    (piece.getY() + j) * BLOCK_SIZE + OFFSET_TOP, BLOCK_SIZE, BLOCK_SIZE, true);
                        }
                    }
                }
            }
            // Draw next piece
            Piece nextPiece = model.getNextShape();
            if (nextPiece != null) {
                for (int i = 0; i < nextPiece.getWidth(); i++) {
                    for (int j = 0; j < nextPiece.getHeight(); j++) {
                        if (nextPiece.getBlock(i, j)) {
                            double offsetX = nextPiece.getWidth() / 2.0;
                            double offsetY = nextPiece.getHeight() / 2.0;
                            int startX = (int) (offsetX * BLOCK_SIZE_NEXT);
                            int startY = (int) (offsetY * BLOCK_SIZE_NEXT);
                            int blockX = i * BLOCK_SIZE_NEXT;
                            int blockY = j * BLOCK_SIZE_NEXT;
                            dbg.setColor(blockTypeColor(nextPiece.getBlockType()));
                            dbg.fill3DRect(377 - startX + blockX, 135 - startY + blockY,
                                    BLOCK_SIZE_NEXT, BLOCK_SIZE_NEXT, true);
                        }
                    }
                }
            }
            // Draw Blocks
            for (int i = 0; i < model.getColumns(); i++) {
                for (int j = 0; j < model.getRows(); j++) {
                    if (model.getBlock(i, j) != EMPTY) {
                        dbg.setColor(blockTypeColor(model.getBlock(i, j)));
                        dbg.fill3DRect(i * BLOCK_SIZE + OFFSET_LEFT, j * BLOCK_SIZE + OFFSET_TOP, BLOCK_SIZE,
                                BLOCK_SIZE, true);
                    }
                }

            }
            // Print score
            dbg.setFont(scoreFont);
            dbg.setColor(scoreColor);
            String score = Integer.toString(model.getScore());
            int offsetX = scoreFM.stringWidth(score);
            dbg.drawString(score, 75 - (offsetX / 2), 145);
            // Print level
            dbg.setFont(levelFont);
            dbg.setColor(levelColor);
            String level = Integer.toString(model.getLevel());
            offsetX = levelFM.stringWidth(level);
            dbg.drawString(level, 75 - (offsetX / 2), 290);
            // Gameover
//            if (net.davidrobles.games.tetris.model.isGameOver()) {
//                // Dark Color
//                dbg.setColor(new Color(0.0f, 0.0f, 0.0f, 0.8f));
//                dbg.fill3DRect(0, 0, dbImage.getWidth(null), dbImage.getHeight(null), true);
//                // Game over message
//                dbg.setFont(gameOverFont);
//                dbg.setColor(scoreColor);
//                dbg.drawString("G A M E  O V E R", 52, 252);
//                dbg.setFont(gameOverFont);
//                dbg.setColor(gameOverColor);
//                dbg.drawString("G A M E  O V E R", 50, 250);
//                dbg.fill3DRect(100, 300, 100, 40, true);
//            }
            // Press key
//            if (game.getKey() != null) {
//                dbg.setColor(Color.RED);
//                dbg.drawString(Character.toString(game.getKey().toString().charAt(0)), 380, 300);
//            }
        } else {
            dbg.setColor(Color.ORANGE);
            dbg.fillRect(80, 200, 260, 200);
            dbg.setColor(Color.YELLOW);
            dbg.drawString("P A U S E", 80, 150);
            dbg.setColor(Color.BLACK);
            dbg.setFont(scoreFont);
            dbg.drawString("Press 'R' to restart!", 120, 300);
        }
        }
    }

    private void paintScreen() {
        Graphics2D g = (Graphics2D) this.getGraphics();
        if (g != null && dbImage != null) {
            g.drawImage(dbImage, 0, 0, null);
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }
    }

    private void loadImages() {
        try {
            bgImage = ImageIO.read(new File("images/tetris.png"));
            // Draw blocks background
            Graphics2D gg = (Graphics2D) bgImage.getGraphics();
            for (int x = 0; x < model.getColumns(); x++) {
                for (int y = 0; y < model.getRows(); y++) {
                    if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) {
                        gg.setColor(new Color(26, 26, 26));
                    } else {
                        gg.setColor(new Color(36, 36, 36));
                    }
                    gg.fillRoundRect((x * BLOCK_SIZE) + 1 + OFFSET_LEFT, (y * BLOCK_SIZE) + 1
                            + OFFSET_TOP, BLOCK_SIZE - 2, BLOCK_SIZE - 2, 2, 2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        gameRender();
        paintScreen();
    }

}
