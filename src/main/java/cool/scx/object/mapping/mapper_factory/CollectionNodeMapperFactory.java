package cool.scx.object.mapping.mapper_factory;

import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMapperFactory;
import cool.scx.object.mapping.NodeMapperSelector;
import cool.scx.object.mapping.mapper.CollectionNodeMapper;
import cool.scx.reflect.ClassInfo;
import cool.scx.reflect.TypeInfo;

import java.util.Collection;

public class CollectionNodeMapperFactory implements NodeMapperFactory {

    @Override
    public NodeMapper<?> createNodeMapper(TypeInfo typeInfo, NodeMapperSelector selector) {
        if (typeInfo instanceof ClassInfo classInfo) {
            if (Collection.class.isAssignableFrom(typeInfo.rawClass())) {
                return new CollectionNodeMapper(classInfo, selector);
            }
        }
        return null;
    }

}
