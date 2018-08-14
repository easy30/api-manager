package com.cehome.apimanager.freemaker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class HtmlGenerator {     
    public String generate(String template, Map<String,Object> variables) throws IOException, TemplateException {
        BufferedWriter writer = null;   
        String htmlContent = "";
        try{
        	Configuration config = FreemarkerConfiguration.getConfiguation();
        	Template tp = config.getTemplate(template);
        	StringWriter stringWriter = new StringWriter();       
        	writer = new BufferedWriter(stringWriter);  
        	
        	tp.setEncoding("UTF-8");       
        	tp.process(variables, writer);       
        	htmlContent = stringWriter.toString();     
        	writer.flush();       
        	
        }finally{
        	if(writer!=null)
        		writer.close();     
        }
        return htmlContent;     
    }     
    
} 