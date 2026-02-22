package main; 

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tile Map Editor");
        CardLayout layout = new CardLayout();
        JPanel cards = new JPanel(layout);

        // Map setup panel with listener
        MapSetup mapSetup = new MapSetup((mapSize, tileSize, tilesetFile) -> {
            EditorPanel editor = new EditorPanel(mapSize, mapSize, tileSize, tilesetFile);
            cards.add(editor, "editor");
            layout.show(cards, "editor");
        });

        cards.add(mapSetup, "setup");

        frame.setContentPane(cards);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}
