package org.example.Greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_11399 {
    static int N;
    static int[] P;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        P = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            P[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(P);
        System.out.println(func_1());

    }

    public static int func_1(){
        int value = 0;

        for(int i = 0; i < N; i++){
            for(int j = 0; j <= i; j++){
                value += P[j];
            }
        }
        return value;
    }
}
