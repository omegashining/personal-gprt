package aserradero.dialogs.reembarque;

import aserradero.database.Consulta;
import aserradero.entities.Destinatario;
import aserradero.entities.Autorizacion;
import aserradero.entities.Informacion;
import aserradero.entities.ProductoAmparado;
import aserradero.entities.Reembarque;
import aserradero.entities.Resolucion;
import aserradero.entities.Saldo;
import aserradero.entities.Titular;
import aserradero.entities.Transporte;
import aserradero.reports.ReembarqueReport;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mx.gob.org.ipn.cic.mu.utl.ImageUtil;

/**
 *
 * @author Gabriel
 */
public class JReembarqueWizard implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JReembarqueForm reembarqueForm;
    private JButton next;
    private JButton previous;
    private JButton create;
    private JButton clear;

    public JReembarqueWizard(JFrame frame) {
        this.frame = frame;

        initComponents();
        setPanel();
        addListeners();
    }

    private void initComponents() {
        reembarqueForm = new JReembarqueForm( frame );

        next     = new JButton( "Siguiente", ImageUtil.getIcon("images/Next.png") );
        previous = new JButton( "Anterior", ImageUtil.getIcon("images/Previous.png") );
        create   = new JButton( "Crear Factura", ImageUtil.getIcon("images/PDF.png") );
        clear    = new JButton( "Limpiar campos", ImageUtil.getIcon("images/Clear.png") );
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel() {
        JPanel card1 = reembarqueForm.getCardOne();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 26;
        gbc.gridwidth = 1;
        card1.add( new JLabel(" "), gbc );

        gbc.gridy = 27;
        gbc.gridx = 32;
        gbc.gridwidth = 8;
        card1.add( next, gbc );

        gbc.gridx = 0;
        gbc.gridy = 28;
        gbc.gridwidth = 1;
        card1.add( new JLabel(" "), gbc );

        JPanel card2 = reembarqueForm.getCardTwo();

        gbc.gridx = 0;
        gbc.gridy = 25;
        gbc.gridwidth = 1;
        card2.add( new JLabel(" "), gbc );

        gbc.gridy = 26;
        gbc.gridx = 0;
        gbc.gridwidth = 8;
        card2.add( previous, gbc );
        gbc.gridx = 12;
        gbc.gridwidth = 8;
        card2.add( create, gbc );
        gbc.gridx = 22;
        gbc.gridwidth = 8;
        card2.add( clear, gbc );

        gbc.gridx = 0;
        gbc.gridy = 27;
        gbc.gridwidth = 1;
        card2.add( new JLabel(" "), gbc );

        JPanel cards = new JPanel( new CardLayout() );
        cards.add( card1, "Card1" );
        cards.add( card2, "Card2" );

        panel = cards;
    }

    private void addListeners() {
        next.addActionListener( this );
        previous.addActionListener( this );
        create.addActionListener( this );
        clear.addActionListener( this );
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        
        if(s.equals("Siguiente")) {
            Reembarque r = reembarqueForm.getReembarqueFromFields();
            Autorizacion a = reembarqueForm.getAuthorization();
            Resolucion o = reembarqueForm.getResolucion();
            Destinatario d = reembarqueForm.getAddressee();

            r.setLetter(" ");
            if(!r.areEmptyFields() && a != null && !o.areEmptyFields() && d != null) {
                CardLayout c = (CardLayout)(getPanel().getLayout());
                c.show( getPanel(), "Card2" );
            } else
                JOptionPane.showMessageDialog( null, "Debe llenar todos los campos." );
        } if(s.equals("Anterior")) {
            CardLayout c = (CardLayout)(getPanel().getLayout());
            c.show( getPanel(), "Card1" );
        } else if(s.equals("Crear Factura")) {
            Consulta enquery = new Consulta();

            Autorizacion a = reembarqueForm.getAuthorization();
            Destinatario d = reembarqueForm.getAddressee();
            Transporte t = reembarqueForm.getTransport();
            Reembarque r = reembarqueForm.getReembarqueFromFields();
            Resolucion o = reembarqueForm.getResolucion();
            ProductoAmparado p = reembarqueForm.getShelterProduct();
            Saldo l = reembarqueForm.getBalance();
            Informacion i = reembarqueForm.getInformation();

            if(t != null && !p.areEmptyFields() && !l.areEmptyFields() && !i.areEmptyFields()) {
                if(enquery.executeUpdate(r.insert())) {
                    if(enquery.executeUpdate(o.insert()) && enquery.executeUpdate(p.insert()) && enquery.executeUpdate(l.insert()) && enquery.executeUpdate(i.insert())) {
                        JOptionPane.showMessageDialog( null, "Los datos fueron guardados correctamente\n" +
                                "Ahora Pulse Aceptar y Espere un momento para que se genere el documento." );

                        Titular titular = new Titular();
                        Titular[] titulars = (Titular[]) enquery.executeQuery( titular.search(), "Titular" );

                        ReembarqueReport report = new ReembarqueReport(r,o,p,l,a,t,d,titulars[0]);
                        report.create();
                    } else
                        JOptionPane.showMessageDialog( null, "Error al guardar los datos" );
                } else
                    JOptionPane.showMessageDialog( null, "Ya existe un Reembarque con el mismo folio Progresivo." );
            } else
                JOptionPane.showMessageDialog( null, "Debe llenar todos los campos vacíos, para que se genere el documento." );
        } else if(s.equals("Limpiar campos")) {
            int confirm = JOptionPane.showConfirmDialog( frame, "¿Está seguro de limpiar todos los campos?");

            if (JOptionPane.OK_OPTION == confirm) {
                reembarqueForm.clearAllFields();

                CardLayout c = (CardLayout)(getPanel().getLayout());
                c.show( getPanel(), "Card1" );
            }
        }

    }

}
