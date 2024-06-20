Steps to Run the Program:
    1. Open a terminal and navigate to the directory where stock-simulation.jar is located
       (Note: the jar file should be located in the res folder of the project).
    2. Execute the following command to run the program using graphical user interface
        (Note: navigate to step 8 to see how to run the textual interface instead):
        java -jar stock-simulation.jar
       After running the JAR file, you will be prompted to enter commands.
       Use the following commands to interact with the program:

        1) Create a Portfolio
        2) Buy a Stock
        3) Sell a Stock
        4) Value of Portfolio
        5) Composition of Portfolio
        6) Quit


        To perform the above functionality, click on their corresponding buttons. After, another
        window with further instructions will open to complete the command.
    3. To create a new portfolio, click the Create a Portfolio button, and then enter the desired
        name. After, click menu to go back to the menu, or quit to exit the program.
    4. To add stocks to a portfolio, click the Buy a Stock button. Then, enter a valid stock symbol
        (Note: the textboxes will be green if the inputs are valid, and red if not). After, click
        menu to go back to the menu, or quit to exit the program.
    6. To get the value of a portfolio, click the Value of Portfolio button. Then use the
        drop-down menu to enter the desired date. (Note: the box will be green if the date is valid,
         and red if not). After, click menu to go back to the menu, or quit to exit the program.
    7. To get the value of a portfolio, click the Composition of Portfolio button. Then use the
       drop-down menu to enter the desired date. (Note: the box will be green if the date is valid,
       and red if not). After, click menu to go back to the menu, or quit to exit the program.

    8. To run the program using the textual interface instead, after navigating to the res folder,
    run the following command: java -jar stock-simulation.jar -text
    After running this command, the following command options should appear:
            1) stock-change
            2) stock-average
            3) stock-crossovers
            4) stock-performance
            5) portfolio-create
            6) portfolio-add
            7) portfolio-remove
            8) portfolio-composition
            9) portfolio-distribution
            10) portfolio-value
            11) portfolio-rebalance
            12) portfolio-performance
            q: quit the program

            Enter numerical option (e.g. Enter '1' for stock-change):
        1. To create a new portfolio, enter the following command:
            5
           followed by the desired name of the portfolio.
        2. To add stocks to a portfolio, enter the following command:
            6
           followed by the name of the desired portfolio, the ticker symbol of the company the user
           desires to purchase stock from (AAPL), and the desired number of stocks from the company to be
           purchased, and the year, month, and day the stock was to be purchased (2024 05 02).
        3. To remove stocks from the portfolio, enter the following command:
            7
           followed by the name of the desired portfolio, the ticker symbol of the company the user
           desires to sell from (AAPL), and the desired number of stocks from the company to be
           sold, and the year, month, and day the stock was to be sold (2024 05 03).
        4. To get the value of the portfolio on 2 specific dates enter the following command:
            10
           followed by the name of the desired portfolio and the desired date. The date should be
           given with the following format: DATE MM DD

Stocks supported by program:
    The stocks supported by the program are all the stocks supported by the alpha vantage api. This
    is including but not limited to: AMZN (Amazon), GOOG (Google), AAPL (Apple), and KO (Coca-cola).

Dates supported by the program:
    The dates supported by this program are the dates when the stock market was open for the past
    20 years. Some examples include: 2024-05-20, 2019-02-09, 2021-12-13, and 2023-11-02.