/**
 * @brief
 * @Detail
 */
package myjava.sample.thread.multi.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class Client1 extends Thread{
	//여러메시지를 동시에 보낼 클라이언트 프로그램 
	private String data = null;
	private String ReqName = null;	
	private int roof = 0;
	private int sendCount = 0;
	
	public static void main(String[] args) {
		Client1 t1 = new Client1("test",1000); 
		Client1 t2 = new Client1("write",1000); 
		Client1 t3 = new Client1("syncro",1000);
		
		t1.run();
		t2.run();
		t3.run();
		
		try{
			t1.join();
			t2.join();
			t3.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
	} 
	
	public Client1( String reqName,int roof) {
		super();
		this.ReqName = reqName;
		this.roof = roof;
	}
		

	@Override
	public void run() {		
		Socket socket= null;
		OutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		InputStream in = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
        try {
        	socket  = new Socket("10.10.30.142",9898);
    		os = socket.getOutputStream();
    		osw = new OutputStreamWriter(os);
    		bw = new BufferedWriter(osw);    
			for(int i=0;i<roof;i++){				
				String msg = makeKeyMessage();			
				bw.write(msg);				
				bw.flush();
				//sendCount++;
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(bw !=null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(osw !=null){
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os !=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(socket != null){
				try {
					socket.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}


	/**
	 * @brief
	 * @details
	 */
	private String makeKeyMessage() {		
		if(ReqName == null){
			return "ReqName is null!";
		}
		StringBuffer sb = new StringBuffer(ReqName);
		sb.append(" : ");
		java.security.SecureRandom random = null;
		try {
			random = java.security.SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte bytes [] = new byte[16];
		random.nextBytes(bytes);
		String plainText =bytesToHexString(bytes);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(plainText.getBytes());
		byte[] hash = md.digest();
		
		sb.append(bytesToHexString(hash));
		sb.append("\n");
		return sb.toString();
	}
	
	public static String bytesToHexString(byte[] bytes){
	
		  final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	        char[] hexChars = new char[bytes.length * 2];
	        int v;
	        for ( int j = 0; j < bytes.length; j++ ) {
	            v = bytes[j] & 0xFF;
	            hexChars[j * 2] = hexArray[v >>> 4];
	            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	        }
	        return new String(hexChars);

	}

}
