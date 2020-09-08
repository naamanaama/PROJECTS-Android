package com.example.ex1;
import java.io.Serializable;
import java.util.Date;

/**
 * This class represents a single Recycler item- in our case it is a text view
 * */

public class Message_item implements Serializable {
    /**
     * Static method that returns all current recycler items that were created
     * @return - list of all current Recyclers
     */

    public String id;
    public String text;
    public Date timestamp;

    public Message_item(){}

    /**
     * Constructor
     * @param text_to_present - the text we want to present when creating this recycler item
     */
    public Message_item(String text_to_present){
        this.text = text_to_present;
        this.timestamp = new Date();
        this.id = timestamp +"";
    }

    @Override
    public boolean equals(Object o){
        return this ==o;
    }


    public String getId(){
        return this.id;
    }
    public String getText(){
        return this.text;
    }
    public Date getTimestamp(){
        return this.timestamp;
    }

}




