package cn.xzxy.lewy.mail;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * java发送邮箱工具类
 */
@Slf4j
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
            log.error("读取邮件配置信息失败，错误原因{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件（支持发送 纯文本 / 附件）
     */
    public static void sendText(String context, String[] TOS) {
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
            //向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();
            // 设置邮件的文本内容
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(context, "text/plain; charset=utf-8");
            multipart.addBodyPart(contentPart);
            //添加附件
            if (!ArrayUtil.isEmpty(APPENDIX)) {
                MimeBodyPart mimeBodyPart = null;
                File file = null;
                int i = 1;
                for (String appendix : APPENDIX) {
                    mimeBodyPart = new MimeBodyPart();
                    file = new File(appendix);
                    FileDataSource source = new FileDataSource(file);
                    mimeBodyPart.setDataHandler(new DataHandler(source));//添加附件的内容
                    mimeBodyPart.setFileName(MimeUtility.encodeWord(
                            APPENDIX_NAME + "_" + i, "GBK", null));
                    multipart.addBodyPart(mimeBodyPart);
                    i++;
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

    /**
     * 发送邮件（支持发送 html / 附件）
     * html中可绘制表格等
     */
    public static void sendHtml(String context, List<?> tableList, String[] TOS) {
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
            //向multipart对象中添加邮件的各个部分内容，alternative包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容（不推荐纯文本和超文本一起发送）
            // MimeBodyPart contentPart = new MimeBodyPart();
            // contentPart.setContent(context, "text/plain; charset=utf-8");
            // multipart.addBodyPart(contentPart);

            // 添加文本性表格
            if (tableList != null && !tableList.isEmpty()) {
                MimeBodyPart tablePart = new MimeBodyPart();
                String html = convertToHtml(context, (List<JobCheckResultCollect>) tableList);
                tablePart.setContent(html, "text/html;charset=UTF-8");
                multipart.addBodyPart(tablePart);
            }

            //添加附件
            if (!ArrayUtil.isEmpty(APPENDIX)) {
                MimeBodyPart mimeBodyPart = null;
                File file = null;
                int i = 1;
                for (String appendix : APPENDIX) {
                    mimeBodyPart = new MimeBodyPart();
                    file = new File(appendix);
                    FileDataSource source = new FileDataSource(file);
                    mimeBodyPart.setDataHandler(new DataHandler(source));//添加附件的内容
                    mimeBodyPart.setFileName(MimeUtility.encodeWord(
                            APPENDIX_NAME + "_" + i, "GBK", null));
                    multipart.addBodyPart(mimeBodyPart);
                    i++;
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

    /**
     * 将表数据渲染为html文本
     */
    private static String convertToHtml(String context, List<JobCheckResultCollect> tableList) {
        StringBuilder content = new StringBuilder();
        content.append("<html><head></head><body><p>" + context + "</p>");
        content.append("<table border=\"1\" style=\"border: 1px #E8F2F9;font-size=14px;;font-size:18px;\">");
        content.append("<tr style=\"background-color: #428BCA; color:#ffffff\"><th>序号</th><th>机构名称</th><th>业务日期</th><th>总指标数</th><th>告警指标数</th></tr>");
        for (int i = 0; i < tableList.size(); i++) {
            content.append("<tr>");
            content.append("<td>" + (i + 1) + "</td>"); //序号
            content.append("<td>" + tableList.get(i).getOrgCode() + "</td>"); //机构代码
            content.append("<td>" + tableList.get(i).getBusinessDate() + "</td>"); //业务日期
            content.append("<td>" + tableList.get(i).getTotalIndex() + "</td>"); //总指标数
            content.append("<td>" + tableList.get(i).getWarnIndex() + "</td>"); //告警指标数
            content.append("</tr>");
        }

        content.append("</table>");
        content.append("</body></html>");
        return content.toString();
    }

    public static void main(String[] args) {

        String content = "java发送邮件乱码解决方法：\n" +
                "1. 发送正文时在setContent()或setHeader()中指定编码;\n" +
                "2. 在setSubject()中指定邮件标题的编码;";

        List<JobCheckResultCollect> collects = new ArrayList<>();
        collects.add(new JobCheckResultCollect("collect001", "check001", "org001", "2015-11-06", 5, 3, new Date()));
        collects.add(new JobCheckResultCollect("collect002", "check002", "org002", "2016-11-06", 5, 2, new Date()));
        collects.add(new JobCheckResultCollect("collect003", "check003", "org003", "2017-11-06", 5, 0, new Date()));
        collects.add(new JobCheckResultCollect("collect004", "check004", "org004", "2018-11-06", 5, 1, new Date()));
        collects.add(new JobCheckResultCollect("collect005", "check005", "org005", "2019-11-06", 5, 3, new Date()));

        // 发送纯文本
        //sendText(content, new String[]{"lewy95@aliyun.com"});
        // 发送超文本
        sendHtml(content, collects, new String[]{"lewy95@aliyun.com"});
    }
}
