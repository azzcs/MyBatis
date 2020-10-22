package com.azzcs.config;

import com.azzcs.nodehandler.NodeHandler;
import com.azzcs.sqlnode.*;
import com.azzcs.sqlsource.DynamicSqlSource;
import com.azzcs.sqlsource.RawSqlSource;
import com.azzcs.sqlsource.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wzg
 * @Date: 2020/10/22 2:59 下午
 */
public class XMLStatementBuilder {

    private Configuration configuration;

    private boolean isDynamic = false;

    private Map<String, NodeHandler> nodeHandlerMap = new HashMap<String, NodeHandler>();

    public XMLStatementBuilder(Configuration configuration) {
        this.configuration = configuration;
        initNodeHandlerMap();
    }

    private void initNodeHandlerMap() {
        nodeHandlerMap.put("if",new IfNodeHandler());
    }


    public void parse(Element selectElement, String namespace) {
        String statementId = selectElement.attributeValue("id");
        if(statementId == null || "".equals(statementId)){
            return;
        }
        statementId = namespace+"."+statementId;

        String parameterType = selectElement.attributeValue("parameterType");
        Class parameterClass = resolveType(parameterType);

        String resultType = selectElement.attributeValue("resultType");
        Class resultClass = resolveType(resultType);

        String statementType = selectElement.attributeValue("statementType");
        statementType = statementType == null || statementType == "" ? "prepared" : statementType;

        // 解析SQL信息
        SqlSource sqlSource = createSqlSource(selectElement);

        MappedStatement mappedStatement = new MappedStatement(statementId, parameterClass, resultClass, statementType,
                sqlSource);

        configuration.addMappedStatement(statementId, mappedStatement);
    }

    private SqlSource createSqlSource(Element selectElement) {
        MixedSqlNode mixedSqlNode = parseDynamicTags(selectElement);
        SqlSource sqlSource = null;
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(mixedSqlNode);
        } else {
            sqlSource = new RawSqlSource(mixedSqlNode);
        }
        return sqlSource;
    }

    private Class resolveType(String parameterType) {
        try {
           return this.getClass().getClassLoader().loadClass(parameterType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MixedSqlNode parseDynamicTags(Element selectElement) {
        List<SqlNode> contents = new ArrayList<>();
        // 使用nodeCount会统计文本节点，而使用elements获取到的都是元素子节点
        int nodeCount = selectElement.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = selectElement.node(i);
            if (node instanceof Text) {
                String sqlText = node.getText().trim();
                if (sqlText == null || sqlText.equals("")) {
                    continue;
                }
                TextSqlNode textSqlNode = new TextSqlNode(sqlText);
                if (textSqlNode.isDynamic()) {
                    isDynamic = true;
                    contents.add(textSqlNode);
                } else {
                    contents.add(new StaticTextSqlNode(sqlText));
                }

            } else if (node instanceof Element) {
                // 此处通过NodeHandler去处理不同的标签
                Element nodeToHandle = (Element) node;
                String name = nodeToHandle.getName();
                // 怎么去查找对应的标签处理器呢，需要通过标签名称
                NodeHandler nodeHandler = nodeHandlerMap.get(name);
                nodeHandler.handleNode(nodeToHandle, contents);

                isDynamic = true;
            }
        }
        return new MixedSqlNode(contents);
    }

    private class IfNodeHandler implements NodeHandler {
        @Override
        public void handleNode(Element nodeToHandler, List<SqlNode> contents) {
            String test = nodeToHandler.attributeValue("test");

            MixedSqlNode parseDynamicTags = parseDynamicTags(nodeToHandler);

            contents.add(new IfSqlNode(test, parseDynamicTags));
        }

    }
}
