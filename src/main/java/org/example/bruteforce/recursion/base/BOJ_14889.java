package org.example.bruteforce.recursion.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14889 {

    static int N;
    static int[][] S;
    static boolean[] visited;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        visited = new boolean[N+1];
        S = new int[N+1][N+1];

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                S[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        solve(1,0);


    }

    //난 현재 이 사람을 뽑을지 말지 결정할거야. 다음 사람은 너가 결정해.
    static void solve(int idx, int count) {
        // 기저 사례: 팀원 절반을 모두 뽑았을 때
        if (count == N / 2) {
            calculate();
            return;
        }

        // 모든 인원을 다 확인했음에도 팀 구성을 못한 경우
        if (idx > N) {
            return;
        }

        // 1. 현재 인원(idx)을 스타트 팀에 포함하는 경우
        visited[idx] = true;
        solve(idx + 1, count + 1);

        // 2. 현재 인원(idx)을 포함하지 않는 경우 (백트래킹 원복)
        visited[idx] = false;
        solve(idx + 1, count);
    }

    static void calculate() {
        int startTeam = 0;
        int linkTeam = 0;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                // i와 j가 같은 경우는 S[i][j]가 0이므로 별도 처리 불필요
                if (visited[i] && visited[j]) {
                    startTeam += S[i][j];
                } else if (!visited[i] && !visited[j]) {
                    linkTeam += S[i][j];
                }
            }
        }

        int diff = Math.abs(startTeam - linkTeam);

        // 차이가 0이면 최솟값이므로 즉시 종료 가능(최적화)하나, 일반적인 갱신 로직 사용
        min = Math.min(min, diff);

        if (min == 0) {
            System.out.println(0);
            System.exit(0);
        }
    }
}
