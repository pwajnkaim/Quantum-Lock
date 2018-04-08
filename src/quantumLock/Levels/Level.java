/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Levels;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import quantumLock.*;
import quantumLock.Objects.Ball;
import quantumLock.Objects.Crate;
import quantumLock.UI.EndLevelMenu;

import javax.swing.*;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pwajn
 */
public class Level extends World{
    private File file;

    protected WorldView view;
    protected PlayerCharacter player;
    private StepListener stepListener;

    public long startTime;
    public long currentTime;
    public long pauseTime;

    private List<SlidingDoor> slidingDoors = new ArrayList<>();

    public Level(File file) {
        this.file = file;
    }

    public void initialize() {
        //create player character
        player = new PlayerCharacter(this);
        //create level bodies
        levelPopulate();
        //set start time for timer
        startTime = System.currentTimeMillis();
    }

    public void levelComplete() {
        this.stop();
        QuantumLock.showLevelEnd();
    }

    public void levelReset() {
        startTime = System.currentTimeMillis();
        //delete everything except for player
        clearSlidingDoors();
        for (StaticBody body : getStaticBodies()) {
            if(!(body instanceof GrabArea)) body.destroy();
        }
        for (DynamicBody body : getDynamicBodies()) {
            if(!(body instanceof PlayerCharacter)) body.destroy();
        }
        player.setLinearVelocity(new Vec2(0,0));
        levelPopulate();
    }

