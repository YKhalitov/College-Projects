using System.Numerics;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System.Text;

//Name: Yaroslav Khalitov
//Class: CSCI.251 COPADS
//Program: This is the main driver program for the Secure Messaging Project #3. This uses public key encryption
// to send and receive secure messages to and from other users. The keys are stored on a server and API calls are made
// to send and receive these messages and keys. RSA generation is used for the ecnrypting and decrypting algorithm.



public class Messenger
{
    static readonly HttpClient client = new HttpClient();

    //Main driver function for the program. Decides what to do based on input.
    public static async Task Main(string[] args)
    {
        if (ensureCorrectInput(args)){
            switch(args[0]){
                case "keyGen":
                    keyGen(Int32.Parse(args[1]));
                    break;
                case "sendKey":
                    await sendKey(args[1]);
                    break;
                case "getMsg":
                    await getMsg(args[1]);
                    break;
                case "getKey":
                    await getKey(args[1]);
                    break;
                case "sendMsg":
                    await sendMsg(args[1], args[2]);
                    break;
            }
        }else{
            printMessage();
            System.Environment.Exit(0);    
        }

    }

    // getKey <email> - this will retrieve public key for a particular user adn store it in the local filesystem
    // as <email>.key. 
    public static async Task getKey(String email){
        try
        {
            using HttpResponseMessage response = await client.GetAsync($"http://kayrun.cs.rit.edu:5000/Key/{email}");
            response.EnsureSuccessStatusCode();
            string responseBody = await response.Content.ReadAsStringAsync();

            if (responseBody.Length == 0){
                Console.WriteLine($"Key for email {email} does not exist. Try another email.");
                return;
            }

            var fileName = $"{email}.key";
            File.WriteAllText(fileName, responseBody);
        }
        catch (HttpRequestException e)
        {
            Console.WriteLine("ERROR, PLEASE RETRY");
            Console.WriteLine("Message :{0} ", e.Message);

        }

    }

    // processKey <email> - this is a helper function for processing a public 
    // key and extracting E & N from it (used in RSA encryption/decryption)
    public static (BigInteger, BigInteger) processKey(String email){
        //get public key object from local filesystem
        var responeBody = File.ReadAllText($"{email}.key");
        var jsonObject = JObject.Parse(responeBody);
        PublicKey publicKey = new PublicKey(jsonObject.GetValue("email")!.ToString(), jsonObject.GetValue("key")!.ToString());

        //extract it and convert it to byte array
        var keyEncoded = publicKey.getKey();
        byte[] keyDecoded = Convert.FromBase64String(keyEncoded!);

        //FIND e & E
        byte[] firstFour = new Byte[] {keyDecoded[0], keyDecoded[1], keyDecoded[2], keyDecoded[3]};
        Array.Reverse(firstFour);
        int e = BitConverter.ToInt32(firstFour, 0);

        byte[] firstFourBytesFollowing = new Byte[e];
        for (int i = 0; i < e; i++){
            firstFourBytesFollowing[i] = keyDecoded[4 + i];   
        }
        BigInteger E = new BigInteger(firstFourBytesFollowing);

        //Find n & N
        byte[] secondFour = new Byte[] {keyDecoded[4 + e], keyDecoded[4 + e + 1], keyDecoded[4 + e + 2], keyDecoded[4 + e + 3]};
        Array.Reverse(secondFour);
        int n = BitConverter.ToInt32(secondFour, 0);

        byte[] secondFourBytesFollowing = new Byte[n];
        for (int i = 0; i < n; i++){
            secondFourBytesFollowing[i] = keyDecoded[8 + e + i];    
        }
        BigInteger N = new BigInteger(secondFourBytesFollowing);

        return (E, N);
    }

