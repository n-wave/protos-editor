package dataContainers;

public class ControlChangeFadeToggleData extends DataStructure {

	private static final long serialVersionUID = 5417825343254169940L;

	final private int ID = 0xEA;
	
	private int[] data = {
			0xF0, 	//0 start bit
			ID,		//1 Option ID
			0, 		//2 Toggle Option
			1,		//3 channel
			0,		//4 Resolution Option
			12,		//5 Control Change Number
			0,		//6 Start Value
			127,	//7 Hold value
			0,		//8 End Value
			0,		//9 Fade in
			0,		//10 Fade out	
			0xFF	//11 End Bit						
		 };
	
	public ControlChangeFadeToggleData(){}
	
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
	
	public int getToggleOption(){
		return data[2];
	}

	public void setChannel(int channel){
		if(channel > 0 && channel <=16 ){
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
	
	public void setStartValue(int startValue){
		data[6] = startValue;
	}
	
	public int getStartValue(){
		return data[6];
	}
	
	public void setHoldValue(int holdValue){
		data[7] = holdValue;
	}
	
	public int getHoldValue(){
		return data[7];
	}
	
	public void setEndValue(int endValue){
		data[8] = endValue;
	}

	public int getEndValue(){
		return data[8];
	}
	
	public void setFadeInValue(int fadeIn){
		if(fadeIn>=0 && fadeIn <= 2000){
			data[9] = fadeIn;
		}
	}
	
	public int getFadeInValue(){
		return data[9];
	}
	
	public void setFadeOutValue(int fadeOut){
		if(fadeOut >= 0 && fadeOut <= 2000){
			data[10] = fadeOut;
		}
	}
	
	public int getFadeOutValue(){
		return data[10];
	}
	
	@Override
	public String toString() {
		String interValues = new String(
				"ControlChangeFadeToggleData" + "\n" +
				"Option ID   : " + data[1] + "\n" +
				"SW Behaviour: " + data[2] + "\n" +
				"MIDI Channel: " + data[3] + "\n" +
				"Resolution  : " + data[4] + "\n" +
				"CCNumber    : " + data[5] + "\n" +
				"Start Value : " + data[6] + "\n" +
				"Hold Value  : " + data[7] + "\n" +
				"End Value   : " + data[8] + "\n" +
				"Fade In     : " + data[9] + "\n" +
				"Fade Out    : " + data[10] + "\n"
			   );

		return interValues;
	}

}
