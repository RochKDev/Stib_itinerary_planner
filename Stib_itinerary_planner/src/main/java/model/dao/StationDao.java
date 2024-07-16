package model.dao;

import model.dto.StationDto;
import model.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDao implements Dao<Integer, StationDto> {

    private Connection connection;
    private StationDao() throws RepositoryException{
        connection = DBManager.getInstance().getConnection();
    }

    public static StationDao getInstance() throws RepositoryException {
        return StationDaoHolder.getInstance();
    }

    @Override
    public void insert(StationDto item) throws RepositoryException {

    }

    @Override
    public void delete(Integer key) throws RepositoryException {

    }

    @Override
    public void update(StationDto item) throws RepositoryException {

    }

    @Override
    public List<StationDto> selectAll() throws RepositoryException {
        String sql = """
                SELECT * 
                FROM stations
                """;
        List<StationDto> dtos = new ArrayList<>();
        try(Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql)){
            while(res.next()){
                StationDto dto = new StationDto(res.getInt("id"), res.getString("name"));
                dtos.add(dto);
            }
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public StationDto select(Integer key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException("No key given in parameter");
        }
        String sql = """
                SELECT *
                FROM stations
                WHERE id = ?
                """;
        StationDto dto = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new StationDto(rs.getInt("id"), rs.getString("name"));
                count++;
            }
            if (count > 1) {
                throw new RepositoryException("Not unique Record : " + key);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dto;
    }

    private static class StationDaoHolder {
        private static StationDao getInstance() throws RepositoryException {
            return new StationDao();
        }
    }
}
