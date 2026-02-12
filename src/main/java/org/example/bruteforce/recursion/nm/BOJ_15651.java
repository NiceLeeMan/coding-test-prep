package org.example.bruteforce.recursion.nm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15651 {

    static int N;
    static int M;
    static int[] arr;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[M];
        dfs(0);
        System.out.println(sb);
    }

    public static void dfs(int idx){

        if(idx == M){
            for(int val : arr){
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
        }

        for(int i = 1; i <= N; i++){
            arr[idx] = i;
            dfs(idx+1);
        }

    }
}
