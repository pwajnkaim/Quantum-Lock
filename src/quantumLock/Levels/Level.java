/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Levels;

import city.cs.engine.World;
import city.cs.engine.WorldView;
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

    private List<SlidingDoor> slidingDoors;

    public void initialize(QuantumLock quantumLock) {
        this.quantumLock = quantumLock;
        slidingDoors = new ArrayList<>();
    }

    public void levelComplete() {
        quantumLock.nextLevel();
    }
    public void levelReset() {
        quantumLock.resetLevel();
    }

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
}
