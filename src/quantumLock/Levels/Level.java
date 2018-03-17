/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Levels;

import city.cs.engine.DynamicBody;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import city.cs.engine.WorldView;
import org.jbox2d.common.Vec2;
import quantumLock.GrabArea;
import quantumLock.Levels.Level1.Level1;
import quantumLock.PlayerCharacter;
import quantumLock.QuantumLock;
import quantumLock.SlidingDoor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pwajn
 */
public class Level extends World{
    protected QuantumLock quantumLock;
    protected WorldView view;
    protected PlayerCharacter player;

    private List<SlidingDoor> slidingDoors = new ArrayList<>();

    public void initialize(QuantumLock quantumLock) {
        this.quantumLock = quantumLock;
    }

    public void levelComplete() {
        delete();
        this.oneStep();
        quantumLock.nextLevel();
    }

    public void levelReset() {

    }

    public void levelPopulate() {}

    public PlayerCharacter getPlayer(){
        return player;
    }
    
    public void setView(WorldView view) {
        this.view = view;
    }
    public WorldView getView() {
        return view;
    }

    protected void addSlidingDoor(SlidingDoor slidingDoor) {
        slidingDoors.add(slidingDoor);
    }
    public List<SlidingDoor> getSlidingDoors() {
        return slidingDoors;
    }

    public void delete() {
        for (StaticBody body : getStaticBodies()) {
            System.out.println(body);
            body.destroy();
        }
        for (DynamicBody body : getDynamicBodies()) {
            body.destroy();
        }
    }
}
