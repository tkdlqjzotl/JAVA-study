package lecture1006;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * 하나씩채워주면서 그 점에서 가로 세로 3*3 체크
 * 작은수부터채워줌
 * 
 * 채워주지못하는경우 바로 전에 채웟던 곳으로돌아가서 새로운 입력
 * 반복
 * 
 * */


//Main
public class B2239 {
	static int sdoku[][]; //스도쿠 맵 
	static boolean isExist[]; //숫자가 이미나온숫자인지 체크하는 배열
	static List<pos> posList = new LinkedList<>(); // 비어있는 공간을 담을 링크드리스트

	static class pos { // 점의 위치 , x,y와 그 곳에 쓰인 숫자 num을 저장하는 객체
		int x;
		int y;
		int num;

		public pos(int x, int y, int num) {
			super();
			this.x = x;
			this.y = y;
			this.num = num;
		}

	}

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		sdoku = new int[9][9];
		for (int i = 0; i < 9; i++) {
			String s = br.readLine();
			for (int j = 0; j < 9; j++) {
				sdoku[i][j] = Integer.parseInt(s.charAt(j) + "");
			}
		}
		//빠르게 맵 입력받기
		
		// 9*9 돌며 0을찾고 채워주는 함수 sdokufill 호출 
		//함수를 만든 이유는 전의 것으로 되돌아가고 재귀적으로 작동되는 기능이 있어서
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sdoku[i][j] == 0) { 
					sdokuFill(j, i, 0);
				}
			}
		}
		
		//결과출력
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(sdoku[i][j]);
			}
			System.out.println();
		}

	}

	private static void sdokuFill(int x, int y, int num) {
		//스도쿠 채우기 함수 , x y 에 num+1 부터 값을 넣어본다
		isExist = new boolean[10]; //숫자 나온거체크 
		squareCheck(x, y); //3*3 사각형체크
		clineCheck(y); //열체크
		rlineCheck(x);//행체크
		
		
		for (int j2 = num+1; j2 < 10; j2++) {//함수호출할떄 쓴 num+1부터 보는데 
			if (!isExist[j2]) {
				//나오지않은 숫자면  스도쿠에 입력하고 
				//posList에 추가해놓고 다음 스도쿠입력을위해 함수를 끝낸다
				
				sdoku[y][x] = j2;
				posList.add(new pos(x, y, j2));
				return;
			}
		}	
		//return이 호출되지않은경우   스도쿠를채우지못한경우 문제가잇는것이다 바로 전부터 살펴본다
		int nowIndex = posList.size() - 1;
		pos nowpos = posList.get(nowIndex);
		
		posList.remove(nowIndex);// poslist에 담겨있떤 바로 전놈은 지워준다. 존재하면 방해가된다
		
		sdoku[nowpos.y][nowpos.x] = 0; // 바로 전이 잘못됐으니 0으로 해주고
		sdokuFill(nowpos.x, nowpos.y,nowpos.num); 
		// 바로전x 바로전y 바로전에넣은 num을 넣어준다
		//그러면 num+1부터 다시 채우기 시작하므로 새로운스도쿠 입력이 된다
		
		sdokuFill(x,y,0);
		//실패한경우 -> 전의전의전의~~~껏들이 모두 다시 성공된 경우 이 함수가 다시 작동되어서 현재 스도쿠에 입력을 다시 시도한다
	}

	private static void clineCheck(int y) {//가로줄 체크 열체크
		for (int j = 0; j < 9; j++) {
			isExist[sdoku[y][j]] = true;
		}

	}

	private static void rlineCheck(int x) {//세로줄 체크 행체크
		for (int i = 0; i < 9; i++) {
			isExist[sdoku[i][x]] = true;
		}

	}

	private static void squareCheck(int x, int y) { //3*3 사각형체크 
		for (int i = (y / 3) * 3; i < (y / 3) * 3 + 3; i++) {
			for (int j = (x / 3) * 3; j < (x / 3) * 3 + 3; j++) {
				isExist[sdoku[i][j]] = true;
			}
		}

	}

}
