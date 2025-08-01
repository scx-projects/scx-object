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
public final class ObjectArrayNodeMapper implements NodeMapper<Object> {

    private final ArrayTypeInfo arrayTypeInfo;
    private final NodeMapper<Object> componentNodeMapper;

    public ObjectArrayNodeMapper(ArrayTypeInfo arrayTypeInfo, NodeMapper<Object> componentNodeMapper) {
        this.arrayTypeInfo = arrayTypeInfo;
        // 这个只能用于 fromNode, 因为 toNode 有可能是 Object[]
        this.componentNodeMapper = componentNodeMapper;
    }

    @Override
    public Node toNode(Object value, ToNodeContext context) throws NodeMappingException {
        switch (value) {
            // 处理 Object[]
            case Object[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                var i = 0;
                for (var a : arr) {
                    // 我们不能确定 每个数组元素一定是 componentNodeMapper 支持的类型
                    // 所以这里 需要 递归调用 context.toNode
                    // 但是对于基本类型则不需要
                    arrayNode.add(context.toNode(a, i));
                    i = i + 1;
                }
                return arrayNode;
            }
            case byte[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                for (var a : arr) {
                    arrayNode.add(componentNodeMapper.toNode(a, context));
                }
                return arrayNode;
            }
            case short[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                for (var a : arr) {
                    arrayNode.add(componentNodeMapper.toNode(a, context));
                }
                return arrayNode;
            }
            case int[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                for (var a : arr) {
                    arrayNode.add(componentNodeMapper.toNode(a, context));
                }
                return arrayNode;
            }
            case long[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                for (var a : arr) {
                    arrayNode.add(componentNodeMapper.toNode(a, context));
                }
                return arrayNode;
            }
            case float[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                for (var a : arr) {
                    arrayNode.add(componentNodeMapper.toNode(a, context));
                }
                return arrayNode;
            }
            case double[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                for (var a : arr) {
                    arrayNode.add(componentNodeMapper.toNode(a, context));
                }
                return arrayNode;
            }
            case boolean[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                for (var a : arr) {
                    arrayNode.add(componentNodeMapper.toNode(a, context));
                }
                return arrayNode;
            }
            case char[] arr -> {
                var arrayNode = new ArrayNode(arr.length);
                for (var a : arr) {
                    arrayNode.add(componentNodeMapper.toNode(a, context));
                }
                return arrayNode;
            }
            // 不是数组类型, 这里基本上是不可达的, 如果不是外部直接调用的话
            default -> throw new NodeMappingException("Unsupported type: " + value.getClass());
        }
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
