package window;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Main
{
    
    private static final String TITLE = "Registration";
    private static final int DIMX = 800;
    private static final int DIMY = 600;
    
    public static void main(String[] args) throws UnsupportedEncodingException{
        
        
        
        File f = new File(System.getProperty("java.class.path"));
        File dir = f.getAbsoluteFile().getParentFile();
        String path = dir.toString();
        
        System.out.println(path);
        
        new ApplicationFrame(TITLE, DIMX, DIMY);
        
    }

}
