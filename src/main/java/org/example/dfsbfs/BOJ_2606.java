package org.example.dfsbfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2606 {

    static int N,K; // N:컴퓨터의 수 , K: 네트워크 상에 연결되어 있는 컴퓨터 쌍의 개수
    static List<Integer>[] graph; // List<Integer>를 원소로 갖는 일차원 배열 graph
    static boolean[] visited;
    static int count = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        visited = new boolean[N + 1];
        graph = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        //그래프가 완성이 된다.
        for (int i = 1; i <= K; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node1 = Integer.parseInt(st.nextToken());
            int node2 = Integer.parseInt(st.nextToken());

            graph[node1].add(node2);
            graph[node2].add(node1);
        }


        System.out.println(bfs(1));
        count = 0;
        visited = new boolean[N + 1];
        System.out.println(dfs(1));
    }

    public static int bfs(int node){

        Queue<int[]> queue = new LinkedList<>();
        visited[node] = true;
        queue.offer(new int[]{node, count});

        while (!queue.isEmpty()){
            int cur = queue.poll()[0]; //현재 위치한 노드의 번호

            //내가 방문할 수 있는 노드 후보군
            for(int next: graph[cur]){
                if(!visited[next]){
                    visited[next] = true;
                    queue.offer(new int[]{next, count++});
                }
            }

        }

        return count-1; //1번 노드는 뺀다.
    }

    public static int dfs(int node){
        visited[node] = true;
        count++;

        //방문할 수 있는 노드들 참색
        for(int next: graph[node]){
            //아직 방문 안한 노드면 방문
            if(!visited[next]){
                //다음 노드로 탐색
                dfs(next);
            }

        }
        return count - 1;

    }

}
