package dataContainers;

public class ControlChangeFadeData extends DataStructure {
	
	
	private static final long serialVersionUID = 8772944200301411154L;

	final private int ID = 0xE6;
	
	private int[] data = {
							0xF0, 	//0 start bit
							ID,		//1 Option ID
							1,		//2 channel
							0,		//3 Resolution Option
							12,		//4 Control Change Number
							0,		//5 Start Value
							127,	//6 Hold value
							0,		//7 End Value
							0,		//8 Fade in
							0,		//9 Fade out	
							0xFF	//10 End Bit						
						 };
	
	public ControlChangeFadeData(){
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
		if(channel > 0 && channel <=16 ){
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
		if(number >= 0 && number <= 127){
			data[4] = number;
		}
	}
	
	public int getControlChangeNumber(){
		return data[4];
	}
	
	/* 
	 * TODO add logic when resolution 
	 * statement is toggled
	 * And add logic that inhibits
	 * top value smaller than lower
	 * value vice versa
	 */
	
	public void setStartValue(int startValue){
		data[5] = startValue;
	}
	
	public int getStartValue(){
		return data[5];
	}
	
	public void setHoldValue(int holdValue){
		data[6] = holdValue;
	}
	
	public int getHoldValue(){
		return data[6];
	}
	
	public void setEndValue(int endValue){
		data[7] = endValue;
	}

	public int getEndValue(){
		return data[7];
	}
	
	public void setFadeInValue(int fadeIn){
		if(fadeIn>=0 && fadeIn <= 2000){
			data[8] = fadeIn;
		}
	}
	
	public int getFadeInValue(){
		return data[8];
	}
	
	public void setFadeOutValue(int fadeOut){
		if(fadeOut >= 0 && fadeOut <= 2000){
			data[9] = fadeOut;
		}
	}
	
	public int getFadeOutValue(){
		return data[9];
	}
	

	
	@Override 
	public String toString(){
		String interValues = new String(
										"ControlChangeFadeData" + "\n" +
										"Option ID   : " + data[1] + "\n" +
										"MIDI Channel: " + data[2] + "\n" +
										"Resolution  : " + data[3] + "\n" +
										"CCNumber    : " + data[4] + "\n" +
										"Start Value : " + data[5] + "\n" +
										"Hold Value  : " + data[6] + "\n" +
										"End Value   : " + data[7] + "\n" +
										"Fade In     : " + data[8] + "\n" +
										"Fade Out    : " + data[9] + "\n"
									   );
		
		return interValues;
	}
}


