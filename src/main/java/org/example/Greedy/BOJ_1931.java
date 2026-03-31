package org.example.Greedy;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_1931 {
    static int N;
    static int[][] meeting;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        meeting = new int[N][2];

        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            meeting[i][0] = Integer.parseInt(st.nextToken());
            meeting[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(meeting, (a, b) -> {
            if (a[1] == b[1]) return a[0] - b[0];
            return a[1] - b[1];
        });

        int meetingCount = 1;

        int count = 1;
        int lastEnd = meeting[0][1]; // 첫 번째 회의는 무조건 선택

        for (int i = 1; i < N; i++) {
            if (lastEnd <= meeting[i][0]) {
                count++;
                lastEnd = meeting[i][1];
            }
        }


        System.out.println(count);




    }


}
