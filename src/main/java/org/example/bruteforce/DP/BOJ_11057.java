package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11057 {

    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N= Integer.parseInt(st.nextToken());

        System.out.println(solve());


    }

    public static int solve(){

        int[][] dp = new int[N+1][10];

        for (int j = 0; j <= 9; j++) {
            dp[1][j] = 1;
        }

        if(N == 1){
            return 10;
        }

        for (int i = 2; i <= N; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= j; k++) {
                    dp[i][j] += dp[i-1][k];
                    dp[i][j] %= 10007;
                }
            }
        }

        int sum = 0;

        for(int i=0; i<=9; i++){
            sum += dp[N][i];
            sum %= 10007;
        }

       return sum;

    }

}