    private void levelPopulate() {
        class BasicInfo {
            private String name;
            private float xPos, yPos, rotation, xSize, ySize, radius;
            private BasicInfo(XMLEventReader eventReader, boolean size, boolean circle) throws XMLStreamException{
                name = readData(eventReader);
                xPos = Float.parseFloat(readData(eventReader));
                yPos = Float.parseFloat(readData(eventReader));
                rotation = Float.parseFloat(readData(eventReader));
                if(circle)
                    radius = Float.parseFloat(readData(eventReader));
                else if(size){
                    xSize = Float.parseFloat(readData(eventReader));
                    ySize = Float.parseFloat(readData(eventReader));
                }
            }
        }
        try {
            ArrayList<SlidingDoor> doorList = new ArrayList<>();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("boxStaticBody")) {
                        BasicInfo bi = new BasicInfo(eventReader, true, false);
                        StaticBody body = new StaticBody(this, new BoxShape(bi.xSize,bi.ySize));
                        body.setName(bi.name);
                        body.setPosition(new Vec2(bi.xPos, bi.yPos));
                        body.setAngle(bi.rotation);
                    } else if(qName.equalsIgnoreCase("circleStaticBody")) {
                        BasicInfo bi = new BasicInfo(eventReader, false,true);
                        StaticBody body = new StaticBody(this, new CircleShape(bi.radius));
                        body.setName(bi.name);
                        body.setPosition(new Vec2(bi.xPos, bi.yPos));
                        body.setAngle(bi.rotation);
                    } else if (qName.equalsIgnoreCase("slidingDoor")) {
                        BasicInfo bi = new BasicInfo(eventReader, true, false);
                        float xOpenPos = Float.parseFloat(readData(eventReader));
                        float yOpenPos = Float.parseFloat(readData(eventReader));
                        float xClosedPos = Float.parseFloat(readData(eventReader));
                        float yClosedPos = Float.parseFloat(readData(eventReader));
                        float slidingSpeed = Float.parseFloat(readData(eventReader));
                        boolean startOpen = Boolean.parseBoolean(readData(eventReader));
                        boolean stayOpen = Boolean.parseBoolean(readData(eventReader));
                        SlidingDoor body = new SlidingDoor(this, new BoxShape(bi.xSize,bi.ySize), new Vec2(xOpenPos,yOpenPos), new Vec2(xClosedPos,yClosedPos), slidingSpeed, !startOpen);
                        body.setName(bi.name);
                        body.setPosition(new Vec2(bi.xPos, bi.yPos));
                        body.setAngle(bi.rotation);
                        body.stayOpen(stayOpen);
                        doorList.add(body);
                        addSlidingDoor(body);
                    } else if(qName.equalsIgnoreCase("crate")) {
                        BasicInfo bi = new BasicInfo(eventReader, false, false);
                        float density = Float.parseFloat(readData(eventReader));
                        Crate body = new Crate(this, new Vec2(bi.xPos,bi.yPos), density);
                        body.setName(bi.name);
                        body.setAngle(bi.rotation);
                    } else if(qName.equalsIgnoreCase("ball")) {
                        BasicInfo bi = new BasicInfo(eventReader, false, false);
                        float density = Float.parseFloat(readData(eventReader));
                        float radius = Float.parseFloat(readData(eventReader));
                        Ball body = new Ball(this, new Vec2(bi.xPos,bi.yPos), new CircleShape(radius), density);
                        body.setName(bi.name);
                        body.setAngle(bi.rotation);
                    } else if(qName.equalsIgnoreCase("button")) {
                        String name = readData(eventReader);
                        float xPos = Float.parseFloat(readData(eventReader));
                        float yPos = Float.parseFloat(readData(eventReader));
                        readData(eventReader); //skip rotation
                        float density = Float.parseFloat(readData(eventReader));
                        String connectedStr = readData(eventReader);
                        WeightButton body = new WeightButton(this, new Vec2(xPos,yPos+2), density);
                        body.setName(name);
                        if (!connectedStr.equals("-1")) {
                            String[] connectedList = connectedStr.split(",");
                            for(String id : connectedList) {
                                body.connectToDoor(doorList.get(Integer.parseInt(id)));
                            }
                        }
                    } else if(qName.equalsIgnoreCase("player")) {
                        BasicInfo bi = new BasicInfo(eventReader, false, false);
                        boolean gunDisabled = Boolean.parseBoolean(readData(eventReader));
                        player.setPosition(new Vec2(bi.xPos,bi.yPos));
                        player.setName(bi.name);
                        player.setGun(!gunDisabled);
                    } else if(qName.equalsIgnoreCase("exit")) {
                        String name = readData(eventReader);
                        float xPos = Float.parseFloat(readData(eventReader));
                        float yPos = Float.parseFloat(readData(eventReader));
                        LevelDoor exit = new LevelDoor(this);
                        exit.setName(name);
                        exit.setPosition(new Vec2(xPos,yPos));
                    }
                }
            }
            doorList.clear();
            File image = new File("maps/"+file.getName().replaceFirst("[.][^.]+$", "")+".png");
            if(image.exists() && !image.isDirectory()) {
                System.out.println();
                new ImageOverlay(this, image);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(QuantumLock.frame, "File not Found", "Warning", JOptionPane.ERROR_MESSAGE);
            QuantumLock.hideLevelEnd();
            QuantumLock.startMainMenu();
        } catch (XMLStreamException e) {
            JOptionPane.showMessageDialog(QuantumLock.frame, "Level is corrupt", "Warning", JOptionPane.ERROR_MESSAGE);
            QuantumLock.hideLevelEnd();
            QuantumLock.startMainMenu();
        }
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

    public void clearSlidingDoors() {
        slidingDoors.clear();
    }

    public void clear() {
        this.removeStepListener(stepListener);
        player.destroy();
        player = null;
        for (StaticBody body : getStaticBodies()) {
            body.destroy();
            body = null;
        }
        for (DynamicBody body : getDynamicBodies()) {
            body.destroy();
            body = null;
        }
    }
    
    public void pause() {
        stop();
        pauseTime = currentTime;
    }
    
    public void resume() {
        start();
        startTime += ((System.currentTimeMillis()-startTime)-pauseTime); //make up for paused time
    }

    @Override
    public void addStepListener(StepListener stepListener) {
        this.stepListener = stepListener;
        super.addStepListener(stepListener);
    }

    private static String readData(XMLEventReader eventReader) throws XMLStreamException {
        XMLEvent event = eventReader.nextEvent();
        while(event.getEventType() != XMLStreamConstants.CHARACTERS) {
            event = eventReader.nextEvent();
        }
        String data = event.asCharacters().getData();
        return data;
    }
}
