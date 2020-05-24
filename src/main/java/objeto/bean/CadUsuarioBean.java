package objeto.bean;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import objeto.dao.CadUsuarioDao;
import objeto.entidade.CadUsuario;
import objeto.entidade.Login;
import objeto.tratamentoErro.ErroSistema;


@ManagedBean
@SessionScoped
public class CadUsuarioBean  {

	CadUsuarioDao cadUsuarioDao;
	private CadUsuario cadUsuario = new CadUsuario();
	
	
	public CadUsuario getCadUsuario() {
		return cadUsuario;
	}
	
	public void setCadUsuario(CadUsuario cadUsuario) {
		this.cadUsuario = cadUsuario;
	}
	
	public String salvar() throws ErroSistema {
		
		if(cadUsuario.getSenha().equals(cadUsuario.getConfSenha())) {
		CadUsuarioDao cadUsuarioDao = new CadUsuarioDao();
		cadUsuarioDao.salvar(cadUsuario);
		
			FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Usuário cadastrado com sucesso!!", null));
			return "index.xhtml";
		}else {
			FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Confirmação de senha inválida!", 
                   null));
			return "cadastro.xhtml";
		}
	}
	
}
