# -*- coding: utf-8 -*-
import scrapy


class ExapleSpider(scrapy.Spider):
    name = 'exaple'
    allowed_domains = ['example.com']
    start_urls = ['http://example.com/']

    def parse(self, response):
        pass
