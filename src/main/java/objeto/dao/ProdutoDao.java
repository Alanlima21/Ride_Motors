package objeto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import objeto.conexao.Conexao;

import objeto.entidade.Produto;
import objeto.tratamentoErro.ErroSistema;

public class ProdutoDao implements CrudDao<Produto> {

	@Override
	public void salvar(Produto produto) throws ErroSistema {
		PreparedStatement ps = null;
		try {
			Connection conexao = Conexao.getConexao();

			if (produto.getId() == null) {
				ps = conexao.prepareStatement(
						"INSERT INTO produto (nome_prod, fornecedor_prod, preco_prod, quantidade_prod, telefone_prod) Values (?,?,?,?,?)");
			} else {
				ps = conexao.prepareStatement(
						"update produto set nome_prod=?, fornecedor_prod=?, preco_prod=?, quantidade_prod=?, telefone_prod=? where id_prod=?");
				ps.setInt(6, produto.getId());
			}
			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getFornecedor());
			ps.setDouble(3, produto.getPreco());
			ps.setInt(4, produto.getQuantidade());
			ps.setString(5, produto.getTelefone());
			ps.execute();

		} catch (SQLException ex) {
			throw new ErroSistema("Erro ao salvar Produto!", ex);
		} finally {
			Conexao.fecharPreparedStatement(ps);
			Conexao.fecharConexao();
		}
	}

	@Override
	public void deletar(Produto produto) throws ErroSistema {
		PreparedStatement ps = null;
		try {
			Connection conexao = Conexao.getConexao();
			ps = conexao.prepareStatement("delete from produto where id_prod=?");
			ps.setInt(1, produto.getId());
			ps.execute();
		} catch (SQLException ex) {
			throw new ErroSistema("Erro ao deletar Produto!", ex);
		} finally {
			Conexao.fecharPreparedStatement(ps);
			Conexao.fecharConexao();
		}

	}

	@Override
	public List<Produto> buscar() throws ErroSistema {
		PreparedStatement ps = null;
		ResultSet resultset = null;
		try {
			Connection conexao = Conexao.getConexao();
			ps = conexao.prepareStatement("select * from produto");
			resultset = ps.executeQuery();
			List<Produto> produtos = new ArrayList<>();
			while (resultset.next()) {
				Produto produto = new Produto();
				produto.setId(resultset.getInt("id_prod"));
				produto.setNome(resultset.getString("nome_prod"));
				produto.setFornecedor(resultset.getString("fornecedor_prod"));
				produto.setPreco(resultset.getDouble("preco_prod"));
				produto.setQuantidade(resultset.getInt("quantidade_prod"));
				produto.setTelefone(resultset.getString("telefone_prod"));
				produtos.add(produto);
			}
			return produtos;
		} catch (SQLException ex) {
			throw new ErroSistema("Erro ao buscar Produto!", ex);
		} finally {
			Conexao.fecharPreparedStatement(ps);
			Conexao.fecharResultset(resultset);
			Conexao.fecharConexao();
		}

	}

	public List<Produto> buscarProd(List<Produto> lista) throws ErroSistema {
		PreparedStatement ps = null;
		ResultSet resultset = null;
		try {
			Connection conexao = Conexao.getConexao();
			ps = conexao.prepareStatement("select * from produto");
			resultset = ps.executeQuery();
			List<Produto> produtos = new ArrayList<>();
			while (resultset.next()) {
				Produto produto = new Produto();

				produto.setNome(resultset.getString("nome_prod"));
				produto.setFornecedor(resultset.getString("fornecedor_prod"));
				produto.setPreco(resultset.getDouble("preco_prod"));
				produto.setQuantidade(resultset.getInt("quantidade_prod"));

				produtos.add(produto);
			}
			return produtos;
		} catch (SQLException ex) {
			throw new ErroSistema("Erro ao buscar Produto!", ex);
		} finally {
			Conexao.fecharPreparedStatement(ps);
			Conexao.fecharResultset(resultset);
			Conexao.fecharConexao();
		}
	}
}
