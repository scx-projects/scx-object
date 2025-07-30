package cool.scx.object.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import cool.scx.object.node.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;

import static cool.scx.object.parser.AutoCloseableXMLStreamReader.createXMLStreamReader;


/// ### 解析规则:
///
/// 1. `<a></a>` -> `""`
///     没有文本和子元素 -> TextNode("")
///
/// 2. `<a/>` -> `NULL`
///     没有文本和子元素 (自闭合标签) -> NULL
///
/// 3. `<a>123</a>` -> `"123"`
///     只有文本时 -> TextNode
///
/// 4. `<a><b>123</b></a>` -> `{"b": "123"}`
///     存在子元素 -> ObjectNode
///
/// 5. `<a name="jack"></a>` 或 `<a name="jack" />` -> `{"name": "jack"}`
///     存在属性 (无论是否自闭合) -> ObjectNode
///
/// 6. `<a name="jack"><age>18</age></a>` -> `{"name": "jack", "age": "18"}`
///     属性和子元素相同方式看待 -> ObjectNode
///
/// 7. `<a>000<b>123</b></a>` -> `{"b": "123", "": "000"}`
///     同时存在子元素和单个文本 -> 将文本视为单个 TextNode, 并以 "" 为 key 合并到 ObjectNode 中
///
/// 8. `<a>000<b>123</b>6666</a>` -> `{"b": "123", "": ["000", "6666"]}`
///     同时存在子元素和多个文本 -> 将多个文本视为 ArrayNode(TextNode[]) , 并以 "" 为 key 合并到 Object 中
///
/// 9. `<a><b>123</b><b>456</b></a>` -> `{"b": ["123", "456"]}`
///     存在多个同名子元素 -> 合并子元素为 ArrayNode
///
/// 10, `<a name="jack" name="rose"></a>` -> `{"name": ["jack", "rose"]}`
///     存在多个同名属性 -> 合并属性为 ArrayNode
///
/// 11, `<a name="">  <b> 1 2 3 </b>   </a>` -> `{"b": " 1 2 3 ", "name": "" }`
///     所有的纯空白文本节点视为不存在, 但有内容则保留原始文本
public class XmlNodeParser {

    private final XMLInputFactory xmlFactory;

    public XmlNodeParser(XMLInputFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    public Node parse(String xml) throws NodeParseException {
        try (var r = createXMLStreamReader(xmlFactory, xml)) {
            var xmlStreamReader = r.reader();
            return parseNode(xmlStreamReader);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Node parseNode(XMLStreamReader reader) throws XMLStreamException {
        // 这里因为 reader 一定是一个新创建的,
        // 也就是 reader.getEventType() == START_DOCUMENT,
        // 所以我们直接移动到下一个有效节点
        reader.next();
        return parseElement(reader);
    }

    private Node parseElement(XMLStreamReader reader) throws XMLStreamException {
        // 记录出现过的子元素和属性
        var elements = new ObjectNode();
        // 记录出现过的文本
        var texts = new ArrayNode();

        // 处理当前元素的属性
        int attributeCount = reader.getAttributeCount();
        for (int i = 0; i < attributeCount; i = i + 1) {
            var attributeName = reader.getAttributeLocalName(i); // 属性名称
            var attributeValue = reader.getAttributeValue(i);    // 属性值
            // 存储属性，使用属性名称作为 key
            elements.put(attributeName, new TextNode(attributeValue));
        }

        // 检查该元素是否是一个空元素
        boolean hasText = false;


        while (reader.next() != XMLStreamConstants.END_ELEMENT) {

            if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
                var localName = reader.getLocalName();
                var childNode = parseElement(reader);
                elements.put(localName, childNode);
            } else if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
                hasText = true;
                var text = reader.getText();
                // 移除空白字符
                if (!text.isBlank()) {
                    texts.add(new TextNode(text));
                }
            }
        }

        // 没有任何子元素
        if (elements.isEmpty()) {
            // 如果文本也是空的
            if (texts.isEmpty()) {
                if (hasText) {

                }
                return NullNode.NULL;
            }
            // 如果只有一个文本节点
            if (texts.size() == 1) {
                return texts.get(0);
            }
            // 有很多文本节点 (应该不会出现这种情况)
            return texts;
        }
        // 如果文本也是空的
        if (texts.isEmpty()) {
            return elements;
        }
        // 如果只有一个文本节点
        if (texts.size() == 1) {
            elements.put("", texts.get(0));
            return elements;
        }
        elements.put("", texts);
        return elements;
    }

    // 测试
    public static void main(String[] args) throws JsonProcessingException {
        var xml = """
                <book a="abc">123<b> 567</b><c> </c> </book>
                """;

        var s = new XmlNodeParser(XMLInputFactory.newFactory());
        Node parse = s.parse(xml);

        var x = new XmlMapper();

        var e = x.readTree(xml);

        String s1 = x.writeValueAsString(e);

        System.out.println(e.toString());

    }


}
