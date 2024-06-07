Steps to Run the Program:
    1. Open a terminal and navigate to the directory where stock-simulation.jar is located.
    2. Execute the following command to run the program:
        java -jar stock-simulation.jar
       After running the JAR file, you will be prompted to enter commands.
       Use the following commands to interact with the program:

        stock-examine-period [symbol] [start-date] [end-date]
        stock-examine-average [symbol] [date] [number of days]
        stock-examine-crossovers [symbol] [start-date] [end-date] [number of days]
        portfolio-create [name]
        portfolio-add [name] [symbol] [amount]
        portfolio-remove [name] [symbol]
        portfolio-value [name] [date]
        q: quit the program
    3. To create a new portfolio, enter the following command:
        portfolio-create
       followed by the desired name of the portfolio.
    4. To create the second portfolio, enter the same command followed by the desired second
        portfolio name.
    5. To add stocks to the first portfolio, enter the following command:
        portfolio-add
       followed by the name of the first portfolio, the ticker symbol of the company the user
       desires to purchase stock from, and the desired number of stocks from the company to be
       purchased.
    6. Complete the command two more times so that 3 stocks are added to the first portfolio.
    7. Complete the command two times but using the name of the second portfolio in order to add
       The necessary stocks to the second portfolio.
    8. To get the value of the portfolios on a specific date enter the following command:
        portfolio-value
       followed by the name of the desired portfolio and the desired date. The date should be given
       with the following format: DATE-MM-DD

Stocks supported by program:
    The stocks supported by the program are all the stocks supported by the alpha vantage api. This
    is including but not limited to: AMZN (Amazon), GOOG (Google), AAPL (Apple), and KO (Coca-cola).

Dates supported by the program:
    The dates supported by this program are the dates when the stock market was open for the past
    20 years. Some examples include: 2024-05-20, 2019-02-09, 2021-12-13, and 2023-11-02.
