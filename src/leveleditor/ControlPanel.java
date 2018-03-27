package leveleditor;

import leveleditor.bodies.BoxStaticBody;
import leveleditor.bodies.CircleStaticBody;
import leveleditor.bodies.FakeBody;
import leveleditor.bodies.SlidingDoor;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel {
    private final MouseController mouseController;

    private JTextField xPositionTF;
    private JTextField yPositionTF;
    private JComboBox newBodySelector;
    private JButton createNewBodyButton;
    private JTextField widthTF;
    private JTextField heightTF;
    public JPanel extraOptions;
    private JPanel nothing;
    private JPanel slidingDoor;
    private JTextField xOpenPosTF;
    private JTextField xClosedPosTF;
    private JCheckBox startOpenCheckBox;
    private JTextField yOpenPosTF;
    private JTextField yClosedPosTF;
    private JTextField slidingSpeedTF;
    private JCheckBox stayOpenCheckBox;
    public JPanel panel;
    private JTextField rotationTF;
    private JLabel selected;
    private JTextField nameTF;
    private JButton deleteButton;

    private CardLayout cardLayout;

    public ControlPanel(MouseController mouseController) {
        cardLayout = (CardLayout) extraOptions.getLayout();
        clearAll();
        this.mouseController = mouseController;

        newBodySelector.addItem(new Object() {
            @Override
            public String toString() {
                return "BoxStaticBody";
            }
        });
        newBodySelector.addItem(new Object() {
            @Override
            public String toString() {
                return "CircleStaticBody";
            }
        });
        newBodySelector.addItem(new Object() {
            @Override
            public String toString() {
                return "SlidingDoor";
            }
        });
        newBodySelector.addItem(new Object() {
            @Override
            public String toString() {
                return "Crate";
            }
        });

        //Basic
        nameTF.addActionListener(e -> { //change name
            if (mouseController.selected != null) mouseController.selected.setName(nameTF.getText());
            mouseController.view.requestFocus();
        });
        xPositionTF.addActionListener(e -> { //change X position
            changePosition();
        });
        yPositionTF.addActionListener(e -> { //change Y position
            changePosition();
        });
        widthTF.addActionListener(e -> { //change width
            changeSize();
        });
        heightTF.addActionListener(e -> { //change height
            changeSize();
        });
        rotationTF.addActionListener(e -> { //change rotation
            if (mouseController.selected != null)
                mouseController.selected.setAngle(Float.parseFloat(rotationTF.getText()));
            mouseController.view.requestFocus();
        });
        deleteButton.addActionListener(e -> { //delete object
            if (mouseController.selected != null) {
                mouseController.selected.destroy();
                mouseController.selected = null;
            }
            clearAll();
        });
        //SlidingDoor
        stayOpenCheckBox.addActionListener(e -> {
            if (mouseController.selected != null)
                ((SlidingDoor) mouseController.selected).setStayOpen(stayOpenCheckBox.isSelected());
            mouseController.view.requestFocus();
        });
        startOpenCheckBox.addActionListener(e -> {
            if (mouseController.selected != null) {
                SlidingDoor door = (SlidingDoor) mouseController.selected;
                door.setStartOpen(startOpenCheckBox.isSelected());
                if (door.isStartOpen()) door.setPosition(door.getOpenPos());
                else door.setPosition(door.getClosedPos());
                slidingDoorSelected(door);
            }
            mouseController.view.requestFocus();
        });
        //other
        createNewBodyButton.addActionListener(e -> {
            String bodyType = newBodySelector.getSelectedItem().toString();
            if (bodyType.equals("BoxStaticBody")) {
                mouseController.newBody(BoxStaticBody.class);
            } else if (bodyType.equals("CircleStaticBody")) {
                mouseController.newBody(CircleStaticBody.class);
            } else if (bodyType.equals("SlidingDoor")) {
                mouseController.newBody(SlidingDoor.class);
            }
        });
    }

    private void changePosition() {
        FakeBody body = mouseController.selected;
        if (body != null) {
            // move body
            body.setPosition(new Vec2(Float.parseFloat(xPositionTF.getText()), Float.parseFloat(yPositionTF.getText())));
            // update door's closed/open position
            if (body instanceof SlidingDoor)
                slidingDoorSelected((SlidingDoor) body);
        }
        mouseController.view.requestFocus();
    }
    private void changeSize() {
        if (mouseController.selected != null)
            mouseController.selected.setSize(new Vec2(Float.parseFloat(widthTF.getText()), Float.parseFloat(heightTF.getText())));
        mouseController.view.requestFocus();
    }

    public void boxStaticSelected(BoxStaticBody body) {
        enableAll();
        selected.setText("BoxStaticBody");
        setBasic(body);
        cardLayout.show(extraOptions, "nothing");
    }
    public void circleStaticSelected(CircleStaticBody body) {
        enableAll();
        selected.setText("CircleStaticBody");
        heightTF.setEnabled(false);
        heightTF.setText("");
        setBasic(body);
        cardLayout.show(extraOptions, "nothing");
    }
    public void slidingDoorSelected(SlidingDoor body) {
        enableAll();
        selected.setText("SlidingDoor");
        setBasic(body);
        cardLayout.show(extraOptions, "slidingDoor");
        xOpenPosTF.setText("" + body.getOpenPos().x);
        yOpenPosTF.setText("" + body.getOpenPos().y);
        xClosedPosTF.setText("" + body.getClosedPos().x);
        yClosedPosTF.setText("" + body.getClosedPos().y);
        slidingSpeedTF.setText("" + body.getSlidingSpeed());
        startOpenCheckBox.setSelected(body.isStartOpen());
        stayOpenCheckBox.setSelected(body.isStayOpen());
    }

    private void setBasic(FakeBody body) {
        nameTF.setText(body.getName());
        xPositionTF.setText("" + body.getPosition().x);
        yPositionTF.setText("" + body.getPosition().y);
        widthTF.setText("" + body.getSize().x);
        heightTF.setText("" + body.getSize().y);
        rotationTF.setText("" + body.getAngle());
    }

    public void clearAll() {
        selected.setText("No Body Selected");
        cardLayout.show(extraOptions, "nothing");
        nameTF.setEnabled(false);
        nameTF.setText("");
        xPositionTF.setEnabled(false);
        xPositionTF.setText("");
        yPositionTF.setEnabled(false);
        yPositionTF.setText("");
        widthTF.setEnabled(false);
        widthTF.setText("");
        heightTF.setEnabled(false);
        heightTF.setText("");
        rotationTF.setEnabled(false);
        rotationTF.setText("");
        deleteButton.setEnabled(false);
    }

    public void enableAll() {
        nameTF.setEnabled(true);
        xPositionTF.setEnabled(true);
        yPositionTF.setEnabled(true);
        widthTF.setEnabled(true);
        heightTF.setEnabled(true);
        rotationTF.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), null));
        final JPanel spacer1 = new JPanel();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer1, gbc);
        selected = new JLabel();
        selected.setText("No Body Selected");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(selected, gbc);
        xPositionTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(xPositionTF, gbc);
        final JSeparator separator1 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 17;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(separator1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 20;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 18;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer4, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Position");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label1, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer6, gbc);
        final JPanel spacer7 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer7, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Size");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label2, gbc);
        final JPanel spacer8 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer8, gbc);
        widthTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(widthTF, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 19;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(panel1, gbc);
        newBodySelector = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        newBodySelector.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(newBodySelector, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("New Body");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(label3, gbc);
        createNewBodyButton = new JButton();
        createNewBodyButton.setText("Create New Body");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(createNewBodyButton, gbc);
        final JPanel spacer9 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer9, gbc);
        final JPanel spacer10 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel1.add(spacer10, gbc);
        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 15;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane1, gbc);
        extraOptions = new JPanel();
        extraOptions.setLayout(new CardLayout(0, 0));
        scrollPane1.setViewportView(extraOptions);
        nothing = new JPanel();
        nothing.setLayout(new GridBagLayout());
        extraOptions.add(nothing, "nothing");
        slidingDoor = new JPanel();
        slidingDoor.setLayout(new GridBagLayout());
        extraOptions.add(slidingDoor, "slidingDoor");
        final JLabel label4 = new JLabel();
        label4.setText("Open Position");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        slidingDoor.add(label4, gbc);
        final JPanel spacer11 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        slidingDoor.add(spacer11, gbc);
        xOpenPosTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        slidingDoor.add(xOpenPosTF, gbc);
        final JPanel spacer12 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        slidingDoor.add(spacer12, gbc);
        final JPanel spacer13 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        slidingDoor.add(spacer13, gbc);
        final JLabel label5 = new JLabel();
        label5.setText("Closed Position");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        slidingDoor.add(label5, gbc);
        xClosedPosTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        slidingDoor.add(xClosedPosTF, gbc);
        final JPanel spacer14 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        slidingDoor.add(spacer14, gbc);
        startOpenCheckBox = new JCheckBox();
        startOpenCheckBox.setText("Start Open");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        slidingDoor.add(startOpenCheckBox, gbc);
        yOpenPosTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        slidingDoor.add(yOpenPosTF, gbc);
        final JPanel spacer15 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        slidingDoor.add(spacer15, gbc);
        yClosedPosTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        slidingDoor.add(yClosedPosTF, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("Sliding Speed");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        slidingDoor.add(label6, gbc);
        slidingSpeedTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        slidingDoor.add(slidingSpeedTF, gbc);
        final JPanel spacer16 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        slidingDoor.add(spacer16, gbc);
        final JPanel spacer17 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        slidingDoor.add(spacer17, gbc);
        stayOpenCheckBox = new JCheckBox();
        stayOpenCheckBox.setText("Stay Open");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        slidingDoor.add(stayOpenCheckBox, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        extraOptions.add(panel2, "Card1");
        final JPanel spacer18 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer18, gbc);
        heightTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(heightTF, gbc);
        yPositionTF = new JTextField();
        yPositionTF.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(yPositionTF, gbc);
        final JPanel spacer19 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer19, gbc);
        final JLabel label7 = new JLabel();
        label7.setText("Rotation");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label7, gbc);
        rotationTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(rotationTF, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label8, gbc);
        nameTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameTF, gbc);
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 16;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(deleteButton, gbc);
        final JPanel spacer20 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer20, gbc);
        label1.setLabelFor(xPositionTF);
        label6.setLabelFor(slidingSpeedTF);
        label8.setLabelFor(nameTF);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
