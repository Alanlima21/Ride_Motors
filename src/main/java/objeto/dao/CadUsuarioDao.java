package objeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;


import objeto.conexao.Conexao;
import objeto.entidade.CadUsuario;
import objeto.tratamentoErro.ErroSistema;

public class CadUsuarioDao  {
	Conexao conexao = new Conexao();

	public boolean salvar(CadUsuario cadUsuario) throws ErroSistema {
		try {
			Connection conexao = Conexao.getConexao();
			PreparedStatement ps;
			if(cadUsuario.getId() == null) {
			
					ps = conexao.prepareStatement("INSERT INTO usuario(nome_usua, sobrenome_usua, cpf_usua, senha_usua) VALUES (?,?,?,?)");
			}else {
				ps =conexao.prepareStatement("update usuario set nome_usua=?, sobrenome_usua=?, cpf_usua=?, senha_usua=? where id_usua=?");
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
		}
	}

}
