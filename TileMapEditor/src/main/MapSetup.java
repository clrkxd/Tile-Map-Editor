package main;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class MapSetup extends JPanel {

    // UI components
    JPanel setupPanel;
    JLabel LBLmapSize, LBLtileSize, LBLimport;
    JTextField TXTmapSize, TXTtileSize;
    JButton importButton, startButton;
    JLabel selectedFileLabel;

    // Data
    File selectedFile;

    // Listener
    private MapSetupListener listener;

    public interface MapSetupListener {
        void onSetupComplete(int mapSize, int tileSize, File tilesetFile);
    }

    // Constructor
    public MapSetup(MapSetupListener listener) {
        this.listener = listener;

        setLayout(null);

        setupPane();
        getInputs();

        setupPanel.setBounds(10, 10, 950, 750);
        add(setupPanel);
    }

    private void setupPane() {
        setupPanel = new JPanel();
        setupPanel.setLayout(null);
        setupPanel.setBounds(10, 10, 950, 750);
        setupPanel.setBorder(BorderFactory.createTitledBorder("Setup"));

        // Labels & Fields
        LBLmapSize = new JLabel("Input Map Size:");
        LBLtileSize = new JLabel("Input Tile Size:");

        TXTmapSize = new JTextField(10);
        TXTtileSize = new JTextField(10);

        LBLmapSize.setBounds(20, 30, 150, 20);
        LBLtileSize.setBounds(20, 50, 150, 20);
        TXTmapSize.setBounds(120, 30, 100, 20);
        TXTtileSize.setBounds(120, 50, 100, 20);

        // Import Button
        LBLimport = new JLabel("Import Tileset:");
        LBLimport.setBounds(20, 180, 150, 20);

        importButton = new JButton("Choose PNG");
        importButton.setBounds(120, 180, 120, 25);

        selectedFileLabel = new JLabel("No file selected");
        selectedFileLabel.setBounds(120, 210, 300, 20);

        // Add to setupPanel
        setupPanel.add(LBLmapSize);
        setupPanel.add(LBLtileSize);
        setupPanel.add(TXTmapSize);
        setupPanel.add(TXTtileSize);
        setupPanel.add(LBLimport);
        setupPanel.add(importButton);
        setupPanel.add(selectedFileLabel);

        // Import action
        importButton.addActionListener(e -> {
            File file = importTileset();
            if (file != null) {
                selectedFile = file;
                selectedFileLabel.setText(file.getName());
            }
        });
    }

    private void getInputs() {
        startButton = new JButton("Start Editor");
        startButton.setBounds(20, 250, 200, 30);

        startButton.addActionListener(e -> {
            try {
                int mapS = Integer.parseInt(TXTmapSize.getText());
                int tileS = Integer.parseInt(TXTtileSize.getText());

                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(this, "Please import a tileset PNG first.");
                    return;
                }

                // ✅ Pass data to listener for editor switching
                if (listener != null) {
                    listener.onSetupComplete(mapS, tileS, selectedFile);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please input valid numbers for map and tile sizes.");
            }
        });

        setupPanel.add(startButton);
    }

    private File importTileset() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Tileset PNG");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        chooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
            }

            public String getDescription() {
                return "PNG Images (*.png)";
            }
        });

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }

        return null;
    }
}
