package model.dao;

import model.dto.LineDto;
import model.dto.StationDto;
import model.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineDao implements Dao<Integer, LineDto> {

    private Connection connection;
    private LineDao() throws RepositoryException{
        connection = DBManager.getInstance().getConnection();
    }
    public static LineDao getInstance() throws RepositoryException {
        return LineDaoHolder.getInstance();
    }

    @Override
    public void insert(LineDto item) throws RepositoryException {

    }

    @Override
    public void delete(Integer key) throws RepositoryException {

    }

    @Override
    public void update(LineDto item) throws RepositoryException {

    }

    @Override
    public List<LineDto> selectAll() throws RepositoryException {
        String sql = """
                SELECT * 
                FROM lines
                """;
        List<LineDto> dtos = new ArrayList<>();
        try(Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql)){
            while(res.next()){
                LineDto dto = new LineDto(res.getInt("id"));
                dtos.add(dto);
            }
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public LineDto select(Integer key) throws RepositoryException {
        if(key == null){
            throw new RepositoryException("No key given in parameter");
        }
        String sql = """
                SELECT *
                FROM lines
                WHERE id = ?
                """;
        LineDto dto = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, key);
            ResultSet rs = pstmt.executeQuery();

            int count = 0;
            while (rs.next()) {
                dto = new LineDto(rs.getInt("id"));
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

    private static class LineDaoHolder {
        private static LineDao getInstance() throws RepositoryException {
            return new LineDao();
        }
    }
}
