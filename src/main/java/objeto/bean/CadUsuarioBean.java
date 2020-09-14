package objeto.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import objeto.dao.CadUsuarioDao;
import objeto.entidade.CadUsuario;
import objeto.tratamentoErro.ErroSistema;

@ManagedBean
@SessionScoped
public class CadUsuarioBean {

	CadUsuarioDao cadUsuarioDao = new CadUsuarioDao();
	private CadUsuario cadUsuario = new CadUsuario();

	public CadUsuario getCadUsuario() {
		return cadUsuario;
	}

	public void setCadUsuario(CadUsuario cadUsuario) {
		this.cadUsuario = cadUsuario;
	}

	public String salvar() throws ErroSistema {

		if (!testNome(cadUsuario)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo nome n�o pode conter n�meros!", null));

			return "cadastro.xhtml";
		}
		if (!testSobrenome(cadUsuario)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "O campo sobrenome n�o pode conter n�meros!", null));

			return "cadastro.xhtml";
		}

		if (cadUsuarioDao.getCpf(cadUsuario.getCpf())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este CPF j� est� cadastrado!", null));

			return "cadastro.xhtml";
		}

		if (cadUsuario.getSenha().equals(cadUsuario.getConfSenha())) {
			CadUsuarioDao cadUsuarioDao = new CadUsuarioDao();
			cadUsuarioDao.salvar(cadUsuario);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usu�rio cadastrado com sucesso!!", null));
			return "index.xhtml";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Confirma��o de senha inv�lida!", null));
			return "cadastro.xhtml";
		}
	}

	public boolean testNome(CadUsuario text) {
		return text.getNome().matches("[^\\d]+");
	}

	public boolean testSobrenome(CadUsuario text) {
		return text.getSobrenome().matches("[^\\d]+");
	}

}
