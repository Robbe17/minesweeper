package model;

public class Minesweeper extends AbstractMineSweeper {

    private AbstractTile[][] board;
    private int with;
    private int height;


    public Minesweeper(){

    }



    @Override
    public int getWidth() {
        return with;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void startNewGame(Difficulty level) {
        switch (level){
            case EASY -> startNewGame(8,8,10);
            case MEDIUM -> startNewGame(16,16,40);
            case HARD -> startNewGame(16,30,99);

        }
    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {
        board = new Tile[row][col];
        double kans = explosionCount/(row*col);
        int bombs = 0;
        for (int i = 0; i <row ; i++) {
            for (int j = 0; j < col; j++) {
                if(Math.random() < kans && bombs<explosionCount){
                    board[i][j] = generateExplosiveTile();
                }
                else{
                    board[i][j] = generateEmptyTile();
                }
            }
        }
    }

    @Override
    public void toggleFlag(int x, int y) {
        if (getTile(x, y).isFlagged()){
            getTile(x, y).unflag();
        }
        else {
            getTile(x, y).flag();
        }
    }

    @Override
    public AbstractTile getTile(int x, int y) {
        return board[x][y];
    }

    @Override
    public void setWorld(AbstractTile[][] world) {

    }

    @Override
    public void open(int x, int y) {
        getTile(x, y).open();
    }

    @Override
    public void flag(int x, int y) {
        getTile(x, y).flag();
    }

    @Override
    public void unflag(int x, int y) {
        getTile(x, y).unflag();
    }

    @Override
    public void deactivateFirstTileRule() {

    }

    @Override
    public AbstractTile generateEmptyTile() {
        Tile empty = new Tile(false);
        return empty;
    }

    @Override
    public AbstractTile generateExplosiveTile() {
        Tile bomb = new Tile(true);
        return bomb;
    }
}
