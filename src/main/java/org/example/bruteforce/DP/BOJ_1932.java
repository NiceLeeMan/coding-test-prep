package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1932 {
    static int N;
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        arr = new int[N+1][N+1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= i; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(solve());


    }

    public static int solve(){

        int[][] dp = new int[N+1][N+1];
        dp[1][1] = arr[1][1];

        if(N == 1){
            return dp[1][1];
        }


        //i값은 현재 깊이, J값든
        for(int i = 2; i <= N; i++){
            dp[i][1] = arr[i][1] + dp[i-1][1];           // 왼쪽 가장자리: 위에서만 옴
            dp[i][i] = arr[i][i] + dp[i-1][i-1];

            for(int j = 2; j < i; j++){
                dp[i][j] = arr[i][j] + Math.max(dp[i-1][j-1],dp[i-1][j]);
            }

        }

        int max = 0;

        for (int j = 1; j <= N; j++) {
            max = Math.max(max, dp[N][j]);
        }

        return max;


    }
}
