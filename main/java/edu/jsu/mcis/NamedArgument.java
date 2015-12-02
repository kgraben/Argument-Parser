package edu.jsu.mcis;

/**
* Takes in the name of a named argument
*
*/
public class NamedArgument extends Argument {
/**
* @param name Name of the named argument
*
*/
	public NamedArgument(String name) {
		this.name = name;
	}

	/** Sets the shortName of the Argument object
	* @param shortName the shortName of the Argument
	*/

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/** Returns the shortName of the Argument object
	* @return the shortName of the Argument
	*/

	public String getShortName() {
		return shortName;
	}
}
