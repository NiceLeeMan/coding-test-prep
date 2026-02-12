package org.example.bruteforce.recursion.base;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 * 1) 재귀함수를 활용한다. 각 재귀는 "내가 이번 행에서  모든 열에 배치해보고, 되는 것들 알려줄게. 다음 행은 너가 해"
 * */
public class BOJ_9663 {

    static int N;
    static int[] cols; //열을 의미 ex 1열 2열
    static int sum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        cols = new int[N];

        //전부 -1로 초기화
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                cols[i] = -1;
            }
        }

        System.out.println(place(0));
    }

    //row: 이번에 내가 배치할려고하는 행
    public static int place(int row){

        //퀸을 다 배치하면 종료
        if(row == N){
            sum++;
            return sum;
        }

        //i는 열을 의미
        for(int i = 0 ; i < N ; i++){
            cols[row] = i; //row행의 i열에 퀸을 배치한다. col[1] = 3은 1행의 3열에 퀸이있다는 것.

            //배치 조건 만족하면 배치하기.
            if(isPossible(row)){
                place(row+1);
            }

            //백트래킹 패턴
            cols[row] = -1;

        }
        return sum;
    }

    //다음 행의 배치할 수 있는 위치 찾는 함수
    public static boolean isPossible(int row){

        for(int i = 0 ; i < row ; i++){
            //같은 열 체크
            if (cols[row] == cols[i]) return false;
            //대각선 체크: 행차이 == 열차이
            if(Math.abs(row-i) == Math.abs(cols[row]-cols[i])) return false;

        }

        return true;

    }

}
