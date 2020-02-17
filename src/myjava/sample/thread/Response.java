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
					out.write(("입력한 값 : "+ tempstr).getBytes("utf-8"));
					System.out.println("입력한 값 : "+tempstr);
					out.write('\r');
					out.write('\n');
					 generateCaracters_ex(out);
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
	public static void generateCaracters(OutputStream out) throws IOException{//한바이트씩 전송하는 메서드 
		int firstPrintableCharacter = 33;
		int numberOfPrintableCharacters =94;
		int numberOfPrintablePerLine = 72; 
		int roof = 0;
		int start = firstPrintableCharacter;
		while(roof < 2){ /*루프*/
			for(int i = start ;i<start+numberOfPrintablePerLine;i++ ){
				out.write((
						(i - firstPrintableCharacter) % numberOfPrintableCharacters)
						+ firstPrintableCharacter);
			}
			out.write('\r');
			out.write('\n');
			start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
			roof++;
		}
	}
	public static void generateCaracters_ex(OutputStream out) throws IOException{//한번에 전송하는 메서드 
		int firstPrintableCharacter = 33;
		int numberOfPrintableCharacters = 94;
		int numberOfPrintablePerLine = 72; 
		int roof = 0;
		int start = firstPrintableCharacter;
		
		byte[] line = new byte[numberOfPrintablePerLine + 2];//+2는 캐리지리턴과 라인피드를 위함  
		
		while(roof < 2){ /*루프*/
			for(int i = start ;i<start+numberOfPrintablePerLine;i++ ){
				line[i - start] = (byte)((i - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter); //해당값을 byte배열에 넣는다 				
			}
			line[72] = (byte) '\r'; 
			line[73] = (byte) '\n'; 
			out.write(line);
			start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
			roof++;
		}
	}
}
