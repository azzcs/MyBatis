package com.azzcs.sqlnode;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:25 下午
 */
public class DynamicContext {
    private StringBuffer sb = new StringBuffer();

    private Map<Object,Object> bindings = new HashMap<>();

    public DynamicContext(Object parameter) {
        bindings.put("_parameter",parameter);
    }

    public String getSql(){
        return sb.toString();
    }

    public void appendSql(String sql){
        sb.append(sql);
        sb.append(" ");
    }

    public Map<Object, Object> getBindings() {
        return bindings;
    }
}
