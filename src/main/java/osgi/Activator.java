package osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dscsag.plm.spi.interfaces.gui.PluginContext;

/**
 * Activator to register provided services
 */
public class Activator implements BundleActivator
{

  @Override
  public void start(BundleContext context) throws Exception
  {
    // then register our service, which "enhances" the ECTR file handling
  }

  @Override
  public void stop(BundleContext context) throws Exception
  {
    // empty
  }

}