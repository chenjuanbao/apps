package com.mediwit.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.text.format.DateUtils;



public class DatetimeUtils {
    public static Date parse(String date) {
        return parse(date, Datetime._DATETIMES_DEFAULT);
    }

    public static Date parse(String date, String pattern) {
        if (StringUtil.isNotBlank(date)) {
            try {
                if (StringUtil.isBlank(pattern))
                    return new SimpleDateFormat(Datetime._DATETIMES_DEFAULT)
                            .parse(date);
                else
                    return new SimpleDateFormat(pattern).parse(date);
            } catch (ParseException e) {
                // e.printStackTrace();
            }
        }
        return null;
    }

    public static String toString(Date date) {
        return toString(date, Datetime._DATETIMES_DEFAULT);

    }
    public static String getDateStr() {
        Date date=new Date();
        return toString(date, Datetime._DATE_DEFAULT);

    }
    public static String formatDate(Date date,String format){
        //startTime
        return toString(date, format);
    }

    public static String getTime(Date date){
        if(null==date)
            date=new Date();
        return toString(date, "hh:mm");
    }
    public static String getDateTime(){
        Date date=new Date();
        return toString(date,Datetime._DATETIMES_DEFAULT);
    }
//


    public static String getShortDateStr() {
        Date date=new Date();
        return toString(date, Datetime._DATE_SHORT);


    }

    public static String getShortDateStr(Date date) {

        return toString(date, Datetime._DATE_SHORT);


    }

