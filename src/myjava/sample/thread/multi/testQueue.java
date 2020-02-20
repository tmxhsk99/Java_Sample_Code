/**
 * @brief
 * @Detail
 */
package myjava.sample.thread.multi;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class testQueue {
	public static void main(String[] args) {

		/*public void offer(Element data);//순차보관
		public Element poll();//가장 먼저 보관한 값 꺼내고 반환
		public Element peek();//가장 먼저 보관한 값 단순 참조, 꺼내지 않음
		public boolean empty(); //비어있는지 판별
		*/
		
		Queue<String> q = new LinkedList<String>();
		q.offer("first");
		q.offer("second");
		System.out.println(q.peek());
		
		System.out.println(q.poll());
		
		q.offer("third");
		q.offer("forth");
		while(q.isEmpty() ==false){
			System.out.println(q.poll());
		}
		
	}
}
