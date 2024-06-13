# Stock Simulation

## Features

### Stock:

- [x] Calculate the net profit/loss of a stock from and to a specified date range.
- [x] Calculate the x-day moving average of a stock over a specified time period. The x-day moving
  average is the average of the last x days (starting from the given date). Note: This
  corresponds to the last x days when the stock prices are available.
- [x] Calculate the x-day crossovers of a stock over a specified time period. An x-day crossover
  happens when the closing price for a day is greater than the x-day moving average for that day.
  A crossover signals a "buy" opportunity. A crossover can be defined over any period of time,
  although the 30-day period (i.e. the 30-day crossover) is usually popular.
- [x] Visualize the performance of a stock over a specified time period.

### Portfolio:

- [x] Create a portfolio with a specified name. A portfolio is utilized to contain shares of one
  or more stock.
- [x] Add a stock to a portfolio on a specific date, representative of purchasing a share of a
  stock to a portfolio.
- [x] Sell/Remove a stock from a portfolio on a specific date.
- [x] Determine the composition of a portfolio at a specific date. Note that the composition may
  change over time. The composition must include (a) the list of stocks and (b) the number of
  shares of each stock.
- [x] Determine the value of a portfolio on a specific date (to be exact, the end of that day).
  The value for a portfolio before the date of its first purchase would be 0, since each stock 
  in the portfolio now was purchased at a specific point in time.
- [x] Re-balance a portfolio based on specified weights on a specific date. Re-balancing 
  involves selling some stock of companies that are over-represented to buy stock of companies 
  that are under-represented.
- [x] Visualize the performance of a portfolio over a specified time period.
- [x] Persist a portfolio so that it can be saved and loaded (i.e. save to and retrieve from files)


- [x] Quit the application.