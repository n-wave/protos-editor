package helpers;

/*
 * Parse the byte Protocol to 
 * a String in a readable format
 * 
 * Functions for all DataStructures
 * Algo's can be used in a modified 
 * form for the embedded system.
 * 
 */

/**
* 0 : Unknown
* 1 : Scene Block 	   (SCENENR(n))
* 2 : Start Block 	   (STRBLOCK)
* 3 : End Block  	   (ENDBLOCK)
* 4 : CRC Begin Block (CRCBGNBL)
* 5 : CRC End Block   (CRCENDBL)
**/

public class ProtocolToString {
	
	private CompareProtocolBlocks compare = new CompareProtocolBlocks();
	
	public String getProtocolBlock(byte data[]){
		String result = new String();
		
		int option = compare.compare(data);
		
		switch(option){
			case 0:
				result = "Unknown";
				break;
			case 1:
				result = "Scene Block: " + data[7] ;
				break;
			case 2:
				result = "  Start Block";
				break;
			case 3: 
				result = "   End Block";
				break;
			case 4:
				result = "CRC Begin Block";
				break;
			case 5:
				result = " CRC End Block";
				break;
		}
		return result;
	}
	
	
	
	
	public String getProtocolString(byte buffer[])
	{
		
		int optionId = Byte.toUnsignedInt(buffer[1]);
		String values = new String();
		
		switch(optionId){
			case 0xE0:
				values = programChangeToString(buffer);
				break;
			case 0xE1: 
				values = noteVelocityToString(buffer);
				break;
			case 0xE2:
				values = noteControlChangeToString(buffer);					
				break;
			case 0xE3:
				values = pitchBendToString(buffer);
				break;
			case 0xE4:
				values = pitchBendNoteToString(buffer);
				break;
			case 0xE5:
				values = controlChangeToString(buffer);
				break; 
			case 0xE6:
				values = controlChangeFadeToString(buffer);
				break;	
			case 0xE7:
				values = noteVelocityToggleToString(buffer);
				break;
			case 0xE8:
				values = noteControlChangeToggleToString(buffer);
				break;
			case 0xE9:
				values = controlChangeToggleToString(buffer);
				break;
			case 0xEA:
				values = controlChangeFadeToggleToString(buffer);
				break;
			case 0xEF:
				values = disabledControllerToString(buffer);
				break;
			case 0xEE:
				values = sceneDataToString(buffer);
				break;	
		}	
		return values;		
	}
	
	
	
