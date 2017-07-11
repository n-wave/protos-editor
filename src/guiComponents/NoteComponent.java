package guiComponents;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;

import dataContainers.DataStructure;
import dataContainers.NoteVelocityData;
import helpers.MidiPitchConverter;


public class NoteComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	private Spinner channelSpinner;
	private Spinner pitchSpinner;
	private Spinner velocitySpinner;
	
	private Label activePitchLabel;
	private Button velocityCheckButton;
	
	private NoteVelocityData noteVelocityData;
	String name = new String("NoteComponent");
	
	private MidiPitchConverter midiPitchConverter = new MidiPitchConverter();
	
	public NoteComponent(Composite parent, int style) {
		super(parent, SWT.NONE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));
		
		initializeChannelSelection();
		initializePitchSelection();
		initializeVelocitySelection();
		
		initializeListeners();
	}

	public NoteComponent(Composite parent, int style, DataStructure data) {
		super(parent, SWT.NONE);
		
		initializeDataStructure(data);

		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));
		
		initializeChannelSelection();
		initializePitchSelection();
		initializeVelocitySelection();
		
		initializeListeners();
		
		//System.out.println(this.toString());
	}

	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void initializeChannelSelection(){
		int channel = noteVelocityData.getChannel();
		
		Label channelLabel = new Label(this, SWT.NONE);
		channelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		channelLabel.setText("Channel");

		channelSpinner = new Spinner(this, SWT.BORDER);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		channelSpinner.setSelection(channel);

	}
	
	private void initializePitchSelection(){
		int pitch = noteVelocityData.getPitch();
		String note = midiPitchConverter.convertPitch(pitch);
		
		new Label(this, SWT.NONE);
		Label pitchLabel = new Label(this, SWT.NONE);
		pitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		pitchLabel.setText("Pitch");
		
		pitchSpinner =  new Spinner(this, SWT.BORDER);
		pitchSpinner.setToolTipText("MIDI Pitch");
		pitchSpinner.setMaximum(127);
		pitchSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		pitchSpinner.setSelection(pitch);

		activePitchLabel = new Label(this, SWT.NONE);
		activePitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		activePitchLabel.setText(note);
	}
	
	private void initializeVelocitySelection(){
		int velocity = noteVelocityData.getVelocity();
		boolean option = noteVelocityData.getVelocityOption();

		Label velocityLabel = new Label(this, SWT.NONE);
		velocityLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		velocityLabel.setText("Velocity");
								
		velocitySpinner = new Spinner(this, SWT.BORDER);
		velocitySpinner.setToolTipText("Static Velocity/Max Velocity");
		velocitySpinner.setMaximum(127);
		velocitySpinner.setSelection(velocity);
		velocitySpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		
		velocityCheckButton = new Button(this, SWT.CHECK);
		velocityCheckButton.setFont(SWTResourceManager.getFont("Noto Sans", 12, SWT.NORMAL));
		velocityCheckButton.setToolTipText("Link Velocity to Sensor Input");
	}
	
	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof NoteVelocityData){
			 noteVelocityData = (NoteVelocityData)data;
		 }	else {
			 System.err.println("Wrong DataStructure Component suplied");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in NoteVelocityComponent");
			e.printStackTrace(System.err);
		}		
	}	
	
	public void enableLinkOption(boolean option){
		velocityCheckButton.setEnabled(option);
		velocityCheckButton.setVisible(option);
	}
	
	public String toString(){
		String internalValues = new String("/** GuiComponent **/" +
				"\n" +
				name + 
				"\n");

		try{
			internalValues = internalValues + "\n" + noteVelocityData.toString();
		} catch(Exception e){
			System.err.println();
		}

		return internalValues;
	}
	
	
	private void initializeListeners(){
		channelSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = channelSpinner.getSelection();
				noteVelocityData.setChannel(value);
			}
		});
		
		pitchSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = pitchSpinner.getSelection();
				String note = midiPitchConverter.convertPitch(value);
				noteVelocityData.setPitch(value);
				activePitchLabel.setText(note);
			}
		});
		
		velocitySpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = velocitySpinner.getSelection();
				noteVelocityData.setVelocity(value);
			}
		});
		
		
		velocityCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e){
				boolean value = velocityCheckButton.getSelection();
				noteVelocityData.setVelocityOption(value);
			}
		});
	}
}
