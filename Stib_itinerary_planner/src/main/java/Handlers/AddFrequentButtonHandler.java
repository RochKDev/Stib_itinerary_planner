package Handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import presenter.Presenter;

public class AddFrequentButtonHandler implements EventHandler<ActionEvent> {
    private final Presenter presenter;
    public AddFrequentButtonHandler(Presenter presenter){
        this.presenter = presenter;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println("Add frequent button clicked");
        presenter.addFrequent();
    }
}
