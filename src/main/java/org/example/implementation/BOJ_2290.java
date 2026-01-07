package org.example.implementation;

/**
 * [문제 분석 및 필수 사고 과정 정리]
 *
 * 1. 차원의 고정 (Invariant)
 * - 숫자의 모양(1 vs 8)과 상관없이, 모든 숫자는 가로(s+2) x 세로(2s+3)의 고정된 박스를 차지한다.
 * - "1은 얇으니까 좁게 출력하자"는 현실적/심미적 판단은 배제해야 한다. (예제 출력이 법이다.)
 *
 * 2. 출력 방식의 전환 (Vector → Raster)
 * - 콘솔 출력은 한 번 줄바꿈(\n)하면 위로 돌아갈 수 없다.
 * - 따라서 "숫자 하나를 완성하고 옆으로 이동"하는 것이 아니라,
 * "모든 숫자의 첫 번째 줄을 다 긋고 -> 줄바꿈 -> 두 번째 줄을 다 긋는" 행(Row) 단위 처리가 필수다.
 *
 * 3. 대상의 분해 (Decomposition)
 * - 숫자를 통으로 보지 않고, 높이(Row) 성격에 따라 5개 구역으로 찢어서 봐야 한다.
 * - [1.맨위] -> [2.위쪽몸통(s번 반복)] -> [3.중간] -> [4.아래쪽몸통(s번 반복)] -> [5.맨아래]
 *
 * 4. 데이터 추상화 (Look-up Table)
 * - 0~9까지 10개의 if/switch문을 만드는 건 하드코딩이다.
 * - 각 숫자가 켜지는(ON) 위치를 0과 1의 배열로 미리 정의해두고, 반복문으로 처리하는 패턴을 찾아야 한다.
 *
 * 5. 기계적 공백 처리
 * - 숫자 내부의 여백과 상관없이, 숫자와 숫자 사이에는 무조건 1칸의 구분자(Separator)가 필요하다.
 * - 이는 시각적 간격이 아니라, 채점 시스템이 요구하는 물리적 칸막이이다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2290 {
    // [데이터 정의] : 각 숫자의 스위치 ON(1)/OFF(0) 상태를 미리 배열로 정의 (Generalization)

    // 1. 가로 구역 (맨위, 중간, 맨아래) - 해당 구역에 '-'가 있는지 여부
    static int[] top = {1, 0, 1, 1, 0, 1, 1, 1, 1, 1}; // 1, 4 제외
    static int[] mid = {0, 0, 1, 1, 1, 1, 1, 0, 1, 1}; // 0, 1, 7 제외
    static int[] bot = {1, 0, 1, 1, 0, 1, 1, 0, 1, 1}; // 1, 4, 7 제외

    // 2. 세로 구역 (위쪽 몸통, 아래쪽 몸통) - 해당 구역 좌/우에 '|'가 있는지 여부
    static int[] upLeft   = {1, 0, 0, 0, 1, 1, 1, 0, 1, 1}; // 1,2,3,7 제외
    static int[] upRight  = {1, 1, 1, 1, 1, 0, 0, 1, 1, 1}; // 5,6 제외
    static int[] downLeft = {1, 0, 1, 0, 0, 0, 1, 0, 1, 0}; // 0,2,6,8만 켜짐
    static int[] downRight= {1, 1, 0, 1, 1, 1, 1, 1, 1, 1}; // 2 제외

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int s = Integer.parseInt(st.nextToken());
        String nStr = st.nextToken();

        StringBuilder sb = new StringBuilder();

        // -------------------------------------------------------
        // Step 1. 맨 윗줄 출력 (가로)
        // -------------------------------------------------------
        for (int i = 0; i < nStr.length(); i++) {
            int num = nStr.charAt(i) - '0';
            printHorizontal(sb, s, top[num]); // top 배열 참조
            if (i < nStr.length() - 1) sb.append(" "); // 숫자 사이 공백
        }
        sb.append("\n");

        // -------------------------------------------------------
        // Step 2. 위쪽 몸통 출력 (세로) - 높이 s만큼 반복
        // -------------------------------------------------------
        for (int row = 0; row < s; row++) {
            for (int i = 0; i < nStr.length(); i++) {
                int num = nStr.charAt(i) - '0';
                printVertical(sb, s, upLeft[num], upRight[num]); // upLeft, upRight 참조
                if (i < nStr.length() - 1) sb.append(" ");
            }
            sb.append("\n");
        }

        // -------------------------------------------------------
        // Step 3. 중간 줄 출력 (가로)
        // -------------------------------------------------------
        for (int i = 0; i < nStr.length(); i++) {
            int num = nStr.charAt(i) - '0';
            printHorizontal(sb, s, mid[num]); // mid 배열 참조
            if (i < nStr.length() - 1) sb.append(" ");
        }
        sb.append("\n");

        // -------------------------------------------------------
        // Step 4. 아래쪽 몸통 출력 (세로) - 높이 s만큼 반복
        // -------------------------------------------------------
        for (int row = 0; row < s; row++) {
            for (int i = 0; i < nStr.length(); i++) {
                int num = nStr.charAt(i) - '0';
                printVertical(sb, s, downLeft[num], downRight[num]); // downLeft, downRight 참조
                if (i < nStr.length() - 1) sb.append(" ");
            }
            sb.append("\n");
        }

        // -------------------------------------------------------
        // Step 5. 맨 아랫줄 출력 (가로)
        // -------------------------------------------------------
        for (int i = 0; i < nStr.length(); i++) {
            int num = nStr.charAt(i) - '0';
            printHorizontal(sb, s, bot[num]); // bot 배열 참조
            if (i < nStr.length() - 1) sb.append(" ");
        }
        sb.append("\n");

        System.out.print(sb);
    }

    // [헬퍼 함수] 가로 줄 그리기
    // flag가 1이면 ' - ', 0이면 '   ' (공백)
    private static void printHorizontal(StringBuilder sb, int s, int flag) {
        sb.append(" "); // 왼쪽 모서리 공백
        for (int k = 0; k < s; k++) {
            sb.append(flag == 1 ? "-" : " ");
        }
        sb.append(" "); // 오른쪽 모서리 공백
    }

    // [헬퍼 함수] 세로 줄 그리기
    // left/right가 1이면 '|', 0이면 ' '
    private static void printVertical(StringBuilder sb, int s, int left, int right) {
        sb.append(left == 1 ? "|" : " ");
        for (int k = 0; k < s; k++) {
            sb.append(" "); // 가운데는 무조건 공백
        }
        sb.append(right == 1 ? "|" : " ");
    }
}

/**
 * [나의 실수와 회고 (Self-Feedback)]
 *
 * 1. 과잉 해석의 함정 (Designer's Eye)
 * - 나는 "1은 얇으니까 공간을 덜 차지해야 예쁘지 않나?"라고 생각하며 시간을 허비했다.
 * - 문제 지문에 없는 조건(커닝, 심미성)을 상상하지 말자.
 * - 내 뇌피셜보다 '예제 출력'의 칸 수를 세어보는 것이 훨씬 빠르고 정확하다.
 *
 * 2. 하드코딩 vs 일반화 (PS적 사고의 부재)
 * - 처음에 0~9까지 모든 경우의 수를 케이스별로 나누려 했다.
 * - PS는 노가다가 아니라 '패턴 찾기'다. "가로줄이 있는 숫자들끼리 묶을 수 없을까?"라는 질문이
 * 배열(Look-up Table)을 활용한 효율적인 풀이로 이끌어줬다.
 *
 * 3. 구현의 기본기 부족 (프린터형 사고)
 * - 출력 장치는 위로 돌아갈 수 없다는 점을 간과하고, 숫자를 하나씩 완성하려 했다.
 * - 구현 문제는 대상을 '행(Row)' 단위로 찢어서 보는 연습이 더 필요하다.
 * - 별 찍기 문제 등을 통해 좌표/행 단위 처리에 익숙해져야겠다.
 */