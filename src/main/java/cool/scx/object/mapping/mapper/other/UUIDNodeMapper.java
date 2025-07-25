package cool.scx.object.mapping.mapper.other;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.Node;
import cool.scx.object.node.TextNode;

import java.util.UUID;

public final class UUIDNodeMapper implements NodeMapper<UUID> {

    @Override
    public Node toNode(UUID value, ToNodeContext context) throws NodeMappingException {
        return new TextNode(value.toString());
    }

    @Override
    public UUID fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null 
        if (node.isNull()) {
            return null;
        }
        //2, 只处理 TextNode 类型
        if (node instanceof TextNode textNode) {
            var value = textNode.value();
            try {
                return UUID.fromString(value);
            } catch (IllegalArgumentException e) {
                throw new NodeMappingException(e);
            }
        }
        //3, 非 TextNode 类型无法转换直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
