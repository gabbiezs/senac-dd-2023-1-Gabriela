package Executavel;

import model.vo.vacina.Pessoa;
import model.vo.vacina.Vacina;

public class executavelVacina {

	public static void main(String[] args) {
		
		// 1- Pesquisadores, 2- Voluntarios, 3- Geral
		
		Pessoa p1 = new Pessoa(null,"Luciano Lazaro", "20/04/1987", "M", "123.456.789-11", 1);
		Pessoa p2 = new Pessoa(null,"Maria Madalena", "13/07/1983", "F", "134.435.768-13", 2);
		Pessoa p3 = new Pessoa(null,"Carlos Claudio", "09/10/1991", "M", "123.456.789-11", 3);
		
		Vacina v1 = new Vacina(null, "Alemanha", 1, "23/07/2022", "Butantan");
		Vacina v2 = new Vacina(null, "Peru", 2, "05/02/2021", "Pfizer");
		Vacina v3 = new Vacina(null, "China", 3, "11/01/2023", "Leonardo Leao");

		
		

	}

}
