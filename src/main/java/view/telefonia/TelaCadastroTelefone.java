package view.telefonia;

import java.awt.EventQueue;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

import controller.telefonia.ClienteController;
import controller.telefonia.TelefoneController;
import model.exception.CampoInvalidoException;
import model.vo.telefonia.Cliente;
import model.vo.telefonia.Telefone;

import javax.swing.JFormattedTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class TelaCadastroTelefone {

	private JFrame frmCadastroDeTelefone;
	private JFormattedTextField txtNumero;
	private ButtonGroup grupoMovel;
	private JLabel lblNumero;
	private JLabel lblMovel;
	private JButton btnCadastrar;
	private MaskFormatter mascaraTelefone;
	private JLabel lblCliente;
	private JCheckBox chckbxMovel;
	private JComboBox cbCliente;
	private List<Cliente> clientes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroTelefone window = new TelaCadastroTelefone();
					window.frmCadastroDeTelefone.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaCadastroTelefone() throws ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws ParseException {
		frmCadastroDeTelefone = new JFrame();
		frmCadastroDeTelefone.setTitle("Cadastro de Telefone");
		frmCadastroDeTelefone.setBounds(100, 100, 349, 177);
		frmCadastroDeTelefone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCadastroDeTelefone.getContentPane().setLayout(null);

		lblNumero = new JLabel("Número:");
		lblNumero.setBounds(15, 14, 65, 14);
		frmCadastroDeTelefone.getContentPane().add(lblNumero);

		lblMovel = new JLabel("Móvel:");
		lblMovel.setBounds(15, 39, 65, 14);
		frmCadastroDeTelefone.getContentPane().add(lblMovel);

		grupoMovel = new ButtonGroup();

		mascaraTelefone = new MaskFormatter("(##)#####-####");
		mascaraTelefone.setValueContainsLiteralCharacters(false);

		txtNumero = new JFormattedTextField(mascaraTelefone);
		txtNumero.setBounds(90, 11, 233, 20);
		frmCadastroDeTelefone.getContentPane().add(txtNumero);

		lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(15, 64, 65, 14);
		frmCadastroDeTelefone.getContentPane().add(lblCliente);
		
		chckbxMovel = new JCheckBox("Sim");
		chckbxMovel.setBounds(86, 35, 97, 23);
		frmCadastroDeTelefone.getContentPane().add(chckbxMovel);

		ClienteController controller = new ClienteController();
		clientes = (ArrayList<Cliente>) controller.consultarTodos();

		cbCliente = new JComboBox(clientes.toArray());
		cbCliente.setToolTipText("Selecione");
		cbCliente.setSelectedIndex(-1);
		cbCliente.setBounds(90, 61, 233, 20);
		frmCadastroDeTelefone.getContentPane().add(cbCliente);

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Telefone novoTelefone = new Telefone();
				try {
					String telefoneSemMascara = (String) mascaraTelefone.stringToValue(txtNumero.getText());
					novoTelefone.setDdd(telefoneSemMascara.substring(0, 2));
					novoTelefone.setNumero(telefoneSemMascara.substring(2));
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Erro ao converter o telefone.", "Erro:",
							JOptionPane.ERROR_MESSAGE);
				}
				novoTelefone.setMovel(chckbxMovel.isSelected());
				Cliente clienteSelecionado = (Cliente) cbCliente.getSelectedItem();
				if (clienteSelecionado != null) {
					novoTelefone.setId(clienteSelecionado.getId());
					novoTelefone.setAtivo(true);
				} else {
					novoTelefone.setAtivo(false);
				}
				TelefoneController controller = new TelefoneController();
				controller.inserir(novoTelefone);

				JOptionPane.showMessageDialog(null, "Telefone cadastrado com sucesso!", "Cadastro com sucesso!",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnCadastrar.setBounds(214, 96, 109, 23);
		frmCadastroDeTelefone.getContentPane().add(btnCadastrar);
	}
}
