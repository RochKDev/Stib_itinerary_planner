package model.dao;

import model.config.ConfigManager;
import model.dto.StationDto;
import model.exceptions.RepositoryException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StationDaoTest {

    private StationDao stationDao;
    private StationDto stationDto;
    private List<StationDto> stationDtos;
    public StationDaoTest() throws RepositoryException {
        try {
            ConfigManager.getInstance().load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stationDao = StationDao.getInstance();
        stationDto = new StationDto(8032, "PARC");
        stationDtos = List.of(
                new StationDto(8012, "DE BROUCKERE"),
                new StationDto(8022, "GARE CENTRALE"),
                new StationDto(8032, "PARC"),
                new StationDto(8042, "ARTS-LOI"),
                new StationDto(8052, "MAELBEEK"),
                new StationDto(8062, "SCHUMAN"),
                new StationDto(8072, "MERODE"),
                new StationDto(8082, "MONGOMERY"),
                new StationDto(8092, "JOSEPH.-CHARLOTTE"),
                new StationDto(8102, "GRIBAUMONT")
        );
    }

    @Test
    public void testSelectExist() throws RepositoryException {
        StationDto result = stationDao.select(stationDto.getKey());
        assertNotNull(result);
    }
    @Test
    public void testSelectNotExist() throws RepositoryException {
        StationDto result = stationDao.select(9999);
        assertNull(result);
    }
    @Test
    public void testSelectIncorrectParameter() {
        assertThrows(RepositoryException.class, () -> {
            stationDao.select(null);
        });
    }
    @Test
    public void selectAll() throws RepositoryException {
        List<StationDto> result = stationDao.selectAll();
        Assert.assertThat(stationDtos, Matchers.containsInAnyOrder(result.toArray()));
    }

}