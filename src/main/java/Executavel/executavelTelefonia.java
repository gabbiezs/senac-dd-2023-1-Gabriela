package Executavel;

import java.util.ArrayList;
import java.util.List;

import model.dao.telefonia.EnderecoDAO;
import model.dao.telefonia.TelefoneDAO;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Endereco;
import model.vo.telefonia.Telefone;

public class executavelTelefonia {

	public static void main(String[] args) {
		
		List<Telefone> telefonesDoSocrates = new ArrayList<Telefone>();
		Telefone tel1 = new Telefone(null, "48", "32243454", true, false);
		telefonesDoSocrates.add(tel1);
		
		List<Telefone> telefonesDoPele = new ArrayList<Telefone>();
		Telefone tel2 = new Telefone(null,"48", "32256854", true, true);
		telefonesDoPele.add(tel2);
		
		Endereco endereco1 = new Endereco(null, "Mauro ramos", "88123145", "10", "Centro", "Floripa", "SC");
		Cliente c1 = new Cliente ("Edson", "11122233344", telefonesDoPele, true, endereco1 );
		Endereco endereco2 = new Endereco(null, "Mauro ramos", "88123145", "10", "Centro", "Floripa", "SC");;
		Cliente c2 = new Cliente("Socrates","11122266644", telefonesDoSocrates, true, endereco2 );
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(c2);
		clientes.add(c1);
		
		System.out.println("---------- Clientes da Firma ----------");
		for(Cliente c: clientes) {
			System.out.println(c.toString());
		}
		
		EnderecoDAO salvadorDeEndereco = new EnderecoDAO();
		salvadorDeEndereco.inserirEndereco(endereco1);
		
		TelefoneDAO salvadorDeTelefone = new TelefoneDAO();
		salvadorDeTelefone.inserirTelefone(tel1);
		

	}

}
