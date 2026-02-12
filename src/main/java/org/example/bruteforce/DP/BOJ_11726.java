package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 * 문제를 분석해보니, 결국 피보나치
 * */
public class BOJ_11726 {

    static int n;
    static int[] dp = new int[1001];


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());

        System.out.println(fun_2(n));


    }

    public static int fun_1(int n){

        if(n == 1) return 1;
        if(n == 2) return 2;

        if(dp[n] != 0) return dp[n];

        dp[n] = (fun_1(n-1) + fun_1(n-2))%10007;

        return dp[n];

    }

    public static int fun_2(int n){

        dp[1] = 1;
        dp[2] = 2;

        if(n == 1) return dp[1];
        if(n == 2) return dp[2];

        for(int i = 3; i <= n; i++){
            dp[i] = (dp[i-1] + dp[i-2]) % 10007;
        }

        return dp[n];
    }
}
