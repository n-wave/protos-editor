package helpers;

public class CompareProtocolBlocks {

public CompareProtocolBlocks(){
	
}


/**
 * compare(byte data[])
 * 
 * Makes a comparison based on the contents 
 * of the supplied bytes in the data[]
 * 
 * 0 : Unknown
 * 1 : Scene Block 	   (SCENENR(n))
 * 2 : Start Block 	   (STRBLOCK)
 * 3 : End Block  	   (ENDBLOCK)
 * 4 : CRC Begin Block (CRCBGNBL)
 * 5 : CRC End Block   (CRCENDBL)
 * 
 * @param data
 * @return return on int based on the contents 
 * of the data Array.
 * 
 * 
 */

public int compare(byte data[]){
	if(compareToSceneBlock(data)){
		return 1;
	}
	
	if(compareToStartBlock(data)){
		return 2;
	}
	
	if(compareToEndBlock(data)){
		return 3;
	}
	
	if(compareToCyclicRedundancyCheckBeginBlock(data)){
		return 4;
	}
	
	if(compareToCyclicRedundancyEndBlock(data)){
		return 5;
	}
	
	return 0;
}


/** 
 * compareToSceneBlock(byte data[])
 * 
 * If the data argument contains the 
 * characters which define the Start Block
 * The main loop will be entered and the 
 * parsing of the DataStructures will 
 * commence.
 * 
 * @param data
 * @return true if the contents of the buffer equals SCENENR 
 * 
 **/

private boolean compareToSceneBlock(byte data[]){
	boolean result = false;
	
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
				result = true;
			} 		
	} catch(Exception e){
		System.err.println("Error ocurred in CompareToProtocolBlocks compareToSceneBlock(byte data[])");
		e.printStackTrace(System.err);
	}
	
	return result;
}


/** 
 * compareToStartBlock(byte data[])
 * 
 * If the data argument contains the 
 * characters which define the Start Block
 * The main loop will be entered and the 
 * parsing of the DataStructures will 
 * commence.
 * 
 * @param data
 * @return true if the contents of the buffer equals STRBLOCK 
 * 
 **/

private boolean compareToStartBlock(byte data[]){
	boolean result = false; 
	
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
			result = true;
		}
	} catch(Exception e){
		System.err.println("Error ocurred in CompareToProtocolBlocks compareToStartBlock(byte data[])");
		e.printStackTrace(System.err);
	}
	
	return result;
}

/**
 * compareToEndBlock(byte data[])
 * 
 * If the data argument contains the 
 * character which defines the End Block. 
 * The total Amount of data for a specific 
 * Scene has been parsed.
 * 
 * @param data
 * @return true if the contents of the buffer equals ENDBLOCK
 * 
 */

private boolean compareToEndBlock(byte data[]){
	boolean result = false; 
	
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
			result = true;
		} 			
	} catch(Exception e){
		System.err.println("Error ocurred in CompareSceneBlocks compareToEndBlock(byte data[])");
		e.printStackTrace(System.err);
	}
	
	return result;
}

/**
 * compareToCyclicRedundancyCheckBeginBlock(byte data[])
 * 
 * If the data argument contains the 
 * character which defines the End Block. 
 * The total Amount of data for a specific 
 * Scene has been parsed.
 * 
 * @param data
 * @return true if the contents of the buffer equals CRCBGNBL
 * 
 */

private boolean compareToCyclicRedundancyCheckBeginBlock(byte data[]){
	boolean result = false;
	
	try{
		if(
			data[0] == 0x43 &&	//C
			data[1] == 0x52 &&	//R
			data[2] == 0x43 &&	//C
			data[3] == 0x42 &&	//B
			data[4] == 0x47 &&	//G
			data[5] == 0x4E && 	//N
			data[6] == 0x42 && 	//B
			data[7] == 0x4C		//L
		  )
		{
			result = true;
		}
			
	} catch(Exception e){
		System.err.println("Error ocurred in CompareSceneBlocks compareToCyclicRedundancyCheckBeginBlock(byte data[])");
		e.printStackTrace(System.err);
	}
	
	return result;
}
	
/**
 * compareToCyclicRedundancyCheckEndBlock(byte data[])
 * 
 * If the data argument contains the 
 * character which defines the End Block. 
 * The total Amount of data for a specific 
 * Scene has been parsed.
 * 
 * @param data
 * @return true if the contents of the buffer equals CRCENDBL
 * 
 */

private boolean compareToCyclicRedundancyEndBlock(byte data[]){
	boolean result = false;
	
	try{
		if(
			data[0] == 0x43 && //C
			data[1] == 0x52 && //R
			data[2] == 0x43 && //C
			data[3] == 0x45 && //E
			data[4] == 0x4E && //N
			data[5] == 0x44 && //D
			data[6] == 0x42 && //B
			data[7] == 0x4C	   //L
		  )
		{
			result = true;
		}
	} catch(Exception e){
		System.err.println("Error ocurred in CompareSceneBlocks compareToCyclicCheckEndBlock(byte data[])");
		e.printStackTrace(System.err);
	}
	
	return result;
}


}
