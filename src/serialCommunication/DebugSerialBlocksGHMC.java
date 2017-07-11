package serialCommunication;

public class DebugSerialBlocksGHMC extends SerialBlocks{
	int blockSize = 6;
	
	final private String optionList[] = {
										"DBGREQ",
										"STARTC",
										"CRCBGN",
										"CALCRC"
										};
	
	private byte dbgreq[] = {
							  0x44, //D
							  0x42,	//B
							  0x47,	//G
							  0x52,	//R
							  0x45,	//E
							  0x51	//Q
							};
	
	private byte startc[] = {
							 0x53,	//S
							 0x54,	//T
							 0x41,	//A
							 0x52,	//R
							 0x54,	//T
							 0x43	//C
							};

	private byte crcbgn[] = {
							0x43,	//C
							0x52,	//R
							0x43,	//C
							0x42,	//B
							0x47,	//G
							0x4E	//N
							};
	
	private byte calcrc[] = {
							 0x43,	//C
							 0x41, 	//A
							 0x4C,	//L
							 0x43,	//C
							 0x52,	//R
							 0x43	//C
							};
	
	
	

	@Override
	public byte[] getWriteBlock(String option) {
		String compare = new String(option);
		byte writeBlock[] = new byte[blockSize];
		
		try{
			if(
				compare.equals("DBGREQ") ||
				compare.equals("STARTC") ||
				compare.equals("CRCBGN") ||
				compare.equals("CALCRC")
			  )
			{
				switch(compare){
					case "DBGREQ":
						writeBlock = dbgreq;
						break;
					case "STARTC":
						writeBlock = startc;
						break;
					case "CRCBGN":
						writeBlock = crcbgn;
						break;
					case "CALCRC":
						writeBlock = calcrc;
						break;
				}
			}else{
				throw new IllegalArgumentException("No such option");
			}			
		} catch(Exception e){
			System.err.println("Error ocurred in DebugSerialBlocksGHMC::getWriteBlock(String option)");
			e.printStackTrace(System.err);
		}
		return writeBlock;
	}

	@Override
	public String[] getOptionList() {
		return optionList;
	}

	@Override
	public int getBlockSize() {
		return blockSize;
	}


	
	@Override
	public int compare(byte[] data) {
		if(compareDebugIdentificationBlock(data)){
			return 1;
		}
		
		if(compareAcknowledgementBlock(data)){
			return 2;
		}
		
		if(compareContinueBlock(data)){
			return 3;
		}
		
		if(compareCyclicRedundancyCheckSendBlock(data)){
			return 4;
		}
		
		if(compareSuccessBlock(data)){
			return 5;
		}
		
		if(compareFailedBlock(data)){
			return -1;
		}
			
		return 0;
	}

	
	@Override
	public String getString(int state) {
		String result = new String("Unknown");
		
		switch(state){
			case 1: 
				result = new String("DB01GH, Debug Identification Block");
				break;
			case 2:
				result = new String("ACKNOW, Acknowledgement Block");
				break;
			case 3:
				result = new String("CONTSC, Continue Serial Communication Block");
				break;
			case 4:
				result = new String("CRCSND, Cyclic Redundancy Check Send Block");
				break;
			case 5:
				result = new String("SUCCES, Success Block");
				break;
			case -1:
				result = new String("FAILTA, Failed Transfer Again Block");
				break;
		}
		
		return result;
	}

	/**
	 * compare data received in the serial buffer
	 * return true if it matches with the comparison.
	 * The identification message (DB01HGH) is the 
	 * answer on the writeSerialMessage
	 * Debug Request (DBGREQ)
	 * 
	 * @param input serial data
	 * @return true if data[] equals 'DB01GH'
	 */
	
