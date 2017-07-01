/**
 * 
 */
package serialCommunication;

/**
 * SerialBlocksGHMC 
 * 
 * Contains all the SerialData to be received and 
 * sent for the Guitar Hero Midi Controller. When 
 * it is operating in Normal Mode. Additional 
 * messages used for debugging are in the 
 * DebugSerialBlocksGHMC.
 * 
 * @author mario
 *
 */
public class SerialBlocksGHMC extends SerialBlocks {
	final private int blockSize = 6;
	
	final private String[] optionList = {
											"STARTC",
											"CALCRC",
										};

	final private byte startc[] = {
									0x53, 	//S
									0x54, 	//T
									0x41,	//A
									0x52,	//R
									0x54,	//T
									0x43	//C							
								  };
		
	final private byte calcrc[] = {
									0x43,	//C
									0x41,	//A
									0x4C,	//L
									0x43,	//C
									0x52,	//R
									0x43,	//C
								  };
	

	@Override
	public int getBlockSize() {
		return blockSize;
	}

	@Override
	public String[] getOptionList() {
		return optionList;
	}
	
	@Override
	public byte[] getWriteBlock(String option) {
		String compare = new String(option);
		byte writeBlock[] = new byte[blockSize];
		try{
			if(compare.equals("STARTC") || compare.equals("CALCRC")){	
				switch(compare){
					case "STARTC":
						writeBlock = startc;
						break;
					case "CALCRC":
						writeBlock = calcrc;
						break;
				} 
			} else {
				throw new IllegalArgumentException("No such option");
			}
		} catch(Exception e){
			System.err.println("Error occurred in SerialBlocksGHMC::getWriteBlock(String option)");
			e.printStackTrace(System.err);
		}
		
		return writeBlock;
	}
	
	@Override
	public String getString(int state) {
		String result = new String("Unknown");
		
		switch(state){
			case 1:
				result = new String("ID01GH, Identification Block");
				break;
			case 2:
				result = new String("CONTSC, Continue Serial Communication Block");
				break;
			case 3:
				result = new String("SUCCES, Success Block");
				break;
			case -1:
				result = new String("FAILTA, Failed Transfer Again Block");
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
      
		if(compareSuccessBlock(data)){
			return 3;
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
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect");	
			}
		} catch(Exception e){
			System.err.println("Error occurred in SerialBlocksGHMC::compareIdentificationBlock(byte datap[])");
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
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect");	
			}		
		} catch(Exception e){
			System.err.println("Error occurred in SerialBlocksGHMC::compareContinueBlock(byte data[])");
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
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect");				
			}			
		} catch(Exception e){
			System.err.println("Error occurred in SerialBlocksGHMC::compareSuccess(byte data[])");
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
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect");		
			}
		} catch(Exception e){
			System.err.println("Error occurred in SerialBlocksGHMC::compareFailedBlock(byte data[])");
			e.printStackTrace(System.err);
		}
		
		return result;
	}
	
}
