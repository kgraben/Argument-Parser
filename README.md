<h1>Argument Parser</h1>

<h2>Contributors</h2>
  <ul>
    <li>Christopher Burdette (<a href="http://github.com/cbgithub">cbgithub</a>)</li>
    <li>Kurtis Graben (<a href="http://github.com/kurtdawg24">kurtdawg24</a>)</li>
    <li>Khoi Phan (<a href="http://github.com/kphanjsu">kphanjsu</a>)</li>
    <li>Hui Wang (<a href="http://github.com/wanghuida0">wanghuida0</a>)</li>
  </ul>
<h2>Requirements and tools</h2>
  <ul>
    <li><a href="http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html">Java SDK</a></li>
    <li>JUnit</li>
    <li><a href="http://robotframework.org/">RobotFramework</a></li>
    <li><a href="https://gradle.org/">Gradle</a></li>
    <li>How to set project up in github available <a href="http://tinyurl.com/githubsetup">here </a>.</li>
  </ul>
<h2>What is Argument Parser? </h2>
  <ul>
    <li>Argument parser is a program made to manage arguments run from the command line.</li>
  </ul>
<h2>Features: </h2>
  <ul>
    <li><b>User friendly</b>
      <ul>
        <li>Adding an argument only requires two methods.<br>
            For instance, if a user wanted to add a positional argument they would type:<br>
            ![alt text](http://i.imgur.com/UYV09MB.png "How to add a positional argument")
            <br> If a user added a named argument, they would type:<br>
            ![alt text](http://i.imgur.com/dptfiJ6.png "How to add a named argument")
        </li>
      </ul>
    </li>
    <li><b>Help command</b>
      <ul>
        <li>The help provides all known information about the program. It will provide the argument name, description, type, default value, program name and program description. </li>
        <li>Example: Inside of the VolumeCalculator demo is an argument parser with 6 different arguments. When the demo is run, it will check to see if any of the arguments contain '-h' or '--help'. If it does, then the help will override any other functionality in the program.
        <br>![alt text](http://i.imgur.com/wZsN7M4.png "How to call help message")
        </li>
        <li>Resulting message after -h is called:
        <br>![alt text](http://i.imgur.com/VUQXxRD.png "Example of help message")
        </li>
      </ul>
    </li>
    <li><b>Named Arguments</b>
      <ul>
        <li>Named Arguments in the program can be called by their short name when adding the argument. Also, a named argument is required to have a default value.</li>
          <br>![alt text](http://i.imgur.com/TlQEM6V.png "Adding a named argument")
      </ul>
    </li>
    <li><b>Positional Arguments</b>
      <ul>
          <li>Positional Arguments are required in the program and doesn’t have a lot of functionality.  The order the user adds the positional arguments to program is the order the arguments will be in. </li>
          <br>![alt text](http://i.imgur.com/jpOVu9W.png "Adding a positional argument")
      </ul>
    </li>
    <li><b>Save/Load XML</b>
        <ul>
        <li>The Save XML file stores all of the argument information, including
          <ul>
            <li>long-form names</li>
            <li>short-form names</li> 
            <li>description</li>
            <li>datatypes</li>
            <li>default values</li> 
            <li>whether an argument is a positional argument or named argument</li>
          </ul>
        </li>
        <li>The Load XML file will load all of the argument information from the XML file</li>
        </ul>
    </li>
    <li><b>Custom Exceptions</b>
      <ul>
        <li>There are 5 different exceptions
          <ol>
            <li>HelpMessageException
              <ul>
                <li>Thrown when a user enters “-h” or “--help”</li>
              </ul>
            </li>
            <li>IncorrectDataTypeException
              <ul>
                <li>Thrown when a user enters a different type from what is expected</li>
              </ul>
            </li>
            <li>MissingArgumentException
              <ul>
                <li>Thrown when a user enters less values than expected</li>
              </ul>
            </li>
            <li>FileErrorException
              <ul>
                <li>Thrown when a file cannot be found, loaded or read</li>
              </ul>
            </li>
            <li>UnknownArgumentException
              <ul>
                <li>Thrown when a user enters more values than expected</li>
              </ul>
            </li>
          </ol>
        </li>
      </ul>
    </li>
    <li><b>Javadoc API</b>
      <ul>
        <li>This library comes with a documented javadoc API for the entire library.
        <br>![alt text](http://i.imgur.com/7AD9L8e.png "JavaDOC screenshot")
    </li>
  </ul>
<h2>

