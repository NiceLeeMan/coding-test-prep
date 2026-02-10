package org.example.bruteforce.recursion.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_2529 {

    static int K;
    static char[] signs;        // 부등호를 담을 배열
    static boolean[] visited; // 0~9 숫자의 사용 여부
    static List<String> results = new ArrayList<>(); // 정답 후보들을 담을 리스트

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        K = Integer.parseInt(br.readLine());
        signs = new char[K];
        visited = new boolean[10]; // 0~9 숫자

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            signs[i] = st.nextToken().charAt(0);
        }

        dfs(0,"");
        Collections.sort(results);

        System.out.println(results.get(results.size() - 1));
        System.out.println(results.get(0));

    }

    //현재 idx에서 앞의 등호를 고려해서 숫자를 배치하겠다. 다음 자리수는 내 알바 아니다.
    public static void dfs(int idx, String num) {

        // 마지막 숫자까지 배치가 완료된 경우
        if (idx == K + 1) {
            results.add(num);
            return;
        }

        for(int i = 0; i <= 9; i++){

            //사용하지 않은 숫자의 경우, 배치해줘야하.
            if(!visited[i]){
                //배치할려면 부등호 조건을 따져야함.
                if(idx == 0 || check(num.charAt(idx-1)-'0',i,signs[idx-1]) ){
                    visited[i] = true;
                    dfs(idx+1,num+i);
                    visited[i] = false;
                }
            }
        }
    }

    public static boolean check(int a, int b, char op){
        if(op == '<') return a < b;
        if(op == '>') return a > b;
        return false;
    }
}
