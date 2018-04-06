package leveleditor;

import city.cs.engine.StaticBody;
import leveleditor.bodies.*;
import leveleditor.bodies.Button;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

public class ControlPanel {
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
    private JPanel dynamic;
    private JTextField densityTF;
    private JPanel player;
    private JCheckBox gunDisabled;
    private JPanel button;
    private JTextField buttonDensityTF;
    private JList doorList;
    private JComboBox addDoorCB;
    private JButton addDoorButton;
    private JButton removeDoorButton;

    private CardLayout cardLayout;
    private DefaultListModel doorListModel;

    public ControlPanel() {
        $$$setupUI$$$();
        addDoorCB.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof FakeBody)
                    value = ((FakeBody) value).getName(); //make combobox render name not toString
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        cardLayout = (CardLayout) extraOptions.getLayout();
        clearAll();

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
        newBodySelector.addItem(new Object() {
            @Override
            public String toString() {
                return "Ball";
            }
        });
        newBodySelector.addItem(new Object() {
            @Override
            public String toString() {
                return "Button";
            }
        });
        newBodySelector.addItem(new Object() {
            @Override
            public String toString() {
                return "Exit";
            }
        });
        newBodySelector.addItem(new Object() {
            @Override
            public String toString() {
                return "Player";
            }
        });

