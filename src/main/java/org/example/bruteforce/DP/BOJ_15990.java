package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_15990 {

    static int T;
    static int N;
    static int[][] dp = new int[4][100001];
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        T = Integer.parseInt(st.nextToken());

        // dp 초기화
        for(int i = 0; i < 4; i++) {
            Arrays.fill(dp[i], -1);
        }

        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());

            int result = 0;

            if(N >= 1) result = (result + solve(1, N-1)) % 1000000009;
            if(N >= 2) result = (result + solve(2, N-2)) % 1000000009;
            if(N >= 3) result = (result + solve(3, N-3)) % 1000000009;

            sb.append(result).append('\n');
        }

        System.out.print(sb);
    }

    /**
     * prev: 방금전에 내가 뽑은 숫자
     * renmain: 문제의 조건에 맞춰 1,2,3의 합으로 표현해야하는 숫자.
     * remain을 1,2,3의 합으로 나타내는 경우의수(연속조건을 만족한 채로)
     * solve(prev, remain) = "이전에 prev를 골랐고, 앞으로 remain을 만들어야 할 때의 경우의 수"
     *
     * */
    public static int solve(int prev, int remain) {

        if(remain == 0) {
            return 1;
        }

        //메모제이션 체크
        if(dp[prev][remain] != -1) {
            return dp[prev][remain];
        }

        int count = 0;
        for (int i =1; i <= 3; i++) {
            if(i != prev && remain >= i) {
                count += solve(i, remain - i);
                count %= 1000000009;  // 문제 조건
            }

        }
        dp[prev][remain] = count;
        return count;
    }
}
