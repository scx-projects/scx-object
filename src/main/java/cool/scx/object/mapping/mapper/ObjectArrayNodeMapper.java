package cool.scx.object.mapping.mapper;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.ArrayNode;
import cool.scx.object.node.Node;
import cool.scx.reflect.ArrayTypeInfo;

/// ObjectArrayNodeMapper
///
/// @author scx567888
/// @version 0.0.1
public final class ObjectArrayNodeMapper implements NodeMapper<Object[]> {

    private final ArrayTypeInfo arrayTypeInfo;
    private final NodeMapper<Object> componentNodeMapper;

    public ObjectArrayNodeMapper(ArrayTypeInfo arrayTypeInfo, NodeMapper<Object> componentNodeMapper) {
        this.arrayTypeInfo = arrayTypeInfo;
        // 这个只能用于 fromNode, 因为 toNode 有可能是 Object[]
        this.componentNodeMapper = componentNodeMapper;
    }

    @Override
    public Node toNode(Object[] value, ToNodeContext context) throws NodeMappingException {
        var arrayNode = new ArrayNode(value.length);
        var i = 0;
        for (var a : value) {
            arrayNode.add(context.toNode(a, i));
            i = i + 1;
        }
        return arrayNode;
    }

    @Override
    public Object[] fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null
        if (node.isNull()) {
            return null;
        }
        //2, 先处理 ArrayNode 类型
        if (node instanceof ArrayNode arrayNode) {
            var result = (Object[]) arrayTypeInfo.newArray(arrayNode.size());
            var i = 0;
            for (var e : arrayNode) {
                result[i] = componentNodeMapper.fromNode(e, context);
                i = i + 1;
            }
            return result;
        }
        //3, 非数组我们尝试宽容处理
        var result = (Object[]) arrayTypeInfo.newArray(1);
        result[0] = componentNodeMapper.fromNode(node, context);
        return result;
    }

}
