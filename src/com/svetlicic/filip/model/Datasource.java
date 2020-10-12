package com.svetlicic.filip.model;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Datasource {

    public static final String DB_NAME = "prodavaonica.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Programming\\Databases\\" + DB_NAME;

    public static final String TABLE_PRODAVAONICE = "prodavaonice";
    public static final String COLUMN_PRODAVAONICA_ID = "_id";
    public static final String COLUMN_PRODAVAONICA_NAZIV = "naziv";

    public static final String TABLE_ARTIKLI = "artikli";
    public static final String COLUMN_ARTIKL_ID = "_id";
    public static final String COLUMN_ARTIKL_NAZIV = "naziv";
    public static final String COLUMN_ARTIKL_ZALIHA = "zaliha";
    public static final String COLUMN_ARTIKL_PRODAVAONICA = "prodavaonica";

    public static final String TABLE_PRODANI_ARTIKLI = "prodaniArtikli";
    public static final String COLUMN_PRODANI_ARTIKL_ID = "_id";
    public static final String COLUMN_PRODANI_ARTIKL_DATUM = "datum";
    public static final String COLUMN_PRODANI_ARTIKL_KOLICINA = "kolicina";
    public static final String COLUMN_PRODANI_ARTIKL = "artikl";

    public static final String INSERT_PRODAVAONICA = "INSERT INTO " + TABLE_PRODAVAONICE + "(" + COLUMN_PRODAVAONICA_NAZIV + ") VALUES (?)";
    public static final String INSERT_ARTIKL = "INSERT INTO " + TABLE_ARTIKLI + "(" + COLUMN_ARTIKL_NAZIV + ", " + COLUMN_ARTIKL_ZALIHA + ", " + COLUMN_ARTIKL_PRODAVAONICA + ") VALUES (?, ?, ?)";
    public static final String INSERT_PRODANI_ARTIKL = "INSERT INTO " + TABLE_PRODANI_ARTIKLI + "(" + COLUMN_PRODANI_ARTIKL_DATUM + ", " +
                                                            COLUMN_PRODANI_ARTIKL_KOLICINA + ", " + COLUMN_PRODANI_ARTIKL + ") VALUES (?, ?, ?)";

    public static final String QUERY_PRODAVAONICA = "SELECT " + COLUMN_PRODAVAONICA_ID + " FROM " + TABLE_PRODAVAONICE + " WHERE " + COLUMN_PRODAVAONICA_NAZIV + " = ?";
    public static final String QUERY_ARTIKL = "SELECT " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_ID + ", " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_ZALIHA + ", " +
                                                TABLE_PRODAVAONICE + "." + COLUMN_PRODAVAONICA_NAZIV + " FROM " + TABLE_ARTIKLI +
                                                " INNER JOIN " + TABLE_PRODAVAONICE + " ON " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_PRODAVAONICA +
                                                    " = " + TABLE_PRODAVAONICE + "." + COLUMN_PRODAVAONICA_ID + " WHERE " +
                                                        TABLE_ARTIKLI + "." + COLUMN_ARTIKL_NAZIV + " = ? AND " + TABLE_PRODAVAONICE +
                                                            "." + COLUMN_PRODAVAONICA_NAZIV + " = ?";
    public static final String QUERY_ALL_ARTIKLE = "SELECT " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_ID + ", " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_NAZIV +
                                                        ", " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_ZALIHA + " FROM " + TABLE_ARTIKLI +
                                                        " INNER JOIN " + TABLE_PRODAVAONICE + " ON " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_PRODAVAONICA +
                                                            " = " + TABLE_PRODAVAONICE + "." + COLUMN_PRODAVAONICA_ID + " WHERE " +
                                                                TABLE_PRODAVAONICE + "." + COLUMN_PRODAVAONICA_NAZIV + " = ?";
    public static final String QUERY_PRODANI_ARTIKL = "SELECT " + TABLE_PRODANI_ARTIKLI + "." + COLUMN_PRODANI_ARTIKL_ID + ", " +
                                                        TABLE_PRODANI_ARTIKLI + "." + COLUMN_PRODANI_ARTIKL_DATUM + ", " +
                                                        TABLE_PRODANI_ARTIKLI + "." + COLUMN_PRODANI_ARTIKL_KOLICINA + " FROM " + TABLE_PRODANI_ARTIKLI +
                                                        " INNER JOIN " + TABLE_ARTIKLI + " ON " + TABLE_PRODANI_ARTIKLI + "." + COLUMN_PRODANI_ARTIKL +
                                                        " = " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_ID + " INNER JOIN " + TABLE_PRODAVAONICE + " ON " +
                                                        TABLE_ARTIKLI + "." + COLUMN_ARTIKL_PRODAVAONICA + " = " + TABLE_PRODAVAONICE + "." + COLUMN_PRODAVAONICA_ID +
                                                        " WHERE " + TABLE_PRODANI_ARTIKLI + "." + COLUMN_PRODANI_ARTIKL_DATUM + " = ? AND " +
                                                        TABLE_ARTIKLI + "." + COLUMN_ARTIKL_NAZIV + " = ? AND " + TABLE_PRODAVAONICE + "." +
                                                        COLUMN_PRODAVAONICA_NAZIV + " = ?";
    public static final String QUERY_ALL_PRODANE_ARTIKLE = "SELECT " + TABLE_PRODANI_ARTIKLI + "." + COLUMN_PRODANI_ARTIKL_DATUM + ", " +
                                                            TABLE_PRODANI_ARTIKLI + "." + COLUMN_PRODANI_ARTIKL_KOLICINA + " FROM " + TABLE_PRODANI_ARTIKLI +
                                                            " INNER JOIN " + TABLE_ARTIKLI + " ON " + TABLE_PRODANI_ARTIKLI + "." + COLUMN_PRODANI_ARTIKL +
                                                            " = " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_ID + " INNER JOIN " + TABLE_PRODAVAONICE + " ON " +
                                                            TABLE_ARTIKLI + "." + COLUMN_ARTIKL_PRODAVAONICA + " = " + TABLE_PRODAVAONICE + "." + COLUMN_PRODAVAONICA_ID +
                                                            " WHERE " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_NAZIV + " = ? AND " + TABLE_PRODAVAONICE + "." +
                                                            COLUMN_PRODAVAONICA_NAZIV + " = ?";

    public static final String UPDATE_ARTIKL = "UPDATE " + TABLE_ARTIKLI + " SET " + COLUMN_ARTIKL_ZALIHA + " = ?" + " WHERE " +
                                                    COLUMN_ARTIKL_ID + " = ?";
    public static final String UPDATE_PRODANI_ARTIKL = "UPDATE " + TABLE_PRODANI_ARTIKLI + " SET " + COLUMN_PRODANI_ARTIKL_KOLICINA + " = ? WHERE " +
                                                        COLUMN_PRODANI_ARTIKL_ID + " = ?";

    public static final String DELETE_ARTIKL = "DELETE FROM " + TABLE_ARTIKLI + " WHERE " + TABLE_ARTIKLI + "." + COLUMN_ARTIKL_ID + " = ?";


    private Connection conn;

    private PreparedStatement insertIntoProdavaonice;
    private PreparedStatement insertIntoArtikli;
    private PreparedStatement insertIntoProdaniArtikli;

    private PreparedStatement queryProdavaonica;
    private PreparedStatement queryArtikl;
    private PreparedStatement queryAllArtikliFromProdavaonica;
    private PreparedStatement queryProdaniArtikl;
    private PreparedStatement queryAllProdaniArtikli;

    private PreparedStatement updateArtikl;
    private PreparedStatement updateProdaniArtikl;

    private PreparedStatement deleteArtikl;

    private static Datasource instance = new Datasource();

    private Datasource(){

    }

    public static Datasource getInstance(){
        return instance;
    }

    public boolean open(){

        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            insertIntoProdavaonice = conn.prepareStatement(INSERT_PRODAVAONICA, Statement.RETURN_GENERATED_KEYS);
            insertIntoArtikli = conn.prepareStatement(INSERT_ARTIKL);
            queryProdavaonica = conn.prepareStatement(QUERY_PRODAVAONICA);
            queryArtikl = conn.prepareStatement(QUERY_ARTIKL);
            updateArtikl = conn.prepareStatement(UPDATE_ARTIKL);
            queryAllArtikliFromProdavaonica = conn.prepareStatement(QUERY_ALL_ARTIKLE);
            insertIntoProdaniArtikli = conn.prepareStatement(INSERT_PRODANI_ARTIKL);
            queryProdaniArtikl = conn.prepareStatement(QUERY_PRODANI_ARTIKL);
            updateProdaniArtikl = conn.prepareStatement(UPDATE_PRODANI_ARTIKL);
            queryAllProdaniArtikli = conn.prepareStatement(QUERY_ALL_PRODANE_ARTIKLE);
            deleteArtikl = conn.prepareStatement(DELETE_ARTIKL);

            return true;
        } catch (SQLException e){
            System.out.println("Connection failed: " + e.getMessage());
            return false;
        }
    }

    public void close(){

        try {
            closePreparedStatement(deleteArtikl);
            closePreparedStatement(queryAllProdaniArtikli);
            closePreparedStatement(updateProdaniArtikl);
            closePreparedStatement(queryProdaniArtikl);
            closePreparedStatement(insertIntoProdaniArtikli);
            closePreparedStatement(queryAllArtikliFromProdavaonica);
            closePreparedStatement(updateArtikl);
            closePreparedStatement(queryArtikl);
            closePreparedStatement(queryProdavaonica);
            closePreparedStatement(insertIntoArtikli);
            closePreparedStatement(insertIntoProdavaonice);

            if(conn != null){
                conn.close();
            }
        }catch (SQLException e){
            System.out.println("Connection closing failed: " + e.getMessage());
        }
    }

    private int insertProdavaonica(String naziv) throws SQLException{
        queryProdavaonica.setString(1, naziv);
        ResultSet results = queryProdavaonica.executeQuery();
        if(results.next()){
            return results.getInt(1);
        } else {
            insertIntoProdavaonice.setString(1, naziv);
            int red = insertIntoProdavaonice.executeUpdate();

            if(red != 1){
                throw new SQLException("Couldn't insert prodavaonica!");
            }

            ResultSet generatedKeys = insertIntoProdavaonice.getGeneratedKeys();
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id from prodavaonica!");
            }
        }
    }

    public boolean insertArtikl(String naziv, int zaliha, String prodavaonica){

        try {
                conn.setAutoCommit(false);

                queryArtikl.setString(1, naziv);
                queryArtikl.setString(2, prodavaonica);
                ResultSet results = queryArtikl.executeQuery();
                if(results.next()){
                    int artiklId = results.getInt(1);
                    int staraZaliha = results.getInt(2);
                    int novaZaliha = staraZaliha + zaliha;
                    updateArtikl.setInt(1, novaZaliha);
                    updateArtikl.setInt(2, artiklId);
                    int red = updateArtikl.executeUpdate();
                    if(red == 1){
                        conn.commit();
                    } else {
                        throw new SQLException("Artikle update failed");
                    }
                    return true;
                } else {
                    int prodavaonicaId = insertProdavaonica(prodavaonica);
                    insertIntoArtikli.setString(1, naziv);
                    insertIntoArtikli.setInt(2, zaliha);
                    insertIntoArtikli.setInt(3, prodavaonicaId);
                    int red = insertIntoArtikli.executeUpdate();
                    if(red == 1){
                        conn.commit();
                    } else {
                        throw new SQLException("Artikle insert failed");
                    }
                    return true;
                }
        } catch (SQLException e){
                System.out.println("Insert artikl exception: " + e.getMessage());
                try {
                    System.out.println("Performing rollback");
                    conn.rollback();
                }catch (SQLException e1){
                    System.out.println("Sve otislo k vragu!" + e1.getMessage());
                }
        } finally {
                try {
                    System.out.println("Resetting default commit behavior");
                    conn.setAutoCommit(true);
                }catch (SQLException e){
                    System.out.println("Couldn't reset auto-commit!" + e.getMessage());
                }
        }
        return false;
    }

    public Set<Artikl> getArtikli(String nazivProdavaonice){
        try {
            Set<Artikl> artikli = new HashSet<>();
            queryAllArtikliFromProdavaonica.setString(1, nazivProdavaonice);
            ResultSet results = queryAllArtikliFromProdavaonica.executeQuery();
            while (results.next()){
                String naziv = results.getString(2);
                int zaliha = results.getInt(3);
                Artikl artikl = new Artikl(naziv, zaliha);
                artikl.setListaProdanihArtikala(Datasource.getInstance().getProdaniArtikli(naziv, nazivProdavaonice));
                artikli.add(artikl);
            }
            return artikli;
        } catch (SQLException e){
            System.out.println("Couldn't get artikl from prodavaonica!" + e.getMessage());
        }
        return null;
    }

    public Map<String, Integer> getProdaniArtikli(String nazivArtikla, String nazivProdavaonice){
        try {
            Map<String, Integer> prodaniArtikli = new HashMap<>();
            queryAllProdaniArtikli.setString(1, nazivArtikla);
            queryAllProdaniArtikli.setString(2, nazivProdavaonice);
            ResultSet results = queryAllProdaniArtikli.executeQuery();
            while (results.next()){
                String datum = results.getString(1);
                int kolicina = results.getInt(2);
                prodaniArtikli.put(datum, kolicina);
            }
            return prodaniArtikli;
        } catch (SQLException e){
            System.out.println("Couldn't get prodani artikli from prodavaonica!" + e.getMessage());
        }
        return null;
    }

    public boolean insertProdaniArtikl(String datum, int kolicina, String nazivArtikla, String nazivProdavaonice){
        try {
            conn.setAutoCommit(false);

            queryArtikl.setString(1, nazivArtikla);
            queryArtikl.setString(2, nazivProdavaonice);
            ResultSet results = queryArtikl.executeQuery();

            if(results.next()){
                int artiklId = results.getInt(1);
                queryProdaniArtikl.setString(1, datum);
                queryProdaniArtikl.setString(2, nazivArtikla);
                queryProdaniArtikl.setString(3, nazivProdavaonice);
                ResultSet results1 = queryProdaniArtikl.executeQuery();
                if(results1.next()){
                    int prodaniArtiklId = results1.getInt(1);
                    int staraKolicina = results1.getInt(3);
                    int novaKolicina = staraKolicina + kolicina;
                    updateProdaniArtikl.setInt(1, novaKolicina);
                    updateProdaniArtikl.setInt(2, prodaniArtiklId);
                    int red = updateProdaniArtikl.executeUpdate();
                    if(red == 1){
                        conn.commit();
                    } else {
                        throw new SQLException("Update prodanog artikla failed!");
                    }
                    return true;
                } else {
                    insertIntoProdaniArtikli.setString(1, datum);
                    insertIntoProdaniArtikli.setInt(2, kolicina);
                    insertIntoProdaniArtikli.setInt(3, artiklId);
                    int red = insertIntoProdaniArtikli.executeUpdate();
                    if(red == 1){
                        conn.commit();
                    } else {
                        throw new SQLException("Insert prodanog artikla failed!");
                    }
                    return true;
                }
            } else {
                System.out.println("There is no such artikl in our pordavaonica!");
                return false;
            }

        }catch (SQLException e){
            System.out.println("Insert prodanog artikla failed!");
            try {
                System.out.println("Performing rollback");
                conn.rollback();
            }catch (SQLException e1){
                System.out.println("Sve otislo k vragu!" + e1.getMessage());
            }
        } finally {
            try {
                System.out.println("Resetting default commit behavior");
                conn.setAutoCommit(true);
            }catch (SQLException e){
                System.out.println("Couldn't reset auto-commit!" + e.getMessage());
            }
        }
        return false;
    }

    public Artikl deleteArtikl(String nazivArtikla, String nazivProdavaonice){
        try {
            queryArtikl.setString(1, nazivArtikla);
            queryArtikl.setString(2, nazivProdavaonice);
            ResultSet results = queryArtikl.executeQuery();
            if(results.next()){
                int artiklId = results.getInt(1);
                int zaliha = results.getInt(2);
                deleteArtikl.setInt(1, artiklId);
                int status = deleteArtikl.executeUpdate();
                if(status == 1){
                    return new Artikl(nazivArtikla, zaliha);
                }
            }
        }catch (SQLException e){
            System.out.println("Deleting artikl failed!");
        }
        return null;
    }

    private void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        if(preparedStatement != null){
            preparedStatement.close();
        }
    }
}
