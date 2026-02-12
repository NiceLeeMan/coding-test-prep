package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BOJ 1463 - 1로 만들기
 *
 * 핵심 포인트:
 * - Top-Down DP (재귀 + 메모이제이션)
 * - operation(n): "n을 1로 만드는 최소 연산 횟수"로 정의
 * - 세 연산(÷3, ÷2, -1) 모두 시도 후 최솟값 선택
 * - 나눠지더라도 -1이 더 빠른 경우 있음 (ex: 10 → 9 → 3 → 1)
 * - +1의 의미: 연산 1회 수행, 재귀 깊이만큼 누적됨
 * */
public class BOJ_1463 {

    static int[] dp = new int[1000001];
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N= Integer.parseInt(st.nextToken());

        System.out.println(operation2(N));
    }


    // Top-Down 방식
    //"n을 1로 만드는 데 필요한 최소 연산 횟수를 리턴하는 함수"
    public static int operation(int n){

        if(n == 1)return 0;
        if(dp[n] != 0) return dp[n];

        int result = operation(n-1)+1;
        if(n % 2 == 0) result = Math.min(result, operation(n/2) + 1);
        if (n % 3 == 0) result = Math.min(result, operation(n / 3) + 1);

        dp[n] = result;

        return result;
    }

    public static int operation2(int n){

        dp[1] = 0;

        for(int i = 2; i <= n; i++){
            dp[i] = dp[i-1] + 1; // i를 1로 만드느데  필요한 최소 연산횟수 = i-1을 1로 만드는데 필요한 최소횟수 +1

            if (i % 2 == 0) dp[i] = Math.min(dp[i], dp[i / 2] + 1);
            if (i % 3 == 0) dp[i] = Math.min(dp[i], dp[i / 3] + 1);

        }
        return dp[n];
    }
}
