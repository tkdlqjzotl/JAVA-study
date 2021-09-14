package lecture0914;
/*왜 시간초과가날까 생각
 * 고기가잇지만 ! 갈수없는경우에는 bfs에서 검증을안해준다 
 * 
 * */
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class pos {
	int x;
	int y;

	public pos(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/*
	 * @Override public int compareTo(pos o) { if (this.y == o.y) return -(this.x -
	 * o.x);
	 * 
	 * return -(this.y - o.y); }
	 */

}

public class B16236 {
	static boolean debugMode=true;
	static int N, minDistance, nowDistance,totalTime;
	static int[][] map;
	static int sharkSize, sharkEatFishNum;
	static boolean callMother, findFish, visited[][];
	static pos sharkPos;
	static Queue<pos> moveQ = new LinkedList<>();
	static PriorityQueue<pos> eatPQ = new PriorityQueue<>((o1, o2) -> {
		if (o1.y == o2.y)
			return (o1.x - o2.x);

		return (o1.y - o2.y);

	});

	static void checkCallMother() {
		if(debugMode)System.out.println("checkcallmother");
		callMother = true;
		outer: for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] < sharkSize &&map[i][j]>0) {
					callMother = false;
					break outer;
				}
			}
		}
		//return callMother;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		map = new int[N][N];
		
		sharkSize = 2;
		callMother = true;
		sharkEatFishNum = 0;
		totalTime=0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = sc.nextInt();
				if (map[i][j] < sharkSize &&map[i][j]>0) {
					callMother = false;
					// pq.add(new pos(j, i));
				}
				if (map[i][j] == 9)
					{
						sharkPos = new pos(j, i);
						moveQ.add(sharkPos);
					}
			}
		}
		/*if (callMother) {
			System.out.println(0);
			return;
		}*/
		//System.out.println(Arrays.deepToString(map));

		while (!callMother &&!moveQ.isEmpty()) {
			if(debugMode) for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					System.out.print(map[i][j]+" ");
				}
				System.out.println();
			}
			minDistance = Integer.MAX_VALUE;
			nowDistance = 1;
			
			findFish = false;
			visited = new boolean[N][N];
			
			bfs();
			if(findFish)eatFish();
		}
		
		System.out.println(totalTime);
		return;
	}

	static int dx[] = { -1, 0, 1, 0 };
	static int dy[] = { 0, -1, 0, 1 };

	private static void bfs() {
		while (!findFish && !moveQ.isEmpty()) {
			int ms = moveQ.size();
			for (int qsi = 0; qsi < ms; qsi++) {				
				pos nowpos = moveQ.poll();
				if(debugMode)System.out.println("bfs 지금어디니 x"+nowpos.x+"y"+nowpos.y+"nowDistance"+nowDistance);
				visited[nowpos.y][nowpos.x] = true;
				for (int i = 0; i < 4; i++) {
					int nx = nowpos.x + dx[i];
					int ny = nowpos.y + dy[i];

					if (nx < 0 || nx >= N || ny < 0 || ny >= N ||
							map[ny][nx] > sharkSize || 
							visited[ny][nx]||
							nowDistance>minDistance)
						continue;
					
					visited[ny][nx] = true;
					
					if (map[ny][nx] < sharkSize && map[ny][nx]>0) {
						eatPQ.add(new pos(nx, ny));
						findFish = true;
						minDistance = Math.min(nowDistance, minDistance);
						if(debugMode)	System.out.println("eqtpq추가 x"+nx+"y"+ny);
					}

					if (!findFish)
						{
							moveQ.add(new pos(nx, ny));							
						}
				}
				
			}
			nowDistance++;
		}

	}

	private static void eatFish() {
		
		pos targetPos= eatPQ.poll();
		sharkEatFishNum++;
		map[targetPos.y][targetPos.x] = 9;
		map[sharkPos.y][sharkPos.x]=0;
		sharkPos= new pos(targetPos.x,targetPos.y);
		
		if(debugMode)System.out.println("eatfish"+"목표x"+targetPos.x+"목표y"+targetPos.y+"상어크기"+sharkSize);
		if (sharkEatFishNum == sharkSize) {
			sharkEatFishNum = 0;
			sharkSize++;			
		}
		checkCallMother();
		moveQ.clear();
		eatPQ.clear();
		moveQ.add(sharkPos);
		totalTime+=minDistance;
	}

}
