package week1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class Pipe1 {

	private static final Logger LOGGER = LoggerFactory.getLogger(Pipe1.class);
	public String response = null;

	public String cat(String pathFileName) throws FileNotFoundException {
		String data;
		try {
			if (pathFileName != null) {
				data = Files.readString(Path.of(pathFileName));
				LOGGER.info("Cat filePath " + pathFileName);
				return data;
			}
		} catch (IOException e) {
			LOGGER.error("Cat Input " + e.getMessage());
			e.printStackTrace();
			throw new FileNotFoundException("File not found");
		}
		return null;
	}

	public String ls() {
		StringBuilder data = new StringBuilder();
		String path = System.getProperty("user.dir");
		if (path != null) {
			File f = new File(path);
			String[] arr = f.list();
			for (String i : arr) {
				if (i.charAt(0) != '.') {
					data.append(i).append("\n");
				}
				LOGGER.debug("Files List" + i);
			}
			return data.toString();
		}
		return null;
	}

	public String head(String value, int n) {
		if (value.equals("") || n == 0) {
			return null;
		}
		StringBuilder data = new StringBuilder();
		String[] arr = value.split("\n\r|\n|\r");
		int count = 0;
		for (String i : arr) {
			if (!i.isEmpty() && count != n) {
				data.append(i).append("\n");
				count++;
			}
		}
		return data.toString();
	}

	public String tail(String value, int n) {
		if (value.equals("") || n == 0) {
			return null;
		}
		StringBuilder data = new StringBuilder();
		String[] reverse = value.split("\n\r|\n|\r");
		for (int i = (reverse.length - n); i < reverse.length; i++) {

			data.append(reverse[i]).append("\n");

		}
		return data.toString();
	}

	public String uniq(String value) {
		if (value.equals("")) {
			return null;
		}
		StringBuilder data = new StringBuilder();
		String[] arr = value.split("\n\r|\n|\r");
		data.append(arr[0]).append("\n");
		for (int i = 1; i < arr.length; i++) {
			if (arr[i].equals(arr[i - 1]))
				continue;
			else
				data.append(arr[i]).append("\n");
			LOGGER.debug(" uniq object " + arr[i]);
		}
		return data.toString();
	}

	public String wc(String value) {
		if (value.equals("")) {
			return null;
		}
		long wordCount, lineCount;
		String[] arr = value.split("\n\r|\n|\r");
		lineCount = arr.length;

		Pattern p = Pattern.compile("[a-zA-Z]+");
		Matcher m = p.matcher(value);
		ArrayList<String> array = new ArrayList<>();
		while (m.find()) {
			array.add(m.group());
		}
		wordCount = array.size();
		String data = (lineCount == 0 ? 1 : lineCount) + "\t" + wordCount + "\t" + value.length();
		return data;
	}

	public String sort(String value) {
		if (value.equals("")) {
			return null;
		}
		StringBuilder data = new StringBuilder();
		String[] arr = value.split("\n\r|\n|\r");
		List<String> list = new ArrayList<>();
		for (String string : arr) {
			list.add(string);
		}
		Collections.sort(list);
		for (String lst : list) {
			data.append(lst).append("\n");
		}
		LOGGER.info("sort command executed");
		return data.toString();
	}

	private String select(String str) throws Exception {
		String[] myList = str.split("\\s");
		switch (myList[0]) {
		case "cat": {
			LOGGER.info("cat method called");
			response = cat(myList[1]);
			LOGGER.debug("cat: \n" + response);
		}
			break;
		case "head": {
			LOGGER.info("head method called");
			String value = response;
			int n = Character.getNumericValue(myList[1].charAt(1));
			response = head(value, n);
			LOGGER.debug("head: \n" + response);
		}
			break;
		case "tail": {
			LOGGER.info("tail method called");
			String value = response;
			int n = Character.getNumericValue(myList[1].charAt(1));
			response = tail(value, n);
			LOGGER.debug("tail: \n" + response);
		}
			break;
		case "ls": {
			LOGGER.info("ls method called");
			response = ls();
			LOGGER.debug("ls: \n" + response);
		}
			break;
		case "wc": {
			LOGGER.info("wc method called");
			String value = response;
			response = wc(value);
			LOGGER.debug("wc: \n" + response);
		}
			break;
		case "sort": {
			LOGGER.info("sort method called");
			String value = response;
			response = sort(value);
			LOGGER.debug("sort: \n" + response);
		}
			break;
		case "uniq": {
			LOGGER.info("uniq method called");
			String value = response;
			response = uniq(value);
			LOGGER.debug("uniq: \n" + response);
		}
			break;
		default:
			LOGGER.error("Incorrect Command");
		}
		return response;
	}

	public String pipes(String args) throws Exception {
		String result = null;
		Pipe1 pipes = new Pipe1();

		LOGGER.info("Pipe method called");
		String[] command = args.split("\\|");
		ArrayList<String> myList = new ArrayList<>();
		LOGGER.info("CLI input string manipulation");
		for (String s : command) {
			// Removing all the left spaces.
			String leftTrim = s.replaceAll("^\\s+", "");
			// Removing all the right spaces.
			String rightTrim = leftTrim.replaceAll("\\s+$", "");
			// Removing all the center spaces.
			String center = rightTrim.replaceAll("\\s+", " ");
			myList.add(center);
		}
		int i = 0;
		while (i < myList.size()) {
			result = pipes.select(myList.get(i));
			i++;
		}

		return result;
	}

	public static void main(String[] args) {
		Pipe1 pipes = new Pipe1();
		try {
			LOGGER.info("Pipe method called");
			String arg = args[0];
			String[] command = arg.split("\\|");
			ArrayList<String> myList = new ArrayList<>();
			LOGGER.info("CLI input string manipulation");
			for (String s : command) {
				// Removing all the left spaces.
				String leftTrim = s.replaceAll("^\\s+", "");
				// Removing all the right spaces.
				String rightTrim = leftTrim.replaceAll("\\s+$", "");
				// Removing all the center spaces.
				String center = rightTrim.replaceAll("\\s+", " ");
				myList.add(center);
			}
			int i = 0;
			while (i < myList.size()) {
				String result = pipes.select(myList.get(i));
				LOGGER.info("Output :" + result);
				i++;
			}
		} catch (Exception e) {
			LOGGER.error("Input error " + e.getMessage());
		}
	}
}
