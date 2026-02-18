package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2156 {

    static int N;
    static int[] arr; // 포도주 점수를 담고있는 배열


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        arr = new int[N+1];

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            arr[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solve());
    }


    public static int solve(){

        if (N == 1) return arr[1];
        if (N == 2) return arr[1] + arr[2];

        int[] dp = new int[N+1]; // i번 째에 위치했을때 포도주의 최대점수

        dp[1] = arr[1]; // 1번째에 최대점수는 무조건 내가 마셨을때
        dp[2] = arr[1]+arr[2]; // 2번째에 최대점수는 연속해서 2잔을 마셨을때

        for(int i = 3; i <= N; i++){ //3번째 부터는 연속된 3개를 마실 수 없으므로, "안마시는 포두주"까 존재할거이

            int case1 = arr[i] + arr[i-1] + dp[i-3]; // i에서 마심, i-1에서 마심, i-2에서 안 마심
            int case2 = arr[i] + dp[i-2]; // i에서 마심  i-1에서 안 마심
            int case3 = dp[i-1]; // i에서 안 마심

            dp[i] = Math.max(case1, Math.max(case2, case3));

        }

        return dp[N];

    }
}
