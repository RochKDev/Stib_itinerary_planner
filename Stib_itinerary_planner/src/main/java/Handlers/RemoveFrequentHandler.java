package Handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import presenter.Presenter;

public class RemoveFrequentHandler implements EventHandler<ActionEvent> {
    private final Presenter presenter;
    public RemoveFrequentHandler(Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println("Remove frequent button clicked");
        presenter.removeFrequent();
    }
}
