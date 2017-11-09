package dataContainers;

public class SceneData extends DataStructure {

	private static final long serialVersionUID = 7718932706279050664L;
	
	final private int ID = 0xEE;
	
	private int data[] = {
							0xF0, 	//Start Byte
							ID, 	//ID
							0x00,	//Option Specifies Amount of program channels
							0x00,	//MIDI Channel One
							0x00,	//Bank Select One 
							0x00,	//Program Select One
							0x00,	//MIDI Channel Two
							0x00,	//Bank Select Two
							0x00,	//Program Select Two
							0x00, 	//MIDI Channel Three 
							0x00,	//Bank Select Three
							0x00,	//Program Select Three
							0x00,	//MIDI Channel Four
							0x00,	//Bank Select Four
							0x00,	//Program Select Four
							0xFF	//End Byte
						 };
	
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
	
	public void setOption(int option){
		if(option >= 0 && option <= 4){
			data[2] = option;
		}
	}
		
	public void setChannel(int channel, int number){
		if(channel > 0 && channel <= 16 && number > 0 && number <= 4){	
			switch(number){
				case 1: 
					data[3] = channel;
					break;
				case 2:
					data[6] = channel;
					break;
				case 3:
					data[9] = channel;
					break;
				case 4:
					data[12] = channel;
					break;					
			}
		}
	}
	
	public int getChannel(int number){
		int channel = 0;
		
		if(number > 0 && number <= 4){
			switch(number){
				case 1:
					channel = data[3];
					break;
				case 2:
					channel = data[6];
					break;
				case 3:
					channel = data[9];
					break;
				case 4:
					channel = data[12];
					break;
			}
		}
		return channel;
	}
	
	public void setBank(int bank, int number){
		if(bank >= 0 && bank <= 127 && number > 0 && number <= 4){	
			switch(number){
				case 1:
					data[4] = bank;
					break;
				case 2:
					data[7] = bank;
					break;
				case 3:
					data[10] = bank;
					break;
				case 4:
					data[13] = bank;
					break;
			}
		}	
	}
	
	public int getBank(int number){
		int bank = 0;
		
		if(number > 0 && number <= 4){
			switch(number){
				case 1:
					bank = data[4];
					break;
				case 2:
					bank = data[7];
					break;
				case 3:
					bank = data[10];
					break;
				case 4:
					bank = data[13];
					break;
			}
		}
		return bank;
	}
	
	public void setProgram(int program, int number){
		if(program >= 0 && program <= 127 && number > 0 && number <= 4){
			switch(number){
				case 1:
					data[5] = program;
					break;
				case 2:
					data[8] = program;
					break;
				case 3:
					data[11] = program;
					break;
				case 4:
					data[14] = program;
					break;
			}
		}
	}
	
	public int getProgram(int number){
		int program = 0;
		
		if(number > 0 && number <= 4){
			switch(number){
				case 1:
					program = data[5];
					break;
				case 2:
					program = data[8];
					break;
				case 3:
					program = data[11];
					break;
				case 4:
					program = data[14];
					break;
			}
		}
		return program;
	}
	

	@Override
	public String toString() {
		String internalValues = new String(
											"SceneData" + "\n" +
											"Option ID     : " + data[1] + "\n" +
											"Option        : " + data[2] + "\n" +
				   
				   							"MIDI Channel 1: " + data[3] + "\n" +
				   							"Bank Select  1: " + data[4] + "\n" +
				   							"Program      1: " + data[5] + "\n" +
				   
				   							"MIDI Channel 2: " + data[6] + "\n" +
				   							"Bank Select  2: " + data[7] + "\n" +
				   							"Program 	 2: " + data[8] + "\n" +
				   
				   							"MIDI Channel 3: " + data[9] + "\n" +
				   							"Bank Select  3: " + data[10] + "\n" +
				   							"Program 	 3: " + data[11] + "\n" +	 
				   
				   							"MIDI Channel 4: " + data[12] + "\n" +
				   							"Bank Select  4: " + data[13] + "\n" +
				   							"Program	 	 4: " + data[14] + "\n" 
											);
		return internalValues;
	}
}
