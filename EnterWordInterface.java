import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class EnterWordInterface extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	
	public EnterWordInterface() {
		
		JPanel centraliser = new JPanel(new GridBagLayout());
		JPanel panel = new JPanel(new GridLayout(0,2));
		JButton associatedWords = new JButton("<html><center>Find all words associated<br>to your Parikh matrix.</center><html>");
		JButton amiableWords = new JButton("<html><center>Find all words that share<br>a Parikh matrix with your word.</center><html>");
		JButton parikhMatrix = new JButton("<html><center>Find the Parikh<br>matrix of your word.</center><html>");
		JButton isParikh = new JButton("<html><center>Decide if your matrix is a Parikh matrix.");
		JButton lParikhMatrix = new JButton("<html><center>Find the L-Parikh<br>matrix of your word.</center><html>");
		JButton lAmiableWords = new JButton("<html><center>Find the words that share an<br>L-Parikh matrix with your word.</center><html>");
		JButton glossary = new JButton("<html><center>Glossary</center><html>");
		JButton pParikhMatrix = new JButton("<html><center>Find the P-Parikh matrix of your word.</center><html>");
		JButton pAmiableWords = new JButton("<html><center>Find the words that share a<br>P-Parikh matrix with your word.</center><html>");
		associatedWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		associatedWords.addActionListener(new AssociatedWordsInterface());
		parikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		parikhMatrix.addActionListener(new ParikhMatrixInterface());
		isParikh.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		isParikh.addActionListener(new IsParikhInterface());
		amiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		amiableWords.addActionListener(new AmiableWordsInterface());
		lParikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		lParikhMatrix.addActionListener(new LParikhMatrixInterface());
		lAmiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		lAmiableWords.addActionListener(new LAmiableWordsInterface());
		glossary.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		glossary.addActionListener(new GlossaryInput());
		pParikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		pParikhMatrix.addActionListener(new PParikhMatrixInterface());
		pAmiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){frame.setVisible(false);}}); 
		pAmiableWords.addActionListener(new PAmiableWordsInterface());
		panel.add(parikhMatrix);
		panel.add(isParikh);
		panel.add(associatedWords);
		panel.add(amiableWords);
		panel.add(lParikhMatrix);
		panel.add(lAmiableWords);
		panel.add(pParikhMatrix);
		panel.add(pAmiableWords);
		panel.add(glossary);
		centraliser.add(panel);
		centraliser.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		add(centraliser);
        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel container = new JPanel();
    	container.add(new EnterWordInterface());
		frame = new JFrame("Enter Word Home");
		frame.add(container);
		
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