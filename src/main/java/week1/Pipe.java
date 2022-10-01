package week1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */


public class Pipe {

    public static void main(String[] args) throws IOException {
        String input = args[0];
        String[] strArr = input.split(" ");
        String key = strArr[0];
        switch (key) {
            case "cat":
                cat(strArr);
                break;
            case "wc":
                wc(strArr[1]);
                break;
        }
    }

    public static void cat(String[] str) throws IOException {
        List<String> list = getData(str[1].replaceAll(" ", ""));
        List<String> result = new ArrayList<String>();
        boolean flag = true;
        for (int i = 2; i < str.length; i++) {
            String s1 = str[i];
            if (s1.equals("sort")) {
                result = sort(list);
                list = result;
            } else if (s1.equals("uniq")) {
                result = unique(list);
                list = result;
            } else if (s1.equals("wc")) {
                wcWithCat(list);
                flag = false;
            }
        }
        if (flag) {
            for (String string : list) {
                System.out.println(string);
            }
        }
    }

    public static List<String> getData(String path) throws IOException {
        List<String> listOfStrings = new ArrayList<String>();
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String line = bf.readLine();

        // checking for end of file
        while (line != null) {
            listOfStrings.add(line);
            line = bf.readLine();
        }
        bf.close();

        return listOfStrings;
    }

    public static List<String> sort(List<String> list) throws IOException {
        Collections.sort(list);
        return list;
    }

    public static List<String> unique(List<String> list) {
        List<String> list1 = list.stream().distinct().collect(Collectors.toList());
        return list1;
    }

    public static void wc(String path) throws IOException {

        File file = new File(path);
        String content = Files.readString(Paths.get(path));

        String[] arr = content.split(" ");
        int l = 0;

        FileReader f = new FileReader(path);
        BufferedReader br = new BufferedReader(f);

        while (br.readLine() != null) {
            l++;

        }

        System.out.print((l == 1 ? 0 : l) + "\t");
        System.out.print(arr.length + "\t");
        System.out.print(content.length() + "\t");
        System.out.print(file.getName());
        br.close();

    }

    public static void wcWithCat(List<String> list) throws IOException {

        int countOfWord = 0;
        int charCount = 0;
        for (String word : list) {
            String[] words = word.split("\\s+");
            countOfWord += words.length;
            charCount += word.length();
        }

        System.out.print(list.size() + "\t");
        System.out.print(countOfWord + "\t");
        System.out.print(charCount + "\t");

    }


}
