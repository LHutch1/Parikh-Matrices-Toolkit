import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class LParikhMatrixUploadInterface {

	public static JFrame frame;
	public static JFileChooser chooser;
	public static JFileChooser chooserSave;
	public static int iteration;
	public static Scanner scanner;

	public LParikhMatrixUploadInterface(int[] word, int alphabet, String savePath, int it)  {
		try { 
			ParikhMatrixCalculator pM = new ParikhMatrixCalculator();

			String convertinputWord = new String("");

			for (int j=0;j<word.length;j++) {
				convertinputWord = convertinputWord + pM.getCharForNumber(word[j]);
			}

			//Open save dialog and create file
			String defaultTitle = "The L-Parikh Matrix Of  " + convertinputWord + ".txt";
			chooserSave = new JFileChooser();
			if (it==1) {
				chooserSave.setSelectedFile(new File("L-Parikh Matrix.txt"));
			}
			if (it==0) {
				File outputfile = new File(savePath,defaultTitle);
				outputfile.createNewFile();
				FileWriter write = new FileWriter(outputfile, false);
				FileWriter append = new FileWriter(outputfile, true);
				PrintWriter print_line = new PrintWriter(write);
				PrintWriter add_line = new PrintWriter(append);


				int[][] parikhMatrix = pM.ParikhMatrix(word,alphabet+1);

				String start = new String("The Parikh matrix of your word ");
				print_line.printf("%s", start);
				print_line.printf("%s"+":"+"%n", convertinputWord);
				print_line.close();
				for (int i=0;i<parikhMatrix.length;i++) {
					for (int j=0;j<parikhMatrix.length;j++) {
						add_line.printf("%4d", parikhMatrix[i][j]);
					}
					add_line.printf("%n");
				}
				add_line.printf("%n");


				String convertWord = new String("");
				for (int j=0;j<word.length;j++) {
					convertWord = convertWord + pM.getCharForNumber(word[j]);
				}
				LyndonConjugate lC = new LyndonConjugate();
				int[] lword = lC.lyndonConj(word);
				int[][] lparikhMatrix = pM.ParikhMatrix(lword,alphabet+1);

				String lconvertWord = new String("");
				for (int j=0;j<lword.length;j++) {
					lconvertWord = lconvertWord + pM.getCharForNumber(lword[j]);
				}
				String start2 = new String("The Lyndon conjugate of your word ");
				add_line.printf("%s", start2);
				add_line.printf("%s"+":"+"%n", convertinputWord);
				add_line.printf("%s"+"%n", lconvertWord);
				add_line.printf("%n");
				String start3 = new String("The L-Parikh matrix of your word ");
				add_line.printf("%s", start3);
				add_line.printf("%s"+":"+"%n", convertinputWord);
				for (int i=0;i<lparikhMatrix.length;i++) {
					for (int j=0;j<lparikhMatrix.length;j++) {
						add_line.printf("%4d", lparikhMatrix[i][j]);
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
			File uploadedFile = Home.uploadedFile;
			Home.dialog.setVisible(false);
			chooserSave = new JFileChooser();
			//Create file chooser for user to select a location to save the output files to (default to same location as uploaded file)
			chooserSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			disableTextField(chooserSave);  //prevent user from altering file name as these are auto generated
			chooserSave.setDialogTitle("Select Location To Save Output Files");
			chooserSave.setSelectedFile(new File("L-Parikh Matrix Of...")); 
			chooserSave.setCurrentDirectory(uploadedFile);
			int returnVal = chooserSave.showSaveDialog(frame);
			String path = chooserSave.getCurrentDirectory().getAbsolutePath();

			//if user selects a location
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				if (Home.mySwingWorker.isCancelled()) {
					return;
				}
				scanner = new Scanner(uploadedFile);
				scanner.useDelimiter(",|\\n");
				while (scanner.hasNext()){
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
					new LParikhMatrixUploadInterface(word,enteredAlphabet, path, 0);

				}
				scanner.close();
				Home.dialog.dispose();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Home.dialog.dispose();
		}
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
