import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        try {
            MySqlConnection mySqlConnection= new MySqlConnection();
            mySqlConnection.initConnection();
            Connection con=mySqlConnection.getCon();
            CityDao cityDao=new CityDao(con);
            List<City> cityList=cityDao.findAll();
            cityList.forEach(System.out::println);

            deleteDublicates(cityList,cityDao); //метод который удаляет дублирующие города по имени

            System.out.println(cityDao.executeUpdate(new City(0,"Osh",3),"Kant"));//метод который обновляет имя города

            List<City> cityListKyrgyzstan=cityDao.findAllKyrgyzstan();//метод который возвращает все города Кыргызстана
            cityListKyrgyzstan.forEach(System.out::println);

            System.out.println(cityDao.findById(28));//метод который возвращает город по id
            System.out.println(cityDao.findByName("Frunze"));//метод который возвращает город по имени

            System.out.println(cityDao.executeInsert(new City(0,"Kant",3)));
            System.out.println(cityDao.executeDelete(new City(0,"Kant",3)));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteDublicates(List<City> cityList,CityDao cityDao) throws SQLException {
        Set<String> uniques = new HashSet<>();

        for(City c : cityList) {
            if(!uniques.add(c.getName())) {
                cityDao.executeDeleteById(c);
            }
        }
    }




}
