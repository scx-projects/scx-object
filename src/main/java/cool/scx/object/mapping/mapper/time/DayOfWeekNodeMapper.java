package cool.scx.object.mapping.mapper.time;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.IntNode;
import cool.scx.object.node.Node;
import cool.scx.object.node.ValueNode;

import java.time.DateTimeException;
import java.time.DayOfWeek;

public final class DayOfWeekNodeMapper implements NodeMapper<DayOfWeek> {

    @Override
    public Node toNode(DayOfWeek value, ToNodeContext context) throws NodeMappingException {
        return new IntNode(value.getValue());
    }

    @Override
    public DayOfWeek fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null
        if (node.isNull()) {
            return null;
        }
        //2, 这里我们实际上可以宽松一点, 同时支持数字和字符串
        // 先使用数字解析
        if (node instanceof ValueNode valueNode) {
            try {
                return DayOfWeek.of(valueNode.asInt());
            } catch (DateTimeException e) {
                throw new NodeMappingException(e);
            } catch (NumberFormatException e) {
                // 无法转换为数字 这里在尝试使用 字符串解析
                try {
                    return DayOfWeek.valueOf(valueNode.asText().toUpperCase());
                } catch (IllegalArgumentException ex) {
                    throw new NodeMappingException(ex);
                }
            }
        }
        // 无法转换 直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
