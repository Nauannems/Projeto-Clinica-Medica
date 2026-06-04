package application;

import java.sql.Connection;

import db.Db;

public class Main {

    public static void main(String[] args) {

        Connection conn = null;

        try {
            conn = Db.getConnection();
            System.out.println("Conexão realizada com sucesso!");
        }
        catch (Exception e) {
            System.out.println("Erro ao conectar:");
            System.out.println(e.getMessage());
        }
        finally {
            Db.closeConnection();
        }
    }
}