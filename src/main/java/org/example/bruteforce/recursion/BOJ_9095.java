package org.example.bruteforce.recursion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * [문제 분석 및 필수 사고 과정 정리]
 *
 * 1. [핵심 포인트/문제의 본질]
 * - 정수 n을 1, 2, 3의 합으로 나타내는 방법의 수는 "가장 마지막에 더한 수"에 따라 분류된다.
 * - 마지막에 1을 더함 -> 이전 합은 n-1 (경우의 수: dp[n-1])
 * - 마지막에 2를 더함 -> 이전 합은 n-2 (경우의 수: dp[n-2])
 * - 마지막에 3을 더함 -> 이전 합은 n-3 (경우의 수: dp[n-3])
 * - 불변 조건(Invariant): f(n) = f(n-1) + f(n-2) + f(n-3)
 *
 * 2. [구현 전략/설계]
 * - 입력 크기 N < 11로 매우 작음 -> O(N)의 DP로 해결 가능.
 * - 재귀(Top-Down)의 오버헤드와 스택 사용을 배제하기 위해 반복문(Bottom-Up) 방식 선택.
 * - 미리 dp 배열을 채워두는 Pre-computation 방식이 아닌, 테스트 케이스마다 계산하는 방식 적용 (N이 작으므로 무관).
 *
 * 3. [주의사항/함정]
 * - n=1, 2, 3인 경우는 점화식으로 구할 수 없는 기저 사례(Base Case)이므로 반드시 초기화가 선행되어야 함.
 * - 반복문 사용 시 목표값 n이 아닌 현재 인덱스 i를 사용하여 쌓아 올려야 함.
 */
public class BOJ_9095 {

    static int T;
    static int[] dp = new int[12];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        T = Integer.parseInt(br.readLine());

        for(int i = 0; i < T; i++){
            int n = Integer.parseInt(br.readLine());
            sb.append(fn2(n)).append("\n");
        }
        System.out.println(sb);
    }


    //재귀를 통해 푼 방식(Top-Down)
    public static int fn(int n){
        if(n == 1) return 1;
        if(n == 2) return 2;
        if(n == 3) return 4;

        if (dp[n] != 0) {
            return dp[n];
        }
        dp[n] = fn(n - 1) + fn(n - 2) + fn(n - 3);
        return dp[n];
    }

    //반복문을 통하는 방식 (Bottom Up)
    public static int fn2(int n){
        if(n == 1) return dp[n] = 1;
        if(n == 2) return dp[n] = 2;
        if(n == 3) return dp[n] = 4;

        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;

        if (dp[n] != 0) {
            return dp[n];
        }

        for(int i = 4; i<=n; i++){
            dp[i] = dp[i-3] + dp[i-2] + dp[i-1];
        }

        return dp[n];
    }

}
/**
 * [나의 실수와 회고 (Self-Feedback)]
 *
 * 1. [오해했거나 잘못 접근한 부분]
 * - 문제를 처음 접했을 때, "1, 2, 3을 어떻게 조합(구성)할지" 절차지향적으로 접근하여 구현의 방향을 잡지 못했음.
 * - 실제로는 "마지막 상태"를 기준으로 이전 문제와의 관계(점화식)를 파악하는 것이 핵심이었음.
 *
 * 2. [구현 단계의 실수]
 * - Bottom-Up 반복문 구현 시, 루프 변수 i(현재 단계) 대신 입력값 n(목표값)을 사용하여 dp[n]에 잘못된 값을 할당하는 논리적 오류 범함.
 * - 초기화(Base Case) 코드를 반복문 내부에 배치하거나 누락하여 값이 0으로 나오는 문제 발생.
 *
 * 3. [개선점/새로 배운 것]
 * - "어떻게 풀지(How)"보다 "무엇인가(What - 정의)"에 집중해야 함을 배움.
 * - 재귀(Top-Down)와 반복문(Bottom-Up)의 차이를 명확히 이해했으며, 다음에는 N=4 정도의 예제를 먼저 그려보고(Visualization) 알고리즘을 선택하겠음.
 */