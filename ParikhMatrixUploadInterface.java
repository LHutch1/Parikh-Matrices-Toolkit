import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner; 
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ParikhMatrixUploadInterface {

	public JFrame frame;
	public JFileChooser chooser;
	public static JFileChooser chooserSave;
	public static int iteration;
	public static File uploadedFile;
	public static Scanner scanner;

	public ParikhMatrixUploadInterface(int[] word, int alphabet, String savePath, int it) {
		try {
			//uploadedFile=uploadFile;
			ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
			int[][] parikhMatrix = pM.ParikhMatrix(word,alphabet+1);
			String convertinputWord = new String("");
			for (int j=0;j<word.length;j++) {
				convertinputWord = convertinputWord + pM.getCharForNumber(word[j]);
			}
			String defaultTitle = "Parikh Matrix Of " + convertinputWord + ".txt";

			if (it==1) {
				chooserSave.setSelectedFile(new File("Parikh Matrix Of Your Word.txt"));
			}
			if (it==0) {
				File output = new File(savePath,defaultTitle);
				output.createNewFile();
				FileWriter write = new FileWriter(output, false);
				FileWriter append = new FileWriter(output, true);
				PrintWriter print_line = new PrintWriter(write);
				PrintWriter add_line = new PrintWriter(append);
				String start = new String("The Parikh matrix of your word ");
				print_line.printf("%s", start);
				print_line.close();
				add_line.printf("%s"+":"+"%n", convertinputWord);
				for (int i=0;i<parikhMatrix.length;i++) {
					for (int j=0;j<parikhMatrix.length;j++) {
						add_line.printf("%4d", parikhMatrix[i][j]);
					}
					add_line.printf("%n");
				}
				add_line.printf("%n");
				add_line.close();
			}
			if (!scanner.hasNext()) {
				Home.dialog.dispose();
			}
		}
		catch (Exception e){
			e.printStackTrace();
			Home.dialog.dispose();
		}
	}

	public static void actioned() {
		try {
			uploadedFile = Home.uploadedFile;
			Home.dialog.setVisible(false);
			chooserSave = new JFileChooser();
			//Create file chooser for user to select a location to save the output files to (default to same location as uploaded file)
			chooserSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			disableTextField(chooserSave);  //prevent user from altering file name as these are auto generated
			chooserSave.setDialogTitle("Select Location To Save Output Files");
			chooserSave.setSelectedFile(new File("Parikh Matrix Of...")); //default empty name - required to click "save" but will not be used in file generation
			chooserSave.setCurrentDirectory(uploadedFile);
			int returnVal = chooserSave.showSaveDialog(null);
			String path = chooserSave.getCurrentDirectory().getAbsolutePath();

			//if user selects a location
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				scanner = new Scanner(uploadedFile);
				scanner.useDelimiter(",|\\n");
				while (scanner.hasNext()){
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
					String enteredWord = scanner.next();
					int[] word = new int[enteredWord.length()];
					ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
					//convert next word in file into int[] for handling
					if (!isNumber(enteredWord)) {
						for (int i=0;i<enteredWord.length();i++) {
							String character = Character.toString(enteredWord.charAt(i));
							int l = pM.getNumberForChar(character)-1;
							if (l>=0){
								word[i] = l;
							}
						}
					}
					else {
						for (int i=0;i<enteredWord.length();i++) {
							int l = Character.getNumericValue(enteredWord.charAt(i));
							word[i] = l;
						}
					}
					//calculate these every iteration to allow different alphabet sizes
					int maxLetter = max(word);
					int enteredAlphabet = maxLetter+1;

					//calculate Parikh matrix of word and create file that contains it
					new ParikhMatrixUploadInterface(word,enteredAlphabet, path, 0);

				}
				scanner.close();
				Home.dialog.dispose();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Home.dialog.dispose();
		}
		Home.dialog.dispose();
	}

	private static boolean isNumber(String s) 
	{ 
		for (int i = 0; i < s.length(); i++) 
			if (Character.isDigit(s.charAt(i)) == false) 
				return false; 
		return true; 
	} 

	private static int max(int[] t) {
		int maximum = t[0];   // start with the first value
		for (int i=1; i<t.length; i++) {
			if (t[i] > maximum) {
				maximum = t[i];   // new maximum
			}
		}
		return maximum;
	}

	public static boolean disableTextField(Container container) {
		Component[] comps = container.getComponents();
		for (Component comp : comps) {
			if (comp instanceof JTextField) {
				((JTextField) comp).setEnabled(false);
				return true;
			}
			if (comp instanceof Container) {
				if (disableTextField((Container) comp)) return true;
			}
		}
		return false;
	}

}
