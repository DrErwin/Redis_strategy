/*
 * @Author: Yuhong Wu
 * @Date: 2024-10-01 12:56:33
 * @LastEditors: Yuhong Wu
 * @LastEditTime: 2024-10-01 12:56:53
 * @Description: 
 */

import java.lang.instrument.Instrumentation;

public class ObjectSizeFetcher {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}