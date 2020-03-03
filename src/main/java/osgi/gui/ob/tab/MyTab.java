package osgi.gui.ob.tab;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.gui.ob.PluginObjectBrowserTab;
import com.dscsag.plm.spi.interfaces.objects.ObjectData;
import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.progress.ProgressEvent;
import com.dscsag.plm.spi.interfaces.progress.ProgressTracker;
import com.dscsag.plm.spi.interfaces.rfc.RfcResult;
import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MyTab implements PluginObjectBrowserTab<RfcResult>
{
  private JLabel label;
  private JPanel panel;
  private JTextField no1;
  private String test;
  private ECTRService ectrService;
  

  MyTab(ECTRService ectrService)
  {
	  
    
    panel = new JPanel();
    
    label = new JLabel("test1");
    panel.add(label);
    
    Button b1 = new Button("Button");
    panel.add(b1);
   
    no1 = new JTextField(30);
    panel.add(no1);
	panel.setVisible(true);
	
	b1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			selectionButtonPressed();
		}
	} );
    
    this.ectrService=ectrService;
  }
  
  public void selectionButtonPressed() 
  {
	test = no1.getText();
	RfcCallBuilder callBuilder = new RfcCallBuilder("Z_FIS_FIELD_MAINTANANCE");	
	callBuilder.setInputParameter("ZZ_FIELD1",test);
	ectrService.getRfcExecutor().execute(callBuilder.toRfcCall());

	//JOptionPane.showMessageDialog(panel, test, "Test", JOptionPane.INFORMATION_MESSAGE, null);
  }

  @Override
  public String getUniqueIdentifier()
  {
    return "org.dogoodthings.osgi.ob.tab.for.doc.data";
  }

  @Override
  public String getName()
  {
    return "FIS";
  }

  @Override
  public JComponent getDisplayComponent()
  {
    return panel;   
  }
  
  public void DocumentData(ObjectData objData)
  {
	   
  }
  
  public String getType()
  {
	return getType();
  }

@Override
public void clear() {
	// TODO Auto-generated method stub
	
}

@Override
public RfcResult fetchData(PlmObjectKey arg0, ProgressTracker arg1) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void updateUI(RfcResult arg0) {
	// TODO Auto-generated method stub
	
}
  

  /*@Override
  public RfcResult fetchData(PlmObjectKey plmObjectKey, ProgressTracker tracker)
  {
	test = no1.getText();
	//JOptionPane.showMessageDialog(panel, test, "Test", JOptionPane.INFORMATION_MESSAGE, null);
    ectrService.getPlmLogger().trace("dataFetcher...");
    tracker.updateProgress(new ProgressEvent("calling SAP..."));
    RfcCallBuilder callBuilder = new RfcCallBuilder("Z_FIS_FIELD_MAINTANANCE");	
    callBuilder.setInputParameter("ZZ_FIELD1",test);
    return ectrService.getRfcExecutor().execute(callBuilder.toRfcCall());
  }

 /* @Override
  public void updateUI(RfcResult executeResult)
  {
    ectrService.getPlmLogger().trace("updateUI...");
    String lab = executeResult.getExportParameter("DOCUMENTDATA").getStructure().getFieldValue("LABORATORY");
    label.setText(lab);
  }

  @Override
  public void clear()
  {
    label.setIcon(null);
    label.setText("Test");
    panel.add(label);
  }*/
}