package GUI;

import Model.ClientController;
import Model.AppDateFormat;
import Model.Message;
import Model.MessageKind;
import Model.Player;
import Model.User;
import Model.UserOverflowException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicComboPopup;

/**
 * Class ComunioView.
 * A messenger GUI.
 * @author Juan Luis
 */
public class ComunioView extends javax.swing.JFrame {

    /**
     * MSN Controller.
     */
    private ClientController controller;
   
    /**
     * State colors array.
     */
    private static final Color[] state_colors = {Color.GREEN,Color.ORANGE,Color.RED,Color.BLACK};
    
    /**
     * State names array.
     */
    private static final String[] state_names = {"ONLINE","ABSENT","BUSY","OFF"};
    
    
    /**
     * Checks if text is valid to be sent.
     */
    boolean validText;
    
    // ---------- SETTINGS ATTRIBUTES ---------- //
    /**
     * Allows sending after pressing Enter.
     */
    private boolean enterSendOption;
    
    /**
     * Text copied in clipboard.
     */
    private String clipboard;
    
    /**
     * Allows sound when receiving a message.
     */
    private boolean sound;
    
    /**
     * Allows desktop notifications when receiving a message.
     */
    private boolean desktopNotifications;
    
    /**
     * Tray icon;
     */
    private TrayIcon myTrayIcon;
    

    

    private void addToUserPanel(UserView uv) {
        uv.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
        //        UsersPanelMouseClicked(me);
            }
        });
       // UsersPanel.add(uv);
