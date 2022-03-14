package model;

import view.TileView;
import java.util.Random;

public class Minesweeper extends AbstractMineSweeper {

    private AbstractTile[][] board;
    private int with;
    private int height;
    private int explosionCount;


    public Minesweeper(){

    }

    public AbstractTile[][] getBoard() {
        return board;
    }

    public int getCountExplosiveNeighbours(int x, int y){
        try {
            if (getTile(x, y).isExplosive()) {
                return 10;
            } else {
                int counter = 0;
                for (int i = x - 1; i < x + 2; i++) {
                    for (int j = y - 1; j < y + 2; j++) {
                        if (getTile(x, y).isExplosive()) {
                            counter++;
                        }
                    }
                }
                return counter;
            }
        }
        catch(Exception e){
            return 100;
        }
    }

    @Override
    public int getWidth() {
        return board[0].length;
    }

    @Override
    public int getHeight() {
        return board.length;
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
        this.explosionCount = explosionCount;

        this.viewNotifier.notifyNewGame(row,col);
        setWorld(board);

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
    //TODO wat moet dit doen?
    public void setWorld(AbstractTile[][] world) {
        int bombs = 0;
        //double kans = explosionCount/(getWidth()*getHeight());
        Random rand = new Random();
        int randNum = rand.nextInt(77);

        while ( bombs< explosionCount) {
            for (int i = 0; i < getHeight(); i++) {
                System.out.print('\n');
                for (int j = 0; j < getWidth(); j++) {
                    if (randNum < 20 && bombs < explosionCount) {
                        board[i][j] = generateExplosiveTile();
                        bombs++;
                        System.out.print(1 + " ");
                    } else {
                        board[i][j] = generateEmptyTile();
                        System.out.print(0 + " ");
                    }
                    board[i][j].setTileNotifier(new TileView(i,j));
                }
            }
        }
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
    // TODO is dit alle nul tegels clearen?
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
