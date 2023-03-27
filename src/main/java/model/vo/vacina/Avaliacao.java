package model.vo.vacina;

public enum Avaliacao {
	
	PESSIMO (1),
	DESCONFORTAVEL (2),
	NORMAL (3),
	BOM (4),
	OTIMO (5);
	
	private int valor;

	private Avaliacao(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static Avaliacao getAvaliacaoPorValor(int valor) {
		Avaliacao avaliacao = null;
		for(Avaliacao elemento: Avaliacao.values()) {
			if(elemento.getValor() == valor) {
				avaliacao = elemento;
			}
		}
		return avaliacao;
	}

}
