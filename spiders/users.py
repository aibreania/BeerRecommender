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
    name = "users"

    start_urls = L

    def parse(self, response):
        users = response.css("a.username::attr(href)").extract()
        List = list()
        for i in range(len(users)):
          if i%2 == 1: continue
          t = re.sub('/community/members/', '', users[i])
          List.append(t)

        yield {
                'Users': List
            }

