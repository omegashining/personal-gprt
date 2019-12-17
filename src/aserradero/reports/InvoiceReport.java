package aserradero.reports;

import aserradero.entities.Cliente;
import aserradero.entities.Factura;
import aserradero.entities.ProductoFacturado;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import mx.gob.org.ipn.cic.mu.classes.MyDate;
import mx.gob.org.ipn.cic.mu.classes.Price;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 *@Gabriel
 */
public class InvoiceReport {
    
    private Factura invoice;
    private Cliente client;
    private ProductoFacturado[] sales;

    public InvoiceReport(Factura invoice, Cliente client, ProductoFacturado[] sales) {
        this.invoice = invoice;
        this.client = client;
        this.sales = sales;
    }

    public void create() {
        String jasperName = "factura";
        JRBeanCollectionDataSource dataSource;
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        Map pars = new HashMap();

        MyDate date = new MyDate();
        date.setDatabaseDate( invoice.getDate() );
        pars.put( "day", date.getDay()+ "" );
        pars.put( "month", date.getMonth() + "" );
        pars.put( "year", date.getYear() + "" );
        pars.put( "name", client.getName() + " " + client.getPaternalSurname() + " " + client.getMaternalSurname() );
        pars.put( "address", client.getAddress() );
        pars.put( "place", client.getCity() );
        pars.put( "rfc", client.getRfc() );

        Double subtotal = 0.0;
        for(int i = 1; i <= 15; i++) {
            if(sales.length >= i)
                subtotal += Double.parseDouble( sales[i-1].getPrice() ) * Integer.parseInt( sales[i-1].getCantity() );
        }

        pars.put( "subtotal", subtotal+"" );
        Double iva = subtotal * Double.parseDouble(invoice.getIva()) * 0.01;
        pars.put( "iva", iva+"" );
        Double total = subtotal + iva;
        pars.put( "total", total+"" );
        
        for( int i = 1; i <= 15; i++ ) {
            if( sales.length >= i ) {
                pars.put( "c_"+i, sales[i-1].getCantity() );
                pars.put( "d_"+i, sales[i-1].getDescription() );
                pars.put( "p_"+i, sales[i-1].getPrice() );
                Double amount = Double.parseDouble(sales[i-1].getPrice()) * Double.parseDouble(sales[i-1].getCantity());
                pars.put("i_"+i, amount+"" );
            } else {
                pars.put( "c_"+i, " " );
                pars.put( "d_"+i, " " );
                pars.put( "p_"+i, " " );
                pars.put( "i_"+i, " " );
            }
        }

        StringTokenizer st = new StringTokenizer( total +"", "." );
        String entero = st.nextToken().trim();
        String decimal = st.nextToken().trim();

        Price price = new Price();

        String enteroText = price.convertToLetters( Integer.parseInt(entero) );
        String decimalText = price.convertToLetters( Integer.parseInt(decimal) );

        if(decimalText.equals(""))
            decimalText = "Cero";

        pars.put( "totalNumber", enteroText + " pesos con " + decimalText + " centavos" );

        try{
            //1.-Llenar el datasource con la informacion de la base de datos o de donde este, en este caso "hardcode"
            Collection collection = populateData();
            dataSource = new JRBeanCollectionDataSource( collection );

            //2.-Compilamos el archivo XML y lo cargamos en memoria
            jasperReport = (JasperReport) JRLoader.loadObject( jasperName + ".jasper" );

            //3.-Llenamos el reporte con los parámetros necesarios para la consulta
            jasperPrint = JasperFillManager.fillReport(jasperReport, pars, dataSource);

            File directory = new File( System.getProperty("user.home") + "\\Reports" );
            directory.mkdir();

            directory = new File( System.getProperty("user.home") + "\\Reports\\Invoices" );
            directory.mkdir();

            //4.-Exportamos el reporte a pdf y lo guardamos en disco
            date.setDatabaseDate( invoice.getDate() );
            String reportName = date.format() + "_" + invoice.getInvoiceNumber();

            JasperExportManager.exportReportToPdfFile( jasperPrint, System.getProperty("user.home") + "\\Reports\\Invoices\\" + reportName + ".pdf" );

            try {
                String command = "cmd /c \"" + System.getProperty("user.home") + "\\Reports\\Invoices\\" + reportName + ".pdf\"";
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
