package org.example.bruteforce.recursion.nm;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_15656 {

    static int N, M;
    static int[] arr; //내가 고른 숫자를 담을 배열
    static int[] arr2; // N개의 숫자.
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[M];
        arr2 = new int[N];

        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < N; i++){
            arr2[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr2);

        dfs(0);
        System.out.println(sb);
    }


    /**
     * 1) Top-Down 방식인 재귀를 통한 문제 풀이
     * 2) idx는 내가 뽑는 숫자의 개수 & 1~N개의 나열된 숫자의 순번.
     * 3) 중복 허용해서 뽑기때문에, 굳이 뽑았는지의 여부를 나타내는 visited[] 필요 없음.
     * */
    public static void dfs(int idx){
        //M개의 순열을 완성한 경우
        if(idx == M){
            for(int  val: arr){
                sb.append(val+" ");
            }
            sb.append("\n");
            return;
        }

        // 중복된 수가 가능하
        for(int i = 0; i<N; i++){
            arr[idx] = arr2[i];
            dfs(idx+1);
        }
    }

}
