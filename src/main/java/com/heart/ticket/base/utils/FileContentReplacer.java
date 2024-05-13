package com.heart.ticket.base.utils;

import java.io.*;

/**
 * 递归遍历"D:\98-极客时间\李文飞"目录下的所有*.md文件，并替换其中出现的"D:/98-极客时间/"为"D:/98-极客时间/李文飞/"
 * @author wfli
 * @since 2024/5/13 10:38
 */
public class FileContentReplacer {

    public static void main(String[] args) {
        // 要遍历的目录
        String directory = "D:\\98-极客时间\\李文飞";
        // 调用递归方法遍历目录并替换文件内容
        replaceContentInDirectory(new File(directory));

        System.out.println(" === 修复任务结束 ===");
    }

    public static void replaceContentInDirectory(File directory) {
        // 检查目录是否存在
        if (directory.exists() && directory.isDirectory()) {
            // 获取目录下的所有文件和子目录
            File[] files = directory.listFiles();
            // 遍历文件和子目录
            for (File file : files) {
                // 如果是目录，则递归调用本方法
                if (file.isDirectory()) {
                    replaceContentInDirectory(file);
                } else {
                    // 如果是文件且是以 .md 结尾的 Markdown 文件，则替换文件内容
                    if (file.getName().endsWith(".md")) {
                        replaceContent(file);
                    }
                }
            }
        }
    }

    public static void replaceContent(File file) {
        try {
            // 读取文件内容
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // 替换内容
                line = line.replace("D:/98-极客时间/", "D:/98-极客时间/李文飞/");
                content.append(line).append("\n");
            }
            reader.close();

            // 将替换后的内容写回文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toString());
            writer.close();
            System.out.println(" *** "+file.getName()+"文件图片修复完成..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
