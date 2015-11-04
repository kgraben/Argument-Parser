package edu.jsu.mcis;

public class PositionalArgument extends Argument {
  String description = "";
  public String setDescription(String description) {
    this.description = description;
  }

  public String getDescription(){
    return description;
  }
}
