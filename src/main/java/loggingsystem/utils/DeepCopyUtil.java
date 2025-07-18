package loggingsystem.utils;

import java.io.*;

public class DeepCopyUtil {
    public static <T> T deepcopy(T original) throws IOException, ClassNotFoundException {
        // object stream to byte stream
        // byte stram to object stream

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(original);
        oos.flush();

        ByteArrayInputStream bias = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bias);

        T copy = (T) ois.readObject();

        oos.close();
        bias.close();
        baos.close();
        ois.close();

        return copy;
    }
}
