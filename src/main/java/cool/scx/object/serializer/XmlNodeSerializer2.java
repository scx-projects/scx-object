package cool.scx.object.serializer;

import com.ctc.wstx.stax.WstxOutputFactory;
import cool.scx.object.ScxObject;
import cool.scx.object.node.*;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringWriter;

import static cool.scx.object.serializer.AutoCloseableXMLStreamWriter.wrap;

/// ### 序列化规则
/// 
/// 0. 跟标签默认 -> root, 没有上下文 key 可用的数组 默认 -> item.
///    支持外部配置, 防止某些情况下的冲突  
/// 
/// 1. "123" -> <root>123</root>
///    值类型 -> 标准标签
/// 
/// 2. NULL -> <root/>
///    NULL -> 闭合标签
/// 
/// 3. {"a": 123} -> <root><a>123</a></root>
///    对象类型 -> 嵌套标签
/// 
/// 4. {"a": [1, 2]} -> <root><a>1</a><a>2</a></root>
///    数组 -> 尝试重复
/// 
/// 5. [1, 2] -> <root><item>1</item><item>2</item></root>
///    数组没有可用的上文 key -> 使用 item
/// 
/// 6. [1, [2]] -> <root><item>1</item><item><item>2</item></item></root>
///    嵌套数组不进行任何扁平化
/// 
/// 7, {"": 123} -> <root>123</root>
///    key 为 "", 直接解包
/// 
public final class XmlNodeSerializer2 {

    private final XMLOutputFactory2 xmlFactory;
    private final XmlNodeSerializerOptions options;

    public XmlNodeSerializer2(XMLOutputFactory2 xmlFactory, XmlNodeSerializerOptions options) {
        this.xmlFactory = xmlFactory;
        this.options = options;
        //有很多的 安全限制 jackson 已经覆盖了 我们直接使用
//        this.xmlFactory.setStreamWriteConstraints(StreamWriteConstraints.builder()
//                .maxNestingDepth(options.maxNestingDepth())
//                .build());
    }

    public String serializeAsString(Node node) throws NodeSerializeException {
        var writer = new StringWriter();
        try (var xmlStreamWriter = wrap(xmlFactory.createXMLStreamWriter(writer))) {
            serializeAndClose(xmlStreamWriter.writer(), node);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    private void serializeAndClose(XMLStreamWriter2 writer2, Node node) throws XMLStreamException, IOException {   // 顶级数组需要特殊处理
        var isRootArray = node instanceof ArrayNode;
        if (isRootArray) {
            writer2.writeStartElement("root");
        }
        writeNode(writer2, node, "root");
        // 顶级数组需要特殊处理
        if (isRootArray) {
            writer2.writeEndElement();
        }
    }

    private void writeNode(XMLStreamWriter2 writer2, Node node, String name) throws IOException, XMLStreamException {
        // 如果根节点本身就是 null, 直接返回自闭合标签
        switch (node) {
            case NullNode _ -> {
                writer2.writeStartElement(name);
                writer2.writeEndElement();
            }
            case ValueNode valueNode -> {
                writer2.writeStartElement(name);
                writer2.writeCharacters(valueNode.toString());
                writer2.writeEndElement();
            }
            case ObjectNode objectNode -> {
                writer2.writeStartElement(name);
                for (var e : objectNode) {
                    writeNode(writer2, e.getValue(), e.getKey());
                }
                writer2.writeEndElement();
            }
            case ArrayNode arrayNode -> {
                for (var e : arrayNode) {
                    writeNode(writer2, e, name);
                }
            }
        }
    }


    public static void main(String[] args) {
        var s = ScxObject.fromJson("[1,[1,2,3]]");
//        ff.put("123", new TextNode("hhh"));
//        ff.put("456", new TextNode("hhh"));
//        ff.put("789", new TextNode("hhh"));
//        s.add(ff);
//        var s=new TextNode("123");
//        var s = new ObjectNode();
//        s.put("name", new TextNode("Text"));
//        s.put("name1", new TextNode("Text1"));
        var f = new XmlNodeSerializer2(new WstxOutputFactory(), new XmlNodeSerializerOptions());
        String s1 = f.serializeAsString(s);
        System.out.println(s1);
    }

}
