package helpers;

public class MidiPitchConverter {

	final private String[] pitchTable = {
										"C ",
										"C#",
										"D ",
										"D#",
										"E ",
										"F ",
										"F#",
										"G ",
										"G#",
										"A ",
										"A#",
							  			"B "
										};
	
	public String convertPitch(int pitch){
		int index = pitch % 12;
		int octave = Math.floorDiv(pitch, 12) - 2;
		String note;
		
		if(octave >= 0){
			note = new String(pitchTable[index] + " " +  octave + " ");
		} else {	
			note = new String(pitchTable[index] + octave + " ");
		}
		
		return note;
	}
}
