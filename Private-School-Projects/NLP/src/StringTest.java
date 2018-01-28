import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Tanner on 9/23/2016.
 */
public class StringTest {
    public static void main(String[] args){
		boolean same = filesAreSame(args[0],args[1]);
		if(same)
			pl("Passed");
		else
			pl("Failed");

    }

    public static void pl(String s){
      System.out.println(s);
    }

    public static boolean filesAreSame(String path1, String path2){
        String str1 = readTextFromFile(path1);
        String str2 = readTextFromFile(path2);
        return (!str1.isEmpty() && str1.equals(str2));
    }

    public static String readTextFromFile(String path){
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String str = new String(data, "UTF-8");
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
