# Stock Simulation

---
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

### Portfolio:
- [x] Create a portfolio with a specified name. A portfolio is utilized to contain shares of one 
  or more stock.
- [x] Add a stock to a portfolio, representative of purchasing a share of a stock to a portfolio.
- [x] Remove a stock from a portfolio.
- [x] Calculate the value of a portfolio on a specific date. Calculate by identifying the value 
  of each stock in the portfolio on the specified date multiplied by the quantity of the 
  respective stock.
- [x] Quit the application.