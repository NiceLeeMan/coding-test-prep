package org.example.Greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1946 {

    static int T;
    static int N;
    static int[][] score;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        T = Integer.parseInt(st.nextToken());

        for(int i =0; i<T; i++){
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            score = new int[N][2];

            for(int j =0; j < N; j++){
                st = new StringTokenizer(br.readLine());
                score[j][0] = Integer.parseInt(st.nextToken());
                score[j][1] = Integer.parseInt(st.nextToken());
            }

            System.out.println(func_1());

        }
    }

    public static int func_1(){

        Arrays.sort(score, (a, b) -> a[0] - b[0]); //서류 점수 기준 정렬
        int passer = 1; //
        int minInterView = score[0][1]; // 서류 1등의 면접 등수

        for(int i =1 ; i<N; i++){

            if(score[i][1] < minInterView){
                passer += 1;
                minInterView = score[i][1];
            }


        }
        return passer;
    }
}
