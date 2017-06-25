package helpers;

import java.util.ArrayList;

import dataContainers.DataContainer;
import dataContainers.DataStructure;


public class ProtocolParser {

	private ArrayList<Byte> byteArray = new ArrayList<Byte>();
	private ProtocolToString protocolToString = new ProtocolToString();
	private CompareProtocolBlocks compare = new CompareProtocolBlocks();
	
	private int crcTable[] = {
							 	0x00000000, 0x1db71064, 0x3b6e20c8, 0x26d930ac,
							 	0x76dc4190, 0x6b6b51f4, 0x4db26158, 0x5005713c,
							 	0xedb88320, 0xf00f9344, 0xd6d6a3e8, 0xcb61b38c,
							 	0x9b64c2b0, 0x86d3d2d4, 0xa00ae278, 0xbdbdf21c
							  };
	
	
	public ProtocolParser()
	{
		byteArray.clear();
	}
	
	public byte[] getByteArray(){
		//Byte byteData[] = byteArray.toArray(new Byte[byteArray.size()]);
		
		return null;
	}
	
	public void parsePreset(Preset preset){
		Preset tmpPreset = new Preset("tmp");
		int byteArraySize = 0;
		tmpPreset = preset;
		
		
		try{
			int length = tmpPreset.getLength();

			for(int i=0; i<length; i++){
				DataContainer data = tmpPreset.getDataContainer(i);
				DataStructure tmp[] = data.getActiveDataStructures();
					
				/** 
				 * First entry in dataStructure 
				 * Array belongs to the Scene 
				 **/
				
				parseSceneBlock(i); 
				
				for(int j=0; j<tmp.length; j++){
					int dataArray[] = tmp[j].getData();
					parseProtocol(dataArray);
							
				}
				
				System.out.println("Parsing Preset data, Scene : " + i);			
				parseEndBlock();			
			}
			byteArraySize = byteArray.size();
			//System.out.println("byteArray size :" + byteArraySize);
			
			
			this.parseCyclicRedundancyCheck();
			
			/** Total Number of Bytes should be 1984 at this point**/
			
			//byteArraySize = byteArray.size();
			//System.out.println("byteArray size with CRC :" + byteArraySize);
			
		} catch(Exception e){
			System.err.println("Error Ocurred in ProtocolParser void parse(DataContainer data");
			e.printStackTrace(System.err);
		}
		/**
		for(int i=0; i<byteArray.size(); i++){
			System.out.println("Index [" + i + "] : " + Byte.toUnsignedInt(byteArray.get(i)));
		}
		**/
		
	}
	
	public void parseProtocol(int data[]){
		int optionId = data[1];
		
		switch(optionId){
			case 0xE0:
				programChangeParser(data);
				break;
			case 0xE1:
				noteVelocityParser(data);
				break;
			case 0xE2:
				noteControlChangeParser(data);
				break;
			case 0xE3:
				pitchBendParser(data);
				break;
			case 0xE4:
				pitchBendNoteParser(data);
				break;
			case 0xE5:
				controlChangeParser(data);
				break;
			case 0xE6:
				controlChangeFadeParser(data);
				break;
			case 0xEA:
				sceneDataParser(data);
				/** 
				 * After the Scene the controller 
				 * Start Block is parsed and followed
				 * by the controller data.
				 */
				parseStartBlock(); 
				break;			
		}
	}
	
		
	public void programChangeParser(int data[]){
		int size = data.length;
		int index = 0;
		
		while(index < size)
		{
			byte tmp = (byte)(data[index]);
			byteArray.add(tmp);
			index++;
		}
		
		/**
		 *  Zero Padding 
		 */
		
		while(index < 16)
		{
			byteArray.add((byte) 0x00);
			index++;
		}
	}
	
	public void noteVelocityParser(int data[]){
		int size = data.length; 
		int index = 0;
		
		while(index < size){
			byte tmp = (byte)data[index];
			byteArray.add(tmp);
			index++;
		}
		
		while(index < 16){
			byteArray.add((byte)0x00);
			index++;
		}

	}
	
