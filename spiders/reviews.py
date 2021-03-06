import scrapy
import csv
from scrapy.selector import Selector

L = list()
S = set()

with open('beerSearch3.csv', encoding = "ISO-8859-1", newline='') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=' ', quotechar='|')
    for row in spamreader:
      t = ''
      for ele in row:
        t = t + ele
      if t in S: continue
      S.add(t)
      tmp = 'https://www.beeradvocate.com/beer/profile/' + t + '/'
      L.append(tmp)


class QuotesSpider(scrapy.Spider):
    name = "reviews"

    start_urls = L

    def parse(self, response):
        name = response.css('div.titleBar h1::text').extract()
        reviews = response.xpath('//div[contains(@id, "rating_fullview_content_2")]/text()').extract()

        yield {
                'Name': name,
                'Reviews': reviews
            }
        next_page = response.xpath('//a[contains(text(), "next")]/@href').extract_first()
        if next_page is not None:
            next_page = response.urljoin(next_page)
            yield scrapy.Request(next_page, callback=self.parse)

