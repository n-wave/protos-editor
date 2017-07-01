package serialCommunication;

public abstract class SerialBlocks {
	
	abstract public byte[] getWriteBlock(String option);
	abstract public String[] getOptionList();
	
	abstract public int getBlockSize();
	abstract public int compare(byte data[]);
	abstract public String getString(int state);
}
