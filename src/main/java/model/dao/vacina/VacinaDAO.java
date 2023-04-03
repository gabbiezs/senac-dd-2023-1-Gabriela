package model.dao.vacina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.dao.Banco;
import model.vo.vacina.EstagioPesquisa;
import model.vo.vacina.Pessoa;
import model.vo.vacina.Vacina;

public class VacinaDAO {
	
public Vacina cadastrarVacina(Vacina novaVacina) {

		//Conectar ao banco
				Connection conexao = Banco.getConnection();
				String sql = "INSERT INTO VACINA (NOMEVACINA, PAISORIGEM, ESTADOPESQUISA, DATAINICIO, IDPESSOA) "
						+ " VALUES (?,?,?,?,?) ";		
				PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
				
				//Executar o INSERT
				try {
				query.setString(1, novaVacina.getNomeVacina());
				query.setString(2, novaVacina.getPaisOrigem());
				query.setInt(3, novaVacina.getEstagioPesquisa().getValor());
				query.setDate(4, java.sql.Date.valueOf(novaVacina.getDataInicio()));
				query.setInt(5, novaVacina.getResponsavel().getId());
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
			   + " SET NOMEVACINA = ?, PAISORIGEM = ?, ESTADOPESQUISA = ?, DATAINICIO = ?, "
			   + " IDPESSOA = ?"
			   + " WHERE ID = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setString(1, vacinaAtualizada.getNomeVacina());
				query.setString(2, vacinaAtualizada.getPaisOrigem());
				query.setInt(3, vacinaAtualizada.getEstagioPesquisa().getValor());
				query.setDate(4, java.sql.Date.valueOf(vacinaAtualizada.getDataInicio()));
				query.setString(5, vacinaAtualizada.getResponsavel().getNome());
				query.setInt(6, vacinaAtualizada.getId());
		
				int quantidadeLinhasAtualizadas = query.executeUpdate();
				atualizou = quantidadeLinhasAtualizadas > 0;
			} catch (SQLException excecao) {
				System.out.println("Erro ao atualizar a vacina. "
				+ "\n Causa: " + excecao.getMessage());
			}finally {
				Banco.closePreparedStatement(query);
				Banco.closeConnection(conexao);
			}
	
			return atualizou;
		}
		
		public Pessoa converterPessoaPorId(int id) {
			Pessoa nomeResponsavel = new Pessoa();
			Connection conexao = Banco.getConnection();
			String sql =  " SELECT NOME FROM PESSOA "
					+ " WHERE ID = ?";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setInt(1, id);
				ResultSet resultado = query.executeQuery();
				if(resultado.next()) {
					nomeResponsavel.setNome(resultado.getString(1));
				}
			} catch (SQLException e) {
				System.out.println("Erro ao buscar o nome da pessoa com id: + " + id 
						+ "\n Causa: " + e.getMessage());	
		}
			
			return nomeResponsavel;
		}

		public Vacina consultarPorId(int id) {
			DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
					vacinaConsultada.setId(resultado.getInt("IDVACINA"));
					vacinaConsultada.setNomeVacina(resultado.getString("NOMEVACINA"));
					vacinaConsultada.setPaisOrigem(resultado.getString("PAISORIGEM"));
					vacinaConsultada.setEstagioPesquisa(EstagioPesquisa.getEstagioPesquisaPorValor(resultado.getInt("ESTADOPESQUISA")));
					vacinaConsultada.setDataInicio(LocalDate.parse(resultado.getString("DATAINICIO"), dataFormatter));							
					vacinaConsultada.setResponsavel(converterPessoaPorId(resultado.getInt("IDPESSOA")));
				}
			} catch (SQLException e) {
				System.out.println("Erro ao buscar vacina com id: + " + id 
							+ "\n Causa: " + e.getMessage());	
			}
	
			return vacinaConsultada;
		}
		
		public ArrayList<Vacina> consultarTodos() {
			Connection conexao = Banco.getConnection();
			ArrayList<Vacina> listaVacina = new ArrayList<Vacina>();
			String sql =  " SELECT * FROM VACINA";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
			ResultSet resultado = query.executeQuery();	
				while (resultado.next()) {
					Vacina vacina = converterDeResultSetParaEntidade(resultado);
				listaVacina.add(vacina);
			} 
		} catch (SQLException e) {
			System.out.println("Erro ao buscar todas as vacinas. Causa: " + e.getMessage());	
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
			return listaVacina;
		}
		
		private Vacina converterDeResultSetParaEntidade(ResultSet resultado) throws SQLException {
			DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Vacina vacina = new Vacina();
			vacina.setId(resultado.getInt("IDVACINA"));
			vacina.setNomeVacina(resultado.getString("NOMEVACINA"));
			vacina.setPaisOrigem(resultado.getString("PAISORIGEM"));
			vacina.setEstagioPesquisa(EstagioPesquisa.getEstagioPesquisaPorValor(resultado.getInt("ESTADOPESQUISA")));
			vacina.setDataInicio(LocalDate.parse(resultado.getString("DATAINICIO"), dataFormatter));
			vacina.setResponsavel(converterPessoaPorId(resultado.getInt("IDPESSOA")));
			return vacina;
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
