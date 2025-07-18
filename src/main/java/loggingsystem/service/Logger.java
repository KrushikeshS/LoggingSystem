package loggingsystem.service;

import loggingsystem.data.FileStore;
import loggingsystem.pojo.Log;

import java.util.Queue;
import java.util.Set;

public class Logger {
    private FileStore fileStore;
    private Set<Log> logTrackSet;
    private Queue<Set<Log>> logsProcessingQueue;
    private Integer timeout;

    private static Logger logger = null;

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void addLog(Log log){
        // add timestamp thread and stack trace
    }

    public void appendLog(){
        //handle excpetion from datastore
    }

    private void flushLogProccessingQueue(){

    }

    private void put(){

    }

    private void deleteLogs(){

    }
}
