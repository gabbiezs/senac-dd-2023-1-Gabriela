package view.telefonia;

import java.awt.EventQueue;
import java.text.ParseException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import controller.telefonia.ClienteController;
import controller.telefonia.EnderecoController;
import model.exception.CampoInvalidoException;
import model.exception.CpfJaUtilizadoException;
import model.exception.EnderecoInvalidoException;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Endereco;

import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class TelaCadastroCliente {

	private JFrame frmNovoCliente;
	private JTextField txtNome;
	private JLabel lblNome;
	private JLabel lblCpf;
	private JFormattedTextField txtCpf;
	private JComboBox cbEndereco;
	
	private MaskFormatter mascaraCpf;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroCliente window = new TelaCadastroCliente();
					window.frmNovoCliente.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws java.text.ParseException 
	 */
	public TelaCadastroCliente() throws java.text.ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws java.text.ParseException 
	 */
	private void initialize() throws java.text.ParseException {
		frmNovoCliente = new JFrame();
		frmNovoCliente.setTitle("Novo Cliente");
		frmNovoCliente.setBounds(100, 100, 453, 220);
		frmNovoCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNovoCliente.getContentPane().setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(32, 30, 46, 14);
		frmNovoCliente.getContentPane().add(lblNome); // (ContentPane) add. no frame no design.
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(32, 70, 46, 14);
		frmNovoCliente.getContentPane().add(lblCpf);
		
		JLabel lblEndereco = new JLabel("Endereco:");
		lblEndereco.setBounds(32, 110, 56, 14);
		frmNovoCliente.getContentPane().add(lblEndereco);
		
		txtNome = new JTextField();
		txtNome.setBounds(99, 27, 295, 20);
		frmNovoCliente.getContentPane().add(txtNome);
		txtNome.setColumns(10);
		
		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setValueContainsLiteralCharacters(false);
		} catch (ParseException e) {
			e.printStackTrace();
		}
				
		txtCpf = new JFormattedTextField(mascaraCpf);
		txtCpf.setBounds(99, 67, 295, 20);
		frmNovoCliente.getContentPane().add(txtCpf);
		
		EnderecoController endController = new EnderecoController();
		List<Endereco> enderecosCadastrados = endController.consultarTodos();
		
		cbEndereco = new JComboBox(enderecosCadastrados.toArray());
		cbEndereco.setBounds(98, 106, 295, 22);
		frmNovoCliente.getContentPane().add(cbEndereco);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(Color.BLACK);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cliente novoCliente = new Cliente();
				novoCliente.setNome(txtNome.getText());
				
				try {
					String cpfSemMascara = (String) mascaraCpf.stringToValue(txtCpf.getText());
				novoCliente.setCpf(cpfSemMascara);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao converter o  CPF", "Erro", JOptionPane.ERROR_MESSAGE);
				}
				
				novoCliente.setEndereco((Endereco) cbEndereco.getSelectedItem());
				
				ClienteController controller = new ClienteController();
				
				try {
					controller.inserir(novoCliente);
					JOptionPane.showMessageDialog(null, "Cliente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				} catch (CpfJaUtilizadoException | EnderecoInvalidoException | CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSalvar.setBounds(305, 147, 89, 23);
		frmNovoCliente.getContentPane().add(btnSalvar);
	}
}
