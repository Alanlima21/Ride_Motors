package objeto.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import objeto.conexao.Conexao;
import objeto.dao.ClienteDao;
import objeto.dao.FuncionarioDao;
import objeto.dao.ProdutoDao;
import objeto.dao.VendaDao;
import objeto.entidade.Cliente;
import objeto.entidade.Funcionario;
import objeto.entidade.Item;
import objeto.entidade.Produto;
import objeto.entidade.Venda;
import objeto.tratamentoErro.ErroSistema;

@ManagedBean
@SessionScoped
public class VendaBean extends CrudBean<Venda, VendaDao> {

	ProdutoDao produtoDao = new ProdutoDao();
	VendaDao vendaDao = new VendaDao();
	Venda vendaCadastro;
	private Venda venda = new Venda();
	private List<Item> listaItens;
	private List<Produto> listaProdutos;
	private List<Cliente> listaClientes;
	private Funcionario funcionario = new Funcionario();
	private Cliente cliente = new Cliente();

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	@Override
	public VendaDao getDao() {
		if (vendaDao == null) {
			vendaDao = new VendaDao();
		}
		return vendaDao;
	}

	@Override
	public Venda criarNovaEntidade() {
		return new Venda();
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Funcionario> getFuncionarios() throws ErroSistema {
		try {
			FuncionarioDao funcionarioDao = new FuncionarioDao();
			List<Funcionario> listFunc = funcionarioDao.buscar().stream().filter(F -> F.getFuncao().equals("Vendedor"))
					.collect(Collectors.toList());
			return listFunc;
		} catch (ErroSistema ex) {
			throw new ErroSistema("Erro ao buscar Funcionario!", ex);
		}
	}

	public List<Cliente> getClientes() throws ErroSistema {
		try {
			ClienteDao clienteDao = new ClienteDao();
			List<Cliente> cliente = clienteDao.buscar();
			cliente.sort((C1, C2) -> C1.getNome().toUpperCase().compareTo(C2.getNome().toUpperCase()));
			return cliente;
		} catch (ErroSistema ex) {
			throw new ErroSistema("Erro ao buscar cliente!", ex);
		}
	}

	public List<Produto> getProdutos() throws ErroSistema {
		try {
			ProdutoDao produtoDao = new ProdutoDao();
			List<Produto> prod = produtoDao.buscar().stream().filter(p -> p.getQuantidade() > 0)
					.collect(Collectors.toList());
			
			prod.sort((p1, p2) -> p1.getNome().toUpperCase().compareTo(p2.getNome().toUpperCase()));
		
			return prod;
		} catch (ErroSistema ex) {
			throw new ErroSistema("Erro ao buscar produto!", ex);
		}

	}

	public Venda getVendaCadastro() {
		if (vendaCadastro == null) {
			vendaCadastro = new Venda();
			vendaCadastro.setValor(0.00);
		}
		return vendaCadastro;
	}

	public void setVendaCadastro(Venda vendaCadastro) {
		this.vendaCadastro = vendaCadastro;
	}

	// Get e Set da lista de itens
	public List<Item> getListaItens() {
		if (listaItens == null) {
			listaItens = new ArrayList<>();
		}
		return listaItens;
	}

	public void setListaItens(List<Item> listaItens) {
		this.listaItens = listaItens;
	}

	// Método para adicionar o item no carrinho
	public void adicionar(Produto produto) {

		int posicao = -1; // variavel para guardar o item quando for encontrado
		for (int i = 0; i < listaItens.size() && posicao < 0; i++) {
			Item itemTemp = listaItens.get(i);

			if (itemTemp.getProduto().equals(produto) && produto.getQuantidade() > itemTemp.getQuantidade()) {
				posicao = i;
			}
		}
		
		Item item = new Item();
		item.setProduto(produto);
		
		
		if (posicao < 0) {
			item.setQuantidade(1);
			item.setValor(produto.getPreco());
			listaItens.add(item);
			vendaCadastro.setquantidadePedido(item.getQuantidade() + item.getQuantidade());
		} else {
			Item itemTemp = listaItens.get(posicao); // variavel para guardar posição do item encontrado
			item.setQuantidade(itemTemp.getQuantidade() + 1);
			item.setValor(produto.getPreco() * item.getQuantidade());
			listaItens.set(posicao, item);
			vendaCadastro.setquantidadePedido(listaItens.size());
		}
		
		vendaCadastro.setValor(vendaCadastro.getValor() + produto.getPreco());
	}

	// Método para remover item do carrinho
	public void remover(Item item) {

		int posicao = -1; // variavel para guardar o item quando for encontrado
		for (int i = 0; i < listaItens.size() && posicao < 0; i++) {
			Item itemTemp = listaItens.get(i);

			if (itemTemp.getProduto().equals(item.getProduto())) {
				posicao = i;
			}

		}
		if (posicao > -1) {
			listaItens.remove(posicao);
			vendaCadastro.setValor(vendaCadastro.getValor() - item.getValor());
		}

	}

	public void horario() {
		vendaCadastro.setHorario(new Date());
	}

	public void adicionaServico() {
		vendaCadastro.setValor(vendaCadastro.getValor() + venda.getServico());
	}

	public void removeServico() {
		vendaCadastro.setValor(vendaCadastro.getValor() - venda.getServico());
	}

	public void finalizarVenda() throws ErroSistema {
		vendaCadastro.setValor(vendaCadastro.getValor());
		vendaCadastro.setHorario(vendaCadastro.getHorario());
		vendaCadastro.setTotalVendas(vendaCadastro.getquantidadePedido());
		vendaCadastro.setFuncionario(vendaCadastro.getFuncionario());
		vendaCadastro.setCliente(vendaCadastro.getCliente());
		vendaDao.salvar(vendaCadastro);

		vendaCadastro = new Venda();

		vendaCadastro.setValor(0);
		vendaCadastro.setquantidadePedido(0);
		
		// faz a baixa de estoque
		for (int posicao = 0; posicao < listaItens.size(); posicao++) {
			Item itemTemp = listaItens.get(posicao);
			Produto produto = itemTemp.getProduto();

			produto.setQuantidade(produto.getQuantidade() - itemTemp.getQuantidade());
			produtoDao.salvar(produto);
		}
		
		listaItens = new ArrayList<Item>();
		
		// Emite mensagem de aviso, quando o item estiver acabando
		for(Produto p : getProdutos()) {
			if(p.getQuantidade() <= 10) {
				 adicionarMensagem("O produto " + p.getNome() + " está quase acabando !", FacesMessage.SEVERITY_WARN);
			}
		}
		
		adicionarMensagem("Venda realizada com sucesso!", FacesMessage.SEVERITY_INFO);
	}

	public void imprimir() {

		String nomeCliente = cliente.getNome();
		String nomeFuncionario = funcionario.getNome();
		Date data = venda.getDatareport();
		Date data2 = venda.getHorario();

		try {
			
			String Jasper = Faces.getRealPath("/reports/vendas.jasper");

			Map<String, Object> params = new HashMap<>();

			params.put("CLIENTE_NOME", "%" + nomeCliente + "%");
			params.put("FUNCIONARIO_NOME", "%" + nomeFuncionario + "%");
			params.put("DATA_VENDA1", data);
			params.put("DATA_VENDA", data2);

			Connection conexao = Conexao.getConexao();

			JasperReport report = (JasperReport) JRLoader.loadObject(new FileInputStream(Jasper));
			JasperPrint print = JasperFillManager.fillReport(report, params, conexao);
			JasperPrintManager.printReport(print, true);

		} catch (JRException ex) {
			Logger.getLogger(VendaBean.class.getName()).log(Level.SEVERE, null, ex.getMessage());
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		} catch (FileNotFoundException e) {
			adicionarMensagem(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}

}
