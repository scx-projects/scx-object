package cool.scx.object.mapping.mapper_factory;

import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMapperFactory;
import cool.scx.object.mapping.NodeMapperSelector;
import cool.scx.object.mapping.NodeMappingException;
import cool.scx.object.mapping.mapper.ObjectArrayNodeMapper;
import cool.scx.reflect.ArrayTypeInfo;
import cool.scx.reflect.TypeInfo;

public class ObjectArrayNodeMapperFactory implements NodeMapperFactory {

    @Override
    public NodeMapper<?> createNodeMapper(TypeInfo typeInfo, NodeMapperSelector selector) throws NodeMappingException {
        // 只处理 数组
        if (typeInfo instanceof ArrayTypeInfo arrayTypeInfo) {
            var componentType = arrayTypeInfo.componentType();
            var componentNodeMapper = selector.findNodeMapper(componentType);
            return new ObjectArrayNodeMapper(arrayTypeInfo, componentNodeMapper);
        }
        return null;
    }

}
