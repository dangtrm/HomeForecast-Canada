package userInterfaceModule;

public class RawDataState implements State{
    private Table table;

    public RawDataState(Table table) {
        this.table = table;
    }

    @Override
    public void switchTable(RawTable rawTable, SummaryTable summaryTable) {
        rawTable.setVisible(false);
        summaryTable.setVisible(true);
        table.setState(table.getSummaryDataState());
    }
}