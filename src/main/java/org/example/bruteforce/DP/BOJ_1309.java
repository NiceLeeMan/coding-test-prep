package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1309 {

    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        System.out.println(solve());
    }


    public static int solve() {
        int[][] dp = new int[N+1][4];

        dp[1][1] = 1;
        dp[1][2] = 1;
        dp[1][3] = 1;

        if(N == 1){
            return 3;
        }

        for(int i=2; i<=N; i++){

           dp[i][1] = (dp[i-1][2] + dp[i-1][3])%9901;
           dp[i][2] = (dp[i-1][1] + dp[i-1][3]) %9901 ;
           dp[i][3] = (dp[i-1][1] + dp[i-1][2]+dp[i-1][3]) %9901;

        }

        return dp[N][1]+dp[N][2]+dp[N][3]%9901;

    }
}
