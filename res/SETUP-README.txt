Steps to Run the Program:
    1. Open a terminal and navigate to the directory where stock-simulation.jar is located.
    2. Execute the following command to run the program:
        java -jar stock-simulation.jar
       After running the JAR file, you will be prompted to enter commands.
       Use the following commands to interact with the program:

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
    3. To create a new portfolio, enter the following command:
        5
       followed by the desired name of the portfolio.
    4. To add stocks to the first portfolio, enter the following command:
        6
       followed by the name of the first portfolio, the ticker symbol of the company the user
       desires to purchase stock from (AAPL), and the desired number of stocks from the company to be
       purchased, and the year, month, and day the stock was to be purchased (2024 05 02).
    6. Complete the command two more times so that 3 stocks are added to the first portfolio.
        (Ex: [(KO), (2023 02 04)] [(GOOG), (2022 02 04)])
    7. To get the value of the portfolio on 2 specific dates enter the following command:
        10
       followed by the name of the desired portfolio and the desired date. The date should be given
       with the following format: DATE MM DD

Stocks supported by program:
    The stocks supported by the program are all the stocks supported by the alpha vantage api. This
    is including but not limited to: AMZN (Amazon), GOOG (Google), AAPL (Apple), and KO (Coca-cola).

Dates supported by the program:
    The dates supported by this program are the dates when the stock market was open for the past
    20 years. Some examples include: 2024-05-20, 2019-02-09, 2021-12-13, and 2023-11-02.