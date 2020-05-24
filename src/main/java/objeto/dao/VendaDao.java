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



public class VendaDao implements CrudDao<Venda>{

	
	@Override
	public void salvar(Venda venda) throws ErroSistema {
		
		try {
			Connection conexao = Conexao.getConexao();
			PreparedStatement ps = null;
			if (venda.getId() == null) {
				ps = conexao.prepareStatement("INSERT INTO venda(quantidade_vend, valor_vend, data_vend) Values(?,?,?)");
	}else {
		new ErroSistema("Quantidade insuficiente no estoque!");
	}
			ps.setInt(1, venda.getTotalVendas());
			ps.setDouble(2, venda.getValor());
			ps.setDate(3, new java.sql.Date( venda.getHorario().getTime()));
			//ps.setInt(4, venda.getFuncionario());
			//ps.setInt(5, venda.getCliente());
			ps.execute();
			Conexao.fecharConexao();
		}catch (SQLException ex) {

			throw new ErroSistema("Erro ao salvar Venda!", ex);
	}
	}
	
	@Override
	public void deletar(Venda venda) throws ErroSistema {
		
		
	}

	@Override
	public List<Venda> buscar() throws ErroSistema {
		Connection conexao = Conexao.getConexao();
		try {
			PreparedStatement ps = conexao.prepareStatement("select * from venda");
			ResultSet resultset = ps.executeQuery();
			List<Venda> vendas = new ArrayList<>();
			while (resultset.next()) {
				Venda venda = new Venda();
				venda.setId(resultset.getInt("id_vend"));
				venda.setTotalVendas(resultset.getInt("quantidade_vend"));
				vendas.add(venda);
			}
			Conexao.fecharConexao();
			return vendas;
		} catch (SQLException ex) {
			throw new ErroSistema("Erro ao buscar vendas!", ex);
		}
	}
	

}
