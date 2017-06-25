package dataContainers;

public class NoteControlChangeData extends DataStructure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6510412498113184290L;

	final private int ID = 0xE2;
	
	private int[] data = {
							0xF0,	//0 Start Bit
						 	ID,		//1 Option Id
						 	1, 		//2 MIDI Channel
						 	60,		//3 MIDI pitch
						 	100,	//4 MIDI Velocity
						 	0,		//5 Link/Static Option
						 	0,		//6 Resolution Option
						 	12,		//7 Control Change Number
						 	127,	//8 Top Value
						 	0,		//9 Bottom Value
						 	0xFF	//10 End Bit
						 };
	
	public NoteControlChangeData(){
		//System.out.println(this.toString());
	}
	
	public int[] getData(){
		return data;
	}
	
	@Override
	public void loadValues(int[] defaultData) {
		if(defaultData[1] == data[1]){
			System.arraycopy(defaultData, 0, data, 0, defaultData.length);
		}	
	}
	
	/** MIDI Channel **/
	public void setChannel(int channel){
		if(channel > 0 && channel <= 16){
			data[2] = channel;
		}
	}
	
	public int getChannel(){
		return data[2];
	}
	
	/** Pitch **/
	public void setPitch(int pitch){
		data[3] = pitch;
	}
	
	public int getPitch(){
		return data[3];
	}
	
	/** Velocity **/
	public void setVelocity(int velocity){
		if(velocity >= 0 && velocity <= 127)
		data[4] = velocity;
	}
	
	public int getVelocity(){
		return data[4];
	}
	
	/** Velocity Option **/
	public void setVelocityOption(boolean option){
		data[5] = option ? 1: 0;
	}
	
	public boolean getVelocityOption(){
		return (data[5] != 0);
	}
	
	/** Resolution **/
	public void setResolutionOption(int option){	
		if(option >= 0 && option <= 1){
			data[6] = option;
		}
	}
	
	public int getResolutionOption(){
		return data[6];
	}
	
	/**  Control Change Number **/
	public void setControlChangeNumber(int number){
		if(number >= 0 && number <= 127){
			data[7] = number;
		}
	}

	public int getControlChangeNumber(){
		return data[7];
	}
	
	/* 
	 * TODO add logic when resolution 
	 * statement is toggled
	 * And add logic that inhibits
	 * top value smaller than lower
	 * value vice versa
	 */
	/** Top Value **/
	public void setTopValue(int topValue){
		data[8] = topValue;
	}
	
	public int getTopValue(){
		return data[8];
	}
	/** Bottom Value **/ 
	public void setBottomValue(int bottomValue){
		data[9] = bottomValue;
	}

	public int getBottomValue(){
		return data[9];
	}
	
	@Override 
	public String toString(){
		String internalValues = new String(
										"NoteControlChangeData" + "\n" +
										"Option ID   : " + data[1] + "\n" +
										"MIDI Channel: " + data[2] + "\n" +
										"MIDI Pitch  : " + data[3] + "\n" +
										"Velocity    : " + data[4] + "\n" +
 										"Link/Static : " + data[5] + "\n" +
										"Resolution  : " + data[6] + "\n" +
										"CC Number   : " + data[7] + "\n" +
										"Top Value   : " + data[8] + "\n" +
										"Bottom Value: " + data[9] + "\n"
									   );
		
		return internalValues;
	}	
}
