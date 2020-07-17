package objeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import objeto.conexao.Conexao;
import objeto.tratamentoErro.ErroSistema;

public class LoginDao {

	public boolean login(String login, String senha) throws ErroSistema {
		Connection conexao = Conexao.getConexao();

		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean check = false;

		try {
			ps = conexao.prepareStatement("SELECT * FROM usuario WHERE cpf_usua = ? and senha_usua = ? ");
			ps.setString(1, login);
			ps.setString(2, senha);

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
