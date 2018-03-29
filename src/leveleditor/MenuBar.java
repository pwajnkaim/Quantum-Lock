package leveleditor;

import leveleditor.bodies.FileManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class MenuBar extends JMenuBar{
    private static final int NONE = 0;
    private static final int SAVING = 1;
    private static final int OPENING = 2;

    private static JFileChooser fileChooser;
    private static FileNameExtensionFilter filter = new FileNameExtensionFilter("Quantum Lock Map Files", "map");
    public MenuBar() {
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        JMenu fileMenu = new JMenu("File");
        JMenuItem newMap = new JMenuItem("New");
        newMap.addActionListener(e -> {

        });

        JMenuItem openMap = new JMenuItem("Open");
        openMap.addActionListener(e -> {
            browserDialog(OPENING);
        });

        JMenuItem saveMap = new JMenuItem("Save");
        saveMap.addActionListener(e -> {
            save();
        });

        JMenuItem saveAsMap = new JMenuItem("Save As");
        saveAsMap.addActionListener(e -> {
            browserDialog(SAVING);
        });

        fileMenu.add(newMap);
        fileMenu.add(openMap);
        fileMenu.add(saveMap);
        fileMenu.add(saveAsMap);

        this.add(fileMenu);
    }

    public static void save() {
        if (LevelEditor.saveFile != null) {
            FileManager.save();
        } else {
            browserDialog(SAVING);
        }
    }

    private static void browserDialog(int state) {
        fileChooser.setCurrentDirectory(new File("maps"));
        int result = 0;
        if (state == SAVING)
            result = fileChooser.showSaveDialog(LevelEditor.frame);
        else if (state == OPENING)
            result = fileChooser.showOpenDialog(LevelEditor.frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (state == SAVING) {
                File newFile;
                if(getFileExtension(selectedFile).equals("map")) {
                    FileManager.saveAs(selectedFile);
                } else {
                    FileManager.saveAs(new File(selectedFile+".map"));
                }
            } else if (state == OPENING) {
                FileManager.open(selectedFile);
            }
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }
}
