package com.yd.ecabinet.python;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.util.Properties;
import java.util.stream.Collectors;

public class PythonTest {

    ///home/yd/.conda/envs/tensorflow/bin/python
    private static String path = "C:/Users/Archimedes/PycharmProjects/first/hello.py";

    public static void main(String[] args) throws Exception {
        test4();
    }

    private static String read(InputStream in) {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(in))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO
    private static void test4() throws Exception {
        ProcessBuilder builder = new ProcessBuilder("python " + path);
        Process process = builder.start();
        String result = read(process.getInputStream());
        System.err.println(result);

        process.destroy();
    }

    private static void test3() throws Exception {
        String command = "python  " + path;
        System.out.println("execute command:" + command);
        Process process = Runtime.getRuntime().exec(command);
        InputStream in = process.getInputStream();
        System.out.println("result:" + read(in));
        process.waitFor();
        process.destroy();
    }

    private static void test2() throws IOException {
        init();
        PythonInterpreter interpreter = new PythonInterpreter();

        InputStream in = new FileInputStream(path);
        interpreter.execfile(in);

        PyFunction function = interpreter.get("add", PyFunction.class);
        PyObject r = function.__call__(new PyInteger(1), new PyInteger(2));
        System.out.println(r);
        in.close();
    }

    private static void test1() throws IOException {
        PythonInterpreter interpreter = new PythonInterpreter();
        InputStream in = new FileInputStream(path);
        interpreter.execfile(in);
        in.close();
    }

    private static void init() {
        Properties props = new Properties();
        props.put("python.home", "D:/work/python");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
    }
}
