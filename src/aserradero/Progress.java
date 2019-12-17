package aserradero;

import javax.swing.SwingUtilities;

import mx.gob.org.ipn.cic.du.dialogs.JLoading;

/**
 *
 * @author Gabriel
 */
public class Progress implements Runnable {
    private JLoading loading;
    private Application appliction;
    private int progress;
    
    public Progress(JLoading loading, Application application) {
        this.loading = loading;
        this.appliction = application;
        this.progress = this.loading.getProgress();
    }

    @Override
    public void run() {
        try {
            this.appliction.initComponents();
            iterate(30);
            
            this.appliction.initGUI();
            iterate(40);
            
            this.appliction.initListeners();
            iterate(10);
            
            this.appliction.initWindow();
            iterate(20);

            this.loading.dispose();
            
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    appliction.setVisible(true);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void iterate(int cantity) {
        for(int i = 0; i < cantity; i++) {
            try {
                Thread.sleep(10);
        
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        loading.setProgress(++progress);
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
