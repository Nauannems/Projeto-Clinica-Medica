
package dao;
import db.Db;
import  entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    public  void insert(Usuario obj){

        Connection conn = null;
        PreparedStatement ps = null;


        try {
            conn = Db.getConnection();

            ps = conn.prepareStatement(
                    "INSERT INTO Usuario" + "(Nome, Telefone," +
                            " CPF, Email, Cargo, Setor ,Nivel_Acesso)"
                            + "VALUES (? ,? ,? ,? ,? ,?, ?) " ,
                    Statement.RETURN_GENERATED_KEYS);


            ps.setString(1,obj.getNome());
            ps.setString(2,obj.getTelefone());
            ps.setString(3,obj.getCpf());
            ps.setString(4,obj.getEmail());
            ps.setString(5,obj.getCargo());
            ps.setString(6,obj.getSetor());
            ps.setString(7,obj.getNivelAcesso());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0){
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    obj.setIdUsuario( rs.getInt(1));

                }
                Db.closeResultSet(rs);

            }


        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            Db.closeStatement(ps);
        }

    }
    public List<Usuario> findAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Db.getConnection();
            String sql = "SELECT * FROM Usuario ORDER BY ID_Usuario";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            List<Usuario> list = new ArrayList<>();

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("ID_Usuario"));
                usuario.setNome(rs.getString("Nome"));
                usuario.setTelefone(rs.getString("Telefone"));
                usuario.setCpf(rs.getString("CPF"));
                usuario.setEmail(rs.getString("Email"));
                usuario.setCargo(rs.getString("Cargo"));
                usuario.setSetor(rs.getString("Setor"));
                usuario.setNivelAcesso(rs.getString("Nivel_Acesso"));

                list.add(usuario);
            }

            return list;

        }catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            Db.closeResultSet(rs);
            Db.closeStatement(ps);
        }
    }


    public void update(Usuario obj ){

        Connection conn=null;
        PreparedStatement ps = null;

        try{

            conn = Db.getConnection();
            ps = conn.prepareStatement(
                    "UPDATE Usuario " +"SET Nome =?"+ "Telefone=?," + " CPF=?," + "Email=?,"+ "Cargo=?,"
                            +"Setor=?," + "Nivel_Acesso=?," + "WHERE ID_Usuario =?");

            ps.setString(1, obj.getNome());
            ps.setString(2,obj.getTelefone());
            ps.setString(3,obj.getCpf());
            ps.setString(4,obj.getEmail());
            ps.setString(5,obj.getCargo());
            ps.setString(6,obj.getSetor());
            ps.setString(7,obj.getNivelAcesso());
            ps.setInt(8,obj.getIdUsuario());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            Db.closeStatement(ps);
        }
    }

    public void deleteById (Integer id){

        Connection conn = null;
        PreparedStatement ps =null;

        try{
            conn = Db.getConnection();
            ps = conn.prepareStatement(
                    "DELETE FROM Usuario WHERE ID_Usuario = ? ");

            ps.setInt(1,id);
            ps.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());

        }finally {
            Db.closeStatement(ps);

        }

    }

}