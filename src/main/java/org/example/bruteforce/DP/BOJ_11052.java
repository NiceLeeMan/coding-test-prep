package org.example.bruteforce.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * [문제 해결 과정 정리]
 *
 * 1. 잘못된 접근
 * - "n번 카드팩을 살지 말지 결정"하는 방식 (0-1 냅색 형태)
 * - 문제점: 같은 카드팩을 여러 번 살 수 없음 (P1을 3번 사는 경우 표현 불가)
 *
 * 2. 올바른 접근
 * - 재귀 함수 정의: buyCardPack(remain) = "remain개를 사는 최대 비용"
 * - 선택지: i개짜리 팩을 산다 → P[i] + buyCardPack(remain-i)
 * - 모든 i (1~remain)를 시도하고 최댓값 리턴
 *
 * 3. 핵심 교훈
 * - 재귀 함수의 정의를 명확히 해야 재귀식을 세울 수 있음
 * - "이 함수는 정확히 무엇을 리턴하는가?"를 먼저 정의
 */
public class BOJ_11052 {

    static int N;
    static int[] P;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        P = new int[N+1];
        dp = new int[N+1];
        Arrays.fill(dp, -1);

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            P[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(buyCardPack(N));
    }


    /** 카드 팩을 구매한다
     * remain: 앞으로 내가 구매해야하는 카드의 개수
     * return: remain개의 카드를 사는데 드는 최대비용
     * Top-Down 방식
    */
    public static int buyCardPack(int remain){

        if(remain == 0){
            return 0;
        }

        if(dp[remain] != -1){  // 이미 계산한 적 있으면
            return dp[remain];  // 저장된 값 리턴
        }

        int maxCost = 0;
        //모든 카드팩 번호를 구매해본다.
        for(int i = 1; i <= remain; i++){
            int cost = P[i] + buyCardPack(remain - i);
            maxCost = Math.max(maxCost, cost);
        }

        dp[remain] = maxCost;
        return maxCost;
    }

    /**
     * N: 우리가 사야할 카드의 개수
     * dp[i] : 카드 i개를 사는데 드는 최대 비용
     */
    public static int buyCardPack2(int N){

        dp[0] = 0;  // 0개 사는 비용은 0
        for (int i = 1; i <= N; i++) {
            // i개를 사는 최대 비용 계산
            for (int j = 1; j <= i; j++) {
                // j개짜리 팩을 사고, 나머지 (i-j)개를 사는 경우
                dp[i] = Math.max(dp[i], P[j] + dp[i-j]);
            }
        }
        return dp[N];
    }
}
