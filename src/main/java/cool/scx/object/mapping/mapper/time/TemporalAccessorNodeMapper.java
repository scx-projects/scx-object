package cool.scx.object.mapping.mapper.time;

import cool.scx.object.mapping.FromNodeContext;
import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.ToNodeContext;
import cool.scx.object.node.Node;
import cool.scx.object.node.TextNode;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

/// LocalDateTimeNodeMapper
///
/// @author scx567888
/// @version 0.0.1
public final class TemporalAccessorNodeMapper<T extends TemporalAccessor> implements NodeMapper<T> {

    private final DateTimeFormatter formatter;
    private final boolean useTimestamp;
    private final TemporalQuery<T> temporalQuery;

    public TemporalAccessorNodeMapper(DateTimeFormatter formatter, boolean useTimestamp, TemporalQuery<T> temporalQuery) {
        this.formatter = formatter;
        this.useTimestamp = useTimestamp;
        this.temporalQuery = temporalQuery;
    }

    @Override
    public Node toNode(T value, ToNodeContext context) {
        return new TextNode(formatter.format(value));
    }

    @Override
    public T fromNode(Node node, FromNodeContext context) throws NodeMappingException {
        //1, 处理 null
        if (node.isNull()) {
            return null;
        }
        //2, 只处理 TextNode 类型
        if (node instanceof TextNode textNode) {
            try {
                return formatter.parse(textNode.asText(), temporalQuery);
            } catch (DateTimeParseException e) {
                throw new NodeMappingException(e);
            }
        }
        //3, 非 TextNode 类型无法转换直接报错
        throw new NodeMappingException("Unsupported node type: " + node.getClass());
    }

}
