package com.mobaijun.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: [ GitHub API 调用返回的仓库文件结果。]
 * Author: [mobaijun]
 * Date: [2023/11/27 9:21]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class GithubResult {

    /**
     * 文件名。
     */
    private String name;

    /**
     * 文件在仓库中的路径。
     */
    private String path;

    /**
     * 文件的 SHA 哈希值。
     */
    private String sha;

    /**
     * 文件的大小（字节）。
     */
    private int size;

    /**
     * 文件的 URL。
     */
    private String url;

    /**
     * 文件的 HTML URL。
     */
    private String htmlUrl;

    /**
     * 文件的 Git URL。
     */
    private String gitUrl;

    /**
     * 文件的下载 URL。
     */
    private String downloadUrl;

    /**
     * 文件的类型（例如 "file" 或 "dir"）。
     */
    private String type;

    /**
     * 文件的内容。
     */
    private String content;

    /**
     * 文件内容的编码（例如 "base64"）。
     */
    private String encoding;

    /**
     * 与文件相关联的链接对象。
     */
    private Link link;
}
