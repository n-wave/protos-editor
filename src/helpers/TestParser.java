package helpers;

import java.util.Random;

import dataContainers.DataContainer;
import dataContainers.DataStructure;
import serialCommunication.SerialCommunication;

public class TestParser {
	ProtocolParser parser = new ProtocolParser();
	SerialCommunication serial; 
	Thread thread;
	String commPort;
	
	int programChangeData[] = {
								0xF0, //0 start
								0xE0, //1 ID
								1,	  //2 channel
								0, 	  //3 bank
								0,    //4 program
								0xFF  //5 end
							  };
	
	int noteVelocityData[] = {
			  					0xF0, //0 Start
			  					0xE1, //1 ID
			  					2,    //2 Channel
			  					0,    //3 Pitch
			  					0,    //4 Velocity
			  					0,    //5 Link/Static
			  					0xFF  //6 end 
							 };
	
	int noteControlChangeData8Bit[] = {
									0xF0, //0 Start
									0xE2, //1 ID
									3,	  //2 channel
									0,    //3 pitch
									0,	  //4 velocity
									0,	  //5 link/static
									0,	  //6 Resolution
									0,	  //7 CC Number
									0,    //8 top value
									0,	  //9 bottom value
									0xFF  //10 end
								  };
	
	int noteControlChangeData16Bit[] = {
										 0xF0, //0 Start
										 0xE2, //1 ID
										 4,	   //2 channel
										 0,    //3 pitch
										 0,    //4 velocity
										 0,	   //5 link/static
										 1,	   //6 Resolution
										 0,	   //7 CC Number
										 0,    //8 top value
										 0,	   //9 bottom value
										 0xFF  //10 end									   	 
								       };	
	
	int pitchBendData[] = {
							0xF0, //1 Start
							0xE3, //2 ID
							5,    //3 Channel
							0xFF  //4 End
						  };
	
	int pitchBendNoteData[] = {
								0xF0, //0 Start
								0xE4, //1 ID
								6,	  //2 Channel
								0,    //3 Pitch
								0, 	  //4 velocity
								0xFF  //5 End
							  };
	
	int controlChangeData8Bit[] = {
									 0xF0, //0 Start
									 0xE5, //1 ID
									 7,    //2 Channel
									 0,    //3 Resolution
									 12,   //4 CC Number
									 80,   //5 Top Value
									 100,  //6 Bottom Value
									 0xFF  //7 end 
								   };
	
	int controlChangeData16Bit[] = {
									 0xF0, 	//0 Start
									 0xE5, 	//1 ID
									 7,    	//2 Channel
									 1,    	//3 Resolution
									 12,   	//4 CC Number
									 16383,	//5 Top Value
									 16383,	//6 Bottom Value
									 0xFF  	//7 end 
									};

	int controlChangeFadeData8Bit[] = {
										0xF0,   //0 Start
										0xE6,   //1 ID
										9,  	//3 channel
										0, 		//4 resolution
										0,    	//5 CC Number
										0,    	//6 Start
										0,  	//7 hold
										0,  	//8 end
										0,  	//9 fade in
										0,  	//10 fade out
										0xFF	//11 end
									  };

	int controlChangeFadeData16Bit[] = {
										 0xF0,  //0 Start
										 0xE6,  //1 ID
										 10, 	//3 channel
										 1,     //4 Resolution
										 14,	//5 Control Change Number
										 65535, //6 Start
										 0, 	//7 hold
										 0,     //8 end
										 65535, //9 fade in
										 65535, //10 fade out
										 0xFF	//11 end
									   };
	
	int sceneData[] = {
						0xF0,	//0 Start Byte
						0xEA,	//1 Option ID
						0x04,	//2 Option 
						0x01,	//3 Channel 1
						0x01,	//4 Bank    1
						0x01,	//5 Program 1
						0x02,	//6 Channel 2
						0x02,	//7 Bank    2
						0x02,	//8 Program 2
						0x03,	//9 Channel 3
						0x03,	//10 Bank 	  3
						0x03,	//11 Program 3
						0x04,	//12 Channel 4
						0x04,	//13 Bank 	  4
						0x04,	//14 Program 4
						0xFF	//15 End Byte
					  };
		
