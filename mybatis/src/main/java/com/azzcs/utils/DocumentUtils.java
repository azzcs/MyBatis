package com.azzcs.utils;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @Author: wzg
 * @Date: 2020/10/21 2:11 下午
 */
public class DocumentUtils {

    public static Document readDocument(InputStream inputStream) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }
}