    //processPrivateKey - this is a helper function for processing the private key
    // stored in the local filesystem and extracting D & N from it (used in RSA encryption/decryption)
    public static (BigInteger, BigInteger) processPrivateKey(){
        //get private key object from local filesystem
        var responeBody = File.ReadAllText("private.key");
        var jsonObject = JObject.Parse(responeBody);
        PrivateKey privateKey = new PrivateKey(jsonObject.GetValue("key")!.ToString());

        //extract it and convert it to byte array
        var keyEncoded = privateKey.getKey();
        byte[] keyDecoded = Convert.FromBase64String(keyEncoded!);

        //FIND d & D
        byte[] firstFour = new Byte[] {keyDecoded[0], keyDecoded[1], keyDecoded[2], keyDecoded[3]};
        Array.Reverse(firstFour);
        int d = BitConverter.ToInt32(firstFour, 0);

        byte[] firstFourBytesFollowing = new Byte[d];
        for (int i = 0; i < d; i++){
            firstFourBytesFollowing[i] = keyDecoded[4 + i];   
        }
        BigInteger D = new BigInteger(firstFourBytesFollowing);

        //Find n & N
        byte[] secondFour = new Byte[] {keyDecoded[4 + d], keyDecoded[4 + d + 1], keyDecoded[4 + d + 2], keyDecoded[4 + d + 3]};
        Array.Reverse(secondFour);
        int n = BitConverter.ToInt32(secondFour, 0);

        byte[] secondFourBytesFollowing = new Byte[n];
        for (int i = 0; i < n; i++){
            secondFourBytesFollowing[i] = keyDecoded[8 + d + i];    
        }
        BigInteger N = new BigInteger(secondFourBytesFollowing);

        return (D, N);
    }

    // keyGen <keysize> - this will generate a keypair of size keysize bits (public and private
    // keys) and store them locally on the disk (in files called public.key and private.key
    // respectively), in the current directory. The format of the private key is:
    public static void keyGen(int bits){
        //generate the prime numbers for p & q
        int pBits = (int)(bits * 0.75);
        int qBits = (int)(bits * 0.25);
        BigInteger p = PrimeGen.generatePrimeNumber(pBits);
        BigInteger q = PrimeGen.generatePrimeNumber(qBits);

        //calculate necessary variables for RSA algorithm
        BigInteger N = p * q;
        BigInteger r = ( p - 1 ) * ( q - 1 ); 
        BigInteger E = PrimeGen.generatePrimeNumber(16);
        BigInteger D = modInverse (E , r);

        //initialize necessary operational variables
        byte[] NByteArray = N.ToByteArray();
        byte[] EByteArray = E.ToByteArray();
        byte[] DByteArray = D.ToByteArray();
        int NByteCount = N.GetByteCount();
        int EByteCount = E.GetByteCount();
        int DByteCount = D.GetByteCount();

        //create the n,e and d 4 byte sized arrays necesary for indicating the amount of N, E, and D
        //needed to read and process in each key
        byte[] tempArray1 = BitConverter.GetBytes(NByteCount);
        byte[] nArray = new byte[4];
        Array.Copy(tempArray1, nArray, tempArray1.Length);

        byte[] tempArray2 = BitConverter.GetBytes(EByteCount);
        byte[] eArray = new byte[4];
        Array.Copy(tempArray2, eArray, tempArray2.Length);

        byte[] tempArray3 = BitConverter.GetBytes(DByteCount);
        byte[] dArray = new byte[4];
        Array.Copy(tempArray3, dArray, tempArray3.Length);

        //initialize byte arrays for both public and private keys
        byte[] myPublicKey = new Byte[8 + EByteCount + NByteCount];
        byte[] myPrivateKey = new Byte[8 + DByteCount + NByteCount];

        //public & private key N construction
        for (int i = (myPublicKey.Length - 1),  j = (NByteArray.Length - 1), k = (myPrivateKey.Length - 1); j >= 0; i--, j--, k--){
            myPublicKey[i] = NByteArray[j];
            myPrivateKey[k] = NByteArray[j];
        }
        //read the size of N (stored in nArray) into just before where N was read into both the public and private keys
        myPublicKey[7 + EByteCount] = nArray[0]; 
        myPublicKey[6 + EByteCount] = nArray[1]; 
        myPublicKey[5 + EByteCount] = nArray[2]; 
        myPublicKey[4 + EByteCount] = nArray[3]; 

        myPrivateKey[7 + DByteCount] = nArray[0];  
        myPrivateKey[6 + DByteCount] = nArray[1];  
        myPrivateKey[5 + DByteCount] = nArray[2];  
        myPrivateKey[4 + DByteCount] = nArray[3];  

        //public key E construction
        for (int i = (3 + EByteCount),  j = (EByteArray.Length - 1); i > 3; i--, j--){
            myPublicKey[i] = EByteArray[j];   
        }
        //read the size of E (stored in eArray) into just before where E was read into the public key
        myPublicKey[3] = eArray[0];  
        myPublicKey[2] = eArray[1];  
        myPublicKey[1] = eArray[2];  
        myPublicKey[0] = eArray[3];  

        //public key D construction
        for (int i = (3 + DByteCount),  j = (DByteArray.Length - 1); i > 3; i--, j--){
            myPrivateKey[i] = DByteArray[j];   
        }
        //read the size of D (stored in dArray) into just before where D was read into the private key
        myPrivateKey[3] = dArray[0]; 
        myPrivateKey[2] = dArray[1]; 
        myPrivateKey[1] = dArray[2]; 
        myPrivateKey[0] = dArray[3]; 

        //store the public and private keys generated
        String myPublicKeyBase64 = Convert.ToBase64String(myPublicKey);
        String myPrivateKeyBase64 = Convert.ToBase64String(myPrivateKey);

        PublicKey publicKey = new PublicKey(myPublicKeyBase64);
        PrivateKey privateKey = new PrivateKey(myPrivateKeyBase64);

        StoreKey.storePublic(publicKey);
        StoreKey.storePrivate(privateKey);

    }

