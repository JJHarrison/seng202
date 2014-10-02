Fitr 
Version 1.0 beta
24/09/2014
Readme


Description of Fitr 

	Fitr is a health tracking and analysis software, that allows a user to import a .csv file containing activity 	data about their physical activities. It provides useful feedback to the user in an array of formats such as 	graphs, maps and numerical figures.


To start Fitr (from .jar)

	Java 8 must be installed and set to default 

	LINUX
	- Open terminal 
	- Navigate to directory containing seng202_2014_team5_fitr.jar
	- Use command java -jar seng202_2014_team5_fitr.jar
	
	OSX
	- Click on seng202_2014_team5_fitr.jar

	WINDOWS
	- Click on seng202_2014_team5_fitr.jar
	
	
To start Fitr (from eclipse project)
    
    - Import the project zip file into eclipse
    - Use the package explorer to locate view/user/UserLoginManager.java
    - Open this file and click run


To use Fitr

	- Start Fitr (see above)
	- Choose location to store Fitr data using the file chooser
	- Create a new user using the Create Profile button 
	- Select a user and log in 

	- To import a .csv file
		- File -> Import  
		- Choose the .csv file you want to view
		- Data will be automatically saved to the location you selected earlier 

	- To export to the database
		- File -> Export 
		- The data is then uploaded to the database

	- To view activity events
		- Go to analysis 
		- Use the calendar to navigate to the event you want to view
