package dataContainers;

public class ControlChangeToggleData extends DataStructure {

	private static final long serialVersionUID = -4124733788568077361L;

	final private int ID = 0xE9;

	private int[] data = {
							0xF0,	//0 Start Bit
							ID,		//1 Option ID
							0, 		//2 Toggle Option
							1,		//3 Channel
							0,		//4 Resolution
							12,		//5 CC Number
							127,	//6 On value
							0,		//7 Off value
							0xFF	//8 end Byte
						  };
	
	public ControlChangeToggleData(){}
	
	@Override
	public void loadValues(int[] defaultData) {
		if(defaultData[1] == data[1]){
			System.arraycopy(defaultData, 0, data, 0, defaultData.length);
		}	
	}

	@Override
	public int[] getData() {
		return data;
	}
	
	public void setToggleOption(int option){
		if(option >= 0 && option <= 1){
			data[2] = option;
		}
	}

	public void setChannel(int channel){
		if(channel > 0 & channel <= 16){
			data[3] = channel;
		}
	}
	
	public int getChannel(){
		return data[3];
	}
	
	public void setResolutionOption(int option){
		if(option >= 0 && option <= 1){
			data[4] = option;
		}
	}
		
	public int getResolutionOption(){
		return data[4];
	}
	
	public void setControlChangeNumber(int number){
		if(number >= 0 && number <= 127){
			data[5] = number;
		}
	}
	
	public int getControlChangeNumber(){
		return data[5];
	}
	
	public void setOnValue(int onValue){
		data[6] = onValue; 
	}
	
	public int getOnValue(){
		return data[6];
	}
	
	public void setOffValue(int offValue){
		data[7] = offValue; 
	}
	
	public int getOffValue(){
		return data[7];
	}
	
	@Override
	public String toString() {
		String internalValues = new String(
				"ControlChangeData" + "\n" +
				"Option ID   : " + data[1] + "\n" +
				"SW Behaviour: " + data[2] + "\n" +
				"MIDI Channel: " + data[3] + "\n" + 
				"Resolution  : " + data[4] + "\n" +
				"Toggle      : " + data[5] + "\n" +
				"CCNumber    : " + data[6] + "\n" +
				"On Value    : " + data[7] + "\n" +
				"Off Value   : " + data[8] + "\n"
			   );

		return internalValues;
	}

}
