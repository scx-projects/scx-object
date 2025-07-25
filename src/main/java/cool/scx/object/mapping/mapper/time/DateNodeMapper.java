package cool.scx.object.mapping.mapper.time;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.Node;
import cool.scx.object.node.TextNode;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;

/// DateNodeMapper
///
/// @author scx567888
/// @version 0.0.1
public final class DateNodeMapper implements NodeMapper<Date> {

    @Override
    public Node toNode(Date value, ToNodeContext context) {
        return new TextNode(Instant.ofEpochMilli(value.getTime()).toString());
    }

    @Override
    public Date fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null
        if (node.isNull()) {
            return null;
        }
        //2, 只处理 TextNode 类型
        if (node instanceof TextNode textNode) {
            try {
                return Date.from(Instant.parse(textNode.asText()));
            } catch (DateTimeParseException e) {
                throw new NodeMappingException(e);
            }
        }
        //3, 非 TextNode 类型无法转换直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