	private boolean compareDebugIdentificationBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x44 &&	//D
					data[1] == 0x42 &&	//B
					data[2] == 0x00 &&	//0
					data[3] == 0x01 &&	//1
					data[4] == 0x47 &&	//G
					data[5] == 0x48		//H
				  )
				{
					result = true;
				}
			} else {
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect");	
			}		
		} catch(Exception e){
			System.err.println("Error occurred in DebugSerialBlocksGHMC::compareDebugIdentificationBlock(byte data][)");
			e.printStackTrace(System.err);
		}	
		return result;
	}

	/**
	 * compare data received in the serial buffer
	 * return true if it matches with the comparison.
	 * The identification message (ACKNOW) is the 
	 * answer on the writeSerialMessage 
	 * Start Communication (STARTC)
	 * 
	 * @param input serial data
	 * @return true if data[] equals 'ACKNOW'
	 */
	
	private boolean compareAcknowledgementBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x41 &&	//A
					data[1] == 0x43 &&	//C
					data[2] == 0x4B &&	//K	
					data[3] == 0x4E	&&	//N	
					data[4] == 0x4F &&	//O
					data[5] == 0x57 	//W
				  )
				{
					result = true;
				}
			} else {
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect");				
			}	
		} catch(Exception e){
			System.err.println("Error occurred in DebugSerialBlocks::compareAcknowledgementBlock(byte data[])");
			e.printStackTrace(System.err);
		}	
		return result;
	}	
	
	/**
	 * compare data received in the serial buffer
	 * return true if it matches with the comparison.
	 * The Continue Serial communication message 
	 * (CONTSC) is send by the MCU when it is 
	 * receiving data.
	 * 
	 * @param input serial data
	 * @return true if data[] equals 'CONTSC'
	 */	
	
	private boolean compareContinueBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x43 &&	//C
					data[1] == 0x4F &&	//O
					data[2] == 0x4E &&	//N
					data[3] == 0x54 && 	//T
					data[4] == 0x53 && 	//S
					data[5] == 0x43		//C
				  )
				{
					result = true;
				}
			} else {
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect");				
			}		
		} catch(Exception e){
			System.err.println("Error occurred in DebugSerialBlocks::compareContinueBlock(byte data[])");
			e.printStackTrace(System.err);
		}	
		return result;
	}
	
	/**
	 * compare data received in the serial buffer
	 * return true if it matches with the comparison.
	 * The Calculate Cyclic Redundancy Send Block
	 * (CRCSND) is send by the MCU when it has
	 * received (CRCBGN) Block and is ready to
	 * receive the four byte CRC. 
	 * 
	 * @param input serial data
	 * @return true if data[] equals 'CRCSND'
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
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect");					
			}		
		} catch(Exception e){
			System.err.println("Error occurred in DebugSerialBlocks::compareCyclicRedundancyCheckSendBlock(byte data[])");
			e.printStackTrace(System.err);
		}	
		return result;
	}
	
	/**
	 * compare data received in the serial buffer
	 * return true if it matches with the comparison.
	 * The Success Block (SUCCES) is send by 
	 * the MCU when it has received the (CALCRC)
	 * message and the internal CRC calculation 
	 * equals the sent CRC message.
	 * 
	 * @param input serial data
	 * @return true if data[] equals 'CRCSND'
	 */		
	
	private boolean compareSuccessBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x53 &&  //S
					data[1] == 0x55 &&	//U	
					data[2] == 0x43 &&	//C
					data[3] == 0x43 &&	//C
					data[4] == 0x45 &&	//E
					data[5] == 0x53		//S
				  )
				{
					result = true;
				}
			} else {
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect"); 
			}			
		} catch(Exception e){
			System.err.println("Error occurred in DebugSerialBlocksGHMC::compareSuccessBlock(byte data[])");
			e.printStackTrace(System.err);
		}
		return result;
	}
	
	/**
	 * compare data received in the serial buffer
	 * return true if it matches with the comparison.
	 * The Success Block (FAILTA) is send by 
	 * the MCU when it has received the (CALCRC)
	 * message and the internal CRC calculation 
	 * does not equals the sent CRC message.
	 * 
	 * @param input serial data
	 * @return true if data[] equals 'CRCSND'
	 */		
	
	private boolean compareFailedBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == blockSize){
				if(
					data[0] == 0x46 &&	//F
					data[1] == 0x41 &&	//A
					data[2] == 0x49 &&	//I
					data[3] == 0x4C &&	//L
					data[4] == 0x54 &&	//T
					data[5] == 0x41 	//A
				  )
				{
					result = true;
				}				
			} else {
				throw new ArrayIndexOutOfBoundsException("Missmatch in the supplied data[], length is incorrect"); 				
			}		
		} catch(Exception e){
			System.err.println("Error occurred in DebugSerialBlocksGHMC::compareFailedBlock(byte data[])");
			e.printStackTrace(System.err);
		}
		return result;
	}
}
