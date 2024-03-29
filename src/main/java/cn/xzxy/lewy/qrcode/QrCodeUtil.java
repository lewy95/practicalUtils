package cn.xzxy.lewy.qrcode;

import com.alibaba.fastjson2.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

public class QrCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "PNG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;

    // *************************************************************************************
    // *************************************************************************************
    // *************************************************************************************

    /**
     * 生成二维码(内嵌LOGO)
     * 二维码文件名随机，文件名可能会有重复
     *
     * @param content      二维码包含的内容
     * @param logoPath     LOGO地址
     * @param destPath     存放目录
     * @param needCompress 是否压缩LOGO
     */
    public static String encode(String content, String logoPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        String fileName = new Random().nextInt(99999999) + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 生成二维码(内嵌LOGO)
     * 调用者指定二维码文件名
     *
     * @param content      二维码包含的内容
     * @param logoPath     LOGO地址
     * @param destPath     存放目录
     * @param fileName     二维码文件名
     * @param needCompress 是否压缩LOGO
     */
    public static String encode(String content, String logoPath, String destPath, String fileName, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        fileName = fileName.substring(0, fileName.indexOf(".") > 0 ? fileName.indexOf(".") : fileName.length())
            + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content  二维码包含的内容
     * @param logoPath LOGO地址
     * @param destPath 存储地址
     */
    public static String encode(String content, String logoPath, String destPath) throws Exception {
        return encode(content, logoPath, destPath, false);
    }

    /**
     * 生成二维码
     *
     * @param content      二维码包含的内容
     * @param destPath     存储地址
     * @param needCompress 是否压缩LOGO
     */
    public static String encode(String content, String destPath, boolean needCompress) throws Exception {
        return encode(content, null, destPath, needCompress);
    }

    /**
     * 生成二维码
     *
     * @param content  二维码包含的内容
     * @param destPath 存储地址
     */
    public static String encode(String content, String destPath) throws Exception {
        return encode(content, null, destPath, false);
    }

    /**
     * 生成二维码(内嵌LOGO)
     * @param content      二维码包含的内容
     * @param logoPath     LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     */
    public static void encode(String content, String logoPath, OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, logoPath, needCompress);
        ImageIO.write(image, FORMAT, output);
    }

    /**
     * 生成二维码
     * @param content 二维码包含的内容
     * @param output  输出流
     */
    public static void encode(String content, OutputStream output) throws Exception {
        encode(content, null, output, false);
    }

    // *************************************************************************************
    // *************************************************************************************
    // *************************************************************************************

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．
     * (mkdir如果父目录不存在则会抛出异常)
     * @param destPath 存放目录
     */
    private static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 创建二维码图片
     * @param content      二维码包含的内容
     * @param logoPath     插入的logo
     * @param needCompress 是否压缩
     */
    private static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
            hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     * @param source       二维码图片文件流
     * @param logoPath     LOGO图片地址
     * @param needCompress 是否压缩
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws Exception {
        File file = new File(logoPath);
        if (!file.exists()) {
            throw new Exception("logo file not found.");
        }
        Image src = ImageIO.read(new File(logoPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    // *************************************************************************************
    // *************************************************************************************
    // *************************************************************************************

    /**
     * 生成二维码(内嵌LOGO)字节流【不生成实际图片文件】
     */
    public static <T> byte[] encode(T obj, String logoPath, boolean needCompress) throws Exception {
        String content = JSONObject.toJSONString(obj);

        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = createImage(content, logoPath, needCompress);
            ImageIO.write(image, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 生成二维码(内嵌LOGO)字节流【不生成实际图片文件】
     */
    public static <T> byte[] encode(T obj, boolean needCompress) throws Exception {
        String content = JSONObject.toJSONString(obj);

        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = createImage(content, null, needCompress);
            ImageIO.write(image, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 生成二维码(内嵌LOGO)字节流【不生成实际图片文件】
     */
    public static <T> byte[] encode(T obj) throws Exception {
        String content = JSONObject.toJSONString(obj);

        byte[] bytes = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = createImage(content, null, false);
            ImageIO.write(image, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // *************************************************************************************
    // *************************************************************************************
    // *************************************************************************************

    /**
     * 解析二维码
     * @param file 二维码图片文件对象
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }

    /**
     * 解析二维码
     *
     * @param path 二维码图片文件地址
     * @return result
     * @throws Exception Exception
     */
    public static String decode(String path) throws Exception {
        return decode(new File(path));
    }

    public static void main(String[] args) throws Exception {
        String text = "https://www.baidu.com";
        //不含Logo
        QrCodeUtil.encode(text, null, "e:\\", true);
        //含Logo，不指定二维码图片名
        //QRCodeUtil.encode(text, "e:\\csdn.jpg", "e:\\", true);
        //含Logo，指定二维码图片名
        //encode(text, "D:\\font.jpg", "D:\\IMG", "baidu.logo", true);
    }
}
