package view.telefonia;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.telefonia.ClienteController;
import controller.telefonia.EnderecoController;
import model.exception.CampoInvalidoException;
import model.exception.CpfJaUtilizadoException;
import model.exception.EnderecoInvalidoException;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Endereco;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class TelaCadastroCliente {

	private JFrame frmCadastroDeCliente;
	private JTextField txtNome;
	private JTextField txtCpf;
	private JLabel lblNome;
	private JLabel lblCpf;
	private JLabel lblEnderecos;
	private JButton btnSalvar;
	private ArrayList<Endereco> enderecos;
	private JComboBox cbEnderecos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroCliente window = new TelaCadastroCliente();
					window.frmCadastroDeCliente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaCadastroCliente() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCadastroDeCliente = new JFrame();
		frmCadastroDeCliente.setTitle("Cadastro de Cliente");
		frmCadastroDeCliente.setBounds(100, 100, 380, 240);
		frmCadastroDeCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCadastroDeCliente.getContentPane().setLayout(null);
		
		txtNome = new JTextField();
		txtNome.setBounds(15, 15, 45, 14);
		frmCadastroDeCliente.getContentPane().add(lblNome);
		
		lblNome = new JLabel("Nome:");
		lblNome.setBounds(15, 15, 45, 14);
		frmCadastroDeCliente.getContentPane().add(lblNome);
		
		txtCpf = new JTextField();
		txtCpf.setBounds(60, 12, 300, 20);
		frmCadastroDeCliente.getContentPane().add(txtCpf);
		txtCpf.setColumns(10);
		
		lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(15, 15, 45, 14);
		frmCadastroDeCliente.getContentPane().add(lblCpf);
		
		lblEnderecos = new JLabel("Estado:");
		lblEnderecos.setBounds(15, 140, 45, 14);
		frmCadastroDeCliente.getContentPane().add(lblEnderecos);
		
		EnderecoController controller = new EnderecoController();
		enderecos = (ArrayList<Endereco>) controller.consultarTodos();
		
		cbEnderecos = new JComboBox(enderecos.toArray());
		cbEnderecos.setToolTipText("Selecione");
		cbEnderecos.setSelectedIndex(-1);
		cbEnderecos.setBounds(60, 136, 300, 22);
		frmCadastroDeCliente.getContentPane().add(cbEnderecos);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cliente cliente = new Cliente();
				cliente.setNome(txtNome.getText());
				cliente.setCpf(txtCpf.getText());
						
				ClienteController controller = new ClienteController();
				try {
					controller.inserir(cliente);
				} catch (CampoInvalidoException e) {
					JOptionPane.showMessageDialog(null, 
							"Preencha os seguintes campos: \n" + e.getMessage(), 
							"Atenção", JOptionPane.WARNING_MESSAGE);
				} catch (CpfJaUtilizadoException e) {
					JOptionPane.showMessageDialog(null, 
							"O CPF informado já está cadastrado: \n" + e.getMessage(), 
							"Atenção", JOptionPane.WARNING_MESSAGE);
					e.printStackTrace();
				} catch (EnderecoInvalidoException e) {
					JOptionPane.showMessageDialog(null, 
							"O endereço é inválido: \n" + e.getMessage(), 
							"Atenção", JOptionPane.WARNING_MESSAGE);
					e.printStackTrace();
				}
			}
		});
		btnSalvar.setBounds(130, 170, 100, 23);
		frmCadastroDeCliente.getContentPane().add(btnSalvar);
		

	
	}
}
