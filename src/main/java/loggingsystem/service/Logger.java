package loggingsystem.service;

import loggingsystem.data.DataStore;
import loggingsystem.data.FileStore;
import loggingsystem.enums.Severity;
import loggingsystem.pojo.Log;
import loggingsystem.utils.DeepCopyUtil;

import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logger {
    private DataStore dataStore = new FileStore();

    private Set<Log> logTrackSet = new HashSet<>();

    private Queue<Set<Log>> logsProcessingQueue = new ArrayDeque<>();

    private Integer timeout;

    ExecutorService service = Executors.newFixedThreadPool(10);

    private static Logger logger = null;

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void addLog(Log log){
        synchronized (Logger.class){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Thread currentThread = Thread.currentThread();

            StackTraceElement[] elements = currentThread.getStackTrace();
            StringBuilder builder = new StringBuilder();

            int counter = 0;
            for(StackTraceElement element: elements){
                if(counter == 0){
                    builder.append(element.toString()).append("\n");
                    counter = 1;
                }else{
                    builder.append("\tat").append(element.toString()).append("\n");
                }
            }

            String stackTraceString = builder.toString();
            log.setStackTrace(stackTraceString);
            log.setTimestamp(timestamp);
            log.setThreadId(Long.toString(currentThread.getId()));
            log.setThreadName(currentThread.getName());
            log.setSeverity(log.getSeverity() == null ? Severity.LOW : log.getSeverity());
            put(logTrackSet,log);
        }
    }

    public void appendLog(){
        //handle excpetion from datastore
        synchronized (Logger.class){
            try{
                Set<Log> logTrackSetCopied = DeepCopyUtil.deepcopy(logTrackSet);
                put(logsProcessingQueue,logTrackSetCopied);
                flushLogTrackSet();

                service.submit(() -> {
                    try{
                        dataStore.appendLog(logsProcessingQueue.peek());
                        logsProcessingQueue.remove();
                    }catch (Exception ex){

                    }
                });


            }catch (Exception ex){

            }
        }
    }

    private void flushLogProccessingQueue(){
        logsProcessingQueue.clear();
    }

    private void flushLogTrackSet(){
        logTrackSet.clear();
    }

    private <T> void put(Collection<T> collection, T item){
        collection.add(item);
    }

    private void deleteLogs(){
        //if timeout occurs delete few logs form the file
    }
}