	public void noteControlChangeParser(int data[]){
		int resolution = data[6];
	
		if(resolution == 0){
			int size = data.length; 
			int index = 0;
			
			while(index < size){
				byte tmp = (byte)data[index];	
				byteArray.add(tmp);
				index++;
			}			
			
			while(index < 16){
				byteArray.add((byte) 0x00);
				index++;
			}
		} else {		
			for(int i=0; i < 8; i++){
				byte tmp = (byte)data[i];
				byteArray.add(tmp);
			}

			/** converted to 14 Bit 
			 *  2 Bytes storing 
			 *  7Bit nr each
			 *  
			 *  data supplied from gui
			 *  
			 * 	0xF0, //Start
			 *	0xE2, //ID
 			 *	4,	   //channel
 			 *	0,    //pitch
			 *	0,    //velocity
			 *	0,	   //link/static
			 *	1,	   //Resolution
			 *	0,	   //CC number
			 *  0,    //top value
			 *	0,	   //bottom value
			 *	0xFF  //end		
			 *  
			 */
			
			for(int i=8; i<10; i++){
				int value = data[i] << 1;
				byte msb = (byte)((value >> 8) & 0x7F);
				byte lsb = (byte)((value >> 1) & 0x7F);
				byteArray.add(msb);
				byteArray.add(lsb);
			}
					
			byteArray.add((byte)0xFF);
		
			for(int i=13; i< 16; i++){
				byteArray.add((byte)0x00);
			}
		
			
		}	
	}
	
	public void pitchBendParser(int data[]){
		int size = data.length; 
		int index = 0;
		
		while(index < size){
			byte tmp = (byte)data[index];
			byteArray.add(tmp);
			index++;
		}
		
		while(index < 16){
			byteArray.add((byte)0x00);
			index++;
		}
	}
	
	public void pitchBendNoteParser(int data[]){
		int size = data.length; 
		int index = 0;
		
		while(index < size){
			byte tmp = (byte)data[index];
			byteArray.add(tmp);
			index++;
		}
		
		while(index < 16){
			byteArray.add((byte)0x00);
			index++;
		}
	}
	
	public void controlChangeParser(int data[]){
		int resolution = data[3];
		
		if(resolution == 0){
			int index = 0;
			
			while(index < data.length){
				byte tmp = (byte)data[index];
				byteArray.add(tmp);
				index++;
			}
			
			
			while(index < 16){
				byteArray.add((byte)0x00);
				index++;
			}
			
		} else{	
			
			/** Parse first 'unaltered bytes 
			 *  into dynamic array
			 * 	After that pickup calculate 
			 *  values for 14Bit MIDI Control Change
			 *  upper address of CC number calculated in 
			 *  Hardware controller Add Hard Coded 
			 *  length and end bit 
			 *  
			   0xF0, //Start
			 	0xE5, //ID
			  	8,    //Channel
				1,    //Resolution
				0,   //CC nr
				0,   //Top Value
				0,   //Bottom Value
				0xFF  //end 
			 */
			
			
			for(int i=0; i<5;i++){
				byte tmp = (byte)data[i];
				byteArray.add(tmp);
			}
			
			for(int i=5; i<7; i++){
				int value = data[i] << 1;
				byte msb = (byte)((value >> 8) & 0x7F);
				byte lsb = (byte)((value >> 1) & 0x7F);
				byteArray.add(msb);
				byteArray.add(lsb);
			}
			

			byteArray.add((byte)0xFF); //end Bit
			
			for(int i=10; i<16; i++){
				byteArray.add((byte)0);
			}				
		}	
	}

