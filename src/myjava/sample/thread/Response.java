/**
 * @brief
 * @Detail
 */
package myjava.sample.thread;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class Response implements Runnable{
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private DataInputStream dis;
	private ByteArrayOutputStream bos;
	
	public Response(Socket socket) throws IOException {
		this.socket = socket;
		this.out = socket.getOutputStream();
		this.in = socket.getInputStream();
		this.dis = new DataInputStream(in);
		
	}

	@Override
	public void run() {
		service ();	
	}

	/**
	 * @brief 
	 * @details   
	 */
	private void service() {		
		try {						
			while (true){
				try {
					int request = in.read();
					byte[] inbyte = intTobyte(request);
					String tempstr = new String (inbyte,"utf-8");					
					out.write(("입력한 값 : "+tempstr).getBytes("utf-8"));
				} catch(Exception e){
					break;
				}				
			}				
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
	}
	public static byte[] intTobyte(int value) {
        byte[] bytes=new byte[4];
        bytes[0]=(byte)((value&0xFF000000)>>24);
        bytes[1]=(byte)((value&0x00FF0000)>>16);
        bytes[2]=(byte)((value&0x0000FF00)>>8);
        bytes[3]=(byte) (value&0x000000FF);
        return bytes;
	}

}