//        uv.select(controller.isSelected(uv.getUserId()));
    }
    
    
    
    
    /**
     * Set valid text and enables components if necessary.
     * @param valid Boolean to set valid text.
     */
    private void setValidText(boolean valid){
        this.validText = valid;

    }
    
    /**
     * Action of sending a message.
     */
    private void performSend(){
 //       controller.send(TextMessage.getText(),BtPrivate.isSelected());
    }
    
    /**
     * Updates the color of the user state combo box
     */
    
    
    
    /**
     * Creates new form MSNView
     */
    public ComunioView() {
               
        initComponents();
        

        
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                System.exit(0);
            }
        });
        
               
        
        //--- SETTINGS INITIALIZE ---//
        enterSendOption = true;
        
        sound = true;
        
        desktopNotifications = true;
        
        clipboard = null;
        
        this.boxSelectCom.setVisible(false);
        this.lblNoCom.setVisible(false);
        this.btCreateCom.setEnabled(false);
        this.btJoinCom.setEnabled(false);
        
        this.setTitle(Data.Txt.PROGRAM_NAME);
        
        myTrayIcon = null;
        initializeTrayIcon();
        
        //jLabel1.setVisible(false);
        //UsersPanel.setVisible(false);
        //MyUserPanel.setVisible(false);

    }




    /**
     * Shows ComunioView.
     */
    public void showView(){
        this.setVisible(true);
        TextMessage.grabFocus();
        TextMessage.requestFocusInWindow();
        TextMessage.requestFocus();
    }
    
    public void initializeTrayIcon(){
        if(SystemTray.isSupported()){
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/football_icon.png"));
            myTrayIcon =
                new TrayIcon(img, "No More Comunio");
            myTrayIcon.setImageAutoSize(true);
            final SystemTray tray = SystemTray.getSystemTray();
             try {
            tray.add(myTrayIcon);
            } catch (Exception e) {}
        }
    }
    
    public void desktopNotify(String msg){      
        if(SystemTray.isSupported() && !isActive() && desktopNotifications){
            myTrayIcon.displayMessage("NO MORE COMUNIO", msg, TrayIcon.MessageType.INFO);
        }
    }
    
    /**
     * Set the view of MSN
     * @param msn msn_controller that will set the view.
     */
    public void setMSN(ClientController ctrl){
        this.controller = ctrl;
        //MyUserPanel.setUser(controller.getUser(),ctrl.getMyId());
        //MyUserPanel.repaint();
        //fillUserPanel(controller.getUserList());
        repaint();
    }
    
    public void setMSN(){
        if(controller != null) setMSN(controller);
    }
    
    /**
     * Enables or disables essential ComunioView components.
     * @param enabled Determines enabling or disabling.
     */
    public void enableMSNComponents(boolean enabled){
   
        setValidText(enabled && !TextMessage.getText().trim().isEmpty());
        //this.ComboUserState.setEnabled(enabled);
//        this.MyUserPanel.setEnabled(enabled);
        this.TextMessage.setEnabled(enabled);
 //       this.UsersPanel.setEnabled(enabled);
//        this.MyUserPanel.setVisible(enabled);
 //       if(!enabled) UsersPanel.removeAll();
        
        
    }
    
    /**
     * Sets the community list for a user
     * @param coms Community list
     */
    public void setCommunityList(ArrayList<String> coms){
        if(coms.isEmpty()){
            lblNoCom.setVisible(true);
            boxSelectCom.setVisible(false);
        }
        else{
            lblNoCom.setVisible(false);
            boxSelectCom.setVisible(true);
            boxSelectCom.setModel(new DefaultComboBoxModel(coms.toArray()));
            boxSelectCom.setSelectedIndex(0);
        }
        this.btCreateCom.setEnabled(true);
        this.btJoinCom.setEnabled(true);
    }
    
    
    public void setMarket(ArrayList<Player> market){
        this.marketPanel.removeAll();
        for(Player p: market){
            PlayerView pv = new PlayerView();
            pv.setPlayer(p);
            marketPanel.add(pv);
        }
        
        btMarket.setSelected(true);
        btJugadores.setSelected(false);
        btCambiarAlineacion.setVisible(false);
        
        btRealizarOferta.setVisible(true);
        btOfrecerJugadores.setVisible(false);
        marketPanel.repaint();
        marketPanel.revalidate();
    }
    
    public void setPlayers(ArrayList<Player> players, ArrayList<Player> lineup){
        this.marketPanel.removeAll();
    
        ArrayList<Integer> claves = new ArrayList();
        
        for (Player l:lineup){
            claves.add(l.getCode());
        }
        
        for(Player p: players){
            PlayerView pv = new PlayerView();
            pv.setPlayer(p);
        
            if (claves.contains(p.getCode())){
                pv.select(true);
            }
                
            marketPanel.add(pv);
        }
        
        
        btMarket.setSelected(false);
        btJugadores.setSelected(true);
        btCambiarAlineacion.setVisible(true);
        
        btRealizarOferta.setVisible(false);
        btOfrecerJugadores.setVisible(true);
        marketPanel.repaint();
        marketPanel.revalidate();
    }
    
    public void setMoney(long money){
        if(money >= 20000000)
            this.lblMoney.setForeground(Color.CYAN);
        else if(money >= 5000000)
            this.lblMoney.setForeground(Color.GREEN);
        else if(money >= 1000000)
            this.lblMoney.setForeground(Color.YELLOW);
        else if(money >= 0)
            this.lblMoney.setForeground(Color.ORANGE);
        else
            this.lblMoney.setForeground(Color.RED);
            
        this.lblMoney.setText(NumberFormat.getNumberInstance().format(money));
    }
    
    
    public void setNews(ArrayList<Message> tablon){
        this.msgPanel.removeAll();
        for(Message m : tablon){
            MessageView mv = new MessageView();
            mv.setMessage(m);
            msgPanel.add(mv);
        }
        MessageScroll.validate();
        boolean isOnBottom = MessageScroll.getVerticalScrollBar().getValue() == 
                MessageScroll.getVerticalScrollBar().getMaximum()-MessageScroll.getVerticalScrollBar().getVisibleAmount();
        if(isOnBottom){
            JScrollBar vertical = MessageScroll.getVerticalScrollBar();
            vertical.setValue( vertical.getMaximum() );
        }
        
        msgPanel.repaint();
        msgPanel.revalidate();
    }
    
    /**
     * Gets users on user list which are selected by the MSN user.
     * @return Array with selected users.
     */
    public ArrayList<User> getSelectedUsers(){
       UserView uv;
       ArrayList<User> users = new ArrayList();
/*       for(Component c : UsersPanel.getComponents()){
           uv = (UserView) c;
           if(uv.isSelected())
               users.add(uv.getUser());
       }*/
       return users;
    }
    

    


    
    /**
     * Fills the user list.
     * @param user_list Array of users that will fill the panel.
     */
    public void fillUserPanel(User[] user_list){
//        UsersPanel.removeAll();
        for(int i = 0; i < User.getMaxUsers(); i++){
            User u = user_list[i];
            if(u != null){
                UserView uv = new UserView();
                uv.setUser(u,i);
                addToUserPanel(uv);
            }
        //    UsersPanel.add(Box.createRigidArea(new Dimension(5,0)));
        }
  //      UsersPanel.repaint();
  //      UsersPanel.revalidate();
    }
    
    /**
     * Updates user panel.
     * @param user_list Array of users that will update user panel.
     */
    public void updateUserPanel(User[] user_list){
        boolean selection_now;
        //UserView view;
  /*      for(int i = 1; i < User.getMaxUsers(); i++){
            selection_now = ((UserView)UsersPanel.getComponent(i)).isSelected();
            ((UserView)UsersPanel.getComponent(i)).setUser(user_list[i],i);
            if(user_list[i].validState())
                ((UserView)UsersPanel.getComponent(i)).select(selection_now);
        //    view = ((UserView)UsersPanel.getComponent(i));
        //    selection_now = view.isSelected();
        //    view.setUser(user_list[i]);
        //   view.select(selection_now);   
        }*/
//        UsersPanel.repaint();
    }
    
    /**
     * Forces the MSN user update, independently of the state of user updater handler.
     */
