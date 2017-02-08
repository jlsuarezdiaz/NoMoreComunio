package Model;

import static java.util.Arrays.stream;
import java.util.Map;
import static java.util.stream.Collectors.toMap;

/**
 * MessageKind enum.
 * Displays every way a message can take.
 * @author Juan Luis
 */
public enum MessageKind {
    
    //1xx - Connection messages.
    HELO               (100),
    LOGIN              (101),
    BYE                (102),
    LOGOUT             (103),
    DISC               (104),
    KILL               (105),
    VERSION            (106),
    UPDATE_DOWNLOAD    (107),
    
    //2xx - OK Messages
    OK                 (200),
    OK_LOGIN           (201),
    //OK_SENT            (202),
    //OK_PRIV            (203),
    //OK_SLCT            (204),
    //OK_STATE           (205),
    OK_FILE            (206),
    OK_VERSION         (207),
    OK_JOIN            (208),
    OK_CREATE          (209),
    OK_LINEUP          (210),
    OK_OFFER           (211),
    OK_OFFERPLAYER     (212),
    OK_SIGNUP          (213),
    OK_REMOVEPLAYER    (214),
    
    //3xx - Communication messages
    USERS              (300),
    //SEND               (301),
    FILE               (302),
    IMALIVE            (303),
    //CHANGE_PRIV        (304),
    //CHANGE_SLCT        (305),
    //CHANGE_STATE       (306),
    SENDMESSAGE         (307),  //Usuario (String), Comunidad (String), msg (String)
    LISTCOMS            (308),  //Usuario (String) [C]  coms (ArrayList<String> [S]
    GETCOM              (309),  //Comunidad (String)
    MARKET              (310),  //Jugadores (ArrayList<players>)
    NEWS                (311),  //Noticias  (ArrayList<Message>)
    MONEY               (312),  //Dinero    (int)
    JOIN                (313),  //Usuario (String), Comunidad (String), passwd (String)
    CREATECOM           (314),  //Usuario (String), Comunidad (String), passwd (String)
    PLAYERS             (315),  //Jugadores (ArrayList<Player>)
    CHANGE_LINEUP       (316),  //Jugadores (ArrayList<Player>) 
    MAKE_OFFER          (317),
    OFFER_PLAYER        (318),
    SIGN_UP             (319),
    REMOVE_PLAYER      (320),
    
    
    SEND_FILE          (399),
    
    //4xx - Error messages
    ERR                (400),
    ERR_BADREQUEST     (401),
    ERR_USEROVERFLOW   (402),
    ERR_FORBIDDEN      (403),
    ERR_NOTFOUND       (404),
    WARN_NOTUPATED     (405),
    ERR_NEEDUPDATE     (406),
    ERR_JARNOTFOUND    (407),
    
    ERR_INVALIDUSER    (408),
    ERR_DATABASE       (409),
    ERR_INVALIDCOM     (410),
    
    
    //0xx
    NOP                (000)
    ;
    
    //For mapping between enum and int
    private final int msgCode;
    
    private final static Map<Integer, MessageKind> map =
            stream(MessageKind.values()).collect(toMap(msg -> msg.msgCode, msg -> msg));
    
    /**
     * Private constructor.
     * @param msgCode Message code. 
     */
    private MessageKind(int msgCode){
        this.msgCode=msgCode;
    }
    
    /**
     * Gets the MessageKind object associated to the given code.
     * @param msgCode Message code.
     * @return MessageKind whose code is \e msgCode.
     */
    public static MessageKind valueOf(int msgCode){
        return map.get(msgCode);
    }
    
    /**
     * Gets the code of the message.
     * @return Integer with the message code.
     */
    public int getMessageCode(){
        return msgCode;
    }
    
    
}