	int testDataOne[] = {
						  0xF0, 0xEA, 0x04, 0x01,	
						  0x01,	0x01, 0x02,	0x02,	
						  0x02, 0x03, 0x13,	0x03,
						  0x04,	0x88, 0x04,	0xFF,	
						  0xF0, 0xE5, 0x57, 0x40,    
						  0x12, 0x80, 0xB1, 0xFF,  
						  0xF0, 0xE6, 0xE1, 0xE4, 		
						  0xE9, 0xFF, 0x07, 0x10,  	
						};
	
	int testDataTwo[] = {
						  0xF0, 0xEA, 0x04, 0x01,	
						  0x01,	0x51, 0x02,	0x02,	
						  0x02, 0x03, 0x63,	0x03,
						  0x04,	0x88, 0x04,	0xFF,
						  0xF0, 0xEA, 0x24, 0x11,	
						  0x01,	0x51, 0x02,	0x02,	
						  0x02, 0x03, 0x13,	0x03,
						  0x04,	0x88, 0x04,	0xFF,
						  0xF0, 0xE5, 0x76, 0x40,    
						  0x12, 0x80, 0xB1, 0xFF,  
						  0x98, 0xE6, 0x71, 0xE4, 		
						  0xE9  	
						};

	int SerialCommunicationTestTwo [] = {
										0xF0, 0xEA, 0x04, 0xA1, 
										0x11, 0xFF, 0xC2, 0x02, 
										0x25, 0x43, 0x13, 0x03,
										0x04, 0xB4, 0x14, 0x88, 
										0xF0, 0xE5, 0x57, 0x49,    
										0x12, 0x80, 0xB1, 0x9F,  
										0x80, 0x50, 0x28, 0x4E,     
										0xAA, 0xF0, 0x87, 0x51 
            						   };

	public TestParser(){
		serial = new SerialCommunication();
		thread = new Thread(serial, "Serial Communication Thread");
		thread.setDaemon(true);
	}
	
	public void testOne(){
		String byteArrayString = new String();
		

		//parser.controlChangeParser(controlChangeData8Bit);

		byteArrayString = parser.toString();
		
		System.out.println(byteArrayString);
	}
	
	public void testTwo(){
		String byteArrayString = new String();
		
		parser.parseSceneBlock(0);

		parser.programChangeParser(programChangeData);
		parser.noteVelocityParser(noteVelocityData);
		parser.noteControlChangeParser(noteControlChangeData8Bit);
		parser.noteControlChangeParser(noteControlChangeData16Bit);	
		parser.pitchBendParser(pitchBendData);
		parser.pitchBendNoteParser(pitchBendNoteData);		 
		parser.controlChangeParser(controlChangeData8Bit);
		parser.controlChangeParser(controlChangeData16Bit);
		parser.controlChangeFadeParser(controlChangeFadeData8Bit);
		parser.controlChangeFadeParser(controlChangeFadeData16Bit);
			
		parser.parseEndBlock();
		byteArrayString = parser.toStringTestTwo();
		System.out.println(byteArrayString);

	}

	public void testThree(){
		String byteArrayString = new String();
		
		parser.parseSceneBlock(0);
		parser.sceneDataParser(sceneData);
		parser.parseStartBlock();
		
		parser.programChangeParser(programChangeData);
		parser.noteVelocityParser(noteVelocityData);
		parser.noteControlChangeParser(noteControlChangeData8Bit);
		parser.noteControlChangeParser(noteControlChangeData16Bit);	
		parser.pitchBendParser(pitchBendData);
		parser.pitchBendNoteParser(pitchBendNoteData);		 
		parser.controlChangeParser(controlChangeData8Bit);
		parser.controlChangeParser(controlChangeData16Bit);
		parser.controlChangeFadeParser(controlChangeFadeData8Bit);
		parser.controlChangeFadeParser(controlChangeFadeData16Bit);
				
		parser.parseEndBlock();		
		
		byteArrayString = parser.toStringTestThree();
		System.out.println(byteArrayString);
	}
	
	public void testFour(){
		String byteArrayString = new String();
		
		parser.parseSceneBlock(0);
		parser.sceneDataParser(sceneData);
		parser.parseStartBlock();
		
		parser.parseProtocol(programChangeData);
		parser.parseProtocol(noteVelocityData);
		parser.parseProtocol(noteControlChangeData8Bit);
		parser.parseProtocol(noteControlChangeData16Bit);
		parser.parseProtocol(pitchBendData);
		parser.parseProtocol(pitchBendNoteData);
		parser.parseProtocol(controlChangeData8Bit);
		parser.parseProtocol(controlChangeData16Bit);
		parser.parseProtocol(controlChangeFadeData8Bit);
		parser.parseProtocol(controlChangeData16Bit);
		parser.parseProtocol(sceneData);
		
		parser.parseEndBlock();	
		
		byteArrayString = parser.toStringTestFour();
		System.out.println(byteArrayString);
		
	}
	
