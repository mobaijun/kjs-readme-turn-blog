package com.mobaijun.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: [GitHub API 调用返回的仓库文件结果。]
 * Author: [mobaijun]
 * Date: [2023/11/27 9:22]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Getter
@Setter
@ToString
public class Link {

    /**
     * 文件的自身链接。
     */
    private String self;

    /**
     * 文件的 Git 链接。
     */
    private String git;

    /**
     * 文件的 HTML 链接。
     */
    private String html;
}
