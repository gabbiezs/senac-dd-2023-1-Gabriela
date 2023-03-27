package Executavel;

import java.time.LocalDate;
import model.dao.vacina.PessoaDAO;
import model.dao.vacina.VacinaDAO;
import model.vo.vacina.EstagioPesquisa;
import model.vo.vacina.Pessoa;
import model.vo.vacina.TipoPessoa;
import model.vo.vacina.Vacina;

public class executavelVacina {

	public static void main(String[] args) {

		// executavel n�o finalizado, rascunho

		Pessoa p1 = new Pessoa(null,"Luciano Lazaro", "20/04/1987", "M", "123.456.789-11", TipoPessoa.PESQUISADOR);
		Pessoa p2 = new Pessoa(null,"Maria Madalena", "13/07/1983", "F", "134.435.768-13", TipoPessoa.VOLUNTARIO);
		Pessoa p3 = new Pessoa(null,"Carlos Claudio", "09/10/1991", "M", "123.456.749-14", TipoPessoa.GERAL);
		
		PessoaDAO cadastrarPessoa = new PessoaDAO();
		cadastrarPessoa.cadastrarPessoa(p1);
		
//		Pessoa pessoaConsultada = cadastrarPessoa.consultarPorId(1);
//		pessoaConsultada.setCpf("456.123.987-15");
//		boolean atualizou = cadastrarPessoa.atualizar(pessoaConsultada);		
//		if (atualizou) {
//			System.out.println("Pessoa atualizada com sucesso.");
//		}

//		if (cadastrarPessoa.excluir(2)) {
//			System.out.println("Pessoa foi excluida!");
//		}
		
		// Consultar Todos
//		for (Pessoa pessoa : cadastrarPessoa.consultarTodos()) {
//			System.out.println(pessoa.getNome());
//		}
		
		// Consultar pessoa por id
//		System.out.println(cadastrarPessoa.consultarPorId(1).getNome());
		

		Vacina v1 = new Vacina(null,"Vacina da Gripe", "Alemanha", EstagioPesquisa.INICIAL, LocalDate.now(), cadastrarPessoa.consultarPorId(1));
		Vacina v2 = new Vacina(null,"Vacina da Dengue", "Peru", EstagioPesquisa.TESTES, LocalDate.now(), cadastrarPessoa.consultarPorId(1));
		Vacina v3 = new Vacina(null,"Vacina da Febre Amarela", "China", EstagioPesquisa.MASSA, LocalDate.now(), cadastrarPessoa.consultarPorId(1));
		VacinaDAO cadastrarVacina = new VacinaDAO();
		cadastrarVacina.cadastrarVacina(v1);
		
	}

}
