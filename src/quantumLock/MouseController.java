/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock;

import city.cs.engine.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.Timer;

import quantumLock.Levels.Level;


/**
 *
 * @author pwajn
 */
public class MouseController extends MouseAdapter{
    
    final private WorldView view;
    final private Level world;
    
    MouseController(WorldView view) {
        this.view = view;
        this.world = (Level)view.getWorld();
    }
   
    
    //private int amount = 20;
    //double angle = (Math.PI*2)/amount;
    
    @Override
    public void mousePressed(MouseEvent e) {
        view.requestFocus();
        if(world.getPlayer().grabbedBody == null) {
            if(e.getButton() == 1) { //left click
                world.getPlayer().fire();
            }
            if(e.getButton() == 3) { //right click
                world.getPlayer().grab();
            }
        } else {
            if(e.getButton() == 1) { //left click
                world.getPlayer().throwGrabbed();
            } else if(e.getButton() == 3) { //right click
                world.getPlayer().dropGrabbed();
            }
        }
        
        /*for (int i = 0; i < amount; i++) {
            Vec2 vector = new Vec2((float)Math.cos(angle*i), (float)Math.sin(angle*i));
            Particle particle = new Particle(view.getWorld());
            particle.setBullet(true);
            particle.setPosition((view.viewToWorld(e.getPoint())).add(vector.mul(0.5f)));
            particle.applyForce(vector.mul(50000));
            particle.startTimer();
        }*/
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    }
}

class Particle extends DynamicBody {
    
    int duration = 100;
    float density = 1000;
    private final Shape ballShape = new CircleShape(0.1f);
    
    Timer timer;
    DestructionTimer task;
    
    public Particle(World world) {
        super(world);
        Fixture fixture = new SolidFixture(this, ballShape, density);
    }
    
    public void startTimer(){
        timer = new Timer(duration, new DestructionTimer(this));
        timer.start();
    }
}
