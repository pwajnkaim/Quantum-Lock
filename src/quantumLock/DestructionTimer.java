/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketjumper;

import city.cs.engine.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author pwajn
 */
public class DestructionTimer implements ActionListener {
    private final Body body;
    
    public DestructionTimer(Body body) {
        this.body = body;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        body.destroy();
    }
}
