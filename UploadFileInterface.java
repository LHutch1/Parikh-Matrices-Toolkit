import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class UploadFileInterface extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	public static JFrame frame;
	public UploadFileInterface() {
		
		JPanel centraliser = new JPanel(new GridBagLayout());
		JPanel panel = new JPanel(new GridLayout(0,2));
		JPanel inst = new JPanel(new GridBagLayout());
		JLabel instructions = new JLabel("<html><center>When you select any of the options below, you will first be asked to select the file that contains your input words. This<br>file must be a .txt file. You will then be asked to select the location you wish to save your output files in.</center><html>");
		instructions.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		JLabel instructions2 = new JLabel("<html><center>If you are using either the Parikh or L-Parikh matrix functions, your text file may use either commas or new lines as delimiters.</center><html>");
		instructions2.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		JLabel instructions3 = new JLabel("<html><center>If you are using either of the P-Parikh matrix functions, your text file must use a new line as the delimiter and each<br>word must be followed in the next line by the projection set to be used on it. Your projection set may be written<br>in any form. For example, your text file may look like this:</center><html>");
		instructions3.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
		JLabel instructions4 = new JLabel("<html><center>abbcca<br>{a,b}<br>ddaccbd<br>[0 2]</center><html>");
		instructions4.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
		JButton amiableWords = new JButton("<html><center>Find all words that share<br>a Parikh matrix with your word.</center><html>");
		JButton parikhMatrix = new JButton("<html><center>Find the Parikh<br>matrix of your word.</center><html>");
		JButton lParikhMatrix = new JButton("<html><center>Find the L-Parikh<br>matrix of your word.</center><html>");
		JButton lAmiableWords = new JButton("<html><center>Find the words that share an<br>L-Parikh matrix with your word.</center><html>");
		JButton glossary = new JButton("<html><center>Glossary</center><html>");
		JButton pParikhMatrix = new JButton("<html><center>Find the P-Parikh matrix of your word.</center><html>");
		JButton pAmiableWords = new JButton("<html><center>Find the words that share a<br>P-Parikh matrix with your word.</center><html>");
		File home = FileSystemView.getFileSystemView().getHomeDirectory(); 
		parikhMatrix.addActionListener(new ParikhMatrixUploadInterface(new int[]{0}, 1, home.getAbsolutePath(), 0));
		amiableWords.addActionListener(new AmiableWordsUploadInterface(new int[]{0}, 1, home.getAbsolutePath(), 0));
		lParikhMatrix.addActionListener(new LParikhMatrixUploadInterface(new int[]{0}, 1, home.getAbsolutePath(), 0));
		lAmiableWords.addActionListener(new LAmiableWordsUploadInterface(new int[]{0}, 1, home.getAbsolutePath(), 0));
		glossary.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		glossary.addActionListener(new Glossary());
		pParikhMatrix.addActionListener(new PParikhMatrixUploadInterface(new int[]{0}, 1, home.getAbsolutePath(), 0, new int[]{0}));
		pAmiableWords.addActionListener(new PAmiableWordsUploadInterface(new int[]{0}, 1, home.getAbsolutePath(), 0, new int[]{0}));
		panel.add(parikhMatrix);
		panel.add(amiableWords);
		panel.add(lParikhMatrix);
		panel.add(lAmiableWords);
		panel.add(pParikhMatrix);
		panel.add(pAmiableWords);
		panel.add(glossary);
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx=0;
		c1.gridy=0;
		inst.add(instructions,c1);
		c1.gridx=0;
		c1.gridy=1;
		inst.add(instructions2,c1);
		c1.gridx=0;
		c1.gridy=2;
		inst.add(instructions3,c1);
		c1.gridx=0;
		c1.gridy=3;
		inst.add(instructions4,c1);
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx=0;
		c2.gridy=0;
		centraliser.add(inst,c2);
		c2.gridx=0;
		c2.gridy=1;
		centraliser.add(panel,c2);
		centraliser.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		add(centraliser);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel container = new JPanel();
    	container.add(new UploadFileInterface());
    	
		frame = new JFrame("Upload File As Input Home Screen");
		frame.add(container);
		frame.pack();
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
            	Home.homeFrame.setVisible(true);
                e.getWindow().dispose();
            }
        });
		
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
}