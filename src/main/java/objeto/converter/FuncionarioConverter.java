package objeto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import net.sf.jasperreports.engine.util.JRStyledText.Run;
import objeto.dao.FuncionarioDao;
import objeto.entidade.Funcionario;

//Não ta pronto
@FacesConverter("funcionarioConverter")
public class FuncionarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			Integer codigo = Integer.parseInt(value);
			FuncionarioDao funcionarioDao = new FuncionarioDao();
			Funcionario funcionario = funcionarioDao.buscarFuncionario(codigo);
			System.out.println(funcionario);
			return funcionario;
		} catch (RuntimeException e) {
			return null;
		}
	}

	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {

		try {
			Funcionario funcionario = (Funcionario) object;
			Integer codigo = funcionario.getId();
			return codigo.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}
}
