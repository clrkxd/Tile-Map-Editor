package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TilePanel extends JPanel {
    private ArrayList<Tile> tiles;
    private int tileSize;
    private int selected = -1;

    public interface TileSelectionListener {
        void onTileSelected(int tileId);
    }

    private TileSelectionListener listener;

    public TilePanel(ArrayList<Tile> tiles, int tileSize) {
        this.tiles = tiles;
        this.tileSize = tileSize;
    
        setPreferredSize(new Dimension(tileSize * 4, 600));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int cols = getWidth() / tileSize;
                int x = e.getX() / tileSize;
                int y = e.getY() / tileSize;
                int index = y * cols + x;
                if (index >= 0 && index < tiles.size()) {
                    selected = index;
                    repaint();
                    if (listener != null) {
                        listener.onTileSelected(selected);
                    }
                }
            }
        });
    }

    public void setTileSelectionListener(TileSelectionListener l) {
        this.listener = l;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cols = getWidth() / tileSize;
        for (int i = 0; i < tiles.size(); i++) {
            int x = (i % cols) * tileSize;
            int y = (i / cols) * tileSize;
            g.drawImage(tiles.get(i).image, x, y, tileSize, tileSize, null);
            if (i == selected) {
                g.setColor(Color.RED);
                g.drawRect(x, y, tileSize - 1, tileSize - 1);
            }
        }
    }
}
