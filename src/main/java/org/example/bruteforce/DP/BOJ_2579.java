package org.example.bruteforce.DP;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2579 {

    static int N;
    static int[] stairScore;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        stairScore = new int[N+1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            stairScore[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solve());

    }

    public static int solve() {

        if(N==1) return stairScore[1];
        if(N==2) return stairScore[1] + stairScore[2];

        int[] dp = new int[N+1]; // dp[i]: i번째 계단까지 밟았을 때 최대값

        dp[1] = stairScore[1];
        dp[2] = stairScore[1] + stairScore[2];

        for(int i = 3; i <= N; i++) {
            // i밟음  & i-1 밟음 , i-2 안 밟음
            int case1 = stairScore[i] + stairScore[i - 1] + dp[i-3];

            // i밟음  & i-1 안 밟음
            int case2 = stairScore[i] + dp[i-2];
            dp[i] = Math.max(case1, case2);

        }
        return dp[N];

    }

}
