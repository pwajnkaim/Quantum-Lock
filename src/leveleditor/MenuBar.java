package leveleditor;

import javax.swing.*;
import java.io.File;

public class MenuBar extends JMenuBar{
    public MenuBar() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMap = new JMenuItem("New");
        newMap.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(LevelEditor.frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            }
        });

        JMenuItem openMap = new JMenuItem("Open");

        JMenuItem saveMap = new JMenuItem("Save");

        JMenuItem saveAsMap = new JMenuItem("Save As");

        fileMenu.add(newMap);
        fileMenu.add(openMap);
        fileMenu.add(saveMap);
        fileMenu.add(saveAsMap);

        this.add(fileMenu);
    }

    public void save() {
        System.out.println("cool");
    }
}
