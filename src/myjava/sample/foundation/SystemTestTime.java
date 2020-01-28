package myjava.sample.foundation;

import java.util.Calendar;
import java.util.Date;

public class SystemTestTime {
	public static void main(String[] args) {
		long mills = System.currentTimeMillis(); //1970.1.1부터 경과한 msec
		System.out.println(mills/1000/24/60/60); //며칠경과
		
		Date d = new Date(); //오늘 
		System.out.println(d);
		Date dd = new Date(d.getTime()+24*60*60*1000); //하루 후 
		System.out.println(dd);
		
		Calendar cal1970 = Calendar.getInstance();
		cal1970.set(1970,1-1,1);//0~11월, 1970.1.1
		
		Calendar today = Calendar.getInstance(); //오늘  
		printCalendar(today);
		long minus = today.getTimeInMillis()-cal1970.getTimeInMillis();
		System.out.println(minus); //1970.1.1 ~ 오늘 msec
		System.out.println(minus/1000/24/60/60);//1970년부터 며칠 지났는가?
	}
	
	public static void printCalendar(Calendar c) {
		System.out.println("-------------------------");
		System.out.println(c.get(Calendar.YEAR)+"년");//몇년
		System.out.println(c.get(Calendar.MONTH)+1+"월");//몇월
		System.out.println(c.get(Calendar.DAY_OF_MONTH)+"일");//몇일
		System.out.println(c.get(Calendar.AM_PM) ==1 ? "PM" : "AM");//1 PM,0 AM
		System.out.println(c.get(Calendar.HOUR_OF_DAY)+"시");//몇시
		System.out.println(c.get(Calendar.MINUTE)+"분");//분
		System.out.println(c.get(Calendar.SECOND)+"초");//초
	}
}
