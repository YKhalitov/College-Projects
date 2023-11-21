//Name: Yaroslav Khalitov
//Class: CSCI.251 COPADS
//Program: This is an Message object that stores an email of a user, and the content of their message.
// This file is used in Messenger.cs in the 'sendMsg' and 'getMsg' functions to both receive and send content.


public class Message
{
    public String email;
    public String content;

    public Message(String email, String content){
        this.email = email;
        this.content = content;
    } 

    public String getContent(){
        return this.content;
    }  

    public String getEmail(){
        return this.email;
    }


}