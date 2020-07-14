package objeto.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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

	FuncionarioDao funcionarioDao;
	ClienteDao clienteDao;
	ProdutoDao produtoDao = new ProdutoDao();

	Cliente cliente = new Cliente();
	Produto produto;
	VendaDao vendaDao = new VendaDao();

	Funcionario funcionario;
	Venda vendaCadastro;

	private Venda venda = new Venda();
	private List<Item> listaItens;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	private List<Produto> listaProdutos;

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
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

	public List<Funcionario> getFuncionarios() throws ErroSistema {
		try {
			FuncionarioDao funcionarioDao = new FuncionarioDao();
			return funcionarioDao.buscar();
		} catch (ErroSistema ex) {
			throw new ErroSistema("Erro ao buscar Funcionario!", ex);
		}
	}

	public List<Cliente> getClientes() throws ErroSistema {
		try {
			ClienteDao clienteDao = new ClienteDao();
			return clienteDao.buscar();
		} catch (ErroSistema ex) {
			throw new ErroSistema("Erro ao buscar cliente!", ex);
		}
	}

	public List<Produto> getProdutos() throws ErroSistema {
		try {
			ProdutoDao produtoDao = new ProdutoDao();
			return produtoDao.buscar();
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
		} else {
			Item itemTemp = listaItens.get(posicao); // variavel para guardar posição do item encontrado
			item.setQuantidade(itemTemp.getQuantidade() + 1);
			item.setValor(produto.getPreco() * item.getQuantidade());
			listaItens.set(posicao, item);
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
		vendaCadastro.setTotalVendas(vendaCadastro.getTotalVendas() + 1);

		/*
		 * funcionario que vendeu
		 * vendaCadastro.setFuncionario(funcionarioBean.getEntidade().getId()); pegar o
		 * cliente que comprou vendaCadastro.setCliente(2); FuncionarioDao
		 * funcionarioDao = new FuncionarioDao(); funcionarioDao.buscarFuncionario(2);
		 * vendaCadastro.setFuncionario(funcionario.getId());
		 */
		vendaDao.salvar(vendaCadastro);

		vendaCadastro = new Venda();
		vendaCadastro.setValor(0);

		// faz a baixa de estoque
		for (int posicao = 0; posicao < listaItens.size(); posicao++) {
			Item itemTemp = listaItens.get(posicao);
			Produto produto = itemTemp.getProduto();

			produto.setQuantidade(produto.getQuantidade() - itemTemp.getQuantidade());
			produtoDao.salvar(produto);
		}

		listaItens = new ArrayList<Item>();
		adicionarMensagem("Venda realizada com sucesso!", FacesMessage.SEVERITY_INFO);

	}
}
