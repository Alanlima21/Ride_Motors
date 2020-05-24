package objeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import objeto.conexao.Conexao;
import objeto.entidade.Funcionario;
import objeto.tratamentoErro.ErroSistema;

public class FuncionarioDao implements CrudDao<Funcionario> {

	@Override
	public void salvar(Funcionario funcionario) throws ErroSistema {
		try {
			Connection conexao = Conexao.getConexao();
			PreparedStatement ps;
			if(funcionario.getId() == null) {
				ps = conexao.prepareStatement("INSERT INTO funcionario (nome_func, cpf_func, telefone_func, salario_func, funcao_func) Values(?,?,?,?,?)");
				
			}else {
				ps = conexao.prepareStatement("update funcionario set nome_func=?, cpf_func=?, telefone_func=?, salario_func=?, funcao_func=? where id_func=?");
				ps.setInt(6, funcionario.getId());
			}
			ps.setString(1, funcionario.getNome());
			ps.setString(2, funcionario.getCpf());
			ps.setString(3, funcionario.getTelefone());
			ps.setDouble(4, funcionario.getSalario());
			ps.setString(5, funcionario.getFuncao());
			ps.execute();
			Conexao.fecharConexao();
		}catch (Exception ex) {
			throw new ErroSistema("Erro ao salvar Funcionario!", ex);
		}
		
	}

	@Override
	public void deletar(Funcionario funcionario) throws ErroSistema {
		try {
			Connection conexao = Conexao.getConexao();
			PreparedStatement ps;
			ps = conexao.prepareStatement("delete from funcionario where id_func=?");
			ps.setInt(1, funcionario.getId());
			ps.execute();
		}catch (Exception ex) {
			throw new ErroSistema("Erro ao deletar Funcionario!", ex);
		}

	}

	@Override
	public List<Funcionario> buscar() throws ErroSistema {
		Connection conexao = Conexao.getConexao();
		try {
			PreparedStatement ps = conexao.prepareStatement("select * from funcionario");
			ResultSet resultset = ps.executeQuery();
			List<Funcionario> funcionarios = new ArrayList<>();
			while(resultset.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultset.getInt("id_func"));
				funcionario.setNome(resultset.getString("nome_func"));
				funcionario.setCpf(resultset.getString("cpf_func"));
				funcionario.setTelefone(resultset.getString("telefone_func"));
				funcionario.setSalario(resultset.getDouble("salario_func"));
				funcionario.setFuncao(resultset.getString("funcao_func"));
				funcionarios.add(funcionario);	
			}
			Conexao.fecharConexao();
			return funcionarios;
		}catch (SQLException ex) {
			throw new ErroSistema("Erro ao buscar Funcionario!", ex);
		
	}
	}
	public Funcionario buscarFuncionario(int codigo) throws ErroSistema {
		Connection conexao = Conexao.getConexao();

		PreparedStatement ps;
		 
		try {
			
			ps = conexao.prepareStatement("SELECT nome_func FROM funcionario where id_func = ? ");
			ps.setInt(1, codigo);
			ResultSet rs =  ps.executeQuery();
			Funcionario funcionario = new Funcionario();
			if(rs.next()) {
			
			funcionario.setNome(rs.getString("nome_func"));
			System.out.println(funcionario.getNome());
			
			}
			return funcionario;
	}catch (Exception ex) {
		throw new ErroSistema("Erro ao buscar funcionario!", ex);
	}
		
}
}