import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

/*
 * To add a new term to the glossary:
 * 
 * Create a JLabel that ends in "Title" to hold the term.
 * Create a JPanel that ends in "Panel" to hold the term label.
 * Add label to panel.
 * Set text alignment of title to center.
 * Create a JTextArea that ends in "Info" to hold the definition.
 * Assign Info compound borders that create 2pt lines overall and pad by 5px.
 * Adjust other Info borders that may now be too thick or thin.
 * Assign term's JPanel borders that create 2pt lines overall.
 * Adjust other terms' borders that may now be too thick or thin.
 * Copy settings for other Text Areas.
 * Add new JPanel and JTextArea to "holder" (NB adjust gridx and gridy accordingly)
 */


public class Glossary  extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;


	public Glossary() {
		
		JPanel holder = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel termTitle = new JLabel("<html><center>Term</center><html>");
		JLabel alphabetTitle = new JLabel("<html><center>Ordered Alphabet</center><html>");
		JLabel parikhMatrixTitle = new JLabel("<html><center>Parikh Matrix</center><html>");
		JLabel amiableTitle = new JLabel("<html><center>Amiable</center><html>");
		JLabel lyndonConjugateTitle = new JLabel("<html><center>Lyndon Conjugate</center><html>");
		JLabel lParikhMatrixTitle = new JLabel("<html><center>L-Parikh Matrix</center><html>");
		JLabel lDistinctTitle = new JLabel("<html><center>L-Distinct</center><html>");
		JLabel lAmiableTitle = new JLabel("<html><center>L-Amiable</center><html>");
		JLabel lDistinguishableTitle = new JLabel("<html><center>L-Distinguishable</center><html>");
		JLabel pParikhMatrixTitle = new JLabel("<html><center>P-Parikh Matrix</center><html>");
		JLabel pDistinctTitle = new JLabel("<html><center>P-Distinct</center><html>");
		JLabel pAmiableTitle = new JLabel("<html><center>P-Amiable</center><html>");
		JLabel pDistinguishableTitle = new JLabel("<html><center>P-Distinguishable</center><html>");
		
		JPanel termPanel = new JPanel(new GridLayout(0,1));
		JPanel alphabetPanel = new JPanel(new GridLayout(0,1));
		JPanel parikhMatrixPanel = new JPanel(new GridLayout(0,1));
		JPanel amiablePanel = new JPanel(new GridLayout(0,1));
		JPanel lyndonConjugatePanel = new JPanel(new GridLayout(0,1));
		JPanel lParikhMatrixPanel = new JPanel(new GridLayout(0,1));
		JPanel lDistinctPanel = new JPanel(new GridLayout(0,1));
		JPanel lAmiablePanel = new JPanel(new GridLayout(0,1));
		JPanel lDistinguishablePanel = new JPanel(new GridLayout(0,1));
		JPanel pParikhMatrixPanel = new JPanel(new GridLayout(0,1));
		JPanel pDistinctPanel = new JPanel(new GridLayout(0,1));
		JPanel pAmiablePanel = new JPanel(new GridLayout(0,1));
		JPanel pDistinguishablePanel = new JPanel(new GridLayout(0,1));
		
		termTitle.setHorizontalAlignment(JLabel.CENTER);
		alphabetTitle.setHorizontalAlignment(JLabel.CENTER);
		parikhMatrixTitle.setHorizontalAlignment(JLabel.CENTER);
		amiableTitle.setHorizontalAlignment(JLabel.CENTER);
		lyndonConjugateTitle.setHorizontalAlignment(JLabel.CENTER);
		lParikhMatrixTitle.setHorizontalAlignment(JLabel.CENTER);
		lDistinctTitle.setHorizontalAlignment(JLabel.CENTER);
		lAmiableTitle.setHorizontalAlignment(JLabel.CENTER);
		lDistinguishableTitle.setHorizontalAlignment(JLabel.CENTER);
		pParikhMatrixTitle.setHorizontalAlignment(JLabel.CENTER);
		pDistinctTitle.setHorizontalAlignment(JLabel.CENTER);
		pAmiableTitle.setHorizontalAlignment(JLabel.CENTER);
		pDistinguishableTitle.setHorizontalAlignment(JLabel.CENTER);
		
		termPanel.add(termTitle);
		alphabetPanel.add(alphabetTitle);
		parikhMatrixPanel.add(parikhMatrixTitle);
		amiablePanel.add(amiableTitle);
		lyndonConjugatePanel.add(lyndonConjugateTitle);
		lParikhMatrixPanel.add(lParikhMatrixTitle);
		lDistinctPanel.add(lDistinctTitle);
		lAmiablePanel.add(lAmiableTitle);
		lDistinguishablePanel.add(lDistinguishableTitle);
		pParikhMatrixPanel.add(pParikhMatrixTitle);
		pDistinctPanel.add(pDistinctTitle);
		pAmiablePanel.add(pAmiableTitle);
		pDistinguishablePanel.add(pDistinguishableTitle);
		
		JLabel termInfo = new JLabel("Definition");
		JTextArea alphabetInfo = new JTextArea("The set of all letters used to create a word is called an alphabet. When an order is assigned to these letters, we call this an ordered alphabet. We say the size of an alphabet is the number of letters that alphabet contains.");
		JTextArea parikhMatrixInfo = new JTextArea("The Parikh matrix of a word has size (k+1)x(k+1), where k is the size of the alphabet. The diagonal of the matrix is populated with 1's and all elements below it are 0. The count of all subwords that consist of consecutive letters in the alphabet and are of length n in the word are found on the n-diagonal.");
		JTextArea amiableInfo = new JTextArea("If two distinct words have the same Parikh matrix, we say that they are amiable.");
		JTextArea lyndonConjugateInfo = new JTextArea("The Lyndon conjugate of a word is the conjugate that is lexicographically smallest based on the order of the alphabet.");
		JTextArea lParikhMatrixInfo = new JTextArea("The L-Parikh matrix of a word is the Parikh matrix of that word's Lyndon conjugate. To reduce a word's ambiguity, we use both it's Parikh matrix and L-Parikh matrix.");
		JTextArea lDistinctInfo = new JTextArea("Two words are L-distinct if they share the same Parikh matrix, but have different L-Parikh matrices.");
		JTextArea lAmiableInfo = new JTextArea("Two words are L-amiable if they share the same Parikh and L-Parikh matrices.");
		JTextArea lDistinguishableInfo = new JTextArea("A Parikh matrix is L-distinguishable if it describes at least two distinct words whose respective L-Parikh matrices are different.");
		JTextArea pParikhMatrixInfo = new JTextArea("Let S be a set that contains letters in your given alphabet. We define the P-Parikh matrix of a word with respect to S as the Parikh matrix of the word obtained from the following. For each letter in your word, if that letter is in S, it remains, otherwise replace that letter with the empty word.");
		JTextArea pDistinctInfo = new JTextArea("Two words are P-distinct if they share the same Parikh matrix, but there exists a set S such that they have different P-Parikh matrices.");
		JTextArea pAmiableInfo = new JTextArea("Two words are P-amiable if they share the same Parikh matrix and there does not exist a set S such that they have different P-Parikh matrices.");
		JTextArea pDistinguishableInfo = new JTextArea("A Parikh matrix is P-distinguishable if it describes at least two distinct words whose respective P-Parikh matrices are different for some set S.");
		
		
		Border empty = BorderFactory.createEmptyBorder(5,5,5,5);
	    Border line = new MatteBorder(1,1,1,2,Color.BLACK);
	    
		alphabetInfo.setMinimumSize(new Dimension(500,65));
		termInfo.setBorder(new CompoundBorder(new MatteBorder(2,1,1,2,Color.BLACK),empty));
		alphabetInfo.setBorder(new CompoundBorder(line,empty));
		parikhMatrixInfo.setBorder(new CompoundBorder(line,empty));
		amiableInfo.setBorder(new CompoundBorder(line,empty));
		lyndonConjugateInfo.setBorder(new CompoundBorder(line,empty));
		lParikhMatrixInfo.setBorder(new CompoundBorder(line,empty));
		lDistinctInfo.setBorder(new CompoundBorder(line,empty));
		lAmiableInfo.setBorder(new CompoundBorder(line,empty));
		lDistinguishableInfo.setBorder(new CompoundBorder(line,empty));
		pParikhMatrixInfo.setBorder(new CompoundBorder(line,empty));
		pDistinctInfo.setBorder(new CompoundBorder(line,empty));
		pAmiableInfo.setBorder(new CompoundBorder(line,empty));
		pDistinguishableInfo.setBorder(new CompoundBorder(new MatteBorder(1,1,2,2,Color.BLACK),empty));
		
		termInfo.setHorizontalAlignment(JLabel.CENTER);
		Font font = termTitle.getFont();
		termInfo.setFont(new Font(font.getName(), Font.PLAIN, 16));
		termTitle.setFont(new Font(font.getName(), Font.PLAIN, 16));
		
		
		Border line2 = new MatteBorder(1,2,1,1,Color.BLACK);
		termPanel.setBorder(new MatteBorder(2,2,1,1,Color.BLACK));
		alphabetPanel.setBorder(line2);
		parikhMatrixPanel.setBorder(line2);
		amiablePanel.setBorder(line2);
		lyndonConjugatePanel.setBorder(line2);
		lParikhMatrixPanel.setBorder(line2);
		lDistinctPanel.setBorder(line2);
		lAmiablePanel.setBorder(line2);
		lDistinguishablePanel.setBorder(line2);
		pParikhMatrixPanel.setBorder(line2);
		pDistinctPanel.setBorder(line2);
		pAmiablePanel.setBorder(line2);
		pDistinguishablePanel.setBorder(new MatteBorder(1,2,2,1,Color.BLACK));
		
		alphabetInfo.setEditable(false);
		alphabetInfo.setLineWrap(true);
		alphabetInfo.setWrapStyleWord(true);
		alphabetInfo.setOpaque(false);
		parikhMatrixInfo.setEditable(false);
		parikhMatrixInfo.setLineWrap(true);
		parikhMatrixInfo.setWrapStyleWord(true);
		parikhMatrixInfo.setOpaque(false);
		amiableInfo.setEditable(false);
		amiableInfo.setLineWrap(true);
		amiableInfo.setWrapStyleWord(true);
		amiableInfo.setOpaque(false);
		lyndonConjugateInfo.setEditable(false);
		lyndonConjugateInfo.setLineWrap(true);
		lyndonConjugateInfo.setWrapStyleWord(true);
		lyndonConjugateInfo.setOpaque(false);
		lParikhMatrixInfo.setEditable(false);
		lParikhMatrixInfo.setLineWrap(true);
		lParikhMatrixInfo.setWrapStyleWord(true);
		lParikhMatrixInfo.setOpaque(false);
		lDistinctInfo.setEditable(false);
		lDistinctInfo.setLineWrap(true);
		lDistinctInfo.setWrapStyleWord(true);
		lDistinctInfo.setOpaque(false);
		lAmiableInfo.setEditable(false);
		lAmiableInfo.setLineWrap(true);
		lAmiableInfo.setWrapStyleWord(true);
		lAmiableInfo.setOpaque(false);
		lDistinguishableInfo.setEditable(false);
		lDistinguishableInfo.setLineWrap(true);
		lDistinguishableInfo.setWrapStyleWord(true);
		lDistinguishableInfo.setOpaque(false);
		pParikhMatrixInfo.setEditable(false);
		pParikhMatrixInfo.setLineWrap(true);
		pParikhMatrixInfo.setWrapStyleWord(true);
		pParikhMatrixInfo.setOpaque(false);
		pDistinctInfo.setEditable(false);
		pDistinctInfo.setLineWrap(true);
		pDistinctInfo.setWrapStyleWord(true);
		pDistinctInfo.setOpaque(false);
		pAmiableInfo.setEditable(false);
		pAmiableInfo.setLineWrap(true);
		pAmiableInfo.setWrapStyleWord(true);
		pAmiableInfo.setOpaque(false);
		pDistinguishableInfo.setEditable(false);
		pDistinguishableInfo.setLineWrap(true);
		pDistinguishableInfo.setWrapStyleWord(true);
		pDistinguishableInfo.setOpaque(false);
		
		termPanel.setPreferredSize(new Dimension(200,40));
		alphabetPanel.setPreferredSize(new Dimension(200,65));
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		holder.setPreferredSize(new Dimension(700,800));
		c.gridx=0;
		c.gridy=0;
		holder.add(termPanel,c);
		c.gridx=1;
		c.gridy=0;
		holder.add(termInfo,c);
		c.gridx=0;
		c.gridy=1;
		holder.add(alphabetPanel,c);
		c.gridx=1;
		c.gridy=1;
		holder.add(alphabetInfo,c);
		c.gridx=0;
		c.gridy=2;
		holder.add(parikhMatrixPanel,c);
		c.gridx=1;
		c.gridy=2;
		holder.add(parikhMatrixInfo,c);
		c.gridx=0;
		c.gridy=3;
		holder.add(amiablePanel,c);
		c.gridx=1;
		c.gridy=3;
		holder.add(amiableInfo,c);
		c.gridx=0;
		c.gridy=4;
		holder.add(lyndonConjugatePanel,c);
		c.gridx=1;
		c.gridy=4;
		holder.add(lyndonConjugateInfo,c);
		c.gridx=0;
		c.gridy=5;
		holder.add(lParikhMatrixPanel,c);
		c.gridx=1;
		c.gridy=5;
		holder.add(lParikhMatrixInfo,c);
		c.gridx=0;
		c.gridy=6;
		holder.add(lDistinctPanel,c);
		c.gridx=1;
		c.gridy=6;
		holder.add(lDistinctInfo,c);
		c.gridx=0;
		c.gridy=7;
		holder.add(lAmiablePanel,c);
		c.gridx=1;
		c.gridy=7;
		holder.add(lAmiableInfo,c);
		c.gridx=0;
		c.gridy=8;
		holder.add(lDistinguishablePanel,c);
		c.gridx=1;
		c.gridy=8;
		holder.add(lDistinguishableInfo,c);
		c.gridx=0;
		c.gridy=9;
		holder.add(pParikhMatrixPanel,c);
		c.gridx=1;
		c.gridy=9;
		holder.add(pParikhMatrixInfo,c);
		c.gridx=0;
		c.gridy=10;
		holder.add(pDistinctPanel,c);
		c.gridx=1;
		c.gridy=10;
		holder.add(pDistinctInfo,c);
		c.gridx=0;
		c.gridy=11;
		holder.add(pAmiablePanel,c);
		c.gridx=1;
		c.gridy=11;
		holder.add(pAmiableInfo,c);
		c.gridx=0;
		c.gridy=12;
		holder.add(pDistinguishablePanel,c);
		c.gridx=1;
		c.gridy=12;
		holder.add(pDistinguishableInfo,c);
		
		add(holder);
	}


public void actionPerformed(ActionEvent e2) {
	
	//make frame scroll-able if matrix is large enough
	JPanel container = new JPanel();
	container.add(new Glossary());
	
	JScrollPane scrPane = new JScrollPane(container);
	scrPane.getVerticalScrollBar().setUnitIncrement(16);
	scrPane.getHorizontalScrollBar().setUnitIncrement(16);
	
	//frame formatting
    JFrame frame = new JFrame("Glossary");
    frame.add(scrPane);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
	
}


}
