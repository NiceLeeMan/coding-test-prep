package org.example.bruteforce.recursion.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1182 {

    static int N;
    static int S;
    static int[] arr;
    static int[] partArr;
    static int count; // 전체 경우의 수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        arr = new int[N];
        partArr = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        //부분 수열의 크기 0개부터 ~ N-1개
        for (int i = 1; i <= N; i++) {
            sum(0,0,i);
        }

        System.out.println(count);
    }

    /**
     크기가 size은 부분수열을 구성하는 함수, 크기가 size+1일때는 다음 함수에게 넘긴다.
     idx: 전체수열에서 가장 첫번째로 뽑을 원소의 인덱스
     count: 현재 부분수열내의 원소 개수
     size: 목표로 하는 부분수열의 크기
     조합의 방식으로 풀기
    */

    public static void sum(int start,int count, int size){

        //종료 조건: 크기에 맞춰서 부분수열을 다 구상했을때
        if(count == size){
            calculate(size);
            return;
        }

        //이제 부분수열을 만들면 된다. 원소를 뽑아 부분 수열을 만든다
        for(int i = start; i<N; i++){
            partArr[count] = arr[i];
            sum(i+1,count+1, size);

        }
    }


    // 부분수열이 완성됐을때, 내부 원소의 합을 구한뒤 S와 같은지 비교
    public static void calculate(int size){

        int sum = 0;
        for(int i = 0; i<size; i++){
            sum += partArr[i];
        }

        //부분 수열의 합 = S 라면, 경우의수 1증가
        if(sum == S){
            count ++;
        }
    }


}
