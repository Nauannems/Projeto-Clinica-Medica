package dao;
import entities.Exame;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExameDao {

        public void insert(Exame obj){ // metodo para cadastra o novo exame
            Connection conn = null; // cria a conexao como banco
            PreparedStatement ps = null; // vai prepara o comando

            try {
                conn = Db.getConnection(); // vai chama a classe db para conecta ao banco
                ps = conn.prepareStatement( "INSERT INTO Exame " + "(ID_Consulta, Tipo_Exame, Data_Exame, Resultado, Status, Valor)" + "VALUES(?, ?, ?, ?, ?, ?)" , Statement.RETURN_GENERATED_KEYS); // comando para inseriri oa dados do exame

                ps.setInt(1, obj.getIdConsulta()); // vaiprenche os paramentros e vada ? vai recebe um valor
                ps.setString(2, obj.getTipoExame());
                ps.setString(3, obj.getDataExame());
                ps.setString(4, obj.getResultado());
                ps.setString(5, obj.getStatus());
                ps.setDouble(6, obj.getValor());

                int rowsAffected = ps.executeUpdate(); // para executa

                if (rowsAffected > 0) {
                    ResultSet rs = ps.getGeneratedKeys(); // vai pega o id criado do banco automaticamente

                    if (rs.next()){
                        obj.setIdExame(rs.getInt(1)); // vai  colocar o id automaticamente dentro do obejto
                    }
                    Db.closeResultSet(rs); // fecha o comando e libera
                }
            }catch (SQLException e ){ // tratamento de exercuçao
                throw  new RuntimeException(e.getMessage());
            }finally {
                Db.closeStatement(ps);
            }
        }

        public List<Exame> findAll(){ // buscar todos os exames cadrastrados
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = Db. getConnection();
                String sql = "SELECT * FROM Exame ORDER BY ID_Exame"; // buscar todos os registros da tabela
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery(); // exercuta o select

                List<Exame> list = new ArrayList<>(); // cria uma lista para armazerna os exames

                while (rs.next()){ // para percorre os resultados
                    Exame exame =  new Exame(); // cria uma objeto vazio
                    // copia os dados e manda  para java
                    exame.setIdExame(rs.getInt("ID_Exame"));
                    exame.setIdConsulta(rs.getInt("ID_Consulta"));
                    exame.setTipoExame(rs.getString("Tipo_Exame"));
                    exame.setDataExame(rs.getString("Data_Exame"));
                    exame.setResultado(rs.getString("Resultado"));
                    exame.setStatus(rs.getString("Status"));
                    exame.setValor(rs.getDouble("Valor"));

                    list.add(exame); // quarda o exame na lista
                }

                return list; // retorna os exames

            } catch (SQLException e ){ // tratamento de exercuçao
                throw  new RuntimeException(e.getMessage());
            }finally {
                Db.closeResultSet(rs);
                Db.closeStatement(ps);
            }
        }

        public void update(Exame obj){ // para altera os dados do exames que ja existem
            Connection conn = null;
            PreparedStatement ps = null;

            try {
                conn =  Db.getConnection();
                ps = conn.prepareStatement("UPDATE Exame" + "SET  ID_Consulta = ?, Tipo_Exame = ?, Data_Exame = ?, " + "Resultado = ?, Status = ?, Valor = ? " + "WHERE ID_Exame = ?"); // vai atualizar os dados dos exames que possuir determinado id
                ps.setInt(1, obj.getIdConsulta()); // preenche oa paramentos  e vai substituir os ? por valores
                ps.setString(2, obj.getTipoExame());
                ps.setString(3, obj.getDataExame());
                ps.setString(4, obj.getResultado());
                ps.setString(5, obj.getStatus());
                ps.setDouble(6, obj.getValor());
                ps.setInt(7, obj.getIdExame()); // indicar qual exame vai ser alterado
                ps.executeUpdate();// realiza a atualizaçao

            }catch (SQLException e){
                throw new RuntimeException(e.getMessage());
            }finally {
                Db.closeStatement(ps);
            }
        }

        public  void deleteById(Integer id){ // esse metado vai excluir um exame
            Connection conn = null;
            PreparedStatement ps = null;

            try {
                conn = Db.getConnection();
                ps = conn.prepareStatement("DELETE FROM Exame WHere ID_Exame = ?"); // vai apaga o id informado
                ps.setInt(1, id); // apaga o exame com o id que foi informado
                ps.executeUpdate(); // remove o registro do banco de dados
            }catch (SQLException e ){
                throw new RuntimeException(e.getMessage());
            }finally {
                Db.closeStatement(ps);
            }
        }
    }



