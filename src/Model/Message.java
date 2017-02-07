////////////////////////////////////////////////////////////////////////////////
// Author: Juan Luis Suarez Diaz
// Jun, 2015
// Dropbox MSN
////////////////////////////////////////////////////////////////////////////////
package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class message. It represents the messages that users send in MSN.
 * @author Juan Luis
 */
public class Message implements Serializable{
        
    /**
     * Message contents.
     */
    private String messageData;
    
    /**
     * Message date.
     */
    private Date date;
    
    /**
     * Index for the message.
     */
    //private int seqNumber;
    
    /**
     * User who sent the message.
     */
    private String sender;
    
    /**
     * Indicates if the message is public.
     */
    //private boolean isPublic;
    

    
    /**
     * Private method to set message attributes.
     * @param text Message text.
     * @param sender User who sends the message.
     * @param seqNumber Message index.
     * @param isPublic Indicates if the message is public or private.
     */
    private void set(String text,String sender, Date d){
        this.messageData = text;
        this.date = d;
        this.sender = sender;
        //this.seqNumber = seqNumber;
        //this.isPublic = isPublic;
    }
    
    /**
     * Default constructor.
     */
    public Message(){
        set("",null,null);
    }
    
    /**
     * Constructor.
     * @param text Message text.
     * @param sender User who sends the message.
     * @param seqNumber Message index.
     * @param isPublic Indicates if the message is public or private.
     */
    public Message(String text,String sender, Date d){
        set(text, sender, d);
    }
    
    /**
     * Gets the text of the message.
     * @return Message text.
     */
    public String getText(){
        return messageData;
    }
    
    /**
     * Gets the date of the message.
     * @return Message date. 
     */
    public Date getDate(){
        return date;
    }

    /**
     * Gets the message number.
     * @return Message number.
     */
/*    public int getSeqNumber() {
        return seqNumber;
    }*/

    /**
     * Gets the message sender.
     * @return Sender.
     */
    public String getSender() {
        return sender;
    }
    
    
    



    /**
     * Indicates if message is public or private.
     * @return True, if and only if message is public.
     */
/*    public boolean isPublic() {
        return isPublic;
    }*/


    
    /**
     * Adds a header to the message text.
     * @param header Text to add as header.
     */
    public void addHeader(String header){
        this.messageData=header+this.messageData;
    }
    
    /**
     * Indicates whether message content has any text.
     * @return true, when message text is empty. Else, false.
     */
    public boolean isEmpty(){
        return messageData.trim().isEmpty();
    }
    
    @Override
    public String toString(){
        return
                "SENDER: "+((sender==null)?"No More Comunio Server":sender.toString())+
                //"\nPUBLIC: "+Boolean.toString(isPublic)+
                
                //"\nNUM: "+Integer.toString(seqNumber)+
                "\nDATE: "+AppDateFormat.getInstance().format(date)+
                "\nDATA:\n\t"+messageData;
    }
}
