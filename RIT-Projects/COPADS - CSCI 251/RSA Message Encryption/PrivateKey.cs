//Name: Yaroslav Khalitov
//Class: CSCI.251 COPADS
//Program: This is an PrivateKey object that stores a list of emails of different users, and a private key.
// This file is used in Messenger.cs for the storage of the private key and aid in operations relating to it.


public class PrivateKey
{
    public List<String> emails = new List<String>();
    public String key;

    public PrivateKey(List<String> emails, String key){
        this.emails = emails;
        this.key = key;
    } 
    public PrivateKey(String key){
        this.key = key;
    } 

    public String getKey(){
        return this.key;
    }  

    public List<String> getEmail(){
        return this.emails;
    }

    public void addEmail(String email){
        this.emails.Add(email);
    }
}