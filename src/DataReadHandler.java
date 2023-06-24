import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DataReadHandler extends DefaultHandler {

  private GetSetElement element;
  private List<GetSetElement> elements;
  private StringBuilder data;
  private int idx;

  @Override
  public void startDocument() throws SAXException {
    elements = new ArrayList<>();
    data = new StringBuilder();
    idx = 1;
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    data.setLength(0);
    if ("getSet".equalsIgnoreCase(qName)) {
      element = new GetSetElement();
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if("object".equalsIgnoreCase(qName)) {

    element.setObject(data.toString());
    }
    if("group".equalsIgnoreCase(qName)) {
      element.setGroup(data.toString());
    }
    if("selection".equalsIgnoreCase(qName)) {
      element.setSelection(data.toString());
    }
    if("getSet".equalsIgnoreCase(qName)) {
      elements.add(element);
      if(elements.size() == 500) {
        DataWriteHandler.writeData(elements, idx++);
        elements.clear();
      }
    }
  }

  @Override
  public void endDocument() throws SAXException {
    if(!elements.isEmpty()) {
      DataWriteHandler.writeData(elements, idx);
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    data.append(ch, start, length);
  }
}
