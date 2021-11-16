package lecture1116;

import java.util.Scanner;

//Main
public class B14501 {

	public static void main(String[] args) {
		int N;
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();

		int T[] = new int[N];
		int P[] = new int[N];
		int result[] = new int[N];
		// boolean checked[] = new boolean[N];

		for (int i = 0; i < N; i++) {
			T[i] = sc.nextInt();
			P[i] = sc.nextInt();
		}

		for (int i = N - 1; i >= 0; i--) {
			if (i < N - 1)
				result[i] = result[i + 1];

			if (T[i] == 1) {
				result[i] += P[i];
				// checked[i] = true;
				continue;
			}

			if (i + T[i] > N)
				continue;
			
			if(i+T[i]<N)
				result[i] = Math.max(result[i + T[i]] + P[i], result[i]);
			else
				result[i] = Math.max(P[i], result[i]);	
			/*
			 * int sum = 0; for (int j = 1; j < T[i]; j++) { if (checked[i + j]) { sum +=
			 * P[i + j]; } } if (sum < P[i]) { checked[i] = true; result[i] += P[i] - sum;
			 * for (int j = 1; j < T[i]; j++) { if (checked[i + j]) { checked[i + j] =
			 * false; } } }
			 */

		}
		System.out.println(result[0]);
	}
}

/*
 * 2 20 이잇다치면 3 15 5 20 1 10
 * 
 * 
 * 
 * 
 * 
 */
