import java.util.Arrays;

public class LyndonConjugate {
	public int[] lyndonConj(int[] w) {
		int size = w.length;
		int[] output = new int[size];
		int[] search_word_test = new int[size+size];
		int[] final_word = new int[size];
		for (int n = 0; n < size; n++) {
			search_word_test[n] = w[n];
			search_word_test[n+size] = w[n];
			final_word[n] = w[n];
		}
		int power = 0;
		for (int index=1; index<size-1; index++) {
			int[] current_word = new int[size];
			for (int letter=0; letter<size; letter++) {
				current_word[letter] = search_word_test[index+letter];
			}
			if (Arrays.equals(current_word,w)) {
				power = 1;
				break;
			}
		}
		if (power == 1) {
			for (int size_of_test = 1; size_of_test < size; size_of_test++) {
				if (size % size_of_test == 0) {
					int[] test_word = new int[size_of_test];
					for (int test_index = 0; test_index < size_of_test; test_index++) {
						test_word[test_index] = w[test_index];
					}
					int is_power = 0;
					int is_match = 1;
					int[] current_test_factor = new int[size_of_test];
					for (int root_position = size_of_test; root_position < size; root_position += size_of_test) {
						for (int it=0; it < size_of_test; it++) {
							current_test_factor[it] = w[root_position + it];
						}
						if (!Arrays.equals(current_test_factor, test_word)) {
							is_match = 0;
						}
					}
					if (is_match == 1) {
						is_power = 1;
					}
					if (is_power == 1) {
						size = size_of_test;
						final_word = new int[size];
						final_word = test_word;
						break;
					}
				}
			}
		}
		int[] fact = new int[size];
		int fact_count = 0;
		int k = 0;
		int i = 0;
		int j = 0;
		int[] search_word = new int[size+size];
		for (int n = 0; n < size; n++) {
			search_word[n] = final_word[n];
			search_word[n+size] = final_word[n];
		}
		int index = 0;
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
							index = k+1;
							break;
						}
					}
					break;
				}
				else if (search_word[i] == search_word[j]) {
					i = i+1;
					j = j+1;
					if (j-1-k == size && ((j - i) % size == 0)) {
						index = k+1;
						break;
					}
				}
				else if (search_word[i] < search_word[j]) {
					i = k+1;
					j = j+1;
					if (j-1-k == size && ((j - i) % size == 0)) {
						index = k+1;
					}
				}
			}
		}
		for (int a=0; a<w.length; a++) {
			output[a] = search_word_test[index+a];
		}
		return output;
	}
}
