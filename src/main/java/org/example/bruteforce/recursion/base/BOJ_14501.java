package org.example.bruteforce.recursion.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


/**
 * [문제 분석 및 필수 사고 과정 정리]
 *  N == 15, 시간 제한 2초여서 브루트 포스로 접근가능하다고 생각함.
 *  나의 선택에 따라 최대결과를 도출한다는 특징이있어서 베낭알고리즘 같다고 생각했음.
 *  깨달은 점은 재귀함수로 문제를 정의할때, 반복되는 행동의 단위를 발견한뒤, 그것을 함수로 만드는게 핵심인듯.
 *  이 문제는 선택과 결과의 최대값을 일일단위로 반복하기에 선택을 재귀함수로 만들었음.
 */
public class BOJ_14501 {

    static int N;
    static int[][] arr;



    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        arr = new int[N+1][2];

        for(int i=1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solve(1));


    }

    static int solve(int day) {
        // 1. 기저 사례: 퇴사일(N+1)에 딱 맞춰 도달 -> 더 이상 상담 불가, 수익 0 반환
        if (day == N + 1) {
            return 0;
        }

        // 2. 기저 사례: 퇴사일을 넘겨버림 -> 불가능한 경우, 매우 작은 값 반환 (Max 계산에서 탈락시킴)
        if (day > N + 1) return -99999999;
        // [핵심 로직]
        // 경우 1: 오늘 상담을 안 하고 다음 날로 넘어감 (Skip)
        // 수익: 0 + 나머지 날짜의 최대 수익
        int option1 = solve(day + 1);
        // 경우 2: 오늘 상담을 함 (Take)
        // 수익: 오늘 상담료(arr[day][1]) + 상담이 끝난 날짜(day + arr[day][0])부터의 최대 수익
        int option2 = arr[day][1] + solve(day + arr[day][0]);

        // 두 경우 중 더 큰 이득을 선택
        return Math.max(option1, option2);
    }

    /**
     * 반복문을 이용한 풀이 (Forward DP)
     * - 관점: 1일차부터 시작해서 수익을 미래(Next Day)로 밀어주는(Push) 방식
     * - 시간 복잡도: O(N)
     */
    static int solveIterative() {
        // dp[i]: i일차 아침에 가질 수 있는 최대 수익
        // N+1일(퇴사일)까지 계산해야 하므로 크기를 N+2로 설정
        int[] dp = new int[N + 2];

        for (int i = 1; i <= N; i++) {

            // [Case 1] 오늘(i일) 상담을 안 하는 경우
            // 행동: 오늘까지 번 돈(dp[i])을 그대로 내일(dp[i+1])로 가져간다.
            // 논리: 내일의 기존 값과 비교하여 더 큰 쪽을 선택 (Math.max)
            dp[i + 1] = Math.max(dp[i + 1], dp[i]);

            // [Case 2] 오늘(i일) 상담을 하는 경우
            // 조건: 상담이 끝나는 날(nextDay)이 퇴사일(N+1)을 넘지 않아야 함
            int nextDay = i + arr[i][0]; // 걸리는 기간(T) 더하기
            int income = arr[i][1];      // 상담 금액(P)

            if (nextDay <= N + 1) {
                // 행동: (오늘까지 번 돈 + 이번 상담 수익)을 상담 끝나는 날에 기록한다.
                // 논리: 기존에 그 날짜에 기록된 수익과 비교하여 갱신
                dp[nextDay] = Math.max(dp[nextDay], dp[i] + income);
            }
        }

        // 퇴사일(N+1)에 도달했을 때 쌓인 최종 최대 수익 반환
        return dp[N + 1];
    }


}
