package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;

import dataContainers.DataStructure;
import dataContainers.PitchBendData;


public class PitchBendComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private Spinner channelSpinner;
	private PitchBendData pitchBendData;
	private String name = new String("PitchBendComponent");
	
	public PitchBendComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));
		
		initializeChannelSelection();
		
		initializeListeners();
	}

	public PitchBendComponent(Composite parent, int style, DataStructure data) {
		super(parent, style);
		
		initializeDataStructure(data);
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));
		
		initializeChannelSelection();
		
		initializeListeners();
		
		//System.out.println(this.toString());
		
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof PitchBendData){
			 pitchBendData = (PitchBendData)data;
		 }	else {
			 System.err.println("Wrong DataStructure Component suplied");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in PitchBendComponent");
			e.printStackTrace(System.err);
		}		
	}

	public String toString(){
		String internalValues = new String("/** GuiComponent **/" +
											"\n" +
											name + 
											"\n");

		try{
			internalValues = internalValues + "\n" + pitchBendData.toString();
		} catch(Exception e){
			System.err.println();
		}

		return internalValues;
	}
	
	private void initializeChannelSelection()
	{
		int channel = pitchBendData.getChannel();
		
		Label ChannelLabel = new Label(this, SWT.NONE);
		ChannelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		ChannelLabel.setText("Channel");
		
		channelSpinner = new Spinner(this, SWT.BORDER);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setSelection(channel);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
	}
	
	private void initializeListeners(){
		channelSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = channelSpinner.getSelection();
				pitchBendData.setChannel(value);
			}
		});
	}
}