	public void testFive(){
		String crcValue;

		int crc = 0;
		long crcLong = 0;
		
		byte data[] = new byte[sceneData.length];

		
		for(int i=0; i<sceneData.length; i++){
			data[i] = (byte)(sceneData[i]);
		}
	
		crcLong = parser.calculateCyclicRedundancyCheck(data);
		crcValue = Long.toUnsignedString(crcLong);
		//crcValue = Long.
		
		System.out.println("calculated int crc : " + crc);
		System.out.println("calculated long crc : " + crcLong);
		System.out.println("calculated String crc : " + crcValue);
	}
	
	public void testSix(){
		byte byteTestDataOne[] = new byte[testDataOne.length];
		byte byteTestDataTwo[] = new byte[testDataTwo.length]; 
		long crcTestDataOne;
		long crcTestDataTwo;
		
		for(int i=0; i<testDataOne.length; i++){
			byteTestDataOne[i] = (byte)testDataOne[i];
		}
		
		for(int i=0; i<testDataTwo.length; i++){
			byteTestDataTwo[i] = (byte)testDataTwo[i];
		}
		
		crcTestDataOne = parser.calculateCyclicRedundancyCheck(byteTestDataOne);
		crcTestDataTwo = parser.calculateCyclicRedundancyCheck(byteTestDataTwo);
		
		System.out.println(
						   "Calculated CRC value from testDataOne[] \n" + 
						   "size Array : " +  testDataOne.length + "\n" + 
						   "CRC = " + crcTestDataOne + "\n"
						   );
		
		System.out.println(
							"Calculated CRC value from testDataTwo[] \n" + 
							"size Array : " +  testDataTwo.length + "\n" + 
							"CRC = " + crcTestDataTwo + "\n"
							);
	}
	
	public void testSeven(){
		/** Previous Values calculated in testSix **/
		long crcValueOne = 1162385503;
		long crcValueTwo = -1990522158;
		long crcRevertOne = 0L;
		long crcRevertTwo = 0L;
		boolean resultOne = false;
		boolean resultTwo = false;
		
		
		byte crcBytesOne[] = new byte[4];
		byte crcBytesTwo[] = new byte[4];
		
		crcBytesOne = parser.convertToByteAray(crcValueOne);
		crcBytesTwo = parser.convertToByteAray(crcValueTwo);
		
		/** Convert Values back and compare with original **/
		crcRevertOne = parser.convertToLong(crcBytesOne);
		crcRevertTwo = parser.convertToLong(crcBytesTwo);
		
		/** Print Out value one **/
		System.out.println("Calculated CRC byte array one");
		System.out.println("Original Value : " + crcValueOne);
		for(int i=0; i<crcBytesOne.length; i++){
			System.out.println("byte " + i + " " + crcBytesOne[i]);
		}
		System.out.println("Reverted CRC value one");
		System.out.println("Value = " + crcRevertOne );
		
		/** Compare crc values and store if zero is return values are equal**/
		resultOne = (Long.compare(crcValueOne, crcRevertOne) == 0) ? true : false;
		System.out.println("Equal Values : " + resultOne + "\n");
		
		
		/** Print Out value one **/
		System.out.println("Calculated CRC byte array two");
		System.out.println("Original Value : " + crcValueTwo);
		for(int i=0; i<crcBytesTwo.length; i++){
			System.out.println("byte " + i + " " + crcBytesTwo[i]);
		}
		System.out.println("Reverted CRC value Two");
		System.out.println("Value = " + crcRevertTwo);
		
		/** Compare crc values and store if zero is return values are equal**/
		resultTwo = (Long.compare(crcValueTwo, crcRevertTwo) == 0) ? true : false;
		System.out.println("Equal Values : " + resultTwo  + "\n");		
	}
	
