package objeto.entidade;

import java.text.DecimalFormat;
import java.util.Date;

public class Venda {

	private Integer id;
	private int quantidadePedido;
	private Double valor;
	private Funcionario funcionario;
	private Cliente cliente;
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
		return converterDoubleDoisDecimais(valor);
	}

	public void setValor(double valor) {
		 this.valor =  converterDoubleDoisDecimais(valor);
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
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
	
	public static double converterDoubleDoisDecimais(double precoDouble) {
	    DecimalFormat fmt = new DecimalFormat("0.00");      
	    String string = fmt.format(precoDouble);
	    String[] part = string.split("[,]");
	    String string2 = part[0]+"."+part[1];
	        double preco = Double.parseDouble(string2);
	    return preco;
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
