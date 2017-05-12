package osgi.gui.ob.tab;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.gui.ob.PluginObjectBrowserTab;
import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.progress.ProgressEvent;
import com.dscsag.plm.spi.interfaces.progress.ProgressTracker;
import com.dscsag.plm.spi.interfaces.rfc.RfcResult;
import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;

import javax.swing.*;

public class MyTab implements PluginObjectBrowserTab<RfcResult>
{
  private JLabel label;
  private ECTRService ectrService;

  MyTab(ECTRService ectrService)
  {
    label = new JLabel();
    this.ectrService=ectrService;
  }

  @Override
  public String getUniqueIdentifier()
  {
    return "org.dogoodthings.osgi.ob.tab.for.mat.weight";
  }

  @Override
  public String getName()
  {
    return "Net Weight";
  }

  @Override
  public JComponent getDisplayComponent()
  {
    return label;
  }

  @Override
  public RfcResult fetchData(PlmObjectKey plmObjectKey, ProgressTracker tracker)
  {
    ectrService.getPlmLogger().trace("dataFetcher...");
    tracker.updateProgress(new ProgressEvent("calling SAP..."));
    RfcCallBuilder callBuilder = new RfcCallBuilder("BAPI_MATERIAL_GET_DETAIL");
    callBuilder.setInputParameter("MATERIAL",plmObjectKey.getKey());
    return ectrService.getRfcExecutor().execute(callBuilder.toRfcCall());
  }

  @Override
  public void updateUI(RfcResult executeResult)
  {
    ectrService.getPlmLogger().trace("updateUI...");
    String nettWeight = executeResult.getExportParameter("MATERIAL_GENERAL_DATA").getStructure().getFieldValue("NET_WEIGHT");
    String wu = executeResult.getExportParameter("MATERIAL_GENERAL_DATA").getStructure().getFieldValue("UNIT_OF_WT");
    label.setText(nettWeight + " " + wu);
  }

  @Override
  public void clear()
  {
    label.setIcon(null);
    label.setText("");
  }
}