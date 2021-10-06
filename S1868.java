package lecture1006;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;
//Solution
//bfs활용
public class S1868 {
	static int N,tempClick,MinClick; // 입력N  일회용클릭 최소클릭수 변수
	static char firstBfsMap[][],secondBfsMap[][]; //지뢰와 숫자체크 한 뒤 다시 비교할거라 두 개의 맵 생성
	static boolean isVisited[][]; //bfs때 겹치는 현상이 발생해서 방문배열
	static int dx[] = { 1, 1, 1, 0, 0, -1, -1, -1 };//8방탐색해야된다
	static int dy[] = { -1, 0, 1, 1, -1, -1, 0, 1 };
	static Deque<pos> qPos = new LinkedList<>(); //뒤로도앞으로도 삭제 가능해야하니 deque 설정
	static class pos { //지뢰 위치 객체
		int x;
		int y;

		public pos(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T;
		T = sc.nextInt();

		for (int test_case = 1; test_case <= T; test_case++) {
			N = sc.nextInt();
			firstBfsMap = new char[N][N];
			isVisited=new boolean[N][N];
			secondBfsMap  = new char[N][N];
			tempClick=0;
			MinClick= N*N; //여기까지초기화부분
			for (int i = 0; i < N; i++) {
				String s = sc.next();
				for (int j = 0; j < N; j++) {
					secondBfsMap[i][j]=	firstBfsMap[i][j] = s.charAt(j);
				}
			} //입력부분

			//처음 bfs는 기존 map을 돌아다니며 지뢰체크하고 숫자를 써놓는다
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (firstBfsMap[i][j] == '.') { 
						tempClick++;
						bfs(i, j,firstBfsMap);
					}
				}
			}
			
			MinClick= Math.min(MinClick, tempClick);
			
			
			//두번째 bfs 진짜로 최솟값을 찾아낸다 
			//처음bfs떄 만들어놓은 숫자 지뢰 배열을 활용하여 0을 클릭하는게 최적이라고 생각하여
			//0을 먼저 싹다 클릭 후 
			//나머지 지뢰근처 숫자들을 click수에 더한다
			isVisited=new boolean[N][N];
			tempClick=0;
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					//처음bfs때 바꾼 map은 0이고(최적의클릭)
					//두번쨰 bfs떄 바꾼 secondBfsMap은 0이아니여야함(이미클릭해서 방문되어지면 안클릭해야함)
					if (firstBfsMap[i][j] == '0'&&secondBfsMap[i][j]!='0') {
						tempClick++;
						bfs(i, j,secondBfsMap);
					}
				}
			}
			//나머지 0주위에잇지않은 숫자들 지뢰주위숫자들은 발견되지않앗다
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (secondBfsMap[i][j] == '.') {//두번쨰bfs에서 탐색못한 공간은 일일히 다 클릭해야하므로 +1해준다
						tempClick++;
					}
				}
			}
			
			
			//처음 bfs와 비교
			MinClick= Math.min(MinClick, tempClick);	
			//결과값출력
			System.out.println("#"+test_case+" "+MinClick);
			
		}

	}
	//지뢰찾기 시작
	private static void bfs(int i, int j, char tempmap[][]) {
		qPos.add(new pos(j, i));
		isVisited[i][j]=true;
		
		while (!qPos.isEmpty()) {
			int bomb = 0,canMove=0; //지뢰수, 빈공간수
			pos nowPos = qPos.poll(); 
			
			for (int d = 0; d < 8; d++) {
				int nx = nowPos.x + dx[d];
				int ny = nowPos.y + dy[d];

				if (nx < 0 || nx >= N || ny < 0 || ny >= N||isVisited[ny][nx])
					continue;
				//지뢰발견하면 지뢰수 추가
				if (tempmap[ny][nx] == '*') {
					bomb++;
					continue;
				}
				else if(tempmap[ny][nx] == '.') //빈공간이면 빈공간수 추가, 방문, 큐에추가
				{
					canMove++;
					isVisited[ny][nx]=true;
					qPos.add(new pos(nx,ny));					
				}
			}
			tempmap[nowPos.y][nowPos.x]=(bomb+"").charAt(0);//지뢰수만큼 그 공간에 입력
			if(bomb>0) //주위에 지뢰가 있네!
			{					
				//아까 주위에 빈공간 만큼 반복하며 방문체크해제 , 큐에서 제거 이래서 deque를 쓴것
				for (int k = 0; k< canMove; k++) {
					isVisited[qPos.getLast().y][qPos.getLast().x]=false;
					qPos.removeLast();
				}
			}

		}

	}

}

/*
 * 
 * 효율적인방법인 클릭 어떤게효율적인방법일까 최소의클릭이가능할까
 * 
 * bfs로 8방향둘러보고 지뢰가없으면 진행 -> 다음꺼에도 팔방향돌아서 지뢰없으면 0표ㅕ시 지뢰가하나라도잇으면 그만. 지뢰개수 써주기
 * 
 * 멥체크 다 클릭햇나 체크
 * 
 * 
 * 
 */
