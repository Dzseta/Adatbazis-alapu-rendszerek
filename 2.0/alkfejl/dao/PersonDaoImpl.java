package hu.alkfejl.dao;

import hu.alkfejl.model.Contract;
import hu.alkfejl.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoImpl implements PersonDAO {

    private final static String DB_STRING = "jdbc:sqlite:sample.db";
    private static final String CREATE_PERSON = "CREATE TABLE IF NOT EXISTS Person (" +
            "id integer PRIMARY KEY AUTOINCREMENT," +
            "name text NOT NULL," +
            "email text NOT NULL," +
            "birthYear integer NOT NULL);";

    private static final String CREATE_CONTRACT = "CREATE TABLE IF NOT EXISTS Contract (" +
            "id integer PRIMARY KEY AUTOINCREMENT," +
            "name text NOT NULL," +
            "position text NOT NULL," +
            "part_time boolean NOT NULL);";

    private static final String INSERT_PERSON = "INSERT INTO Person (name, email, birthYear) VALUES " +
            "(?,?,?);";

    private static final String SELECT_PERSON = "SELECT * FROM Person;";

    private static final String INSERT_CONTRACT = "INSERT INTO Contract (name, position, part_time) VALUES " +
            "(?,?,?);";

    private static final String SELECT_CONTRACT = "SELECT * FROM Contract;";

    public void initializeTables() {
        try (Connection conn = DriverManager.getConnection(DB_STRING); Statement st = conn.createStatement()) {
            st.executeUpdate(CREATE_PERSON);
            st.executeUpdate(CREATE_CONTRACT);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public PersonDaoImpl() {
        initializeTables();
    }

    @Override
    public boolean add(Person p) {
        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(INSERT_PERSON)) {
            st.setString(1, p.getName());
            st.setString(2, p.getEmail());
            st.setInt(3, p.getBirthYear());
            int res = st.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Person> getAll() {
        List<Person> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_STRING); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_PERSON);

            while (rs.next()) {
                Person p = new Person(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                );
                result.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addContract(Contract contract) {
        boolean rvFlag = false;

        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(INSERT_CONTRACT)) {
            st.setString(1, contract.getName());
            st.setString(2, contract.getPos());
            st.setBoolean(3, contract.isPartTime());
            int res = st.executeUpdate();
            if (res == 1) {
                rvFlag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rvFlag;
    }

    @Override
    public List<Contract> listContracts() {
        List<Contract> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_STRING); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_CONTRACT);

            while (rs.next()) {
                Contract c = new Contract();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setPos(rs.getString("position"));
                c.setPartTime(rs.getBoolean("part_time"));

                result.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
