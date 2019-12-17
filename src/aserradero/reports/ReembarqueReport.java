package aserradero.reports;

import aserradero.entities.Destinatario;
import aserradero.entities.Autorizacion;
import aserradero.entities.Reembarque;
import aserradero.entities.Resolucion;
import aserradero.entities.Titular;
import aserradero.entities.Transporte;
import aserradero.entities.ProductoAmparado;
import aserradero.entities.Saldo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mx.gob.org.ipn.cic.mu.classes.MyDate;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Gabriel
 */
public class ReembarqueReport {

    private Reembarque reembarque;
    private Resolucion resolution;
    private ProductoAmparado product;
    private Saldo balance;
    private Autorizacion authorization;
    private Transporte transport;
    private Destinatario addressee;
    private Titular titular;

    public ReembarqueReport(Reembarque reeembarque, Resolucion resolution, ProductoAmparado product, Saldo balance, Autorizacion authorization, Transporte transport, Destinatario addressee, Titular titular) {
        this.reembarque = reeembarque;
        this.resolution = resolution;
        this.product = product;
        this.balance = balance;
        this.authorization = authorization;
        this.transport = transport;
        this.addressee = addressee;
        this.titular = titular;
    }

    public void create() {
        String jasperName = "reembarque";
        JRBeanCollectionDataSource dataSource;
        JasperReport jasperReport;
        JasperPrint jasperPrint = null;
        Map pars = new HashMap();

        pars.put( "folioAutorizado", reembarque.getAuthorizedFolio() );
        MyDate date = new MyDate();
        date.setDatabaseDate( reembarque.getExpedition() );
        pars.put( "fechaExpedicion", date.getDay() + "         " + date.getMonth() + "      " + date.getYear() );
        date.setDatabaseDate( reembarque.getExpiration() );
        pars.put( "fechaVencimiento", date.getDay() + "         " + date.getMonth() + "      " + date.getYear() );
        pars.put( "nombreTitular", titular.getName() );
        pars.put( "domicilioTitular", titular.getAddress() );
        pars.put( "curpTitular", titular.getCurp() );
        pars.put( "siemTitular", titular.getSiem() );
        pars.put( "oficioTitular", authorization.getOfficeNumber() );
        date.setDatabaseDate( authorization.getDate() );
        pars.put( "fechaTitular", date.format() );
        pars.put( "amparoTitular", authorization.getShelterCantity() );
        pars.put( "inicialTitular", authorization.getInicialFolio() );
        pars.put( "finalTitular", authorization.getFinalFolio() );
        pars.put( "medidaTitular", authorization.getMeasure() );
        date.setDatabaseDate( authorization.getExpiration() );
        pars.put( "vencimientoTitular", date.format() );
        pars.put( "ubicacionRemitente", titular.getName() );
        pars.put( "rfnRemitente", resolution.getRfn() );
        pars.put( "municipioRemitente", titular.getTown() );
        pars.put( "entidadRemitente", titular.getEntity() );
        pars.put( "resolucionRemitente", resolution.getResolutionType() );
        pars.put( "numeroRemitente", resolution.getNumber() );
        date.setDatabaseDate( resolution.getDate() );
        pars.put("fechaRemitente", date.format() );
        pars.put("vigenciaRemitente", resolution.getValidity() );
        pars.put("volumenRemitente", resolution.getVolumeAnuality() );
        pars.put("anualidadRemitente", resolution.getCantityAnuality() );
        pars.put("deRemitente", resolution.getPaymentAnuality() );
        pars.put("productoRemitente", resolution.getProduct() );
        pars.put("nombreDestinatario", addressee.getName() );
        pars.put("curpDestinatario", addressee.getCurp() );
        pars.put("identificacionDestinatario", addressee.getIdentificationCode() );
        pars.put("rfnDestinatario", addressee.getRfn() );
        pars.put("destinoDestinatario", addressee.getResidenceDestiny() );
        pars.put("poblacionDestinatario", addressee.getPopulation() );
        pars.put("municipioDestinatario", addressee.getTown() );
        pars.put("entidadDestinatario", addressee.getEntity() );
        pars.put("domicilioDestinatario", addressee.getResidence() );
        pars.put("cantidadProducto", product.getCantity() );
        pars.put("descripcionProducto", product.getDescription() );
        pars.put("volumenProducto", product.getVolume() );
        pars.put("medidaProducto", product.getMeasure() );
        pars.put("letraProducto", reembarque.getLetter() );
        pars.put("observacionSaldo", balance.getObservation() );
        pars.put("anteriorSaldo", balance.getPreviousBalance() );
        pars.put("actualSaldo", balance.getActualBalance() );
        pars.put("siguienteSaldo", balance.getNextBalance() );
        pars.put("medioTransporte", transport.getTransport() );
        pars.put("propietarioTransporte", transport.getProprietor() );
        pars.put("marcaTransporte", transport.getMark() );
        pars.put("tipoTransporte", transport.getType() );
        pars.put("modeloTransporte", transport.getModel() );
        pars.put("capacidadTransporte", transport.getCapacity() );
        pars.put("placasTransporte", transport.getLicensePlate() );
        pars.put("nombreExpide", titular.getName() );
        pars.put("identificacionExpide", titular.getIdentificationCode() );

        try{
            //1.-Llenar el datasource con la informacion de la base de datos o de donde este, en este caso "hardcode"
            Collection lista = populateData();
            dataSource = new JRBeanCollectionDataSource(lista);

            //2.-Compilamos el archivo XML y lo cargamos en memoria
            jasperReport = (JasperReport) JRLoader.loadObject( jasperName + ".jasper" );

            //3.-Llenamos el reporte con los parámetros necesarios para la consulta
            jasperPrint = JasperFillManager.fillReport(jasperReport, pars, dataSource);

            File directory = new File( System.getProperty("user.home") + "\\Reports" );
            directory.mkdir();

            directory = new File( System.getProperty("user.home") + "\\Reports\\Reembarques" );
            directory.mkdir();

            //4.-Exportamos el reporte a pdf y lo guardamos en disco
            String reportName = reembarque.getProgressiveFolio();
            JasperExportManager.exportReportToPdfFile( jasperPrint, System.getProperty("user.home") + "\\Reports\\reembarques\\" + reportName + ".pdf" );

            try {
                String command = "cmd /c \"" + System.getProperty("user.home")+"\\Reports\\Reembarques\\" + reportName + ".pdf\"";
                Process p = Runtime.getRuntime().exec( command );
            } catch (Exception err) {
                err.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
  
    private static Collection populateData(){
        Collection list = new ArrayList();
        list.add(new Universidad("Universidad de Montemorelos","Dr. Oscar Castillo","Montemorelos NL.",new Integer(4800)));
        list.add(new Universidad("Tecnológico de Monterrey","Dr. Jair Acosta","Monterrey NL.",new Integer(10956)));
        list.add(new Universidad("Universidad Regiomontana","Dr. Meliza Orduñez","Monterrey NL.",new Integer(5200)));
        return list;
    }

}
