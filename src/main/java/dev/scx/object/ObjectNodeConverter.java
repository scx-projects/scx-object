package dev.scx.object;

import dev.scx.node.Node;
import dev.scx.reflect.TypeInfo;
import dev.scx.reflect.TypeReference;

import static dev.scx.reflect.ScxReflect.typeOf;

/// ObjectNodeConverter
///
/// @author scx567888
public interface ObjectNodeConverter<O extends ObjectNodeConvertOptions> {

    Node objectToNode(Object value, O options) throws ObjectToNodeException;

    <T> T nodeToObject(Node node, TypeInfo type, O options) throws NodeToObjectException;

    <T> T nodeToObject(Node node, Class<T> type, O options) throws NodeToObjectException;

    default <T> T nodeToObject(Node node, TypeReference<T> type, O options) throws NodeToObjectException {
        return nodeToObject(node, typeOf(type), options);
    }

}
