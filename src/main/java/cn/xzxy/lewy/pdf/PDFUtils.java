package cn.xzxy.lewy.pdf;

import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Locale;

public class PDFUtils {
    private static final String DEFAULT_ENCODING = "utf-8";
    private static final String PDF_TYPE = "application/pdf";
    private static final boolean DEFAULT_NOCACHE = true;
    private static final String HEADER_ENCODING = "utf-8";
    private static final String HEADER_NOCACHE = "no-cache";
    private static final int TWO_MB = 2048;

    protected static Logger logger = LoggerFactory.getLogger(PDFUtils.class);

    /**
     * 生成PDF文件流
     *
     * @param ftlName 文件名称
     * @param data    数据
     * @return ByteArrayOutputStream
     * @throws Exception
     */
    public static ByteArrayOutputStream createPDF(String ftlName, Object data) throws Exception {
        // 相对路径
        File file = new File(PDFUtils.class.getResource("/").getPath());
        System.out.println(PDFUtils.class.getResource("/").getPath());
        Configuration cfg = new Configuration();
        try {
            cfg.setLocale(Locale.CHINA);
            cfg.setEncoding(Locale.CHINA, "UTF-8");
            // 设置编码
            cfg.setDefaultEncoding("UTF-8");
            // 设置模板路径
            cfg.setDirectoryForTemplateLoading(file);
            // 获取模板
            Template template = cfg.getTemplate(ftlName);
            template.setEncoding("UTF-8");
            ITextRenderer iTextRenderer = new ITextRenderer();
            // 设置字体
            ITextFontResolver fontResolver = iTextRenderer.getFontResolver();
            fontResolver.addFont(file.getPath() + "/public/font/simsun.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Writer writer = new StringWriter();
            // 数据填充模板
            template.process(data, writer);
            // 设置输出文件内容及路径
            iTextRenderer.setDocumentFromString(writer.toString());
            /* iTextRenderer.getSharedContext().setBaseURL("");//共享路径 */
            iTextRenderer.layout();
            // 生成PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            iTextRenderer.createPDF(baos);
            baos.close();
            return baos;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static void renderPdf(HttpServletResponse response, final byte[] bytes, final String filename) {
        initResponseHeader(response, PDF_TYPE);
        setFileDownloadHeader(response, filename, ".pdf");
        if (null != bytes) {
            try {
                response.getOutputStream().write(bytes);
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    /**
     * 分析并设置contentType与headers.
     */
    private static HttpServletResponse initResponseHeader(HttpServletResponse response, final String contentType,
                                                          final String... headers) {
        // 分析headers参数
        String encoding = DEFAULT_ENCODING;
        boolean noCache = DEFAULT_NOCACHE;
        for (String header : headers) {
            String headerName = StringUtils.substringBefore(header, ":");
            String headerValue = StringUtils.substringAfter(header, ":");
            if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
                encoding = headerValue;
            } else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
                noCache = Boolean.parseBoolean(headerValue);
            } else {
                throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
            }
        }
        // 设置headers参数
        String fullContentType = contentType + ";charset=" + encoding;
        response.setContentType(fullContentType);
        if (noCache) {
            // Http 1.0 header
            response.setDateHeader("Expires", 0);
            response.addHeader("Pragma", "no-cache");
            // Http 1.1 header
            response.setHeader("Cache-Control", "no-cache");
        }
        return response;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header
     *
     * @param response
     * @param fileName
     * @param fileType
     */
    public static void setFileDownloadHeader(HttpServletResponse response, String fileName, String fileType) {
        try {
            // 中文文件名支持
            String encodedFileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + fileType + "\"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void procInputStreamToResponse(InputStream is, String fileName, HttpServletResponse response)
            throws Exception {
        // 设置response参数，可以打开下载页面

        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String((fileName).getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[TWO_MB];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
    }

    /**
     * @param htmlFile        html文件存储路径
     * @param pdfFile         生成的pdf文件存储路径
     * @param chineseFontPath 中文字体存储路径
     */
    public static void html2pdf(String htmlFile, String pdfFile, String chineseFontPath) {
        // step 1
        String url;
        OutputStream os = null;
        try {
            url = new File(htmlFile).toURI().toURL().toString();
            os = new FileOutputStream(pdfFile, false);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            // 解决中文不显示问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(chineseFontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);
        } catch (MalformedURLException e) {
            logger.warn(e.toString(), e);
        } catch (FileNotFoundException e) {
            logger.warn(e.toString(), e);
        } catch (com.lowagie.text.DocumentException e) {
            logger.warn(e.toString(), e);
        } catch (IOException e) {
            logger.warn(e.toString(), e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.warn(e.toString(), e);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            // html文件路径
            String htmlFilePath = "E:\\supervision\\wdyxjk.html";
            // 中文字体存储路径
            String chineseFontPath = "E:\\supervision\\simsun.ttc";
            // pdf生成路径
            String pdfFilePath = "E:\\supervision\\wdyxjk.pdf";
            // html转pdf
            html2pdf(htmlFilePath, pdfFilePath, chineseFontPath);
            System.out.println("转换成功！");
        } catch (Exception e) {
            logger.error("html转换为pdf失败", e);
        }
    }

}
