package Handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import presenter.Presenter;

public class SearchButtonHandler implements EventHandler<ActionEvent> {
    private final Presenter presenter;

    public SearchButtonHandler(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println("Search button clicked");
        presenter.search();
    }
}
