package lecture1006;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
 * N * N 한줄씩비교하자
 * 
 * 어케비교할건데
 * 
 * 설치가능이면 ++
 * 
 * 
 * 
 * 
 * 
 * Solution
 * */
public class S4014 {
	static int N, X, map[][];

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			map = new int[N][N];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			int nowNum = 0, result = 0, nowLength = 1;

			outer: for (int i = 0; i < N; i++) {
				nowNum = map[i][0];
				nowLength = 1;
				for (int j = 1; j < N; j++) {
					if (Math.abs(nowNum - map[i][j]) > 1)
						continue outer;

					if (nowNum == map[i][j])// 현재맵의숫자가 이전까지번호랑같으면 현재번호길이++
						nowLength++;
					else if (nowNum > map[i][j]) {// 이전숫자가크다 내려왓다 다음껄봐야한다
						nowLength = 0;
						for (int j2 = j; j2 < N; j2++) {
							if (map[i][j] == map[i][j2])
								nowLength++;
							else
								break;
						}
						if (nowLength < X)
							continue outer;
						else {
							if (j + nowLength >= N)
								break;
							//nowLength = nowLength-X;
							nowLength = 0;
							nowNum=map[i][j];
							j=j  + X -1;
							/*	nowNum = map[i][j + nowLength - 1];

							else
								break;*/
						}

					} else {// 이전숫자가 작다 이제까지 번호길이랑 X랑비교해서 X이상이면 넘어가
						if (nowLength < X)
							continue outer;
						else {
							nowNum = map[i][j];
							nowLength = 1;
						}
					}
				}
				//System.out.println("성공 i행" + i);
				// 앞에모든조건들을만족하면 활주로 성공
				result++;
			}

			outer: for (int j = 0; j < N; j++) {
				nowNum = map[0][j];
				nowLength = 1;
				for (int i = 1; i < N; i++) {
					if (Math.abs(nowNum - map[i][j]) > 1)
						continue outer;

					if (nowNum == map[i][j])// 현재맵의숫자가 이전까지번호랑같으면 현재번호길이++
						nowLength++;
					else if (nowNum > map[i][j]) {// 이전숫자가크다 내려왓다 다음껄봐야한다
						nowLength = 0;
						for (int i2 = i; i2 < N; i2++) {
							if (map[i][j] == map[i2][j])
								nowLength++;
							else
								break;
						}
						if (nowLength < X)
							continue outer;
						else {
							if (i + nowLength >= N)
								break;
							nowLength = 0;
							nowNum=map[i][j];
							i=i + X - 1;
						}

					} else {// 이전숫자가 작다 이제까지 번호길이랑 X랑비교해서 X이상이면 넘어가
						if (nowLength < X)
							continue outer;
						else {
							nowNum = map[i][j];
							nowLength = 1;
						}
					}
				}
				// 앞에모든조건들을만족하면 활주로 성공
				result++;
				//System.out.println("성공 j열" + j);
			}

			System.out.println("#" + t + " " + result);
		}

	}

}
