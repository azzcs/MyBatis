package com.azzcs.io;

import java.io.*;

/**
 * @Author: wzg
 * @Date: 2020/10/21 2:00 下午
 */
public class Resources {

    public static InputStream getResourceAsStream(String location) {
        return Resources.class.getClassLoader().getResourceAsStream(location);
    }

    public Reader getResourceAsReader(String location) {
        return null;
    }
}
