package org.example.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1476 {

    static int E, S , M;
    static int year;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        E = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        calculate();

        System.out.println(year);
    }

    public static int calculate() {
        int[] arr = new int[3];

        // 세 원소값이 E ,S ,M과 각각 일치하기 전까지 반복
        while(true) {

            if(arr[0]==E && arr[1]==S && arr[2]==M) {
                break;
            }

            arr[0]++;
            if (arr[0] == 16){
                arr[0] = 1;
            }

            arr[1]++;
            if (arr[1] == 29){
                arr[1] = 1;
            }

            arr[2]++;
            if (arr[2] == 20){
                arr[2] = 1;
            }

            year++;
        }
        return year;
    }

}
