# leadsdatabase

## Requirements
The marketing department is responsible for profiling customers for e-mail targeting based on their monthly
spending habits.
They have decided to classify customers based on the following rules:

| Classification     | Rule |
| -------------------|:--------------------------------------------------------------------------------------:|    
| Afternoon Person   | Makes over 50% of their transactions in the month after midday (count of transactions) |
| Big Spender        | Spends over 80% of their deposits every month ($ value of deposits)                    |
| Big Ticket Spender | Makes one or more withdrawals over $1000 in the month                                  |
| Fast Spender       | Spends over 75% of any deposit within 7 days of making it                              |  
| Morning Person     | Makes over 50% of their transactions in the month before midday (count of transactions)|
| Potential Saver    | Spends less than 25% of their deposits every month ($ value of deposits)               |


* Periods are delimited by the first day of each month (e.g. July 2016 is defined as 1st July 2016 to 31st
July 2016 inclusive)
* If a person is identified as both a _Big Spender_ and a _Fast Spender_ then they should be classified as a
_Potential Loan customer_ instead.


The marketing department wants a user interface where they can enter the month (e.g. July 2016) and
CustomerId and be presented with:
* The _Classification_
* The list of transactions processed, with the _Current Balance_ as of today

## Building the project
After you've checked out the project, please change to the directory root where it is checked out on your command prompt or shell.

Please build the project using 

```
mvn clean package
```

## Data
The data file is available here - 
```
./feedprocessor/src/main/resources/data.txt
```


## Running the application
Assuming you're still in the root directory of the project, please run the following command -

```
java -jar services/target/services-1.0-SNAPSHOT.jar
```

## Accessing the application
Goto the following URL after successfully running the application - 

http://localhost:8080/index.html

Please feel free to test for customer ids - 3, 5, 23, 11, 19, 4.
