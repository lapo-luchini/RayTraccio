import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
/**
 * Insert the type's description here.
 * Creation date: (13/02/2001 23.55.09)
 * @author: 
 */
public class RayDE extends javax.swing.JApplet implements WindowListener, Runnable, DocumentListener {
	Thread thread;
	private javax.swing.JPanel ivjJAppletContentPane = null;
	private javax.swing.JEditorPane ivjEditorSDL = null;
	private javax.swing.JTabbedPane ivjLinguette = null;
	private javax.swing.JPanel ivjPanelImage = null;
	private javax.swing.JPanel ivjPanelSDL = null;
	private javax.swing.JPanel ivjPanelTree = null;
	private javax.swing.JTextPane ivjParserError = null;
	private javax.swing.JTree ivjJTree1 = null;
	private javax.swing.JScrollPane ivjScrollaImmagine = null;
	private long time_mod = 0;
	private long time_parse = 0;
	private SDL parser = null;
	private Scene scene = null;
	private javax.swing.text.PlainDocument document = null;
	IvjEventHandler ivjEventHandler = new IvjEventHandler();
	private javax.swing.JProgressBar ivjProgressBar = null;
	private javax.swing.JButton ivjTraccia = null;
	private javax.swing.JTextField ivjDimX = null;
	private javax.swing.JTextField ivjDimY = null;
	private javax.swing.JLabel ivjLabelPer = null;
	private javax.swing.JPanel ivjPanelStats = null;
	private RayTracer ivjRayTracerComponent = null;
	private javax.swing.JLabel ivjLabelAbout1 = null;
	private javax.swing.JLabel ivjLabelAbout2 = null;
	private javax.swing.JPanel ivjPanelAbout = null;
	private javax.swing.JScrollPane ivjScrollaEditor = null;
	private javax.swing.JLabel ivjLabelAbout3 = null;
	private javax.swing.JLabel ivjLabelAbout4 = null;
	private javax.swing.JCheckBox ivjCheckBoxAA = null;

class IvjEventHandler implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent e) {
			if (e.getSource() == RayDE.this.getTraccia()) 
				connEtoC2(e);
		};
	};
/**
 * changedUpdate method comment.
 */
public void changedUpdate(javax.swing.event.DocumentEvent e) {}
/**
 * connEtoC2:  (Traccia.action.actionPerformed(java.awt.event.ActionEvent) --> RayDE.traccia_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
 * @param arg1 java.awt.event.ActionEvent
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void connEtoC2(java.awt.event.ActionEvent arg1) {
	try {
		// user code begin {1}
		// user code end
		this.traccia_ActionPerformed(arg1);
		// user code begin {2}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {3}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * Returns information about this applet.
 * @return a string of information about this applet
 */
