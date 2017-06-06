import scrapy
import csv
import re
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
    name = "users2"

    start_urls = L

    def parse(self, response):
        users = response.xpath('//div[@id="rating_fullview_content_2"]/div/span[@class="muted"]/a[@class="username"]/text()').extract()
        overall = response.xpath('//div[@id="rating_fullview_content_2"]/span[@class="muted"][1]/text()').extract()
        beer = response.xpath('//div[@class="titleBar"]/h1/text()').extract_first()

        for i in range(len(overall)):
            t = re.findall(r"\d*\.\d+|\d+",overall[i])
            sum = 0
            rng = len(t) - 1
            if rng == 0:
              overall[i] = t[0]
              continue
            for j in range(rng):
                sum = sum + float(t[j])
            sum = sum/rng
            sum = float("{0:.2f}".format(sum))
            overall[i] = sum

        users = '$%#'.join(users)
        overall = '$%#'.join(map(str, overall))

        yield {
                'Users': users,
                'Score': overall,
                'beer': beer
            }

        next_page = response.xpath('//a[contains(text(), "next")]/@href').extract_first()
        if next_page is not None:
                next_page = response.urljoin(next_page)
                yield scrapy.Request(next_page, callback=self.parse)

