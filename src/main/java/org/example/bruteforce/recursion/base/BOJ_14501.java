package org.example.bruteforce.recursion.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14501 {

    static int N; //백준이가 일을 하는 기간 (Day)
    static int[][] arr; // 백준의 상담표 row: 날짜 정보, cols: Ti & Pi

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        arr = new int[N + 1][2];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        System.out.println(solvedByLoop());
    }

    /**
     * day일에 상담을 할지 말지 선택하고, 그 결과로 얻을 수 있는 최대 수익을 구한다.
     *
     * @param day: 현재 선택해야 하는 날짜 (1부터 시작)
     * @return: day일부터 N일까지 얻을 수 있는 최대 수익
     */
    public static int solvedByRecursion(int day) {

        // 퇴사날짜를 초과하는 상담은 포함하지 않음.
        if (day > N) {
            return Integer.MIN_VALUE;
        }

        //상담을 한 경우, 오늘 소득을 받고, 다음 날짜로 넘어간다.
        int option1 = arr[day][1] + solvedByRecursion(day + arr[day][0]);

        //상담을 진행하지 않은 경우, 다음 날로 넘어간다.
        int option2 = solvedByRecursion(day + 1);

        return Math.max(option1, option2);

    }

    /**
     * 1)반복문을 통한 풀이는, 시작할 날짜 day부터 ~ N일까지 에 대해 선택
     *
     * */
    public static int solvedByLoop(){

        int[] dp = new int[N + 2]; // i일부터 마지막날까지, 최적으로 선택했을 때의 최대 수익

        //i일에 상담을 시작해서 최대 수익을 계산하는 함수.
        for(int i = N; i>=1; i--){

            int option1; //오늘 상담을 했을 때, 오늘부터 마지막날까지 얻는 총 수익
            int option2; // 오늘 상담을 안 했을 때, 오늘부터 마지막날까지 얻는 총 수익

            if(i + arr[i][0] <= N+1){ //오늘 시작한 상담일이 마지막날을 초과하는 경우
                option1 = arr[i][1] + dp[i+arr[i][0]]; // 상담을 해서 돈을 벌었음.
            }else{
                option1 = 0;
            }

            option2 = dp[i+1]; // 다음날부터 얻을 수 있는 최대 수익

            dp[i] = Math.max(option1, option2);

        }

        return dp[1];
    }



}
