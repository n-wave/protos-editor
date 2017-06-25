package dataContainers;

public class ProgramChangeData extends DataStructure {
	
	private static final long serialVersionUID = -8510360007290257687L;

	private int ID = 0xE0;
	
	private int[] data = {
							0xF0,	//0 Start
							ID,		//1 Option ID
							1,		//2 MIDI Channel
							0, 		//3 Bank select
							0,		//4 Program Change
							0xFF	//5	End Bit
						 };
	
	public ProgramChangeData(){
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
	
	/** Bank **/ 
	public void setBank(int bank){
		if(bank >= 0 && bank <= 127){
			data[3] = bank;
		}
	}
	
	public int getBank(){
		return data[3];
	}
	
	/** Program **/
	public void setProgram(int program){
		if(program >= 0 && program <= 127){
			data[4] = program;
		}
	}
	
	public int getProgram(){
		return data[4];
	}
	
	public String toString(){
		String internalValues = new String(
										   "ProgramChangeData" + "\n" +
										   "Option ID   : " + data[1] + "\n" +
										   "MIDI Channel: " + data[2] + "\n" +
										   "Bank Select : " + data[3] + "\n" +
										   "Program     : " + data[4] +  "\n"
										   );
		
		return internalValues;
	}
}
