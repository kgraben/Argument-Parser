package edu.jsu.mcis;
/**
* @author (TeamName)
*/

/**
* Argument class sets and provides Argument Parser parameters
*/
public class Argument {
/**
* Data type constants INT, FLOAT, BOOLEAN, STRING
*/
	public enum Type {
		/**
		* A integer data type
		*/
		INT,
		/**
		* A floating point number data type
		*/
		FLOAT,
		/**
		* A true/false boolean data type
		*/
		BOOLEAN,
		/**
		* A character String data type
		*/
		STRING
	};
	private String value;
	protected String name;
	protected String shortName;
	private Type type;
	private String description;
	/**
	* Class constructor
	*/
	public Argument() {
		value = "0";
		name = "";
		shortName = "";
		type = Type.STRING;
		description = "";
	}
	/** Sets the value of the Argument object
	* @param value the value of the Argument
	*/
	public void setValue(String value) {
		this.value = value;
	}
	/** Returns the value of the Argument object
	* @return the value of the Argument
	*/
	public String getValue() {
		return value;
	}
	/** Sets the type of the Argument object
	* @param type the type of the Argument
	*/
	public void setType(Type type) {
		this.type = type;
	}
	/** Returns the type of the Argument object
	* @return the type of the Argument
	*/
	public Type getType() {
		return type;
	}
	/** Sets the name of the Argument object
	* @param name the name of the Argument
	*/
	public void setName(String name) {
		this.name = name;
	}
	/** Returns the name of the Argument object
	* @return the name of the Argument
	*/
	public String getName() {
		return name;
	}
	/** Sets the description of the Argument object
	* @param description the description of the Argument
	*/
	public void setDescription(String description) {
		this.description = description;
	}
	/** Returns the description of the Argument object
	* @return the description of the Argument
	*/
	public String getDescription() {
		return description;
	}
}
