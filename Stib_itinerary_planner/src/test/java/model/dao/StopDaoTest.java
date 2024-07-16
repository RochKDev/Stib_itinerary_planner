package model.dao;

import model.config.ConfigManager;
import model.dataStructure.Pair;
import model.dto.FullStopDto;
import model.dto.StopDto;
import model.exceptions.RepositoryException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class StopDaoTest {
    private StopDao stopDao;
    private StopDto stopDto;
    private StopDto stopDtoToTestNeighbours;
    private List<StopDto> stopDtos;
    private List<FullStopDto> fullStopDtos;
    private List<FullStopDto> neighbours;

    public StopDaoTest() throws RepositoryException {
        try {
            ConfigManager.getInstance().load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stopDao = StopDao.getInstance();
        stopDto = new StopDto(new Pair<>(1, 8012), 6);
        stopDtoToTestNeighbours = new StopDto(new Pair<>(1, 8042), 9);
        stopDtos = List.of(
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
        fullStopDtos = List.of(
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
    }

    @Test
    public void testSelectExist() throws RepositoryException {
        System.out.println("testSelectExist");
        StopDto result = stopDao.select(stopDto.getKey());
        assertEquals(stopDto, result);
    }

    @Test
    public void testSelectNotExist() throws RepositoryException {
        System.out.println("testSelectNotExist");
        StopDto result = stopDao.select(new Pair<>(0, 9999));
        assertNull(result);
    }

    @Test
    public void testSelectIncorrectParameter() throws RepositoryException {
        System.out.println("testSelectIncorrectParameter");
        Pair<Integer, Integer> incorrect = null;
        assertThrows(RepositoryException.class, () -> {
            stopDao.select(incorrect);
        });
    }

    @Test
    public void testSelectAllExist() throws RepositoryException {
        System.out.println("testSelectAllExist");
        List<StopDto> result = stopDao.selectAll();
        Assert.assertThat(stopDtos, Matchers.containsInAnyOrder(result.toArray()));
    }

    @Test
    public void testSelectAllFull() throws RepositoryException {
        System.out.println("testSelectAllFull");
        List<FullStopDto> result = stopDao.selectAllFull();
        Assert.assertThat(fullStopDtos, Matchers.containsInAnyOrder(result.toArray()));
    }

    @Test
    public void testSelectNeighboursFull() throws RepositoryException {
        System.out.println("testSelectNeighboursFull");
        List<FullStopDto> result = stopDao.selectNeighboursFull(stopDtoToTestNeighbours.getKey());
        Assert.assertThat(neighbours, Matchers.containsInAnyOrder(result.toArray()));
    }

}