package presenter;

import model.dto.FrequentTravelDto;
import model.graph.StibNetGraph;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Presenter implements PropertyChangeListener {
    private final StibNetGraph graph;
    private final ViewController viewController;

    public Presenter(StibNetGraph graph, ViewController viewController) {
        this.graph = graph;
        this.viewController = viewController;
        viewController.populateComboBoxes(graph.getGraph().getAdj().keySet());
        viewController.handleSearchButtonAction(this);
        viewController.handleAddFrequentButtonAction(this);
        initialiseFrequentTravels();
    }

    public void search() {
        this.viewController.stopsTableView.getItems().clear();
        this.graph.calculateShortestPathFromSource(this.viewController.originSearchableBox.getValue());
    }

    public void addFrequent() {
        if (this.viewController.frequentTravelTextField.getText().isEmpty()) {
            this.viewController.displayAlert("Empty field", "You cannot add a frequent travel with an empty name!");
            return;
        }
        try {

            if (this.viewController.originSearchableBox.getValue() == null || this.viewController.destinationSearchableBox.getValue() == null) {
                this.viewController.displayAlert("Empty selection", "You cannot add a frequent travel with an empty origin or destination!");
                return;
            }

            FrequentTravelDto frequentTravelDto = new FrequentTravelDto(
                    this.viewController.frequentTravelTextField.getText(),
                    this.viewController.originSearchableBox.getValue().getStopDto().getKey(),
                    this.viewController.destinationSearchableBox.getValue().getStopDto().getKey()
            );
            this.graph.addFrequentTravel(frequentTravelDto);
        } catch (Exception e) {
            this.viewController.displayAlert("Error while trying to add the frequent travel!", e.getMessage());
        }
    }

    public void initialiseFrequentTravels() {
        try {
            this.viewController.FrequentListView.getItems().addAll(this.graph.getAll());

        } catch (Exception e) {
            this.viewController.displayAlert("Error while trying to initialise the frequent travels!", e.getMessage());
        }
    }

    public void removeFrequent() {
        try {
            this.graph.removeFrequent(this.viewController.FrequentListView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            this.viewController.displayAlert("Error while trying to remove the frequent travel!", e.getMessage());
        }
    }
    public void selectFrequent() {
        FrequentTravelDto selectedItem = this.viewController.FrequentListView.getSelectionModel().getSelectedItem();
        setFrequentTravelInComboBoxes(selectedItem);
    }
    private void setFrequentTravelInComboBoxes(FrequentTravelDto frequentTravelDto) {
        for (var node : graph.getGraph().getAdj().entrySet()) {
           // node.isKey(frequentTravelDto.getKeySource())
            if (node.getKey().getStopDto().getKey().equals(frequentTravelDto.getKeySource())) {
                this.viewController.originSearchableBox.setValue(node.getKey());
            }
            if (node.getKey().getStopDto().getKey().equals(frequentTravelDto.getKeyDestination())) {
                this.viewController.destinationSearchableBox.setValue(node.getKey());
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("shortestPathCompleted")) {
            this.viewController.stopsTableView.getItems().add(this.viewController.originSearchableBox.getValue().getStopDto());
            for (int i = 1; i < this.viewController.destinationSearchableBox.getValue().getShortestPath().size(); i++) {
                this.viewController.stopsTableView.getItems().add(this.viewController.destinationSearchableBox.getValue().getShortestPath().get(i).getStopDto());
            }
            this.viewController.stopsTableView.getItems().add(this.viewController.destinationSearchableBox.getValue().getStopDto());
            this.graph.resetGraph();
        }

        if(evt.getPropertyName().equals("frequentRemoved")){
            this.viewController.FrequentListView.getItems().remove(evt.getNewValue());
        }

        if(evt.getPropertyName().equals("frequentAdded")){
            if (!this.viewController.FrequentListView.getItems().contains(evt.getNewValue())) {
                this.viewController.FrequentListView.getItems().add((FrequentTravelDto) evt.getNewValue());
            }
        }

    }
}
