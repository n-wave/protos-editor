package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import dataContainers.DataStructure;
import dataContainers.DisabledControllerData;

import org.eclipse.swt.widgets.Label;

public class DisabledControllerComponent extends Composite {

	
	private DisabledControllerData disabledControllerData;
	private String name = new String("DisabledControllerComponent");
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DisabledControllerComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		setBounds(146, 35, 490, 355);
		setLayout(null);
		
		
		
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		lblNewLabel.setBounds(10, 10, 128, 15);
		lblNewLabel.setText("Disabled");
	}

	public DisabledControllerComponent(Composite parent, int style, DataStructure data){
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		setBounds(146, 35, 490, 355);
		setLayout(null);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		lblNewLabel.setBounds(10, 10, 128, 15);
		lblNewLabel.setText("Disabled");
		
		initializeDataStructure(data);
	}
	
	private void initializeDataStructure(DataStructure data){
		try{
			if(data instanceof DisabledControllerData){
				disabledControllerData = (DisabledControllerData)data;
			} else {
				System.err.println("Wrong DataStructure Object supplied in DisabledControllerComponent::initializeDataStructure(DataStructure)");
			}
			
			
		} catch(Exception e){
			System.err.println("Error occured in DisabledControllerComponent::initializeDataStructure(DataStructure)");
			e.printStackTrace(System.err);
		}
	}
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	@Override 
	public String toString(){
		String internalValues = new String("/** GuiComponent **/" +
											"\n" +
											name + 
											"\n");
			
		try{
			internalValues += "\n" + disabledControllerData.toString();
		} catch(Exception e){
			System.err.println("Error ocurred in DisabledControllerComponent::toString()");
			e.printStackTrace(System.err);
		}
		
		return internalValues;
	}
}
