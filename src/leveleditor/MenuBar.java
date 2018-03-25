package leveleditor;

import javax.swing.*;

public class MenuBar extends JMenuBar{
    public MenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMap = new JMenuItem("New");
        fileMenu.add(newMap);
        JMenuItem openMap = new JMenuItem("Open");
        fileMenu.add(openMap);
        JMenuItem saveMap = new JMenuItem("Save");
        fileMenu.add(saveMap);
        JMenuItem saveAsMap = new JMenuItem("Save As");
        fileMenu.add(saveAsMap);
        this.add(fileMenu);
    }
}
