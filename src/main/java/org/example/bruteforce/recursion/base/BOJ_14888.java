package org.example.bruteforce.recursion.base;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 내가 해야할 최소 작업: 이웃한 두수를 연산하고 결과만 줄것. 그 결과로 연산하는건, 재귀가 해줄것.
 * 가능한 모든 가짓수를 전부 탐색한다 -> 재귀 + 백트래킹
 **/

public class BOJ_14888 {

    static int N;
    static int[] numArr;
    static int[] visited;

    static int max = Integer.MIN_VALUE;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        N = Integer.parseInt(st.nextToken());
        numArr = new int[N];
        visited = new int[4];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            numArr[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 4; i++) {
            visited[i] = Integer.parseInt(st.nextToken());
        }

        dfs(1,numArr[0]);

        System.out.println(max);
        System.out.println(min);

    }


    // return : 연산 결과
    // 난 현재 두 수를 연산한뒤 값으로 넘겨주기만 하면됨
    public static void dfs(int idx, int result) {

        //마지막 숫자 도달. 결과값 반환
        if (idx == N) {
            max = Math.max(max, result);
            min = Math.min(min, result);
            return;
        }
        // 숫자에 연산자 끼워넣기
        for(int i = 0; i < 4; i++){
            //현재 연산자 있는지 체크, 없다면 다른 연산자 사용
            if(visited[i]!=0){
                visited[i]--; //이번 연산자 사용
                dfs(idx+1, calculate(result, numArr[idx],i));
                //백트래킹
                visited[i]++;
            }
        }
    }

    public static int calculate(int a,int b,int operation){
        if (operation == 0) return a+b;
        if (operation == 1) return a-b;
        if (operation == 2) return a*b;
        if (operation == 3) return a/b;

        return 0;
    }

}
