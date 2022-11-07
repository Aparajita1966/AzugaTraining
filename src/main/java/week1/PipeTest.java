package week1;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -aparajita.
 */

public class PipeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(Pipe1.class);

	Pipe1 pipe = new Pipe1();

	@Test
	void catSortHeadTest() {
		LOGGER.info("Testing for Cat Sort Head");
		try {
			String expected = "Andhra Pradesh\nArunachal Pradesh\nAssam\nBihar\n";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states.txt | sort | head -4");
			Assertions.assertEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void catSortHeadWithWrongInputTest() {
		LOGGER.info("Testing for Cat Sort Head with Wrong Input");
		try {
			String expected = "Andhra Pradesh\nAssam\nArunachal Pradesh\nBihar\n";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states1.txt | sort | head -4");
			Assertions.assertNotEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void catUniqTailTest() {
		LOGGER.info("Testing for Cat Sort Head");
		try {
			String expected = "Chhattisgarh\nArunachal Pradesh\nGoa\nPunjab\nBihar\n";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states.txt | uniq | tail -5");
			Assertions.assertEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void catUniqTailWithWrongInputTest() {
		LOGGER.info("Testing for Cat Sort Head with Wrong Input");
		try {
			String expected = "Chhattisgarh\nArunachal Pradesh\nGoa\nPunjab\nBihar\nAssam\n";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states.txt | uniq | tail -5");
			Assertions.assertNotEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void catWcTest() {
		LOGGER.info("Testing for Cat Wc");
		try {
			String expected = "10\t12\t86";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states.txt | wc");
			Assertions.assertEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void catHeadTest() {
		LOGGER.info("Testing for Cat Head");
		try {
			String expected = "Andhra Pradesh\nAssam\nBihar\nPunjab\nGoa\nChhattisgarh\nArunachal Pradesh\nGoa\n";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states.txt | head -8");
			Assertions.assertEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void catTailTest() {
		LOGGER.info("Testing for Cat Tail");
		try {
			String expected = "Goa\nChhattisgarh\nArunachal Pradesh\nGoa\nPunjab\nBihar\n";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states.txt | tail -6");
			Assertions.assertEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void catSortHeadWcTest() {
		LOGGER.info("Testing for Cat Sort Head Wc");
		try {
			String expected = "4\t6\t45";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states.txt | sort | head -4 | wc");
			Assertions.assertEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void catWcWithWrongInputTest() {
		LOGGER.info("Testing for Cat Wc with Wrong Input");
		try {
			String expected = "9\t12\t86";
			String actual = pipe.pipes("cat /Users/aparajita/Downloads/states.txt | wc");
			Assertions.assertNotEquals(expected, actual);
		} catch (Exception e) {
			LOGGER.info("File not found");
		}
	}

	@Test
	void fileNotFoundTest() {
		assertThrows(FileNotFoundException.class, () -> {
			pipe.pipes("cat /Users/aparajita/Downloads/test.txt | wc");
		});
	}
}
