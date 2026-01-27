package org.example.bruteforce.recursion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static org.example.bruteforce.recursion.BOJ_15649.sb;

public class BOJ_15650 {

    static int N;
    static int M;
    static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[M];
        dfs(0,1);
        System.out.println(sb);
    }
    public static void dfs(int idx, int start) {

        // 1. [기저 조건] 수열 완성 시점
        if (idx == M) {
            for (int val : arr) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
        }

        // 2. [탐색 로직]
        for (int i = start; i <= N; i++) {

            // (1) 값 할당 및 진입 로그
            arr[idx] = i;
            // (2) 재귀 호출
            dfs(idx + 1, i + 1);

        }
    }
}
