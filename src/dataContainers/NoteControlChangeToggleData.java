package dataContainers;

public class NoteControlChangeToggleData extends DataStructure {

	private static final long serialVersionUID = -3403157159763978237L;

	final private int ID = 0xE8;
	
	private int[] data = {
			0xF0,	//0 Start Bit
		 	ID,		//1 Option Id
		 	0, 		//2 Toggle Option
		 	1, 		//3 MIDI Channel
		 	60,		//4 MIDI pitch
		 	100,	//5 MIDI Velocity
		 	0,		//6 Resolution Option
		 	12,		//7 Control Change Number
		 	127,	//8 On Value
		 	0,		//9 Value Value
		 	0xFF	//10 End Bit
		 };
	
	public NoteControlChangeToggleData(){}
	
	
	@Override
	public void loadValues(int[] defaultData) {
		if(defaultData[1] == data[1]){
			System.arraycopy(defaultData, 0, data, 0, defaultData.length);
		}	
	}

	@Override 
	public int[] getData(){
		return data;
	}
	
	/** Toggle Option **/
	public void setToggleOption(int option){
		if(option >= 0 && option <= 1){
			data[2] = option;
		}
	}
	
	public int getToggleOption(){
		return data[2];
	}
	
	/** MIDI Channel **/
	public void setChannel(int channel){
		if(channel > 0 && channel <= 16){
			data[3] = channel;
		}
	}
	
	public int getChannel(){
		return data[3];
	}
	
	/** Pitch **/
	public void setPitch(int pitch){
		data[4] = pitch;
	}
	
	public int getPitch(){
		return data[4];
	}
	
	/** Velocity **/
	public void setVelocity(int velocity){
		if(velocity >= 0 && velocity <= 127)
		data[5] = velocity;
	}
	
	public int getVelocity(){
		return data[5];
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
	
	/** Top Value **/
	public void setOnValue(int topValue){
		data[8] = topValue;
	}
	
	public int getOnValue(){
		return data[8];
	}
	/** Bottom Value **/ 
	public void setOffValue(int bottomValue){
		data[9] = bottomValue;
	}

	public int getOffValue(){
		return data[9];
	}
	
	@Override 
	public String toString(){
		String internalValues = new String(
										"NoteControlChangeToggleData" + "\n" +
										"Option ID   : " + data[1] + "\n" +
										"SW Behaviour: " + data[2] + "\n" +
										"MIDI Channel: " + data[3] + "\n" +
										"MIDI Pitch  : " + data[4] + "\n" +
										"Velocity    : " + data[5] + "\n" +
 										"Link/Static : " + data[6] + "\n" +
										"Resolution  : " + data[7] + "\n" +
										"CC Number   : " + data[8] + "\n" +
										"Off Value   : " + data[9] + "\n" +
										"On Value    : " + data[10] + "\n"
									   );
		
		return internalValues;
	}	
}
