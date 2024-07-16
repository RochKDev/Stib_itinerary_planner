package model.repository;


import model.dao.StopDao;
import model.dataStructure.Pair;
import model.dto.FullStopDto;
import model.dto.StopDto;
import model.exceptions.RepositoryException;

import java.util.List;

public class StopRepository implements Repository<Pair<Integer, Integer>, StopDto> {
    private final StopDao dao;

    public StopRepository() throws RepositoryException {
        dao = StopDao.getInstance();
    }

    StopRepository(StopDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(StopDto item) throws RepositoryException {

    }

    @Override
    public void remove(Pair<Integer, Integer> key) throws RepositoryException {

    }

    @Override
    public List<StopDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopDto get(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.select(key) != null;
    }

    public List<FullStopDto> getAllFullStop() throws RepositoryException {
        return dao.selectAllFull();
    }
    public List<FullStopDto> getAllNeighbours(Pair<Integer, Integer> key) throws RepositoryException {
        return dao.selectNeighboursFull(key);
    }
}
