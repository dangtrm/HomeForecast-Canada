package userInterfaceModule;

public class SummaryDataState implements State {

    private Table table;

    public SummaryDataState(Table table) {
        this.table = table;
    }

    @Override
    public void switchTable(RawTable rawTable, SummaryTable summaryTable) {
        summaryTable.setVisible(false);
        rawTable.setVisible(true);
        table.setState(table.getRawDataState());
    }
}