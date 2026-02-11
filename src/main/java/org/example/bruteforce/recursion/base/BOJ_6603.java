package org.example.bruteforce.recursion.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_6603 {

    static int k;
    static int[] result = new int[6];
    static int[] S;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            k = Integer.parseInt(st.nextToken());

            if(k==0) break;

            S = new int[k];
            for (int i = 0; i < k; i++) {
                S[i] = Integer.parseInt(st.nextToken());
            }

            dfs(0,0);
            sb.append("\n");
        }
        System.out.print(sb);
    }


    //난 k개의 나열된 숫자중 start번째에 있는 것을 뽑을거야. 그 다음숫자를 뽑는건 재귀가 해줄거야.
    public static void dfs(int start, int count) {

        //마지막 수까지 뽑으면 결과 만들기
        if(count == 6){
            for (int val : result) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
        }

        for(int i = start; i<k; i++){
            result[count] = S[i];
            dfs(i+1, count+1);

        }
    }
}
