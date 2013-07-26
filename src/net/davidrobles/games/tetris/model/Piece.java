package net.davidrobles.games.tetris.model;

public class Piece {

    private boolean[][] blocks;
    private int x;
    private int y;
    private BlockType blockType;

    private Piece(int width, int height, BlockType blockType) {
        blocks = new boolean[width][height];
        this.blockType = blockType;
    }

    private Piece(boolean[][] blocks, BlockType blockType) {
        this.blocks = blocks.clone();
        this.blockType = blockType;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public boolean getBlock(int x, int y) {
        return blocks[x][y];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return blocks[0].length;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public boolean[][] rotate() {
        boolean[][] newBlocks = new boolean[getHeight()][getWidth()];
        for (int x = 0; x < newBlocks.length; x++)
            for (int y = 0; y < newBlocks[0].length; y++)
                newBlocks[x][y] = blocks[y][getHeight() - x - 1];
        return newBlocks;
    }

    public void setBlock(int x, int y, boolean b) {
        blocks[x][y] = b;
    }

    public void setBlocks(boolean[][] blocks) {
        this.blocks = blocks;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return blocks.length;
    }

    public Piece copy() {
        return new Piece(blocks, blockType);
    }

    @Override
    public String toString() {
        return blockType + ": " + x + ", " + y;
    }

    //Static factory algorithms

    public static Piece newCyan() {
        Piece piece = new Piece(4, 1, BlockType.I);
        piece.setBlock(0, 0, true);
        piece.setBlock(1, 0, true);
        piece.setBlock(2, 0, true);
        piece.setBlock(3, 0, true);
        return piece;
    }

    public static Piece newBlue() {
        Piece newPiece = new Piece(3, 2, BlockType.J);
        newPiece.setBlock(0, 0, true);
        newPiece.setBlock(1, 0, true);
        newPiece.setBlock(2, 0, true);
        newPiece.setBlock(2, 1, true);
        return newPiece;
    }

    public static Piece newOrange() {
        Piece newPiece = new Piece(3, 2, BlockType.L);
        newPiece.setBlock(0, 0, true);
        newPiece.setBlock(1, 0, true);
        newPiece.setBlock(2, 0, true);
        newPiece.setBlock(0, 1, true);
        return newPiece;
    }

    public static Piece newYellow() {
        Piece newPiece = new Piece(2, 2, BlockType.O);
        newPiece.setBlock(0, 0, true);
        newPiece.setBlock(1, 0, true);
        newPiece.setBlock(0, 1, true);
        newPiece.setBlock(1, 1, true);
        return newPiece;
    }

    public static Piece newGreen() {
        Piece newPiece = new Piece(3, 2, BlockType.S);
        newPiece.setBlock(1, 0, true);
        newPiece.setBlock(2, 1, true);
        newPiece.setBlock(0, 1, true);
        newPiece.setBlock(1, 1, true);
        return newPiece;
    }

    public static Piece newPurple() {
        Piece newPiece = new Piece(3, 2, BlockType.T);
        newPiece.setBlock(0, 0, true);
        newPiece.setBlock(1, 0, true);
        newPiece.setBlock(2, 0, true);
        newPiece.setBlock(1, 1, true);
        return newPiece;
    }

    public static Piece newRed() {
        Piece newPiece = new Piece(3, 2, BlockType.Z);
        newPiece.setBlock(0, 0, true);
        newPiece.setBlock(1, 0, true);
        newPiece.setBlock(1, 1, true);
        newPiece.setBlock(2, 1, true);
        return newPiece;
    }

}
