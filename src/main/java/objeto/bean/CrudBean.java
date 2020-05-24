package objeto.bean;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import objeto.dao.CrudDao;
import objeto.tratamentoErro.ErroSistema;

@SuppressWarnings("rawtypes")
public abstract  class CrudBean <E, D extends CrudDao>{

	private String estadoTela = "buscar"; //inserir, editar, buscar
	
	private E entidade;
	private List<E> entidades;
	
	public void novo() {
		entidade = criarNovaEntidade();
		mudarParaInseri();
	}
	
	@SuppressWarnings("unchecked")
	public void salvar() {
		try {
			getDao().salvar(entidade);
			entidade = criarNovaEntidade();
			adicionarMensagem("Salvo com sucesso!", FacesMessage.SEVERITY_INFO);
			mudarParaBusca();
		} catch (ErroSistema ex) {
			Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void editar(E entidade) {
		this.entidade = entidade;
		mudarParaedita();
	}
	
	@SuppressWarnings("unchecked")
	public void deletar(E entidade) {
		try {
			getDao().deletar(entidade);
			entidades.remove(entidade);
			adicionarMensagem("deletado com sucesso!", FacesMessage.SEVERITY_INFO);
		} catch (ErroSistema ex) {
			Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	@SuppressWarnings("unchecked")
	public void buscar() {
		if(isBusca() == false) {
			mudarParaBusca();
			return;
		}
		try {
			entidades = getDao().buscar();
			if(entidades == null || entidades.size() < 1) {
				adicionarMensagem("Não temos nada cadastrado", FacesMessage.SEVERITY_WARN);
			}
		} catch (ErroSistema ex) {
			Logger.getLogger(CrudBean.class.getName()).log(Level.SEVERE, null, ex);
			adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
	}
	
	public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
		FacesMessage fm = new FacesMessage(tipoErro, mensagem, null);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	
	//getters e setters
	
	public E getEntidade() {
		return entidade;
	}	
	
	public void setEntidade(E entidade) {
		this.entidade = entidade;
	}
	
	public List<E> getEntidades() {
		return entidades;
	}
	
	public void setEntidades(List<E> entidades) {
		this.entidades = entidades;
	}
	
	
	// Responsavel por criar os metodos nas classes bean
	public abstract D getDao();
	public abstract E criarNovaEntidade();
	
	
	
	
	//metodos para controle de tela
	public boolean isInseri() {
		return "inserir".equals(estadoTela);	
	}
	public boolean isEdita() {
		return "editar".equals(estadoTela);	
	}
	public boolean isBusca() {
		return "buscar".equals(estadoTela);	
	}
	public void mudarParaInseri() {
		estadoTela = "inserir";
	}
	public void mudarParaedita() {
		estadoTela = "editar";
	}
	public void mudarParaBusca() {
		estadoTela = "buscar";
	}
}
