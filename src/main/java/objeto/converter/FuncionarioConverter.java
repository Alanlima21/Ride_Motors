package objeto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import objeto.dao.FuncionarioDao;
import objeto.entidade.Funcionario;

@FacesConverter(forClass = Funcionario.class)
public class FuncionarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		FuncionarioDao funcionarioDao = new FuncionarioDao();
		Integer codigo = Integer.parseInt(value);
		if (value != null && !value.isEmpty()) {
			Funcionario funcionario = funcionarioDao.buscarFuncionario(codigo);
			return funcionario;
		}
		return codigo;
		/*
		 * try { Integer codigo = Integer.parseInt(value); FuncionarioDao funcionarioDao
		 * = new FuncionarioDao(); Funcionario funcionario =
		 * funcionarioDao.buscarFuncionario(codigo); return funcionario; } catch
		 * (RuntimeException e) { return null; }
		 */
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {

		if (object != null) {
			Funcionario f = (Funcionario) object;
			System.out.println(f.getId().toString());
			return f.getId() != null && f.getId() > 0 ? f.getId().toString() : null;
		}
		return null;
		/*
		 * try { Funcionario funcionario = (Funcionario) object; Integer codigo =
		 * funcionario.getId(); return codigo.toString(); } catch (RuntimeException ex)
		 * { return null; }
		 */
	}
}