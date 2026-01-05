package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int H = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());

        int[][] B = new int[H + X][W + Y];

        for (int i = 0; i < H + X; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < W + Y; j++) {
                B[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] A = new int[H][W];

        // A 복원: B의 (0,0)부터 (H-1, W-1) 영역에서 가져옴
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                A[i][j] = B[i][j];
            }
        }

        // 겹친 영역 보정: A[i][j]에서 A[i-X][j-Y]를 빼줌
        for (int i = X; i < H; i++) {
            for (int j = Y; j < W; j++) {
                A[i][j] = B[i][j] - A[i - X][j - Y];
            }
        }

        // 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                sb.append(A[i][j]);
                if (j < W - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);

    }
}