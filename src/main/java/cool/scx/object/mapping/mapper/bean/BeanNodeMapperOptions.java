package cool.scx.object.mapping.mapper.bean;

import cool.scx.object.mapping.NodeMapperOptions;

public final class BeanNodeMapperOptions implements NodeMapperOptions {

    private boolean ignoreNullValue;

    public BeanNodeMapperOptions() {
        this.ignoreNullValue = false;
    }

    /// 是否忽略 null 值, 多用于 Map 和 Object
    public boolean ignoreNullValue() {
        return ignoreNullValue;
    }

    public BeanNodeMapperOptions ignoreNullValue(boolean ignoreNullValue) {
        this.ignoreNullValue = ignoreNullValue;
        return this;
    }

}
