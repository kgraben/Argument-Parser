package edu.jsu.mcis;
/**
  * @author (TeamName)
  *
  * 
  */
public class Argument{

	public enum Type {INT, FLOAT, BOOLEAN, STRING};
	private String value;
	protected String name;
	private String shortName;
	private Type type;
	private String description;
	
	
	/**
	 * Class constructor
	 *
	 */

	public Argument(){
		value = "0";
		name = "";
		shortName = "";
		type = Type.STRING;
		description = "";
	}
	
	/** Sets the value of the Argument object
     *
     * @param value the value of the Argument
     */

	public void setValue(String s){
		value = s;
	}
	
	 /** Gets the value of the Argument object
     *
     * @return the value of the Argument
     */

	public String getValue(){
		return value;
	}
		
		
	/** Sets the type of the Argument object
     *
     * @param type the type of the Argument
     */
	public void setType(Type t){
		type = t;
	}
	
	
	 /** Gets the type of the Argument object
     *
     * @return the type of the Argument
     */
     
	public Type getType(){
		return type;
	}
	
	
	/** Sets the name of the Argument object
     *
     * @param name the name of the Argument
     */
	public void setName(String s){
		name = s;
	}
	
	
	 /** Gets the name of the Argument object
     *
     * @return the name of the Argument
     */

	public String getName(){
		return name;
	}
	
	/** Sets the shortName of the Argument object
     *
     * @param shortName the shortName of the Argument
     */
	public void setShortName(String n){
		shortName = n;

	}
	
	 /** Gets the shortName of the Argument object
     *
     * @return the shortName of the Argument
     */
	
	public String getShortName(){
		return shortName;
	}
	
	/** Sets the description of the Argument object
     *
     * @param description the description of the Argument
     */
	public void setDescription(String desc) {
		description = desc;
	}
	
	 /** Gets the description of the Argument object
     *
     * @return the description of the Argument
     */
	
	public String getDescription() {
		return description;
	}

	
}
