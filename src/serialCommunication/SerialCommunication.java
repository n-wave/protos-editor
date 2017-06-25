package serialCommunication;
import com.fazecast.jSerialComm.*;

/** Make Runnable so its works on a 
 *  on a separate thread
 * 
 * @author mario
 *
 */

public class SerialCommunication implements Runnable { 
	SerialPort commPort = null;
	
	private boolean run = false;
	private int state = -1;
	
	private byte[] readBuffer =  new byte[32];
	private byte[] dataBuffer = new byte[6];
	
	/** Start Communication Blockto be send to the hardware **/

	private final byte[] startc = {
									0x53, 	//S
									0x54, 	//T
									0x41,	//A
									0x52,	//R
									0x54,	//T
									0x43	//C
								  };
	
	private final byte[]crcbgn = {
								    0x43, 	//C
								    0x52,	//R
								    0x43,	//C
								    0x42,	//B
								    0x47,	//G
								    0x4E	//N
								   };
	
	private final byte[] crcend = {
									0x43,	//C
									0x52,	//R
									0x43,	//C
									0x45,	//E
									0x4E,	//N	
									0x44	//D
								  };
	
	public SerialCommunication(){
		
	}
	
	public void enablePolling(){
		run = true;
	}
	
	public void disablePolling(){
		run = false;
	}
	
	/**
	 * Returns the state of the, the 
	 * state is dependent on the messages
	 * sent to the COMM Port which originate 
	 * from the hardware device. Based on the 
	 * state of the object appropiate actions
	 * can be taken. For example send data 
	 * or but the system on wait for another 
	 * message
	 * 
	 * 
	 * @author Mario van Etten
	 * @date   	22-01-2017 
	 * @state returns state of the object.
	 * the state changes based on the serial
	 * messages received on the commport
	 * 
	 */
	
