package org.sopt.util;

public class IdGeneratorUtil {
    static int sequence;
    public static int generateId(){
        return sequence++;
    }
}
