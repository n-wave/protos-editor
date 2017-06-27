package helpers;

import java.util.Random;

import serialCommunication.SerialCommunication;

public class TestParser {
	ProtocolParser parser = new ProtocolParser();
	SerialCommunication serial; 
	Thread thread;
	String commPort;

	public TestParser(){
	//	serial = new SerialCommunication();
		thread = new Thread(serial, "Serial Communication Thread");
		thread.setDaemon(true);
	}

	/** Parse contents to console **/
	
	public void parsePreset(Preset preset){
		String byteArrayString = new String();
		Preset tmpPreset = new Preset("tmp");
		tmpPreset = preset;
		

		try{
		parser.parsePreset(tmpPreset);
	    byteArrayString = parser.toStringTestEight();
	    
	    System.out.println(byteArrayString);
		
		}catch(Exception e){
			System.err.println("Error occurred in TestParser testEight(Preset preset)");
			e.printStackTrace(System.err);
		}
	}
	
	/** 
	 *  Serial Communication Test Four 
	 *  Fill 128 Byte array with random 
	 *  values. Calculate and convert 
	 *  crc and write the byte array to 
	 *  the mcu in chunks of 32 Bytes.
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
			//result = serial.stateToString(state);
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

					//System.out.println("Current State = " + serial.stateToString(state));				
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
		//	result = serial.stateToString(state);
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
		//	result = serial.stateToString(state);
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
			//result = serial.stateToString(state);
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
