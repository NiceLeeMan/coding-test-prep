package org.example.Greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_1715 {

    static int N;
    static PriorityQueue<Integer> pq = new PriorityQueue<>();;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());

        for(int i =0; i<N; i++){
            pq.offer(Integer.parseInt(br.readLine()));
        }

        System.out.println(func_1());
    }

    public static int func_1(){
        int sum =0;

        while(pq.size() != 1){
            int a = pq.poll();
            int b = pq.poll();
            sum += a+b;
            pq.offer(a+b);
        }

        return sum;

    }

}
