package cool.scx.object.mapping.mapper.time;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.IntNode;
import cool.scx.object.node.Node;
import cool.scx.object.node.NumberNode;
import cool.scx.object.node.TextNode;

import java.time.DateTimeException;
import java.time.Month;

/// MonthNodeMapper
///
/// @author scx567888
/// @version 0.0.1
public class MonthNodeMapper implements NodeMapper<Month> {

    @Override
    public Node toNode(Month value, ToNodeContext context) throws NodeMappingException {
        // 这里我们不以普通的枚举转换成字符串来看待, 而是转换成数字,
        // 因为 枚举值 字符串 JANUARY, FEBRUARY 等不便于统一, 同时也有大小写的问题
        return new IntNode(value.getValue());
    }

    @Override
    public Month fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null
        if (node.isNull()) {
            return null;
        }
        //2, 这里我们实际上可以宽松一点, 同时支持数组和字符串
        // 先使用数字解析
        if (node instanceof NumberNode numberNode) {
            try {
                return Month.of(numberNode.asInt());
            } catch (DateTimeException e) {
                throw new NodeMappingException(e);
            }
        }
        // 再尝试使用字符串解析
        if (node instanceof TextNode textNode) {
            try {
                return Month.valueOf(textNode.asText().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new NodeMappingException(e);
            }
        }
        // 无法转换 直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
