package com.mobaijun;

import com.mobaijun.parse.ReadmeContentAnalysis;
import com.mobaijun.util.ReadmeFileUtil;

/**
 * Description: [GitHub启动类]
 * Author: [mobaijun]
 * Date: [2023/11/27 13:51]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
public class GithubAppRun {

    public static void main(String[] args) {
        // 获取 GitHub 结果
        String githubResult = ReadmeContentAnalysis.getGithubResult("Heroic-Studio", "Google-Mirrors");

        // 解析并插入内容
        String content = ReadmeContentAnalysis.parsStringInsert(githubResult, "# 网站镜像", "# 机场推荐");

        // README 文件路径
        String readmeFilePath = "D:\\WebstormProjects\\kjs-blog\\source\\_posts\\【随笔】一些可用的-Google-搜索镜像站.md";

        // 插入内容到 README 文件
        ReadmeFileUtil.insertStringIntoReadme(readmeFilePath, content,
                "![](https://tencent.cos.mobaijun.com/img/blog/【随笔】一些可用的-Google-搜索镜像站/0.png)", "## 2023年4月9日更新");
    }
}
