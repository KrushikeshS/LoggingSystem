package loggingsystem.data;

import loggingsystem.pojo.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

public class FileStore implements DataStore{

    @Override
    public void appendLog(Collection<Log> logs) throws TimeoutException {
        try{
            File file = new File("test.log");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Log log:logs){
                oos.writeObject(log);
            }
            fos.close();
            oos.close();

        }catch (Exception ex){
            System.err.println("File not found or not able to open");
        }
    }

    @Override
    public void deleteLog(){
        //delete old 30% logs
    }
}
