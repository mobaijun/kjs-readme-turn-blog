package com.mobaijun.parse;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mobaijun.constant.Constant;
import com.mobaijun.result.GithubResult;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: [readme 内容解析]
 * Author: [mobaijun]
 * Date: [2023/11/27 9:19]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 */
@Slf4j
@UtilityClass
public class ReadmeContentAnalysis {

    /**
     * 获取 GitHub 仓库的 README 文件内容。
     *
     * @param username GitHub 用户名
     * @param repoName GitHub 仓库名
     * @return README 文件内容
     */
    public static String getGithubResult(String username, String repoName) {
        String apiUrl = StrUtil.format(Constant.GITHUB_API, username, repoName);

        // 创建信任所有SSL证书的HttpClient
        SSLContext sslContext = createTrustAllSSLContext();

        // 创建 HttpClient
        HttpClient httpClient = HttpClient.newBuilder().sslContext(sslContext).build();

        try {
            HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder().uri(URI.create(apiUrl)).GET().build(), HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // 解析 JSON 获取加密后的内容
                GithubResult githubResult = JSONUtil.toBean(response.body(), GithubResult.class);

                // 提取加密后的内容并解码
                return Base64.decodeStr(githubResult.getContent());
            } else {
                log.error("Failed to get README information. Status code: {}", response.statusCode());
            }
        } catch (Exception e) {
            log.error("获取失败：{}", e.getMessage());
        }
        return null;
    }

    /**
     * 创建信任所有SSL证书的SSLContext。
     *
     * @return SSLContext 实例
     */
    private static SSLContext createTrustAllSSLContext() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }}, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在原始内容中解析并插入指定标记之间的内容，并返回包含日期、分隔线和解析内容的新字符串。
     *
     * @param originalContent 原始内容，包含待解析的文本
     * @param startTag        开始标记，标识待解析内容的起始位置
     * @param endTag          结束标记，标识待解析内容的结束位置
     * @return 包含日期、分隔线和解析内容的新字符串
     */
    public static String parsStringInsert(String originalContent, String startTag, String endTag) {
        // 构建动态的正则表达式，使用 Pattern.quote 避免正则表达式中的特殊字符干扰
        String regex = Pattern.quote(startTag) + "([\\s\\S]*?)" + Pattern.quote(endTag);

        // 使用正则表达式匹配器，Pattern.DOTALL 使点号可以匹配所有字符，包括换行符
        Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(originalContent);

        // 获取匹配的内容，去除首尾空白
        String parsedContent = "";

        // 查找匹配的内容
        while (matcher.find()) {
            parsedContent = matcher.group(1).trim();
        }

        // 构建新字符串，包含当前日期、分隔线和解析的内容
        return Constant.NEW_IN_CHAR_ACT + Constant.H2 + formatDate(LocalDate.now()) + Constant.NEW_IN_CHAR_ACT +
                parsedContent + Constant.NEW_IN_CHAR_ACT + Constant.SPEAR +
                Constant.NEW_IN_CHAR_ACT;
    }

    /**
     * 格式化日期为 "yyyy 年 M 月 d 日更新" 格式的字符串
     *
     * @param date 要格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(LocalDate date) {
        // 使用中文 Locale
        Locale chineseLocale = Locale.CHINA;

        // 创建自定义日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy 年 M 月 d 日更新", chineseLocale);

        // 格式化日期
        return date.format(formatter);
    }
}
