package org.example.implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**겉넓이 구하기 문제
 * 1.위,아래,왼쪽,오른쪽,앞,뒤 방향으로 시선을 나누는 것
 * 2.위,아래 방향의 겉넓이는 MxN 이라는 것을 발견하는 것
 * 3. 높이의 증감에 따라 가려지는 면이 존재하기에 "unit"을 좌표단위로 나눠야한다는 필요성 발견하는 것
 * 4. 현재높이와 이웃한 높이의 높이값 관계를 이용하여 문제를 풀어야한다는 생각
 * 5. 높이가 증가하면 다음블록 높이- 현재블록 높이, 같거나 작다면 변화 없다는 것 발견ㅇㅋ
 * */
public class BOJ_16931 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N: 세로 길이(행), M: 가로 길이(열)
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] arr = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 1. 위/아래 겉넓이
        // (0인 칸이 없으므로 N * M * 2)
        int upDownArea = 2 * N * M;


        // 2. 좌/우(측면) 겉넓이
        // 전략: 왼쪽에서 볼 때 '오르막길'만 다 더하고, 마지막에 x2 (오르막=내리막 이므로)
        int leftRightAscentSum = 0;
        for (int i = 0; i < N; i++) {
            // 그 줄의 맨 첫 번째 블록은 무조건 바닥(0)에서 올라가는 것이므로 더해줌
            int currentLineAscent = arr[i][0];

            for (int j = 0; j < M - 1; j++) { // j+1 참조하므로 M-1 까지만 반복
                // 오르막길인 경우에만 차이만큼 더함 (내리막길 무시)
                if (arr[i][j] < arr[i][j + 1]) {
                    currentLineAscent += (arr[i][j + 1] - arr[i][j]);
                }
            }
            leftRightAscentSum += currentLineAscent;
        }


        // 3. 앞/뒤(정면) 겉넓이
        // 전략: 앞에서 볼 때 '오르막길'만 다 더하고, 마지막에 x2
        int frontBackAscentSum = 0;
        for (int j = 0; j < M; j++) {
            // 그 열의 맨 첫 번째 블록 높이 더해줌
            int currentLineAscent = arr[0][j];

            for (int i = 0; i < N - 1; i++) { // i+1 참조하므로 N-1 까지만 반복
                // 오르막길인 경우에만 차이만큼 더함
                if (arr[i][j] < arr[i + 1][j]) {
                    currentLineAscent += (arr[i + 1][j] - arr[i][j]);
                }
            }
            frontBackAscentSum += currentLineAscent;
        }

        // 최종 계산
        // 측면과 정면은 오르막길 합 x 2
        int total = upDownArea + (leftRightAscentSum * 2) + (frontBackAscentSum * 2);

        System.out.println(total);
    }
}
