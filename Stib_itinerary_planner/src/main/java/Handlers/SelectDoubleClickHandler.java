package Handlers;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import presenter.Presenter;

public class SelectDoubleClickHandler implements EventHandler<MouseEvent> {
    private final Presenter presenter;
    public SelectDoubleClickHandler(Presenter presenter){
        this.presenter = presenter;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            presenter.selectFrequent();        }
    }
}
