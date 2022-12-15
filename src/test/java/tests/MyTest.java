package tests;

import org.testng.annotations.Test;

import java.io.IOException;
import java.net.InetAddress;

public class MyTest {
    @Test
    public static void systemTest() throws IOException {
        System.out.println(Runtime.getRuntime().exec("hostname"));
        System.out.println(InetAddress.getLocalHost().getHostName());
    }
}
