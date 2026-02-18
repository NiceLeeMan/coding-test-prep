package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1149 {

    static int N;
    static int[][] RGB;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());


        N = Integer.parseInt(st.nextToken());
        RGB = new int[N+1][3];

        for(int i=1;i <= N;i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0;j<3;j++){
                RGB[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(solve());



    }

    public static int solve(){

        int[][] dp = new int[N+1][3];// i번째 집을 특정 컬러(R,G,B)로 칠했을때 1~i번째 집까지의 총 비용의 최소값

        dp[1][0] = RGB[1][0];
        dp[1][1] = RGB[1][1];
        dp[1][2] = RGB[1][2];

        if(N==1){
            return Math.min(RGB[1][0], Math.min(RGB[1][1],RGB[1][2]));
        }

        for(int i=2; i<=N; i++){

            int case1 = RGB[i][0] + Math.min(dp[i-1][1], dp[i-1][2]);
            int case2 = RGB[i][1] + Math.min(dp[i-1][0], dp[i-1][2]);
            int case3 = RGB[i][2] + Math.min(dp[i-1][0], dp[i-1][1]);

            dp[i][0] = case1; //i번째 집이 R일때 1~i번째 집까지 색칠하는데 드는 최소 비용
            dp[i][1] = case2; //i번째 집이 G일때 1~i번째 집까지 색칠하는데 드는 최소 비용
            dp[i][2] = case3; //i번째 집이 B일때 1~i번째 집까지 색칠하는데 드는 최소 비용
        }

        // i번째 집이 R, G, B 중 어떤것을 칠했어야. 가장 최소비용인지 따짐
        return Math.min(dp[N][0], Math.min(dp[N][1], dp[N][2]));



    }
}
