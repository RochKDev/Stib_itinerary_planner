package Handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import presenter.Presenter;

public class SelectFrequentHandler implements EventHandler<ActionEvent> {
    private final Presenter presenter;
    public SelectFrequentHandler(Presenter presenter){
        this.presenter = presenter;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        presenter.selectFrequent();
    }
}
