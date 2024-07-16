
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.config.ConfigManager;
import model.dao.StationDao;
import model.dataStructure.Pair;
import model.dto.FullStopDto;
import model.exceptions.RepositoryException;
import model.graph.Dijkstra;
import model.graph.Edge;
import model.graph.StibNetGraph;
import model.graph.StopNode;
import model.repository.StopRepository;
import presenter.Presenter;
import presenter.ViewController;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Main  extends Application {
    public void start(Stage stage){
        ConfigManager manager = ConfigManager.getInstance();
        try {
            manager.load();
            FXMLLoader FXMLloader = new FXMLLoader(Main.class.getResource("/fxml/stib_main_view.fxml"));
            VBox loader = FXMLloader.load();
            StibNetGraph graph = new StibNetGraph();
            ViewController controller = FXMLloader.getController();
            Presenter presenter = new Presenter(graph, controller);
            graph.addPropertyChangeListener(presenter);
            controller.initialize(presenter);
            Scene scene = new Scene(loader);
            stage.setTitle("StibRide");
            stage.setScene(scene);
            stage.show();
        } catch (IOException | RepositoryException e) {
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
