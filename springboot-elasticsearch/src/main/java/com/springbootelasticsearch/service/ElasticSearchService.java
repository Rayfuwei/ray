package com.springbootelasticsearch.service;

import com.springbootelasticsearch.config.Item;
import com.springbootelasticsearch.config.ItemRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;


@Service
public class ElasticSearchService {

    @Autowired
    private  ElasticsearchRestTemplate template;



    private static ElasticsearchRestTemplate elasticsearchTemplate;


    @Autowired
    private ItemRepository rep;


    @Autowired
    private static ItemRepository itemRepository;


    @PostConstruct
    public void init() {
        elasticsearchTemplate = this.template;

        itemRepository=this.rep;
    }

    public static void createIndex() {
        elasticsearchTemplate.createIndex (Item.class);
    }

    /**
     * 新增或修改文档
     */
    public static void insert() {
//		Item item = new Item(1L, "小米手机7", " 手机", "小米", 3499.00, "http://image.baidu.com/13123.jpg");
        Item item = new Item (2L, "三星Note10+", " 手机", "三星", 2499.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save (item);
    }

    /**
     * 查询Item索引下所有文档.
     * 此处需要注意，如果Item实体中有含参构造函数而缺少了无参构造函数，itemRepository.findAll查询会报映射实体失败的错误
     */
    public static void query(@PageableDefault Pageable pageable) {
      //  Iterable<Item> list = itemRepository.findAll (Sort.by ("price").ascending ());
       // Iterable<Item> list = itemRepository.findAll ();
        SearchQuery searchQuery = new NativeSearchQueryBuilder ().withQuery (QueryBuilders.queryStringQuery ("三星")).withPageable (pageable).build ();
        //Iterable<Item> list = itemRepository.search (searchQuery);
        Iterable<Item> list = elasticsearchTemplate.queryForList (searchQuery, Item.class);
        for (Item item : list) {
            System.out.println (item.toString ());
        }
    }

    /**
     * 单字符串模糊查询，默认排序。将从所有字段中查找包含传来的word分词后字符串的数据集
     *
     * @param word
     * @param pageable
     * @return
     */
    public static List<Item> singleTitle(String word, @PageableDefault Pageable pageable) {
        //使用queryStringQuery完成单字符串查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder ().withQuery (QueryBuilders.queryStringQuery (word).field ("brand")).withPageable (pageable).build ();
        return elasticsearchTemplate.queryForList (searchQuery, Item.class);
    }

    /**
     * 单字段对某字符串模糊查询
     */
    public static Object singleMatch(String title, String brand, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder ().withQuery (matchQuery ("title", title)).withPageable (pageable).build ();
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("brand", brand)).withPageable(pageable).build();
        return elasticsearchTemplate.queryForList (searchQuery, Item.class);
       // return itemRepository.search (searchQuery);
    }

    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果
     */
    public List<Item> singlePhraseMatch(String title, @PageableDefault Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder ().withQuery (QueryBuilders.matchPhraseQuery ("title", title)).withPageable (pageable).build ();
        return elasticsearchTemplate.queryForList (searchQuery, Item.class);
    }

    /**
     * term匹配，即不分词匹配，你传来什么值就会拿你传的值去做完全匹配
     */
    public Object singleTerm(String title, @PageableDefault Pageable pageable) {
        //不对传来的值分词，去找完全匹配的
        SearchQuery searchQuery = new NativeSearchQueryBuilder ().withQuery (termQuery ("title", title)).withPageable (pageable).build ();
        return elasticsearchTemplate.queryForList (searchQuery, Item.class);

    }

    /**
     * 多字段匹配
     */
    public Object singleUserId(String title, @PageableDefault(sort = "weight", direction = Sort.Direction.DESC) Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder ().withQuery (QueryBuilders.multiMatchQuery (title, "title", "content")).withPageable (pageable).build ();
        return elasticsearchTemplate.queryForList (searchQuery, Item.class);
    }

    /**
     * 单字段包含所有输入
     */
    public static Object contain(String title) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder ().withQuery (QueryBuilders.matchQuery ("title", title).operator (Operator.AND)).build ();
        return elasticsearchTemplate.queryForList (searchQuery, Item.class);
    }

    /**
     * 多字段合并查询
     */
    public static Object bool(String title, String brand, Integer weight) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder ().withQuery (QueryBuilders.boolQuery ().must (termQuery ("brand", brand))
                .should (rangeQuery ("weight").lt (weight)).must (matchQuery ("title", title))).build ();
        return elasticsearchTemplate.queryForList (searchQuery, Item.class);
    }


}