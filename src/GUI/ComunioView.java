package GUI;

import Model.ClientController;
import Model.AppDateFormat;
import Model.MessageKind;
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
import java.util.ArrayList;
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
                UsersPanelMouseClicked(me);
            }
        });
        UsersPanel.add(uv);
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
        
        
        this.setTitle(Data.Txt.PROGRAM_NAME);
        
        myTrayIcon = null;
        initializeTrayIcon();

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
            Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/msn_icon.png"));
            myTrayIcon =
                new TrayIcon(img, "No More Dropbox MSN");
            myTrayIcon.setImageAutoSize(true);
            final SystemTray tray = SystemTray.getSystemTray();
             try {
            tray.add(myTrayIcon);
            } catch (Exception e) {}
        }
    }
    
    public void desktopNotify(String msg){      
        if(SystemTray.isSupported() && !isActive() && desktopNotifications){
            myTrayIcon.displayMessage("NO MORE DROPBOX MSN", msg, TrayIcon.MessageType.INFO);
        }
    }
    
    /**
     * Set the view of MSN
     * @param msn msn_controller that will set the view.
     */
    public void setMSN(ClientController ctrl){
        this.controller = ctrl;
        MyUserPanel.setUser(controller.getUser(),ctrl.getMyId());
        MyUserPanel.repaint();
        fillUserPanel(controller.getUserList());
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
        this.MyUserPanel.setEnabled(enabled);
        this.TextMessage.setEnabled(enabled);
        this.UsersPanel.setEnabled(enabled);
        this.MyUserPanel.setVisible(enabled);
        if(!enabled) UsersPanel.removeAll();
        
        
    }
    
    /**
     * Gets users on user list which are selected by the MSN user.
     * @return Array with selected users.
     */
    public ArrayList<User> getSelectedUsers(){
       UserView uv;
       ArrayList<User> users = new ArrayList();
       for(Component c : UsersPanel.getComponents()){
           uv = (UserView) c;
           if(uv.isSelected())
               users.add(uv.getUser());
       }
       return users;
    }
    

    


    
    /**
     * Fills the user list.
     * @param user_list Array of users that will fill the panel.
     */
    public void fillUserPanel(User[] user_list){
        UsersPanel.removeAll();
        for(int i = 0; i < User.getMaxUsers(); i++){
            User u = user_list[i];
            if(u != null){
                UserView uv = new UserView();
                uv.setUser(u,i);
                addToUserPanel(uv);
            }
        //    UsersPanel.add(Box.createRigidArea(new Dimension(5,0)));
        }
        UsersPanel.repaint();
        UsersPanel.revalidate();
    }
    
    /**
     * Updates user panel.
     * @param user_list Array of users that will update user panel.
     */
    public void updateUserPanel(User[] user_list){
        boolean selection_now;
        //UserView view;
        for(int i = 1; i < User.getMaxUsers(); i++){
            selection_now = ((UserView)UsersPanel.getComponent(i)).isSelected();
            ((UserView)UsersPanel.getComponent(i)).setUser(user_list[i],i);
            if(user_list[i].validState())
                ((UserView)UsersPanel.getComponent(i)).select(selection_now);
        //    view = ((UserView)UsersPanel.getComponent(i));
        //    selection_now = view.isSelected();
        //    view.setUser(user_list[i]);
        //   view.select(selection_now);   
        }
        UsersPanel.repaint();
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
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        MyUserPanel = new GUI.UserView();
        UserScroll = new javax.swing.JScrollPane();
        UsersPanel = new javax.swing.JPanel();
        BtExit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextMessage = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        BtSendMessage = new javax.swing.JButton();
        msgPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dropbox MSN");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/football_icon.png")));
        setMinimumSize(new java.awt.Dimension(945, 591));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("USUARIOS CONECTADOS:");

        MyUserPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        UserScroll.setMaximumSize(new java.awt.Dimension(204, 32767));
        UserScroll.setPreferredSize(new java.awt.Dimension(204, 1000000));

        UsersPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        UsersPanel.setNextFocusableComponent(TextMessage);
        UsersPanel.setLayout(new javax.swing.BoxLayout(UsersPanel, javax.swing.BoxLayout.PAGE_AXIS));
        UsersPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsersPanelMouseClicked(evt);
            }
        });
        UsersPanel.setLayout(new javax.swing.BoxLayout(UsersPanel, javax.swing.BoxLayout.PAGE_AXIS));
        UserScroll.setViewportView(UsersPanel);

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

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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
        jScrollPane1.setViewportView(TextMessage);

        jLabel2.setText("Seleccionar comunidad: ");

        jLabel3.setText("Mercado:");

        jLabel4.setText("Noticias");

        jLabel5.setText("Escribir noticia:");

        BtSendMessage.setText("ENVIAR");
        BtSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSendMessageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout msgPanelLayout = new javax.swing.GroupLayout(msgPanel);
        msgPanel.setLayout(msgPanelLayout);
        msgPanelLayout.setHorizontalGroup(
            msgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        msgPanelLayout.setVerticalGroup(
            msgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(148, 148, 148)
                        .addComponent(jLabel3))
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(msgPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtSendMessage)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 387, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BtExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UserScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(MyUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UserScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MyUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(BtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(msgPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                            .addComponent(BtSendMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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

    private void UsersPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsersPanelMouseClicked
        if(evt.getComponent() instanceof UserView){
//            controller.changeSelect(((UserView)evt.getComponent()).getUserId());
        }
    }//GEN-LAST:event_UsersPanelMouseClicked

    private void BtSendMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSendMessageActionPerformed
        controller.sendMessage(TextMessage.getText());
        TextMessage.setText("");
    }//GEN-LAST:event_BtSendMessageActionPerformed

    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtExit;
    private javax.swing.JButton BtSendMessage;
    private GUI.UserView MyUserPanel;
    private javax.swing.JTextArea TextMessage;
    private javax.swing.JScrollPane UserScroll;
    private javax.swing.JPanel UsersPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel msgPanel;
    // End of variables declaration//GEN-END:variables
}
