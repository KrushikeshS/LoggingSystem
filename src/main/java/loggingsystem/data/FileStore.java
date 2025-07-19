package loggingsystem.data;

import loggingsystem.pojo.Log;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class FileStore implements DataStore{

    private static final String LOG_FILE_NAME = "test.log";

    @Override
    public void appendLog(Collection<Log> logs) throws TimeoutException {
        try{
            File file = new File(LOG_FILE_NAME);
            FileOutputStream fos = new FileOutputStream(file,true);
            ObjectOutputStream oos;

            if(file.length() == 0){
                oos = new ObjectOutputStream(fos);
            }else{
                oos = new ObjectOutputStream(fos){
                    @Override
                    protected void writeStreamHeader(){
                        //do not write this header
                    }
                };
            }

            for(Log log:logs){
                oos.writeObject(log);
            }

            oos.flush();
            oos.close();

        }catch (Exception ex){
            System.err.println("File not found or not able to open" + ex.getMessage());
        }
    }

    @Override
    public void deleteLog(){
        //delete old 30% logs
        File file = new File(LOG_FILE_NAME);
        if(!file.exists() || !file.canRead()){
            return;
        }

        List<Log> allLogs = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(ois);

            while (true){
                try{
                    Log log = (Log) ois.readObject();
                    allLogs.add(log);
                }catch (EOFException ex){
                    break;
                }
            }
        }catch (Exception ex){
            System.err.println("Errror reading logs for deletion: " + ex.getMessage());
            return;
        }finally {
            try{
                if(ois != null) ois.close();
            }catch (Exception ex){
                System.err.println(ex.getMessage());
            }
            try{
                if(fis != null) fis.close();
            }catch (Exception ex){
                System.err.println(ex.getMessage());
            }
        }

        if(allLogs.isEmpty()){
            return;
        }

        Collections.sort(allLogs, Comparator.comparing(Log::getTimestamp));

        int logsToKeepCount = (int) (allLogs.size() * 0.70);
        if(logsToKeepCount==0 && !allLogs.isEmpty()){
            logsToKeepCount = 1;
        }

        List<Log> logsToRetain = allLogs.subList(allLogs.size() - logsToKeepCount, allLogs.size());

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file, false);
            oos = new ObjectOutputStream(fos);

            for (Log log : logsToRetain) {
                oos.writeObject(log);
            }
            oos.flush();
            System.out.println("Old logs successfully deleted and file rewritten.");
        } catch (java.io.IOException ex) {
            System.err.println("Error rewriting log file after deletion: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (oos != null) oos.close();
            } catch (java.io.IOException e) {
                System.err.println("Error closing ObjectOutputStream after delete rewrite: " + e.getMessage());
            }
            try {
                if (fos != null) fos.close();
            } catch (java.io.IOException e) {
                System.err.println("Error closing FileOutputStream after delete rewrite: " + e.getMessage());
            }
        }
    }

}
