package org.example.dfsbfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1260 {
    static int N; // 정점의 개수
    static int M; // 간선의 개수
    static int V; // 탐색을 시작할 정점의 번호
    static List<Integer>[] graph;
    static boolean[] visited; //각 노드에 방문 햇는지를 나타내는 변수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());

        visited = new boolean[N + 1];
        graph = new ArrayList[N + 1];

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph[u].add(v);
            graph[v].add(u); // 양방향
        }

        for (int i = 1; i <= N; i++) {
            Collections.sort(graph[i]);
        }
        dfs(V);
        visited = new boolean[N + 1];
        bfs(V);
    }

    // u: 내가 방문할 예정인 노드의 번호
    public static void dfs(int node) {
        visited[node] = true;
        System.out.print(node + " ");
        //우선 현재 내가 방문할 수 있는 노드들 을 탐색해야한다. 2,3,4중 선택(선택기준은? 난 그냥 반복문 생각)

        for(int next:graph[node]){
            if(!visited[next]){ //아직 해당 노드를 방문하지 않았다면 방문한다
                visited[next] = true; //방문표시
                dfs(next);
            }
        }
    }

    // u: 내가 방문할 예정인 노드의 번호
    public static void bfs (int node) {
        Queue<Integer> queue = new LinkedList<>();
        visited[node] = true;
        queue.offer(node); //시작 노드를 큐에 넣는것.

        while(!queue.isEmpty()){ //더이상 방문할 인접노드가 없을때 까지 반복
            int cur = queue.poll();
            System.out.print(cur + " ");
            for(int next : graph[cur]){
                if(!visited[next]){
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
    }
}
