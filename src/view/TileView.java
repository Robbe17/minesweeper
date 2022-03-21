package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseListener;

import notifier.ITileStateNotifier;
import view.MinesweeperView.AssetPath;

public class TileView extends JButton implements ITileStateNotifier {
    private static ImageIcon flagIcon = new ImageIcon(AssetPath.FLAG_ICON);
    private static ImageIcon bombIcon = new ImageIcon(AssetPath.BOMB_ICON);

    private int x, y;
    public TileView(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getPositionX() {return x;}
    public int getPositionY() {return y;}

    @Override
    public void notifyOpened(int explosiveNeighbourCount) {
        super.setIcon(null);
        super.setText((explosiveNeighbourCount> 0)? Integer.toString(explosiveNeighbourCount) : "");
        super.setEnabled(false);
    }

    @Override
    public void notifyFlagged() {
        super.setText("F");
        super.setForeground(new Color(255,0,255));
        super.setIcon(flagIcon);
    }

    @Override
    public void notifyUnflagged() {
        super.setText("");
        super.setIcon(null);
    }

    public void removalAllMouseListeners() {
        for (MouseListener listener : super.getMouseListeners()) 
            this.removeMouseListener(listener);    
    }

    @Override
    public void notifyExplode() {
        super.setText("B");
        super.setIcon(bombIcon);
        super.setEnabled(false);
    }

    @Override
    public String toString(){
        return "["+Integer.toString(x)+","+Integer.toString(y)+"]";
    }
    
}
