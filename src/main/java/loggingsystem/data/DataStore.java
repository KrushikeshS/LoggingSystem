package loggingsystem.data;

import loggingsystem.pojo.Log;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public interface DataStore {

    void appendLog(Collection<Log> logs) throws TimeoutException;

    void deleteLog();
}
