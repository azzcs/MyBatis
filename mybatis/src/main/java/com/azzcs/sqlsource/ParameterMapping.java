package com.azzcs.sqlsource;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:14 下午
 */
public class ParameterMapping {
    private String name;
    private Class<?> type;

    public ParameterMapping(String content) {
        this.name = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
