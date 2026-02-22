package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditorPanel extends JPanel {

    private TilePanel tilePanel;
    private GridPanel gridPanel;
    private JScrollPane gridScroll;

    public EditorPanel(int mapWidth, int mapHeight, int tileSize, File tilesetFile) {
        setLayout(new BorderLayout());

        // Load and slice the tileset
        ArrayList<Tile> tiles = loadTileset(tilesetFile, tileSize);

        // Tile selection panel
        tilePanel = new TilePanel(tiles, tileSize);

        // Grid editor panel
        gridPanel = new GridPanel(mapWidth, mapHeight, tileSize, tiles);
        tilePanel.setTileSelectionListener(gridPanel::setSelectedTile);

        // Scrollable grid
        gridScroll = new JScrollPane(gridPanel);
        add(gridScroll, BorderLayout.CENTER);
        add(tilePanel, BorderLayout.WEST);

        // Export and Import buttons
        JButton exportButton = new JButton("Export to Text File");
        exportButton.addActionListener(e -> gridPanel.exportToTextFile());

        JButton importButton = new JButton("Import from Text File");
        importButton.addActionListener(e -> gridPanel.importFromTextFile());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(exportButton);
        bottomPanel.add(importButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private ArrayList<Tile> loadTileset(File file, int tileSize) {
        ArrayList<Tile> tiles = new ArrayList<>();
        try {
            BufferedImage tileset = ImageIO.read(file);
            int cols = tileset.getWidth() / tileSize;
            int rows = tileset.getHeight() / tileSize;

            int id = 0;
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    BufferedImage sub = tileset.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize);
                    tiles.add(new Tile(sub, id++));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tiles;
    }
}
