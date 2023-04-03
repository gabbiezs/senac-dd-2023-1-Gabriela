package model.bo.telefonia;

import java.util.List;

import model.dao.telefonia.ClienteDAO;
import model.exception.ClienteComTelefoneException;
import model.exception.CpfAlteradoException;
import model.exception.CpfJaUtilizadoException;
import model.exception.EnderecoInvalidoException;
import model.vo.telefonia.Cliente;

public class ClienteBO {

			private ClienteDAO dao = new ClienteDAO();
			
			/**
			 * Insere um novo cliente, mas faz valida��es que podem gerar exce��es
			 * @param novoCliente
			 * @return o novoCliente inserido, com a PK gerada
			 * @throws CpfJaUtilizadoException
			 * @throws EnderecoInvalidoException
			 */
			public Cliente inserir(Cliente novoCliente) throws CpfJaUtilizadoException, 
					EnderecoInvalidoException {
				if(dao.cpfJaUtilizado(novoCliente.getCpf())) {
					throw new CpfJaUtilizadoException("CPF informado j� foi utilizado");
				}
				
				validarEndereco(novoCliente);

				//Caso CPF n�o utilizado -> salvar e devolver o cliente
				return dao.inserir(novoCliente);
			}
			
			/**
			 * Atualiza um novo cliente, mas faz valida��es que podem gerar exce��es
			 * @param cliente
			 * @return o cliente atualizado
			 * @throws CpfAlteradoException 
			 * @throws EnderecoInvalidoException
			 */
			public boolean atualizar(Cliente clienteAlterado) throws EnderecoInvalidoException, CpfAlteradoException {
				Cliente clienteOriginal = dao.consultarPorId(clienteAlterado.getId());
				
				if(clienteAlterado.getCpf() != clienteOriginal.getCpf()) {
					throw new CpfAlteradoException("CPF n�o pode ser alterado");
				}
				
				validarEndereco(clienteAlterado);

				return dao.atualizar(clienteAlterado);
			}
			
			/**
			 * n�o deixar excluir cliente que possua telefone associado
			 * Criar exce��o ClienteComTelefoneException
			 * Caso cliente possua telefone(s): lan�ar ClienteComTelefoneException
			 * Caso contr�rio: chamar dao.excluir(id)
			 * @param id
			 * @return se excluiu ou n�o o cliente
			 */
			public boolean excluir(int id) throws ClienteComTelefoneException{
				Cliente clienteBuscado = dao.consultarPorId(id);
				if(!clienteBuscado.getTelefones().isEmpty()) {
					throw new ClienteComTelefoneException("Cliente possui telefones");
				}
				return dao.excluir(id);
			}
			
			public Cliente consultarPorId(int id) {
				return dao.consultarPorId(id);
			}
			
			public List<Cliente> consultarTodos() {
				return dao.consultarTodos();
			}
			
			private void validarEndereco(Cliente cliente) throws EnderecoInvalidoException {
				if(cliente.getEndereco() == null || cliente.getEndereco().getId() == null) {
					throw new EnderecoInvalidoException("Endere�o n�o informado");
				}
			}

	}

