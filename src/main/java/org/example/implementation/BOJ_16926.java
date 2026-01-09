package org.example.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제 분석 및 필수 사고 과정 정리]
 *
 * 1. [핵심 포인트/문제의 본질]
 * - 배열을 껍질(Layer) 단위로 벗겨내어 반시계 방향으로 회전시키는 구현 문제.
 * - 회전 횟수 R만큼 반복하되, 각 껍질은 독립적으로 회전함.
 * - Invariant(불변량): 껍질의 개수는 Math.min(N, M) / 2로 고정됨.
 *
 * 2. [구현 전략/설계]
 * - 모서리 4개를 각각 처리하는 대신, 시작점(Top-Left) 값 하나만 temp에 저장하고
 * 나머지 값을 빈칸 밀기(Shifting) 방식으로 처리하여 로직 단순화.
 * - 밖에서 안으로(0 -> layers) 진입하며, 각 layer의 depth를 's'로 정의.
 * - 인덱스 범위: (s, s) ~ (N-1-s, M-1-s)
 *
 * 3. [주의사항/함정]
 * - 배열 값을 덮어쓰는 순서가 중요함. (반시계 방향 회전 시: Top <- Right <- Bottom <- Left 순으로 당겨야 함)
 * - 마지막에 temp 값을 복구할 위치는 시작점(s, s)이 아니라 회전 후 위치인 (s+1, s)임.
 */
public class BOJ_16926 {
    static int N, M, R;
    static int[][] array;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        array = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // R번 회전 수행
        for (int i = 0; i < R; i++) {
            rotate();
        }

        // 출력 (StringBuilder 사용)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(array[i][j]).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    static void rotate() {
        // 껍질의 개수 계산 (짧은 변 / 2)
        int layers = Math.min(N, M) / 2;

        for (int s = 0; s < layers; s++) {
            // 1. 시작 값(Top-Left) 임시 저장 (데이터 유실 방지)
            int temp = array[s][s];

            // 2. Top (윗변): 왼쪽으로 당기기 (←)
            for (int j = s; j < M - 1 - s; j++) {
                array[s][j] = array[s][j + 1];
            }

            // 3. Right (오른변): 위로 당기기 (↑)
            for (int i = s; i < N - 1 - s; i++) {
                array[i][M - 1 - s] = array[i + 1][M - 1 - s];
            }

            // 4. Bottom (아랫변): 오른쪽으로 당기기 (→)
            for (int j = M - 1 - s; j > s; j--) {
                array[N - 1 - s][j] = array[N - 1 - s][j - 1];
            }

            // 5. Left (왼변): 아래로 당기기 (↓)
            for (int i = N - 1 - s; i > s; i--) {
                array[i][s] = array[i - 1][s];
            }

            // 6. Temp 복구 (시작점 바로 아래 위치)
            array[s + 1][s] = temp;
        }
    }
}
/**
 * [나의 실수와 회고 (Self-Feedback)]
 *
 * 1. [오해했거나 잘못 접근한 부분]
 * - 나는 모서리 4개의 값을 모두 저장해서 교환해야 한다고 생각했으나(오개념),
 * 실제로는 시작 값 하나만 빼두고 순차적으로 밀어버리는 것(Shifting)이 훨씬 효율적이었다(팩트).
 * - "동시에 바꾼다"는 직관 때문에 순차적 처리의 단순함을 놓쳤다.
 *
 * 2. [구현 단계의 실수]
 * - 껍질(Layer) 개념은 알았으나, 이를 코드상의 변수 's'와 인덱스 수식(N-1-s)으로 변환하지 못했다.
 * - 예제를 손으로 그릴 때 값(Value)만 적고 좌표(Index)를 적지 않아 패턴 발견이 늦어졌다.
 *
 * 3. [개선점/새로 배운 것]
 * - 다음에는 배열 회전/이동 문제에서 '빈 칸 이동(Hole Movement)' 관점으로 접근해본다.
 * - 추상적인 그림을 그릴 때 반드시 (y, x) 좌표를 함께 기입하여 수식화 단계를 거친다.
 */