package dev.scx.object;

import dev.scx.node.Node;
import dev.scx.reflect.TypeInfo;

/// ObjectNodeConverter
///
/// @author scx567888
/// @version 0.0.1
public interface ObjectNodeConverter<O extends ObjectNodeConvertOptions> {

    Node objectToNode(Object value, O options) throws ObjectToNodeException;

    <T> T nodeToObject(Node node, TypeInfo type, O options) throws NodeToObjectException;

    <T> T nodeToObject(Node node, Class<T> clazz, O options) throws NodeToObjectException;

}
