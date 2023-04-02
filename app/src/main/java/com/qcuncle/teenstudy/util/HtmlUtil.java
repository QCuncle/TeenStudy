package com.qcuncle.teenstudy.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Author: LiuQingchen
 * @Date: 2022/1/7 16:04
 * @Description: HtmlUtil
 */
public class HtmlUtil {
    /**
     * <title></title>
     */
    private static final String REGEX_TITLE = ".*?<title>(.*?)</title>.*";
    private static final String REGEX_OG_TITLE = "<meta[^>]+property=\"?og:title\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>";
    private static final String REGEX_TWITTER_TITLE = "<meta[^>]+property=\"?twitter:title\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>";
    /**
     * <meta name="description">
     */
    // .*?<meta[^>]+name="?description"?[^>]+content="?(.*?)"?[^>]*>.*
    //".*?<meta[^>]+name=\"?description\"?[^>]+content=\"?([^>]+)\"?[^>]+>.*";
    private static final String REGEX_DESCRIPTION = "<meta[^>]+name=\"?description\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>";
    private static final String REGEX_OG_DESCRIPTION = "<meta[^>]+name=\"?og:description\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>";
    private static final String REGEX_TWITTER_DESCRIPTION = "<meta[^>]+name=\"?twitter:description\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>";
    private static final String REGEX_CONTENT = "(?<=content=\").*[\\r|\\n]*(?=\".*[/]?>)";
    /**
     * <link rel="icon"/>
     */
    private static final String REGEX_LINK_ICON = "<link rel=\"icon\".*?>"; // href =
    /**
     * <img></img>
     */
    private static final String REGEX_IMG = "<img.*?>";
    /**
     * "http.*?.png"
     */
    private static final String LABEL_IMG_PNG = "http.*?.png";


    /**
     * 从指定Url获取Html内容转化为String对象
     *
     * @param url
     * @return
     */
    public static String getHtmlFromUrl(String url) {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() {
                if (url == null) return null;
                return getHtmlContent(url);
            }
        });
        //创建线程
        Thread thread = new Thread(futureTask);
        //启动线程
        thread.start();
        try {
            return futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从指定Url获取Html内容转化为String对象
     *
     * @param url
     * @return
     */
    private static String getHtmlContent(String url) {
        String htmlText;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            htmlText = body != null ? body.string() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return htmlText.replaceAll("\r\n|\r|\n", "");
    }

    /**
     * 通过Html内容获取title,description,img_url
     *
     * @param regex         获取标签的正则（例如"<img.*?>"标签）
     * @param htmlContent   提取标签的内容
     * @param isDistinguish 是否区分大小写
     * @return
     */
    private static String getHtmlLabel(final String regex, final String htmlContent, boolean isDistinguish) {
        if (htmlContent == null || regex == null) return null;
        Pattern compile;
        if (isDistinguish) {
            compile = Pattern.compile(regex);
        } else {
            compile = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        Matcher matcher = compile.matcher(htmlContent);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 获取title
     *
     * @param html
     * @return
     */
    public static String getTitle(String html) {
        if (html == null) return "";
        Matcher titleMatcher = Pattern.compile(REGEX_TITLE).matcher(html);
        return titleMatcher.matches() ? titleMatcher.group(1) : getOgTitle(html);
    }

    private static String getOgTitle(String html) {
        if (html == null) return "";
        Matcher titleMatcher = Pattern.compile(REGEX_OG_TITLE).matcher(html);
        if (titleMatcher.find()) {
            Matcher contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(titleMatcher.group());
            return contentMatcher.find() ? contentMatcher.group() : getTwitterTitle(html);
        } else return getTwitterTitle(html);
    }

    private static String getTwitterTitle(String html) {
        if (html == null) return "";
        Matcher titleMatcher = Pattern.compile(REGEX_TWITTER_TITLE).matcher(html);
        if (titleMatcher.find()) {
            Matcher contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(titleMatcher.group());
            return contentMatcher.find() ? contentMatcher.group() : "";
        } else return "";
    }

    /**
     * 获取描述
     *
     * @param html
     * @return
     */
    public static String getDescription(String html) {
        if (html == null) return "";
        Matcher descriptionMatcher = Pattern.compile(REGEX_DESCRIPTION).matcher(html);
        if (descriptionMatcher.find()) {
            Matcher contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(descriptionMatcher.group());
            return contentMatcher.find() ? contentMatcher.group() : getOgDescription(html);
        } else return getOgDescription(html);
    }

    private static String getOgDescription(String html) {
        if (html == null) return "";
        Matcher descMatcher = Pattern.compile(REGEX_OG_DESCRIPTION).matcher(html);
        if (descMatcher.find()) {
            Matcher contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(descMatcher.group());
            return contentMatcher.find() ? contentMatcher.group() : getTwitterDescription(html);
        } else return getTwitterDescription(html);
    }

    private static String getTwitterDescription(String html) {
        if (html == null) return "";
        Matcher descMatcher = Pattern.compile(REGEX_TWITTER_DESCRIPTION).matcher(html);
        if (descMatcher.find()) {
            Matcher contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(descMatcher.group());
            return contentMatcher.find() ? contentMatcher.group() : "";
        } else return "";
    }

    /**
     * 获取图片
     *
     * @param htmlContent
     * @return
     */
    public static String getImgUrl(String htmlContent) {
        String label = getHtmlLabel(LABEL_IMG_PNG, htmlContent, false);
        if (label == null) return "";
        if (label.contains("http")) {
            label = label.substring(label.lastIndexOf("http"));
            if (label.contains(" ")) return "";
        }
        return label;
    }

    /**
     * 获取描述
     *
     * @param htmlContent
     * @return
     */
    public static String getDescriptionFromContent(String htmlContent) {
        String label = getHtmlLabel("<meta name=\"?description\"?.*?>", htmlContent, false);
        if (label == null) return "";
        int beginIndex;
        int endIndex;
        if (label.contains("\"")) {
            beginIndex = label.indexOf("content=\"");
            endIndex = label.lastIndexOf("\"");
            label = label.substring(beginIndex + 9, endIndex);
        } else { // 适配解析出来的label属性不带引号的html
            beginIndex = label.indexOf("content=");
            endIndex = label.lastIndexOf("/");
            label = label.substring(beginIndex + 8, endIndex);
        }
        return label;
    }

}
