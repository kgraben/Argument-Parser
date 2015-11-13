package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import edu.jsu.mcis.*;
import org.junit.rules.ExpectedException;

public class ArgumentParserTest {

	public ArgumentParser ap;
	public XML xml;

	@Before
	public void startUp(){
		ap = new ArgumentParser();
		xml = new XML();
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test (expected=MissingArgumentException.class)
	public void testOnly1numberEntered() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data = {"7"};
		ap.parse(data);
	}

	@Test (expected=MissingArgumentException.class)
	public void testLessThan3NumbersEntered() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data = {"7","5"};
		ap.parse(data);
	}

	@Test (expected=UnknownArgumentException.class)
	public void testMoreThan3NumbersEntered() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data = {"7","5", "3", "7"};
		ap.parse(data);
	}

	@Test
	public void testGetLength() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data = {"7","5", "3"};
		ap.parse(data);
		assertEquals(ap.getValue("length"), "7");
	}

	@Test
	public void testHelpMessageCalled() {
		expectedEx.expect(HelpMessageException.class);
		String message = ("usage: java VolumeCalculator [length][width][height]" +
		 "\n" + "Calculate the volume of a box." + "\n" + "positional arguments:" +
		 "\n" +   "length the length of the box (FLOAT)"  + "\n" +
		 "width the width of the box (FLOAT)" + "\n" +
		 "height the height of the box (FLOAT)");
		expectedEx.expectMessage(message);
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length", Argument.Type.FLOAT, "the length of the box");
		ap.addPositionalArgument("width", Argument.Type.FLOAT, "the width of the box");
		ap.addPositionalArgument("height", Argument.Type.FLOAT, "the height of the box");
		String[] data = {"-h"};
		ap.parse(data);
	}

	@Test
	public void testGetDefaultType() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type", "t", Argument.Type.STRING, "Box");
		String[] data = {"7","5", "3"};
		ap.parse(data);
		assertEquals("Box", ap.getValue("Type"));
	}

	@Test
	public void testGetType() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type", "t" , Argument.Type.STRING, "Box");
		String[] data = {"7","5", "3", "--Type","circle"};
		ap.parse(data);
		assertEquals("circle", ap.getValue("Type"));
	}

	@Test
	public void testGetColor() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type", "t",Argument.Type.STRING, "Box");
		ap.addNamedArgument("Color", "c", Argument.Type.STRING, "Red");
		String[] data = {"7","5", "3", "--Type","circle","--Color","Blue"};
		ap.parse(data);
		assertEquals("Blue", ap.getValue("Color"));
	}

	@Test
	public void testGetPizza() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Pizza", "p", Argument.Type.STRING, "pepperoni");
		ap.addNamedArgument("Color", "c", Argument.Type.STRING, "Red");
		String[] data = {"7","5", "3", "--Pizza","cheese","--Color","Blue"};
		ap.parse(data);
		assertEquals("cheese", ap.getValue("Pizza"));
	}

	@Test
	public void testGetDigit() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type", "t",Argument.Type.STRING, "Box");
		ap.addNamedArgument("Digits", "d", Argument.Type.INT, "4");
		String[] data = {"7","5", "3", "--Type","circle","--Digits","1"};
		ap.parse(data);
		assertEquals(1, ap.getValue("Digits"));
	}

	@Test
	public void testGetTypeAnywhere() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type", "t", Argument.Type.STRING, "Box");
		ap.addNamedArgument("Digits", "d", Argument.Type.INT, "4");
		String[] data = {"--Type","circle","7","5","--Digits","8","3"};
		ap.parse(data);
		assertEquals("circle", ap.getValue("Type"));
	}

	@Test
	public void testGetDigitsAnywhere() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type", "t", Argument.Type.STRING, "Box");
		ap.addNamedArgument("Digits", "d", Argument.Type.INT, "4");
		String[] data = {"--Type","circle","2","5","--Digits","7","3"};
		ap.parse(data);
		assertEquals(7, ap.getValue("Digits"));
	}

	@Test
	public void testGetLengthAnywhere() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type", "t", Argument.Type.STRING, "Box");
		ap.addNamedArgument("Digits","d", Argument.Type.INT, "4");
		String[] data = {"--Type","circle","2","5","--Digits","7","3"};
		ap.parse(data);
		assertEquals("2", ap.getValue("length"));
	}

	@Test
	public void testGetDigits() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Digits", "d", Argument.Type.INT, "4");
		String[] data = {"7","5", "3"};
		ap.parse(data);
		assertEquals(4, ap.getValue("Digits"));
	}

	@Test
	public void testTypeInt() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length",Argument.Type.INT);
		ap.addPositionalArgument("width",Argument.Type.INT);
		ap.addPositionalArgument("height",Argument.Type.INT);
		String[] data = {"7","5", "3"};
		ap.parse(data);
		int num=7;
		assertEquals(ap.getValue("length"), num);
	}

	@Test
	public void testTypeBoolean() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length",Argument.Type.INT);
		ap.addPositionalArgument("Dog",Argument.Type.BOOLEAN);
		ap.addPositionalArgument("height",Argument.Type.STRING);
		String[] data = {"7","true", "3"};
		ap.parse(data);
		boolean value=true;
		assertEquals(true, ap.getValue("Dog"));
	}

	@Test
	public void testTypeString() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length",Argument.Type.INT);
		ap.addPositionalArgument("Dog",Argument.Type.STRING);
		ap.addPositionalArgument("height",Argument.Type.FLOAT);
		String[] data = {"7","true", "4.0"};
		ap.parse(data);
		assertEquals(ap.getValue("Dog"), "true");
	}

	@Test
	public void testTypeFloat() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length",Argument.Type.INT);
		ap.addPositionalArgument("Dog",Argument.Type.FLOAT);
		ap.addPositionalArgument("height",Argument.Type.FLOAT);
		String[] data = {"7","8", "4"};
		ap.parse(data);
		float number=8.0f;
		assertEquals(ap.getValue("Dog"), number);
	}

	@Test (expected=IncorrectDataTypeException.class)
	public void testInvalidTypeFloat() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length",Argument.Type.FLOAT);
		ap.addPositionalArgument("width",Argument.Type.FLOAT);
		ap.addPositionalArgument("height",Argument.Type.FLOAT);
		String[] data = {"7","something", "4"};
		ap.parse(data);
	}

	@Test
	public void testIncorrectDataTypeExceptionMessageCorrect() {
		expectedEx.expect(IncorrectDataTypeException.class);
		String message = ("usage: java VolumeCalculator [length][width][height]" +
		"\n" + "VolumeCalculator.java: error: argument width: invalid FLOAT value: something");
		expectedEx.expectMessage(message);
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length",Argument.Type.FLOAT);
		ap.addPositionalArgument("width",Argument.Type.FLOAT);
		ap.addPositionalArgument("height",Argument.Type.FLOAT);
		String[] data = {"7","something", "4"};
		ap.parse(data);
	}

	@Test
	public void testHelpMessageStringBuilder() {
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		assertEquals("[length][width][height]", ap.getMissingArguments() );
	}

	@Test (expected=IncorrectDataTypeException.class)
	public void testInvalidTypeInteger() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length",Argument.Type.FLOAT);
		ap.addPositionalArgument("width",Argument.Type.INT);
		ap.addPositionalArgument("height",Argument.Type.FLOAT);
		String[] data = {"7","something", "4"};
		ap.parse(data);
	}

	@Test
	public void testGetHelpMessageShortNameAnywhere() {
		expectedEx.expect(HelpMessageException.class);
		String message = ("usage: java VolumeCalculator [length][width][height]" +
		"\n" + "Calculate the volume of a box." + "\npositional arguments:" +
		"\n" +   "length the length of the box (FLOAT)"  + "\n" +
		"width the width of the box (FLOAT)" + "\nheight the height of the box (FLOAT)");
		expectedEx.expectMessage(message);
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length", Argument.Type.FLOAT, "the length of the box");
		ap.addPositionalArgument("width", Argument.Type.FLOAT, "the width of the box");
		ap.addPositionalArgument("height", Argument.Type.FLOAT, "the height of the box");
		String[] data = {"7","5", "-h", "4"};
		//System.out.println("|" + message + "|");
		//try {
			ap.parse(data);
		//}
		//catch(Exception e) {
		//	System.out.println("|" + e.getMessage() + "|");
		//}
	}

	@Test
	public void testGetHelpMessageLongNameAnywhere() {
		expectedEx.expect(HelpMessageException.class);
		String message = ("usage: java VolumeCalculator [length][width][height]" +
		"\n" + "Calculate the volume of a box." + "\n" + "positional arguments:" +
		"\n" +   "length the length of the box (FLOAT)"  + "\n" +
		"width the width of the box (FLOAT)" + "\n" +
		"height the height of the box (FLOAT)");
		expectedEx.expectMessage(message);
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length", Argument.Type.FLOAT, "the length of the box");
		ap.addPositionalArgument("width", Argument.Type.FLOAT, "the width of the box");
		ap.addPositionalArgument("height", Argument.Type.FLOAT, "the height of the box");
		String[] data = {"7","5", "--help", "4"};
		ap.parse(data);
	}

	@Test
	public void testGetHelpMessageAnywhereWithNamedArguments() {
		expectedEx.expect(HelpMessageException.class);
		String message = ("usage: java VolumeCalculator [length][width][height][Type][t][Digits][d][--Type]" +
		"\n" + "Calculate the volume of a box." + "\n" + "positional arguments:" +
		"\n" + "length the length of the box (FLOAT)"  + "\n" +
		"width the width of the box (FLOAT)" + "\n" + "height the height of the box (FLOAT)\n" +
		"Type  (STRING)\n" + "Type  (STRING)\n" + "Digits  (INT)\n" + "Digits  (INT)");
		expectedEx.expectMessage(message);
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length", Argument.Type.FLOAT, "the length of the box");
		ap.addPositionalArgument("width", Argument.Type.FLOAT, "the width of the box");
		ap.addPositionalArgument("height", Argument.Type.FLOAT, "the height of the box");
		ap.addNamedArgument("Type", "t", Argument.Type.STRING, "Box");
		ap.addNamedArgument("Digits", "d", Argument.Type.INT, "4");
		String[] data = {"7","--Type","circle","5", "--h", "4","--Digits","2"};
		ap.parse(data);
	}


	@Test
	public void testShortNamedArgument() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type", "t", Argument.Type.STRING, "Box");
		ap.addNamedArgument("Digits","d", Argument.Type.INT, "4");
		String[] data = {"7","-t","circle","5", "4","--Digits","2"};
		ap.parse(data);
		assertEquals("circle",ap.getValue("Type"));
	}

	@Test
	public void testShortNamedColorArgument() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("color", "c", Argument.Type.STRING, "Blue");
		ap.addNamedArgument("Digits", "d", Argument.Type.INT, "4");
		String[] data = {"7","-c","red","5", "4","--Digits","2"};
		ap.parse(data);
		assertEquals("red",ap.getValue("color"));
	}

	@Test
	public void testGetHelp() {
		expectedEx.expect(HelpMessageException.class);
		String message = ("usage: java VolumeCalculator [length][width][height]" +
		"\n" + "Calculate the volume of a box." + "\n" + "positional arguments:" +
		"\n" +   "length the length of the box (FLOAT)"  + "\n" +
		"width the width of the box (FLOAT)" + "\n" +
		"height the height of the box (FLOAT)");
		expectedEx.expectMessage(message);
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length", Argument.Type.FLOAT, "the length of the box");
		ap.addPositionalArgument("width", Argument.Type.FLOAT, "the width of the box");
		ap.addPositionalArgument("height", Argument.Type.FLOAT, "the height of the box");
		String[] data = {"--help"};
		ap.parse(data);
	}

	@Test
	public void testGetProgramName() {
		ap.assignProgramName("VolumeCalculator");
		assertEquals (ap.getProgramName(), "VolumeCalculator");
	}

	@Test
	public void testAssignProgramDescription() {
		ap.assignProgramDescription("Calculate the volume of a box");
		assertEquals (ap.getProgramDescription(), "Calculate the volume of a box");
	}

	/*
	@Test
	public void testXmlFileIsRead() {
		xml.setFileName("arguments.xml");
		assertEquals("arguments.xml", xml.getFileName());
	}

	@Test
	public void testXmlFileIsNotRead() {
		expectedEx.expect(FileErrorException.class);
		String message = "Not Found: fakeFile.xml";
		expectedEx.expectMessage(message);
		xml.setFileName("fakeFile.xml");
	}

	@Test
<<<<<<< HEAD
	public void testLoadsPositionalArguments(){
		xml.setFileName("arguments.xml");
		assertEquals("arguments.xml", xml.getFileName());
		//assertEquals("Something", xml.returnLoad());
	}

	@Test
	public void testWeCanParseXML(){
		xml.setFileName("arguments.xml");
		assertEquals("arguments.xml", xml.getFileName());
		assertEquals("", "");
	}

	@Test
	public void testXmlGetProgramName(){
=======
	public void testWeGetFloatDataTypeFromArguments() {
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box");
>>>>>>> 8c5a7896b68b7b88fcc73b6796d8f85b7d53da1f
		xml.setFileName("arguments.xml");
		xml.loadXML();
		String[] inp = {"6", "7", "8"};
		ap.parse(inp);
		assertEquals(6, ap.getValue("length"));
		assertEquals(7, ap.getValue("width"));
		assertEquals(8, ap.getValue("height"));
	}
	*/
	
	 @Test
    public void testLoadXMLGetPositionalArgumentValue() {
       
       	xml.loadXML("arguments.xml");
       	String[] data = {"2","5","3"};
       	ap.parse(data);
       	
        
        assertEquals(6, ap.getValue("Length"));
        assertEquals(7, ap.getValue("Width"));
        assertEquals(8, ap.getValue("Height"));
    }

}
