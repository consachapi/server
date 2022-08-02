package pe.regioncusco.llankasun.server.commons;

import java.io.FileInputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static Tpl tpl(boolean success, List<?> list, int total){
        return new Tpl(success, list, total);
    }

    public static Tpl tpl(boolean success, String message, List<?> list, int total){
        return new Tpl(success, message, list, total);
    }

    public static Tpl tpl(boolean success, String message){
        return new Tpl(success, message);
    }

    public static String generateNameFile(){
        return UUID.randomUUID().toString();
    }

    public static byte[] decodeFile(String fileContent){
        return Base64.getDecoder().decode(fileContent);
    }

    public static String encodeFile(byte[] fileContent){
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static String encodeBytesFile(byte[] inputFile){
        String encodedString = new String(Base64.getEncoder().encode(inputFile));
        return encodedString;
    }

    public static String decodeBase64(String input){
        byte[] bytes = Base64.getDecoder().decode(input);
        return new String(bytes);
    }

}
