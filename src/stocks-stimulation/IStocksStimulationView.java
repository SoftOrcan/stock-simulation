interface IStocksStimulationView {
  public static void main(String[] args) {
    IStocksStimulationModel model = new BetterSparseSpreadSheet();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    IStocksStimulationController controller = new SpreadSheetController(model, rd, ap);
    controller.initiate();
  }
}