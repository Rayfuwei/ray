package com.springbootelasticsearch;

import com.springbootelasticsearch.config.Item;
import com.springbootelasticsearch.config.ItemRepository;
import com.springbootelasticsearch.service.ElasticSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class ItemTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ElasticSearchService elasticSearchService;



    /**
     * 新建/删除索引
     */
    @Test
    void test() {
        elasticsearchTemplate.createIndex(Item.class);
        //elasticsearchTemplate.deleteIndex(Item.class);
    }

    /**
     * 新增或修改文档
     */
    @Test
    public void insert() {
//		Item item = new Item(1L, "小米手机7", " 手机", "小米", 3499.00, "http://image.baidu.com/13123.jpg");
        Item item = new Item(2L, "三星Note10+", " 手机", "三星", 2499.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save(item);
    }

    /**
     * 查询Item索引下所有文档.
     * 此处需要注意，如果Item实体中有含参构造函数而缺少了无参构造函数，itemRepository.findAll查询会报映射实体失败的错误
     */
    @Test
    public void query() {
        Iterable<Item> list = itemRepository.findAll(Sort.by("price").ascending());
        for(Item item : list) {
            System.out.println(item.toString());
        }
    }

    @Test
    public void singleTitle() {

        PageRequest page = PageRequest.of (0,10);
        String title = "三星";
        List<Item> items = elasticSearchService.singleTitle (title, page);
        System.out.println (items.get (0).toString ());
    }

    @Test
    public void singlePhraseMatch() {

        PageRequest page = PageRequest.of (0,10);
        String title = "三星";
        List<Item> items = elasticSearchService.singlePhraseMatch (title, page);
        System.out.println (items.get (0).toString ());
    }


    @Test
    public void queryall() {

        PageRequest page = PageRequest.of (0,10);
        String title = "三星";
//        List<Item> items = elasticSearchService.queryall (page);
        elasticSearchService.query(page);
       // System.out.println (items.get (0).toString ());
    }

}