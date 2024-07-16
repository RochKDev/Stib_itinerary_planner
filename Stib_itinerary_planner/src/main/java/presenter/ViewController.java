package presenter;

import Handlers.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.FrequentTravelDto;
import model.dto.FullStopDto;
import model.graph.StopNode;
import org.controlsfx.control.SearchableComboBox;

import java.util.Set;

public class ViewController {
    @FXML
    TableView<FullStopDto> stopsTableView;
    @FXML
    TableColumn<FullStopDto, String> stationsTableColumn;
    @FXML
    TableColumn<FullStopDto, String> linesTableColumn;
    @FXML
    Label originLabel;
    @FXML
    Label destinationLabel;
    @FXML
    SearchableComboBox<StopNode> originSearchableBox;
    @FXML
    SearchableComboBox<StopNode> destinationSearchableBox;
    @FXML
    Button searchButton;
    @FXML
    Button addFrequentButton;
    @FXML
    TextField frequentTravelTextField;
    @FXML
    ListView<FrequentTravelDto> FrequentListView;

    @FXML
    public void initialize(Presenter presenter) {
        stationsTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        linesTableColumn.setCellValueFactory(cellData -> {
            int line = cellData.getValue().getKey().getFirst();
            return new ReadOnlyObjectWrapper<>(line == 0 ? "transit" : Integer.toString(line));
        });

        initialiseListView(presenter);
    }

    public void populateComboBoxes(Set<StopNode> nodes) {
        for (var node : nodes) {
            if (node.getStopDto().getKey().getFirst() == 0) {
                continue;
            }
            originSearchableBox.getItems().add(node);
            destinationSearchableBox.getItems().add(node);
        }
    }

    public void handleSearchButtonAction(Presenter presenter) {
        SearchButtonHandler searchButtonHandler = new SearchButtonHandler(presenter);
        searchButton.setOnAction(searchButtonHandler);
    }

    public void handleAddFrequentButtonAction(Presenter presenter) {
        AddFrequentButtonHandler addFrequentButtonHandler = new AddFrequentButtonHandler(presenter);
        addFrequentButton.setOnAction(addFrequentButtonHandler);
    }


    public void displayAlert(String messageTitle, String messageContent){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(messageTitle);
        alert.setHeaderText(null);
        alert.setContentText(messageContent);

        alert.showAndWait();
    }

    private void initialiseListView(Presenter presenter){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemDelete = new MenuItem("Delete");
        RemoveFrequentHandler removeFrequentHandler = new RemoveFrequentHandler(presenter);
        menuItemDelete.setOnAction(removeFrequentHandler);

        MenuItem menuItemSelect = new MenuItem("Select");
        SelectFrequentHandler selectFrequentHandler = new SelectFrequentHandler(presenter);
        menuItemSelect.setOnAction(selectFrequentHandler);

        contextMenu.getItems().addAll(menuItemDelete, menuItemSelect);

        FrequentListView.setCellFactory(lv -> {
            ListCell<FrequentTravelDto> cell = new ListCell<>() {
                @Override
                protected void updateItem(FrequentTravelDto item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            };
            cell.setContextMenu(contextMenu);
            cell.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! cell.isEmpty())) {
                    SelectDoubleClickHandler selectDoubleClickHandler = new SelectDoubleClickHandler(presenter);
                    cell.setOnMouseClicked(selectDoubleClickHandler);
                }
            });
            return cell;
        });
    }
}
