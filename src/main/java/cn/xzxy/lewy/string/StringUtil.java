package cn.xzxy.lewy.string;

import java.util.List;

public class StringUtil {

    public static String listConvertToStr(List list, String separator) {
        if(list.size() == 0) {
            return "";
        } else {
            StringBuilder sbuf = new StringBuilder();
            sbuf.append(list.get(0));

            for(int idx = 1; idx < list.size(); ++idx) {
                sbuf.append(separator);
                sbuf.append(list.get(idx));
            }
            return sbuf.toString();
        }
    }
}
