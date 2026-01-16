package org.example.bruteforce;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * [문제 분석 및 필수 사고 과정 정리]
 *
 * 1. [핵심 포인트/문제의 본질]
 * - 시간 복잡도: N <= 50이므로 O(N^4) (약 625만 연산) 수행 시 1초 내 충분히 통과 가능.
 * - 알고리즘 선택: 완전 탐색(Brute Force). 모든 인접한 두 칸을 교환해보고 최댓값을 찾는다.
 * - 접근 방식: 별도의 배열 복사(Clone) 없이 Swap -> Check -> Restore(Swap) 패턴을 사용하여 메모리 오버헤드를 최소화한다.
 *
 * 2. [구현 전략/설계]
 * - 모듈화(Modularization): 기능별로 함수를 분리하여 로직의 명확성 확보.
 * 1) solve(): 전체 보드 순회 및 교환 제어 (Main Logic)
 * 2) check(): 현재 보드 상태에서 가장 긴 연속 사탕 개수(행/열 전체) 계산
 * 3) swap(): 좌표 기반의 물리적 데이터 교환
 *
 * - 로직 최적화 (Direction Array):
 * 행(Row)과 열(Col)의 교환 로직을 별도 함수로 분리하지 않고,
 * 방향 배열 (dr={0, 1}, dc={1, 0})을 사용하여 우측/하단 교환 로직을 하나의 루프로 통합.
 * -> 중복 코드 방지 및 유지보수성 향상.
 *
 * 3. [주의사항/함정]
 * - 중복 연산 방지: 상하좌우 4방향이 아닌, '우측'과 '하단'만 검사하면 모든 인접 쌍을 커버 가능.
 * - 가지치기(Pruning): 인접한 두 사탕의 색이 '다를 경우'에만 교환을 시도하여 불필요한 연산 감소.
 * - check() 로직: 교환은 부분적으로 일어나지만, 최댓값 갱신은 보드 전체(행+열)를 대상으로 수행해야 함.
 */
public class BOJ_3085 {
    static int N;
    static char[][] board;
    static int maxCandy = 0;

    // 우측(Right), 하단(Down) 방향 배열
    static int[] dr = {0, 1};
    static int[] dc = {1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 공백이 없는 입력이므로 StringTokenizer 불필요
        N = Integer.parseInt(br.readLine());
        board = new char[N][N];

        for (int row = 0; row < N; row++) {
            // 문자열을 받아 char 배열로 바로 변환
            board[row] = br.readLine().toCharArray();
        }

        solve();

        System.out.println(maxCandy);
    }

    public static void solve() {
        // 초기 상태에서도 최댓값이 나올 수 있으므로 먼저 검사 (필수)
        check();

        // 1. 전체 보드 순회
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {

                // 2. 우측, 하단 두 방향만 검사
                for (int d = 0; d < 2; d++) {
                    int nextRow = row + dr[d];
                    int nextCol = col + dc[d];

                    // 3. 범위 체크
                    if (nextRow >= N || nextCol >= N) continue;

                    // 4. 가지치기: 색이 다를 때만 교환 시도
                    if (board[row][col] != board[nextRow][nextCol]) {
                        swap(row, col, nextRow, nextCol); // 교환
                        check();                          // 상태 검사
                        swap(row, col, nextRow, nextCol); // 복구
                    }
                }
            }
        }
    }

    public static void check() {
        // 가로(행) 검사
        for (int row = 0; row < N; row++) {
            int count = 1;
            for (int col = 1; col < N; col++) {
                if (board[row][col] == board[row][col - 1]) {
                    count++;
                } else {
                    count = 1;
                }
                maxCandy = Math.max(maxCandy, count);
            }
            // *초기 상태(N=1 등)나 교환 없이도 답이 될 수 있으므로 여기서 갱신 유지
            maxCandy = Math.max(maxCandy, count);
        }

        // 세로(열) 검사
        for (int col = 0; col < N; col++) {
            int count = 1;
            for (int row = 1; row < N; row++) {
                if (board[row][col] == board[row - 1][col]) {
                    count++;
                } else {
                    count = 1;
                }
                maxCandy = Math.max(maxCandy, count);
            }
            maxCandy = Math.max(maxCandy, count);
        }
    }

    public static void swap(int r1, int c1, int r2, int c2) {
        char temp = board[r1][c1];
        board[r1][c1] = board[r2][c2];
        board[r2][c2] = temp;
    }
}
/**
 * [나의 실수와 회고 (Self-Feedback)]
 *
 * 1. [오해했거나 잘못 접근한 부분]
 * - 초기 접근: 행/열 검사 로직을 별도 함수로 분리하여 코드 중복이 발생할 뻔함.
 * - 수정: 방향 배열을 사용하여 하나의 로직으로 통합함.
 *
 * 2. [구현 단계의 실수]
 * - (예: 입력값 파싱 시 StringTokenizer를 사용하여 공백 없는 입력을 제대로 읽지 못함 -> charAt/toCharArray로 수정)
 * - (예: check 함수에서 최댓값 갱신 위치를 루프 바깥에 두어 마지막 연속 카운트를 누락함)
 *
 * 3. [개선점/새로 배운 것]
 * - 방향 배열(dr, dc)을 이용한 2차원 배열 탐색 패턴 습득.
 * - Swap -> Check -> Restore 패턴으로 배열 복사(Clone) 없이 상태 관리하는 법.
 */



















