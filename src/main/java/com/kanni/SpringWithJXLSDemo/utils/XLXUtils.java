package com.kanni.SpringWithJXLSDemo.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class XLXUtils {

    public static void exportExcel(InputStream is, OutputStream os, Map<String, Object> model) throws IOException {
        Context context = new Context();
        if (model != null) {
            for (String key : model.keySet()) {
                context.putVar(key, model.get(key));
            }
        }
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        jxlsHelper.setHideTemplateSheet(true);
        jxlsHelper.setDeleteTemplateSheet(true);
        Transformer transformer  = jxlsHelper.createTransformer(is, os);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> functionMap = new HashMap<>();
        functionMap.put("fun", new ExcelUtils());
        JexlEngine customJexlEngine = new JexlBuilder().namespaces(functionMap).create();
        evaluator.setJexlEngine(customJexlEngine);
        jxlsHelper.processTemplate(context, transformer);
    }

}
