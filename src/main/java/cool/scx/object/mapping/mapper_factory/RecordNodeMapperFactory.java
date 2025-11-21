package cool.scx.object.mapping.mapper_factory;

import cool.scx.object.mapping.NodeMapper;
import cool.scx.object.mapping.NodeMapperFactory;
import cool.scx.object.mapping.NodeMapperSelector;
import cool.scx.object.mapping.mapper.record.RecordNodeMapper;
import dev.scx.reflect.ClassInfo;
import dev.scx.reflect.ClassKind;
import dev.scx.reflect.TypeInfo;

public class RecordNodeMapperFactory implements NodeMapperFactory {

    @Override
    public NodeMapper<?> createNodeMapper(TypeInfo typeInfo, NodeMapperSelector selector) {
        if (typeInfo instanceof ClassInfo classInfo) {
            if (classInfo.classKind() == ClassKind.RECORD) {
                return new RecordNodeMapper(classInfo);
            }
        }
        return null;
    }

}
