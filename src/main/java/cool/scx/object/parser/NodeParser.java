package cool.scx.object.parser;

import cool.scx.object.node.Node;

import java.io.File;

public interface NodeParser {

    Node parse(String text) throws NodeParseException;

    Node parse(File file) throws NodeParseException;

}
