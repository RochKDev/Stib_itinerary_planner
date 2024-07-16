package model.dao;

import model.dataStructure.Pair;
import model.dto.FullStopDto;
import model.dto.StopDto;
import model.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StopDao implements Dao<Pair<Integer, Integer>, StopDto>{
    private Connection connection;
    private StopDao() throws RepositoryException{
        connection = DBManager.getInstance().getConnection();
    }
    public static StopDao getInstance() throws RepositoryException {
        return StopDaoHolder.getInstance();
    }

    @Override
    public void insert(StopDto item) throws RepositoryException {

    }

    @Override
    public void delete(Pair<Integer, Integer> key) throws RepositoryException {

    }

    @Override
    public void update(StopDto item) throws RepositoryException {

    }

    @Override
    public List<StopDto> selectAll() throws RepositoryException {
        String sql = """
                SELECT * 
                FROM stops
                """;
        List<StopDto> dtos = new ArrayList<>();
        try(Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql)){
            while(res.next()){
                StopDto dto = new StopDto(new Pair<>(res.getInt("id_line"), res.getInt("id_station")),
                        res.getInt("id_order"));
                dtos.add(dto);
            }
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    public List<FullStopDto> selectAllFull() throws RepositoryException {
        String sql = """
                SELECT * 
                FROM stops AS s 
                JOIN stations AS st ON s.id_station = st.id
                """;
        List<FullStopDto> dtos = new ArrayList<>();
        try(Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql)){
            while(res.next()){
                FullStopDto dto = new FullStopDto(new Pair<>(res.getInt("id_line"), res.getInt("id_station")),
                        res.getInt("id_order"), res.getString("name"));
                dtos.add(dto);
            }
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }
    public List<FullStopDto> selectNeighboursFull(Pair<Integer, Integer> key) throws RepositoryException {
        String sql = """
               
                SELECT s.*, st.*
                FROM stops AS s
                JOIN stations AS st ON s.id_station = st.id
                LEFT JOIN stops AS s2 ON s2.id_line = ? AND s2.id_station = ?
                WHERE (s.id_line = ? AND (s.id_order = s2.id_order + 1 OR s.id_order = s2.id_order - 1))
                OR (s.id_station = ? AND s.id_line != ?)
                """;
        //OR  (s.id_station = ? AND s.id_line != ?);
        List<FullStopDto> dtos = new ArrayList<>();
        try(PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setInt(1, key.getFirst());
            pstmt.setInt(2, key.getSecond());
            pstmt.setInt(3, key.getFirst());
            pstmt.setInt(4, key.getSecond());
            pstmt.setInt(5, key.getFirst());
            ResultSet res = pstmt.executeQuery();
            while(res.next()){
                FullStopDto dto = new FullStopDto(new Pair<>(res.getInt("id_line"), res.getInt("id_station")),
                        res.getInt("id_order"), res.getString("name"));
                dtos.add(dto);
            }
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }


    @Override
    public StopDto select(Pair<Integer, Integer> key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException("No key given in parameter");
        }
        String sql = """
                SELECT *
                FROM stops
                WHERE id_line = ?
                AND id_station = ?
                """;
        StopDto dto = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key.getFirst());
            pstmt.setInt(2, key.getSecond());
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new StopDto(new Pair<>(rs.getInt("id_line"), rs.getInt("id_station")),
                        rs.getInt("id_order"));
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



    private static class StopDaoHolder {
        private static StopDao getInstance() throws RepositoryException {
            return new StopDao();
        }
    }
}
