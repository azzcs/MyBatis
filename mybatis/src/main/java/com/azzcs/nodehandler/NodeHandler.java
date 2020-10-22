package com.azzcs.nodehandler;

import com.azzcs.sqlnode.SqlNode;
import org.dom4j.Element;

import java.util.List;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:54 下午
 */
public interface NodeHandler {

    void handleNode(Element nodeToHandle, List<SqlNode> contents);
}
