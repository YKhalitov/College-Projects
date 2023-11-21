//Name: Yaroslav Khalitov
//Class: CSCI.251 COPADS
//Program: This is an PublicKey object that stores an email of a users, and a public key.
// This file is used in Messenger.cs for the storage of the public key and aid in operations relating to it.


public class PublicKey
{
    public String? email;
    public String key;
    public PublicKey(String email, String key){
        this.email = email;
        this.key = key;
    }
    public PublicKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }  

    public String getEmail(){
        return email!;
    }
    public void setEmail(String email){
        this.email = email;
    }
}      