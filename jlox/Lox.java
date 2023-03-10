import java.io.*;
import java.nio.*;
import java.util.*;


public class Lox{
    static boolean hadError = false;

    private static void runFile(String path) throws IOException{
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if(hadError){
            System.exit(65);
        }
    }


    private static void runPrompt()throws IOException{
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print(">");
            String line = reader.readLine();
            if(line == null){
                break;
            }else{
                run(line);
                hadError = false;
            }
        }
    }

    private static void run(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // Print the tokens for now
        for(Token token : tokens){
            System.out.println(token);
        }   
    }

    static void error(int line,String message){
        report(line, "", message);
    }

    private static void report(int line, String where, String message){
        System.err.println("[line " + line + "] Error"+where+": "+message);
        hadError = true;
    }


    public static void main(String[] args) throws IOException{
        if(args.length > 1){
            System.out.println("Usage: Jlox [script]");
            System.exit(64);
        }else if(args.length == 1){
            runFile(args[0]);
        }else{
            runPrompt();
        }
    }
}
