package dataContainers;

public class NoteVelocityData extends DataStructure {

	private static final long serialVersionUID = -4330016197216724246L;

	final private int ID = 0xE1;
	
	/*data array for storing and copying data into it from xml*/
	private int[]data = {
							0xF0,	//0 Start
							ID,		//1 Option ID
							1,     	//2 MIDI Channel
							60, 	//3 MIDI pitch
							100,	//4 MIDI velocity
							0,		//5 Static/Link option
							0xFF	//6 End Bit
						 };
	
	public NoteVelocityData(){
		//System.out.println(this.toString());
	}
	
	public int[] getData(){
		return data;
	}
	
	@Override 
	public void loadValues(int []defaultData){
		if(defaultData[1] == data[1]){
			System.arraycopy(defaultData, 0, data, 0, defaultData.length);
		}	
	}
	
	/** MIDI Channel **/
	public void setChannel(int channel){
		if(channel >0 && channel <= 16){
			data[2] = channel;
		}
	}
	
	public int getChannel(){
		return data[2];
	}
	
	/** Pitch **/
	public void setPitch(int pitch){
		if(pitch >= 0 && pitch <=127){
			data[3] = pitch;
		}
	}
	
	public int getPitch(){
		return data[3];
	}
	
	/** Velocity **/
	public void setVelocity(int velocity){
		if(velocity >= 0  && velocity <= 127){
			data[4] = velocity;
		}
	}
	
	public int getVelocity(){
		return data[4];
	}
	
	/** Velocity Option **/
	public void setVelocityOption(boolean option){
		data[5] = option ? 1 : 0;
	}
	
	public boolean getVelocityOption(){
		return (data[5] != 0);
	}

	public String toString(){
		String internalValues = new String(
										"NoteVelocityData" + "\n" +
										"Option ID   : " + data[1] + "\n" +
										"MIDI Channel: " + data[2] + "\n" +
										"MIDI Pitch  : " + data[3] + "\n" +
										"Velocity    : " + data[4] + "\n" +
										"Link/Static : " + data[5] + "\n"
									   );
		
		return internalValues;
	}	
}
