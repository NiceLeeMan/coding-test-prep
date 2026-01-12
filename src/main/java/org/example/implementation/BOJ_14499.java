package org.example.implementation;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * [문제 분석 및 설계]
 *
 * 1. [핵심 포인트]
 * - 주사위의 '물리적 회전'이 아닌 '값의 위치 변경(Permutation)' 문제임.
 * - 지도 밖으로 나가는 명령은 무시해야 하며, 이때는 출력도 하지 않음.
 * - 지도와 주사위 바닥면의 값 복사 규칙(0일 때와 아닐 때)을 정확히 구현해야 함.
 *
 * 2. [구현 전략]
 * - 주사위 상태: int[] dice = new int[6] (0:위, 1:북, 2:동, 3:서, 4:남, 5:바닥)
 * - 이동 로직: 방향별로 인덱스 값을 Swap하는 하드코딩 함수 구현.
 * - 순서: 좌표 이동 가상 계산 -> 범위 체크 -> 좌표 확정 -> 주사위 굴리기 -> 지도 상호작용 -> 출력
 *
 * 3. [주의사항]
 * - 명령어 입력은 한 줄에 공백으로 구분되어 들어오므로 StringTokenizer 필수 사용.
 * - 주사위는 굴린 후 윗면(dice[0])을 출력해야 함.
 */
public class BOJ_14499 {

    static int N, M, X, Y, K;
    static int[][] map;
    static int[] dice = new int[6]; // 0:윗 면, 1:뒷 면, 2:오른쪽 면, 3:왼쪽 면, 4:앞 면, 5:바닥 면

    // 방향: 1:동, 2:서, 3:북, 4:남 (문제 인덱스 일치)
    static int[] dx = {0, 0, 0, -1, 1};
    static int[] dy = {0, 1, -1, 0, 0};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine()); // 한 줄 읽기
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(st.nextToken()); // 공백 기준으로 자르기
            }
        }

        int[] commands = new int[K];

       // 줄 전체를 먼저 읽음
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<K; i++){
            commands[i] = Integer.parseInt(st.nextToken()); // 공백 기준으로 하나씩 자름
        }

        // 명령어 처리 (마지막 줄 입력)
        for(int i=0; i<K; i++){
            int command = commands[i];

            // 1. 이동할 좌표 계산
            int nx = X + dx[command];
            int ny = Y + dy[command];

            // 2. 범위 벗어나면 무시 (명령어 skip)
            if (nx < 0 || nx >= N || ny < 0 || ny >= M) continue;

            // 3. 실제 이동 및 주사위 굴리기
            X = nx;
            Y = ny;
            move(command);

            // 4. 지도와 상호작용
            if(map[X][Y]==0){
                map[X][Y] = dice[5];
            }
            else {
                dice[5] = map[X][Y];
                map[X][Y] = 0;
            }

            // 5. 윗면 출력
            System.out.println(dice[0]);
        }
    }

    // 주사위 굴리기 분기
    static void move(int dir) {
        switch (dir) {
            case 1: moveEast(); break;
            case 2: moveWest(); break;
            case 3: moveNorth(); break;
            case 4: moveSouth(); break;
        }
    }
    // 동쪽 이동: 위(0)->동(2)->아래(5)->서(3)->위(0)
    static void moveEast() {
        int temp = dice[0];
        dice[0] = dice[3];
        dice[3] = dice[5];
        dice[5] = dice[2];
        dice[2] = temp;
    }

    // 서쪽 이동: 위(0)->서(3)->아래(5)->동(2)->위(0)
    static void moveWest() {
        int temp = dice[0];
        dice[0] = dice[2];
        dice[2] = dice[5];
        dice[5] = dice[3];
        dice[3] = temp;
    }

    // 북쪽 이동: 위(0)->북(1)->아래(5)->남(4)->위(0)
    static void moveNorth() {
        int temp = dice[0];
        dice[0] = dice[4];
        dice[4] = dice[5];
        dice[5] = dice[1];
        dice[1] = temp;
    }

    // 남쪽 이동: 위(0)->남(4)->아래(5)->북(1)->위(0)
    static void moveSouth() {
        int temp = dice[0];
        dice[0] = dice[1];
        dice[1] = dice[5];
        dice[5] = dice[4];
        dice[4] = temp;
    }
}
/**
 * [나의 실수와 회고 (Self-Feedback)]
 *
 * 1. [오해했거나 잘못 접근한 부분]
 * - 초기 문제 해석 오류: "윗면을 바꾸는 요소는 없다"고 판단하여, 이동 시 바닥면 값만 지도에 의해 갱신되는 것으로 오해함(오개념).
 * - 문제의 본질 재정의: 주사위를 굴린다는 것은 면 자체가 이동하는 것이 아니라, **고정된 인덱스(틀) 안에서 값들이 '굴려지는 행위'에 의해 규칙적으로 재배치(Permutation)되는 것**임을 깨달음(팩트).
 *
 * 2. [구현 단계의 실수]
 * - 입력 처리: 명령어 입력이 한 줄에 공백으로 들어오는데, 루프마다 br.readLine()을 호출하여 런타임 에러 유발.
 * - 변수 스코프: dice 배열을 main 내부에 선언하여 static 메서드(moveEast 등)에서 접근 불가했음.
 *
 * 3. [개선점/새로 배운 것]
 * - 시뮬레이션 순서 정립: '좌표 이동 가상 계산 -> 범위 체크(유효성 검사) -> 실제 이동 -> 상호작용' 순서를 엄격히 지켜야 함.
 * - 3차원 회전 문제를 1차원 배열의 인덱스 스왑으로 추상화하여 해결하는 방법 습득.
 */