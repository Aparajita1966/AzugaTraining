package week2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class XMLCreators {

    public static ArrayList<String[]> CSVtoArrayList(String csvFile, String csvSplit) throws IOException {
        ArrayList<String[]> elements = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        String line;
        while ((line = Objects.requireNonNull(csvReader).readLine()) != null) {
            String[] nodes = line.split(csvSplit);
            elements.add(nodes);
        }

        return elements;
    }
}

