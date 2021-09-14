package lecture0914;

import java.util.Scanner;

public class B1493 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		
		int [] memo = new int[N+1];
		
		for (int i = 2; i <= N; i++) 
		{
			memo[i]=memo[i-1]+1;
			if(i%2==0)
				memo[i]= Math.min(memo[i/2]+1, memo[i]);
			if(i%3==0)
				memo[i]= Math.min(memo[i/3]+1, memo[i]);
		}
		
		System.out.println(memo[N]);
	}

}
