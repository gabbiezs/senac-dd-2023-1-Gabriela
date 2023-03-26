package model.dao.vacina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.Banco;
import model.vo.vacina.Vacina;

public class VacinaDAO {
	
public Vacina cadastrarVacina(Vacina novaVacina) {
		
		//Conectar ao banco
				Connection conexao = Banco.getConnection();
				String sql = "INSERT INTO VACINA (PAISORIGEM, ESTADOPESQUISA, DATAINICIO, RESPONSAVEL) "
						+ " VALUES (?,?,?,?) ";		
				PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
				
				//Executar o INSERT
				try {
				query.setString(1, novaVacina.getPaisOrigem());
				query.setInt(2, novaVacina.getEstadoPesquisa());
				query.setString(3, novaVacina.getDataInicio());
				query.setString(4, novaVacina.getResponsavel());
				query.execute();
				
				//Preencher o ID gerado no banco no objeto
						ResultSet resultado = query.getGeneratedKeys();
						if(resultado.next()) {
							novaVacina.setId(resultado.getInt(1));
						}
				} catch (SQLException e) {
					System.out.println("Erro ao inserir vacina. \nCausa: " + e.getMessage());
				} finally {				
					//Fechar a conex�o
					Banco.closePreparedStatement(query);
					Banco.closeConnection(conexao);
				}
		
				return novaVacina;
		}

		public boolean atualizar(Vacina vacinaAtualizada) {
			boolean atualizou = false;
			Connection conexao = Banco.getConnection();
			String sql = " UPDATE VACINA "
			   + " SET PAISORIGEM = ?, ESTADOPESQUISA = ?, DATAINICIO = ?, "
			   + " RESPONSAVEL = ?"
			   + " WHERE ID = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setString(1, vacinaAtualizada.getPaisOrigem());
				query.setInt(2, vacinaAtualizada.getEstadoPesquisa());
				query.setString(3, vacinaAtualizada.getDataInicio());
				query.setString(4, vacinaAtualizada.getResponsavel());
				query.setInt(5, vacinaAtualizada.getId());
		
				int quantidadeLinhasAtualizadas = query.executeUpdate();
				atualizou = quantidadeLinhasAtualizadas > 0;
			} catch (SQLException excecao) {
				System.out.println("Erro ao atualizar a pessoa. "
				+ "\n Causa: " + excecao.getMessage());
			}finally {
				Banco.closePreparedStatement(query);
				Banco.closeConnection(conexao);
			}
	
			return atualizou;
		}

		public Vacina consultarPorId(int id) {
			Vacina vacinaConsultada = null;
			Connection conexao = Banco.getConnection();
			String sql =  " SELECT * FROM VACINA "
					+ " WHERE ID = ?";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			
			try {
				query.setInt(1, id);
				ResultSet resultado = query.executeQuery();
		
				if(resultado.next()) {
					vacinaConsultada = new Vacina();
					vacinaConsultada.setId(resultado.getInt("id"));
					vacinaConsultada.setPaisOrigem(resultado.getString("PaisOrigem"));;
					vacinaConsultada.setEstadoPesquisa(resultado.getInt("EstadoPesquisa"));
					vacinaConsultada.setDataInicio(resultado.getString("DataInicio"));
					vacinaConsultada.setResponsavel(resultado.getString("Responsavel"));
				}
			} catch (SQLException e) {
				System.out.println("Erro ao buscar pessoa com id: + " + id 
							+ "\n Causa: " + e.getMessage());	
			}
	
			return vacinaConsultada;
		}

		public boolean excluir(int id) {
			boolean excluiu = false;
	
			Connection conexao = Banco.getConnection();
			String sql = " DELETE FROM VACINA "
					+ " WHERE ID = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setInt(1, id);
		
				int quantidadeLinhasAtualizadas = query.executeUpdate();
				excluiu = quantidadeLinhasAtualizadas > 0;
			} catch (SQLException excecao) {
				System.out.println("Erro ao excluir vacina. "
				+ "\n Causa: " + excecao.getMessage());
			}finally {
				Banco.closePreparedStatement(query);
				Banco.closeConnection(conexao);
			}
				return excluiu;
		}
		
		

}
