package com.azzcs.sqlnode;

import com.azzcs.utils.GenericTokenParser;
import com.azzcs.utils.OgnlUtils;
import com.azzcs.utils.SimpleTypeRegistry;
import com.azzcs.utils.TokenHandler;

/**
 * @Author: wzg
 * @Date: 2020/10/22 3:31 下午
 */
public class TextSqlNode implements SqlNode{

    private String sqlText;

    public TextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }


    @Override
    public void apply(DynamicContext context) {
        //处理${}
        GenericTokenParser tokenParser = new GenericTokenParser("${", "}", new BindingTokenParser(context));
        String sql = tokenParser.parse(sqlText);
        context.appendSql(sql);
    }

    public boolean isDynamic() {
        if (sqlText.indexOf("${") > -1) {
            return true;
        }
        return false;
    }

    private class BindingTokenParser implements TokenHandler {

        private DynamicContext context;

        public BindingTokenParser(DynamicContext context) {
            this.context = context;
        }

        @Override
        public String handleToken(String expression) {
            Object paramObject = context.getBindings().get("_parameter");
            if (paramObject == null) {
                // context.getBindings().put("value", null);
                return "";
            } else if (SimpleTypeRegistry.isSimpleType(paramObject.getClass())) {
                // context.getBindings().put("value", paramObject);
                return String.valueOf(paramObject);
            }

            // 使用Ognl api去获取相应的值
            Object value = OgnlUtils.getValue(expression, context.getBindings());
            String srtValue = value == null ? "" : String.valueOf(value);
            return srtValue;
        }
    }
}
