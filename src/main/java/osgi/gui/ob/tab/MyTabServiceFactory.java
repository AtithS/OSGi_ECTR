package osgi.gui.ob.tab;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.gui.ob.PluginObjectBrowserTab;
import com.dscsag.plm.spi.interfaces.gui.ob.PluginObjectBrowserTabService;

import java.util.Collections;
import java.util.List;

public class MyTabServiceFactory implements PluginObjectBrowserTabService
{
  private List<PluginObjectBrowserTab<?>> tabProvider;

  public MyTabServiceFactory(ECTRService ectrService)
  {
    tabProvider = Collections.singletonList(new MyTab(ectrService));
  }

  @Override
  public List<PluginObjectBrowserTab<?>> getTabsFor(String type)
  {
    return "MARA".equals(type)? tabProvider: Collections.emptyList();
  }

}
