package model.repository;

import model.dao.StationDao;
import model.dto.StationDto;
import model.exceptions.RepositoryException;

import java.util.List;

public class StationRepository implements Repository<Integer, StationDto> {
    private final StationDao dao;

    public StationRepository() throws RepositoryException{
        dao = StationDao.getInstance();
    }
    StationRepository(StationDao dao){
        this.dao = dao;
    }

    @Override
    public void add(StationDto item) throws RepositoryException {

    }

    @Override
    public void remove(Integer key) throws RepositoryException {

    }

    @Override
    public List<StationDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StationDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return dao.select(key) != null;
    }
}
