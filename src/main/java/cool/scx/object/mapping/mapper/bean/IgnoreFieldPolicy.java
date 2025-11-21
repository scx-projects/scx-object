package cool.scx.object.mapping.mapper.bean;

import dev.scx.reflect.ClassInfo;
import dev.scx.reflect.FieldInfo;

public interface IgnoreFieldPolicy {

    boolean needIgnore(ClassInfo classInfo, FieldInfo fieldInfo);

}
