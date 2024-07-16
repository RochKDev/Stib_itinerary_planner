package model.repository;

import model.dao.LineDao;
import model.dto.LineDto;
import model.exceptions.RepositoryException;

import java.util.List;

public class LineRepository implements Repository<Integer, LineDto> {
    private final LineDao dao;

    public LineRepository() throws RepositoryException {
        dao = LineDao.getInstance();
    }

    LineRepository(LineDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(LineDto item) throws RepositoryException {

    }

    @Override
    public void remove(Integer key) throws RepositoryException {

    }

    @Override
    public List<LineDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public LineDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return dao.select(key) != null;
    }
}
