package com.cehome.apimanager.pdf;

import com.cehome.apimanager.freemaker.HtmlGenerator;
import com.cehome.apimanager.pdf.exception.DocumentGeneratingException;
import com.cehome.apimanager.pdf.factory.ITextRendererObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class PdfDocumentGenerator {
	private final static Logger logger = LoggerFactory.getLogger(PdfDocumentGenerator.class);

	private final static HtmlGenerator htmlGenerator;
	static {
		htmlGenerator = new HtmlGenerator();
	}

	public boolean generate(String template, DocumentVo documentVo,
							String outputFile) throws DocumentGeneratingException {
		Map<String, Object> variables = new HashMap<String, Object>();

		try {
			variables = documentVo.fillDataMap();
			String htmlContent = this.htmlGenerator.generate(template,
					variables);
			this.generate(htmlContent, outputFile);

			logger.info("The document [primarykey="
					+ documentVo.findPrimaryKey()
					+ "] is generated successfully,and stored in ["
					+ outputFile + "]");
		} catch (Exception e) {
			String error = "The document [primarykey="
					+ documentVo.findPrimaryKey() + "] is failed to generate";
			logger.error(error);
			throw new DocumentGeneratingException(error, e);
		}

		return true;
	}
	public void generate(String htmlContent, String outputFile)
			throws Exception {
		OutputStream out = null;
		ITextRenderer iTextRenderer = null;

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(new ByteArrayInputStream(htmlContent.getBytes("UTF-8")));
			File f = new File(outputFile);
			if (f != null && !f.getParentFile().exists()) {
				f.getParentFile().mkdir();
			}
			out = new FileOutputStream(outputFile);

			iTextRenderer = (ITextRenderer) ITextRendererObjectFactory
					.getObjectPool().borrowObject();//获取对象池中对象

			try {
				iTextRenderer.setDocument(doc, null);
				iTextRenderer.layout();
				iTextRenderer.createPDF(out);
			} catch (Exception e) {
				ITextRendererObjectFactory.getObjectPool().invalidateObject(
						iTextRenderer);
				iTextRenderer = null;
				throw e;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null)
				out.close();

			if (iTextRenderer != null) {
				try {
					ITextRendererObjectFactory.getObjectPool().returnObject(
							iTextRenderer);
				} catch (Exception ex) {
					logger.error("Cannot return object from pool.", ex);
				}
			}
		}
	}

}