package dataContainers;

import dataContainers.DataStructure;;

public class ControlChangeData extends DataStructure {
	
	private static final long serialVersionUID = -8628646768026806991L;

	final private int ID = 0xE5;

	private int[]data = {
							0xF0,	//0 Start Bit
							ID,		//1 Option ID
							1,		//2 Channel
							0,		//3 Resolution
							12,		//4 CC Number
							127,	//5 top value
							0,		//6 bottom value
							0xFF	//7
						};
	
	public ControlChangeData(){
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

	public void setChannel(int channel){
		if(channel > 0 && channel <= 16){
			data[2] = channel;
		}
	}
	
	public int getChannel(){
		return data[2];
	}
	
	public void setResolutionOption(int option){
		if(option >= 0 && option <= 1){
			data[3] = option;
		}
	}
	
	public int getResolutionOption(){
		return data[3];
	}
	
	public void setControlChangeNumber(int number){
		if(number >= 0 && number <=127){
			data[4] = number;
		}
	}
	
	public int getControlChangeNumber(){
		return data[4];
	}

	public void setTopValue(int topValue){
		data[5] = topValue;
	}
	
	public int getTopValue(){
		return data[5];
	}
	
	public void setBottomValue(int bottomValue){
		data[6] = bottomValue;
	}
	
	public int getBottomValue(){
		return data[6];
	}
	
	@Override 
	public String toString(){
		String internalValues = new String(
										"ControlChangeData" + "\n" +
										"Option ID   : " + data[1] + "\n" +
										"MIDI Channel: " + data[2] + "\n" + 
										"Resolution  : " + data[3] + "\n" +
										"CCNumber    : " + data[4] + "\n" +
										"Top Value   : " + data[5] + "\n" +
										"Bottom value: " + data[6] + "\n"
									   );
		
		return internalValues;
	}
}

