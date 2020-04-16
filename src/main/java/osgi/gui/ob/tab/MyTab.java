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
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class MyTab implements PluginObjectBrowserTab<RfcResult>
{
  private JLabel label; //Label wird nur einmal erzeugt da er kein Wert speichern muss 
  private JPanel panel;
  private JTextField sparteTF, produkthTF, gJahresm;// wird pro TF erzeugt da es jeweils ein anderen Wert speichern soll
  private JCheckBox wBeenden, IC;
  private String gMClassTFS, shTeilS, exRvTS, eTeilS, verschlTS, versandVS, gJahresmS, icS;
  private String sparteTFS, produkthTFS, gBrandTFS, wBeendenS, test;
  private JComboBox sparteAuswahl, gBrandTF, gMClassTF, shTeil, exRvT, verschlT, versandV, ersatzteilAuswahl;
  private ECTRService ectrService;
  //private PlmObjectKey plmObjectKey;
  
  String sparte[] = {"", "10 - Home Finishing", "15 - DF Personal Care", "20 - Prof. Finishing", "30 - IS Liquid", "40 - IS Powder", "50 - IS Adhesive", "90 - Intercompany"};
  String gm[] = {"", "001 - Wagner", "002 - Titan", "003 - Earlex", "004 - Walther Pilot", "005 - Reinhardt Technik", "006 - CAT", "007 - Unbranded", "008 - HomeRight", "999 - noch nicht zugewiesen"};
  String gms[] = {"", "001 - Gerät", "002 - Zubehör", "003 - Serviceteil", "004 - Literatur", "005 - Dienstleistungen", "999 - noch nicht zugewiesen"};
  String sicherheitsteil[] = {"Ja", "Nein", "noch zu definieren"};
  String exrelevanz[] = {"Ja", "Nein", "noch zu definieren"};
  String verschleissteil[] = {"Ja", "Nein", "noch zu definieren"};
  String versand[] = {"", "1 - Kein Schüttgut", "2 - Einzelverpackt", "3 - ESD Verpackung", "4 - Serialnr. sortiert", "5 - Einzelverpackt: Schlauchnetze", "6 - Einzelverpackt: Schutzpapier", "7 - Korrosionsgefahr: gut schützen", "8 - Präzisionsteile: gut schützen", "9 - Mehrere Einzelteile!", "10 - Teile vor Licht schützen", "11 - Magnetisch: Kontakt schützen", "12 - Teile einzel gesteckt", "13 - Einzelverpackt: Polybeutel", "99 - Keine besondere Vorschriften"};
  String ersatzteil[] = {"", "1 - Ersatzteil Endkunde (UM/SC)", "2 - Ersatzteil Service (SM)", "3 - kein ET - nicht verkaufsfähig", "4 - Gerät, Zubehör (kein ET)", "0 - noch zu definieren"};
  
  MyTab(ECTRService ectrService)
  {
	panel = new JPanel();
	panel.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
	label = new JLabel("Sparte");
	c.fill = GridBagConstraints.WEST;
	c.gridx = 0;
    c.gridy = 2;
	panel.add(label, c);
	sparteAuswahl = new JComboBox(sparte);
	//sparteTF = new JTextField(20);
	c.fill = GridBagConstraints.CENTER;
	c.gridx = 1;
    c.gridy = 2;
	panel.add(sparteAuswahl,c);
	
	label = new JLabel("Produkthierarchie");
	c.gridx = 0;
    c.gridy = 3;
	panel.add(label,c);
    produkthTF = new JTextField(20);
    c.gridx = 1;
    c.gridy = 3;
    panel.add(produkthTF,c);
    
    Button b2 = new Button("PDF");
    c.gridx = 2;
    c.gridy = 3;
    c.gridwidth = 1;
    c.fill = GridBagConstraints.EAST;
    panel.add(b2,c);
    
    label = new JLabel("Global Brand");
    c.gridx = 0;
    c.gridy = 4;
    panel.add(label,c );
    gBrandTF = new JComboBox(gm);
    c.gridx = 1;
    c.gridy = 4;
    panel.add(gBrandTF,c);
    
    label = new JLabel("Global Mat Class");
    c.gridx = 0;
    c.gridy = 5;
    panel.add(label,c);
    gMClassTF = new JComboBox(gms);
    c.gridx = 1;
    c.gridy = 5;
    panel.add(gMClassTF,c);
    
    label = new JLabel("Ex-Relevantes Teil");
    c.gridx = 0;
    c.gridy = 6;
    panel.add(label,c);
    exRvT = new JComboBox(exrelevanz);
    c.gridx = 1;
    c.gridy = 6;
    panel.add(exRvT,c);
    
    label = new JLabel("Ersatzteil");
    c.gridx = 0;
    c.gridy = 7;
    panel.add(label,c);
    ersatzteilAuswahl = new JComboBox(ersatzteil);
    c.gridx = 1;
    c.gridy = 7;
    panel.add(ersatzteilAuswahl,c);
    
    label = new JLabel("Verschleissteil");
    c.gridx = 0;
    c.gridy = 8;
    panel.add(label,c);
    verschlT = new JComboBox(verschleissteil);
    c.gridx = 1;
    c.gridy = 8;
    panel.add(verschlT,c);
    
    label = new JLabel("Sicherheitsteil");
    c.gridx = 0;
    c.gridy = 9;
    panel.add(label,c);
    shTeil = new JComboBox(sicherheitsteil);
    c.gridx = 1;
    c.gridy = 9;
    panel.add(shTeil,c);
    
    label = new JLabel ("Versandvorschrift");
    c.gridx = 0;
    c.gridy = 10;
    panel.add(label,c);
    versandV = new JComboBox(versand);
    c.gridx = 1;
    c.gridy = 10;
    panel.add(versandV,c);
    
    label = new JLabel ("Geplante Jahresmenge");
    c.gridx = 0;
    c.gridy = 11;
    panel.add(label,c);
    gJahresm = new JTextField(20);
    c.gridx = 1;
    c.gridy = 11;
    panel.add(gJahresm,c);
    
    label = new JLabel ("Intercompany");
    c.gridx = 0;
    c.gridy = 12;
    panel.add(label,c);
    IC = new JCheckBox();
    c.gridx = 1;
    c.gridy = 12;
    panel.add(IC,c);
    
    label = new JLabel ("Workflow beenden");
    c.gridx = 0;
    c.gridy = 13;
    panel.add(label,c);
    wBeenden = new JCheckBox();
    c.gridx = 1;
    c.gridy = 13;
    panel.add(wBeenden,c);
    
    Button b1 = new Button("Speichern");
    c.gridx = 1;
    c.gridy = 14;
    panel.add(b1,c);
    

    panel.setVisible(true);
    
	b1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			selectionButtonPressed();
		}
	} );
	
	b2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			openPDF();
		}
	} );
		    
    this.ectrService=ectrService;
  }
  
	public void openPDF() {
		// TODO Auto-generated method stub
		Desktop desktop = Desktop.getDesktop();
		File file = new File("C:/ECTR/addons/OSGi-Examples/basis/plugins/Resources/Produkthierarie.pdf");	
		try {
			desktop.open(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
  
  public void selectionButtonPressed() 
  {
	gMClassTFS = String.valueOf(gMClassTF.getSelectedItem());
	shTeilS = String.valueOf(shTeil.getSelectedItem());
	exRvTS = String.valueOf(exRvT.getSelectedItem());
	eTeilS = String.valueOf(ersatzteilAuswahl.getSelectedItem());
	verschlTS = String.valueOf(verschlT.getSelectedItem());
	versandVS = String.valueOf(versandV.getSelectedItem());
	gJahresmS = gJahresm.getText();
	if (IC.isSelected()) {
		icS = "1";
	} else {
		icS = "0";
	}
	sparteTFS = String.valueOf(sparteAuswahl.getSelectedItem());
	produkthTFS = produkthTF.getText();
	gBrandTFS = String.valueOf(gBrandTF.getSelectedItem());
	if (wBeenden.isSelected()) {
		wBeendenS = "1";
	} else {
		wBeendenS = "0";
  	}
	
	RfcCallBuilder callBuilder = new RfcCallBuilder("Z_FIS_FIELD_MAINTANANCE");	
	callBuilder.setInputParameter("ZZ_WORKFLOW",wBeendenS);
	callBuilder.setInputParameter("ZZ_GCLASS",gMClassTFS);
	callBuilder.setInputParameter("ZZ_SICHERHEITSTEIL",shTeilS);
	callBuilder.setInputParameter("ZZ_EXRELEVANZ",exRvTS);
	callBuilder.setInputParameter("ZZ_ERSATZTEIL",eTeilS);
	callBuilder.setInputParameter("ZZ_VERSCHLEISS",verschlTS);
	callBuilder.setInputParameter("ZZ_VERSAND",versandVS);
	callBuilder.setInputParameter("ZZ_JAHRESMENGE",gJahresmS);
	callBuilder.setInputParameter("ZZ_SPARTE",sparteTFS);
	callBuilder.setInputParameter("ZZ_PRODUKT",produkthTFS);
	callBuilder.setInputParameter("ZZ_GBRAND",gBrandTFS);
	callBuilder.setInputParameter("ZZ_IC",icS);
	callBuilder.setInputParameter("ZZ_DOCUMENT",test);
	ectrService.getRfcExecutor().execute(callBuilder.toRfcCall());
	
	JOptionPane.showMessageDialog(panel, "Daten wurden erfolgreich übertragen", "Information", JOptionPane.INFORMATION_MESSAGE, null);
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
	
	panel.revalidate();
	panel.repaint();
	produkthTF.setText("");
    sparteAuswahl.setSelectedIndex(0);;
    gBrandTF.setSelectedIndex(0);
    gMClassTF.setSelectedIndex(0);
    shTeil.setSelectedIndex(0);
    exRvT.setSelectedIndex(0);
    verschlT.setSelectedIndex(0);
    versandV.setSelectedIndex(0);
    ersatzteilAuswahl.setSelectedIndex(0);
    gJahresm.setText("");
	wBeenden.setSelected(false);
	IC.setSelected(false);
}

@Override
public RfcResult fetchData(PlmObjectKey plmObjectKey, ProgressTracker tracker) {
	test = plmObjectKey.getKey();
    ectrService.getPlmLogger().trace("dataFetcher...");
    tracker.updateProgress(new ProgressEvent("calling SAP..."));
    RfcCallBuilder callBuilder = new RfcCallBuilder("Z_FIS_FIELD_GET");	
    callBuilder.setInputParameter("ZZ_DOCUMENT",test);
    return ectrService.getRfcExecutor().execute(callBuilder.toRfcCall());
}

@Override
public void updateUI(RfcResult executeResult) {
    ectrService.getPlmLogger().trace("updateUI...");
    String product = executeResult.getExportParameter("ZZ_PRODUKT").toString();
    String sparte = executeResult.getExportParameter("ZZ_SPARTE").toString();
    String jahresmenge = executeResult.getExportParameter("ZZ_JAHRESMENGE").toString();
    String workflow = executeResult.getExportParameter("ZZ_WORKFLOW").toString();
    String intercompany = executeResult.getExportParameter("ZZ_WORKFLOW").toString();
    String globalBrand = executeResult.getExportParameter("ZZ_GBRAND").toString();
    String globalClass = executeResult.getExportParameter("ZZ_GCLASS").toString();
    String sicherheit = executeResult.getExportParameter("ZZ_SICHERHEITSTEIL").toString();
    String exrelevanz = executeResult.getExportParameter("ZZ_EXRELEVANZ").toString();
    String verschleiss = executeResult.getExportParameter("ZZ_VERSCHLEISS").toString();
    String versand = executeResult.getExportParameter("ZZ_VERSAND").toString();
    String ersatz = executeResult.getExportParameter("ZZ_ERSATZTEIL").toString();
    
    produkthTF.setText(product);
    sparteAuswahl.setSelectedItem(sparte);
    gBrandTF.setSelectedItem(globalBrand);
    gMClassTF.setSelectedItem(globalClass);
    shTeil.setSelectedItem(sicherheit);
    exRvT.setSelectedItem(exrelevanz);
    verschlT.setSelectedItem(verschleiss);
    versandV.setSelectedItem(versand);
    ersatzteilAuswahl.setSelectedItem(ersatz);
    gJahresm.setText(jahresmenge);
    if (workflow.equals("1")) {
    	wBeenden.setSelected(true);
    } else {
    	wBeenden.setSelected(false);
    }
    	
    if (intercompany.equals("1")) {
    	IC.setSelected(true);
    } else {
    	IC.setSelected(false);
    }
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