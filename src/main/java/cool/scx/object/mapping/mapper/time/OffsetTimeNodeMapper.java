package cool.scx.object.mapping.mapper.time;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.Node;
import cool.scx.object.node.TextNode;

import java.time.OffsetTime;
import java.time.format.DateTimeParseException;

/// OffsetTimeNodeMapper
///
/// @author scx567888
/// @version 0.0.1
public final class OffsetTimeNodeMapper implements NodeMapper<OffsetTime> {

    @Override
    public Node toNode(OffsetTime value, ToNodeContext context) {
        return new TextNode(context.options().offsetTimeFormatter().format(value));
    }

    @Override
    public OffsetTime fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null
        if (node.isNull()) {
            return null;
        }
        //2, 只处理 TextNode 类型
        if (node instanceof TextNode textNode) {
            try {
                return OffsetTime.parse(textNode.asText(), context.options().offsetTimeFormatter());
            } catch (DateTimeParseException e) {
                throw new NodeMappingException(e);
            }
        }
        //3, 非 TextNode 类型无法转换直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
