import java.io.IOException;
import java.net.URI;
import java.io.*;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    Vector<String> database = new Vector<String>();
    String result = "Search result:";

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Weclome to the Search Engine!");
        } else if (url.getPath().contains("/search")) {
            String match = "";
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                   for(int i = 0; i < database.size(); i++)
                   {
                        if(database.get(i).contains(parameters[1]))
                        {
                            match+= database.get(i);
                            match+=" ";
                        }
                   }
                   return String.format(result+match);
                }
           /* else if(url.getPath().equals("/clear"))
            {
                database.clear();
                return String.format("Database erased!");
            }*/
        } else {
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                   database.add(parameters[1]);
                   return String.format("Term added!");
                }
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
