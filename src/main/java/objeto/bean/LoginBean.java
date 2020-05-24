package objeto.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import objeto.dao.LoginDao;
import objeto.entidade.Login;
import objeto.tratamentoErro.ErroSistema;

@ManagedBean
@SessionScoped
public class LoginBean {
	
	Login login = new Login();
	
	LoginDao loginDao = new LoginDao();
	
	public Login getLogin() {
		return login;
	}
	
	public void setLogin(Login login) {
		this.login = login;
	}

	public String logar() throws ErroSistema {
		if(loginDao.login(login.getLogin(), login.getSenha())) {
			return "home.xhtml";
		}else {
			FacesContext.getCurrentInstance().addMessage(
			        null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuario ou senha incorretos","Erro"));
			 
			  FacesContext.getCurrentInstance()
			      .getExternalContext()
			      .getFlash().setKeepMessages(true);
			  return "index.xhtml?faces-redirect=true";
		}
	}
}
