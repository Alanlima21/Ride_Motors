package objeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import objeto.conexao.Conexao;
import objeto.entidade.Cliente;
import objeto.tratamentoErro.ErroSistema;

public class ClienteDao implements CrudDao<Cliente> {

	@Override
	public void salvar(Cliente cliente) throws ErroSistema {
		try {
		Connection conexao = Conexao.getConexao();
		PreparedStatement ps;
		if(cliente.getId() == null) {
		
				ps = conexao.prepareStatement("INSERT INTO cliente(nome_clie, cpf_clie, email_clie, data_clie) VALUES (?,?,?,?)");
		}else {
			ps =conexao.prepareStatement("update cliente set nome_clie=?, cpf_clie=?, email_clie=?, data_clie=? where id_clie=?");
			ps.setInt(5, cliente.getId());
		}
		ps.setString(1, cliente.getNome());
		ps.setString(2, cliente.getCpf());
		ps.setString(3, cliente.getEmail());
		ps.setString(4, cliente.getData());
		ps.execute();
		Conexao.fecharConexao();
	} catch (Exception ex) {
		throw new ErroSistema("Erro ao salvar Cliente!", ex);
	}
	}

	@Override
	public void deletar(Cliente cliente) throws ErroSistema {
		try {
		Connection conexao = Conexao.getConexao();
		PreparedStatement ps;
		ps = conexao.prepareStatement("delete from cliente where id_clie=?");
		ps.setInt(1, cliente.getId());
		ps.execute();
		} catch(SQLException ex){
			throw new ErroSistema("Erro ao deletar Cliente!", ex);
		}
		
	}

	@Override
	public List<Cliente> buscar() throws ErroSistema {
		Connection conexao = Conexao.getConexao();
		try {
			PreparedStatement ps = conexao.prepareStatement("select * from cliente");
			ResultSet resultset = ps.executeQuery();
			List<Cliente> clientes = new ArrayList<>();
			while (resultset.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(resultset.getInt("id_clie"));
				cliente.setNome(resultset.getString("nome_clie"));
				cliente.setCpf(resultset.getString("cpf_clie"));
				cliente.setEmail(resultset.getString("email_clie"));
				cliente.setData(resultset.getString("data_clie"));
				clientes.add(cliente);
			}
			Conexao.fecharConexao();
			return clientes;
		} catch (SQLException ex) {
			throw new ErroSistema("Erro ao buscar Cliente!", ex);
		}

	}
	
}
