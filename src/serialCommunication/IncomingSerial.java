package serialCommunication;

public abstract class IncomingSerial {
	abstract public int getBlockSize();
	abstract public int compare(byte data[]);
	abstract public String getString(int state);
}
