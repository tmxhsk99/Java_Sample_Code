package myjava.sample.foundation;

public class UsingObject {
	/**
	 * @brief
	 * �ڹ� ������Ʈ Ŭ����
	 * @details
	 * �ڹٿ��� �ֻ��� Ŭ������ Object
	 * Oject�� JVM�� �޼��� (C/C++ ������ native �޼��� )
	 * �� �̿��Ͽ� �޸𸮸� �����Ѵ�. 
	 * �ڹ��� ��� ��ü�� Object�� ��� �޾� �޸𸮸� �ڵ����� �����Ѵ�. 
	 * Oject�� native hashCode(),
	 * getClass()�� JVM�� �޼��带 ȣ���Ͽ� ���� ��ü�� ���� �ּҿ� ��ü Ÿ���� ��ȯ�Ѵ�.
	 * Object Ŭ������ ���� ��ü�� �����Ѵ�. 
	 * 
	 * Object ������ JVM�� �����ϴ� �ؽ��ڵ�� ����ڰ� ������ ���ִ� 
	 * ���۷����� �غ�ȴ�.
	 * 
	 * Object Ŭ������ ����ƽ ������ �ö󰡰� ��ü ������ �� ������ ������ 
	 * ���۷����� ���� ������ �ְԵ� 
	 * 
	 * ��ü 2�� �����ϸ� Ŭ���� �ϳ��� ��ü 2�� 
	 * �� ��ü�� ���� ���� ���۷��� �ؽ��ڵ尡 �����ȴ�. 
	 * 
	 * JVM�� �����Ϸ��� ��ü�� �ؽ��ڵ� �����Ѵ�.
	 * Object�� hashCode �޼���� �÷����� �������̸� 
	 * C/C++ �� ������ ����Ƽ��(native) �޼��带 ȣ���Ѵ�.
	 * 
	 * ���� Ŭ������ �̿��Ͽ� ��ü�� ���� �ּ� �ؽ��ڵ� 16���� ���ڿ��� ��ȯ�Ѵ�.
	 * == �񱳴� ���� ������ ���۷����� ���Ѵ�.
	 * new �� �����ϴ� ���� Ÿ���� ���۷����� ��ü���� �����ϹǷ� �׻� false�̴�.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Object obj1 = new Object();
		Object obj2 = new Object();
		
		System.out.println("��ü  �ؽ��ڵ� native 10���� : "+obj1.hashCode());
		System.out.println("��ü  �ؽ��ڵ� 16���� : "+Integer.toHexString(obj1.hashCode()));
		System.out.println("obj1�� obj2�� ������? : "+obj1 == obj2);//��ü�� �����ϴ�
		System.out.println("equals��� " + obj1.equals(obj2));
		System.out.println(obj1);
		System.out.println(obj2.toString());//Class@hashCode()
		
		System.out.println(obj1.getClass().getName());//Ŭ���� �̸�
		String str = obj1.getClass().getName()+"@"+Integer.toHexString(obj1.hashCode());
		System.out.println(str);//Ŭ���� �̸� @ 16���� �ؽ��ڵ�
		
		Object objstr = new String ("Good");
		System.out.println(objstr.toString());
		System.out.println(objstr instanceof Object);
		System.out.println(objstr instanceof String);
		
		String hello = "hello";
		System.out.println(hello.getClass());
				
		
	}
}
