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
    name = "beers"

    start_urls = L

    def parse(self, response):
        name = response.css('div.titleBar h1::text').extract_first()
        brewery = response.css('div.titleBar h1 span::text').extract_first().split("|")[1].strip()
        state = response.xpath('//div[contains(@class, "break")]/a[contains(@href, "place")]/text()').extract_first()
        score = response.xpath('//span[contains(@class, "ba-score")]/text()').extract_first()
        style = response.xpath('//div[contains(@class, "break")]/a[contains(@href, "style")]/b/text()').extract_first()
        abv = response.xpath('//div[contains(@class, "break")]/text()').extract()[17]
        abv = ''.join(abv.split())

        yield {
                'Name': name,
                'Brewery': brewery,
                'State': state,
                'Score': score,
                'Style': style,
                'Abv': abv
            }

