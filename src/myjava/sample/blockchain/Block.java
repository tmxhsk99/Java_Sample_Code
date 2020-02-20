/**
 * @brief
 * @Detail
 */
package myjava.sample.blockchain;

import java.util.Date;

/**
 * @author juhyeon
 * @biref 
 * @details 
 * @date 
 * @version
 * 
 */
public class Block {
	public String hash; 
	public String previousHash;
	private String data; 
	private long timeStamp;
	private int nonce;

	public Block(String data , String previosHash) {
		this.data = data;
		this.previousHash = previosHash;		
		this.timeStamp = new Date().getTime();
		this.hash = calulateHash(); 
	} 

	public String calulateHash(){
		//이전 해시 + 타임스탬프 + 데이터 를  sha-256 Hash 후 HexString으로 변환 
		String calculatedhash = StringUtil.applySha256
								(previousHash + 
								 Long.toString(timeStamp) + 
								 data);
		return calculatedhash;		
	}
	
	public void mineBlock(int difficulty){
		String target = new String(new char[difficulty]).replace("\0","0");
		while(!hash.substring(0,difficulty).equals(target)){
			nonce ++;
			hash = calulateHash();
		}
		System.out.println("Block Mined!! : " + hash);
	}
	
}
