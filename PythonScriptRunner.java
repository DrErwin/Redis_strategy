package com.gl.lw.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PythonScriptRunner<T>{


    public  T runResObj(String inputData, String pythonEnvPath,String pythonScriptPath) {
//        List<String> strings = run(inputData, pythonEnvPath, pythonScriptPath, Integer.MAX_VALUE);
//        //strings 拼接为一个String 并返回
//        StringBuilder sb = new StringBuilder();
//        for (String s : strings) {
//            sb.append(s);
//        }
//
//        String sb_str = sb.toString();
//        System.out.println("======= sb_str =======");
//        System.out.println(sb_str);

        String sb_str = runResStr(inputData, pythonEnvPath, pythonScriptPath);

        // 将sb_str {"code": 200, "message": "success", "data": [345, 305, 470, 738, 573, 425, 414, 3799, 4971, 5094]}
        // 将sb_str解析为json 并获取data为List<Integer>
        // 创建 ObjectMapper 对象
        ObjectMapper mapper = new ObjectMapper();
        ResponseResult<T> responseResult;
        try {
            TypeReference<ResponseResult<T>> typeRef = new TypeReference<ResponseResult<T>>() {};
//            TypeReference<ResponseResult<T>> typeRef = new TypeReference<>() {};
            responseResult = mapper.readValue(sb_str, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return responseResult.getData();
    }


    public static String runResStr(String inputData, String pythonEnvPath,String pythonScriptPath) {
        List<String> strings = run(inputData, pythonEnvPath, pythonScriptPath, Integer.MAX_VALUE);
        //strings 拼接为一个String 并返回
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
        }
        // 从 sb.toString() 中取出 @%% %%@ 之间包围的字符串
        String sb_str = sb.toString();
        int start = sb_str.indexOf("@%%");
        int end = sb_str.lastIndexOf("%%@");
        if (start == -1 || end == -1) {
            throw new RuntimeException("未找到 @%% %%@ 之间的字符串");
        }
        sb_str = sb_str.substring(start + 3, end);
        return sb_str;
    }

    public static List<String> run(String inputData,String pythonEnvPath, String pythonScriptPath) {
        return run( inputData, pythonEnvPath, pythonScriptPath, Integer.MAX_VALUE);
    }

    public static List<String> run(String inputData,String pythonEnvPath, String pythonScriptPath, int outputLinesCount ) {

        List<String> outputLines = new ArrayList<>();
        try {

            // 创建 ProcessBuilder 并指定要执行的 Python 脚本
            ProcessBuilder pb = new ProcessBuilder(pythonEnvPath, pythonScriptPath);
            pb.directory(new File(pythonScriptPath).getParentFile()); // 设置工作目录为 Python 项目根路径
            Map<String, String> env = pb.environment();
            env.put("PYTHONIOENCODING", "utf-8");
            Process process = pb.start();


            // 获取进程的输入和输出流
            PrintWriter stdin = new PrintWriter(process.getOutputStream(), true);
//            PrintWriter stdin = new PrintWriter(process.getOutputStream(), true, StandardCharsets.UTF_8);
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

            // 向 Python 脚本发送输入数据
            stdin.println(inputData);
            stdin.flush();

            // 读取 Python 脚本的输出结果
            for (int i = 0; i < outputLinesCount; i++) {
                String line = stdout.readLine();
                if (line != null) {
                    outputLines.add(line);
                } else {
                    break;
                }
            }

            // 关闭流和进程
            stdin.close();
            stdout.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return outputLines;
    }

    public static void main(String[] args) {

        // ------------------ 测试runResObj() ------------------
        //        List<Integer> potentialNodeIds = PythonScriptRunnerrunResObj(/*省略参数*/);
        String pythonEnvPath = "D:\\MyProgram\\winget\\Anaconda3\\envs\\gpu2\\python.exe";
        String pythonScriptPath = "";

//       pythonScriptPath = "E:\\FromIN\\第一次\\python-in\\system_sync\\forService\\javaRun.py";

//        pythonScriptPath = "E:\\FromIN\\第一次\\python-in\\system_sync" +
//                "\\xunyuan_LightREGNN\\methods\\A_baseline_copy24\\A24_my_run_yzt_YS2.py";

        pythonScriptPath= "E:\\FromIN\\第一次\\python-in\\system_sync\\xunyuan_LightREGNN\\methods\\A_baseline_copy24\\javaRun3.py";


//        String companyId = "123456";
        int companyId = 806;
        int matchCount = 10;

        // 将上面的 companyId 和 matchCount 存入map 转为json 传给python脚本
        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("companyId", companyId);
        argsMap.put("matchCount", matchCount);

        // 使用 springboot 默认的json 序列化工具将 map 转为 json 字符串
        ObjectMapper mapper = new ObjectMapper();
        String pythonScriptArgs = null;
        try {
            pythonScriptArgs = mapper.writeValueAsString(argsMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        String s = PythonScriptRunner.runResStr(pythonScriptArgs, pythonEnvPath, pythonScriptPath);
//        System.out.println("s: " + s);

        Map<String,List<Integer>> resultMap = new PythonScriptRunner<Map<String,List<Integer>>>()
                .runResObj(pythonScriptArgs, pythonEnvPath,pythonScriptPath);
        System.out.println("nodeIds: " + resultMap.get("node_ids"));
        List<Integer> node_ids = resultMap.get("node_ids");
        List<Integer> recommend_scores = resultMap.get("recommend_scores");
        System.out.println("recommend_scores: " + recommend_scores);
        System.out.println("nodeIds2: " + node_ids);


        // 1. 调用Python脚本获取潜在客户的node_id列表
//        List<Integer> nodeIds = new PythonScriptRunner<List<Integer>>()
//                .runResObj(pythonScriptArgs, pythonEnvPath,pythonScriptPath);
//        System.out.println("nodeIds: " + nodeIds);
    }
    
}
