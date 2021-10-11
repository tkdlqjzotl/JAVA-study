package lecture1008;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*
 * .: 깨끗한 칸
*: 더러운 칸
x: 가구
o: 로봇 청소기의 시작 위치
 * 더러운 칸의 개수는 10개를 넘지 않으며, 로봇 청소기의 개수는 항상 하나이다.

입력의 마지막 줄에는 0이 두 개 주어진다.
 * 
 * 1로봇시작위치
 * 여러개의 더러운칸이잇다
 * bfs로 가장가까운칸을구한다
 * 갈수잇는칸이없다 ->철책에막혀잇다 -1출력
 * 갈수잇다  -> 간다   위치 이동후 반복 
 *  
 *  2 로봇시작위치 
 *  먼지위치들을 다 저장해둔다
 *  로봇시작위치에서 각각의 먼지거리
 *  먼지각각에서 먼지각각의 거리를 구한다 

 거리를구하는 함수 만들기!
 거리를못구한다 -> 갈수없다 ->-1 출력
 *  
 *  
 *  나올수잇는경우
 *  w h 0 그만
 *  
 *  
 *  
 *  w or h 0 안돌아가야함
 *  
 *  
 *  w h 정상 
 *  
 *  o는 무조건 1
 *  
 *  
 *  * 더러운곳은 0

1~10 길찾을수잇어야함
 *  
 *  
 * Main
 * */
public class B4991 {
	static int W, H, minDistance, distanceArray[][], INF = 999_999_999;
	static char map[][];
	static int dx[] = { 0, 0, 1, -1 };
	static int dy[] = { 1, -1, 0, 0 };
	static List<pos> dirtyPosList;
	static Queue<pos> posQueue ;
	static boolean dirtyFound[], mapVisited[][],isDebug=false;
	//위치 객체
	static class pos {
		int x;
		int y;

		public pos(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		testcase : while (true) {
			dirtyPosList = new LinkedList<>();dirtyPosList.clear();
			posQueue = new LinkedList<>();posQueue.clear();
			minDistance=INF; //최소거리의합
			pos startpos = null;
			
			st = new StringTokenizer(br.readLine());

			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			if (W == 0 && H == 0)
				break;

			map = new char[H][W];
			for (int i = 0; i < H; i++) {
				String s = br.readLine();
				for (int j = 0; j < W; j++) {
					map[i][j] = s.charAt(j);
					if (map[i][j] == 'o') {
						startpos = new pos(j, i);
						if(isDebug) System.out.println(i+" "+j);
					} else if (map[i][j] == '*') {
						dirtyPosList.add(new pos(j, i));
						if(isDebug) System.out.println(i+" "+j);
					}
				}
			}
			
			dirtyFound = new boolean[dirtyPosList.size()];
			distanceArray = new int[dirtyPosList.size() + 1][dirtyPosList.size() + 1];

			//각점까지의 거리를 배열에저장
			for (int j = 1; j < dirtyPosList.size() + 1; j++) {
				distanceArray[0][j] = bfs(startpos.x, startpos.y, 0, dirtyPosList.get(j - 1).x,
						dirtyPosList.get(j - 1).y);
				if(distanceArray[0][j]==INF)
				{
					System.out.println(-1);
					continue testcase;
				}	
			}

			for (int i = 1; i < dirtyPosList.size() + 1; i++) {
				for (int j = 1; j < dirtyPosList.size() + 1; j++) {
					distanceArray[i][j] = bfs(dirtyPosList.get(i - 1).x, dirtyPosList.get(i - 1).y, 0,
							dirtyPosList.get(j-1).x, dirtyPosList.get(j-1).y);
					
					if(i!=j&&distanceArray[i][j]==INF)
					{
						System.out.println(-1);
						continue testcase;
					}	
				}
			}
			if(isDebug) System.out.println(Arrays.deepToString(distanceArray));
			
			perm(0,0,0);			
			System.out.println(minDistance);
		}
	}
	//순열
	private static void perm(int distanceSum, int cnt,int beforeNum) {
		if(cnt==dirtyPosList.size())
		{
			minDistance= Math.min(minDistance, distanceSum);	
			if(isDebug) System.out.println("결과"+distanceSum);
			return;			
		}

		for (int j = 0; j < dirtyFound.length; j++) {
			if(!dirtyFound[j]&&distanceArray[beforeNum][j+1]!=INF)
			{
				dirtyFound[j]=true;
				//i+= distanceArray[k][j];
				if(isDebug) System.out.print((j+1)+" "+distanceArray[beforeNum][j+1]+" ");
				perm(distanceSum+distanceArray[beforeNum][j+1],cnt+1,j+1);
				dirtyFound[j]=false;
			}
		}
		if(isDebug) System.out.println("실패?");
	}

	private static int bfs(int x, int y, int nowdistance, int arriveX, int arriveY) {
		/*
		 * if (nowDirty == dirtyPosList.size()) { return nowdistance; }
		 */
		if (x == arriveX && y == arriveY)
			return INF;
		posQueue.clear();
		posQueue.add(new pos(x, y));
		mapVisited = new boolean[H][W];		
		mapVisited[y][x] = true;
		while (!posQueue.isEmpty()) {
			nowdistance++;
			int qSize = posQueue.size();
			for (int i = 0; i < qSize; i++) {
				pos nowpos = posQueue.poll();
				for (int d = 0; d < 4; d++) {
					int nx = nowpos.x + dx[d];
					int ny = nowpos.y + dy[d];

					if (nx < 0 || nx >= W || ny < 0 || ny >= H || map[ny][nx] == 'x' || mapVisited[ny][nx]) {
						continue;
					}

					if (nx == arriveX && ny == arriveY) {
						return nowdistance;
					} else {//if (map[ny][nx] != '*') {
						posQueue.add(new pos(nx, ny));
						mapVisited[ny][nx] = true;
					}
				}
			}
		}
		return INF;
	}

}