public String getAppletInfo() {
	return "RayDE\n" + 
		"\n" + 
		"RayTracer IDE.\n" + 
		"Creation date: (13/02/2001 23.54.45)\n" + 
		"@author: Lapo Luchini <lapo@lapo.it>\n" + 
		"";
}
/**
 * Return the CheckBoxAA property value.
 * @return javax.swing.JCheckBox
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JCheckBox getCheckBoxAA() {
	if (ivjCheckBoxAA == null) {
		try {
			ivjCheckBoxAA = new javax.swing.JCheckBox();
			ivjCheckBoxAA.setName("CheckBoxAA");
			ivjCheckBoxAA.setMnemonic('a');
			ivjCheckBoxAA.setText("AntiAlias");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjCheckBoxAA;
}
/**
 * Return the DimX property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getDimX() {
	if (ivjDimX == null) {
		try {
			ivjDimX = new javax.swing.JTextField();
			ivjDimX.setName("DimX");
			ivjDimX.setText("200");
			ivjDimX.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			ivjDimX.setColumns(4);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDimX;
}
/**
 * Return the DimY property value.
 * @return javax.swing.JTextField
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextField getDimY() {
	if (ivjDimY == null) {
		try {
			ivjDimY = new javax.swing.JTextField();
			ivjDimY.setName("DimY");
			ivjDimY.setText("150");
			ivjDimY.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
			ivjDimY.setColumns(4);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjDimY;
}
/**
 * Return the EditorSDL property value.
 * @return javax.swing.JEditorPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JEditorPane getEditorSDL() {
	if (ivjEditorSDL == null) {
		try {
			ivjEditorSDL = new javax.swing.JEditorPane();
			ivjEditorSDL.setName("EditorSDL");
			ivjEditorSDL.setDocument(new javax.swing.text.PlainDocument());
			ivjEditorSDL.setFont(new java.awt.Font("monospaced", 0, 12));
			ivjEditorSDL.setBackground(java.awt.Color.white);
			ivjEditorSDL.setBounds(0, 0, 4, 21);
			// user code begin {1}
			document=(javax.swing.text.PlainDocument)ivjEditorSDL.getDocument();
			document.putProperty(document.tabSizeAttribute, new Integer(2));
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjEditorSDL;
}
/**
 * Return the JAppletContentPane property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getJAppletContentPane() {
	if (ivjJAppletContentPane == null) {
		try {
			ivjJAppletContentPane = new javax.swing.JPanel();
			ivjJAppletContentPane.setName("JAppletContentPane");
			ivjJAppletContentPane.setLayout(new java.awt.BorderLayout());
			getJAppletContentPane().add(getLinguette(), "Center");
			getJAppletContentPane().add(getPanelStats(), "South");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJAppletContentPane;
}
/**
 * Return the JTree1 property value.
 * @return javax.swing.JTree
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTree getJTree1() {
	if (ivjJTree1 == null) {
		try {
			ivjJTree1 = new javax.swing.JTree();
			ivjJTree1.setName("JTree1");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjJTree1;
}
/**
 * Return the LabelAbout1 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getLabelAbout1() {
	if (ivjLabelAbout1 == null) {
		try {
			ivjLabelAbout1 = new javax.swing.JLabel();
			ivjLabelAbout1.setName("LabelAbout1");
			ivjLabelAbout1.setFont(new java.awt.Font("dialog.bold", 1, 36));
			ivjLabelAbout1.setText("RayDE");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLabelAbout1;
}
/**
 * Return the LabelAbout2 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getLabelAbout2() {
	if (ivjLabelAbout2 == null) {
		try {
			ivjLabelAbout2 = new javax.swing.JLabel();
			ivjLabelAbout2.setName("LabelAbout2");
			ivjLabelAbout2.setFont(new java.awt.Font("dialog.bold", 1, 14));
			ivjLabelAbout2.setText("RayTracer IDE");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLabelAbout2;
}
/**
 * Return the LabelAbout3 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getLabelAbout3() {
	if (ivjLabelAbout3 == null) {
		try {
			ivjLabelAbout3 = new javax.swing.JLabel();
			ivjLabelAbout3.setName("LabelAbout3");
			ivjLabelAbout3.setText("2001 by Lapo Luchini");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLabelAbout3;
}
/**
 * Return the LabelAbout4 property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getLabelAbout4() {
	if (ivjLabelAbout4 == null) {
		try {
			ivjLabelAbout4 = new javax.swing.JLabel();
			ivjLabelAbout4.setName("LabelAbout4");
			ivjLabelAbout4.setText("http://www.lapo.it/RayTraccio.html");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLabelAbout4;
}
/**
 * Return the LabelPer property value.
 * @return javax.swing.JLabel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JLabel getLabelPer() {
	if (ivjLabelPer == null) {
		try {
			ivjLabelPer = new javax.swing.JLabel();
			ivjLabelPer.setName("LabelPer");
			ivjLabelPer.setText("x");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLabelPer;
}
/**
 * Return the Modi property value.
 * @return javax.swing.JTabbedPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTabbedPane getLinguette() {
	if (ivjLinguette == null) {
		try {
			ivjLinguette = new javax.swing.JTabbedPane();
			ivjLinguette.setName("Linguette");
			ivjLinguette.setTabPlacement(javax.swing.JTabbedPane.TOP);
			ivjLinguette.setEnabled(true);
			ivjLinguette.insertTab("SDL", null, getPanelSDL(), "Qua puoi definire la scena in linguaggio SDL.", 0);
			ivjLinguette.insertTab("Albero", null, getPanelTree(), null, 1);
			ivjLinguette.insertTab("Immagine", null, getPanelImage(), "Qua appare l\'immagine creata.", 2);
			ivjLinguette.insertTab("About", null, getPanelAbout(), null, 3);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjLinguette;
}
/**
 * Return the About property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getPanelAbout() {
	if (ivjPanelAbout == null) {
		try {
			ivjPanelAbout = new javax.swing.JPanel();
			ivjPanelAbout.setName("PanelAbout");
			ivjPanelAbout.setLayout(new java.awt.GridBagLayout());

			java.awt.GridBagConstraints constraintsLabelAbout1 = new java.awt.GridBagConstraints();
			constraintsLabelAbout1.gridx = 0; constraintsLabelAbout1.gridy = 0;
			constraintsLabelAbout1.insets = new java.awt.Insets(4, 4, 4, 4);
			getPanelAbout().add(getLabelAbout1(), constraintsLabelAbout1);

			java.awt.GridBagConstraints constraintsLabelAbout2 = new java.awt.GridBagConstraints();
			constraintsLabelAbout2.gridx = 0; constraintsLabelAbout2.gridy = 1;
			constraintsLabelAbout2.insets = new java.awt.Insets(4, 4, 4, 4);
			getPanelAbout().add(getLabelAbout2(), constraintsLabelAbout2);

			java.awt.GridBagConstraints constraintsLabelAbout3 = new java.awt.GridBagConstraints();
			constraintsLabelAbout3.gridx = 0; constraintsLabelAbout3.gridy = 2;
			constraintsLabelAbout3.insets = new java.awt.Insets(4, 4, 4, 4);
			getPanelAbout().add(getLabelAbout3(), constraintsLabelAbout3);

			java.awt.GridBagConstraints constraintsLabelAbout4 = new java.awt.GridBagConstraints();
			constraintsLabelAbout4.gridx = 0; constraintsLabelAbout4.gridy = 3;
			constraintsLabelAbout4.insets = new java.awt.Insets(4, 4, 4, 4);
			getPanelAbout().add(getLabelAbout4(), constraintsLabelAbout4);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPanelAbout;
}
/**
 * Return the PanelImage property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getPanelImage() {
	if (ivjPanelImage == null) {
		try {
			ivjPanelImage = new javax.swing.JPanel();
			ivjPanelImage.setName("PanelImage");
			ivjPanelImage.setOpaque(true);
			ivjPanelImage.setLayout(new java.awt.BorderLayout());
			ivjPanelImage.setEnabled(true);
			getPanelImage().add(getScrollaImmagine(), "Center");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPanelImage;
}
/**
 * Return the Page property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getPanelSDL() {
	if (ivjPanelSDL == null) {
		try {
			ivjPanelSDL = new javax.swing.JPanel();
			ivjPanelSDL.setName("PanelSDL");
			ivjPanelSDL.setLayout(new java.awt.BorderLayout());
			getPanelSDL().add(getScrollaEditor(), "Center");
			getPanelSDL().add(getParserError(), "South");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPanelSDL;
}
/**
 * Return the Stats property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getPanelStats() {
	if (ivjPanelStats == null) {
		try {
			ivjPanelStats = new javax.swing.JPanel();
			ivjPanelStats.setName("PanelStats");
			ivjPanelStats.setLayout(new java.awt.FlowLayout());
			getPanelStats().add(getDimX(), getDimX().getName());
			getPanelStats().add(getLabelPer(), getLabelPer().getName());
			getPanelStats().add(getDimY(), getDimY().getName());
			getPanelStats().add(getCheckBoxAA(), getCheckBoxAA().getName());
			getPanelStats().add(getTraccia(), getTraccia().getName());
			getPanelStats().add(getProgressBar(), getProgressBar().getName());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPanelStats;
}
/**
 * Return the PanelTree property value.
 * @return javax.swing.JPanel
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JPanel getPanelTree() {
	if (ivjPanelTree == null) {
		try {
			ivjPanelTree = new javax.swing.JPanel();
			ivjPanelTree.setName("PanelTree");
			ivjPanelTree.setLayout(new java.awt.BorderLayout());
			getPanelTree().add(getJTree1(), "Center");
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjPanelTree;
}
/**
 * Return the ParserError property value.
 * @return javax.swing.JTextPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JTextPane getParserError() {
	if (ivjParserError == null) {
		try {
			ivjParserError = new javax.swing.JTextPane();
			ivjParserError.setName("ParserError");
			ivjParserError.setBorder(new javax.swing.border.EtchedBorder());
			ivjParserError.setFont(new java.awt.Font("dialog", 0, 10));
			ivjParserError.setEditable(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjParserError;
}
/**
 * Return the ProgressBar property value.
 * @return javax.swing.JProgressBar
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JProgressBar getProgressBar() {
	if (ivjProgressBar == null) {
		try {
			ivjProgressBar = new javax.swing.JProgressBar();
			ivjProgressBar.setName("ProgressBar");
			ivjProgressBar.setToolTipText("Percentuale dell\'immagine creata.");
			ivjProgressBar.setStringPainted(true);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjProgressBar;
}
/**
 * Return the RayTracerComponent property value.
 * @return RayTracer
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private RayTracer getRayTracerComponent() {
	return ivjRayTracerComponent;
}
/**
 * Return the JScrollPane1 property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getScrollaEditor() {
	if (ivjScrollaEditor == null) {
		try {
			ivjScrollaEditor = new javax.swing.JScrollPane();
			ivjScrollaEditor.setName("ScrollaEditor");
			getScrollaEditor().setViewportView(getEditorSDL());
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjScrollaEditor;
}
/**
 * Return the ScrollaImmagine property value.
 * @return javax.swing.JScrollPane
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JScrollPane getScrollaImmagine() {
	if (ivjScrollaImmagine == null) {
		try {
			ivjScrollaImmagine = new javax.swing.JScrollPane();
			ivjScrollaImmagine.setName("ScrollaImmagine");
			ivjScrollaImmagine.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			ivjScrollaImmagine.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			getScrollaImmagine().setViewportView(getRayTracerComponent());
			// user code begin {1}
			if(ivjRayTracerComponent == null) { // vallo a capire!
				ivjRayTracerComponent = new RayTracer();
				getScrollaImmagine().setViewportView(getRayTracerComponent());
			}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjScrollaImmagine;
}
/**
 * Return the Traccia property value.
 * @return javax.swing.JButton
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private javax.swing.JButton getTraccia() {
	if (ivjTraccia == null) {
		try {
			ivjTraccia = new javax.swing.JButton();
			ivjTraccia.setName("Traccia");
			ivjTraccia.setToolTipText("Premi qua per creare l\'immagine.");
			ivjTraccia.setMnemonic('t');
			ivjTraccia.setText("Traccia");
			ivjTraccia.setEnabled(false);
			// user code begin {1}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjTraccia;
}
/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(java.lang.Throwable exception) {

	/* Uncomment the following lines to print uncaught exceptions to stdout */
	// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
	// exception.printStackTrace(System.out);
}
/**
 * Initializes the applet.
 * 
 * @see #start
 * @see #stop
 * @see #destroy
 */