	/** Test Eight 
	 * 
	 * Test Parsing Preset with 
	 * the complete contents of
	 * the total pogram. Print 
	 * out the contents to 
	 * console.
	 * 
	 **/ 
	
	
	public void testEight(Preset preset){
		String byteArrayString = new String();
		Preset tmpPreset = new Preset("tmp");
		tmpPreset = preset;
		
		DataContainer data = tmpPreset.getDataContainer(0);
		DataStructure structure[] = data.getActiveDataStructures();
		
		

		try{
	
		parser.parsePreset(tmpPreset);
	    byteArrayString = parser.toStringTestEight();
	    
	    System.out.println(byteArrayString);
		
		}catch(Exception e){
			System.err.println("Error Ocurred in TestParser testEight(Preset preset)");
			e.printStackTrace(System.err);
		}
		
		
		
		//parser.parsePreset(preset);
		
	}

	public void testParserFullPreset(Preset preset){
		String byteArrayString = new String();
		int length = preset.getLength();
		parser.parsePreset(preset);
		byteArrayString = parser.toStringTestEight();
		
		System.out.println(byteArrayString);
		
	}
	
	
	
	public void serialCommunicationTestOne(){
		try{
			int state = 0;
			String result = new String();
			byte emptyBuffer[] = new byte[32];
			byte emptyCRCBuffer[] = new byte[4];
			
			
			serial.openPort(commPort);
			
			
			if(!thread.isAlive()){
			  thread.start();
			}
			
			serial.enablePolling();
		
			/** 
			 * First step send STARTC should 
			 * be Answered by ACKNOW
			 **/
			
			serial.sendStartCommunicationBlock(); //Answer = ACKNOW
			//Thread.sleep(100); // Needed to give the MCU time to process the information
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Second step arbitrary array with 
			 * 32 bytes. Should be Answered with 
			 * CONTSC
			 **/
			
			serial.writeDataToCommunicationPort(emptyBuffer); //Answer = CONTSC
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/**
			 * Third step send CRCBGN should 
			 * be answered with CRCSND
			 **/
		
			serial.sendCyclicRedundancyBeginBlock(); //Answer = CRCSND
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Fourth step send arbitrary array
			 * with 4 b bytes. Should be answered 
			 * with 
			 **/

			serial.writeDataToCommunicationPort(emptyCRCBuffer);
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Fifth step send CRCEND should
			 * be answered with CLOSEC.
			 */
			
			serial.sendCyclicRedundancyEndBlock();
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			serial.disablePolling();
			Thread.sleep(100);
			serial.closePort(commPort);
			
		} catch(Exception e){
			e.printStackTrace(System.err);
		}
		
	}
	
	/** Passed Tested with Teensy SerialCommunicationTest-v0.2 **/
	
