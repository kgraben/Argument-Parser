package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import edu.jsu.mcis.*;

public class ArgumentParserTest {

	private ArgumentParser ap;
	private Argument argument;

	@Before
	public void startUp(){
		ap=new ArgumentParser();
		argument = new Argument();
	}

	@Test
	public void testOnly3numbersEntered(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data= {"7","5","2"};
		ap.parse(data);
		assertEquals(3,ap.getSizeOfHashMap());
	}

	@Test
	public void testMissingArgumentWidthAndHeightMessageNotCalled(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data= {"7","5","2"};
		ap.parse(data);
		assertEquals(false,ap.isMissingArgumentWidthAndHeightMessageCalled());
	}

	@Test
	public void testOnly1numberEntered(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data={"7"};
		ap.parse(data);
		assertEquals(true,ap.isMissingArgumentWidthAndHeightMessageCalled());
		assertEquals(false,ap.isMissingArgumentHeightMessageCalled());
	}

	@Test
	public void testLessThan3NumbersEntered(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data= {"7","5"};
		ap.parse(data);
		assertEquals(true, ap.isMissingArgumentHeightMessageCalled());
		assertEquals(false,ap.isMissingArgumentWidthAndHeightMessageCalled());
		assertEquals(false,ap.isUnrecognizedArgumentsMessageCalled());
	}

	@Test
	public void testMoreThan3NumbersEntered(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data= {"7","5", "3", "7"};
		ap.parse(data);
		assertEquals(true,ap.isUnrecognizedArgumentsMessageCalled());
	}

	@Test
	public void testGetLength(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data= {"7","5", "3"};
		ap.parse(data);
		assertEquals(ap.getValue("length"), "7");
		assertEquals(false,ap.isHelpMessageCalled());
	}

  @Test
	public void testHelpMessageCalled(){
		String[] data= {"-h"};
		ap.parse(data);
		assertEquals(true, ap.isHelpMessageCalled());
  }

	@Test
	public void testGetDefaultType(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type");
		String[] data= {"7","5", "3"};
		ap.parse(data);
		assertEquals("Box", ap.getValue("Type"));
  }

  @Test
	public void testGetType(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type");
		String[] data= {"7","5", "3", "--Type","circle"};
		ap.parse(data);
		assertEquals("circle", ap.getValue("Type"));
	}

  @Test
	public void testGetColor(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type");
		ap.addNamedArgument("Color");
		String[] data= {"7","5", "3", "--Type","circle","--Color","Blue"};
		ap.parse(data);
		assertEquals("Blue", ap.getValue("Color"));
  }

  @Test
	public void testGetPizza(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Pizza");
		ap.addNamedArgument("Color");
		String[] data= {"7","5", "3", "--Pizza","cheese","--Color","Blue"};
		ap.parse(data);
		assertEquals("cheese", ap.getValue("Pizza"));
	}

  @Test
	public void testGetDigit(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type");
		ap.addNamedArgument("Digits");
		String[] data= {"7","5", "3", "--Type","circle","--Digits","1"};
		ap.parse(data);
		assertEquals("1", ap.getValue("Digits"));
	}

  @Test
	public void testGetTypeAnywhere(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type");
		ap.addNamedArgument("Digits");
		String[] data= {"--Type","circle","7","5","--Digits","4","3"};
		ap.parse(data);
		assertEquals("circle", ap.getValue("Type"));
	}

  @Test
	public void testGetDigitsAnywhere(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type");
		ap.addNamedArgument("Digits");
		String[] data= {"--Type","circle","2","5","--Digits","7","3"};
		ap.parse(data);
		assertEquals("7", ap.getValue("Digits"));
	}

