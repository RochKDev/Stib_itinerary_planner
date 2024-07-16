package model.dao;

import model.dataStructure.Pair;
import model.dto.FrequentTravelDto;
import model.exceptions.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FrequentTravelDao implements Dao<String, FrequentTravelDto> {

    private Connection connection;

    private FrequentTravelDao() throws RepositoryException {
        connection = DBManager.getInstance().getConnection();
    }

    public static FrequentTravelDao getInstance() throws RepositoryException {
        return FrequentTravelDaoHolder.getInstance();
    }

    @Override
    public List<FrequentTravelDto> selectAll() throws RepositoryException {
        String sql = """
                SELECT * 
                FROM frequent_travels
                """;
        List<FrequentTravelDto> dtos = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet res = stmt.executeQuery(sql)) {
            while (res.next()) {
                FrequentTravelDto dto = new FrequentTravelDto(res.getString("name"),
                        new Pair<>(res.getInt("id_line_origin"), res.getInt("id_station_origin")),
                        new Pair<>(res.getInt("id_line_destination"), res.getInt("id_station_destination")));
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public FrequentTravelDto select(String key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("No key given in parameter");
        }
        String sql = """
                SELECT *
                FROM frequent_travels
                WHERE name = ?
                """;
        FrequentTravelDto dto = null;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, key);
            ResultSet res = pstmt.executeQuery();

            int count = 0;
            while (res.next()) {
                dto = new FrequentTravelDto(res.getString("name"),
                        new Pair<>(res.getInt("id_line_origin"), res.getInt("id_station_origin")),
                        new Pair<>(res.getInt("id_line_destination"), res.getInt("id_station_destination")));

                count++;
            }
            if (count > 1) {
                throw new RepositoryException("Not unique Record : " + key);
            }
            return dto;
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public void insert(FrequentTravelDto dto) throws RepositoryException {
        if (dto == null) {
            throw new RepositoryException("No dto given in parameter");
        }
        String sql = """
                INSERT INTO frequent_travels
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dto.getKey());
            pstmt.setInt(2, dto.getKeySource().getFirst());
            pstmt.setInt(3, dto.getKeySource().getSecond());
            pstmt.setInt(4, dto.getKeyDestination().getFirst());
            pstmt.setInt(5, dto.getKeyDestination().getSecond());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(String key) throws RepositoryException {
        if (key == null) {
            throw new RepositoryException("No key given in parameter");
        }
        String sql = """
                DELETE FROM frequent_travels
                WHERE name = ?
                """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, key);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(FrequentTravelDto dto) throws RepositoryException {
        if (dto == null) {
            throw new RepositoryException("No dto given in parameter");
        }
        String sql = """
                           UPDATE frequent_travels
                SET name = ?,
                    id_line_origin = ?,
                    id_station_origin = ?,
                    id_line_destination = ?,
                    id_station_destination = ?
                WHERE name = ?
                            """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dto.getKey());
            pstmt.setInt(2, dto.getKeySource().getFirst());
            pstmt.setInt(3, dto.getKeySource().getSecond());
            pstmt.setInt(4, dto.getKeyDestination().getFirst());
            pstmt.setInt(5, dto.getKeyDestination().getSecond());
            pstmt.setString(6, dto.getKey());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    private static class FrequentTravelDaoHolder {
        private static FrequentTravelDao getInstance() throws RepositoryException {
            return new FrequentTravelDao();
        }
    }
}


