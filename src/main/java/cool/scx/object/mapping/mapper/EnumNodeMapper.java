package cool.scx.object.mapping.mapper;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.Node;
import cool.scx.object.node.TextNode;

/// EnumNodeMapper
///
/// @author scx567888
/// @version 0.0.1
public final class EnumNodeMapper<E extends Enum<E>> implements NodeMapper<E> {

    private final Class<E> enumClass;

    public EnumNodeMapper(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public Node toNode(E value, ToNodeContext context) {
        return new TextNode(value.name());
    }

    @Override
    public E fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null 
        if (node.isNull()) {
            return null;
        }
        //2, 只处理 TextNode 类型
        if (node instanceof TextNode textNode) {
            var value = textNode.value();
            try {
                return Enum.valueOf(enumClass, value);
            } catch (IllegalArgumentException e) {
                throw new NodeMappingException(e);
            }
        }
        //3, 非 TextNode 类型无法转换直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
