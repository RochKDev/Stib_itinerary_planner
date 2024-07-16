package model.dao;

import model.config.ConfigManager;
import model.dataStructure.Pair;
import model.dto.FrequentTravelDto;
import model.exceptions.RepositoryException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FrequentTravelDaoTest {
    private FrequentTravelDao frequentTravelDao;
    private FrequentTravelDto frequentTravelDtoSchool;
    private FrequentTravelDto frequentTravelDtoWork;
    private List<FrequentTravelDto> frequentTravelDtos;
    public FrequentTravelDaoTest() throws RepositoryException {
        try {
            ConfigManager.getInstance().load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        frequentTravelDao = FrequentTravelDao.getInstance();
        frequentTravelDtoSchool = new FrequentTravelDto("ecole", new Pair<>(1, 8012), new Pair<>(1, 8062));
        frequentTravelDtoWork = new FrequentTravelDto("travail", new Pair<>(1, 8012), new Pair<>(5, 8072));
        frequentTravelDtos = List.of(
                new FrequentTravelDto("ecole", new Pair<>(1, 8012), new Pair<>(1, 8062)),
                new FrequentTravelDto("travail", new Pair<>(1, 8012), new Pair<>(5, 8072))
        );
    }
    @Test
    public void testSelectExist() throws RepositoryException {
        assertNotNull(frequentTravelDao.select(frequentTravelDtoSchool.getKey()));
    }
    @Test
    public void testSelectNotExist() throws RepositoryException {
        assertNull(frequentTravelDao.select("test"));
    }
    @Test
    public void testSelectIncorrectParameter() {
        assertThrows(RepositoryException.class, () -> {
            frequentTravelDao.select(null);
        });
    }
    @Test
    public void selectAll() throws RepositoryException {
        List<FrequentTravelDto> result = frequentTravelDao.selectAll();
        Assert.assertThat(frequentTravelDtos, Matchers.containsInAnyOrder(result.toArray()));
    }


}