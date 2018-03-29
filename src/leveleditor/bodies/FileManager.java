package leveleditor.bodies;

import city.cs.engine.StaticBody;
import leveleditor.LevelEditor;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
                XMLwriter.writeAttribute("name",body.getName()+"pls");

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

    }
}
