package objeto.dao;

import objeto.tratamentoErro.ErroSistema;
import java.util.List;

public interface CrudDao <E> { // E representa a minha entidade
	
	public void salvar(E entidade) throws ErroSistema;
	public void deletar(E entidade) throws ErroSistema;
	public List<E> buscar() throws ErroSistema;
	
}
