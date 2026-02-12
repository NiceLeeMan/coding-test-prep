package org.example.bruteforce.recursion.nm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_15654 {

    static int N;
    static int M;
    static int[] nums;   // [NEW] 입력받은 자연수들을 저장할 창고
    static int[] result; // M개를 뽑은 결과를 담을 배열
    static boolean[] visited; // 인덱스 사용 여부 체크
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        nums = new int[N];       // 재료 창고
        result = new int[M];     // 결과 그릇
        visited = new boolean[N]; // 방문 체크 (인덱스로 체크)

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(nums);

        dfs(0);
        System.out.println(sb);

    }

    public static void dfs(int idx){
        if(idx == M){
            for(int val : result){
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
        }

        for(int i = 0; i < N; i++){
            if(!visited[i]){
                result[idx] = nums[i];
                visited[i] = true;
                dfs(idx+1);
                visited[i] = false;
            }
        }
    }
}
