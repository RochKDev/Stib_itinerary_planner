package model.repository;

import model.dao.StopDao;
import model.dataStructure.Pair;
import model.dto.FullStopDto;
import model.dto.StopDto;
import model.exceptions.RepositoryException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class StopRepositoryTest {
    private StopDto parc;
    private StopDto schuman;
    private StopDto stopDtoToTestNeighbours;
    private final Pair<Integer, Integer> key;
    private final List<StopDto> all;
    private final List<FullStopDto> allFull;
    private final List<FullStopDto> neighbours;
    @Mock
    private StopDao mock;

    public StopRepositoryTest() {
        System.out.println("StopRepositoryTest Constructor");
        //Test data
        key = new Pair<>(1, 8032);
        parc = new StopDto(key, 8);
        schuman = new StopDto(new Pair<>(1, 8062), 11);
        all = List.of(
                new StopDto(new Pair<>(1, 8012), 6),
                new StopDto(new Pair<>(1, 8022), 7),
                new StopDto(new Pair<>(1, 8032), 8),
                new StopDto(new Pair<>(1, 8042), 9),
                new StopDto(new Pair<>(1, 8052), 10),
                new StopDto(new Pair<>(1, 8062), 11),
                new StopDto(new Pair<>(1, 8072), 12),
                new StopDto(new Pair<>(1, 8082), 13),
                new StopDto(new Pair<>(1, 8092), 14),
                new StopDto(new Pair<>(1, 8102), 15),
                new StopDto(new Pair<>(2, 8042), 13),
                new StopDto(new Pair<>(5, 8012), 15),
                new StopDto(new Pair<>(5, 8022), 16),
                new StopDto(new Pair<>(5, 8032), 17),
                new StopDto(new Pair<>(5, 8042), 18),
                new StopDto(new Pair<>(5, 8052), 19),
                new StopDto(new Pair<>(5, 8062), 20),
                new StopDto(new Pair<>(5, 8072), 21),
                new StopDto(new Pair<>(6, 8042), 20)
        );
        allFull = List.of(
                new FullStopDto(new Pair<>(1, 8012), 6, "DE BROUCKERE"),
                new FullStopDto(new Pair<>(1, 8022), 7, "GARE CENTRALE"),
                new FullStopDto(new Pair<>(1, 8032), 8, "PARC"),
                new FullStopDto(new Pair<>(1, 8042), 9, "ARTS-LOI"),
                new FullStopDto(new Pair<>(1, 8052), 10, "MAELBEEK"),
                new FullStopDto(new Pair<>(1, 8062), 11, "SCHUMAN"),
                new FullStopDto(new Pair<>(1, 8072), 12, "MERODE"),
                new FullStopDto(new Pair<>(1, 8082), 13, "MONTGOMERY"),
                new FullStopDto(new Pair<>(1, 8092), 14, "JOSEPH.-CHARLOTTE"),
                new FullStopDto(new Pair<>(1, 8102), 15, "GRIBAUMONT"),
                new FullStopDto(new Pair<>(2, 8042), 13, "ARTS-LOI"),
                new FullStopDto(new Pair<>(5, 8012), 15, "DE BROUCKERE"),
                new FullStopDto(new Pair<>(5, 8022), 16, "GARE CENTRALE"),
                new FullStopDto(new Pair<>(5, 8032), 17, "PARC"),
                new FullStopDto(new Pair<>(5, 8042), 18, "ARTS-LOI"),
                new FullStopDto(new Pair<>(5, 8052), 19, "MAELBEEK"),
                new FullStopDto(new Pair<>(5, 8062), 20, "SCHUMAN"),
                new FullStopDto(new Pair<>(5, 8072), 21, "MERODE"),
                new FullStopDto(new Pair<>(6, 8042), 20, "ARTS-LOI")
        );
        neighbours = List.of(
                new FullStopDto(new Pair<>(1, 8032), 8, "PARC"),
                new FullStopDto(new Pair<>(1, 8052), 10, "MAELBEEK"),
                new FullStopDto(new Pair<>(6, 8042), 20, "ARTS-LOI"),
                new FullStopDto(new Pair<>(2, 8042), 13, "ARTS-LOI"),
                new FullStopDto(new Pair<>(5, 8042), 18, "ARTS-LOI")
        );
        stopDtoToTestNeighbours = new StopDto(new Pair<>(1, 8042), 9);
    }

    @BeforeEach
    void init() throws RepositoryException {
        System.out.println("==== BEFORE EACH =====");
        //Mock behaviour
        Mockito.lenient().when(mock.select(key)).thenReturn(parc);
        Mockito.lenient().when(mock.select(new Pair<>(1, 1))).thenReturn(null);
        Mockito.lenient().when(mock.select(null)).thenThrow(RepositoryException.class);
        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.selectAllFull()).thenReturn(allFull);
        Mockito.lenient().when(mock.selectNeighboursFull(stopDtoToTestNeighbours.getKey())).thenReturn(neighbours);
    }

    @Test
    public void testGetExist() throws Exception {
        System.out.println("testGetExist");
        //Arrange
        StopRepository repo = new StopRepository(mock);
        //Action
        StopDto result = repo.get(key);
        //Assert
        assertEquals(parc, result);
        Mockito.verify(mock, Mockito.times(1)).select(key);
    }

    @Test
    public void testGetNotExist() throws Exception {
        System.out.println("testGetNotExist");
        //Arrange
        StopRepository repo = new StopRepository(mock);
        //Action
        StopDto result = repo.get(new Pair<>(1, 1));
        //Assert
        assertNull(result);
        Mockito.verify(mock, Mockito.times(1)).select(new Pair<>(1, 1));
    }

    @Test
    public void testGetIncorrectParameter() throws Exception {
        System.out.println("testGetIncorrectParameter");
        //Arrange
        StopRepository repo = new StopRepository(mock);
        Pair<Integer, Integer> incorrect = null;
        //Assert
        assertThrows(RepositoryException.class, () -> {
            //Action
            repo.get(incorrect);
        });
        Mockito.verify(mock, Mockito.times(1)).select(incorrect);
    }
    @Test
    public void testGetAll() throws Exception {
        System.out.println("testGetAll");
        //Arrange
        StopRepository repo = new StopRepository(mock);
        //Action
        List<StopDto> result = repo.getAll();
        //Assert
        assertEquals(all, result);
        Assert.assertThat(all, Matchers.containsInAnyOrder(result.toArray()));
        Mockito.verify(mock, Mockito.times(1)).selectAll();
    }
    @Test
    public void testSelectAllFull() throws Exception {
        System.out.println("testSelectAllFull");
        //Arrange
        StopRepository repo = new StopRepository(mock);
        //Action
        List<FullStopDto> result = repo.getAllFullStop();
        //Assert
        assertEquals(allFull, result);
        Assert.assertThat(allFull, Matchers.containsInAnyOrder(result.toArray()));
        Mockito.verify(mock, Mockito.times(1)).selectAllFull();
    }
    @Test
    public void testSelectNeighboursFull() throws Exception {
        System.out.println("testSelectNeighboursFull");
        //Arrange
        StopRepository repo = new StopRepository(mock);
        //Action
        List<FullStopDto> result = repo.getAllNeighbours(stopDtoToTestNeighbours.getKey());
        //Assert
        Assert.assertThat(neighbours, Matchers.containsInAnyOrder(result.toArray()));
        Mockito.verify(mock, Mockito.times(1)).selectNeighboursFull(stopDtoToTestNeighbours.getKey());
    }
}