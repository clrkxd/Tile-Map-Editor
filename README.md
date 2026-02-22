This is my first repo!



TILEMAPEDITOR

A Java Swing-based Tile Map Editor that allows users to create, edit, and save tile-based maps using custom tilesets.

Features

Set map size and tile size at startup

Import custom tileset PNGs

Select tiles from the tileset to place on the map

Place tiles with left-click, remove with right-click

Zoom in/out the map using Ctrl + Mouse Wheel

Export maps to text files for saving

Import maps from text files to continue editing

Technologies

Java

Swing (GUI)

Java AWT (for graphics)

No external libraries required

Installation

Clone or download the repository.

Open the project in your preferred Java IDE (IntelliJ IDEA, Eclipse, etc.).

Make sure Java SDK is installed (Java 8 or above).

Run Main.java to start the editor.

Usage

Launch the program.

Input map size and tile size.

Import a tileset PNG (each tile must be square, e.g., 32x32 pixels).

Click Start Editor.

Select a tile from the left panel and paint it onto the grid.

Right-click to erase tiles.

Use Ctrl + Mouse Wheel to zoom in or out.

Use Export/Import buttons at the bottom to save/load your maps as text files.

File Format

Exported maps are saved as text files containing tile IDs in a grid format.

Example:

0 0 1 1 2
0 1 1 2 2
3 3 0 0 1

-1 indicates an empty tile.






Author

Your Name
