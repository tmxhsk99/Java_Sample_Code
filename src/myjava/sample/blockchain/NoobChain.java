/**
 * @brief
 * @Detail
 */
package myjava.sample.blockchain;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

import io.netty.util.concurrent.BlockingOperationException;

/**
 * @author juhyeon
 * @biref 
 * @details 최초 블럭을 생성한다.  
 * @date 
 * @version
 * 
 */
public class NoobChain {
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	
	public static void main(String[] args) {
		
		Block genesisBlock = new Block("Hi im the first block", "0");
		System.out.println("Hash for block 1 : " + genesisBlock.hash);
		
		
		Block secondBlock = new Block("Yo im the second block",genesisBlock.hash);
		System.out.println("Hash for block 2 : " + secondBlock.hash);
		
		
		Block thirdBlock = new Block("Hey im the third block", secondBlock.hash);
		System.out.println("Hash for block 3 : " + thirdBlock.hash);
		
		//해당 블럭을 리스트에 넣는다 . 
		blockchain.add(new Block("Hi im the first block", "0"));
		blockchain.add(new Block("Yo im the second block", blockchain.get(blockchain.size()-1).hash));
		blockchain.add(new Block("Hey im the third block", blockchain.get(blockchain.size()-1).hash));		
				
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		
		System.out.println(blockchainJson);
		
	}
	
	public static Boolean isChainVaild(){
		Block currentBlock;
		Block previosBlock; 
		
		//루프를 돌면서 블록을 체크한다. 
		for(int i=1; i< blockchain.size(); i++){
			currentBlock = blockchain.get(i);
			previosBlock = blockchain.get(i-1);
			
			//현제 해시를 비교
			if(!currentBlock.hash.equals(currentBlock.calulateHash())){
				System.out.println("Current Hashes not equal");
				return false;
			}			
			//이전해시 비교
			if(!previosBlock.hash.equals(currentBlock.previousHash)){
				System.out.println("Previos Hashes not equal");
				
				return false;
			}			
			
		}	
		
		return true;
		
	}
}
