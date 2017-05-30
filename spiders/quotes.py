import scrapy
import csv
from scrapy.selector import Selector

L = list()

with open('beerSearch.csv', encoding = "ISO-8859-1", newline='') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=' ', quotechar='|')
    for row in spamreader:
      tmp = 'http://www.bing.com/search?q='
      for ele in row:
        tmp = tmp + ele + '+'
      tmp = tmp + 'beer+advocate'
      L.append(tmp)


class QuotesSpider(scrapy.Spider):
    name = "quotes"

    start_urls = L

    def parse(self, response):
        a = response.css('h2 a').xpath('@href').re_first(r'/beer/profile/(.*)/')
        b = response.css('h2 a strong::text').extract_first().split('|')[0]
        b = b.strip(' ')

        yield {
                'url': a,
                'name': b,
            }
