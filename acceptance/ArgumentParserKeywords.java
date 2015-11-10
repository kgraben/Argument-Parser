import edu.jsu.mcis.*;

public class ArgumentParserKeywords {

  private ArgumentParser ap;
 	private String output;

  public void StartVolumeCalculatorWithArguments(String[] args) throws UnknownArgumentException{
  	ap=new ArgumentParser();
  	ap.assignProgramName("VolumeCalculator");
  	ap.assignProgramDescription("Calculate the volume of a box.");
  	ap.addPositionalArgument("length");
	ap.addPositionalArgument("width");
	ap.addPositionalArgument("height");
	ap.addNamedArgument("Type", "t", Argument.Type.STRING, "Box");
	ap.addNamedArgument("Digits", "d", Argument.Type.INT, "4");

		try{
			ap.parse(args);
			int width = Integer.parseInt(getWidth());
			int height = Integer.parseInt(getHeight());
			int length = Integer.parseInt(getLength());
			output = String.valueOf(width * length * height);
		}
		catch(UnknownArgumentException ex){
			output = ex.getMessage();
		}
  }

  public void StartVolumeCalculatorWithUserArguments(String[] args){
  	ap=new ArgumentParser();
  	ap.assignProgramName("VolumeCalculator");
  	ap.assignProgramDescription("Calculate the volume of a box.");
  	ap.addPositionalArgument("length");
	ap.addPositionalArgument("width");
	ap.addPositionalArgument("height");
	ap.parse(args);
  }

  public void StartProgramWithArguments(String[] args){
  	ap=new ArgumentParser();
  	ap.assignProgramName("VolumeCalculator");
  	ap.assignProgramDescription("Calcuate the volume of a box.");
  	ap.addPositionalArgument("length", Argument.Type.FLOAT, "the length of the box");
    ap.addPositionalArgument("width", Argument.Type.FLOAT, "the width of the box");
    ap.addPositionalArgument("height", Argument.Type.FLOAT, "the height of the box");

    try {
    	ap.parse(args);
    }
    catch(HelpMessageException ex){
      output = ex.getMessage();
    }
  }

  public void StartAbsurdProgramWithArguments(String[] args){
  	ap=new ArgumentParser();

  	ap.addPositionalArgument("pet");
		ap.addPositionalArgument("number");
		ap.addPositionalArgument("rainy");
		ap.addPositionalArgument("bathrooms");
		ap.parse(args);
  }

	public void StartProgramWithDataTypeArguments(String[] args) throws IncorrectDataTypeException{
		ap=new ArgumentParser();
		ap.assignProgramName("VolumeCalculator");
		ap.assignProgramDescription("Calculate the volume of a box.");
		ap.addPositionalArgument("length",Argument.Type.FLOAT);
		ap.addPositionalArgument("width", Argument.Type.FLOAT);
		ap.addPositionalArgument("height",Argument.Type.FLOAT);
		try{
			ap.parse(args);
		}
		catch(IncorrectDataTypeException ex){
        	output = ex.getMessage();
		}
	}

  public String getPet(){
  	return ap.getValue("pet");
  }

  public String getNumber(){
  	return ap.getValue("number");
  }

  public String getRainy(){
  	return ap.getValue("rainy");
  }

  public String getBathrooms(){
  	return ap.getValue("bathrooms");
  }

  public String getLength(){
  	return ap.getValue("length") + "";
  }
  public String getWidth(){
  	return   ap.getValue("width") + "";
  }
  public String getHeight(){
  	return  ap.getValue("height") + "";
  }

  public String getType(){
  	return ap.getValue("Type") + "";
  }

  public String getDigits(){
  	return ap.getValue("Digits") + "";
  }

  public String getProgramOutput(){
  	return output;
  }
}
