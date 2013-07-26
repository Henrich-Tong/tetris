package net.davidrobles.games.tetris.model;

import net.davidrobles.games.tetris.view.TetrisModelObserver;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class TetrisModel implements TetrisModelObservable {

    private List<TetrisModelObserver> observers = new ArrayList<TetrisModelObserver>();
    private BlockType[][] maze = new BlockType[COLUMNS][ROWS];
    private Piece currentPiece;
    private Piece nextPiece;
    private Piece[] pieces;
    private boolean gameOver = true; // TODO: Check usage
    private int level = 1;
    private int score = 0;
    private int linesCleared = 0;
    private static final int[] lineScores = {40, 100, 300, 1200};
    private static final int ROWS = 20;
    private static final int COLUMNS = 10;
    private static final int LINES_PER_LEVEL = 10;
    private static final Random rand = new Random();

    public TetrisModel() {
        initPieces();
        clearBlocks();
        setPieces();
    }

    private void clearBlocks() {
        for (int i = 0; i < COLUMNS; i++)
            for (int j = 0; j < ROWS; j++)
                setBlock(i, j, BlockType.EMPTY);
    }

    private void clearLine(int y) {
        // Clear line
        for (int x = 0; x < maze.length; x++)
            maze[x][y] = BlockType.EMPTY;
        linesCleared++;
        // Move blocks down
        for (int i = y; i > 0; i--)
            for (int x = 0; x < maze.length; x++)
                maze[x][i] = maze[x][i - 1];
    }

    private void clearLines() {
        List<Integer> lines = new ArrayList<Integer>();
        // Identify the lines to be deleted
        for (int y = 0; y < maze[0].length; y++) {
            boolean removeRow = true;
            for (BlockType[] aMaze : maze) {
                if (aMaze[y] == BlockType.EMPTY) {
                    removeRow = false;
                    break;
                }
            }
            if (removeRow)
                lines.add(y);
        }
        // Delete the lines
        for (Integer i : lines)
            clearLine(i);
        // Increase score
        if (lines.size() > 0)
            score += level * lineScores[lines.size() - 1];
        // Change level
        if (linesCleared >= LINES_PER_LEVEL) {
            level++;
            linesCleared = 0;
        }
    }

    public BlockType getBlock(int x, int y) {
        if (x < 0 || x >= maze.length || y < 0 || y >= maze[0].length) {
            return null;
        }
        return maze[x][y];
    }

    public int getColumns() {
        return COLUMNS;
    }

    public Piece getCurrentShape() {
        return currentPiece;
    }

    public int getLevel() {
        return level;
    }

    public BlockType[][] getMaze() {
        return maze;
    }

    public Piece getNextShape() {
        return nextPiece;
    }

    public int getRows() {
        return ROWS;
    }

    public int getScore() {
        return score;
    }

    public void forceMove() {
        if (currentPiece == null) {
            currentPiece = nextPiece;
            nextPiece = randomShape();
            if (!placeShape(currentPiece)) {
                gameOver = true;
            }
        } else if (currentPiece.getY() + currentPiece.getHeight() == ROWS) {
            leavePiece();
            clearLines();
        } else {
            for (int y = currentPiece.getHeight() - 1; y >= 0; y--) {
                for (int x = 0; x < currentPiece.getWidth(); x++) {
                    if (currentPiece.getBlock(x, y)
                            && getBlock(x + currentPiece.getX(), y + currentPiece.getY() + 1) != BlockType.EMPTY) {
                        leavePiece();
                        clearLines();
                        return;
                    }
                }
            }
            currentPiece.moveDown();
        }
        notifyObservers();
    }

    private void initPieces() {
        pieces = new Piece[7];
        pieces[0] = Piece.newCyan();
        pieces[1] = Piece.newBlue();
        pieces[2] = Piece.newOrange();
        pieces[3] = Piece.newYellow();
        pieces[4] = Piece.newGreen();
        pieces[5] = Piece.newPurple();
        pieces[6] = Piece.newRed();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void leavePiece() {
        for (int i = 0; i < currentPiece.getWidth(); i++)
            for (int j = 0; j < currentPiece.getHeight(); j++)
                if (currentPiece.getBlock(i, j))
                    setBlock(i + currentPiece.getX(), j + currentPiece.getY(), currentPiece.getBlockType());
        currentPiece = null;
    }

    public void moveLeft() { 
        if (currentPiece != null && currentPiece.getX() > 0) {
            for (int x = 0; x < currentPiece.getWidth(); x++) {
                for (int y = 0; y < currentPiece.getHeight(); y++) {
                    if (currentPiece.getBlock(x, y) && (getBlock((x + currentPiece.getX()) - 1,
                            y + currentPiece.getY()) != BlockType.EMPTY)) {
                        return;
                    }
                }
            }
            currentPiece.moveLeft();
            notifyObservers();
        }
    }

    public void moveRight() {
        if (currentPiece != null && (currentPiece.getX() + currentPiece.getWidth()) < COLUMNS) {
            for (int x = currentPiece.getWidth() - 1; x >= 0; x--) {
                for (int y = 0; y < currentPiece.getHeight(); y++) {
                    if (currentPiece.getBlock(x, y) && (getBlock((x + currentPiece.getX()) + 1,
                            y + currentPiece.getY()) != BlockType.EMPTY)) {
                        return;
                    }
                }
            }
            currentPiece.moveRight();
            notifyObservers();
        }
    }

    public void notifyObservers() {
        for (TetrisModelObserver o : observers)
            o.update();
    }

    private boolean placeShape(Piece piece) {
        piece.setX(4);
        piece.setY(0);
        for (int x = 0; x < piece.getWidth(); x++) {
            for (int y = 0; y < piece.getHeight(); y++) {
                if (piece.getBlock(x, y) && getBlock(piece.getX() + x, piece.getY() + y) != BlockType.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private Piece randomShape() {
        return pieces[rand.nextInt(pieces.length)].copy();
    }

    public void registerObserver(TetrisModelObserver observer) {
        observers.add(observer);
    }

    public void reset() {
        clearBlocks();
        setPieces();
        score = 0;
        level = 1;
        linesCleared = 0;
    }

    public void rotate() {
        if (currentPiece != null) {
            boolean[][] newBlocks = currentPiece.rotate();
            for (int x = 0; x < newBlocks.length; x++)
                for (int y = 0; y < newBlocks[0].length; y++)
                    if (getBlock(x + currentPiece.getX(), y + currentPiece.getY()) != BlockType.EMPTY)
                        return;
            currentPiece.setBlocks(newBlocks);
            notifyObservers();
        }
    }

    public void runCycle() {
        forceMove();
    }

    private void setBlock(int x, int y, BlockType color) {
        maze[x][y] = color;
    }

    private void setPieces() {
        currentPiece = randomShape();
        nextPiece = randomShape();
    }

}
