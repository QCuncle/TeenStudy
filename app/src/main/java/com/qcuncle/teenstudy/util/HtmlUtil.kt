package com.qcuncle.teenstudy.util

import java.util.regex.Pattern

object HtmlUtil {
    /**
     * title标签
     */
    const val REGEX_TITLE = ".*?<title>(.*?)</title>.*"
    const val REGEX_OG_TITLE =
        "<meta[^>]+property=\"?og:title\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>"
    const val REGEX_TWITTER_TITLE =
        "<meta[^>]+property=\"?twitter:title\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>"

    /**
     * 描述标签
     */
    const val REGEX_DESCRIPTION =
        "<meta[^>]+name=\"?description\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>"
    const val REGEX_OG_DESCRIPTION =
        "<meta[^>]+name=\"?og:description\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>"
    const val REGEX_TWITTER_DESCRIPTION =
        "<meta[^>]+name=\"?twitter:description\"?[^>]+content=\"?([^>\"]*)\"?[^>]+>"

    /**
     * 描述内容
     */
    const val REGEX_CONTENT = "(?<=content=\").*[\\r|\\n]*(?=\".*[/]?>)"

    /**
     * 图片标签
     */
    const val LABEL_IMG_PNG = "http.*?.png"


    /**
     * 获取title
     */
    fun getTitle(html: String?): String {
        if (html == null) return ""
        val titleMatcher = Pattern.compile(REGEX_TITLE).matcher(html)
        return if (titleMatcher.matches()) titleMatcher.group(1)
            ?: getOgTitle(html) else getOgTitle(html)
    }

    /**
     * 获取描述
     */
    fun getDescription(html: String?): String? {
        if (html == null) return ""
        val descriptionMatcher = Pattern.compile(REGEX_DESCRIPTION).matcher(html)
        return if (descriptionMatcher.find()) {
            val contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(descriptionMatcher.group())
            if (contentMatcher.find()) contentMatcher.group() else getOgDescription(html)
        } else getOgDescription(html)
    }

    private fun getOgTitle(html: String): String {
        val titleMatcher = Pattern.compile(REGEX_OG_TITLE).matcher(html)
        return if (titleMatcher.find()) {
            val contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(titleMatcher.group())
            if (contentMatcher.find()) contentMatcher.group() else getTwitterTitle(html)
        } else getTwitterTitle(html)
    }

    private fun getTwitterTitle(html: String): String {
        val titleMatcher = Pattern.compile(REGEX_TWITTER_TITLE).matcher(html)
        return if (titleMatcher.find()) {
            val contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(titleMatcher.group())
            if (contentMatcher.find()) contentMatcher.group() else ""
        } else ""
    }

    private fun getOgDescription(html: String): String {
        val descMatcher = Pattern.compile(REGEX_OG_DESCRIPTION).matcher(html)
        return if (descMatcher.find()) {
            val contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(descMatcher.group())
            if (contentMatcher.find()) contentMatcher.group() else getTwitterDescription(html)
        } else getTwitterDescription(html)
    }

    private fun getTwitterDescription(html: String): String {
        val descMatcher = Pattern.compile(REGEX_TWITTER_DESCRIPTION).matcher(html)
        return if (descMatcher.find()) {
            val contentMatcher = Pattern.compile(REGEX_CONTENT).matcher(descMatcher.group())
            if (contentMatcher.find()) contentMatcher.group() else ""
        } else ""
    }
}