public void init() {
	try {
		setName("RayDE");
		setSize(600, 400);
		setContentPane(getJAppletContentPane());
		initConnections();
		// user code begin {1}
		// user code end
	} catch (java.lang.Throwable ivjExc) {
		// user code begin {2}
		// user code end
		handleException(ivjExc);
	}
}
/**
 * Initializes connections
 * @exception java.lang.Exception The exception description.
 */
/* WARNING: THIS METHOD WILL BE REGENERATED. */
private void initConnections() throws java.lang.Exception {
	// user code begin {1}
	getEditorSDL().getDocument().addDocumentListener(this);
	// user code end
	getTraccia().addActionListener(ivjEventHandler);
}
/**
 * insertUpdate method comment.
 */
public void insertUpdate(javax.swing.event.DocumentEvent e) {
	time_mod = System.currentTimeMillis();
	getTraccia().setEnabled(false); // attivo solo dopo che la scena è parsata
}
/**
 * Starts the applet when it is run as an application
 * @param args an array of command-line arguments
 */
public static void main(java.lang.String[] args) {
	RayDE applet = new RayDE();
	java.awt.Frame frame = new java.awt.Frame("Applet");

	frame.addWindowListener(applet);
	frame.add("Center", applet);
	frame.setSize(350, 250);
	frame.show();

	applet.init();
	applet.start();
}
/**
 * Paints the applet.
 * If the applet does not need to be painted (e.g. if it is only a container for other
 * awt components) then this method can be safely removed.
 * 
 * @param g  the specified Graphics window
 * @see #update
 */
