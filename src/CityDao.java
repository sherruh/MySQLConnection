import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao {
    private Connection con;

    public CityDao(Connection con) {
        this.con = con;
    }

    public List<City> findAll() throws SQLException {
        String query="select id,name,country_id from city";
        PreparedStatement statement=this.con.prepareStatement(query);
        ResultSet rs=statement.executeQuery();
        List<City> cityList=new ArrayList<>();
        while (rs.next()){
            City city=new City(rs.getInt("id"),rs.getString("name"),rs.getInt("country_id"));
            cityList.add(city);
        }
        rs.close();
        statement.close();
        return cityList;
    }

    public List<City> findAllKyrgyzstan() throws SQLException {
        String query="select id,name,country_id from city where country_id=?";
        PreparedStatement statement=this.con.prepareStatement(query);
        statement.setInt(1,3);
        ResultSet rs=statement.executeQuery();
        List<City> cityList=new ArrayList<>();
        while (rs.next()){
            City city=new City(rs.getInt("id"),rs.getString("name"),rs.getInt("country_id"));
            cityList.add(city);
        }
        rs.close();
        statement.close();
        return cityList;
    }

    public City findById(int id) throws SQLException {
        String query="select id,name,country_id from city where id=?";
        PreparedStatement statement=this.con.prepareStatement(query);
        statement.setInt(1,id);
        ResultSet rs=statement.executeQuery();
        City city=null;
        if(rs.next()){
            city=new City(rs.getInt("id"),rs.getString("name"),rs.getInt("country_id"));
        }
        rs.close();
        statement.close();
        return city;
    }

    public City findByName(String name) throws SQLException {
        String query="select id,name,country_id from city where name=?";
        PreparedStatement statement=this.con.prepareStatement(query);
        statement.setString(1,name);
        ResultSet rs=statement.executeQuery();
        City city=null;
        if(rs.next()){
            city=new City(rs.getInt("id"),rs.getString("name"),rs.getInt("country_id"));
        }
        rs.close();
        statement.close();
        return city;
    }

    public int executeInsert(City city) throws SQLException {
        String query = "insert into city (name, country_id) " +
                "values (?,?)";
        PreparedStatement stmt = null;
        int status = 0;
        try {
            stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, city.getName());
            stmt.setInt(2, city.getCountryId());
            //метод принимает значение без параметров
            //темже способом можно сделать и UPDATE или DELETE
            status = stmt.executeUpdate();
            ResultSet rs=stmt.getGeneratedKeys();
            int id=0;
            if(rs.next()){
                id=rs.getInt(1);
            }
            city.setId(id);
            System.out.println(id);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close stmt and resultset here
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
        }
        return status;
    }

    public int executeDelete(City city) throws SQLException {
        String query = "delete from city where name= (?)";
        PreparedStatement stmt = null;
        int status = 0;
        try {
            stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, city.getName());
            status = stmt.executeUpdate();
            ResultSet rs=stmt.getGeneratedKeys();
            int id=0;
            if(rs.next()){
                id=rs.getInt(1);
            }
            city.setId(id);
            System.out.println(id);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException se) {}
        }
        return status;
    }

    public int executeDeleteById(City city) throws SQLException {
        String query = "delete from city where id= ?";
        PreparedStatement stmt = null;
        int status = 0;
        try {
            stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, city.getId());
            status = stmt.executeUpdate();
            ResultSet rs=stmt.getGeneratedKeys();
            int id=0;
            if(rs.next()){
                id=rs.getInt(1);
            }
            city.setId(id);
            System.out.println(id);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException se) {}
        }
        return status;
    }

    public int executeUpdate(City city,String newCityName) throws SQLException {
        String query = "update city set name=? where name=?";
        PreparedStatement stmt = null;
        int status = 0;
        try {
            stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, newCityName);;
            stmt.setString(2, city.getName());;
            status = stmt.executeUpdate();
            ResultSet rs=stmt.getGeneratedKeys();
            int id=0;
            if(rs.next()){
                id=rs.getInt(1);
            }
            city.setId(id);
            System.out.println(id);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException se) {}
        }
        return status;
    }
}
