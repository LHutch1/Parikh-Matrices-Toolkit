public class ParikhMatrixCalculator {

	public int[][] ParikhMatrix(int[] arr,int size) {
		int[][] matrix = new int[size][size];
		
		int[][][] alphabet = new int[size-1][size][size];
		//create array of matrices for all letters in alphabet
		int[][] tempmatrix = new int[size][size];
		for (int k=0; k<=size-2;k++) {	
			if (Home.mySwingWorker.isCancelled()) {
				return null;
			}
			for (int i=0; i<=size-1;i++) {
				for (int j=0; j<=size-1;j++) {
					if (i==j || (i+1==j && i==k)) {
						tempmatrix[i][j] = 1;
					}
					else {
						tempmatrix[i][j] = 0;
					}
				}
			}
			for (int i=0; i<=size-1;i++) {
				for (int j=0; j<=size-1;j++) {
					alphabet[k][i][j]=tempmatrix[i][j];
				}
			}
		}
		
		//now cycle through the world and multiply matrices to create matrix;
		matrix = alphabet[arr[0]];
		for (int i = 1;i<arr.length;i++) {
			matrix = MatrixMultiplier(matrix,alphabet[arr[i]]);
		}
		return matrix;
	}
	
	
	
	public int[][] MatrixMultiplier(int[][] m1,int[][] m2) {
		
		int size = m1.length;
		int[][] c = new int[size][size];
		for(int i=0;i<size;i++){  
			if (Home.mySwingWorker.isCancelled()) {
				return null;
			}
			for(int j=0;j<size;j++){    
				c[i][j]=0;      
				for(int k=0;k<size;k++){      
					c[i][j]+=m1[i][k]*m2[k][j];      
				}
			}  
		}
		return c;
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
