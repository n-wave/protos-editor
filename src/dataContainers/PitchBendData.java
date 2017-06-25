package dataContainers;

public class PitchBendData extends DataStructure {

	private static final long serialVersionUID = -5529600122414872842L;

	final private int ID = 0xE3;
	
	private int[]data = {
							0xF0,	//0 Start Bit
							ID,		//1	Option ID
							1,		//2	MIDI Channel
							0xFF	//3 End Bit
						};
	
	public PitchBendData(){
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

	/** Channel **/
	public void setChannel(int channel){
		if(channel > 0 && channel <= 16){
			data[2] = channel;
		}
	}
	
	public int getChannel(){
		return data[2];
	}
	
	@Override
	public String toString(){
		String internalValues = new String(
										"PitchBendData" + "\n" +
										"Option ID   : " + data[1] + "\n" +
										"MIDI Channel: " + data[2] + "\n"
										);
		return internalValues;
	}
}
