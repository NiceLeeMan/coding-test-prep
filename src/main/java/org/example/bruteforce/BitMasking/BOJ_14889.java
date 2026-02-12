package org.example.bruteforce.BitMasking;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1) 비트마스킹 방식 활용.
 *  N개의 비트 -> N명의 회원들,
 *  각 비트의 상태값 -> 각 회원이 속한 팀
 *
 * 2) N으로 만들어지는 팀은 2^N개(2^N개의 팀을 돌면서 0의개수 = 1개의 개수 같은 케이스만 따진다)
 *
 * 3) 재귀로 풀었을때의 메모리 23272KB, 비트마스킹으로 풀었을때 14628KB
 * */
public class BOJ_14889 {

    static int N;
    static int[][] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        arr = new int[N][N];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(solvedByBitMasking());


    }

    //팀을 꾸리는 방법을 비트마스킹으로 해결해보자.
    public static int solvedByBitMasking(){

        int minDiff = Integer.MAX_VALUE;
        //선수가 N명이면 N개의 비트 필요. 거기서 나올수 있는 모든 팀의 경우, i값의 의미: 구성되는 모든 팀의 가짓수
        for(int i = 0; i < 1<<N; i++){
            if (Integer.bitCount(i) == N / 2) {
                int startTeam = calculate(i);
                int linkTeam = calculate(~i & ((1 << N) - 1));
                int diff = Math.abs(startTeam - linkTeam);
                minDiff = Math.min(minDiff, diff);

            }
        }

        return minDiff;
    }

    public static int calculate(int mask){
        int sum = 0;
        for(int i = 0; i < N; i++){
            if((mask & (1 << i)) == 0) continue;

            for(int j = 0; j < N; j++){
                if ((mask & (1 << j)) == 0) continue;
                sum += arr[i][j];
            }
        }
        return sum;
    }
}
