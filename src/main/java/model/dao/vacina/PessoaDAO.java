package model.dao.vacina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.dao.Banco;
import model.vo.vacina.Pessoa;

	public class PessoaDAO {

		public Pessoa cadastrarPessoa(Pessoa novaPessoa) {
		
		//Conectar ao banco
				Connection conexao = Banco.getConnection();
				String sql = "INSERT INTO PESSOA (NOME, DATANASCIMENTO, SEXO, CPF, TIPOPESSOA) "
						+ " VALUES (?,?,?,?,?) ";
				
				PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
				
				//Executar o INSERT
				try {
				query.setString(1, novaPessoa.getNome());
				query.setString(2, novaPessoa.getDataNascimento());
				query.setString(3, novaPessoa.getSexo());
				query.setString(4, novaPessoa.getCpf());
				query.setInt(5, novaPessoa.getTipoPessoa());
				query.execute();
				
				//Preencher o ID gerado no banco no objeto
						ResultSet resultado = query.getGeneratedKeys();
						if(resultado.next()) {
						novaPessoa.setId(resultado.getInt(1));
						}
				} catch (SQLException e) {
					System.out.println("Erro ao inserir telefone. \nCausa: " + e.getMessage());
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
			   + " CPF = ? , TIPOPESSOA = ? "
			   + " WHERE ID = ? ";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			try {
				query.setString(1, pessoaAtualizada.getNome());
				query.setString(2, pessoaAtualizada.getDataNascimento());
				query.setString(3, pessoaAtualizada.getSexo());
				query.setString(4, pessoaAtualizada.getCpf());
				query.setInt(5, pessoaAtualizada.getTipoPessoa());
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
					+ " WHERE ID = ?";
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
			
			try {
				query.setInt(1, id);
				ResultSet resultado = query.executeQuery();
		
				if(resultado.next()) {
					pessoaConsultada = new Pessoa();
					pessoaConsultada.setId(resultado.getInt("id"));
					pessoaConsultada.setNome(resultado.getString("Nome"));
					pessoaConsultada.setDataNascimento(resultado.getString("DataNascimento"));
					pessoaConsultada.setSexo(resultado.getString("Sexo"));
					pessoaConsultada.setCpf(resultado.getString("CPF"));
					pessoaConsultada.setTipoPessoa(resultado.getInt("TipoUsuario"));
				}
				
			} catch (SQLException e) {
				System.out.println("Erro ao buscar pessoa com id: + " + id 
							+ "\n Causa: " + e.getMessage());	
			}
	
			return pessoaConsultada;
		}

		public boolean excluir(int id) {
			boolean excluiu = false;
	
			Connection conexao = Banco.getConnection();
			String sql = " DELETE FROM PESSOA "
					+ " WHERE ID = ? ";
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
