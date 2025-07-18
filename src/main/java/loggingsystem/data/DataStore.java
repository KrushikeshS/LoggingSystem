package loggingsystem.data;

import loggingsystem.pojo.Log;

import java.util.concurrent.TimeoutException;

public interface DataStore {
    void addLog(Log log);

    void appendLog() throws TimeoutException;
}
