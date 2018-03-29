package leveleditor;

import city.cs.engine.StaticBody;
import javafx.scene.shape.Circle;
import leveleditor.LevelEditor;
import leveleditor.bodies.*;
import org.jbox2d.common.Vec2;

import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.util.Iterator;

public class FileManager {
    public static void save() {
        if (LevelEditor.saveFile != null) {
            writeToFile(LevelEditor.saveFile);
        } else {
            System.out.println("THIS SHOULDN'T HAPPEN");
        }
    }
    public static void saveAs(File file) {
        writeToFile(file);
        LevelEditor.saveFile = file;
        LevelEditor.frame.setTitle("Level Editor - " + file.getName().replaceFirst("[.][^.]+$", ""));//get rid of the file extension
    }
    private static void writeToFile(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter XMLwriter = xMLOutputFactory.createXMLStreamWriter(fileWriter);

            XMLwriter.writeStartDocument();
            XMLwriter.writeStartElement("bodies");
            for (StaticBody staticBody : LevelEditor.world.getStaticBodies()) {
                FakeBody body = (FakeBody)staticBody;
                XMLwriter.writeStartElement(body.toString()); //class name

                writeData(XMLwriter, "name", body.getName()+"");
                writeData(XMLwriter, "xPosition", Float.toString(body.getPosition().x));//x position
                writeData(XMLwriter, "yPosition", Float.toString(body.getPosition().y));//y position
                writeData(XMLwriter, "rotation", Float.toString(body.getAngle()));//rotation

                if(body instanceof BoxStaticBody) {
                    writeData(XMLwriter, "xSize", Float.toString(body.getSize().x));//x size
                    writeData(XMLwriter, "ySize", Float.toString(body.getSize().y));//y size
                } else if (body instanceof CircleStaticBody) {
                    writeData(XMLwriter, "radius", Float.toString(body.getSize().x));//radius
                } else if (body instanceof SlidingDoor) {
                    SlidingDoor door = (SlidingDoor)body;
                    writeData(XMLwriter, "xSize", Float.toString(door.getSize().x));//x size
                    writeData(XMLwriter, "ySize", Float.toString(door.getSize().y));//y size

                    writeData(XMLwriter, "xOpenPosition", Float.toString(door.getOpenPos().x));//x open position
                    writeData(XMLwriter, "yOpenPosition", Float.toString(door.getOpenPos().y));//y open position
                    writeData(XMLwriter, "xClosedPosition", Float.toString(door.getClosedPos().x));//x closed position
                    writeData(XMLwriter, "yClosedPosition", Float.toString(door.getClosedPos().y));//y closed position

                    writeData(XMLwriter,"slidingSpeed", Float.toString(door.getSlidingSpeed()));//sliding speed
                    writeData(XMLwriter, "startOpen", Boolean.toString(door.isStartOpen()));//start open
                    writeData(XMLwriter, "stayOpen", Boolean.toString(door.isStayOpen()));//stay open
                } else if (body instanceof Density) {
                    writeData(XMLwriter, "density", Float.toString(((Density) body).getDensity()));//density
                } else if (body instanceof Player) {
                    writeData(XMLwriter, "gunDisabled", Boolean.toString(((Player) body).isGunDisabled()));
                }
                XMLwriter.writeEndElement();
            }
            XMLwriter.writeEndElement();
            XMLwriter.writeEndDocument();
            XMLwriter.flush();
            XMLwriter.close();

            fileWriter.close();
            System.out.println("Map Saved");
        } catch (XMLStreamException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeData(XMLStreamWriter writer, String name, String data) throws XMLStreamException {
        writer.writeStartElement(name);
        writer.writeCharacters(data);
        writer.writeEndElement();
    }

    public static void open(File file) {
        LevelEditor.clearWorld();
        LevelEditor.controlPanel.clearAll();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("boxStaticBody")) {
                        BoxStaticBody body = new BoxStaticBody(LevelEditor.world);
                        readBasic(eventReader, body);
                        float xSize = Float.parseFloat(readData(eventReader));
                        float ySize = Float.parseFloat(readData(eventReader));
                        body.setSize(new Vec2(xSize,ySize));
                    } else if(qName.equalsIgnoreCase("circleStaticBody")) {
                        CircleStaticBody body = new CircleStaticBody(LevelEditor.world);
                        readBasic(eventReader, body);
                        float radius = Float.parseFloat(eventReader.nextEvent().asCharacters().getData());
                        body.setSize(new Vec2(radius,0));
                    } else if (qName.equalsIgnoreCase("slidingDoor")) {
                        SlidingDoor body = new SlidingDoor(LevelEditor.world);
                        readBasic(eventReader, body);
                        float xSize = Float.parseFloat(readData(eventReader));
                        float ySize = Float.parseFloat(readData(eventReader));
                        body = (SlidingDoor) body.setSize(new Vec2(xSize, ySize));
                        float xOpenPos = Float.parseFloat(readData(eventReader));
                        float yOpenPos = Float.parseFloat(readData(eventReader));
                        body.setOpenPos(new Vec2(xOpenPos, yOpenPos));
                        float xClosedPos = Float.parseFloat(readData(eventReader));
                        float yClosedPos = Float.parseFloat(readData(eventReader));
                        body.setClosedPos(new Vec2(xClosedPos, yClosedPos));
                        float slidingSpeed = Float.parseFloat(readData(eventReader));
                        body.setSlidingSpeed(slidingSpeed);
                        boolean startOpen = Boolean.parseBoolean(readData(eventReader));
                        boolean stayOpen = Boolean.parseBoolean(readData(eventReader));
                        body.setStartOpen(startOpen);
                        body.setStayOpen(stayOpen);
                    } else if(qName.equalsIgnoreCase("crate")) {
                        Crate body = new Crate(LevelEditor.world);
                        readBasic(eventReader, body);
                        float density = Float.parseFloat(readData(eventReader));
                        body.setDensity(density);
                    } else if(qName.equalsIgnoreCase("ball")) {
                        Ball body = new Ball(LevelEditor.world);
                        readBasic(eventReader, body);
                        float density = Float.parseFloat(readData(eventReader));
                        body.setDensity(density);
                    } else if(qName.equalsIgnoreCase("button")) {
                        Button body = new Button(LevelEditor.world);
                        readBasic(eventReader, body);
                        float density = Float.parseFloat(readData(eventReader));
                        body.setDensity(density);
                    } else if(qName.equalsIgnoreCase("player")) {
                        Player body = new Player(LevelEditor.world);
                        readBasic(eventReader, body);
                        boolean gunDisabled = Boolean.parseBoolean(readData(eventReader));
                        body.setGunDisabled(gunDisabled);
                    } else if(qName.equalsIgnoreCase("exit")) {
                        Exit body = new Exit(LevelEditor.world);
                        readBasic(eventReader, body);
                    }
                }
            }
            LevelEditor.saveFile = file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private static void readBasic(XMLEventReader eventReader, FakeBody body) throws XMLStreamException {
        String name = readData(eventReader);
        body.setName(name);
        float xPos = Float.parseFloat(readData(eventReader));
        float yPos = Float.parseFloat(readData(eventReader));
        body.setPosition(new Vec2(xPos, yPos));
        float rotation = Float.parseFloat(readData(eventReader));
        body.setAngle(rotation);
    }

    private static String readData(XMLEventReader eventReader) throws XMLStreamException {
        eventReader.nextEvent();
        String data = eventReader.nextEvent().asCharacters().getData();
        eventReader.nextEvent();
        return data;
    }
}
