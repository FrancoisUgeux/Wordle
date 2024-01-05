package be.he2b.atl.common.message;

import be.he2b.atl.common.model.User;
import java.io.Serializable;


public interface Message extends Serializable {

    Type getType();
    
    User getAuthor();

    User getRecipient();
    
    Object getContent();
    
}