/*    public void updateUserForced(){
        try {
            controller.updateUser(true);
            MyUserPanel.setUser(controller.getUser());
        } catch (UserOverflowException ex) {
            ComunioView.showUserOverflowMsg(ex);
        }
    }*/
    
    /**
     * Shows User Overflow exception message dialog.
     * @param ex User Overflow exception.
     */
    public static void showUserOverflowMsg(UserOverflowException ex){
        JOptionPane.showMessageDialog(null, "Dropbox MSN está desbordado en estos instantes. Inténtelo más tarde." +
            "\n[Error: " + ex.getMessage() + "]\n",
            "User Overflow",JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Performs message received sound, if sound setting allows it.
     */
    public void messageSound(){
        if(sound){
            new Thread(() -> {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            (getClass().getResource("/Media/MSNsound.wav")));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                //    System.err.println(e.getMessage());
                }
            }).start();
        }
    }
    
    /**
     * Enables or disables settings button.
     * @param b Determines if enabling of disabling.
     */

    

    // ---------- SETTINGS ACCESSORS ---------- //
    /**
     * Gets the value of sending when pressing enter option.
     */
    public boolean getEnterSendOption(){
        return enterSendOption;
    }
    
    /**
     * Sets the value of sending when pressing enter option.
     */
    public void setEnterSendOption(boolean b){
        enterSendOption = b;
    }
    
    /**
     * Gets the value of sounding option.
     */
    public boolean getSound(){
        return sound;
    }
    
    /**
     * Sets the value of sounding option.
     */
    public void setSound(boolean b){
        sound = b;
    }
    
    public boolean getDesktopNotifications(){
        return desktopNotifications;
    }
    
    public void setDesktopNotifications(boolean b){
        desktopNotifications = b;
    }
       
    public ArrayList<Player> getSelectedPlayers(){
        ArrayList<Player> players = new ArrayList();
        
        for(Component c: marketPanel.getComponents()){
            if(((PlayerView)c).isSelected()){
                players.add(((PlayerView)c).getPlayer());
            }
        }
       
        return players;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtExit = new javax.swing.JButton();
        MessageScroll = new javax.swing.JScrollPane();
        TextMessage = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        lblMarket_Players = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        BtSendMessage = new javax.swing.JButton();
        lblNoCom = new javax.swing.JLabel();
        boxSelectCom = new javax.swing.JComboBox();
        btJoinCom = new javax.swing.JButton();
        btCreateCom = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblMoney = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        msgPanel = new javax.swing.JPanel();
        marketScroll = new javax.swing.JScrollPane();
        marketPanel = new javax.swing.JPanel();
        btMarket = new javax.swing.JToggleButton();
        btJugadores = new javax.swing.JToggleButton();
        btCambiarAlineacion = new javax.swing.JButton();
        btRealizarOferta = new javax.swing.JButton();
        btOfrecerJugadores = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dropbox MSN");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/football_icon.png")));
        setMaximumSize(new java.awt.Dimension(945, 591));
        setMinimumSize(new java.awt.Dimension(945, 591));
        setResizable(false);

        BtExit.setBackground(new java.awt.Color(255, 0, 0));
        BtExit.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        BtExit.setForeground(new java.awt.Color(255, 255, 255));
        BtExit.setText("EXIT");
        BtExit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtExitActionPerformed(evt);
            }
        });

        MessageScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        TextMessage.setColumns(20);
        TextMessage.setLineWrap(true);
        TextMessage.setRows(5);
        TextMessage.setNextFocusableComponent(TextMessage);
        TextMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextMessageKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TextMessageKeyReleased(evt);
            }
        });
        MessageScroll.setViewportView(TextMessage);

        jLabel2.setText("Seleccionar comunidad: ");

        lblMarket_Players.setText("Mercado:");

        jLabel4.setText("Noticias");

        jLabel5.setText("Escribir noticia:");

        BtSendMessage.setText("ENVIAR");
        BtSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSendMessageActionPerformed(evt);
            }
        });

        lblNoCom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNoCom.setForeground(new java.awt.Color(255, 51, 0));
        lblNoCom.setText("¡NO ESTÁS TODAVÍA EN NIGUNA COMUNIDAD! ");

        boxSelectCom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--NO COMS--" }));
        boxSelectCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxSelectComActionPerformed(evt);
            }
        });

        btJoinCom.setText("Unirse a una comunidad");
        btJoinCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btJoinComActionPerformed(evt);
            }
        });

        btCreateCom.setText("Crear una nueva comunidad");
        btCreateCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCreateComActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 153, 0));
        jLabel6.setText("DINERO:");

        lblMoney.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMoney.setForeground(new java.awt.Color(0, 255, 255));
        lblMoney.setText(NumberFormat.getNumberInstance().format(20000000)
        );

        msgPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        msgPanel.setLayout(new javax.swing.BoxLayout(msgPanel, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane2.setViewportView(msgPanel);

        marketPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        marketPanel.setLayout(new javax.swing.BoxLayout(marketPanel, javax.swing.BoxLayout.PAGE_AXIS));
        marketScroll.setViewportView(marketPanel);

        btMarket.setText("Mercado");
        btMarket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMarketActionPerformed(evt);
            }
        });

        btJugadores.setText("Mis jugadores");
        btJugadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btJugadoresActionPerformed(evt);
            }
        });

        btCambiarAlineacion.setText("Cambiar Alineación");
        btCambiarAlineacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCambiarAlineacionActionPerformed(evt);
            }
        });

        btRealizarOferta.setText("Realizar Oferta");
        btRealizarOferta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRealizarOfertaActionPerformed(evt);
            }
        });

        btOfrecerJugadores.setText("Ofrecer Jugadores");
        btOfrecerJugadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOfrecerJugadoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMoney, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btCreateCom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btJoinCom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boxSelectCom, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNoCom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(MessageScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BtSendMessage)))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btMarket)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btJugadores)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btCambiarAlineacion)
                        .addGap(18, 18, 18)
                        .addComponent(btRealizarOferta)
                        .addGap(18, 18, 18)
                        .addComponent(btOfrecerJugadores)
                        .addGap(73, 73, 73)
                        .addComponent(BtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(lblMarket_Players)
                    .addComponent(marketScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblMarket_Players))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNoCom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxSelectCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btJoinCom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCreateCom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMoney)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
                    .addComponent(marketScroll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btMarket)
                                .addComponent(btJugadores)
                                .addComponent(btCambiarAlineacion)
                                .addComponent(btRealizarOferta)
                                .addComponent(btOfrecerJugadores)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MessageScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(BtSendMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(BtExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

   
    /**
     * Button exit event.
     * @param evt 
     */
    private void BtExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtExitActionPerformed
        controller.stop();
        System.exit(0);
    }//GEN-LAST:event_BtExitActionPerformed

    /**
     * Key released event on message text area. Enables sending button or empties text area if needed.
     * @param evt 
     */
    private void TextMessageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextMessageKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && enterSendOption){
            TextMessage.setText("");
        }
        setValidText(!TextMessage.getText().trim().isEmpty());
    }//GEN-LAST:event_TextMessageKeyReleased

    /**
     * Key pressed event on message text area. Sends the message if needed and allowed.
     * @param evt 
     */
    private void TextMessageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextMessageKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER && enterSendOption && validText){
            performSend();
        }
    }//GEN-LAST:event_TextMessageKeyPressed

    private void BtSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSendMessageActionPerformed
        controller.sendMessage(TextMessage.getText());
        TextMessage.setText("");
    }//GEN-LAST:event_BtSendMessageActionPerformed

    private void boxSelectComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxSelectComActionPerformed
        this.controller.setCommunity((String)this.boxSelectCom.getSelectedItem());
    }//GEN-LAST:event_boxSelectComActionPerformed

    private void btJoinComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btJoinComActionPerformed
        JoinCommunityDialog joind = new JoinCommunityDialog(this, true);
        joind.setVisible(true);
        ArrayList<String> comData = joind.getCommunityData();
        controller.joinCommunity(comData);
    }//GEN-LAST:event_btJoinComActionPerformed

    private void btCreateComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCreateComActionPerformed
        JoinCommunityDialog joind = new JoinCommunityDialog(this, true);
        joind.setBtText("CREAR");
        joind.setVisible(true);
        ArrayList<String> comData = joind.getCommunityData();
        controller.createCommunity(comData);
    }//GEN-LAST:event_btCreateComActionPerformed

    private void btMarketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMarketActionPerformed
        controller.setMarketView(true);
    }//GEN-LAST:event_btMarketActionPerformed

    private void btJugadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btJugadoresActionPerformed
        controller.setMarketView(false);
    }//GEN-LAST:event_btJugadoresActionPerformed

    private void btCambiarAlineacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCambiarAlineacionActionPerformed
        controller.changeLineUp(getSelectedPlayers());
    }//GEN-LAST:event_btCambiarAlineacionActionPerformed

    private void btRealizarOfertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRealizarOfertaActionPerformed
        // TODO add your handling code here:
        ArrayList<Player> jugadores = getSelectedPlayers();
        
        for(Player p:jugadores){
            fijarPrecio fp = new fijarPrecio(this,true);
            fp.setJugador(p.getName());
            fp.setVisible(true);
            
            int pOferta = fp.getOferta();
            controller.makeOffer(p.getCode(),pOferta);
        }
        
    }//GEN-LAST:event_btRealizarOfertaActionPerformed

    private void btOfrecerJugadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOfrecerJugadoresActionPerformed
        // TODO add your handling code here:
        ArrayList<Player> jugadores = getSelectedPlayers();
        
        for(Player p:jugadores){
            fijarPrecio fp = new fijarPrecio(this,true);
            fp.setJugador(p.getName());
            fp.setText("Introduzca precio mínimo para el jugador ");
            fp.setVisible(true);
            
            int pOferta = fp.getOferta();
            controller.ofrecerJugador(p.getCode(),pOferta);
        }
    }//GEN-LAST:event_btOfrecerJugadoresActionPerformed

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtExit;
    private javax.swing.JButton BtSendMessage;
    private javax.swing.JScrollPane MessageScroll;
    private javax.swing.JTextArea TextMessage;
    private javax.swing.JComboBox boxSelectCom;
    private javax.swing.JButton btCambiarAlineacion;
    private javax.swing.JButton btCreateCom;
    private javax.swing.JButton btJoinCom;
    private javax.swing.JToggleButton btJugadores;
    private javax.swing.JToggleButton btMarket;
    private javax.swing.JButton btOfrecerJugadores;
    private javax.swing.JButton btRealizarOferta;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMarket_Players;
    private javax.swing.JLabel lblMoney;
    private javax.swing.JLabel lblNoCom;
    private javax.swing.JPanel marketPanel;
    private javax.swing.JScrollPane marketScroll;
    private javax.swing.JPanel msgPanel;
    // End of variables declaration//GEN-END:variables
}