    public static String toString(Date date, String pattern) {
        try {
            if (StringUtil.isBlank(pattern))
                return (new SimpleDateFormat(Datetime._DATETIMES_DEFAULT)).format(date);
            else
                return (new SimpleDateFormat(pattern)).format(date);
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return null;
    }

    /**
     * 获得两个时间之差(毫秒).
     *
     * @param startDate
     *            开始时间.
     * @param endDate
     *            结束时间.
     * @param patten
     *            时间格式.
     *
     * @return 两个时间之差(毫秒).
     */
    public static long diff(String start, String end) {
        return diff(start, end, Datetime._DATETIMES_DEFAULT);
    }

    public static long diff(String start, String end, String patten) {
//        Date sdate = parse(start, patten);
//        Date edate = parse(end, patten);
        return diff(parse(start, patten), parse(end, patten));
    }

    public static long diff(Date start, Date end) {
        return end.getTime() - start.getTime();
    }

    /**
     * 功能描述：两个时间相差小时数
     *
     * 参数解释：
     * 返回值：long
     *
     */
    public static double hDiff(Date start, Date end) {
        return (end.getTime() - start.getTime())/(60.0*60*1000);
    }

    public static double hDiff(String start, String end) {
        return (parse(end, Datetime._DATETIMES_DEFAULT).getTime() - parse(start, Datetime._DATETIMES_DEFAULT).getTime())/(60.0*60*1000);
    }

    public static double hDiff(Object start, Object end) {
        return (parse(end.toString(), Datetime._DATETIMES_DEFAULT).getTime() - parse(start.toString(), Datetime._DATETIMES_DEFAULT).getTime())/(60.0*60*1000);
    }


    /**
     * 获得时间.
     *
     * @param date
     *            时间.
     *
     * @return 时间的数据(年、月、日、周等).
     */

    public static int getDay(Date date) {
        try {
            return getCalendar(date).get(Calendar.DATE);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return 0;
    }

    public static int getWeek(Date date) {
        try {
            return getCalendar(date).get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return 0;
    }

    public static int getMonth(Date date) {
        try {
            return getCalendar(date).get(Calendar.MONTH)+1;
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return 0;
    }
    
    public static int getYear(Date date) {
        try {
            return getCalendar(date).get(Calendar.YEAR);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return 0;
    }

    private static Calendar getCalendar(Date date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    public static List getDateList(String start, long len, int step, int field, boolean isBothSide,
            boolean isString) {
        int side;
        if (isBothSide) {
            side = 0;
        } else {
            side = 1;
        }
        List list = new ArrayList();

        Date date = parse(start, Datetime._DATETIMES_DEFAULT);

        for (int i = 0; i < (len - side); i++) {
            if (isString)
                list.add(add(date, i + side + step, field));
            else
                list.add(toString(add(date, i + side + step, field),Datetime._DATETIMES_DEFAULT));
        }
        return list;
    }
    
    public static Date add(Date date, int amount, int field) {
        Calendar calendar = getCalendar(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }
    
//---------------------------------------------------------------------------------
    /**
     * 获得两个时间之差.
     *
     * @param startDate
     *            开始时间.
     * @param endDate
     *            结束时间.
     * @param patten
     *            时间格式.
     * @param divisor
     *            时间类型的除数.
     * @return 两个时间之差.
     */
    public static long getPeriod(String startDate, String endDate,
            String patten, long divisor) {
        Date sdate = parse(startDate, patten);
        Date edate = parse(endDate, patten);
        if (divisor <= 0)
            return (edate.getTime() - sdate.getTime());
        else
            return (edate.getTime() - sdate.getTime()) / divisor;
    }



// --------------------------

    public static Date getDate(String time, int period) {
        SimpleDateFormat df = new SimpleDateFormat(Datetime._DATE_DEFAULT);
        SimpleDateFormat df1 = new SimpleDateFormat(Datetime._DATETIMES_DEFAULT);
        Date date = null;
        if (StringUtil.isNotBlank(time)) {
            try {
                date = df1.parse(df.format(new Date()) + " " + time);
                if (date.after(new Date())) {
                    return date;
                } else {
                    return null;
                }
            } catch (ParseException e) {
                // e.printStackTrace();
            }
        }
        return date;
    }





    public static String LastDate(){
        SimpleDateFormat formatter = new SimpleDateFormat(Datetime._DATE_SHORT);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1);
        String currentDate = formatter.format(c.getTime());// 当前时间
        return currentDate;
    }


    public static String[] getDateBetween(String date){
        String[] dateBetween=new String[2];
        if(date.length()==8){//20130101
            dateBetween[0]=date;
            dateBetween[1]=date;
        }else if(date.length()==6){//201301
            dateBetween[0]=date+"01";
            dateBetween[1]=date+"31";
        }else if(date.length()==5){//201301
            if(date.endsWith("1")){
                dateBetween[0]=date.substring(0, 4)+"0101";
                dateBetween[1]=date.substring(0, 4)+"0331";
            }else if(date.endsWith("2")){
                dateBetween[0]=date.substring(0, 4)+"0401";
                dateBetween[1]=date.substring(0, 4)+"0631";
            }else if(date.endsWith("3")){
                dateBetween[0]=date.substring(0, 4)+"0701";
                dateBetween[1]=date.substring(0, 4)+"0931";
            }else if(date.endsWith("4")){
                dateBetween[0]=date.substring(0, 4)+"01001";
                dateBetween[1]=date.substring(0, 4)+"01231";
            }
        }else if(date.length()==4){//201301
            dateBetween[0]=date+"0101";
            dateBetween[1]=date+"1231";
        }
        return dateBetween;
    }


    public static String[] getTimeBetween(String date){
        if(null==date){
            date=DatetimeUtils.getDateStr();
        }
        String[] dateBetween=new String[2];
        if(date.length()==8){//20130101
            dateBetween[0]=date+" 00:00:00";
            dateBetween[1]=date+" 23:59:59";
        }else if(date.length()==6){//201301
            dateBetween[0]=date+"-01 00:00:00";
            dateBetween[1]=date+"-31 23:59:59";
        }else if(date.length()==5){//201301
            if(date.endsWith("1")){
                dateBetween[0]=date.substring(0, 4)+"-01-01 00:00:00";
                dateBetween[1]=date.substring(0, 4)+"-03-31 23:59:59";
            }else if(date.endsWith("2")){
                dateBetween[0]=date.substring(0, 4)+"-04-01 00:00:00";
                dateBetween[1]=date.substring(0, 4)+"-06-31 23:59:59";
            }else if(date.endsWith("3")){
                dateBetween[0]=date.substring(0, 4)+"-07-01 00:00:00";
                dateBetween[1]=date.substring(0, 4)+"-09-31 23:59:59";
            }else if(date.endsWith("4")){
                dateBetween[0]=date.substring(0, 4)+"-10-01 00:00:00";
                dateBetween[1]=date.substring(0, 4)+"-12-31 23:59:59";
            }
        }else if(date.length()==4){//201301
            dateBetween[0]=date+"-01-01 00:00:00";
            dateBetween[1]=date+"-12-31 23:59:59";
        }else{
            dateBetween[0]=date+"-01-01 00:00:00";
            dateBetween[1]=date+"-12-31 23:59:59";
        }
        return dateBetween;
    }

    public static void main(String[] args) {
//        Date startDate=parse("2013-08-26 15:30:21");
//
//        Date now=new Date();
//
//
//        double hdiff=hDiff(startDate,now);
//
//        System.out.println("hdiff="+hdiff);
        String[] bd=getDateBetween("20130203");
        System.out.println("start="+bd[0]+"   end="+bd[1]);

    }
}
