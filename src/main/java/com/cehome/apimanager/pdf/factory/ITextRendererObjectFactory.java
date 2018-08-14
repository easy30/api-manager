package com.cehome.apimanager.pdf.factory;

import com.cehome.apimanager.utils.ResourceLoader;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;

public class ITextRendererObjectFactory extends
		BasePoolableObjectFactory {
	private static GenericObjectPool itextRendererObjectPool = null;

	@Override
	public Object makeObject() throws Exception {
		ITextRenderer renderer = createTextRenderer();
		return renderer;
	}
	public static GenericObjectPool getObjectPool(){
		synchronized (ITextRendererObjectFactory.class) {
			if(itextRendererObjectPool==null){
				itextRendererObjectPool = new GenericObjectPool(
						new ITextRendererObjectFactory());
				GenericObjectPool.Config config = new GenericObjectPool.Config();
				config.lifo = false;
				config.maxActive = 15;
				config.maxIdle = 5;
				config.minIdle = 1;
				config.maxWait = 5 * 1000;
				itextRendererObjectPool.setConfig(config);
			}
		}

		return itextRendererObjectPool;
	}

	public static synchronized ITextRenderer createTextRenderer()
			throws DocumentException, IOException {
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver();
		addFonts(fontResolver);
		return renderer;
	}

	public static ITextFontResolver addFonts(ITextFontResolver fontResolver)
			throws DocumentException, IOException {
		// Font fontChinese = null;
		// BaseFont bfChinese = BaseFont.createFont("STSong-Light",
		// "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		// fontChinese = new Font(bfChinese, 12, Font.NORMAL);

		File fontsDir = new File(ResourceLoader.getPath("config/fonts"));
		if (fontsDir != null && fontsDir.isDirectory()) {
			File[] files = fontsDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f == null || f.isDirectory()) {
					break;
				}
				fontResolver.addFont(f.getAbsolutePath(), BaseFont.IDENTITY_H,
						BaseFont.NOT_EMBEDDED);
			}
		}
		return fontResolver;
	}
}
