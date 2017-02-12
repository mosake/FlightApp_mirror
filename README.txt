FLIGHT MANAGER APP
    by: Andrew Wang, Rose Gao, Tarun Yellu and Kelly Mo

CONTENTS:
  1) WHERE TO FIND THE PROJECT
  2) FILES TO BE PUSHED TO THE DEVICE
  3) HOW TO RUN THE APP
  4) REGISTERING A NEW ADMIN
  5) REGISTERING A NEW CLIENT
  6) ENHANCEMENTS
 
==================================================

1) WHERE TO FIND THE PROJECT:
  CRC, meetings and README.txt
    (path-to-group_0494)/group_0494/p3/*.txt
  Phase III project App
    (path-to-group_0494)/group_0494/p3/FlightManager
	**note: all application files/directories are located in FlightManager
--------------------------------------------------
2) FILES TO BE PUSHED TO THE DEVICE:
  clients.txt, passwords.txt, flights.txt can all be found in the following directory:
	(path-to-group_0494)/group_0494/p3/starter/sampleTests
  YOUR_PATH/cs.b07.flightmanager/app_text/clients.txt
  YOUR_PATH/cs.b07.flightmanager/app_text/passwords.txt
  YOUR_PATH/cs.b07.flightmanager/app_text/flights/flights.txt
	
  format of text files:
  ____
   clients.txt -> see handout
      LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
      username
      ...
      (newline character)
    **note: Admins are only written with username. Clients are written with full information.
  ____
   flights.txt -> see handout
      Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price,NumSeats
      Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,Destination,Price,NumSeats
      ...
      (newline character)
  ____
    passwords.txt
      username,password
      username,password
      ...
      (newline character)
    **note: username entries must be unique
	
    **NOTE: clients.txt and password.txt have information corresponding on their line number
          THE TWO FILES MUST HAVE THE SAME NUMBER OF LINES.
------------------------------------------------
3) HOW TO RUN THE APP:
    1. open the app
	2. register as a new user or log in using credentials found in passwords.txt
	3. select which process you would like to complete on the welcome screen.
------------------------------------------------
4) TO REGISTER A NEW ADMIN:
  Secret Code (case sensitive): "AnyaRocks!"
------------------------------------------------
5) REGISTERING A NEW CLIENT:
  All fields must be completed.
  Credit card number must be greater than 4 digits
  Expiry date is in the format "####-##-##""
------------------------------------------------
6) ENHANCEMENTS
  Clean, user friendly interface that alerts users if invalid values are input.
  Administrator authorization validation during registration by using a secret code.
  
=================================================
