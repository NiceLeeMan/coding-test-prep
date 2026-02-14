package org.example.bruteforce.DP;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_11727 {

    static int n;
    static int[] dp = new int[10001];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        System.out.println(func_1(n));


    }

    // Bottom-Up방식
    public static int func_1(int n){

        dp[1] = 1;
        dp[2] = 3;

        if(n == 1) return dp[1];
        if(n == 2) return dp[2];

        for(int i = 3; i <=n; i++){
            dp[i] = (dp[i-1] + (2 * dp[i-2])) % 10007;
        }

        return dp[n];

    }
}
