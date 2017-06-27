/**
 * 
 */
package serialCommunication;

/**
 * @author mario
 *
 */
public class IncomingSerialGHMC extends IncomingSerial {
	final private int blockSize = 6;
	/* (non-Javadoc)
	 * @see serialCommunication.CompareSerialBlocks#compare(byte[])
	 */

	@Override
	public int getBlockSize() {
		return blockSize;
	}

	@Override
	public String getString(int state) {
		String result = new String("Unknown");
		
		switch(state){
			case 1:
				result = new String("ID01GH (ID01GH (Identification Block)");
				break;
			case 2:
				result = new String("CONTSC (Continue Serial Communication Block)");
				break;
			case 3:
				result = new String("CRCSND (Cyclic Redundancy Check Send Block)");
				break;
			case 4:
				result = new String("SUCCES (Success Block)");
				break;
			case 5:
				result = new String("CLOSEC (close Communication Block)");
				break;

			case -1:
				result = new String("FAILTA (Failed Transfer Again Block)");
				break;
		}	
		return result;
	}


	@Override
	public int compare(byte data[]){	
		if(compareIdentificationBlock(data)){
			return 1;
		}	
		
		if(compareContinueBlock(data)){
			return 2;
		}
		
		if(compareCyclicRedundancyCheckSendBlock(data)){
			return 3;
		}
      
		if(compareSuccessBlock(data)){
			return 4;
		}
		
		if(compareCloseCommunicationBlock(data)){
			return 5;
		}
			
		if(compareFailedBlock(data)){
			return -1;
		}
		
		return 0;
	}
	

	
	/** 
	 * Compare data received in the serial buffer 
	 * if true start sending protocol data for 
	 * the EEPROM in chunks of 64 Bytes.
	 *  
	 * @param serial data[] buffer with a length of 6 bytes
	 * @return true if Acknowledgment block has been found
	 */
	

