package osgi.gui.ob.tab;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.gui.ob.PluginObjectBrowserTab;
import com.dscsag.plm.spi.interfaces.gui.ob.Tracker;
import com.dscsag.plm.spi.interfaces.objects.PlmObjectKey;
import com.dscsag.plm.spi.interfaces.rfc.RfcResult;
import com.dscsag.plm.spi.rfc.builder.RfcCallBuilder;

import javax.swing.*;
import java.util.Collections;
import java.util.Map;

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
  public String getName()
  {
    return "my OSGi tab";
  }

  @Override
  public JComponent getDisplayComponent()
  {
    return label;
  }

  @Override
  public RfcResult dataFetcher(PlmObjectKey plmObjectKey, Tracker tracker)
  {
    RfcCallBuilder callBuilder = new RfcCallBuilder("BAPI_MATERIAL_GET_DETAIL");
    callBuilder.setInputParameter("MATERIAL",plmObjectKey.getKey());
    return ectrService.getRfcExecutor().execute(callBuilder.toRfcCall());
  }

  @Override
  public void updateUI(RfcResult executeResult)
  {
    String grossWeight = executeResult.getExportParameter("MATERIAL_GENERAL_DATA").getStructure().getFieldValue("GROSS_WT");
    label.setText(grossWeight);
  }

  @Override
  public void clear()
  {
    label.setIcon(null);
    label.setText("");
  }
}