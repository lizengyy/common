import com.pkg.util.DateTimeUtil;

import java.sql.Timestamp;
import java.util.Date;

public class TestDemo {

    public static void main(String[] args) throws Exception{
        String str1 = "2021-01-01 00:00:00";
        String str2 = "2022-01-01 00:00:00";
        Date date1 = DateTimeUtil.formatStrDate(str1, DateTimeUtil.CT_S);
        Date date2 = DateTimeUtil.formatStrDate(str2, DateTimeUtil.CT_S);
        Timestamp tt1 = new Timestamp(date1.getTime());
        Timestamp tt2 = new Timestamp(date2.getTime());
        int rst = DateTimeUtil.countBetweenDays(date2, date1);
        System.out.println(rst);
    }

}