	private String programChangeToString(byte buffer[])
	{
		String values = new String();
		try{	
			
			/** 
			 *  Convert to unsigned int 
			  *	or else the comparison 
			  * will fail.
			  * 
			  **/
			int startByte = Byte.toUnsignedInt(buffer[0]);
			int optionId = Byte.toUnsignedInt(buffer[1]);
			int endByte = Byte.toUnsignedInt(buffer[5]);
			
			if(startByte == 0xF0 && optionId == 0xE0 && endByte == 0xFF)
			{
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Program Change ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Bank Select \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Program Select \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : End Byte \n";

				for(int i=6; i<buffer.length; i++)
				{	
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error"; //Error ocurred there shouldn't be any value stored
					}
				}
				
				
			} else {
				values = "Error in parsing while parsing program change \n";
				return values;
			}
			
		} catch(Exception e){
			System.err.println("Error Ocurred in ProtocolToString::ProgramChangeToString");
			e.printStackTrace(System.err);
		}
		return values;
	}
	
	private String noteVelocityToString(byte buffer[])
	{
		String values = new String();
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int endByte = Byte.toUnsignedInt(buffer[6]);
		
		try{
			if(startByte == 0xF0 && optionId == 0xE1 && endByte == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Note Velocity ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Pitch \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Velocity \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Link/Static Option \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : End Byte \n";
				
				for(int i=7; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error";
					}
				}
				
			} else {
				values = "Error while parsing note velocity data";
			}
			
		} catch(Exception e) {
			System.err.println("Error ocurred in ProtocolToString::noteVelocityToString");
			e.printStackTrace(System.err);		
		}
		
		return values;
	}
	
	private String noteControlChangeToString(byte buffer[])
	{
		String values = new String();
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int resolutionByte = Byte.toUnsignedInt(buffer[6]);
		
		/** 8 Bit Value **/
		try{
			if(startByte == 0xF0 && optionId == 0xE2 && resolutionByte == 0x00 &&  Byte.toUnsignedInt(buffer[10]) == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Note Control Change ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Pitch \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Velocity \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Link/Static Option \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Resolution 8-bit \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Control Change Number \n";	
				values += Byte.toUnsignedInt(buffer[8]) + " : Top Value \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : Bottom Value \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : End Byte \n";
				
				for(int i=11; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error \n";
					}
				}
				
				
			/** 16 Bit Value **/
			} else if(startByte == 0xF0 && optionId == 0xE2 && resolutionByte == 0x01 &&  Byte.toUnsignedInt(buffer[12]) == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Note Control Change ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Pitch \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Velocity \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Link/Static Option \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Resolution 14-bit \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Top Value MSB \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : Top Value LSB \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : Bottom Value MSB \n";
				values += Byte.toUnsignedInt(buffer[11]) + " : Bottom Value LSB \n";
				values += Byte.toUnsignedInt(buffer[12]) + " : End Byte \n";
				
				for(int i=13; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error";
					}
				}
			} else {
				values = "Error while Parsing Note Control Change Data \n";
			}
			
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::noteControlChangeToString");
			e.printStackTrace(System.err);
		}
		
		return values;
	}

	private String pitchBendToString(byte buffer[])
	{
		String values = new String();	
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int endByte = Byte.toUnsignedInt(buffer[3]);
		
		try{
			if(startByte == 0xF0 && optionId == 0xE3 && endByte == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Pitch Bend ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : End Byte \n";
				
				for(int i=4; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error";
					}
				}
				
			} else {
				values = "Error while Parsing Pitch Bend Data";
			}
				
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::pitchBendToNoteString");
			e.printStackTrace(System.err);
		}
		
		return values;
	}
	
	private String pitchBendNoteToString(byte buffer[])
	{
		String values = new String(); 

		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int endByte = Byte.toUnsignedInt(buffer[5]);
		
		try{
			if(startByte == 0xF0 && optionId == 0xE4 && endByte == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Pitch Bend Note ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";	
				values += Byte.toUnsignedInt(buffer[3]) + " : Pitch \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Velocity \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : End Byte \n";
				
				
				for(int i=6; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error \n";
					}	
				}
			} else {
				values = "Error while Parsing Pitch Bend Note Data \n";
			}
				
			
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::pitchBendNoteToString");
			e.printStackTrace(System.err);
		}
		
		
		return values;
	}
	
	
	private String controlChangeToString(byte buffer[])
	{
		String values = new String();

		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int resolutionByte = Byte.toUnsignedInt(buffer[3]);
		
		try{ 
			
			/** 8 Bit Values **/
			if(startByte == 0xF0 && optionId == 0xE5 && resolutionByte == 0x00 && Byte.toUnsignedInt(buffer[7]) == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Control Change ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Resolution 8-bit \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Top Value \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Bottom Value \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : End Byte \n";
				
				for(int i=8; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error \n";
					}
				} 
			} else if(startByte == 0xF0 && optionId == 0xE5 && resolutionByte == 0x01 && Byte.toUnsignedInt(buffer[9]) == 0xFF){
				
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Control Change ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Resolution 14-bit \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Top Value MSB\n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Top Value LSB\n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Bottom Value MSB \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Bottom Value MSB \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : End Byte \n";
				
				for(int i=10; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error \n";
					}
				} 
			} else {
				values = "Error while parsing Control Change Data \n";
			}
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::controlChangeToString");
			e.printStackTrace(System.err);
		}
	
		return values;
	}
	
	private String controlChangeFadeToString(byte buffer[])
	{
		String values = new String();
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int resolutionByte = Byte.toUnsignedInt(buffer[3]);
		
		try{
			if(startByte == 0xF0 && optionId == 0xE6 && resolutionByte == 0x00 && Byte.toUnsignedInt(buffer[12]) == 0xFF){
				
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Control Change Fade ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Resolution 8-bit \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Start \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Hold \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : End \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Fade In MSB \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : Fade In LSB \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : Fade Out MSB \n";
				values += Byte.toUnsignedInt(buffer[11]) + " : Fade Out LSB \n";
				values += Byte.toUnsignedInt(buffer[12]) + " : End Byte \n";
				
				for(int i=13; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error";
					}			
				}
				
			} else if(startByte == 0xF0 && optionId == 0xE6 && resolutionByte == 0x01 && Byte.toUnsignedInt(buffer[15]) == 0xFF){
				
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Control Change Fade ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Resolution 14-bit \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Start MSB\n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Start LSB\n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Hold MSB \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Hold LSB \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : End MSB \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : End LSB \n";			
				values += Byte.toUnsignedInt(buffer[11]) + " : Fade In MSB \n";
				values += Byte.toUnsignedInt(buffer[12]) + " : Fade In LSB \n";
				values += Byte.toUnsignedInt(buffer[13]) + " : Fade Out MSB \n";
				values += Byte.toUnsignedInt(buffer[14]) + " : Fade Out LSB \n";
				values += Byte.toUnsignedInt(buffer[15]) + " : End Byte \n";
				
			} else {
				values = "Error while parsing Control Change Fade Data \n";
			}
			
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::controlChangeFadeToString \n");
			e.printStackTrace(System.err);			
		}
		
		return values;
	}
	
	private String noteVelocityToggleToString(byte buffer[]){
		String values = new String();
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int endByte = Byte.toUnsignedInt(buffer[6]);
		
		try{
			if(startByte == 0xF0 && optionId == 0xE7 && endByte == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Note Velocity Toggle ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : Switch Option \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Pitch \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Velocity \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : End Byte \n";
				
				for(int i=7; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error";
					}
				}
				
			} else {
				values = "Error while parsing note velocity data";
			}
			
		} catch(Exception e) {
			System.err.println("Error ocurred in ProtocolToString::noteVelocityToString");
			e.printStackTrace(System.err);		
		}
		
		return values;
	}
	
	
	private String noteControlChangeToggleToString(byte buffer[])
	{
		String values = new String();
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int resolutionByte = Byte.toUnsignedInt(buffer[6]);
		
		/** 8 Bit Value **/
		try{
			if(startByte == 0xF0 && optionId == 0xE8 && resolutionByte == 0x00 &&  Byte.toUnsignedInt(buffer[10]) == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Note Control Change Toggle ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : Toggle Option \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Pitch \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Velocity \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Resolution 8-bit \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Control Change Number \n";	
				values += Byte.toUnsignedInt(buffer[8]) + " : Top Value \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : Bottom Value \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : End Byte \n";
				
				for(int i=11; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error \n";
					}
				}
				
				
			/** 16 Bit Value **/
			} else if(startByte == 0xF0 && optionId == 0xE8 && resolutionByte == 0x01 &&  Byte.toUnsignedInt(buffer[12]) == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Note Control Change Toggle ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : Toggle Option \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Pitch \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Velocity \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Resolution 14-bit \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Top Value MSB \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : Top Value LSB \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : Bottom Value MSB \n";
				values += Byte.toUnsignedInt(buffer[11]) + " : Bottom Value LSB \n";
				values += Byte.toUnsignedInt(buffer[12]) + " : End Byte \n";
				
				for(int i=13; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error";
					}
				}
			} else {
				values = "Error while Parsing Note Control Change Toggle Data \n";
			}
			
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::noteControlChangeToString");
			e.printStackTrace(System.err);
		}
		
		return values;
	}
	
	private String controlChangeToggleToString(byte buffer[])
	{
		String values = new String();

		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int resolutionByte = Byte.toUnsignedInt(buffer[4]);
		
		try{ 
			
			/** 8 Bit Values **/
			if(startByte == 0xF0 && optionId == 0xE9 && resolutionByte == 0x00 && Byte.toUnsignedInt(buffer[8]) == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Control Change Toggle ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : Toggle Option \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Resolution 8-bit \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Top Value \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Bottom Value \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : End Byte \n";
				
				for(int i=9; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error \n";
					}
				} 
			} else if(startByte == 0xF0 && optionId == 0xE9 && resolutionByte == 0x01 && Byte.toUnsignedInt(buffer[10]) == 0xFF){
				
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Control Change Toggle ID \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : Toggle Option \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Resolution 14-bit \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Top Value MSB\n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Top Value LSB\n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Bottom Value MSB \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : Bottom Value MSB \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : End Byte \n";
				
				for(int i=11; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error \n";
					}
				} 
			} else {
				values = "Error while parsing Control Change Toggle Data \n";
			}
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::controlChangeToString");
			e.printStackTrace(System.err);
		}
	
		return values;
	}
	
	private String controlChangeFadeToggleToString(byte buffer[])
	{
		String values = new String();
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		
		/* 
		 * Extract Toggle and Resolution by masking the unwanted bits
		 * Use the same Method in the MCU software 
		 **/
		
		int toggleOption =  (Byte.toUnsignedInt(buffer[3]) & 0B00000010) >> 1;
		int resolutionByte = Byte.toUnsignedInt(buffer[3]) & 0B00000001;

		
		
		
		try{
			if(startByte == 0xF0 && optionId == 0xEA && resolutionByte == 0x00 && Byte.toUnsignedInt(buffer[12]) == 0xFF){
				
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Control Change Fade Toggle ID \n";
				values += toggleOption + 				  " : Toggle Option extracted from Resolution \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Resolution 8-bit \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Start \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Hold \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : End \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Fade In MSB \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : Fade In LSB \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : Fade Out MSB \n";
				values += Byte.toUnsignedInt(buffer[11]) + " : Fade Out LSB \n";
				values += Byte.toUnsignedInt(buffer[12]) + " : End Byte \n";
				
				for(int i=13; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error";
					}			
				}
				
			} else if(startByte == 0xF0 && optionId == 0xEA && resolutionByte == 0x01 && Byte.toUnsignedInt(buffer[15]) == 0xFF){			
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Control Change Fade Toggle ID \n";
				values += toggleOption + 				  " : Toggle Option extracted from Resolution \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : MIDI Channel \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : Resolution 14-bit \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Control Change Number \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Start MSB\n";
				values += Byte.toUnsignedInt(buffer[6]) + " : Start LSB\n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Hold MSB \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Hold LSB \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : End MSB \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : End LSB \n";			
				values += Byte.toUnsignedInt(buffer[11]) + " : Fade In MSB \n";
				values += Byte.toUnsignedInt(buffer[12]) + " : Fade In LSB \n";
				values += Byte.toUnsignedInt(buffer[13]) + " : Fade Out MSB \n";
				values += Byte.toUnsignedInt(buffer[14]) + " : Fade Out LSB \n";
				values += Byte.toUnsignedInt(buffer[15]) + " : End Byte \n";
				
			} else {
				values = "Error while parsing Control Change Fade Toggle Data \n";
			}
			
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::controlChangeFadeToggleToString \n");
			e.printStackTrace(System.err);			
		}
		
		return values;
	}
	
	private String sceneDataToString(byte buffer[])
	{
		String values = new String();
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int endByte = Byte.toUnsignedInt(buffer[15]);
		
		try{
			if(startByte == 0xF0 && optionId == 0xEE && endByte == 0xFF)
			{
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Scene Data Id \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : Option \n";
				values += Byte.toUnsignedInt(buffer[3]) + " : MIDI Channel 1 \n";
				values += Byte.toUnsignedInt(buffer[4]) + " : Bank 1 \n";
				values += Byte.toUnsignedInt(buffer[5]) + " : Program 1 \n";
				values += Byte.toUnsignedInt(buffer[6]) + " : MIDI Channel 2 \n";
				values += Byte.toUnsignedInt(buffer[7]) + " : Bank 2 \n";
				values += Byte.toUnsignedInt(buffer[8]) + " : Program 2 \n";
				values += Byte.toUnsignedInt(buffer[9]) + " : MIDI Channel 3 \n";
				values += Byte.toUnsignedInt(buffer[10]) + " : Bank 3 \n";
				values += Byte.toUnsignedInt(buffer[11]) + " : Program 3 \n";
				values += Byte.toUnsignedInt(buffer[12]) + " : MIDI Channel 4 \n";
				values += Byte.toUnsignedInt(buffer[13]) + " : Bank 4 \n";
				values += Byte.toUnsignedInt(buffer[14]) + " : Program 4 \n";
				values += Byte.toUnsignedInt(buffer[15]) + " : End Byte \n";
			} else {
				values = "Error while parsing Scene Data \n";
			}
		} catch(Exception e){
			System.err.println("Error ocurred in ProtocolToString::sceneDataToString");
			e.printStackTrace(System.err);
		}	
		return values;
	}
	
	private String disabledControllerToString(byte buffer[]){
		String values = new String();
		
		int startByte = Byte.toUnsignedInt(buffer[0]);
		int optionId = Byte.toUnsignedInt(buffer[1]);
		int endByte = Byte.toUnsignedInt(buffer[2]);
		
	
		
		
		try {
			if(startByte == 0xF0 && optionId == 0xEF && endByte == 0xFF){
				values += Byte.toUnsignedInt(buffer[0]) + " : Start Byte \n";
				values += Byte.toUnsignedInt(buffer[1]) + " : Disabled Controller \n";
				values += Byte.toUnsignedInt(buffer[2]) + " : End Byte \n";
				
				for(int i=3; i<buffer.length; i++){
					if(buffer[i] == 0){
						values += Byte.toUnsignedInt(buffer[i]) + " : Zero Padding \n";
					} else {
						values += "Error";
					}	
				}
			} else {
				values = "Error occurred while parsing disabled controller data \n";
			}
			
		} catch(Exception e){
			System.err.println("Error occurred in ProtocolToString::disabledControllerToString(byte[])");
			e.printStackTrace(System.err);
		}
		
		return values;
	}
}




