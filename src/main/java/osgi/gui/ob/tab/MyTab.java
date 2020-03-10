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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MyTab implements PluginObjectBrowserTab<RfcResult>
{
  private JLabel label; //Label wird nur einmal erzeugt da er kein Wert speichern muss 
  private JPanel panel;
  private JTextField no1, sparteTF, produkthTF, gBrandTF;// wird pro TF erzeugt da es jeweils ein anderen Wert speichern soll
  private JTextField gMClassTF, shTeil, exRvT, eTeil, verschlT, versandV, gJahresm, werk;
  private JCheckBox wBeenden;
  private String test, gMClassTFS, shTeilS, exRvTS, eTeilS, verschlTS, versandVS, gJahresmS, werkS ;
  private String sparteTFS, produkthTFS, gBrandTFS;
  private ECTRService ectrService;
  

  MyTab(ECTRService ectrService)
  {
	panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	  
    label = new JLabel("test1");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    panel.add(label, c);
    no1 = new JTextField(20);
    c.gridx = 1;
    c.gridy = 0;
    panel.add(no1, c);
	
	
	label = new JLabel("Sparte");
	c.gridx = 0;
    c.gridy = 2;
	panel.add(label, c); 
	sparteTF = new JTextField(20);
	c.gridx = 1;
    c.gridy = 2;
	panel.add(sparteTF,c);
	
	label = new JLabel("Produkthierarchie");
	c.gridx = 0;
    c.gridy = 3;
	panel.add(label,c);
    produkthTF = new JTextField(20);
    c.gridx = 1;
    c.gridy = 3;
    panel.add(produkthTF,c);
    
    label = new JLabel("Global Brand");
    c.gridx = 0;
    c.gridy = 4;
    panel.add(label,c );
    gBrandTF = new JTextField(20);
    c.gridx = 1;
    c.gridy = 4;
    panel.add(gBrandTF,c);
    
    label = new JLabel("Global Mat Class");
    c.gridx = 0;
    c.gridy = 5;
    panel.add(label,c);
    gMClassTF = new JTextField(20);
    c.gridx = 1;
    c.gridy = 5;
    panel.add(gMClassTF,c);
    
    wBeenden = new JCheckBox("Workflow beenden");
    c.gridx = 0;
    c.gridy = 6;
    panel.add(wBeenden,c);
    
    label = new JLabel("Sicherheitsteil");
    c.gridx = 0;
    c.gridy = 7;
    panel.add(label,c);
    shTeil = new JTextField(20);
    c.gridx = 1;
    c.gridy = 7;
    panel.add(shTeil,c);
    
    label = new JLabel("Ex-Relevantes Teil");
    c.gridx = 0;
    c.gridy = 8;
    panel.add(label,c);
    exRvT = new JTextField(20);
    c.gridx = 1;
    c.gridy = 8;
    panel.add(exRvT,c);
    
    label = new JLabel("Ersatzteil");
    c.gridx = 0;
    c.gridy = 9;
    panel.add(label,c);
    eTeil = new JTextField(20);
    c.gridx = 1;
    c.gridy = 9;
    panel.add(eTeil,c);
    
    label = new JLabel("Verschleissteil");
    c.gridx = 0;
    c.gridy = 10;
    panel.add(label,c);
    verschlT = new JTextField(20);
    c.gridx = 1;
    c.gridy = 10;
    panel.add(verschlT,c);
    
    label = new JLabel ("Versandvorschrift");
    c.gridx = 0;
    c.gridy = 11;
    panel.add(label,c);
    versandV = new JTextField(20);
    c.gridx = 1;
    c.gridy = 11;
    panel.add(versandV,c);
    
    label = new JLabel ("Geplante Jahresmenge");
    c.gridx = 0;
    c.gridy = 12;
    panel.add(label,c);
    gJahresm = new JTextField(20);
    c.gridx = 1;
    c.gridy = 12;
    panel.add(gJahresm,c);
    
    label = new JLabel ("Werk");
    c.gridx = 0;
    c.gridy = 13;
    panel.add(label,c);
    werk = new JTextField(20);
    c.gridx = 1;
    c.gridy = 13;
    panel.add(werk,c);
    
    Button b1 = new Button("Button");
    c.gridx = 1;
    c.gridy = 14;
    panel.add(b1,c);
   
    
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
	gMClassTFS = gMClassTF.getText();
	shTeilS = shTeil.getText();
	exRvTS = exRvT.getText();
	eTeilS = eTeil.getText();
	verschlTS = verschlT.getText();
	versandVS = versandV.getText();
	gJahresmS = gJahresm.getText();
	werkS = werk.getText();
	sparteTFS = sparteTF.getText();
	produkthTFS = produkthTF.getText();
	gBrandTFS = gBrandTF.getText();
		
	RfcCallBuilder callBuilder = new RfcCallBuilder("Z_FIS_FIELD_MAINTANANCE");	
	callBuilder.setInputParameter("ZZ_FIELD1",test);
	callBuilder.setInputParameter("ZZ_FIELD2",gMClassTFS);
	callBuilder.setInputParameter("ZZ_FIELD3",shTeilS);
	callBuilder.setInputParameter("ZZ_FIELD4",exRvTS);
	callBuilder.setInputParameter("ZZ_FIELD5",eTeilS);
	callBuilder.setInputParameter("ZZ_FIELD6",verschlTS);
	callBuilder.setInputParameter("ZZ_FIELD7",versandVS);
	callBuilder.setInputParameter("ZZ_FIELD8",gJahresmS);
	callBuilder.setInputParameter("ZZ_FIELD9",sparteTFS);
	callBuilder.setInputParameter("ZZ_FIELD10",produkthTFS);
	callBuilder.setInputParameter("ZZ_FIELD11",gBrandTFS);
	callBuilder.setInputParameter("ZZ_FIELD12",werkS);
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