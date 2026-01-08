package org.example.implementation;

/**
 * [문제 분석 및 필수 사고 과정 정리]
 *
 * 1. 공간과 데이터의 분리 (위치 vs 값)
 * - '올리는 위치(0)'와 '내리는 위치(N-1)'는 고정된 좌표값이다.
 * - 반면 그 위를 지나가는 '벨트(내구도+로봇)'는 계속 변하는 데이터다.
 * - 따라서 인덱스를 복잡하게 계산하기보다, 배열 내부의 값을 실제로 한 칸씩 미는 방식이 구현상 직관적이고 오류가 적다.
 *
 * 2. 데이터의 묶음 관리 (Class 활용)
 * - 내구도(int)와 로봇(boolean)은 항상 같이 움직여야 하는 데이터다.
 * - 이를 각각의 배열로 관리하면 회전 시 실수할 위험이 크므로, Belt 클래스로 묶어서 하나의 객체로 관리한다.
 *
 * 3. 시뮬레이션 순서 준수
 * - 문제에 명시된 [1.회전 → 2.이동 → 3.올리기] 순서는 절대적이다.
 * - 초기 상태(로봇 없음)라고 해서 순서를 임의로 바꾸면, 로봇이 한 턴에 두 번 이동하는 논리적 오류가 발생한다.
 *
 * 4. 배열 제어의 방향성
 * - 배열 회전: 데이터를 덮어쓰지 않으려면 뒤(2N-1)에서 앞(0)으로 값을 당겨와야 한다.
 * - 로봇 이동: 가장 먼저 올라간(오른쪽 끝) 로봇부터 움직여야 빈칸이 확보되므로, 역순(N-2 → 0)으로 탐색해야 한다.
 *
 * 5. 내리는 위치(N-1)의 처리
 * - 로봇은 N-1번 칸에 도달하는 순간 즉시 내려야 한다.
 * - 회전 직후와 이동 직후, 두 시점 모두에서 N-1번 위치를 검사하여 로봇을 삭제해야 한다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_20055 {
    static int n, k;
    static Belt[] belt;
    static int zeroCount = 0; // 내구도 0인 칸의 개수 (실시간 관리)

    static class Belt {
        int durability;
        boolean hasRobot;

        public Belt(int durability) {
            this.durability = durability;
            this.hasRobot = false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        belt = new Belt[2 * n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 2 * n; i++) {
            int d = Integer.parseInt(st.nextToken());
            belt[i] = new Belt(d);
        }

        int step = 0;

        while (zeroCount < k) {
            step++;

            rotate(); // 1. 회전
            move();   // 2. 이동
            load();   // 3. 올리기
        }

        System.out.println(step);
    }

    // 1단계: 벨트 회전
    static void rotate() {
        Belt temp = belt[2 * n - 1];

        // 뒤에서부터 당겨오기 (데이터 덮어쓰기 방지)
        for (int i = 2 * n - 1; i > 0; i--) {
            belt[i] = belt[i - 1];
        }

        belt[0] = temp;

        // 회전 후 내리는 위치 로봇 삭제
        if (belt[n - 1].hasRobot) {
            belt[n - 1].hasRobot = false;
        }
    }

    // 2단계: 로봇 이동
    static void move() {
        // 먼저 올라간 로봇(오른쪽 끝)부터 처리해야 하므로 역순 탐색
        for (int i = n - 2; i >= 0; i--) {
            if (belt[i].hasRobot) {
                if (!belt[i + 1].hasRobot && belt[i + 1].durability >= 1) {

                    belt[i].hasRobot = false;
                    belt[i + 1].hasRobot = true;
                    belt[i + 1].durability--;

                    if (belt[i + 1].durability == 0) zeroCount++;

                    // 이동 후 내리는 위치면 삭제
                    if (i + 1 == n - 1) {
                        belt[i + 1].hasRobot = false;
                    }
                }
            }
        }
    }

    // 3단계: 로봇 올리기
    static void load() {
        if (belt[0].durability > 0 && !belt[0].hasRobot) {
            belt[0].hasRobot = true;
            belt[0].durability--;

            if (belt[0].durability == 0) zeroCount++;
        }
    }
}

/**
 * [나의 실수와 회고 (Self-Feedback)]
 *
 * 1. 자의적인 순서 해석 (순서의 중요성)
 * - "처음엔 로봇이 없으니 올리기부터 처리해도 되지 않을까?"라고 추측하며 문제의 순서를 바꾸려 했다.
 * - 시뮬레이션 문제에서 단계별 순서(회전 → 이동 → 올리기)는 절대적인 규칙이다.
 * - 내 생각이나 효율성보다 지문에 적힌 텍스트 그대로를 구현하는 것이 정답으로 가는 가장 빠른 길이다.
 *
 * 2. 자료구조의 선택 (2차원 배열 vs 클래스)
 * - 처음에는 `arr[N][3]` 형태의 2차원 배열로 모든 속성을 관리하려 했다.
 * - 하지만 내구도(int)와 로봇(boolean)을 인덱스로만 구분하면 코드를 짤 때 헷갈리기 쉽다.
 * - 관련된 데이터를 클래스(객체)로 묶어서 관리하는 것이 로직의 명확성을 높여준다는 점을 배웠다.
 *
 * 3. Java 객체 배열 초기화 실수
 * - `Belt[] belt = new Belt[2*n];` 선언만 하면 배열이 완성된 것으로 착각했다.
 * - 배열 안의 각 요소에 대해 `new Belt()`로 실제 객체를 할당해주지 않으면 NullPointerException이 발생한다.
 * - 배열 생성과 요소 생성은 별개라는 기본기를 다시 확인했다.
 */