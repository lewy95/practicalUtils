package cn.xzxy.lewy.mail;

import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

/**
 * java发送邮箱工具类
 */
public class SendMailUtil {

    private static final String SUBJECT = "数据质控告警邮件"; // 邮件标题

    private static String HOST = ""; // smtp服务器
    private static String FROM = ""; // 发件人地址
    //private static String TO = ""; // 收件人地址
    //private static String USER = "robertlewy9@163.com"; // 用户名
    private static String AUTH = ""; // 163的授权码
    //private static String[] TOS = null; // 收件人地址
    private static String APPENDIX[] = new String[]{"data/xml/cyxj.xml", "data/xml/jcbg.xml"}; // 附件地址
    private static String APPENDIX_STR = ""; // 附件地址串（多个）
    private static String APPENDIX_NAME = ""; // 附件名称

    static {
        try {
            Properties props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties"));//从自定义配置文件获取相关参数
            HOST = props.getProperty("mail.host");
            FROM = props.getProperty("mail.from");
            //TO = props.getProperty("mail.to");
            //TOS = TO.split(",");
            if (StringUtils.isNotBlank(props.getProperty("mail.appendix"))) {
                APPENDIX_STR = props.getProperty("mail.appendix");
                APPENDIX = APPENDIX_STR.split(",");
            }
            APPENDIX_NAME = props.getProperty("mail.appendixName");
            //USER = props.getProperty("mail.user");
            AUTH = props.getProperty("mail.auth");
            //SUBJECT = props.getProperty("mail.subject");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件（支持发送附件）
     */
    public static void send(String context, String[] TOS) {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.auth", "true");  //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        Session session = Session.getDefaultInstance(props);//用props对象构建一个session
        session.setDebug(true);
        MimeMessage message = new MimeMessage(session);//用session为参数定义消息对象
        try {
            message.setFrom(new InternetAddress(FROM));// 加载发件人地址
            InternetAddress[] sendTo = new InternetAddress[TOS.length]; // 加载收件人地址
            for (int i = 0; i < TOS.length; i++) {
                sendTo[i] = new InternetAddress(TOS[i]);
            }
            message.addRecipients(Message.RecipientType.TO, sendTo);
            //message.addRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(FROM));//设置在发送给收信人之前给自己（发送方）抄送一份，不然会被当成垃圾邮件，报554错
            message.setSubject(SUBJECT);//加载标题
            Multipart multipart = new MimeMultipart();//向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            BodyPart contentPart = new MimeBodyPart();//设置邮件的文本内容
            contentPart.setText(context);
            multipart.addBodyPart(contentPart);
            if (!ArrayUtil.isEmpty(APPENDIX)) {//添加附件
                MimeBodyPart mimeBodyPart = null;
                File file = null;
                for (String appendix : APPENDIX) {
                    mimeBodyPart = new MimeBodyPart();
                    file = new File(appendix);
                    FileDataSource source = new FileDataSource(file);
                    mimeBodyPart.setDataHandler(new DataHandler(source));//添加附件的内容
                    mimeBodyPart.setFileName(MimeUtility.encodeWord(
                            APPENDIX_NAME, "GBK", null));
                    multipart.addBodyPart(mimeBodyPart);
                }
            }
            message.setContent(multipart);//将multipart对象放到message中
            message.saveChanges(); //保存邮件
            Transport transport = session.getTransport("smtp");//发送邮件
            transport.connect(HOST, FROM, AUTH);//连接服务器的邮箱
            transport.sendMessage(message, message.getAllRecipients());//把邮件发送出去
            transport.close();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String content = "java发送邮件乱码解决方法：\n" +
        "1. 发送正文时在setContent()或setHeader()中指定编码;\n" +
                "2. 在setSubject()中指定邮件标题的编码;";

        send(content, new String[]{"lewy95@aliyun.com"});
    }
}
