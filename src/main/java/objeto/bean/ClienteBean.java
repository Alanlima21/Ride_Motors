package objeto.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import objeto.dao.ClienteDao;
import objeto.entidade.Cliente;

@ManagedBean
@SessionScoped
public class ClienteBean extends CrudBean<Cliente, ClienteDao >{

	private ClienteDao clienteDao;
	private List<Cliente> listaClientes; 
	private Cliente cliente = new Cliente();
	
	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	@Override
	public ClienteDao getDao() {
		if(clienteDao == null) {
			clienteDao = new ClienteDao();
		}
		return clienteDao;
	}

	@Override
	public Cliente criarNovaEntidade() {
		return new Cliente();
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
