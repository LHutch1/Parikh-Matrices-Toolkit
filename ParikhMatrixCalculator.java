public class ParikhMatrixCalculator {

	public int[][] ParikhMatrix(int[] arr,int size) {
		int[][] matrix = new int[size][size];
		
		int[][][] alphabet = new int[size-1][size][size];
		//create array of matrices for all letters in alphabet
		int[][] tempmatrix = new int[size][size];
		for (int k=0; k<=size-2;k++) {	
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
	
	
	
	public int[][] MatrixMultiplier(int[][] A,int[][] B) {
		
		int n = A.length;
        int[][] R = new int[n][n];
        if (n == 1)
            R[0][0] = A[0][0] * B[0][0];
        else {
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];
            split(A, A11, 0, 0);
            split(A, A12, 0, n / 2);
            split(A, A21, n / 2, 0);
            split(A, A22, n / 2, n / 2);
            split(B, B11, 0, 0);
            split(B, B12, 0, n / 2);
            split(B, B21, n / 2, 0);
            split(B, B22, n / 2, n / 2);
            int[][] M1 = MatrixMultiplier(add(A11, A22), add(B11, B22));
            int[][] M2 = MatrixMultiplier(add(A21, A22), B11);
            int[][] M3 = MatrixMultiplier(A11, sub(B12, B22));
            int[][] M4 = MatrixMultiplier(A22, sub(B21, B11));
            int[][] M5 = MatrixMultiplier(add(A11, A12), B22);
            int[][] M6 = MatrixMultiplier(sub(A21, A11), add(B11, B12));
            int[][] M7 = MatrixMultiplier(sub(A12, A22), add(B21, B22));
            int[][] C11 = add(sub(add(M1, M4), M5), M7);
            int[][] C12 = add(M3, M5);
            int[][] C21 = add(M2, M4);
            int[][] C22 = add(sub(add(M1, M3), M2), M6);
            join(C11, R, 0, 0);
            join(C12, R, 0, n / 2);
            join(C21, R, n / 2, 0);
            join(C22, R, n / 2, n / 2);
        }
        return R;
	}
	
	
	public String getCharForNumber(int i) {
	    return i >= 0 && i < 26 ? String.valueOf((char)(i + 'a')) : null;
	}
	
	
	public int[][] sub(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }
 

    public int[][] add(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    
    public void split(int[][] P, int[][] C, int iB, int jB)
    {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }

    
    public void join(int[][] C, int[][] P, int iB, int jB)
    {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                P[i2][j2] = C[i1][j1];
    }
    
    
	public int getNumberForChar(String i) {
		String input = i.toLowerCase();
		String a = "abcdefghijklmnopqrstuvwxyz";
		return a.indexOf(input.charAt(0))+1;
	}
	
	
}
