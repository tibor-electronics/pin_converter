/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.MessageFormat;

import org.junit.Test;

import com.kevlindev.utils.IOUtils;

/**
 * CommandLineArgsTests
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class CommandLineArgsTests {
	protected void assertOutput(String[] args, String testFile) {
		// capture stdout
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		PrintStream stdout = new PrintStream(outStream);
		System.setOut(stdout);

		// run command
		PinConverter.main(args);

		// now compare stdout to a file containing the expected output
		try {
			FileInputStream input = new FileInputStream(testFile);
			String expected = IOUtils.getString(input);
			String message = MessageFormat.format("Converter output does not match contents of {0}", testFile);

			assertEquals(message, expected, outStream.toString());
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testTransform() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"Papilio One",
				"--destinationBoard",
				"Papilio RAM",
				"UCF/transform-before.ucf"
			},
			"UCF/transform-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testTransform2() {
		// @formatter:off
		assertOutput(
			new String[] {
				"-src",
				"p1",
				"-dst",
				"pram",
				"UCF/transform-before.ucf"
			},
			"UCF/transform-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testMoveWing() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--move",
				"A->B",
				"UCF/move-wing-before.ucf"
			},
			"UCF/move-wing-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testMoveWingLowercase() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--move",
				"a->b",
				"UCF/move-wing-before.ucf"
			},
			"UCF/move-wing-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testMoveHalfWing() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--move",
				"AL->AH",
				"UCF/move-half-wing-before.ucf"
			},
			"UCF/move-half-wing-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testMoveHalfWingLowercase() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--move",
				"al->ah",
				"UCF/move-half-wing-before.ucf"
			},
			"UCF/move-half-wing-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testMoveHalfWings() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--move",
				"AL->AH,BH->BL",
				"UCF/move-half-wings-before.ucf"
			},
			"UCF/move-half-wings-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testMoveHalfWingsLowercase() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--move",
				"al->ah,bh->bl",
				"UCF/move-half-wings-before.ucf"
			},
			"UCF/move-half-wings-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testTransformAndMoveWing() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--destinationBoard",
				"pram",
				"--move",
				"A->D",
				"UCF/transform-and-move-before.ucf"
			},
			"UCF/transform-and-move-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testTransformAndMoveWingLowercase() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--destinationBoard",
				"pram",
				"--move",
				"a->d",
				"UCF/transform-and-move-before.ucf"
			},
			"UCF/transform-and-move-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testReverseWing() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--move",
				"A[15:0]->A[0:15]",
				"UCF/reverse-wing-before.ucf"
			},
			"UCF/reverse-wing-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testReverseWingLowercase() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--move",
				"a[15:0]->a[0:15]",
				"UCF/reverse-wing-before.ucf"
			},
			"UCF/reverse-wing-after.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testGenerate() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One",
				"--generate",
				"all"
			},
			"UCF/p1.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testGenerateSubset() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One",
				"--generate",
				"CLK,A,BL"
			},
			"UCF/p1-subset.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testGenerateSubsetLowercase() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One",
				"--generate",
				"clk,a,bl"
			},
			"UCF/p1-subset.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testPlaceWing() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One",
				"--placeWing",
				"B/LED->A",
				"--generate",
				"CLK,AL"
			},
			"UCF/p1-with-bled.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testPlaceWings() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One",
				"--placeWing",
				"B/LED->AL,AH",
				"--generate",
				"CLK,A"
			},
			"UCF/p1-with-bleds.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testPlaceWings2() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One",
				"--placeWing",
				"B/LED->AL;PS/2->BH",
				"--generate",
				"CLK,AL,BH"
			},
			"UCF/p1-with-bled-ps2.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testPlaceMegaWing() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One",
				"--placeWing",
				"Arcade->A",
				"--generate",
				"CLK,A,B,C"
			},
			"UCF/p1-with-arcade.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testPlaceMegaWingAndSort() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One",
				"--placeWing",
				"Arcade->A",
				"--sort",
				"--generate",
				"CLK,A,B,C"
			},
			"UCF/p1-with-arcade-sorted.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testPlaceMegaWingAndSortAndExclude() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One 500K",
				"--placeWing",
				"Arcade->A",
				"--sort",
				"--excludeUnusedPins",
				"--generate",
				"CLK,A,B,C"
			},
			"UCF/p1_500-with-arcade-sorted-excluded.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testPlaceMegaWingAndSortAndExcludeAndAddSpaces() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--destinationBoard",
				"Papilio One 500K",
				"--placeWing",
				"Arcade->A",
				"--sort",
				"--excludeUnusedPins",
				"--spacesAroundEquals",
				"--generate",
				"CLK,A,B,C"
			},
			"UCF/p1_500-with-arcade-sorted-excluded-spaces.ucf"
		);
		// @formatter:on
	}

	@Test
	public void testVerify() {
		// @formatter:off
		assertOutput(
			new String[] {
				"--sourceBoard",
				"p1",
				"--inputFile",
				"UCF/p1.ucf",
				"--validate"
			},
			"UCF/verify.txt"
		);
		// @formatter:on
	}
}
