package dataContainers;

public class DisabledControllerData extends DataStructure {

	private static final long serialVersionUID = -989356317682804322L;

	final private int ID = 0xEF;
	
	private int data[] = {
						  0xF0, //Start Byte
						  ID, //Option ID
						  0xFF	//End Byte
						 };
	
	public DisabledControllerData(){}
	
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

	@Override
	public String toString() {
		String internalValues = new String(
											"DisabledController" + "\n" +
											"Option ID   : " + data[1] 
										  );

		return internalValues;
	}

}