	public void controlChangeFadeParser(int data[]){
		int resolution = data[3]; 
		
		if(resolution == 0){
			
			for(int i=0; i<8; i++)
			{
				byte tmp = (byte)data[i];
				byteArray.add(tmp);
			}
			
			for(int i=8; i<10; i++){
				int value = data[i];
				byte msb = (byte)((value >> 8) & 0xFF);
				byte lsb = (byte)(value & 0xFF);
				byteArray.add(msb);
				byteArray.add(lsb);
			}
			
			byteArray.add((byte)0xFF);
			
			for(int i=13; i<16; i++){
				byteArray.add((byte)0x00);
			}
			
		} else {
			
			for(int i = 0; i < 5; i++)
			{
				byte tmp = (byte)data[i];
				byteArray.add(tmp);
			}		
			for(int i=5; i<8; i++){
				int value = data[i] << 1;
				byte msb = (byte)((value >> 8) & 0x7F); 
				byte lsb = (byte)((value >> 1) & 0x7F);
				byteArray.add(msb);
				byteArray.add(lsb);
			}
			
			for(int i=8; i<10; i++){
				int value = data[i];
				byte msb = (byte)((value >> 8) & 0xFF);
				byte lsb = (byte)(value & 0xFF);
				byteArray.add(msb);
				byteArray.add(lsb);
			}
			byteArray.add((byte)0xFF);			
		}
	}
	
	public void sceneDataParser(int[] data){
		for(int i=0; i<data.length; i++){
			byte tmp = (byte)data[i];
			byteArray.add(tmp);
		}
	}
	
	public void parseCyclicRedundancyCheck(){
		byte crcBuffer[] = new byte[4];
		byte data[] = new byte[byteArray.size()];
		long crc = 0L;
		
		try{
			
			for(int i=0; i<byteArray.size(); i++){
				data[i] = byteArray.get(i);
			}
			
			crc = calculateCyclicRedundancyCheck(data);
			crcBuffer = convertToByteAray(crc);
			
			/** Parse CRC begin Block **/
			parseCyclicRedundancyCheckBeginBlock(); //8 Bytes
			
			for(int i=0; i<crcBuffer.length; i++){
				byteArray.add(crcBuffer[i]);		//4 Bytes
			}
			
			this.parseCyclicedundancyCheckEndBlock(); //8 Bytes
			
			for(int i=0; i<12; i++){
				byteArray.add((byte)0x00);
			}
			
			
			
		} catch(Exception e){
			System.out.println("Error ocurred in ProtocolParser parseCyclicRedundancyCheck(byte data[])");
			e.printStackTrace(System.err);
		}
	}
	
	@Override
	public String toString(){
		String byteArrayString = new String("ByteArray Values \n");
				
		for(byte value : byteArray){
			int byteValue = Byte.toUnsignedInt(value);
			byteArrayString +=  byteValue + "\n"; 	
		}
			
		return byteArrayString;
	}
	/**
	 * Converts ByteArray into hum readable format 
	 * With Algo to intercept start bytes and from there 
	 * on calculate array length locat end byte and 
	 * parse the values.
	 * 
	 */
	public String toStringTestTwo(){
		String byteArrayString = new String("ByteArray Values Test Two \n");
		
		try{
			if(!byteArray.isEmpty()){
				boolean run = false;
				int size = byteArray.size();
				int byteArrayIndex = 0;	
				byte[] buffer = new byte[16];
			
				byteArrayString += "Array Size: " + size + "\n" + "\n";
			
				int bufferIndex = 0; //reset BufferIndex
				
				//System.out.println("toStringTestTwo first while loop, testing for SCENE");
				
				while(bufferIndex < 8 && byteArrayIndex < size)
				{
					buffer[bufferIndex] = byteArray.get(byteArrayIndex);
					bufferIndex++; 
					byteArrayIndex++; 
				}
				
				/**
			 	* If sceneBlock is found determine if 
			 	* a Start Byte is available and get 
		     	* all data from the dataStructue 
			 	**/
			
				run = findSceneBlock(buffer);
			
				if(run){
					byteArrayString += "\n-------------------\n" + "Scene Block nr : " + buffer[7]  + "\n-------------------\n" ;				
				}
			
				while(run)
				{					
					bufferIndex = 0;
			
					/** Fill first Part of Buffer check 
				 	*  for specific Message blocks
				 	*
				 	*   These Block Are Six bytes in total
				 	*  
				 	*   ENDBLOCK	//EndBlock 
				 	*   CRCBGN //CRC begin
				 	*   CRCEND //CRC end  
				 	**/
					
					
					//System.out.println("toStringTestTwo Second While loop, testing for End block");
					while(bufferIndex < 8 && byteArrayIndex < size)
					{
						buffer[bufferIndex] = byteArray.get(byteArrayIndex);
						bufferIndex++; 
						byteArrayIndex++; 
					}
				
					if(findEndBlock(buffer)){
						byteArrayString += "-------------------\n     End Block";
						run = false;									
					} 
				
					while(bufferIndex < 16 && byteArrayIndex < size)
					{
						buffer[bufferIndex] = byteArray.get(byteArrayIndex);
						bufferIndex++; 
						byteArrayIndex++; 
					}
				
					/** 
				 	* Store values in String
				 	*/
				
					byteArrayString += protocolToString.getProtocolString(buffer) + "\n";			
				}										
			}		
		} catch(Exception e){
			System.err.println("Error ocurred in toStringTestTwo");
			e.printStackTrace(System.err);
		}
		return byteArrayString;
	}
	
//	public 
	
