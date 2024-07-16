package model.dao;

import model.config.ConfigManager;
import model.dto.LineDto;
import model.exceptions.RepositoryException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LineDaoTest {
    private LineDao lineDao;
    private int lineId = 1;
    private List<LineDto> linesIds;

    public LineDaoTest() throws RepositoryException {
        try {
            ConfigManager.getInstance().load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lineDao = LineDao.getInstance();
        linesIds = List.of(
                new LineDto(1),
                new LineDto(2),
                new LineDto(5),
                new LineDto(6)
        );
    }

    @Test
    public void testSelectExist() throws RepositoryException {
        assertNotNull(lineDao.select(lineId));
    }

    @Test
    public void testSelectNotExist() throws RepositoryException {
        assertNull(lineDao.select(9999));
    }

    @Test
    public void testSelectIncorrectParameter() {
        assertThrows(RepositoryException.class, () -> {
            lineDao.select(null);
        });
    }

    @Test
    public void selectAll() throws RepositoryException {
        List<LineDto> result = lineDao.selectAll();
        Assert.assertThat(linesIds, Matchers.containsInAnyOrder(result.toArray()));
    }

}