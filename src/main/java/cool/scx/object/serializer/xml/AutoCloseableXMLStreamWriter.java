package cool.scx.object.serializer.xml;

import org.codehaus.stax2.XMLStreamWriter2;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

record AutoCloseableXMLStreamWriter(XMLStreamWriter2 writer) implements AutoCloseable {

    public static AutoCloseableXMLStreamWriter wrap(XMLStreamWriter writer) {
        // 这里我们保证 XMLStreamReader 一定是 XMLStreamReader2
        return new AutoCloseableXMLStreamWriter((XMLStreamWriter2) writer);
    }

    @Override
    public void close() throws XMLStreamException {
        writer.closeCompletely();
    }

}
