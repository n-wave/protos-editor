package dataContainers;

public class PitchBendNoteData extends DataStructure{

	private static final long serialVersionUID = 1528940360414532299L;

	final private int ID = 0xE4;
	
	private int[] data = {	
							0xF0,	//0	Start Bit
							ID, 	//1 Option ID
							1,		//2 MIDI Channel
							60,		//3 Base Pitch
							100,	//4 Velocity
							0xFF	//5 End Bit
						 };
	
	public PitchBendNoteData(){
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
		if(pitch >= 0 && pitch <= 127){
			data[3] = pitch;
		}
	}
	
	public int getPitch(){
		return data[3];
	}
	
	/** Velocity **/
	public void setVelocity(int velocity){
		if(velocity >= 0 && velocity <= 127){
			data[4] = velocity;
		}
	}
	
	public int getVelocity(){
		return data[4];
	}
	
	public String toString(){
		String internalValues = new String(
										   "PitchBendNoteData" + "\n" +
										   "Option ID   : " + data[1] + "\n" +
										   "MIDI Channel: " + data[2] + "\n" +
										   "Base Pitch  : " + data[3] + "\n" +
										   "Velocity    : " + data[4] +  "\n"
										   );
		
		return internalValues;
	}
}
