
import city.cs.engine.*;

import java.awt.event.*;

import org.jbox2d.common.Vec2;

import javax.swing.*;


/**
 *
 * @author pwajn
 */
public class MouseController extends MouseAdapter implements ActionListener{
    private KeyController keyController;

    final private WorldView view;

    private Body holding = null;
    private Body selected = null;

    JTextField textBox;
    JTextField textBox2;

    Vec2 offset; //offset between mouse and screen centre, used for dragging
    Vec2 holdingOffset; //used for dragging bodies (so their center doesn't snap to the mouse)'
    MouseController(WorldView view) {
        this.view = view;
        textBox = new JTextField();
        textBox2 = new JTextField();
        textBox.setBounds(1,1,50,20);
        textBox2.setBounds(1,1,50,20);
        view.add(textBox);
        view.add(textBox2);
        textBox.addActionListener(this);
        textBox2.addActionListener(this);
        toggleTextBox(false);
    }

    public void setKeyController(KeyController keyController) {
        this.keyController = keyController;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        view.requestFocus();
        Vec2 mousePos = view.viewToWorld(e.getPoint());

        toggleTextBox(false);
        offset = mousePos;
        for (StaticBody body : view.getWorld().getStaticBodies()) {
            if(body.contains(mousePos)){ //if mouse clicked on a body
                if (e.getButton() == 1) { //left click
                    toggleTextBox(false);
                    holding = body;
                    holdingOffset = holding.getPosition().sub(mousePos);
                } else if(e.getButton() == 3) {// right click
                    selected = body;
                    textBox.setBounds(e.getX(),e.getY(),50,20);
                    if (selected instanceof BoxStaticBody) {
                        textBox2.setBounds(e.getX(),e.getY()+25,50,20);
                        textBox.setText(Float.toString(((BoxStaticBody)selected).getSize().x));
                        textBox2.setText(Float.toString(((BoxStaticBody)selected).getSize().y));
                    }
                    toggleTextBox(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (holding != null) {
            //System.out.println(holding.getPosition());
            Vec2 roundedPos = holding.getPosition();
            roundedPos.x = Math.round(roundedPos.x*2)/2.f; //round to nearest multiple of 5
            roundedPos.y = Math.round(roundedPos.y*2)/2.f;
            holding.setPosition(roundedPos);
            //System.out.println(holding.getPosition());
        }
        holding = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Vec2 mousePos = view.viewToWorld(e.getPoint());
        if (holding != null) {
            holding.setPosition(mousePos.add(holdingOffset));
        } else if(selected == null){
            Vec2 delta = view.getCentre().sub(mousePos);
            view.setCentre(delta.add(offset));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            float value = Float.parseFloat(textBox.getText());
            if (selected instanceof BoxStaticBody) {
                float value2 = Float.parseFloat(textBox2.getText());
                value = (value <= 0) ? 0.5f : value; //don't let value be 0 or less
                value2 = (value2 <= 0) ? 0.5f : value2;
                BoxStaticBody body = new BoxStaticBody(view.getWorld(), new Vec2(value,value2));
                body.setPosition(selected.getPosition());
                selected.destroy();
            }
            toggleTextBox(false);
        } catch (NumberFormatException err) {
            System.out.println("Invalid Number");
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            view.setZoom(view.getZoom()+0.6f);
        } else {
            view.setZoom(view.getZoom()-0.6f);
        }
    }

    private void toggleTextBox(Boolean bool) {
        textBox.setEnabled(bool);
        textBox.setVisible(bool);
        textBox2.setEnabled(bool);
        textBox2.setVisible(bool);
        if(bool) {
            textBox.requestFocus();
        } else {
            selected = null;
            textBox.setText("");
            textBox2.setText("");
        }
    }
}