package myjava.sample.foundation;

public class UsingObject {
	/**
	 * @brief
	 * 자바 오브젝트 클래스
	 * @details
	 * 자바에서 최상위 클래스는 Object
	 * Oject는 JVM의 메서드 (C/C++ 구현되 native 메서드 )
	 * 를 이용하여 메모리를 관리한다. 
	 * 자바의 모든 객체는 Object를 상속 받아 메모리를 자동으로 관리한다. 
	 * Oject의 native hashCode(),
	 * getClass()는 JVM의 메서드를 호출하여 각각 객체의 고유 주소와 객체 타입을 반환한다.
	 * Object 클래스에 대한 객체를 생성한다. 
	 * 
	 * Object 생성시 JVM이 구별하는 해시코드와 사용자가 구별할 수있는 
	 * 레퍼런스도 준비된다.
	 * 
	 * Object 클래스가 스택틱 영역에 올라가고 객체 영역은 힙 영역에 생성됨 
	 * 레퍼런스는 스택 영역에 있게됨 
	 * 
	 * 객체 2개 생성하면 클래스 하나와 객체 2개 
	 * 각 객체에 대한 고유 레퍼런스 해시코드가 생성된다. 
	 * 
	 * JVM이 구별하려는 객체의 해시코드 리턴한다.
	 * Object의 hashCode 메서드는 플랫폼에 종속적이며 
	 * C/C++ 로 구현된 네이티브(native) 메서드를 호출한다.
	 * 
	 * 랩퍼 클래스를 이용하여 객체의 고유 주소 해시코드 16진수 문자열로 변환한다.
	 * == 비교는 스택 영역의 레퍼런스를 비교한다.
	 * new 로 생성하는 참조 타입의 레퍼런스는 객체마다 고유하므로 항상 false이다.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Object obj1 = new Object();
		Object obj2 = new Object();
		
		System.out.println("객체  해시코드 native 10진수 : "+obj1.hashCode());
		System.out.println("객체  해시코드 16진수 : "+Integer.toHexString(obj1.hashCode()));
		System.out.println("obj1과 obj2는 같은가? : "+obj1 == obj2);//객체는 고유하다
		System.out.println("equals사용 " + obj1.equals(obj2));
		System.out.println(obj1);
		System.out.println(obj2.toString());//Class@hashCode()
		
		System.out.println(obj1.getClass().getName());//클래스 이름
		String str = obj1.getClass().getName()+"@"+Integer.toHexString(obj1.hashCode());
		System.out.println(str);//클래스 이름 @ 16진수 해시코드
		
		Object objstr = new String ("Good");
		System.out.println(objstr.toString());
		System.out.println(objstr instanceof Object);
		System.out.println(objstr instanceof String);
		
		String hello = "hello";
		System.out.println(hello.getClass());
				
		
	}
}
