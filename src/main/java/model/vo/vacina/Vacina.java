package model.vo.vacina;

public class Vacina {
	
	private Integer Id;
	private String paisOrigem;
	private int estadoPesquisa;
	private String dataInicio;
	private String responsavel;
	
	public Vacina(Integer id, String paisOrigem, int estadoPesquisa, String dataInicio, String responsavel) {
		super();
		Id = id;
		this.paisOrigem = paisOrigem;
		this.estadoPesquisa = estadoPesquisa;
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

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public int getEstadoPesquisa() {
		return estadoPesquisa;
	}

	public void setEstadoPesquisa(int estadoPesquisa) {
		this.estadoPesquisa = estadoPesquisa;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

}
