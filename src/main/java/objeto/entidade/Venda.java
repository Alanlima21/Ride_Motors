package objeto.entidade;

import java.util.Date;

public class Venda {

	private Integer id;
	private int quantidadePedido;
	private Double valor;
	private int funcionario;
	private int cliente;
	private int totalVendas;
	public Date horario;
	private double servico;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getquantidadePedido() {
		return quantidadePedido;
	}

	public void setquantidadePedido(int quantidadePedido) {
		this.quantidadePedido = quantidadePedido;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		 this.valor = valor;
	}
	
	public int getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(int funcionario) {
		this.funcionario = funcionario;
	}
	
	public int getCliente() {
		return cliente;
	}
	
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	
	public int getTotalVendas() {
		return totalVendas;
	}
	
	public void setTotalVendas(int totalVendas) {
		this.totalVendas = totalVendas;
	}
	
	public void incrementaEstoque() {
		this.quantidadePedido++;
	}
	
	public void diminuiEstoque() {
		this.quantidadePedido--;
	}

	public Date getHorario() {
		return horario;
	}
	
	public void setHorario(Date horario) {
		this.horario = horario;
	}
	
	public Double getServico() {
		return servico;
	}
	
	public void setServico(Double servico) {
		this.servico = servico;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