	public int getState(){
		return state;
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
				while(run){     
					  /** The commPort is leaded with the option to block 
					   *  calling readBytes if there are no bytes in the 
					   *  serial buffer.
					   */
				      int numRead = commPort.readBytes(readBuffer, readBuffer.length);
				      //System.out.println("Read " + numRead + " bytes.");
				
				      if(numRead == 6){
				    	  System.arraycopy(readBuffer, 0, dataBuffer, 0, dataBuffer.length);
				    	  state = findMessageBlocks(dataBuffer);
				      }				      
				}
		} catch(Exception e){
			System.err.println("Error ocurred while polling the Serial Port");
			e.printStackTrace(System.err);
		}	
	}
	
	public String[] getPorts(){
		String tmpPorts[] = null;
		
		try{
		    SerialPort[] port = SerialPort.getCommPorts();
			tmpPorts = new String[port.length];
		    
			for(int i=0; i<port.length; i++){
				tmpPorts[i] = port[i].getSystemPortName();
			}
			
			
		} catch(Exception e){
			System.err.println("Error Ocurred in SerialCommunication String[] getPorts()");
			e.printStackTrace(System.err);
		}
		
		return tmpPorts;
	}
	
	public String openPort(String name){
		String result = new String();
		
		try{
			commPort = SerialPort.getCommPort(name);
			
			if(!commPort.isOpen()){
				if(commPort.openPort()){
					commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
					result = "COMM port : " + name + " opened \n";
					
				} else {
					result = "Could not open COMM port : \n" + name;
				}
		
			} else {
				result = "COMM Port : " + name + " is already open";
			}
			
		} catch(Exception e){
			System.err.println("Error Ocurred in SerialCommunication openPort(String name)");
			e.printStackTrace(System.err);
		}
		
		return result;
	}
	
	public String closePort(String name){
		String result = new String();
		
		try{	
			if(commPort != null){
				if(commPort.isOpen()){		
					commPort.closePort();
					result = "Missmatch in supplied COMM port name \nClosed COMM port : \n";
				} else {
				result = "No COMM port in use \n";
				}
			}
		} catch(Exception e){
			System.err.println("Error Ocured in SerialCommunication closePort(String name)");
			e.printStackTrace(System.err);
		}	
		
	
		
		return result;
	}
	
		
	/**
	 * General Function for Writing data to the 
	 * communication port. 
	 * 
	 * @param serial data[] buffer to be written to
	 * the opened COMM Port
	 */
	
	public void writeDataToCommunicationPort(byte data[]){
		try{
			if(commPort.isOpen()){
				long length = (long)data.length;
				commPort.writeBytes(data, length);	
			} else {
				System.err.println("Error Ocurred in SerialCommunication writeDataToCommunicationPort(byte data[])");
				System.err.println("Commport is not available, nothing has been send");
			}		
		}catch(Exception e){
			System.err.println("Error Ocurred in SerialCommunication writeSerialToCommPort");
			e.printStackTrace(System.err);
		}
	}
	
	/** Messages destined to be send to the Hardware **/
	
	/** 
	 * Send the Start Communication Block as soon 
	 * as the acknowledgement block has been 
	 * received start sendinging data 
	 * 
	 */
	
	public void sendStartCommunicationBlock(){
		writeDataToCommunicationPort(startc);
	}
	
	public void sendCyclicRedundancyEndBlock(){
		writeDataToCommunicationPort(crcend);
	}
	
	public void sendCyclicRedundancyBeginBlock(){
		writeDataToCommunicationPort(crcbgn);
	}
	/** Messages to be received **/
	
	/** State Machine If a Block has been found 
	 * 	take appropriate action and return to
	 *  the caller skipping the rest of the 
	 *  functions.
	 * 
	 * @param byte[] buffer with 6 bytes only 
	 * used for comparing the bytes against 
	 * the messages in the find Functions
	 */
	 
	
	private int findMessageBlocks(byte data[]){	
		if(findAcknowledgementBlock(data)){
			return 1;
		}	
		
		if(findContinueBlock(data)){
			return 2;
		}
		
		if(findCyclicRedundancyCheckSendBlock(data)){
			return 3;
		}
      
		if(findSuccessBlock(data)){
			return 4;
		}
		
		if(findCloseCommunicationBlock(data)){
			return 5;
		}
			
		if(findFailedBlock(data)){
			return -1;
		}
		
		return 0;
	}
	
	/** convert state to a String for 
	 * 	clarification.
	 * 
	 * @param state
	 * @return string result
	 * 
	 * 0: Unknown
	 * 1: ACKNOW (Acknowledgement Block)
	 * 3: CONTSC (Continue Serial Communication Block)
	 * 3: CRCSND (Cyclic Redundancy Check Send Block)
	 * 4: SUCCES (Success Block) 
	 * 5: CLOSEC (close Communication Block)
	 * 
	 * -1: FAILTA (Failed Transfer Again Block)
	 */
	
	public String stateToString(int state){
		String result = new String("Unknown");
		
		switch(state){
			case 1:
				result = new String("ACKNOW (Acknowledgement Block)");
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
	
	/** 
	 * Compare data received in the serial buffer 
	 * if true start sending protocol data for 
	 * the EEPROM in chunks of 64 Bytes.
	 *  
	 * @param serial data[] buffer with a length of 6 bytes
	 * @return true if Acknowledgment block has been found
	 */
	
	private boolean findAcknowledgementBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == 6){
				if(
					data[0] == 0x41 &&
					data[1] == 0x43 &&
					data[2] == 0x4B && 
					data[3] == 0x4E &&
					data[4] == 0x4F && 
					data[5] == 0x57
				  )
				{
					result = true;
				}	
			} else {
				System.err.println("Error ocurred in SerialCommunication findAckowledgementBlock(byte data[])");
				System.err.print("Missmatch in the supplied data[], length is incorrect");
			}
		} catch(Exception e){
			System.err.println("Error ocurred in SerialCommunication findAckowledgementBlock(byte datap[])");
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
	
	private boolean findContinueBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == 6){
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
				System.err.println("Error ocurred in SerialCommunication findContinueSerialCommunicationBlock(byte data[])");
				System.err.print("Missmatch in the supplied data[], length is incorrect");
			}		
		} catch(Exception e){
			System.err.println("Error ocurred in SerialCommunication findContinueSerialCommunicationBlock(byte data[])");
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
	
	private boolean findSuccessBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == 6){
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
				System.err.println("Error ocurred in SerialCommunication findSuccesBlock(byte data[])");				
				System.err.print("Missmatch in the supplied data[], length is incorrect");					
			}			
		} catch(Exception e){
			System.err.println("Error ocurred in SerialCommunication findSuccesBlock(byte data[])");
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
	
	private boolean findCyclicRedundancyCheckSendBlock(byte data[]){
		boolean result = false; 
		
		try{
			if(data.length == 6){
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
				System.err.println("Error ocurred in SerialCommunication findCyclicRedundancyCheckSendBlock(byte data[]");				
				System.err.print("Missmatch in the supplied data[], length is incorrect");	
			}
			
			
		} catch(Exception e){
			System.err.println("Error ocurred in SerialCommunication findCyclicRedundancyCheck(byte data[])");
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
	private boolean findCloseCommunicationBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == 6){
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
				System.err.println("Error ocurred in SerialCommunication findCloseCommunicationBlocks(byte data[]");				
				System.err.print("Missmatch in the supplied data[], length is incorrect");	
			}
			
			
			
		}catch(Exception e){
			System.err.println("Error ocurred in SerialCommunication findCommunicationBlock(byte data[])");
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
	
	private boolean findFailedBlock(byte data[]){
		boolean result = false;
		
		try{
			if(data.length == 6){
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
				System.err.println("Error ocurred in SerialCommunication findFailedTransferAgainBlock(byte data[])");
				System.err.print("Missmatch in the supplied data[], length is incorrect");					
			}
		} catch(Exception e){
			System.err.println("Error ocurred in SerialCommunication findFailedTransferAgainBlock(byte data[])");
			e.printStackTrace(System.err);
		}
		
		return result;
	}
	
}
