package model.vo.vacina;

import java.time.LocalDate;

public class Vacina {
	
	private Integer Id;
	private String nomeVacina;
	private String paisOrigem;
	private EstagioPesquisa estagioPesquisa;
	private LocalDate dataInicio;
	private Pessoa responsavel;
	
	public Vacina(Integer id, String nomeVacina, String paisOrigem, EstagioPesquisa estagioPesquisa,
			LocalDate dataInicio, Pessoa responsavel) {
		super();
		Id = id;
		this.nomeVacina = nomeVacina;
		this.paisOrigem = paisOrigem;
		this.estagioPesquisa = estagioPesquisa;
		this.dataInicio = dataInicio;
		this.responsavel = responsavel;
	}

	public Vacina() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getNomeVacina() {
		return nomeVacina;
	}

	public void setNomeVacina(String nomeVacina) {
		this.nomeVacina = nomeVacina;
	}

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public EstagioPesquisa getEstagioPesquisa() {
		return estagioPesquisa;
	}

	public void setEstagioPesquisa(EstagioPesquisa estagioPesquisa) {
		this.estagioPesquisa = estagioPesquisa;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}
	
}
