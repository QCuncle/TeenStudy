package com.qcuncle.teenstudy.service

interface IHtml {
    public suspend fun getHtmlBody(url: String) : String?
}