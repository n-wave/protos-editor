package dataContainers;

import java.io.Serializable;

public abstract class DataStructure implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8455523422189874881L;

	abstract public void loadValues(int []defaultData);
	abstract public int[] getData();
	abstract public String toString();
}