	public void serialCommunicationTestTwo(){
		try{
			String result = new String();
			int state = 0;
			long crc = 0L;						 //Calculated crc	
			byte dataBuffer[] = new byte[32];	 //Hold data destined for "EEPROM"
			byte crcBuffer[] = new byte[4]; 	 //Holds chopped up crc data

			
			/** store int Buffer as bytes **/
			
			for(int i=0; i<SerialCommunicationTestTwo.length; i++){
				dataBuffer[i] = (byte)(SerialCommunicationTestTwo[i] & 0xFF);
			}
			
			/** Calculate crc **/
			crc = parser.calculateCyclicRedundancyCheck(dataBuffer);
			
			System.out.println("Calculated crc: " + crc);
			
			/** Convert crc to four byte Array **/
			
			crcBuffer = parser.convertToByteAray(crc);
			
			for(int i=0; i<crcBuffer.length; i++){
				System.out.println("byte: " + i + " : " + crcBuffer[i]);
			}
			
			/** All data is in order start opening COMM port */
			
			serial.openPort(commPort);
			
			
			if(!thread.isAlive()){
			  thread.start();
			}
			
			serial.enablePolling();
		
			/** 
			 * First step send STARTC should 
			 * be Answered by ACKNOW
			 **/
			
			serial.sendStartCommunicationBlock(); //Answer = ACKNOW
			Thread.sleep(100); // Needed to give the MCU time to process the information
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Second step data Array with 
			 * 32 bytes. Should be Answered with 
			 * CONTSC
			 **/
			
			serial.writeDataToCommunicationPort(dataBuffer); //Answer = CONTSC
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);

			/**
			 * Third step send CRCBGN should 
			 * be answered with CRCSND
			 **/
		
			serial.sendCyclicRedundancyBeginBlock(); //Answer = CRCSND
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Fourth step send crc Buffer
			 * with 4 b bytes. Should be answered 
			 * with 
			 **/

			serial.writeDataToCommunicationPort(crcBuffer);
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Fifth step send CRCEND should
			 * be answered with CLOSEC.
			 */
			
			serial.sendCyclicRedundancyEndBlock();
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			serial.disablePolling();
			serial.closePort(commPort);
			thread.interrupt();
		}catch(Exception e){
			System.err.println("Error ocurred in TestParser serialCommunicationTestTwo()");
			e.printStackTrace(System.err);
		}
	}
	
	/*
	 *  * SerialCommunication-v0.3
	 * 
	 *  Load up random values in a byteArray 
	 *  calculate and convert crc locally 
	 *  upload and verify.
	 *  
	 **/
	
	public void serialCommunicationTestThree(){
		try{
			String result = new String();
			Random random = new Random();
			int state = 0;
			long crc = 0L;						 //Calculated crc	
			byte dataBuffer[] = new byte[32];	 //Hold data destined for "EEPROM"
			byte crcBuffer[] = new byte[4]; 	 //Holds chopped up crc data
			
			String serialPorts[] = serial.getPorts();
			
			if(serialPorts.length > 0){
				commPort = serialPorts[0];
				System.out.println("COMM port : " + commPort + "\n");
			}
			
			
			/** Fill Array with Random data **/ 
		
			random.nextBytes(dataBuffer);
			
			/** 
			 *  Calculate crc over the array with random Data 
			 *  and print out the result
			 **/
			System.out.println("Execution SerialCommunicationTestThree send Random Values");
			System.out.println("\n-----------------------------------------------\n");
			
			System.out.println("Random Values");
			crc = parser.calculateCyclicRedundancyCheck(dataBuffer);
			Thread.sleep(100);
			System.out.println("Calculated crc: " + crc);
			
			/** Convert crc to four byte Array **/
			
			crcBuffer = parser.convertToByteAray(crc);
			
			/** print out all the values **/
			
			for(int i=0; i<crcBuffer.length; i++){
				Thread.sleep(500);
				System.out.println("byte[" + i + "] = " + crcBuffer[i]);			
			}
			Thread.sleep(100);
			System.out.println("\n-----------------------------------------------\n");
			
			serial.openPort(commPort);
			
			
			if(!thread.isAlive()){
			  thread.start();
			}
			
			serial.enablePolling();
		
			/** 
			 * First step send STARTC should 
			 * be Answered by ACKNOW
			 **/
			
			serial.sendStartCommunicationBlock(); //Answer = ACKNOW
			Thread.sleep(100); // Needed to give the MCU time to process the information
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Second step data Array with 
			 * 32 bytes. Should be Answered with 
			 * CONTSC
			 **/
			System.out.println("---------------------");
			Thread.sleep(250);
			System.out.println("Sending Random Values");
			Thread.sleep(250);
			System.out.println("---------------------");
			Thread.sleep(250);
			
			serial.writeDataToCommunicationPort(dataBuffer); //Answer = CONTSC
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			Thread.sleep(250);
			System.out.println("---------------------------------------------");
			/**
			 * Third step send CRCBGN should 
			 * be answered with CRCSND
			 **/
		
			serial.sendCyclicRedundancyBeginBlock(); //Answer = CRCSND
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Fourth step send crc Buffer
			 * with 4 b bytes. Should be answered 
			 * with 
			 **/

			serial.writeDataToCommunicationPort(crcBuffer);
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Fifth step send CRCEND should
			 * be answered with CLOSEC.
			 */
			
			serial.sendCyclicRedundancyEndBlock();
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			serial.disablePolling();
			serial.closePort(commPort);
			thread.interrupt();
			
		} catch(Exception e){
			System.err.println("Error ocurred in TestParser serialCommunicationTestThree()");
			e.printStackTrace(System.err);
		}
	}
	
	/** Serial Communication Test Four 
	 *  Fill 128 Byte array with random 
	 *  values. Calculate and convert 
	 *  crc and write the byte array to 
	 *  the mcu in chunks of 32 Bytes.
	 * 
	 * 
	 */
	
	public void serialCommunicationTestFour(){
		try{
			String result = new String();
			Random random = new Random();
			int state = 0;
			long crc = 0L;						  //Calculated crc	
			byte dataBuffer[] = new byte[1024];	  //Hold data destined for "EEPROM"
			byte transferBuffer[] = new byte[32]; //Copy data from transfer buffer and wait for CONTSC 
			byte crcBuffer[] = new byte[4]; 	  //Holds chopped up crc data
			
			System.out.println("Execution SerialCommunicationTestFour send 128 Random Values");
		
			/** fill dataBuffer[128] **/
		
			random.nextBytes(dataBuffer);
		
			/** calculate crc **/
			crc = parser.calculateCyclicRedundancyCheck(dataBuffer);
			crcBuffer = parser.convertToByteAray(crc);
			
			Thread.sleep(100);
			System.out.println("Converted crc"); 
			Thread.sleep(250);
			
			for(int i=0; i<crcBuffer.length; i++){
				Thread.sleep(250);
				System.out.println("byte[" + i + "] = " + crcBuffer[i]);
			}
			Thread.sleep(250);
			System.out.println("Calculate CRC = " + crc);
			
			System.out.println("\n-----------------------\n");
			
			
			/** get Commports **/ 
			
			String serialPorts[] = serial.getPorts();
			
			if(serialPorts.length > 0){
				commPort = serialPorts[0];
				System.out.println("COMM port : " + commPort + "\n");
			}
			
			serial.openPort(commPort);
			
			
			if(!thread.isAlive()){
			  thread.start();
			}
			
			serial.enablePolling();
			/** 
			 * First step send STARTC should 
			 * be Answered by ACKNOW
			 **/
			
			serial.sendStartCommunicationBlock(); //Answer = ACKNOW
			Thread.sleep(100); // Needed to give the MCU time to process the information
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Second step data Array with 
			 * 32 bytes. Should be Answered with 
			 * CONTSC
			 **/
			System.out.println("---------------------");
			Thread.sleep(250);
			System.out.println("Sending : " + dataBuffer.length + "bytes");
			Thread.sleep(250);
			System.out.println("---------------------");
			Thread.sleep(250);
			
			
			
			if(state == 1) //Acknowledgement has been received proceed with sending. 
			{
				int dataIndex = 0;
				int nrOfUploads = (dataBuffer.length/32)-1; // (256/32)-1 = 7
				boolean run = true;
				
				/**
				 * Do at least once, after the first 32 Bytes 
				 * The MCU will start Sending Continue Serial
				 * Communication Blocks (CONTSC)
				 **/
				
				do{
					System.out.println("Testing do While Loop");
						
					
					if(nrOfUploads == 0 || dataIndex >= dataBuffer.length){
						run = false;
					}
					for(int i=0; i<transferBuffer.length; i++){
						transferBuffer[i] = dataBuffer[dataIndex];
						dataIndex++;
					}
					
					Thread.sleep(250);
					state = serial.getState();
					System.out.println("Sending 32 bytes");
					
					if(state == 2 || state == 1){
						serial.writeDataToCommunicationPort(transferBuffer);
					}
					
					Thread.sleep(150);

					System.out.println("Current State = " + serial.stateToString(state));				
					System.out.println("DataIndex = " + dataIndex);
					System.out.println("nrOfUploads = " + nrOfUploads);
					
					Thread.sleep(500);
					nrOfUploads--;
				} while(run); //Send Once and then wait for Continue Block (CONTSC)
					
			}
			
			/**
			 * Third step send CRCBGN should 
			 * be answered with CRCSND
			 **/
		
			Thread.sleep(250);
			System.out.println("Sending CRC begin block");
			Thread.sleep(250);
			
			serial.sendCyclicRedundancyBeginBlock(); //Answer = CRCSND
			Thread.sleep(250);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Fourth step send crc Buffer
			 * with 4 b bytes. Should be answered 
			 * with 
			 **/

			Thread.sleep(250);
			System.out.println("Sending CRC Bytes");
			Thread.sleep(250);
			
			
			serial.writeDataToCommunicationPort(crcBuffer);
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			/** 
			 * Fifth step send CRCEND should
			 * be answered with CLOSEC.
			 */
			
			Thread.sleep(250);
			System.out.println("Sending CRC End block");
			Thread.sleep(250);
			
			
			serial.sendCyclicRedundancyEndBlock();
			Thread.sleep(100);
			state = serial.getState();
			result = serial.stateToString(state);
			System.out.println(result);
			
			serial.disablePolling();
			serial.closePort(commPort);
			thread.interrupt();
		
			
		} catch(Exception e){
			System.err.println("Error ocurred in TestParser serialCommunicationTestFour()");
			e.printStackTrace(System.err);
		}	
	}
}
