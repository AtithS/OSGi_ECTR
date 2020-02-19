package osgi.gui.ob.tab;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.gui.ob.PluginObjectBrowserTab;
import com.dscsag.plm.spi.interfaces.objects.ObjectData;
import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.objects.doc.DocumentData;
import com.dscsag.plm.spi.interfaces.progress.ProgressEvent;
import com.dscsag.plm.spi.interfaces.progress.ProgressTracker;
import com.dscsag.plm.spi.interfaces.rfc.RfcResult;
import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;

import javax.swing.*;

public class MyTab implements PluginObjectBrowserTab<RfcResult>
{
  private JLabel label;
  private JPanel panel;
  private ECTRService ectrService;

  MyTab(ECTRService ectrService)
  {
    label = new JLabel("test1");
    panel = new JPanel();
    
    panel.add(label);
   
    JTextField no1 = new JTextField(30);
    panel.add(no1);
	panel.setVisible(true);  
    
    this.ectrService=ectrService;
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
  public RfcResult fetchData(PlmObjectKey plmObjectKey, ProgressTracker tracker)
  {
    ectrService.getPlmLogger().trace("dataFetcher...");
    tracker.updateProgress(new ProgressEvent("calling SAP..."));
    RfcCallBuilder callBuilder = new RfcCallBuilder("/DSCSAG/DOC_GETDETAIL2");
    callBuilder.setInputParameter("DOCUMENTTYPE",getType());
    callBuilder.setInputParameter("DOCUMENTNUMBER",plmObjectKey.getKey());
    callBuilder.setInputParameter("DOCUMENTPART",plmObjectKey.getKey());
    callBuilder.setInputParameter("DOCUMENTVERSION",plmObjectKey.getKey());
    callBuilder.setInputParameter("GETCLASSIFICATION","X");
    return ectrService.getRfcExecutor().execute(callBuilder.toRfcCall());
  }

  @Override
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
    label.setText("");
  }
}