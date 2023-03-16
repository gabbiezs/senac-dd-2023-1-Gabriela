package model.dao.telefonia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dao.Banco;
import model.vo.telefonia.Endereco;

public class EnderecoDAO {
	
	//INSERT
	//INSERT INTO ENDERECO (RUA, CEP, BAIRRO, CIDADE, ESTADO, NUMERO)
	//VALUES ('', '', '', '', 'SC', '');
	
	/**
	 * Insere um novo endereço ao banco
	 * @param novoTelefone o endereço a sepersistido
	 * @return o endereço inserido com a chave primaria gerada
	 */
	public Endereco inserirEndereco(Endereco novoEndereco) {
		//Conectar ao banco
		Connection conexao = Banco.getConnection();
		String sql = "INSERT INTO ENDERECO (RUA, CEP, BAIRRO, CIDADE, ESTADO, NUMERO) "
				+ " VALUES (?,?,?,?,?,?) ";
		
		PreparedStatement query = Banco.getPreparedStatementWithPk(conexao, sql);
		
		//Executar o INSERT
		try {
			query.setString(1, novoEndereco.getRua());
			query.setString(2, novoEndereco.getCep());
			query.setString(3, novoEndereco.getBairro());
			query.setString(4, novoEndereco.getCidade());
			query.setString(5, novoEndereco.getEstado());
			query.setString(6, novoEndereco.getNumero());
			query.execute();
			
			//Preencher o ID gerado no banco no objeto
			ResultSet resultado = query.getGeneratedKeys();
			if(resultado.next()) {
				novoEndereco.setId(resultado.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao inserir endereço. \nCausa: " + e.getMessage());
		} finally {
			//Fechar a conexão
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return novoEndereco;
	}
	
	public boolean atualizar(Endereco enderecoEditado) {
		boolean atualizou = false;
		Connection conexao = Banco.getConnection();
		String sql = " UPDATE ENDERECO "
				   + " SET CEP = ?, RUA = ?, NUMERO = ?, "
				   + "     BAIRRO = ?, CIDADE = ?, ESTADO = ? "
				   + " WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			query.setString(1, enderecoEditado.getCep());
			query.setString(2, enderecoEditado.getRua());
			query.setString(3, enderecoEditado.getNumero());
			query.setString(4, enderecoEditado.getBairro());
			query.setString(5, enderecoEditado.getCidade());
			query.setString(6, enderecoEditado.getEstado());
			query.setInt(7, enderecoEditado.getId());
			
			int quantidadeLinhasAtualizadas = query.executeUpdate();
			atualizou = quantidadeLinhasAtualizadas > 0;
		} catch (SQLException excecao) {
			System.out.println("Erro ao atualizar endereço. "
					+ "\n Causa: " + excecao.getMessage());
		}finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		
		return atualizou;
	}
	
	public Endereco consultarPorId(int id) {
		Endereco enderecoConsultado = null;
		Connection conexao = Banco.getConnection();
		String sql =  " SELECT * FROM ENDERECO "
				    + " WHERE ID = ?";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		
		try {
			query.setInt(1, id);
			ResultSet resultado = query.executeQuery();
			
			if(resultado.next()) {
				enderecoConsultado = new Endereco();
				enderecoConsultado.setId(resultado.getInt("id"));
				enderecoConsultado.setCep(resultado.getString("cep"));
				enderecoConsultado.setRua(resultado.getString("rua"));
				enderecoConsultado.setBairro(resultado.getString("bairro"));
				enderecoConsultado.setNumero(resultado.getString("numero"));
				enderecoConsultado.setCidade(resultado.getString("cidade"));
				enderecoConsultado.setEstado(resultado.getString("estado"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar endereço com id: + " + id 
								+ "\n Causa: " + e.getMessage());	
		} finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return enderecoConsultado;
	}
	
	public ArrayList<Endereco> consultarTodos() {
		Connection conexao = Banco.getConnection();
		ArrayList<Endereco> listaEndereco = new ArrayList<Endereco>();
		String sql =  " SELECT * FROM ENDERECO";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
		ResultSet resultado = query.executeQuery();	
			do {
			Endereco endereco = new Endereco();
			endereco.setId(resultado.getInt("id"));
			endereco.setCep(resultado.getString("cep"));
			endereco.setRua(resultado.getString("rua"));
			endereco.setBairro(resultado.getString("bairro"));
			endereco.setNumero(resultado.getString("numero"));
			endereco.setCidade(resultado.getString("cidade"));
			endereco.setEstado(resultado.getString("estado"));
			listaEndereco.add(endereco);
		} while (resultado.next());
	} catch (SQLException e) {
		System.out.println("Erro ao buscar todos os endereços. Causa: " + e.getMessage());	
	} finally {
		Banco.closePreparedStatement(query);
		Banco.closeConnection(conexao);
	}
		return listaEndereco;
	}
	
	public boolean excluir(int id) {
		boolean excluiu = false;
		
		Connection conexao = Banco.getConnection();
		String sql = " DELETE FROM ENDERECO "
				   + " WHERE ID = ? ";
		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		try {
			query.setInt(1, id);
			
			int quantidadeLinhasAtualizadas = query.executeUpdate();
			excluiu = quantidadeLinhasAtualizadas > 0;
		} catch (SQLException excecao) {
			System.out.println("Erro ao excluir endereço. "
					+ "\n Causa: " + excecao.getMessage());
		}finally {
			Banco.closePreparedStatement(query);
			Banco.closeConnection(conexao);
		}
		return excluiu;
	}

}
