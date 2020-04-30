package hu.adatb.dao;

import hu.adatb.controller.DBController;
import hu.adatb.model.Series;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class SeriesDAOImpl implements SeriesDAO{

    private Connection conn;

    private static final String ADD_SOROZAT_STR = "INSERT INTO SOROZAT VALUES (?,?) ";

    private static final String DELETE_SOROZAT_STR = "DELETE FROM SOROZAT WHERE NEV=? ";

    private static final String UPDATE_SOROZAT_STR = "UPDATE SOROZAT SET nev=?,isbn=? WHERE NEV=? AND ISBN =? ";

    private static final String LIST_SOROZAT_STR = "SELECT * FROM SOROZAT ";

    private static final String LIST_KONYVEK_STR = "SELECT * FROM KONYVEK WHERE ISBN=? ";

    public void initialize(){
        conn = DBController.connect();
    }

    public SeriesDAOImpl(){
        initialize();
    }

    public boolean foreignKey(Series series) {
        try (PreparedStatement st = conn.prepareStatement(LIST_KONYVEK_STR)){
            st.setInt(1, series.getIsbn());

            int res = st.executeUpdate();

            ResultSet rs = st.executeQuery();

            if(!(rs.next())) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(Series series) {
        try (PreparedStatement st = conn.prepareStatement(ADD_SOROZAT_STR)){
            st.setString(1, series.getName());
            st.setInt(2, series.getIsbn());

            int res = st.executeUpdate();

            if(res == 1){
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Series series) {
        try (PreparedStatement st = conn.prepareStatement(DELETE_SOROZAT_STR)){
            st.setString(1, series.getName());

            int res = st.executeUpdate();

            if(res == 1){
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Series series, String oldName, int oldIsbn) {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_SOROZAT_STR)){
            st.setString(1, series.getName());
            st.setInt(2, series.getIsbn());
            st.setString(3, oldName);
            st.setInt(4, oldIsbn);

            int res = st.executeUpdate();

            if(res == 1){
                return true;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Series> list() {
        List<Series> serieslist = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(LIST_SOROZAT_STR)){
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                Series series = new Series();
                series.setName(rs.getString("nev"));
                series.setIsbn(rs.getInt("isbn"));

                serieslist.add(series);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return serieslist;
    }
}
