import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class DataWriteHandler {

  public static void writeData(List<GetSetElement> elements, int idx) {

    try {
      StringWriter stringWriter = new StringWriter();

      XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
      XMLStreamWriter xMLStreamWriter =
          xMLOutputFactory.createXMLStreamWriter(stringWriter);

      writeHeader(xMLStreamWriter);

      elements.forEach(element -> {
        try {
          xMLStreamWriter.writeStartElement("GetSet");
          xMLStreamWriter.writeStartElement("Object");
          xMLStreamWriter.writeCharacters(element.getObject());
          xMLStreamWriter.writeEndElement();
          xMLStreamWriter.writeStartElement("Group");
          xMLStreamWriter.writeCharacters(element.getGroup());
          xMLStreamWriter.writeEndElement();
          xMLStreamWriter.writeStartElement("Selection");
          xMLStreamWriter.writeCharacters(element.getSelection());
          xMLStreamWriter.writeEndElement();
          xMLStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
          e.printStackTrace();
        }
      });

      writeFooter(xMLStreamWriter);

      xMLStreamWriter.flush();
      xMLStreamWriter.close();

      String xmlString = stringWriter.getBuffer().toString();

      stringWriter.close();

//      System.out.println(xmlString);

      BufferedWriter writer = new BufferedWriter(new FileWriter("temp" + idx + ".xml"));
      writer.write(xmlString);
      writer.close();

    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static void writeFooter(XMLStreamWriter xMLStreamWriter) {
    try {
      xMLStreamWriter.writeEndElement();
      xMLStreamWriter.writeEndDocument();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  private static void writeHeader(XMLStreamWriter xMLStreamWriter) {
    try {
      xMLStreamWriter.writeStartElement("eoapi:Request");
      xMLStreamWriter.writeAttribute("version", "1.0");
      xMLStreamWriter.writeAttribute("EliminateNamespace", "Yes");
      xMLStreamWriter.writeAttribute("xmlns:utility", "com.cdk.integration.framework.util.Utility");
      xMLStreamWriter.writeAttribute("xmlns:eoapi", "http://www.adp.com/eoapi");
      xMLStreamWriter.writeAttribute("xmlns:java", "java");

      xMLStreamWriter.writeStartElement("Session");

      xMLStreamWriter.writeEmptyElement("Echo");
      xMLStreamWriter.writeAttribute("ApplicationName", "SalesOverD");
      xMLStreamWriter.writeAttribute("ExtraCriteria",
          "AD_Bulk_GetVehicleGlPostingsBulk_71d67282-1b16-4488-b34a-f8f5c6ae2ed9_48739");
      xMLStreamWriter.writeAttribute("SenderNameCode", "AD");
      xMLStreamWriter.writeAttribute("CreatorNameCode", "AD");
      xMLStreamWriter.writeAttribute("DealerCountryCode", "US");
      xMLStreamWriter.writeAttribute("LanguageCode", "en-US");
      xMLStreamWriter.writeAttribute("DestinationNameCode", "AD");
      xMLStreamWriter.writeAttribute("DealerCode", "48739");
      xMLStreamWriter.writeAttribute("TransactionName", "GetVehicleGlPostingsBulk");
      xMLStreamWriter.writeAttribute("TransactionID", "71d67282-1b16-4488-b34a-f8f5c6ae2ed9");
      xMLStreamWriter.writeAttribute("Application", "SalesOverD");
      xMLStreamWriter.writeAttribute("ConversationID", "");

      xMLStreamWriter.writeStartElement("Connection");
      xMLStreamWriter.writeStartElement("Product");
      xMLStreamWriter.writeCharacters("TRKSVINT");
      xMLStreamWriter.writeEndElement();
      xMLStreamWriter.writeStartElement("Server");
      xMLStreamWriter.writeCharacters("TRKSVINT");
      xMLStreamWriter.writeEndElement();
      xMLStreamWriter.writeStartElement("Password");
      xMLStreamWriter.writeCharacters("TRKSVINT");
      xMLStreamWriter.writeEndElement();
      xMLStreamWriter.writeStartElement("Pooling");
      xMLStreamWriter.writeCharacters("Yes");
      xMLStreamWriter.writeEndElement();
      xMLStreamWriter.writeEndElement();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    int i = 1;
    while (i < 3) {
      writeData(new ArrayList<>(), i++);
    }

  }
}
