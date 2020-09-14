package objeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import objeto.conexao.Conexao;
import objeto.entidade.CadUsuario;
import objeto.tratamentoErro.ErroSistema;

public class CadUsuarioDao {
	Conexao conexao = new Conexao();

	public boolean salvar(CadUsuario cadUsuario) throws ErroSistema {
		PreparedStatement ps= null;
		try {
			Connection conexao = Conexao.getConexao();
		
			if (cadUsuario.getId() == null) {

				ps = conexao.prepareStatement(
						"INSERT INTO usuario(nome_usua, sobrenome_usua, cpf_usua, senha_usua) VALUES (?,?,?,?)");
			} else {
				ps = conexao.prepareStatement(
						"update usuario set nome_usua=?, sobrenome_usua=?, cpf_usua=?, senha_usua=? where id_usua=?");
				ps.setInt(6, cadUsuario.getId());
			}
			
			ps.setString(1, cadUsuario.getNome());
			ps.setString(2, cadUsuario.getSobrenome());
			ps.setString(3, cadUsuario.getCpf());
			ps.setString(4, cadUsuario.getSenha());
			ps.execute();
			return true;

		} catch (Exception ex) {
			throw new ErroSistema("Erro ao salvar Usuário!", ex);
		} finally {
			Conexao.fecharPreparedStatement(ps);
			Conexao.fecharConexao();
		}
	}

	public boolean getCpf(String cpf) throws ErroSistema {
		Connection conexao = Conexao.getConexao();

		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean check = false;

		try {
			ps = conexao.prepareStatement("SELECT * FROM usuario WHERE cpf_usua = ? ");
			ps.setString(1, cpf);

			rs = ps.executeQuery();
			if (rs.next()) {
				check = true;
			}

		} catch (Exception ex) {
			throw new ErroSistema("Erro ao salvar Usuário!", ex);
		} finally {
			Conexao.fecharResultset(rs);
			Conexao.fecharPreparedStatement(ps);
			Conexao.fecharConexao();
		}
		return check;

	}
}
