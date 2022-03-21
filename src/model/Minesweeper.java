package model;

import view.TileView;
import java.util.Random;

public class Minesweeper extends AbstractMineSweeper {

    private AbstractTile[][] board;
    private int with;
    private int height;
    private int explosionCount;
    private int playercounter;//hoeveel vakjes er nog ingedrukt moeten worden init line 73


    public Minesweeper(){

    }

    //TODO hoe weten wannneer je gewonnen bent aka wanneer heb je alle tegels geopend-->line73
    //TODO eerst tegel mag geen bom zijn.


    public AbstractTile[][] getBoard() {
        return board;
    }

    public int getCountExplosiveNeighbours(int x, int y){
        int counter = 0;
        if (getTile(x, y).isExplosive()) {
            return 10;
        }
        else {
            for (int i = x - 1; i < x + 2; i++) {
                for (int j = y - 1; j < y + 2; j++) {
                    try{
                        if (getTile(i, j).isExplosive()) {
                            counter++;
                        }
                    }
                    catch (Exception e){
                    }
                }
            }
            return counter;
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
        playercounter = (row*col)-explosionCount;
        this.explosionCount = explosionCount;

        this.viewNotifier.notifyNewGame(row,col);
        setWorld(board);

    }

    @Override
    public void toggleFlag(int x, int y) {
        if (getTile(x, y).isFlagged()){
            unflag(x,y);
            this.viewNotifier.notifyUnflagged(x,y);
        }
        else {
            flag(x,y);
            this.viewNotifier.notifyFlagged(x,y);
        }
    }

    @Override
    public AbstractTile getTile(int x, int y) {
        return board[y][x];//wout aangepast // thx wout
    }

    @Override
    public void setWorld(AbstractTile[][] world) {
        int bombs = 0;
        //double kans = explosionCount/(getWidth()*getHeight());
        Random rand = new Random();
        //int randNum = rand.nextInt(77);


            for (int i = 0; i < getHeight(); i++) {
                System.out.print('\n');
                for (int j = 0; j < getWidth(); j++) {
                    int randNum = rand.nextInt(77);
                    if (randNum < 11 && bombs < explosionCount) {
                        board[i][j] = generateExplosiveTile();
                        bombs++;
                        System.out.print(1 + " ");
                    } else {
                        board[i][j] = generateEmptyTile();
                        System.out.print(0 + " ");
                    }
                    //board[i][j].setTileNotifier(new TileView(i, j));
                }
            }
        if(bombs < explosionCount) {
            while (bombs < explosionCount) {
                int x = rand.nextInt(getHeight());
                int y = rand.nextInt(getWidth());
                if (!world[x][y].isExplosive()) {
                    world[x][y] = generateExplosiveTile();
                    bombs++;
                }
            }
        }
    }

    @Override
    public void open(int x, int y) {


        // wat als het een bom is
        if (getTile(x,y).isExplosive()){
            this.viewNotifier.notifyExploded(x, y);
            this.viewNotifier.notifyGameLost();
            System.out.println("Tile [" + x + ";" + y + "] opened it was a bomb you are dead");

        }
        // wat als het geen bom is
        else{
            this.viewNotifier.notifyOpened(x,y, getCountExplosiveNeighbours(x,y));
            if (!getTile(x ,y).isOpened()){
                playercounter = playercounter - 1;
            }
            getTile(x,y).open();


            if (playercounter == 0) {
                this.viewNotifier.notifyGameWon();
            }
            if (getCountExplosiveNeighbours(x,y) ==0) {
                for (int i = x - 1; i < x + 2; i++) {
                    for (int j = y - 1; j < y + 2; j++) {
                        try {
                            if (!getTile(i, j).isOpened()) {
                                open(i, j);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
            System.out.println("Tile [" + x + ";" + y + "] opened it wasn't a bomb and there are " + playercounter + " tiles left");
        }
        //System.out.println("Tile [" + x + ";" + y + "] opened");
    }

    @Override
    public void flag(int x, int y) {
        getTile(x, y).flag();
        this.viewNotifier.notifyFlagged(x,y);
        System.out.println("Tile [" + x + ";" + y + "] flagged");
    }

    @Override
    public void unflag(int x, int y) {
        getTile(x, y).unflag();
        this.viewNotifier.notifyUnflagged(x,y);
        System.out.println("Tile [" + x + ";" + y + "] unflagged");
    }

    @Override
    // TODO is dit alle nul tegels clearen?
    //volgens mij is het dat de eerste tegel geen bom mag zijn
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
