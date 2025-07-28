package cool.scx.object.mapping;

public interface ToNodeOptions {

    /// 是否忽略 null 值, 多用于 Map 和 Object
    void setMapperOptions(NodeMapperOptions options);

    <T extends NodeMapperOptions> T getMapperOptions(Class<T> mapNodeMapperOptionsClass);

    <T extends NodeMapperOptions> T getMapperOptions(Class<T> mapNodeMapperOptionsClass, T defaultValue);

}
