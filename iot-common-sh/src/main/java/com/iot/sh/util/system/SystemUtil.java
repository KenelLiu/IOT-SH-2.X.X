package com.iot.sh.util.system;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 *
 * @author Kenel Liu
 */
@Slf4j
public class SystemUtil {

    public  String getOSBit(){
        String system=System.getProperty("os.name").toLowerCase();
        if(log.isDebugEnabled())
            log.debug("os.name={}",system);
        if(system.startsWith("windows")){
            String arch = System.getenv("PROCESSOR_ARCHITECTURE");
            String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
            if(arch.endsWith("64")||
                    (wow64Arch != null && wow64Arch.endsWith("64"))){
                return "64";
            }else{
                return "32";
            }
        }else if(system.startsWith("linux")){
            Process process=null;
            BufferedReader bufferedReader=null;
            try {
                process= Runtime.getRuntime().exec("getconf LONG_BIT");
                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String bit=bufferedReader.readLine();
                if(bit.contains("64")){
                    return "64";
                }else{
                    return "32";
                }
            } catch (IOException e) {
                return "Unknown";
            }finally {
                if(bufferedReader!=null) try{bufferedReader.close();}catch (Exception ignored){};
                if(process!=null) try{process.destroy();}catch (Exception ignored){};
            }
        }
        return "Unknown";
    }

    public static boolean isWindowsOS(){
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().indexOf("windows")>-1){
            isWindowsOS = true;
        }
        return isWindowsOS;
    }
}
