package com.mobaijun.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Description: [readme 文件工具类]
 * Author: [mobaijun]
 * Date: [2023/11/27 9:18]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
public class ReadmeFileUtil {

    /**
     * 向README文件的指定位置插入字符串
     *
     * @param readmeFilePath  README文件的路径
     * @param insertionString 要插入的字符串
     * @param startNode       开始节点标记
     * @param endNode         结束节点标记
     */
    public static void insertStringIntoReadme(String readmeFilePath, String insertionString,
                                              String startNode, String endNode) {
        try {
            // 读取README文件中的内容
            String content = FileReader.create(FileUtil.file(readmeFilePath)).readString();

            // 查找开始节点和结束节点的位置
            int startIndex = content.indexOf(startNode);
            int endIndex = content.indexOf(endNode);

            // 检查开始和结束节点的位置是否有效
            if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                log.error("无效的开始或结束节点位置,开始：{}，结束：{}", startNode, endNode);
                return;
            }

            // 在开始节点和结束节点之间插入字符串
            String prefix = content.substring(0, startIndex + startNode.length());
            String suffix = content.substring(endIndex);

            String modifiedContent = prefix + "\n" + insertionString + "\n" + suffix;

            // 将修改后的内容写回README文件
            Path readmePath = FileUtil.file(readmeFilePath).toPath();
            Files.writeString(readmePath, modifiedContent, StandardOpenOption.TRUNCATE_EXISTING);

            log.info("字符串插入成功。");
        } catch (IOException e) {
            log.error("字符串插入失败：{}", e.getMessage());
        }
    }
}
