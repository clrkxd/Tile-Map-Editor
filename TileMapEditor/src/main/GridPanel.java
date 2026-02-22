package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GridPanel extends JPanel {
    private int[][] tileMap;
    private int tileSize;
    private double zoom = 1.0;

    private ArrayList<Tile> tiles;
    private int selectedTile = -1;

    public GridPanel(int width, int height, int tileSize, ArrayList<Tile> tiles) {
        this.tileSize = tileSize;
        this.tiles = tiles;
        this.tileMap = new int[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                tileMap[y][x] = -1;

        setPreferredSize(new Dimension((int)(width * tileSize * zoom), (int)(height * tileSize * zoom)));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = (int)(e.getX() / (tileSize * zoom));
                int y = (int)(e.getY() / (tileSize * zoom));
                if (x >= 0 && y >= 0 && y < tileMap.length && x < tileMap[0].length) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        tileMap[y][x] = selectedTile;
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        tileMap[y][x] = -1;
                    }
                    repaint();
                }
            }
        });

        addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                zoom += e.getPreciseWheelRotation() * -0.1;
                zoom = Math.max(0.5, Math.min(zoom, 4.0));
                setPreferredSize(new Dimension((int)(tileMap[0].length * tileSize * zoom), (int)(tileMap.length * tileSize * zoom)));
                revalidate();
                repaint();
            }
        });
    }

    public void setSelectedTile(int tileId) {
        this.selectedTile = tileId;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < tileMap.length; y++) {
            for (int x = 0; x < tileMap[0].length; x++) {
                int id = tileMap[y][x];
                int drawX = (int)(x * tileSize * zoom);
                int drawY = (int)(y * tileSize * zoom);

                if (id >= 0 && id < tiles.size()) {
                    g.drawImage(tiles.get(id).image, drawX, drawY, (int)(tileSize * zoom), (int)(tileSize * zoom), null);
                }

                g.setColor(Color.GRAY);
                g.drawRect(drawX, drawY, (int)(tileSize * zoom), (int)(tileSize * zoom));
            }
        }
    }

    public void exportToTextFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Tile Map As Text");
        int result = chooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            try (PrintWriter out = new PrintWriter(file)) {
                for (int y = 0; y < tileMap.length; y++) {
                    for (int x = 0; x < tileMap[0].length; x++) {
                        out.print(tileMap[y][x]);
                        if (x < tileMap[0].length - 1)
                            out.print(" ");
                    }
                    out.println();
                }
                JOptionPane.showMessageDialog(this, "Exported successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error exporting file.");
                e.printStackTrace();
            }
        }
    }

    public void importFromTextFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (Scanner scanner = new Scanner(file)) {
                for (int y = 0; y < tileMap.length; y++) {
                    if (!scanner.hasNextLine()) break;
                    String line = scanner.nextLine();
                    String[] tokens = line.trim().split("\\s+");

                    for (int x = 0; x < tileMap[0].length && x < tokens.length; x++) {
                        int tileID = Integer.parseInt(tokens[x]);
                        if (tileID >= -1 && tileID < tiles.size()) {
                            tileMap[y][x] = tileID;
                        }
                    }
                }
                repaint();
                JOptionPane.showMessageDialog(this, "Imported successfully!");
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error importing file.");
                e.printStackTrace();
            }
        }
    }
}
