package dataContainers;

public class NoteVelocityToggleData extends DataStructure {

	private static final long serialVersionUID = -7971191276781455580L;

	private final int ID = 0xE7;
	
	private int[]data = {
			0xF0,	//0 Start
			ID,		//1 Option ID
			0, 		//2	Toggle Option
			1,     	//3 MIDI Channel
			60, 	//4 MIDI pitch
			100,	//5 MIDI velocity
			0xFF	//6 End Bit
		 };
	
	@Override
	public void loadValues(int[] defaultData) {
		if(defaultData[1] == data[1]){
			System.arraycopy(defaultData, 0, data, 0, defaultData.length);
		}	
	}

	@Override
	public int[] getData() {
		// TODO Auto-generated method stub
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
		if(channel > 0 && channel <= 16){
			data[3] = channel;
		}
	}
	
	public int getChannel(){
		return data[3];
	}
	
	public void setPitch(int pitch){
		data[4] = pitch;
	}
	
	public int getPitch(){
		return data[4];
	}
	
	public void setVeocity(int velocity){
		if(velocity >= 0 && velocity <= 127){
			data[5] = velocity;
		}
	}
	
	public int getVelocity(){
		return data[5];
	}
	
	@Override
	public String toString() {
		String internalValues = new String(
				"NoteVelocityToggleData" + "\n" +
				"Option ID   : " + data[1] + "\n" +
				"SW Behaviour: " + data[2] + "\n" +
				"SW Option   : " + data[3] + "\n" + 
				"MIDI Channel: " + data[4] + "\n" +
				"MIDI Pitch  : " + data[5] + "\n" +
				"Velocity    : " + data[6] + "\n"
			   );

		return internalValues;
	}

}
