elasticsearch的版本选用6.8.6与spring-data-elasticsearch3.2.5版本可以搭配使用，无冲突
其他版本可能有冲突，目前发现7.6.1与3.2.5查询时会报
Caused by: java.lang.NoSuchMethodError: org.elasticsearch.search.SearchHits.getTotalHits()J
原因es 的getTotalHits返回的类型为TotalHits,而spring-data-elasticsearch3.2.5返回类型却为long
