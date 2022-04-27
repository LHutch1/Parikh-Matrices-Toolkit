
public class ParikhMatrixCalculator {
	
	public int[][] ParikhMatrix(int[] arr,int size){
		int[][] matrix = new int[size][size];
		for (int i=0; i<size; i++) {
			matrix[i][i] = 1;
		}
		for (int i=0; i< arr.length; i++) {
			int col = arr[i];
			for (int row=0; row<size; row++) {
				matrix[row][col+1] = matrix[row][col+1] + matrix[row][col];
			}
		}
		return matrix;
	}
	
	
	public String getCharForNumber(int i) {
	    return i >= 0 && i < 26 ? String.valueOf((char)(i + 'a')) : null;
	}
	
	
	public int getNumberForChar(String i) {
		String input = i.toLowerCase();
		String a = "abcdefghijklmnopqrstuvwxyz";
		return a.indexOf(input.charAt(0))+1;
	}
	
	
}