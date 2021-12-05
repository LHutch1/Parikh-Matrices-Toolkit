import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Home{
	public static JFrame homeFrame;
	
	public static void main(String[] args) {
		homeFrame = new JFrame("Home");
		JPanel centraliser = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JPanel panel = new JPanel(new GridLayout(0,2));
		JLabel label = new JLabel("Please select how you would like to input your words:");
		JButton uploadFile = new JButton("<html><center>Upload a text file of a list of input words.</center><html>");
		uploadFile.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){homeFrame.setVisible(false);}}); 
		JButton enterWord = new JButton("<html><center>Enter each word individually.</center><html>");
		enterWord.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){homeFrame.setVisible(false);}}); 
		label.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		uploadFile.addActionListener(new UploadFileInterface());
		enterWord.addActionListener(new EnterWordInterface());
		c.gridx=0;
		c.gridy=0;
		centraliser.add(label,c);
		panel.add(uploadFile);
		panel.add(enterWord);
		c.gridx=0;
		c.gridy=1;
		centraliser.add(panel,c);
		centraliser.setBorder(BorderFactory.createEmptyBorder(20,30,40,30));
		homeFrame.add(centraliser);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setLocationRelativeTo(null);
		homeFrame.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		homeFrame.setPreferredSize(screenSize);
		homeFrame.setVisible(true);
	}
}
