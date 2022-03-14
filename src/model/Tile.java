package model;

import view.MinesweeperView;
import view.TileView;

public class Tile extends AbstractTile {
    private boolean bomb;
    private boolean flagged;
    private boolean open;
    //private boolean isOpened;


    public Tile(boolean bomb) {
        this.bomb = bomb;
        flagged = false;
        open = false;
    }


    @Override
    public boolean open() {
        open = true;
        return false;
    }

    @Override
    public void flag() {
        flagged = true;
    }

    @Override
    public void unflag() {
        flagged = false;
    }

    @Override
    public boolean isFlagged() {
        return flagged;
    }

    @Override
    public boolean isExplosive() {
        return bomb;
    }

    @Override
    public boolean isOpened() {
        return false;
    }
}
