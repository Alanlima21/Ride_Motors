package objeto.dao;

import java.util.List;

import objeto.tratamentoErro.ErroSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import objeto.conexao.Conexao;

import objeto.entidade.Venda;

public class VendaDao implements CrudDao<Venda> {

	@Override
	public void salvar(Venda venda) throws ErroSistema {
		PreparedStatement ps = null;
		try {
			Connection conexao = Conexao.getConexao();

			if (venda.getId() == null) {
				ps = conexao
						.prepareStatement("INSERT INTO venda(quantidade_vend, valor_vend, data_vend) Values(?,?,?)");
			} else {
				new ErroSistema("Quantidade insuficiente no estoque!");
			}
			ps.setInt(1, venda.getTotalVendas());
			ps.setDouble(2, venda.getValor());
			ps.setDate(3, new java.sql.Date(venda.getHorario().getTime()));
			// ps.setInt(4, venda.getFuncionario());
			// ps.setInt(5, venda.getCliente());
			ps.execute();
		} catch (SQLException ex) {
			throw new ErroSistema("Erro ao salvar Venda!", ex);
		} finally {
			Conexao.fecharPreparedStatement(ps);
			Conexao.fecharConexao();
		}
	}

	@Override
	public void deletar(Venda venda) throws ErroSistema {

	}

	@Override
	public List<Venda> buscar() throws ErroSistema {
		PreparedStatement ps = null;
		ResultSet resultset = null;
		try {
			Connection conexao = Conexao.getConexao();
			ps = conexao.prepareStatement("select * from venda");
			resultset = ps.executeQuery();
			List<Venda> vendas = new ArrayList<>();
			while (resultset.next()) {
				Venda venda = new Venda();
				venda.setId(resultset.getInt("id_vend"));
				venda.setTotalVendas(resultset.getInt("quantidade_vend"));
				vendas.add(venda);
			}
			return vendas;
		} catch (SQLException ex) {
			throw new ErroSistema("Erro ao buscar vendas!", ex);
		} finally {
			Conexao.fecharPreparedStatement(ps);
			Conexao.fecharResultset(resultset);
			Conexao.fecharConexao();
		}
	}

}
