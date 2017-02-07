/*
 * Author: Juan Luis Su�rez D�az
 * July, 2016
 * No More Dropbox MSN
 */
package GUI;

import Model.Player;

/**
 *
 * @author Juan Luis
 */
public class PlayerView extends javax.swing.JPanel {

    private Player playerModel;
    
    /**
     * Creates new form PlayerView
     */
    public PlayerView() {
        initComponents();
    }
    
    public void setPlayer(Player p){
        this.playerModel = p;
        this.lblName.setText(p.getName());
        this.lblPos.setText(p.getPos());
        this.lblTeam.setText(p.getTeam());
        this.btGoles.setText(Integer.toString(p.getGoals()));
        this.btAsist.setText(Integer.toString(p.getAsists()));
        this.btGolesEnc.setText(Integer.toString(p.getEncajados()));
        this.btAmarillas.setText(Integer.toString(p.getYellowCards()));
        this.btRojas.setText(Integer.toString(p.getRedCards()));
        this.btPuntos.setText(Integer.toString(p.getPoints()));
    }
    
    public Player getPlayer(){
        return playerModel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblName = new javax.swing.JLabel();
        lblTeam = new javax.swing.JLabel();
        btGoles = new javax.swing.JButton();
        btAsist = new javax.swing.JButton();
        btGolesEnc = new javax.swing.JButton();
        btAmarillas = new javax.swing.JButton();
        btRojas = new javax.swing.JButton();
        btPuntos = new javax.swing.JButton();
        lblPos = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(714, 55));
        setMinimumSize(new java.awt.Dimension(714, 55));

        lblName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblName.setText("Youssef El-Arabi");

        lblTeam.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTeam.setText("Granada C.F");

        btGoles.setBackground(new java.awt.Color(255, 255, 255));
        btGoles.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btGoles.setText("15");
        btGoles.setToolTipText("Goles");
        btGoles.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btAsist.setBackground(new java.awt.Color(153, 255, 255));
        btAsist.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAsist.setText("8");
        btAsist.setToolTipText("Asistencias");
        btAsist.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btGolesEnc.setBackground(new java.awt.Color(153, 153, 153));
        btGolesEnc.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btGolesEnc.setForeground(new java.awt.Color(255, 255, 255));
        btGolesEnc.setText("0");
        btGolesEnc.setToolTipText("Goles encajados");
        btGolesEnc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btAmarillas.setBackground(new java.awt.Color(255, 255, 0));
        btAmarillas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAmarillas.setForeground(new java.awt.Color(255, 255, 255));
        btAmarillas.setText("8");
        btAmarillas.setToolTipText("Tarjetas amarillas");
        btAmarillas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btRojas.setBackground(new java.awt.Color(255, 0, 0));
        btRojas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btRojas.setForeground(new java.awt.Color(255, 255, 255));
        btRojas.setText("0");
        btRojas.setToolTipText("Tarjetas rojas");
        btRojas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btPuntos.setBackground(new java.awt.Color(0, 51, 255));
        btPuntos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btPuntos.setForeground(new java.awt.Color(255, 255, 255));
        btPuntos.setText("128");
        btPuntos.setToolTipText("Puntos");
        btPuntos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblPos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPos.setText("DEL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btGoles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btAsist, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btGolesEnc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAmarillas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btRojas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btGoles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTeam, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btAsist, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btGolesEnc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btAmarillas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btRojas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAmarillas;
    private javax.swing.JButton btAsist;
    private javax.swing.JButton btGoles;
    private javax.swing.JButton btGolesEnc;
    private javax.swing.JButton btPuntos;
    private javax.swing.JButton btRojas;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPos;
    private javax.swing.JLabel lblTeam;
    // End of variables declaration//GEN-END:variables
}