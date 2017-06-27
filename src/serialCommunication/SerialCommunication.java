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
	private int blockSize = 8;
	
	IncomingSerial serialMessages;
	
	private byte[] readBuffer =  new byte[32];
	private byte[] dataBuffer = new byte[blockSize]; 
	
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
	
	public SerialCommunication(IncomingSerial compareSerialBlocks){
		serialMessages = compareSerialBlocks;
		
		blockSize = serialMessages.getBlockSize();
		dataBuffer = new byte[blockSize];
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
	 * state of the object appropriate actions
	 * can be taken. The state is determined 
	 * by a calling the compare method of a
	 * CompareSerialBlocks object.
	 * 
	 * 
	 * @author Mario van Etten
	 * @date   	22-01-2017 
	 * @state returns state of the object.
	 * the state changes based on the serial
	 * messages received on the COMM Port
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
				
				      if(numRead == blockSize){
				    	  System.arraycopy(readBuffer, 0, dataBuffer, 0, dataBuffer.length);
				    	  state = serialMessages.compare(dataBuffer);
				      }				      
				}
		} catch(Exception e){
			System.err.println("Error occurred while polling the Serial Port");
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
			System.err.println("Error occurred in SerialCommunication String[] getPorts()");
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
			System.err.println("Error occurred in SerialCommunication openPort(String name)");
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
			System.err.println("Error Occcured in SerialCommunication closePort(String name)");
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
				System.err.println("Error occurred in SerialCommunication writeDataToCommunicationPort(byte data[])");
				System.err.println("Commport is not available, nothing has been send");
			}		
		}catch(Exception e){
			System.err.println("Error occurred in SerialCommunication writeSerialToCommPort");
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
}
