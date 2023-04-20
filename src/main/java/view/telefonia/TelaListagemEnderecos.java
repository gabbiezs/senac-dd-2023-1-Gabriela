package view.telefonia;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.telefonia.EnderecoController;
import model.exception.EnderecoInvalidoException;
import model.vo.telefonia.Endereco;

/**
 * Tela para mostrar todos os endere�os
 * 
 * @author Vilmar C�sar Pereira J�nior
 * 
 * */
public class TelaListagemEnderecos {
	
	//Atributos da tela (componentes visuais)
	private JFrame frmListagemDeEnderecos;
	private JTable tblEnderecos;
	private String[] nomesColunas = { "#", "CEP", "Rua", "N�mero", "Bairro", "Cidade", "Estado" };
	private JButton btnBuscar;
	private JButton btnEditar;
	private JButton btnExcluir;
	
	//Lista para armezenar os endere�os consultados no banco
	private ArrayList<Endereco> enderecos;
	
	//Objeto usado para armazenar o endere�o que o usu�rio selecionar na tabela (tblEnderecos)
	private Endereco enderecoSelecionado;

	//M�todos usados no JTable
	private void limparTabela() {
		tblEnderecos.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}

	private void atualizarTabelaEnderecos() {
		this.limparTabela();

		DefaultTableModel model = (DefaultTableModel) tblEnderecos.getModel();
		//Preenche os valores na tabela linha a linha
		for (Endereco e : enderecos) {
			Object[] novaLinhaDaTabela = new Object[7];
			
			novaLinhaDaTabela[0] = e.getId();
			novaLinhaDaTabela[1] = e.getCep();
			novaLinhaDaTabela[2] = e.getRua();
			novaLinhaDaTabela[3] = e.getNumero();
			novaLinhaDaTabela[4] = e.getBairro();
			novaLinhaDaTabela[5] = e.getCidade();
			novaLinhaDaTabela[6] = e.getEstado();

			model.addRow(novaLinhaDaTabela);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaListagemEnderecos window = new TelaListagemEnderecos();
					window.frmListagemDeEnderecos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaListagemEnderecos() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmListagemDeEnderecos = new JFrame();
		frmListagemDeEnderecos.setTitle("Listagem de Endere�os");
		frmListagemDeEnderecos.setBounds(100, 100, 700, 525);
		frmListagemDeEnderecos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListagemDeEnderecos.getContentPane().setLayout(null);

		btnBuscar = new JButton("Buscar Todos");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnderecoController controller = new EnderecoController();
				enderecos = (ArrayList<Endereco>) controller.consultarTodos();
				
				atualizarTabelaEnderecos();
			}
		});
		btnBuscar.setBounds(285, 20, 120, 35);
		frmListagemDeEnderecos.getContentPane().add(btnBuscar);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(220, 430, 120, 35);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Mostra a TelaCadastroEndereco, passando o enderecoSelecionado como par�metro
				TelaCadastroEndereco telaEdicaoEndereco = new TelaCadastroEndereco(enderecoSelecionado);
			}
		});
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(360, 430, 120, 35);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcaoSelecionada = JOptionPane.showConfirmDialog(null, "Confirma a exclus�o do endereco selecionado?");
				
				if(opcaoSelecionada == JOptionPane.YES_OPTION) {
					try {
					EnderecoController controller = new EnderecoController();
					controller.excluir(enderecoSelecionado.getId());
					JOptionPane.showMessageDialog(null, "Endereco excluido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					} catch (EnderecoInvalidoException excecao) {
						JOptionPane.showConfirmDialog(null, excecao.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
			}
		});
		
		frmListagemDeEnderecos.getContentPane().add(btnExcluir);
		frmListagemDeEnderecos.getContentPane().add(btnEditar);
		//Bot�es iniciam bloqueados
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);

		tblEnderecos = new JTable();
		this.limparTabela();
		tblEnderecos.setBounds(15, 70, 655, 350);
		
		//Evento de clique em uma linha da tabela
		//Habilita/desabilita os bot�es "Editar" e "Excluir"
		tblEnderecos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indiceSelecionado = tblEnderecos.getSelectedRow();
				
				if (indiceSelecionado > 0) {
					//Primeira linha da tabela cont�m o cabe�alho, por isso o '- 1' 
					enderecoSelecionado = enderecos.get(indiceSelecionado - 1); 
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
				}
			}
		});
		
		frmListagemDeEnderecos.getContentPane().add(tblEnderecos);
	}
}