        //Basic
        nameTF.addActionListener(e -> { //change name
            if (controller().selected != null) controller().selected.setName(nameTF.getText());
            controller().view.requestFocus();
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
            if (controller().selected != null)
                controller().selected.setAngle(Float.parseFloat(rotationTF.getText()));
            controller().view.requestFocus();
        });
        deleteButton.addActionListener(e -> { //delete object
            if (controller().selected != null) {
                controller().selected.destroy();
                controller().selected = null;
            }
            clearAll();
        });
        //SlidingDoor
        xOpenPosTF.addActionListener(e -> {
            changeOpenPosition();
        });
        yOpenPosTF.addActionListener(e -> {
            changeOpenPosition();
        });
        xClosedPosTF.addActionListener(e -> {
            changeClosedPosition();
        });
        yClosedPosTF.addActionListener(e -> {
            changeClosedPosition();
        });
        slidingSpeedTF.addActionListener(e -> {
            if (controller().selected != null)
                ((SlidingDoor) controller().selected).setSlidingSpeed(Float.parseFloat(slidingSpeedTF.getText()));
            controller().view.requestFocus();
        });
        stayOpenCheckBox.addActionListener(e -> {
            if (controller().selected != null)
                ((SlidingDoor) controller().selected).setStayOpen(stayOpenCheckBox.isSelected());
            controller().view.requestFocus();
        });
        startOpenCheckBox.addActionListener(e -> {
            if (controller().selected != null) {
                SlidingDoor door = (SlidingDoor) controller().selected;
                door.setStartOpen(startOpenCheckBox.isSelected());
                if (door.isStartOpen()) door.setPosition(door.getOpenPos());
                else door.setPosition(door.getClosedPos());
                slidingDoorSelected(door);
            }
            controller().view.requestFocus();
        });
        //dynamic
        densityTF.addActionListener(e -> changeDensity(densityTF));
        //button
        buttonDensityTF.addActionListener(e -> changeDensity(buttonDensityTF));
        addDoorButton.addActionListener(e -> {
            if (controller().selected != null && addDoorCB.getItemCount() > 0) {
                Button button = (Button) controller().selected;
                button.addDoor((SlidingDoor) addDoorCB.getSelectedItem());
                buttonSelected((Button) controller().selected);
            }
            controller().view.requestFocus();
        });
        doorList.addListSelectionListener(e -> removeDoorButton.setEnabled(true));
        removeDoorButton.addActionListener(e -> {
            if (controller().selected != null) {
                Button button = (Button) controller().selected;
                button.removeDoor((SlidingDoor) doorList.getSelectedValue());
                buttonSelected((Button) controller().selected);
            }
            controller().view.requestFocus();
        });
        //player
        gunDisabled.addActionListener(e -> {
            if (controller().selected != null) {
                ((Player) controller().selected).setGunDisabled(gunDisabled.isSelected());
            }
            controller().view.requestFocus();
        });
        //other
        createNewBodyButton.addActionListener(e -> {
            String bodyType = newBodySelector.getSelectedItem().toString();
            if (bodyType.equals("BoxStaticBody")) {
                controller().newBody(BoxStaticBody.class);
            } else if (bodyType.equals("CircleStaticBody")) {
                controller().newBody(CircleStaticBody.class);
            } else if (bodyType.equals("SlidingDoor")) {
                controller().newBody(SlidingDoor.class);
            } else if (bodyType.equals("Crate")) {
                controller().newBody(Crate.class);
            } else if (bodyType.equals("Ball")) {
                controller().newBody(Ball.class);
            } else if (bodyType.equals("Button")) {
                controller().newBody(Button.class);
            } else if (bodyType.equals("Exit")) {
                controller().newBody(Exit.class);
            } else if (bodyType.equals("Player")) {
                controller().newBody(Player.class);
            }
        });
    }

    private MouseController controller() { //get mouse controller
        return LevelEditor.mouseController;
    }

    private void changePosition() {
        FakeBody body = controller().selected;
        try {
            if (body != null) {
                // move body
                body.setPosition(new Vec2(Float.parseFloat(xPositionTF.getText()), Float.parseFloat(yPositionTF.getText())));
                // update door's closed/open position
                if (body instanceof SlidingDoor)
                    slidingDoorSelected((SlidingDoor) body);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Invalid Position", "Warning", JOptionPane.ERROR_MESSAGE);
        }
        controller().view.requestFocus();
    }

    private void changeSize() {
        try {
            if (controller().selected != null) {
                float width = Float.parseFloat(widthTF.getText());
                float height = (heightTF.getText().equals("")) ? height = 1 : Float.parseFloat(heightTF.getText());
                if (width < 0.05f || width > 100f || height < 0.05f || height > 100f) {
                    throw new NumberFormatException();
                }
                if (controller().selected instanceof CircleStaticBody || controller().selected instanceof Ball) {
                    controller().select(controller().selected.setSize(new Vec2(width, 0)));
                } else
                    controller().select(controller().selected.setSize(new Vec2(width, height)));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Invalid Size", "Warning", JOptionPane.ERROR_MESSAGE);
        }
        controller().view.requestFocus();
    }

    private void changeDensity(JTextField textField) {
        try {
            if (controller().selected != null)
                ((Density) controller().selected).setDensity(Float.parseFloat(textField.getText()));
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(panel, "Invalid Number", "Warning", JOptionPane.ERROR_MESSAGE);
        }
        controller().view.requestFocus();
    }

    private void changeOpenPosition() {
        SlidingDoor body = (SlidingDoor) controller().selected;
        try {
            if (body != null) {
                body.setOpenPos(new Vec2(Float.parseFloat(xOpenPosTF.getText()), Float.parseFloat(yOpenPosTF.getText())));
                if (body.isStartOpen()) {
                    body.setPosition(body.getOpenPos());
                    slidingDoorSelected(body);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Invalid Position", "Warning", JOptionPane.ERROR_MESSAGE);
        }
        controller().view.requestFocus();
    }

    private void changeClosedPosition() {
        SlidingDoor body = (SlidingDoor) controller().selected;
        try {
            if (body != null) {
                body.setClosedPos(new Vec2(Float.parseFloat(xClosedPosTF.getText()), Float.parseFloat(yClosedPosTF.getText())));
                if (!body.isStartOpen()) {
                    body.setPosition(body.getClosedPos());
                    slidingDoorSelected(body);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Invalid Position", "Warning", JOptionPane.ERROR_MESSAGE);
        }
        controller().view.requestFocus();
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
        setBasic(body);
        heightTF.setText("");
        heightTF.setEnabled(false);
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

    public void crateSelected(Crate body) {
        enableAll();
        selected.setText("Crate");
        setBasic(body);
        widthTF.setText("");
        widthTF.setEnabled(false);
        heightTF.setText("");
        heightTF.setEnabled(false);
        cardLayout.show(extraOptions, "dynamic");
        densityTF.setText("" + body.getDensity());
    }

    public void ballSelected(Ball body) {
        enableAll();
        selected.setText("Ball");
        setBasic(body);
        heightTF.setText("");
        heightTF.setEnabled(false);
        cardLayout.show(extraOptions, "dynamic");
        densityTF.setText("" + body.getDensity());
    }

    public void buttonSelected(Button body) {
        enableAll();
        selected.setText("Button");
        setBasic(body);
        widthTF.setText("");
        widthTF.setEnabled(false);
        heightTF.setText("");
        heightTF.setEnabled(false);
        cardLayout.show(extraOptions, "button");
        buttonDensityTF.setText(Float.toString(body.getDensity()));
        doorListModel.removeAllElements();
        for (SlidingDoor door : body.doors) {
            doorListModel.addElement(door);
        }
        addDoorCB.removeAllItems();
        for (StaticBody body_ : LevelEditor.world.getStaticBodies()) {
            if (body_ instanceof SlidingDoor) {
                addDoorCB.addItem(body_);
            }
        }
        removeDoorButton.setEnabled(false);
    }

    public void exitSelected(Exit body) {
        enableAll();
        selected.setText("Exit");
        setBasic(body);
        widthTF.setText("");
        widthTF.setEnabled(false);
        heightTF.setText("");
        heightTF.setEnabled(false);
        cardLayout.show(extraOptions, "nothing");
    }

    public void playerSelected(Player body) {
        enableAll();
        selected.setText("Player");
        setBasic(body);
        widthTF.setText("");
        widthTF.setEnabled(false);
        heightTF.setText("");
        heightTF.setEnabled(false);
        cardLayout.show(extraOptions, "player");
        gunDisabled.setSelected(body.isGunDisabled());
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
        if (LevelEditor.mouseController != null)
            LevelEditor.mouseController.selected = null;
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
        addDoorCB.removeAllItems();
        doorListModel.removeAllElements();
    }

    private void enableAll() {
        nameTF.setEnabled(true);
        xPositionTF.setEnabled(true);
        yPositionTF.setEnabled(true);
        widthTF.setEnabled(true);
        heightTF.setEnabled(true);
        rotationTF.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    private void createUIComponents() {
        doorListModel = new DefaultListModel();
        doorList = new JList(doorListModel);
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
        newBodySelector.setFocusable(false);
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
        dynamic = new JPanel();
        dynamic.setLayout(new GridBagLayout());
        extraOptions.add(dynamic, "dynamic");
        final JLabel label7 = new JLabel();
        label7.setText("Density");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        dynamic.add(label7, gbc);
        densityTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        dynamic.add(densityTF, gbc);
        final JPanel spacer18 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        dynamic.add(spacer18, gbc);
        player = new JPanel();
        player.setLayout(new GridBagLayout());
        extraOptions.add(player, "player");
        gunDisabled = new JCheckBox();
        gunDisabled.setText("Gun Disabled");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        player.add(gunDisabled, gbc);
        final JPanel spacer19 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.VERTICAL;
        player.add(spacer19, gbc);
        button = new JPanel();
        button.setLayout(new GridBagLayout());
        extraOptions.add(button, "button");
        final JLabel label8 = new JLabel();
        label8.setText("Density");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        button.add(label8, gbc);
        buttonDensityTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        button.add(buttonDensityTF, gbc);
        final JPanel spacer20 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        button.add(spacer20, gbc);
        final JLabel label9 = new JLabel();
        label9.setText("Connected Doors");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        button.add(label9, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        button.add(panel2, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(doorList, gbc);
        addDoorCB = new JComboBox();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        button.add(addDoorCB, gbc);
        addDoorButton = new JButton();
        addDoorButton.setText("Connect Door");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        button.add(addDoorButton, gbc);
        final JPanel spacer21 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.VERTICAL;
        button.add(spacer21, gbc);
        removeDoorButton = new JButton();
        removeDoorButton.setText("Disconnect Door");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        button.add(removeDoorButton, gbc);
        final JPanel spacer22 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        button.add(spacer22, gbc);
        final JPanel spacer23 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 14;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer23, gbc);
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
        final JPanel spacer24 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(spacer24, gbc);
        final JLabel label10 = new JLabel();
        label10.setText("Rotation");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label10, gbc);
        rotationTF = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(rotationTF, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("Name");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label11, gbc);
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
        final JPanel spacer25 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(spacer25, gbc);
        label1.setLabelFor(xPositionTF);
        label6.setLabelFor(slidingSpeedTF);
        label11.setLabelFor(nameTF);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }
}
