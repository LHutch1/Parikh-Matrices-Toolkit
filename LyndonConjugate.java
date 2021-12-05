public class LyndonConjugate {
	public int[] lyndonConj(int[] w) {
		int size = w.length;
		int[] fact = new int[size];
		int fact_count = 0;
		int k = 0;
		int i = 0;
		int j = 0;
		int[] search_word = new int[size+size];
		int[] output = new int[size];
		for (int n = 0; n < size; n++) {
			search_word[n] = w[n];
			search_word[n+size] = w[n];
		}
		while (k < size+size-1) {
			i = k+1;
			j = k+2;
			while (true) {
				if (j == size+size || search_word[i] > search_word[j] ) {
					while (k < i) {
						k = k + (j - i);
						fact[fact_count] = k;
						fact_count = fact_count + 1;
						if (j-1-k == size && ((j - i) % size == 0)) {
							for (int a=0; a<size; a++) {
								output[a] = search_word[k+1+a];
							}
							return output;
						}
					}
					break;
				}
				else if (search_word[i] == search_word[j]) {
					i = i+1;
					j = j+1;
					if (j-1-k == size && ((j - i) % size == 0)) {
						for (int a=0; a<size; a++) {
							output[a] = search_word[k+1+a];
						}
						return output;
					}
				}
				else if (search_word[i] < search_word[j]) {
					i = k+1;
					j = j+1;
					if (j-1-k == size && ((j - i) % size == 0)) {
						for (int a=0; a<size; a++) {
							output[a] = search_word[k+1+a];
						}
						return output;
					}
				}
			}
		}
		return output;
	}
}