	private boolean compareIdentificationBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x49 &&
					data[1] == 0x44 &&
					data[2] == 0x00 && 
					data[3] == 0x01 &&
					data[4] == 0x47 && 
					data[5] == 0x48
				  )
				{
					result = true;
				}	
			} else {
				System.err.println("Error occurred in IncomingSerialGHMC compareIdentificationBlock(byte data[])");
				System.err.print("Missmatch in the supplied data[], length is incorrect");
			}
		} catch(Exception e){
			System.err.println("Error occurred in IncomingSerialGHMC compareIdentificationBlock(byte datap[])");
			e.printStackTrace(System.err);
		}
		return result;
	}
	
	/**
	 * Compare data received in the serial buffer 
	 * if true continue sending protocol data for 
	 * the EEPROM. 
	 * 
	 * @param serial data[] buffer with a length of 6 bytes
	 * @return true if Continue Block has been found
	 */
	
	private boolean compareContinueBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x43 && //C
					data[1] == 0x4F && //O
					data[2] == 0x4E && //N
					data[3] == 0x54 && //T
					data[4] == 0x53 && //S
					data[5] == 0x43    //C
				  )
				{
					result = true;
				}
			} else {
				System.err.println("Error occurred in IncomingSerialGHMC compareContinueBlock(byte data[])");
				System.err.print("Missmatch in the supplied data[], length is incorrect");
			}		
		} catch(Exception e){
			System.err.println("Error occurred in IncomingSerialGHMC compareContinueBlock(byte data[])");
			e.printStackTrace(System.err);
		}		
		return result;
	}
	
	/**
	 * Compare data received in the serial buffer 
	 * if true the CRC send to the hardware matches 
	 * the CRC calculated thus the transfer was a 
	 * success. 
	 * 
	 * @param serial data[] buffer with a length of 6 bytes
	 * @return true if Succes Block has been found
	 */
	
	private boolean compareSuccessBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x53 && //S
					data[1] == 0x55 && //U
					data[2] == 0x43 && //C
					data[3] == 0x43 && //C
					data[4] == 0x45 && //E
					data[5]	== 0x53	   //S
				  )
				{
					result = true;
				}
			} else {
				System.err.println("Error occurred in IncomingSerialGHMC compaeeSuccess(byte data[])");				
				System.err.print("Missmatch in the supplied data[], length is incorrect");					
			}			
		} catch(Exception e){
			System.err.println("Error occurred in IncomingSerialGHMC compareSuccess(byte data[])");
			e.printStackTrace(System.err);
		}	
		return result;
	}
	
	/** 
	 * Compare data received in the serial buffer
	 * return true if it matches with the comparison.
	 * the next step is to send the chopped up crc 
	 * for comparison.
	 * 
	 * @param data
	 * @return true if the serial buffer matches
	 */
	
	private boolean compareCyclicRedundancyCheckSendBlock(byte data[]){
		boolean result = false; 
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x43 && //C
					data[1] == 0x52 && //R
					data[2] == 0x43 && //C
					data[3] == 0x53 && //S
					data[4] == 0x4E && //N
					data[5] == 0x44    //D
				  )
				{
					result = true;
				}
				
			} else {
				System.err.println("Error occurred in IncomingSerialGHMC compareCyclicRedundancyCheckSendBlock(byte data[]");				
				System.err.print("Missmatch in the supplied data[], length is incorrect");	
			}
			
			
		} catch(Exception e){
			System.err.println("Error occurred in IncomingSerialGHMC compareCyclicRedundancyCheck(byte data[])");
			e.printStackTrace(System.err);
		}
		return result;
	}
	
	/**
	 * compare data received in the serial buffer
	 * return true if it matches with the comparison.
	 * This is the last message after this the serial
	 * communication can be closed.
	 * 
	 * @param data
	 * @return compare if the data in sereial buffer 
	 * equals (CLOSEC).
	 */
	private boolean compareCloseCommunicationBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x43 && 	//C
					data[1] == 0x4C &&	//L
					data[2] == 0x4F &&	//O
					data[3] == 0x53 &&	//S
					data[4] == 0x45 && 	//E
					data[5] == 0x43		//C
				  )
				{
					result = true;
				}
			} else {
				System.err.println("Error occurred in IncomingSerialGHMC compareCloseCommunicationBlocks(byte data[]");				
				System.err.print("Missmatch in the supplied data[], length is incorrect");	
			}
			
			
			
		}catch(Exception e){
			System.err.println("Error occurred in IncomingSerialGHMC compareCommunicationBlock(byte data[])");
			e.printStackTrace(System.err);
		}
		
		
		return result;
	}
	
	/**
	 * Compare data received in the serial buffer 
	 * if true the CRC send to the hardware does 
	 * not match the CRC calculated. Thus the transfer
	 * was a failure and the data has to be send again. 
	 * 
	 * @param serial data[] buffer with a length of 6 bytes
	 * @return true if Failed Block has been found
	 */
	
	private boolean compareFailedBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x46 && //F
					data[1] == 0x41 && //A
					data[2] == 0x49 && //I
					data[3] == 0x4C && //L
					data[4] == 0x54 && //T
					data[5] == 0x41	   //A
				  )
				{
					result = true;
				}
			} else {
				System.err.println("Error occurred in IncomingSerialGHMC compareFailedBlock(byte data[])");
				System.err.print("Missmatch in the supplied data[], length is incorrect");					
			}
		} catch(Exception e){
			System.err.println("Error occurred in IncomingSerialGHMC compareFailedBlock(byte data[])");
			e.printStackTrace(System.err);
		}
		
		return result;
	}
	
	/**
	 * Compare data received in the serial buffer 
	 * If true Debugging and serial communication 
	 * can start
	 * 
	 * @param serial data[] buffer with a length of 6 bytes
	 * @return true if Failed Block has been found
	 */
	
	/**
	private boolean compareDebugBlock(byte data[]){
		boolean result = false; 
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x44 &&	//D
					data[1] == 0x42 &&	//B
					data[2] == 0x00 &&	//0
					data[3] == 0x01 &&	//1
					data[4] == 0x47 &&	//G
					data[5] == 0x48 	//H
				  )
				{
					result = true;
				}
				
			} else {
				System.err.println("Error occurred in IncomingSerialGHMC compareDebugBlock(byte data[])");
				System.err.print("Missmatch in the supplied data[], length is incorrect");		
			}
		} catch(Exception e){
			System.err.println("Error occurred in IncomingSerialGHMC compareDebugBlock(byte data[])");
			e.printStackTrace(System.err);
		}	
		return result;
	}
	**/
}
