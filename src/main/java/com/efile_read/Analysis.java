package com.efile_read;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Company: </p>
 * <p>Date: 2017/12/5 16:32</p>
 * <p>Copyright: 2016-2017 </p>
 *
 * @author hyq
 */
public class Analysis {

    private static final Logger log = LoggerFactory.getLogger(Analysis.class);

    public static List<File> getFiles(String path) {
        File root = new File(path);
        List<File> files = new ArrayList<File>();
        if (!root.isDirectory()) {
            files.add(root);
        } else {
            File[] subFiles = root.listFiles();
            for (File f : subFiles) {
                files.addAll(getFiles(f.getAbsolutePath()));
            }
        }
        return files;
    }

    public static List<Object> readFileByLines(File file) {
        BufferedReader reader = null;
        List<Object> listt = new ArrayList<Object>();
        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
//            reader = new BufferedReader(new FileReader(file));
            //改写 设置编码格式
            InputStream fileStream = new FileInputStream(file);
            byte[] b = new byte[3];
            fileStream.read(b);
            InputStreamReader isr = new InputStreamReader(fileStream, getEncoding(b));
            reader = new BufferedReader(isr);

            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</SolubleGasInOil>")) {
                    break;
                }
                if (!tempString.contains("<SolubleGasInOil>") && line > 2) {
                    tempString = tempString.replace("||", ",");
                    String[] s = tempString.split(",", -1);
                    listt.add(s);
                }
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return listt;
    }

    public static void main(String[] args) {
        List<File> files = getFiles("/Users/huangyanqiu/Documents/ddd/oilgas/ECMS_HISTORY");
        long startTime = System.currentTimeMillis();
        long totalDataLength = 0;
        FileOutputStream out;
        ObjectOutputStream objectOut = null;
        try {
            out = new FileOutputStream("hello.txt", true);
            objectOut = new ObjectOutputStream(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (File f : files) {
            List<Object> objects = readFileByLines(f);
            log.info("fileName : {} data length : {}  return Object : {}", f.getName(), objects.size(), JSON.toJSON(objects).toString());
            //循环加载到数据库
            totalDataLength += objects.size();
            try {
                objectOut.writeObject(objects);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("start time {}", startTime);
        log.info("end time {}", endTime);
        log.info("cost {}", endTime - startTime);
        log.info("dataLength {}", totalDataLength);
    }

    public static String getEncoding(byte[] b) {
        if (b[0] == -17 && b[1] == -69 && b[2] == -65) {//UTF-8
            return "UTF-8";
        } else {
            return "GBK";
        }
    }
}

