package lecture0914;

import java.util.Arrays;
import java.util.Scanner;

public class B1149 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		int [][] rgbStreet = new int [N][3];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 3; j++) {
				rgbStreet[i][j]=sc.nextInt();				
			}			
		}
		
		for (int i = 1; i < N; i++) {			
			for (int j = 0; j < 3; j++) {				
				rgbStreet[i][j] += Math.min(rgbStreet[i-1][(j+1)%3], rgbStreet[i-1][(j+2)%3]);
			}			
		}
		
		Arrays.sort(rgbStreet[N-1]);
		System.out.println(rgbStreet[N-1][0]);
	}
}
