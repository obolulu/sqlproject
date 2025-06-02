package dao;

import model.Region;
import util.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegionDAO {
    public List<Region> getAllRegions(){
        String sql = "SELECT region_id, region_name, currency FROM Regions";
        List<Region> regions = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Region region = new Region(rs.getInt("region_id"), rs.getString("region_name"),
                        rs.getString("currency"));
                regions.add(region);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all regions: " + e.getMessage());
            e.printStackTrace();
        }
        return regions;
    }
    public Region getRegionById(int regionId) {
        String sql = "SELECT region_id, region_name, currency FROM Regions WHERE region_id = ?";
        Region region = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, regionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    region = new Region(rs.getInt("region_id"), rs.getString("region_name"),
                            rs.getString("currency"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching region by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return region;
    }
}