public void paint(Graphics g) {
	super.paint(g);

	// insert code to paint the applet here
}
/**
 * removeUpdate method comment.
 */
public void removeUpdate(javax.swing.event.DocumentEvent e) {
	time_mod = System.currentTimeMillis();
	getTraccia().setEnabled(false); // attivo solo dopo che la scena è parsata
}
/**
 * Contains the thread execution loop.
 */
public void run() {
	while(true) {
		if((time_mod>time_parse)&&(System.currentTimeMillis()>time_mod+3000)) {
			time_parse=System.currentTimeMillis();
			try {
				if(parser==null)
					parser=new SDL(new java.io.StringReader(getEditorSDL().getText()));
				else
					parser.ReInit(new java.io.StringReader(getEditorSDL().getText()));
				scene=parser.sdlScene();
				getParserError().setText("Scene succesfully parsed in "+(System.currentTimeMillis()-time_parse)+" ms.");
			} catch(Throwable e) {
				scene=null;
				getParserError().setText(e.toString());
				//document. highlighting
			}
			getTraccia().setEnabled(scene!=null); // attivo solo se la scena è parsata
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		//ivjParserError.setText(ivjParserError.getText()+".");
	}
}
/**
 * Starts up the thread.
 */
public void start() {
	if (thread == null){
		thread = new Thread(this);
		thread.start();
	}
}
/**
 * Terminates the thread and leaves it for garbage collection.
 */
public void stop() {
	if (thread != null)
		thread = null;
}
/**
 * Comment
 */
public void traccia_ActionPerformed(java.awt.event.ActionEvent actionEvent) {
	getParserError().setText("Setting up.");
	RayTracer rt=getRayTracerComponent();
	rt.init(scene, 1, new Dimension(Integer.parseInt(getDimX().getText()), Integer.parseInt(getDimY().getText())), 1, getCheckBoxAA().isSelected());
	rt.start();
	getParserError().setText("Rendering.");
}
/**
 * Invoked when a window is activated.
 * @param e the received event
 */
public void windowActivated(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window has been closed.
 * @param e the received event
 */
public void windowClosed(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window is in the process of being closed.
 * The close operation can be overridden at this point.
 * @param e the received event
 */
public void windowClosing(WindowEvent e) {
	// The window is being closed.  Shut down the system.
	this.stop();
	System.exit(0);
}
/**
 * Invoked when a window is deactivated.
 * @param e the received event
 */
public void windowDeactivated(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window is de-iconified.
 * @param e the received event
 */
public void windowDeiconified(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window is iconified.
 * @param e the received event
 */
public void windowIconified(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
/**
 * Invoked when a window has been opened.
 * @param e the received event
 */
public void windowOpened(WindowEvent e) {
	// Do nothing.
	// This method is required to comply with the WindowListener interface.
}
}
