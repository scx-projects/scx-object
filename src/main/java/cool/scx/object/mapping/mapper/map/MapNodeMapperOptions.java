package cool.scx.object.mapping.mapper.map;

import cool.scx.object.mapping.NodeMapperOptions;

public class MapNodeMapperOptions implements NodeMapperOptions {

    /// null 时 对应的 key
    public String nullKey(){
        return null;
    }

    /// 是否忽略 null 值, 多用于 Map 和 Object
    public boolean ignoreNullValue() {
        return false;
    }
    
}