	public String toStringTestThree(){
		String byteArrayString = new String("ByteArray Values Test Three \n");
		
		try {
			boolean run = false;
			int size = byteArray.size();
			int byteArrayIndex = 0;	
			int bufferIndex = 0;
			byte[] buffer = new byte[16];
			
			byteArrayString += "Array Size: " + size + "\n";
			
			//System.out.println("toString Three while loop, testing for sceneBlock");
			while(bufferIndex < 8){
				buffer[bufferIndex] = byteArray.get(byteArrayIndex);
				bufferIndex++;
				byteArrayIndex++;
			}
			
			if(findSceneBlock(buffer)){
				byteArrayString += "\n-------------------\n" + "Scene Block nr : " + buffer[7]  + "\n-------------------\n\n" ;	
				bufferIndex = 0; 
				
				//System.out.println("toStringThree while loop 2 parsing scene data.");
				/** Load in Scene Data **/ 
				while(bufferIndex < 16){
					buffer[bufferIndex] = byteArray.get(byteArrayIndex);
					
					bufferIndex++;
					byteArrayIndex++;
				}
				/** Parse Scene Data to String **/
				byteArrayString += protocolToString.getProtocolString(buffer);			
				
				bufferIndex = 0;
				
				/** 
				 *  Load up next Block 
				 * 	This should be a 
				 *  Start Block  
				 */
				
				//System.out.println("toString Three while loop 3 testing for StartBlock");
				while(bufferIndex < 8){
					buffer[bufferIndex] = byteArray.get(byteArrayIndex);
					bufferIndex++;
					byteArrayIndex++;
				}
				
				if(findStartBlock(buffer)){
					run = true;
					byteArrayString += "\n-------------------\n" + "    Start Block " + "\n-------------------\n\n" ;	
				}
			}
			
			while(run){
				bufferIndex = 0;
				//System.out.println("toStringThree inner While loop testing for endBlock");
				while(bufferIndex < 8 && byteArrayIndex < size)
				{
					buffer[bufferIndex] = byteArray.get(byteArrayIndex);
					bufferIndex++; 
					byteArrayIndex++; 
				}
			
				if(findEndBlock(buffer)){
					byteArrayString += "-------------------\n     End Block" + "\n-------------------\n\n" ;
					run = false;									
				} 
			
				/** 
				 *  Bufferindex is 6 at this point 
				 *  Incremented in the next while loop
				 *  cause we didn't find the end Block
				 */
				
				while(bufferIndex < 16 && byteArrayIndex < size)
				{
					buffer[bufferIndex] = byteArray.get(byteArrayIndex);
					bufferIndex++; 
					byteArrayIndex++; 
				}
			
				/** 
			 	* Store values in String
			 	*/
			
				byteArrayString += protocolToString.getProtocolString(buffer) + "\n";		
			}
			
			
		} catch(Exception e){
			System.err.println("Error ocurred in toStringTestThree");
			e.printStackTrace(System.err);
		}
				
		return byteArrayString;
	}
	
	public String toStringTestFour(){
		String byteArrayString = new String("Test Four \n");
		byteArrayString += toStringTestThree();
		return byteArrayString;
	}
	
