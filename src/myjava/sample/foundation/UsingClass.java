package myjava.sample.foundation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class UsingClass {
	public static void main(String[] args) {
		Object obj1 = new Object();
		Class classes = obj1.getClass();
		System.out.println(classes.getName());//클래스 이름
		System.out.println("----Construct----");
		Constructor [] contructor = classes.getDeclaredConstructors();
		for(Constructor con : contructor) {
			System.out.println(con.getName());
		}
		System.out.println("----Method----");
		Method[] method = classes.getMethods();
		for(Method met : method) {
			System.out.println(met.getName());
		}
	}
}
