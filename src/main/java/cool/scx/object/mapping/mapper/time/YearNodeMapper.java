package cool.scx.object.mapping.mapper.time;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.IntNode;
import cool.scx.object.node.Node;
import cool.scx.object.node.ValueNode;

import java.time.DateTimeException;
import java.time.Year;

/// YearNodeMapper
///
/// @author scx567888
/// @version 0.0.1
public final class YearNodeMapper implements NodeMapper<Year> {

    @Override
    public Node toNode(Year value, ToNodeContext context) throws NodeMappingException {
        // 当作纯数字看待
        return new IntNode(value.getValue());
    }

    @Override
    public Year fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null
        if (node.isNull()) {
            return null;
        }
        //2, 只处理 ValueNode 类型
        if (node instanceof ValueNode valueNode) {
            try {
                return Year.of(valueNode.asInt());
            } catch (DateTimeException | NumberFormatException e) {
                throw new NodeMappingException(e);
            }
        }
        //3, 非 ValueNode 类型无法转换直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