    // sendKey email - this option sends the public key that was generated in keyGen
    // and sends it to the server, with the email address given. This should be your
    // email address. The server will then register this email address as a valid receiver of
    // messages.
    public static async Task sendKey(String userEmail){
        try
        {   
            //make sure you we have the public key generated
            if (!File.Exists("public.key")){
                Console.WriteLine("Public key does not exist yet, please run -> keyGen <keysize>");
                return;    
            }

            StoreKey.addToPrivateKeys(userEmail);
            //get public key object from local filesystem
            var responseBody = File.ReadAllText("public.key");
            var jsonObject = JObject.Parse(responseBody);
            PublicKey publicKey = new PublicKey(jsonObject.GetValue("email")!.ToString(), jsonObject.GetValue("key")!.ToString());

            //send it to the server with the user email
            publicKey.setEmail(userEmail);
            string jsonString = JsonConvert.SerializeObject(publicKey);

            var content = new StringContent(jsonString, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PutAsync($"http://kayrun.cs.rit.edu:5000/Key/{userEmail}", content);
            response.EnsureSuccessStatusCode();
            string res = await response.Content.ReadAsStringAsync();

            Console.WriteLine("Key saved");
        }
        catch (HttpRequestException e)
        {
            Console.WriteLine("ERROR, PLEASE RETRY");
            Console.WriteLine("Message :{0} ", e);

        }
        
    }

    // getMsg <email> - this will retrieve a message for a particular user, while it is possible
    // to download messages for any user, it is only possible to decode messages for
    // which you have the private key. 
    public static async Task getMsg(String email){
        try
        {
            //make sure you we have the private key generated
            if (!File.Exists("private.key")){
                Console.WriteLine("Private key does not exist yet, please run -> keyGen <keysize>");
                return;    
            }

            //check if we can decode the message
            if (!StoreKey.containsPrivateEmail(email)){
                Console.WriteLine("Cannot Decode");
                return;
            }

    

            //retrieve the message from the server
            using HttpResponseMessage response = await client.GetAsync($"http://kayrun.cs.rit.edu:5000/Message/{email}");
            response.EnsureSuccessStatusCode();
            string responseBody = await response.Content.ReadAsStringAsync();

            var jsonObjectMessage = JObject.Parse(responseBody);
            Message message = new Message(jsonObjectMessage.GetValue("email")!.ToString(), jsonObjectMessage.GetValue("content")!.ToString());


            //get D & N from the private key stored in the local filesystem
            (BigInteger D, BigInteger N) = processPrivateKey();

            //decode the message
            var messageBase64 = message.getContent();
            byte[] messageByteArray = Convert.FromBase64String(messageBase64!);
            BigInteger messageBigInt = new BigInteger(messageByteArray);
            BigInteger result = BigInteger.ModPow(messageBigInt, D, N);
            byte[] decryptedByteArray = result.ToByteArray();
            String decryptedString = Encoding.UTF8.GetString(decryptedByteArray);

            Console.WriteLine(decryptedString);
        }
        catch (HttpRequestException e)
        {
            Console.WriteLine("ERROR, PLEASE RETRY");
            Console.WriteLine("Message :{0} ", e.Message);

        }  

    }


    // sendMsg <email, plaintext> - this will take a plaintext message, encrypt it using the
    // public key of the person you are sending it to, based on their email address.
    public static async Task sendMsg(String email, String plaintext){
        try
        {
            //make sure you we have the private key generated
            if (!File.Exists("private.key") || !File.Exists("public.key")){
                Console.WriteLine("Private & public key does not exist yet, please run -> keyGen <keysize>");
                return;    
            }

            //make sure you we have the public key for that person
            if (!File.Exists($"{email}.key")){
                Console.WriteLine($"Key does not exist for {email}");
                return;    
            }
            //get public key object from local filesystem
            var publicKeyFile = File.ReadAllText($"{email}.key");
            var publicKeyObject = JObject.Parse(publicKeyFile);
            PublicKey publicKey = new PublicKey(publicKeyObject.GetValue("email")!.ToString(), publicKeyObject.GetValue("key")!.ToString());

            //extract it and convert it to byte array
            byte[] messageByteArray = Encoding.UTF8.GetBytes(plaintext);
            BigInteger messageBigInt = new BigInteger(messageByteArray);

            //extract E & N from the public key associated with that email
            (BigInteger E, BigInteger N) = processKey(email);
            BigInteger encryptedMessage = BigInteger.ModPow(messageBigInt, E, N);
            byte[] encryptedMessageArray = encryptedMessage.ToByteArray();
            string encryptedMessageBase64 = Convert.ToBase64String(encryptedMessageArray);

            //create message object and send it to the server
            Message message = new Message(email, encryptedMessageBase64);
            string jsonString = JsonConvert.SerializeObject(message);
            var content = new StringContent(jsonString, Encoding.UTF8, "application/json");
            HttpResponseMessage response = await client.PutAsync($"http://kayrun.cs.rit.edu:5000/Message/{email}", content);

            response.EnsureSuccessStatusCode();
            string res = await response.Content.ReadAsStringAsync();

            Console.WriteLine("Message written");
        }
        catch (HttpRequestException e)
        {
            Console.WriteLine("ERROR, PLEASE RETRY");
            Console.WriteLine("Message :{0} ", e.Message);

        }  
       
    }
    

    // printMessage - print out an error message for the user
    public static void printMessage(){
        Console.WriteLine("\nYou MUST specify one of the following commands... \n \t -> getKey <email> \n \t -> getMsg <email> \n \t -> sendKey <email> \n \t -> sendMsg <email> <message> \n \t -> keyGen <keysize> \n \t * Example input: 'dotnet sendMsg exampleEmail@gmail.com \"My Message!\"\n");
    }

    //ensureCorrectInput <args> - ensures that the user input is correct/valid
    public static Boolean ensureCorrectInput(String[] args){ 
        if (args.Length == 2){
            try{
                if (args[0].Equals("keyGen") && Int32.Parse(args[1]) >= 32 && Int32.Parse(args[1]) % 8 == 0){
                    return true;
                }else if (args[0].Equals("sendKey") || args[0].Equals("getMsg") || args[0].Equals("getKey")){
                    return true;
                }else{
                    return false;    
                }
            }catch (System.FormatException){
                return false;    
            }
        }else if(args.Length == 3){
            if (args[0].Equals("sendMsg")){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    //modInverse <a, n> - used in RSA key generation algorithm
    public static BigInteger modInverse(BigInteger a, BigInteger n){
        BigInteger i = n, v = 0, d = 1;
        while (a>0) {
            BigInteger t = i/a, x = a;
            a = i % x;
            i = x;
            x = d;
            d = v - t*x;
            v = x;
        }
        v %= n;
        if (v<0) 
            v = (v+n)%n;
            return v;
    }
}    

