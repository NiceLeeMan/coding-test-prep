package org.example.bruteforce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * [회고록 : 직관을 넘어 논리적 재귀로]
 *
 * 1. 문제의 본질과 한계 인식
 * - 처음에는 M중 반복문(for문 M개)을 떠올렸으나, M이 입력값으로 가변적이라는 점에서 구조적 한계를 느낌.
 * - 실행 시점에 반복문의 깊이를 조절할 수 있는 유일한 도구가 '재귀'임을 깨달음.
 * - 즉, 재귀 함수는 '가변적인 중첩 반복문'을 구현하는 역할을 한다.
 *
 * 2. 관점의 전환 (Memory Model)
 * - 직관 : (1,2), (1,3)... 처럼 완성된 수열들이 개별적으로 존재한다고 생각하여 혼란스러웠음.
 * - 논리 : 배열(arr)은 단 하나뿐인 '공용 자원'이다.
 * 우리는 시간을 써서 이 하나의 그릇에 값을 채우고, 기록하고, 비우는 행위를 반복할 뿐이다.
 *
 * 3. 컴퓨터의 사고 방식 (Sequential Scan)
 * - 인간의 사고 : "3 다음엔 남은 게 4니까 4를 넣자"
 * - 컴퓨터의 사고 : 남은 게 무엇인지 한눈에 모른다.
 * 매 단계(idx)마다 1부터 N까지 출석체크(for)를 하고,
 * 장부(visited)를 확인해 안 쓴 숫자를 필터링(if)해야 한다.
 * 이 "무식한 전수조사"가 컴퓨터의 방식이다.
 *
 * 4. 백트래킹의 핵심 (State Recovery)
 * - 가장 이해하기 어려웠던 부분 : visited[i] = false; 가 왜 필요한가?
 * - 이유 : 그릇(arr)과 장부(visited)가 하나뿐이기 때문.
 * 내가 쓴 숫자를 반납(false)하지 않고 나가면, 다른 분기(Branch)에서 그 숫자를 영영 쓸 수 없다.
 * 이는 공용 자원을 쓰고 나서 원상복구(Reset) 해놓는 필수 로직이다.
 *
 * 5. 로직 매핑
 * - depth(idx) : 현재 채워야 할 배열의 인덱스
 * - for(i=1~N) : 넣을 수 있는 모든 수의 후보
 * - if(!visited) : 중복 검사 (Promising)
 */
public class BOJ_15649 {

    static int N, M;
    static int[] arr;
    static boolean[] visited;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[M];
        visited = new boolean[N + 1]; // 1-based index 사용을 위해 N+1 크기 할당
        dfs(0);
        System.out.println(sb);

    }

    public static void dfs(int idx){

        if(idx == M){
            for(int val : arr){
                sb.append(val).append(" ");
            }
            sb.append("\n");
            return;
        }

        for(int i = 1; i <= N; i++) {
            if (!visited[i]) {
                arr[idx] = i;
                visited[i] = true;
                dfs(idx + 1);

                visited[i] = false;

            }
        }
    }
}


















