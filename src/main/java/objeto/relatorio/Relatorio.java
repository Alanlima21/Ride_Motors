package objeto.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import objeto.conexao.Conexao;
import objeto.entidade.Produto;
import objeto.tratamentoErro.ErroSistema;

public class Relatorio {

	private HttpServletResponse response;
	private FacesContext context;
	private ByteArrayOutputStream baos;


	public Relatorio() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) context.getExternalContext().getResponse();
	}

	public void getRelatorio(List<Produto> lista) throws ErroSistema {
		File Jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/Produtos.jasper"));
		Map<String, Object> params = new HashMap<>();
		baos = new ByteArrayOutputStream();
		Connection conexao = Conexao.getConexao();
		try {
			JasperReport report = (JasperReport) JRLoader.loadObject(Jasper);
			JasperPrint print = JasperFillManager.fillReport(report, params, conexao);
			JasperExportManager.exportReportToPdfStream(print, baos);

			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();

			context.responseComplete();

		} catch (JRException ex) {
			Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
