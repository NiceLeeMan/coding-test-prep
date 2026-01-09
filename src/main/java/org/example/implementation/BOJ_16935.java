package org.example.implementation;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *[문제 분석 및 설계]
 *
 * 1. 핵심 포인트
 * - 배열의 크기(N, M)가 연산(3, 4번)에 따라 변할 수 있으므로, 매 연산 후 N, M을 갱신해야 한다.
 * - 연산 1, 2는 In-place Swapping이 가능하지만, 3~6은 인덱스 매핑이 복잡하므로 새로운 배열을 반환하는 것이 안전하다.
 *
 * 2. 구현 전략
 * - 연산 1, 2: void 메서드로 원본 배열(static array) 직접 수정
 * - 연산 3~6: int[][] 반환 메서드로 구현 후 원본 배열 교체
 * - 연산 5, 6: 4개의 사분면을 오프셋(Offset) 연산으로 한 번에 이동 (N/2, M/2 활용)
 */
public class BOJ_16935 {
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

        // 명령어 라인 읽기
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < R; i++) {
            int cmd = Integer.parseInt(st.nextToken());
            switch (cmd) {
                case 1:
                    operation_one();
                    break;
                case 2:
                    operation_two();
                    break;
                case 3:
                    array = operation_three();
                    break;
                case 4:
                    array = operation_four();
                    break;
                case 5:
                    array = operation_five();
                    break;
                case 6:
                    array = operation_six();
                    break;
            }
            // 배열 크기가 변했을 수 있으므로 갱신
            N = array.length;
            M = array[0].length;
        }

        // 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                sb.append(array[i][j]);
                if (j < M - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    // 상하 반전
    static void operation_one() {
        for (int i = 0; i < N / 2; i++) {
            int[] temp = array[i];
            array[i] = array[N - 1 - i];
            array[N - 1 - i] = temp;
        }
    }

    // 좌우 반전
    static void operation_two() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M / 2; j++) {
                int temp = array[i][j];
                array[i][j] = array[i][M - 1 - j];
                array[i][M - 1 - j] = temp;
            }
        }
    }

    // 오른쪽 90도 회전
    static int[][] operation_three() {
        int[][] newArr = new int[M][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                newArr[j][N - 1 - i] = array[i][j];
            }
        }
        return newArr;
    }

    // 왼쪽 90도 회전
    static int[][] operation_four() {
        int[][] newArr = new int[M][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                newArr[M - 1 - j][i] = array[i][j];
            }
        }
        return newArr;
    }

    // 1->2, 2->3, 3->4, 4->1 (시계 방향)
    static int[][] operation_five() {
        int[][] newArr = new int[N][M];
        int nMid = N / 2;
        int mMid = M / 2;

        // 1 -> 2 (우측 이동)
        for (int i = 0; i < nMid; i++) {
            for (int j = 0; j < mMid; j++) {
                newArr[i][j + mMid] = array[i][j];
            }
        }
        // 2 -> 3 (하단 이동)
        for (int i = 0; i < nMid; i++) {
            for (int j = mMid; j < M; j++) {
                newArr[i + nMid][j] = array[i][j];
            }
        }
        // 3 -> 4 (좌측 이동)
        for (int i = nMid; i < N; i++) {
            for (int j = mMid; j < M; j++) {
                newArr[i][j - mMid] = array[i][j];
            }
        }
        // 4 -> 1 (상단 이동)
        for (int i = nMid; i < N; i++) {
            for (int j = 0; j < mMid; j++) {
                newArr[i - nMid][j] = array[i][j];
            }
        }
        return newArr;
    }

    // 1->4, 4->3, 3->2, 2->1 (반시계 방향)
    static int[][] operation_six() {
        int[][] newArr = new int[N][M];
        int nMid = N / 2;
        int mMid = M / 2;

        // 1 -> 4 (하단 이동)
        for (int i = 0; i < nMid; i++) {
            for (int j = 0; j < mMid; j++) {
                newArr[i + nMid][j] = array[i][j];
            }
        }
        // 4 -> 3 (우측 이동)
        for (int i = nMid; i < N; i++) {
            for (int j = 0; j < mMid; j++) {
                newArr[i][j + mMid] = array[i][j];
            }
        }
        // 3 -> 2 (상단 이동)
        for (int i = nMid; i < N; i++) {
            for (int j = mMid; j < M; j++) {
                newArr[i - nMid][j] = array[i][j];
            }
        }
        // 2 -> 1 (좌측 이동)
        for (int i = 0; i < nMid; i++) {
            for (int j = mMid; j < M; j++) {
                newArr[i][j - mMid] = array[i][j];
            }
        }
        return newArr;
    }
}

/**
 * [회고]
 * 1. 개선점
 * - N, M이 회전 연산(3, 4번) 시 뒤바뀌는 점을 main 루프 내에서 처리하여 OutOfBounds 오류 방지
 * - 4분면 이동 시 배열을 쪼개지 않고, 오프셋 연산을 통해 한 번에 목적지로 복사하여 구현 복잡도 감소
 *
 * 2. 실수 방지
 * - BufferedReader와 StringTokenizer 초기화 시점 분리 (배열 입력 후 명령어 입력 라인 읽기)
 *
 * 3. 배열 이동
 * - 2차원배열에서 배열 평행이동시, 열단위, 행단위로 한번에 옮길려고 하지말고 인덱스 단위로 좌표를 옮기는 연습을 하자.
 * 그래야 반복문도 잘 짜지고, 흐름이 안 햇갈린다
 */