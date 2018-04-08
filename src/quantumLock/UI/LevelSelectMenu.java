package quantumLock.UI;

import quantumLock.QuantumLock;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LevelSelectMenu {
    private JList<String> levelList;
    public JPanel levelSelectPanel;
    private JButton backButton;
    private JButton playButton;

    DefaultListModel<String> listModel;

    public LevelSelectMenu() {
        $$$setupUI$$$();
        try {
            File directory = new File("maps");
            for (final File fileEntry : directory.listFiles()) {
                if (!fileEntry.isDirectory()) //display all levels from maps directory
                    listModel.addElement(fileEntry.getName().replaceFirst("[.][^.]+$", "")); //hide file extension
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(QuantumLock.frame, "maps directory not found", "Error", JOptionPane.ERROR_MESSAGE);
            QuantumLock.startMainMenu();
        }

        levelList.addListSelectionListener(e -> playButton.setEnabled(true));
        backButton.addActionListener(e -> QuantumLock.startMainMenu());
        playButton.addActionListener(e -> {
            String selected = levelList.getSelectedValue();
            if (selected.equals("Level1")) {
                QuantumLock.goToLevel(1);
            } else if (selected.equals("Level2")) {
                QuantumLock.goToLevel(2);
            } else if (selected.equals("Level3")) {
                QuantumLock.goToLevel(3);
            } else {
                QuantumLock.startCustomLevel(new File("maps/" + selected + ".map"));
            }
        });
    }

    private void createUIComponents() {
        listModel = new DefaultListModel<>();
        levelList = new JList<>(listModel);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        levelSelectPanel = new JPanel();
        levelSelectPanel.setLayout(new GridBagLayout());
        final JScrollPane scrollPane1 = new JScrollPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        levelSelectPanel.add(scrollPane1, gbc);
        scrollPane1.setViewportView(levelList);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        levelSelectPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 0.4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        levelSelectPanel.add(spacer2, gbc);
        backButton = new JButton();
        Font backButtonFont = this.$$$getFont$$$(null, -1, 14, backButton.getFont());
        if (backButtonFont != null) backButton.setFont(backButtonFont);
        backButton.setText("Back");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        levelSelectPanel.add(backButton, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        levelSelectPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        levelSelectPanel.add(spacer4, gbc);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Select a Level");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        levelSelectPanel.add(label1, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.VERTICAL;
        levelSelectPanel.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.VERTICAL;
        levelSelectPanel.add(spacer6, gbc);
        playButton = new JButton();
        playButton.setEnabled(false);
        Font playButtonFont = this.$$$getFont$$$(null, -1, 14, playButton.getFont());
        if (playButtonFont != null) playButton.setFont(playButtonFont);
        playButton.setText("Play");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        levelSelectPanel.add(playButton, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.VERTICAL;
        levelSelectPanel.add(spacer7, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return levelSelectPanel;
    }
}