  @Test
	public void testGetLengthAnywhere(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Type");
		ap.addNamedArgument("Digits");
		String[] data= {"--Type","circle","2","5","--Digits","7","3"};
		ap.parse(data);
		assertEquals("2", ap.getValue("length"));
	}
	@Test
	public void testGetDigits(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		ap.addNamedArgument("Digits");
		String[] data= {"7","5", "3"};
		ap.parse(data);
		assertEquals("4", ap.getValue("Digits"));
	}
	@Test
  public void testDataTypeInt(){
		ap.addPositionalArgument("length",Argument.DATATYPE.INT);
		ap.addPositionalArgument("width",Argument.DATATYPE.INT);
		ap.addPositionalArgument("height",Argument.DATATYPE.INT);
		String[] data= {"7","5", "3"};
		ap.parse(data);
		int num=7;
		assertEquals(ap.getValue("length"), num);
  }
	@Test
  public void testDataTypeBoolean(){
		ap.addPositionalArgument("length",Argument.DATATYPE.INT);
		ap.addPositionalArgument("Dog",Argument.DATATYPE.BOOLEAN);
		ap.addPositionalArgument("height",Argument.DATATYPE.STRING);
		String[] data= {"7","true", "3"};
		ap.parse(data);
		boolean value=true;
		assertEquals(true, ap.getValue("Dog"));
  }
	@Test
  public void testDataTypeString(){
		ap.addPositionalArgument("length",Argument.DATATYPE.INT);
		ap.addPositionalArgument("Dog",Argument.DATATYPE.STRING);
		ap.addPositionalArgument("height",Argument.DATATYPE.FLOAT);
		String[] data= {"7","true", "4.0"};
		ap.parse(data);
		assertEquals(ap.getValue("Dog"), "true");
  }
	@Test
  public void testDataTypeFloat(){
		ap.addPositionalArgument("length",Argument.DATATYPE.INT);
		ap.addPositionalArgument("Dog",Argument.DATATYPE.FLOAT);
		ap.addPositionalArgument("height",Argument.DATATYPE.FLOAT);
		String[] data= {"7","8", "4"};
		ap.parse(data);
		float number=8.0f;
		assertEquals(ap.getValue("Dog"), number);
  }

	@Test
  public void testInvalidDataTypeFloat(){
		ap.addPositionalArgument("length",Argument.DATATYPE.FLOAT);
		ap.addPositionalArgument("width",Argument.DATATYPE.FLOAT);
		ap.addPositionalArgument("height",Argument.DATATYPE.FLOAT);
		String[] data= {"7","something", "4"};
		ap.parse(data);
		assertEquals(true,ap.isIncorrectDataTypeMessageCalled());
	}

  @Test
  public void testInvalidDataTypeInteger(){
		ap.addPositionalArgument("length",Argument.DATATYPE.FLOAT);
		ap.addPositionalArgument("width",Argument.DATATYPE.INT);
		ap.addPositionalArgument("height",Argument.DATATYPE.FLOAT);
		String[] data= {"7","something", "4"};
		ap.parse(data);
		assertEquals(true,ap.isIncorrectDataTypeMessageCalled());
	}

	@Test
	public void testGetHelp(){
		String[] data ={"--h"};
		ap.parse(data);
		assertEquals("usage: java VolumeCalculator length width height" + "\n" + "Calcuate the volume of a box." + "\n" + "positional arguments:" + "\n" +   "length the length of the box (float)"  + "\n" +   "width the width of the box(float)" + "\n" + "height the height of the box(float)",ap.getHelpMessage());
	}

	@Test
	public void testSetAndGetUserArg() {
		String test = "Ya'ay!";
		argument.setUserArg(test);
		assertEquals(test, argument.getUserArg());
	}

	@Test
	public void testIsIncorrectDataTypeMessageCalledReturnsFalse(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data= {"7","5"};
		ap.parse(data);
		assertFalse(ap.isIncorrectDataTypeMessageCalled());
	}

	@Test
	public void testIsUnrecognizedArgumentsMessageCalledReturnsFalse(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data= {"7","5","2"};
		ap.parse(data);
		assertFalse(ap.isUnrecognizedArgumentsMessageCalled());
	}

	@Test
	public void testIsMissingArgumentHeightMessageCalledReturnsFalse(){
		ap.addPositionalArgument("length");
		ap.addPositionalArgument("width");
		ap.addPositionalArgument("height");
		String[] data= {"7","5","2"};
		ap.parse(data);
		assertFalse(ap.isMissingArgumentHeightMessageCalled());
	}
}
