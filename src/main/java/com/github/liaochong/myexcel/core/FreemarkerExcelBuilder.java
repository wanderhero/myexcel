/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liaochong.myexcel.core;

import com.github.liaochong.myexcel.exception.ExcelBuildException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.codec.CharEncoding;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * freemarker的excel创建者
 *
 * @author liaochong
 * @version 1.0
 */
public class FreemarkerExcelBuilder extends AbstractExcelBuilder {

    private static final Configuration CFG;

    static {
        CFG = new Configuration(Configuration.VERSION_2_3_23);
        CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CFG.setDefaultEncoding(CharEncoding.UTF_8);
        CFG.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), "/");
    }

    private Template template;

    /**
     * 设置模板信息
     *
     * @param path 模板路径，相对路径
     */
    @Override
    public ExcelBuilder template(String path) {
        try {
            template = CFG.getTemplate(path);
            return this;
        } catch (IOException e) {
            throw ExcelBuildException.of("Failed to get freemarker template", e);
        }
    }

    @Override
    protected <T> void render(Map<String, T> data, Writer out) throws Exception {
        checkTemplate(template);
        template.process(data, out);
    }

}
