package cool.scx.object.mapping.mapper.primitive;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.IntNode;
import cool.scx.object.node.Node;
import cool.scx.object.node.ValueNode;

/// ByteNodeMapper
///
/// @author scx567888
/// @version 0.0.1
public final class ByteNodeMapper implements NodeMapper<Byte> {

    private final boolean isPrimitive;

    public ByteNodeMapper(boolean isPrimitive) {
        this.isPrimitive = isPrimitive;
    }

    @Override
    public Node toNode(Byte value, ToNodeContext context) {
        return new IntNode(value);
    }

    @Override
    public Byte fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null, 基本类型不允许 null
        if (node.isNull()) {
            return isPrimitive ? (byte) 0 : null;
        }
        //2, 只处理值类型
        if (node instanceof ValueNode valueNode) {
            try {
                return (byte) valueNode.asInt();
            } catch (NumberFormatException e) {
                throw new NodeMappingException(e);
            }
        }
        //3, 非值类型无法转换直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
