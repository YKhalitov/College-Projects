using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
//Name: Yaroslav Khalitov
//Class: CSCI.251 COPADS
//Program: This is a file that aids in storing public & private keys.
// This file is used in Messenger.cs also for operations regarding the private key list of emails.


public class StoreKey
{

    public static void storePublic(PublicKey key){
        var fileName = "public.key";
        string jsonString = JsonConvert.SerializeObject(key);
        File.WriteAllText(fileName, jsonString);
    } 

    public static void storePrivate(PrivateKey key){
        var fileName = "private.key";
        string jsonString = JsonConvert.SerializeObject(key);
        File.WriteAllText(fileName, jsonString);
    } 

    public static void addToPrivateKeys(String emailToAdd){
        var fileName = "private.key";

        string jsonBody = File.ReadAllText(fileName);
        // deserialize the JSON string into a JObject
        JObject jsonObject = JObject.Parse(jsonBody);

        // get the array of emails from the JObject
        JArray emailsArray = (JArray)jsonObject["emails"]!;
        
        //check if the emailToAdd is in the list before adding
        if (!emailsArray.Any(email => email.ToString() == emailToAdd)){
            emailsArray.Add(emailToAdd); 
            string updatedJson = jsonObject.ToString();
            File.WriteAllText(fileName, updatedJson);   
        }
    } 

    public static Boolean containsPrivateEmail(String emailToFind){
        var fileName = "private.key";

        string jsonBody = File.ReadAllText(fileName);
        // deserialize the JSON string into a JObject
        JObject jsonObject = JObject.Parse(jsonBody);

        // get the array of emails from the JObject
        JArray emailsArray = (JArray)jsonObject["emails"]!;

        //check if emailToFind is in the list
        foreach (var email in emailsArray){
            if(email.ToString() == emailToFind){
                return true;
            }
        }

        return false;
    }

}  