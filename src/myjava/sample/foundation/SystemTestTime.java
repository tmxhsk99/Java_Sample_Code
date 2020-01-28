package myjava.sample.foundation;

import java.util.Calendar;
import java.util.Date;

public class SystemTestTime {
	public static void main(String[] args) {
		long mills = System.currentTimeMillis(); //1970.1.1���� ����� msec
		System.out.println(mills/1000/24/60/60); //��ĥ���
		
		Date d = new Date(); //���� 
		System.out.println(d);
		Date dd = new Date(d.getTime()+24*60*60*1000); //�Ϸ� �� 
		System.out.println(dd);
		
		Calendar cal1970 = Calendar.getInstance();
		cal1970.set(1970,1-1,1);//0~11��, 1970.1.1
		
		Calendar today = Calendar.getInstance(); //����  
		printCalendar(today);
		long minus = today.getTimeInMillis()-cal1970.getTimeInMillis();
		System.out.println(minus); //1970.1.1 ~ ���� msec
		System.out.println(minus/1000/24/60/60);//1970����� ��ĥ �����°�?
	}
	
	public static void printCalendar(Calendar c) {
		System.out.println("-------------------------");
		System.out.println(c.get(Calendar.YEAR)+"��");//���
		System.out.println(c.get(Calendar.MONTH)+1+"��");//���
		System.out.println(c.get(Calendar.DAY_OF_MONTH)+"��");//����
		System.out.println(c.get(Calendar.AM_PM) ==1 ? "PM" : "AM");//1 PM,0 AM
		System.out.println(c.get(Calendar.HOUR_OF_DAY)+"��");//���
		System.out.println(c.get(Calendar.MINUTE)+"��");//��
		System.out.println(c.get(Calendar.SECOND)+"��");//��
	}
}
