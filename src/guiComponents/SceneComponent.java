package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import dataContainers.SceneData;
import dataContainers.DataStructure;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class SceneComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	
	/** First programChange block **/
	
	Label channelOneLabel;
	Label bankOneLabel;
	Label programOneLabel;
	
	Spinner channelOneSpinner;
	Spinner bankOneSpinner;
	Spinner programOneSpinner;
	
	/** Second ProgramChange Block **/
	
	Label channelTwoLabel;
	Label bankTwoLabel;
	Label programTwoLabel;
	
	Spinner channelTwoSpinner;
	Spinner bankTwoSpinner;
	Spinner programTwoSpinner;
	
	/** Third ProgramChange Block **/
	
	Label channelThreeLabel;
	Label bankThreeLabel;
	Label programThreeLabel;
	
	Spinner channelThreeSpinner;
	Spinner bankThreeSpinner;
	Spinner programThreeSpinner;
	
	/** Fourth programChange Block **/
	
	Label channelFourLabel;
	Label bankFourLabel;
	Label programFourLabel;
	
	Spinner channelFourSpinner;
	Spinner bankFourSpinner;
	Spinner programFourSpinner;
	
	
	private SceneData sceneData;
	private String name = new String("SceneComponent");
	
	public SceneComponent(Composite parent, int style) {
		super(parent, style);
			
		initializeGui();
		initializeListeners();
	}
	
	public SceneComponent(Composite parent, int style, DataStructure data){
		super(parent, style);
	
		initializeDataStructure(data);
		initializeGui();
		initializeParameters();
		initializeListeners();
	}
	
	private void initializeGui()
	{	
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		setLayout(new GridLayout(7, false));
		
		channelOneLabel = new Label(this, SWT.NONE);
		channelOneLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		channelOneLabel.setText("Channel");
		
		channelOneSpinner = new Spinner(this, SWT.BORDER);
		channelOneSpinner.setMaximum(16);
		channelOneSpinner.setMinimum(1);
		channelOneSpinner.setToolTipText("Channel One");
		new Label(this, SWT.NONE);
		
		Label horizontalSeparator = new Label(this, SWT.NONE);
		horizontalSeparator.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 3));
		new Label(this, SWT.NONE);
		
		channelTwoLabel = new Label(this, SWT.NONE);
		channelTwoLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		channelTwoLabel.setText("Channel");
		
		channelTwoSpinner = new Spinner(this, SWT.BORDER);
		channelTwoSpinner.setMaximum(16);
		channelTwoSpinner.setMinimum(1);
		channelTwoSpinner.setToolTipText("Channel Two");
		
		bankOneLabel = new Label(this, SWT.NONE);
		bankOneLabel.setText("Bank");
		bankOneLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
	    bankOneSpinner = new Spinner(this, SWT.BORDER);
		bankOneSpinner.setMaximum(127);
		bankOneSpinner.setToolTipText("Bank One");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		bankTwoLabel = new Label(this, SWT.NONE);
		bankTwoLabel.setText("Bank");
		bankTwoLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		bankTwoSpinner = new Spinner(this, SWT.BORDER);
		bankTwoSpinner.setMaximum(127);
		bankTwoSpinner.setToolTipText("Bank Two");
		
		programOneLabel = new Label(this, SWT.NONE);
		programOneLabel.setText("Program");
		programOneLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		programOneSpinner = new Spinner(this, SWT.BORDER);
		programOneSpinner.setMaximum(127);
		programOneSpinner.setToolTipText("Program Change One");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		programTwoLabel = new Label(this, SWT.NONE);
		programTwoLabel.setText("Program");
		programTwoLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		programTwoSpinner = new Spinner(this, SWT.BORDER);
		programTwoSpinner.setMaximum(127);
		programTwoSpinner.setToolTipText("Program Change Two");
		
		Label separatorLabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_separatorLabel = new GridData(SWT.LEFT, SWT.TOP, false, false, 7, 1);
		gd_separatorLabel.heightHint = 7;
		gd_separatorLabel.widthHint = 357;
		separatorLabel.setLayoutData(gd_separatorLabel);
		
		channelThreeLabel = new Label(this, SWT.NONE);
		channelThreeLabel.setText("Channel");
		channelThreeLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		channelThreeSpinner = new Spinner(this, SWT.BORDER);
		channelThreeSpinner.setMaximum(16);
		channelThreeSpinner.setMinimum(1);
		channelThreeSpinner.setToolTipText("Channel Three");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		channelFourLabel = new Label(this, SWT.NONE);
		channelFourLabel.setText("Channel");
		channelFourLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		channelFourSpinner = new Spinner(this, SWT.BORDER);
		channelFourSpinner.setMinimum(1);
		channelFourSpinner.setMaximum(16);
		channelFourSpinner.setToolTipText("Channel Four");
		
		bankThreeLabel = new Label(this, SWT.NONE);
		bankThreeLabel.setText("Bank");
		bankThreeLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		bankThreeSpinner = new Spinner(this, SWT.BORDER);
		bankThreeSpinner.setMaximum(127);
		bankThreeSpinner.setToolTipText("Bank Three");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		bankFourLabel = new Label(this, SWT.NONE);
		bankFourLabel.setText("Bank");
		bankFourLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		bankFourSpinner = new Spinner(this, SWT.BORDER);
		bankFourSpinner.setMaximum(127);
		bankFourSpinner.setToolTipText("Bank Four");
		
		programThreeLabel = new Label(this, SWT.NONE);
		programThreeLabel.setText("Program");
		programThreeLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		programThreeSpinner = new Spinner(this, SWT.BORDER);
		programThreeSpinner.setMaximum(127);
		programThreeSpinner.setToolTipText("Program Change Three");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
	    programFourLabel = new Label(this, SWT.NONE);
		programFourLabel.setText("Program");
		programFourLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		programFourSpinner = new Spinner(this, SWT.BORDER);
		programFourSpinner.setMaximum(127);
		programFourSpinner.setToolTipText("Program Change Four");
		
	}

	private void initializeParameters()
	{
		channelOneSpinner.setSelection(sceneData.getChannel(1));
		bankOneSpinner.setSelection(sceneData.getBank(1));
		programOneSpinner.setSelection(sceneData.getProgram(1));
		
		channelTwoSpinner.setSelection(sceneData.getChannel(2));
		bankTwoSpinner.setSelection(sceneData.getBank(2));
		programTwoSpinner.setSelection(sceneData.getProgram(2));
		

		channelThreeSpinner.setSelection(sceneData.getChannel(3));
		bankThreeSpinner.setSelection(sceneData.getBank(3));
		programThreeSpinner.setSelection(sceneData.getProgram(3));
		
		channelFourSpinner.setSelection(sceneData.getChannel(4));
		bankFourSpinner.setSelection(sceneData.getBank(4));
		programFourSpinner.setSelection(sceneData.getProgram(4));
	}
	
	
	public void enableProgramChangeBlock(int option)
	{
		switch(option){
			case 0:
				setEnabledProgramChangeBlockOne(false);
				setEnabledProgramChangeBlockTwo(false);
				setEnabledProgramChangeBlockThree(false);
				setEnabledProgramChangeBlockFour(false);
				break;
			case 1:
				setEnabledProgramChangeBlockOne(true);
				setEnabledProgramChangeBlockTwo(false);
				setEnabledProgramChangeBlockThree(false);
				setEnabledProgramChangeBlockFour(false);
				break;
			case 2: 
				setEnabledProgramChangeBlockOne(true);
				setEnabledProgramChangeBlockTwo(true);
				setEnabledProgramChangeBlockThree(false);
				setEnabledProgramChangeBlockFour(false);
				break;
			case 3:
				setEnabledProgramChangeBlockOne(true);
				setEnabledProgramChangeBlockTwo(true);
				setEnabledProgramChangeBlockThree(true);
				setEnabledProgramChangeBlockFour(false);
				break;
			case 4:
				setEnabledProgramChangeBlockOne(true);
				setEnabledProgramChangeBlockTwo(true);
				setEnabledProgramChangeBlockThree(true);
				setEnabledProgramChangeBlockFour(true);
				break;
		}
	}
	
	private void setEnabledProgramChangeBlockOne(boolean enabled)
	{
		channelOneLabel.setEnabled(enabled);
		bankOneLabel.setEnabled(enabled);
		programOneLabel.setEnabled(enabled);
		
		channelOneSpinner.setEnabled(enabled);
		bankOneSpinner.setEnabled(enabled);
		programOneSpinner.setEnabled(enabled);	
	}
	
	private void setEnabledProgramChangeBlockTwo(boolean enabled)
	{
		channelTwoLabel.setEnabled(enabled);
		bankTwoLabel.setEnabled(enabled);
		programTwoLabel.setEnabled(enabled);
		
		channelTwoSpinner.setEnabled(enabled);
		bankTwoSpinner.setEnabled(enabled);
		programTwoSpinner.setEnabled(enabled);
	}
	
	private void setEnabledProgramChangeBlockThree(boolean enabled)
	{
		channelThreeLabel.setEnabled(enabled);
		bankThreeLabel.setEnabled(enabled);
		programThreeLabel.setEnabled(enabled);
		
		channelThreeSpinner.setEnabled(enabled);
		bankThreeSpinner.setEnabled(enabled);
		programThreeSpinner.setEnabled(enabled);		
	}
	
	private void setEnabledProgramChangeBlockFour(boolean enabled)
	{
		channelFourLabel.setEnabled(enabled);
		bankFourLabel.setEnabled(enabled);
		programFourLabel.setEnabled(enabled);
		
		channelFourSpinner.setEnabled(enabled);
		bankFourSpinner.setEnabled(enabled);
		programFourSpinner.setEnabled(enabled);
	}
	
	private void initializeDataStructure(DataStructure data){
		try{
			if(data instanceof SceneData){
				sceneData = (SceneData)data;
				//optionIndex = sceneData.getOptionIndex
			} else {
				System.err.println("Wrong DataStructure supplied");
			}
			
		} catch(Exception e){
			System.err.println("Error ocurred in SceneComponent");
			e.printStackTrace(System.err);
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override 
	public String toString(){
		String internalValues = new String("/** SceneComponent **/" +
											"\n" +
											name +
											"\n");		
		
		try{
			internalValues += "\n" + sceneData.toString();
		} catch(Exception e){
			System.err.println("Error ocurred in SceneComponent");
			e.printStackTrace(System.err);
		}
		return internalValues;
	}
	
	private void initializeListeners()
	{
		/** Program Change Block One **/
		
		channelOneSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int channel = channelOneSpinner.getSelection();
				sceneData.setChannel(channel, 1);
			}
		});
		
		bankOneSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int bank = bankOneSpinner.getSelection();
				System.out.println(bank);
				sceneData.setBank(bank, 1);
			}
		});
		
		programOneSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int program = programOneSpinner.getSelection();
				sceneData.setProgram(program, 1);
			}
		});
		
		/** Program Change Block Two **/
		
		channelTwoSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int channel = channelTwoSpinner.getSelection();
				sceneData.setChannel(channel, 2);					
			}
		});
		
		bankTwoSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int bank = bankTwoSpinner.getSelection();
				sceneData.setBank(bank, 2);
			}
		});
		
		programTwoSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int program = programTwoSpinner.getSelection();
				sceneData.setProgram(program, 2);
			}
		});
		
		/** Program Change Block Three **/
		
		channelThreeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int channel = channelThreeSpinner.getSelection();
				sceneData.setChannel(channel, 3);
			}
		});
		
		bankThreeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int bank = bankThreeSpinner.getSelection();
				sceneData.setBank(bank, 3);
			}
		});
		
		programThreeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int program = programThreeSpinner.getSelection();
				sceneData.setProgram(program, 3);
			}
		});
		
		/** Program Change Block Four **/
		
		channelFourSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int channel = channelFourSpinner.getSelection();
				sceneData.setChannel(channel, 4);
			}
		});
		
		bankFourSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int bank = bankFourSpinner.getSelection();
				sceneData.setBank(bank, 4);
			}
		});
		
		programFourSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int program = programFourSpinner.getSelection();
				sceneData.setProgram(program, 4);
			}
		});
	}
}