	public String toStringTestEight(){
		String byteArrayString = new String("toStringTest Eight \n");
		int nrOfScenes = 4;
		int byteArrayIndex = 0;
		int size = byteArray.size();
		
		byteArrayString += "byteArray size with CRC : " + size + "\n";

		return byteArrayString;
	}
	
	public void parseSceneBlock(int sceneNumber)
	{
		byteArray.add((byte)0x53); 		//S
		byteArray.add((byte)0x43); 		//C
		byteArray.add((byte)0x45);	    //E
		byteArray.add((byte)0x4E);		//N
		byteArray.add((byte)0x45); 		//E
		byteArray.add((byte)0x4E);		//N
		byteArray.add((byte)0x52);		//R
		byteArray.add((byte)sceneNumber);
	}
	
	public void parseStartBlock()
	{	
		byteArray.add((byte)0x53);	//S
		byteArray.add((byte)0x54);	//T
		byteArray.add((byte)0x52);	//R
		byteArray.add((byte)0x42);	//B
		byteArray.add((byte)0x4C);	//L
		byteArray.add((byte)0x4F);	//O	
		byteArray.add((byte)0x43);	//C
		byteArray.add((byte)0x4B);	//K
	}
	
	public void parseEndBlock()
	{
		byteArray.add((byte)0x45);	//E
		byteArray.add((byte)0x4E);	//N
		byteArray.add((byte)0x44);	//D
		byteArray.add((byte)0x42);	//B
		byteArray.add((byte)0x4C);	//L
		byteArray.add((byte)0x4F);	//O
		byteArray.add((byte)0x43);	//C
		byteArray.add((byte)0x4B);	//K	
	}
	
	public void parseCyclicRedundancyCheckBeginBlock()
	{
		byteArray.add((byte)0x43); //C
		byteArray.add((byte)0x52); //R
		byteArray.add((byte)0x43); //C
		byteArray.add((byte)0x42); //B
		byteArray.add((byte)0x47); //G
		byteArray.add((byte)0x4E); //N
		byteArray.add((byte)0x42); //B
		byteArray.add((byte)0x4C); //L
	}
	
	public void parseCyclicedundancyCheckEndBlock(){
		byteArray.add((byte)0x43);	//C
		byteArray.add((byte)0x52);	//R
		byteArray.add((byte)0x43);	//C
		byteArray.add((byte)0x45);	//E
		byteArray.add((byte)0x4E);	//N
		byteArray.add((byte)0x44);	//D
		byteArray.add((byte)0x42);  //B
		byteArray.add((byte)0x4C);  //L
	}
	
	public long calculateCyclicRedundancyCheck(byte[]data){
		long crc = ~0L;
		
		try{
			for(int i=0; i<data.length; ++i){
			 crc = crcTable[(int) ((crc ^ data[i]) & 0x0f)] ^ (crc >> 4);
	         crc = crcTable[(int) ((crc ^ (data[i] >> 4)) & 0x0f)] ^ (crc >> 4);
	    	 crc = ~crc;
			}	
		} catch (Exception e){
			System.err.println("Error ocurred in ProtocolParser public long calculateCyclicRedundancyCheck");
			e.printStackTrace(System.err);
		}
				
		return crc;	
	}
	
	/** 
	 *  Convert Long to 4 Bytes stored in 
	 *  an Array, Used to convert the CRC
	 *  value from long to 4 Bytes. Which 
	 *  in turn are uploaded to hardware.
	 *  
	 *  @author Mario Van Etten
	 *  @param long 
	 *  @return byte[]
	 *  
	 **/
	
	public byte[] convertToByteAray(long data)
	{
		byte byteArray[] = new byte[4];
	
		byteArray[0] = (byte)(data & 0xFF);
		byteArray[1] = (byte)((data >> 8) & 0xFF);
		byteArray[2] = (byte)((data >> 16) & 0xFF);
		byteArray[3] = (byte)((data >> 24) & 0xFF);
						
		return byteArray;
	}
	
