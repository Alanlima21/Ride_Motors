package objeto.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import objeto.conexao.Conexao;
import objeto.dao.ProdutoDao;
import objeto.entidade.Produto;

@ManagedBean
@SessionScoped
public class ProdutoBean extends CrudBean<Produto, ProdutoDao> {

	ProdutoDao produtoDao = new ProdutoDao();

	private Produto produto = new Produto();
	private List<Produto> listaProdutos;
	private List<Produto> lista = new ArrayList<Produto>();

	public List<Produto> getLista() {
		 return lista;
	}
	
	public void setLista(List<Produto> lista) {
		this.lista = lista;
	}
	
	@Override
	public ProdutoDao getDao() {
		if (produtoDao == null) {
			produtoDao = new ProdutoDao();
		}
		return produtoDao;
	}

	@Override
	public Produto criarNovaEntidade() {
		return new Produto();
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}
	
	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public void imprimir() {

		try {

			String nome = produto.getNome();
			String fornecedor = produto.getFornecedor();

			String Jasper = Faces.getRealPath("/reports/produtos.jasper");

			Map<String, Object> params = new HashMap<>();
			
			
			if (nome == null) {
				params.put("PRODUTO_NOME", "%%");
			} else {
				params.put("PRODUTO_NOME", "%" + nome + "%");
			}
			if (fornecedor == null) {
				params.put("PRODUTO_FORNECEDOR", "%%");
			} else {
				params.put("PRODUTO_FORNECEDOR", "%" + fornecedor + "%");
			}
			
		
			Connection conexao = Conexao.getConexao();

			JasperReport report = (JasperReport) JRLoader.loadObject(new FileInputStream(Jasper));
			JasperPrint print = JasperFillManager.fillReport(report, params, conexao);
			JasperPrintManager.printReport(print, true);

		} catch (JRException ex) {
			Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, ex.getMessage());
		}catch(FileNotFoundException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),null));
		}
	}
}