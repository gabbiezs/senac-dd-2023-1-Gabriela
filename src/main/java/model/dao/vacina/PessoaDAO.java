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
import model.vo.vacina.TipoPessoa;
import model.vo.vacina.Vacina;

	public class PessoaDAO {

		public Pessoa cadastrarPessoa(Pessoa novaPessoa) {
		
		//Conectar ao banco
				Connection conexao = Banco.getConnection();
				String sql = "INSERT INTO PESSOA (NOME, DATANASCIMENTO, SEXO, CPF, IDTIPOPESSOA) "
						+ " VALUES (?,?,?,?,?) ";
				
				PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
				
				//Executar o INSERT
				try {
				query.setString(1, novaPessoa.getNome());
				query.setString(2, novaPessoa.getDataNascimento());
				query.setString(3, novaPessoa.getSexo());
				query.setString(4, novaPessoa.getCpf());
				query.setInt(5, novaPessoa.getTipoPessoa().getValor());
				query.execute();
				
				//Preencher o ID gerado no banco no objeto
						ResultSet resultado = query.getGeneratedKeys();
						if(resultado.next()) {
						novaPessoa.setId(resultado.getInt(1));
						}
				} catch (SQLException e) {
					System.out.println("Erro ao cadastrar pessoa. \nCausa: " + e.getMessage());
				} finally {
					//Fechar a conex�o
					Banco.closePreparedStatement(query);
					Banco.closeConnection(conexao);
				}
		
				return novaPessoa;
		}

		public boolean atualizar(Pessoa pessoaAtualizada) {
			boolean atualizou = false;
			Connection conexao = Banco.getConnection();
			String sql = " UPDATE PESSOA "
			   + " SET NOME = ?, DATANASCIMENTO = ?, SEXO = ?, "
			   + " CPF = ? , IDTIPOPESSOA = ? "
			   + " WHERE IDPESSOA = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setString(1, pessoaAtualizada.getNome());
				query.setString(2, pessoaAtualizada.getDataNascimento());
				query.setString(3, pessoaAtualizada.getSexo());
				query.setString(4, pessoaAtualizada.getCpf());
				query.setInt(5, pessoaAtualizada.getTipoPessoa().getValor());
				query.setInt(6, pessoaAtualizada.getId());
				
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

		public Pessoa consultarPorId(int id) {
			Pessoa pessoaConsultada = null;
			Connection conexao = Banco.getConnection();
			String sql =  " SELECT * FROM PESSOA "
					+ " WHERE IDPESSOA = ?";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			
			try {
				query.setInt(1, id);
				ResultSet resultado = query.executeQuery();
		
				if(resultado.next()) {
					pessoaConsultada = new Pessoa();
					pessoaConsultada.setId(resultado.getInt("IDPESSOA"));
					pessoaConsultada.setNome(resultado.getString("NOME"));
					pessoaConsultada.setDataNascimento(resultado.getString("DATANASCIMENTO"));
					pessoaConsultada.setSexo(resultado.getString("SEXO"));
					pessoaConsultada.setCpf(resultado.getString("CPF"));
					pessoaConsultada.setTipoPessoa(TipoPessoa.getTipoPessoaPorValor(resultado.getInt("IDTIPOPESSOA")));
				}
				
			} catch (SQLException e) {
				System.out.println("Erro ao buscar pessoa com id: + " + id 
							+ "\n Causa: " + e.getMessage());	
			}
	
			return pessoaConsultada;
		}
		
		public ArrayList<Pessoa> consultarTodos() {
			Connection conexao = Banco.getConnection();
			ArrayList<Pessoa> listaPessoa = new ArrayList<Pessoa>();
			String sql =  " SELECT * FROM PESSOA";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
			ResultSet resultado = query.executeQuery();	
				while (resultado.next()) {
					Pessoa pessoa = converterDeResultSetParaEntidade(resultado);
				listaPessoa.add(pessoa);
			} 
		} catch (SQLException e) {
			System.out.println("Erro ao buscar todas as pessoas. Causa: " + e.getMessage());	
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
			return listaPessoa;
		}
		
		private Pessoa converterDeResultSetParaEntidade(ResultSet resultado) throws SQLException {
			DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Pessoa pessoa = new Pessoa();
			pessoa.setId(resultado.getInt("IDPESSOA"));
			pessoa.setNome(resultado.getString("NOME"));
			pessoa.setDataNascimento(resultado.getString("DATANASCIMENTO"));
			pessoa.setSexo(resultado.getString("SEXO"));
			pessoa.setCpf(resultado.getString("CPF"));
			pessoa.setTipoPessoa(TipoPessoa.getTipoPessoaPorValor(resultado.getInt("IDTIPOPESSOA")));
			return pessoa;
		}

		public boolean excluir(int id) {
			boolean excluiu = false;
	
			Connection conexao = Banco.getConnection();
			String sql = " DELETE FROM PESSOA "
					+ " WHERE IDPESSOA = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setInt(1, id);
		
				int quantidadeLinhasAtualizadas = query.executeUpdate();
				excluiu = quantidadeLinhasAtualizadas > 0;
			} catch (SQLException excecao) {
				System.out.println("Erro ao excluir pessoa. "
				+ "\n Causa: " + excecao.getMessage());
			}finally {
				Banco.closePreparedStatement(query);
				Banco.closeConnection(conexao);
			}
				return excluiu;
		}

}
