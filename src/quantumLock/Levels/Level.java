/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rocketjumper.Levels;

import city.cs.engine.World;
import city.cs.engine.WorldView;
import rocketjumper.PlayerCharacter;
import rocketjumper.RocketJumper;
import rocketjumper.SlidingDoor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pwajn
 */
public class Level extends World{
    protected RocketJumper rocketJumper;
    protected WorldView view;
    protected PlayerCharacter player;

    private List<SlidingDoor> slidingDoors;

    public void initialize(RocketJumper rocketJumper) {
        this.rocketJumper = rocketJumper;
        slidingDoors = new ArrayList<>();
    }

    public void levelComplete() {
        rocketJumper.nextLevel();
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
