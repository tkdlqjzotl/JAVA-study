package lecture0929;

import java.util.Scanner;
//DFS
//Main
public class B4485 {
	static int N, minRupee;// result,
	static int map[][], rupeeMap[][];

	static boolean isVisited[][];
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int problemCount = 1;
		while (true) {
			N = sc.nextInt();			
			if (N == 0)
				return;
			
			minRupee = 9 * (N * 2-1);// result=0;
			map = new int[N][N];
			rupeeMap = new int[N][N];
			isVisited=new boolean[N][N];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					map[i][j] = sc.nextInt();
					rupeeMap[i][j] = minRupee;
				}
			}

			dfs(0, 0, map[0][0]);

			System.out.println("Problem " + (problemCount++) + ": " + minRupee);
		}

	}

	static int dx[] = { 1, -1, 0, 0 };
	static int dy[] = { 0, 0, 1, -1 };

	private static void dfs(int i, int j, int nowRupee) {
		isVisited[i][j]=true;
		rupeeMap[i][j] = nowRupee;
		if (i == N - 1 && j == N - 1) {
			minRupee = nowRupee;
			return;
		}

		for (int d = 0; d < 4; d++) {
			int nx = j + dx[d];
			int ny = i + dy[d];

			if (nx < 0 || nx >= N || ny < 0 || ny >= N || nowRupee + map[ny][nx] >= rupeeMap[ny][nx]
					|| nowRupee + map[ny][nx] >= minRupee
				||isVisited[ny][nx])
				continue;

			dfs(ny, nx, nowRupee + map[ny][nx]);
			isVisited[ny][nx]=false;
		}

	}

}
