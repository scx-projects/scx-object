package cool.scx.object.mapping.mapper_factory;

import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMapperFactory;
import cool.scx.object.mapping.NodeMapperSelector;
import cool.scx.object.mapping.mapper.MapNodeMapper;
import cool.scx.reflect.ClassInfo;
import cool.scx.reflect.TypeInfo;

import java.util.Map;

public class MapNodeMapperFactory implements NodeMapperFactory {

    @Override
    public NodeMapper<?> createNodeMapper(TypeInfo typeInfo, NodeMapperSelector selector) {
        if (typeInfo instanceof ClassInfo classInfo) {
            if (Map.class.isAssignableFrom(typeInfo.rawClass())) {
                return new MapNodeMapper(classInfo, selector);
            }
        }
        return null;
    }

}
