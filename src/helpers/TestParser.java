package helpers;

public class TestParser {
	ProtocolParser parser = new ProtocolParser();

	public TestParser(){}

	/** Parse contents to console **/
	
	public void parsePreset(Preset preset){
		String byteArrayString = new String();
		Preset tmpPreset = new Preset("tmp");
		tmpPreset = preset;
		

		try{
		parser.parsePreset(tmpPreset);
	    byteArrayString = parser.toStringTestEight();
	    
	    System.out.println(byteArrayString);
		
	    byte presetBuffer[] = parser.getByteArray();
	    
	    int bgnIndex = 1952;
	    
	    /**CRC Begin Block Printing **/ 
	    System.out.println("Out CRCBGNBL");
	    for(int i=0; i<8; i++){
	    	System.out.println(Character.toString((char)presetBuffer[bgnIndex++]));
	    }
	    	    		
	    int endIndex = 1964;
	    
	    /**CRC End Block printing **/
	    System.out.println("Out CRCENDBL");
	    for(int i=0; i<8; i++){
	    	System.out.println(Character.toString((char)presetBuffer[endIndex++]));
	    }
	    
	    byte crcBuffer[] = new byte[4];
	    int crcIndex = 1960;
	    
	    for(int i=0; i<4;i++){
	    	crcBuffer[i] = presetBuffer[crcIndex++];
	    	System.out.println("Byte [" + i + "] :" + Byte.toUnsignedInt(crcBuffer[i]));
	    }
	    
	    long crc = 0L;
	    crc = parser.convertToLong(crcBuffer);
	    System.out.println("long crc : " + crc);
	    
	    
		}catch(Exception e){
			System.err.println("Error occurred in TestParser testEight(Preset preset)");
			e.printStackTrace(System.err);
		}
	}
}