	/**
	 * Convert ByteArray to a Long, used to 
	 * revert the byte array back to the 
	 * original crc value. The embedded MCU 
	 * will calculate the crc value internally 
	 * and compare it with the value stored 
	 * in the EEPROM memory.
	 * 
	 * @author Mario Van Etten
	 * @param byte[]
	 * @return long
	 * 
	 **/
	
	public long convertToLong(byte data[])
	{
		long longData= 0L;
		
		/** First & the values in the Array 
		 *  or the result will be invalid 
		 *  next or to enable bits based 
		 *  on the value stored in the byte
		 */
		
		if(data.length == 4){
			longData =(data[0] & 0xFF) | 
					  (data[1] & 0xFF) << 8 | 
					  (data[2] & 0xFF) << 16 | 
					  (data[3] & 0xFF) << 24;
		}
	
		return longData;
	}
	/**
	 *  Part of testing the protocol
	 *  Redundant in final version 
	 *  but needs to stay in for testing
	 *  purposes and parsing of the 
	 *  protocol. Thus said this 
	 *  pattern needs to be used when 
	 *  decoding the stream byte in
	 *  the embedded system
	 * 
	 **/

	/** 
	 * private boolean findSceneBlock(byte data[], byte sceneNumber)
	 *  
	 * @author Mario Van Etten 
	 * @param data
	 * @param sceneNumber
	 * @return boolean
	 * 
	 *
	 * If the data argument contains the 
	 * characters which define the SceneBlock
	 * The next 16 Bytes define the SceneData.
	 * The sceneNumber is needed to distinguish
	 * which Scene in the hardware needs to be 
	 * initiated.
	 * 
	 **/
	
	

	private boolean findSceneBlock(byte[] data){
		boolean match = false;
		
		try{
			if(
				data[0] == 0x53 &&	//S
				data[1] == 0x43 &&	//C
				data[2] == 0x45 && 	//E
				data[3] == 0x4E &&	//N	
				data[4] == 0x45 && 	//E
				data[5] == 0x4E &&	//N
				data[6] == 0x52		//R
				)
			{
				match = true;
			} 
		} catch(Exception e){
			System.err.println("Error ocurred in findSceneBlock");
			e.printStackTrace(System.err);
		}
		return match;
	}

	
	/** private boolean findStartBlock(byte data[])
	 * 
	 * @param data
	 * @return boolean 
	 * 
	 * If the data argument contains the 
	 * characters which define the Start Block
	 * The main loop will be entered and the 
	 * parsing of the DataStructures will 
	 * commence.
	 * 
	 **/
	


	private boolean findStartBlock(byte data[]){
		boolean match = false; 
		
		try{
			if(
				data[0] == 0x53 &&	//S
				data[1] == 0x54 &&	//T
				data[2] == 0x52 &&	//R
				data[3] == 0x42 &&	//B
				data[4] == 0x4C &&	//L	 
				data[5] == 0x4F	&&	//O
				data[6] == 0x43 &&	//C
				data[7] == 0x4B		//K
				)
			{
				match = true;
			}
		} catch(Exception e){
			System.err.println("Error ocurred in findStartBlock");
			e.printStackTrace(System.err);
		}
		
		return match;
	}
	
	
	/** private boolean findEndBlock(byte data[])
	 * 
	 * @param data
	 * @return boolean
	 * 
	 * If the data argument contains the 
	 * character which defines the End Block. 
	 * The total Amount of data for a specific 
	 * Scene has been parsed.
	 * 
	 */
	
	private boolean findEndBlock(byte data[]){
		boolean match = false;
		
		try{	
			if(
				data[0] == 0x45 && //E
				data[1] == 0x4E && //N
				data[2] == 0x44 && //D
				data[3] == 0x42 && //B
				data[4] == 0x4C && //L 
				data[5] == 0x4F && //O
				data[6] == 0x43 && //C
				data[7] == 0x4B    //K
				)
			{
				match = true;
			} 
		} catch(Exception e) {
			System.err.println("Error ocurred in findEndBlock");
			e.printStackTrace(System.err);
		}
		return match;
	}
}
