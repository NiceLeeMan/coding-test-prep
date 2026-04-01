package org.example.Greedy;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1541 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        String[] minusParts = input.split("-");

        int result = 0;

        for (String num : minusParts[0].split("\\+")) {
            result += Integer.parseInt(num);
        }

        for (int i = 1; i < minusParts.length; i++) {
            for (String num : minusParts[i].split("\\+")) {
                result -= Integer.parseInt(num);
            }
        }

        System.out.println(result);



    }
}
