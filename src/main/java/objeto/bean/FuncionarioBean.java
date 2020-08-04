package objeto.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import objeto.dao.FuncionarioDao;
import objeto.entidade.Funcionario;

@ManagedBean
@SessionScoped
public class FuncionarioBean extends CrudBean<Funcionario, FuncionarioDao> {

	private FuncionarioDao funcionarioDao;
	private List<Funcionario> listaFuncionario;
	private Funcionario funcionario = new Funcionario();

	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}

	public void setListaFuncionario(List<Funcionario> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}

	@Override
	public FuncionarioDao getDao() {
		if (funcionarioDao == null) {
			funcionarioDao = new FuncionarioDao();
		}
		return funcionarioDao;
	}

	@Override
	public Funcionario criarNovaEntidade() {
		return new Funcionario();
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
