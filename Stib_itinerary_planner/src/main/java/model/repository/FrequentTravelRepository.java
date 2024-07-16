package model.repository;

import model.dao.FrequentTravelDao;
import model.dto.FrequentTravelDto;
import model.exceptions.RepositoryException;

import java.util.List;

public class FrequentTravelRepository implements Repository<String, FrequentTravelDto>{
    private final FrequentTravelDao dao;
    public FrequentTravelRepository() throws RepositoryException {
        dao = FrequentTravelDao.getInstance();
    }

    FrequentTravelRepository(FrequentTravelDao dao){
        this.dao = dao;
    }

    @Override
    public void add(FrequentTravelDto item) throws RepositoryException {
        String key = item.getKey();
        if(contains(key)){
           dao.update(item);
        }else {
            dao.insert(item);
        }
    }

    @Override
    public void remove(String key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<FrequentTravelDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FrequentTravelDto get(String key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(String key) throws RepositoryException {
        FrequentTravelDto dto = dao.select(key);
        return dto != null;
    }
}
