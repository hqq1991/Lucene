package cn.itcast;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created by hqq on 2017/6/1.
 */
public class LuceneSeracherTest {

    @Test
    public void searchTest() throws IOException, ParseException {
        //索引的查询
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"title","id"},new IKAnalyzer());
        Query query = queryParser.parse("谷歌");
        doSearch(query);

    }

    @Test
    public void serachTermTest() throws IOException, ParseException {
        //词条查询 词条就是最小的查询单位 不能再分割
        //创建查询的对象
        //Query query = new TermQuery(new Term("title","大牛"));//1条
        Query query = new TermQuery(new Term("title","大牛2"));//0条
        doSearch(query);
    }

    @Test
    public void genericSearch() throws IOException, ParseException{
        //通配符查询
        Query query = new WildcardQuery(new Term("title","*拉斯*"));//0？
        doSearch(query);
    }

    @Test
    public void mohuSearch() throws IOException, ParseException{
        //模糊查询  参数term(字段对象) 最远编辑距离
        Query query = new FuzzyQuery(new Term("title","facebook"),2);
        doSearch(query);
    }

    @Test
    public void numberRangeSearch() throws IOException, ParseException{
        //数据范围查询
        Query query = NumericRangeQuery.newIntRange("id",2,4,true,false);
        doSearch(query);
    }

    @Test
    public void zongheSearch() throws IOException, ParseException{
        //综合查询
        Query query1 = NumericRangeQuery.newIntRange("id",2,4,true,false);
        Query query2 =  new WildcardQuery(new Term("title","*拉*"));
        BooleanQuery query = new BooleanQuery();
        query.add(query1, BooleanClause.Occur.MUST);
        query.add(query2, BooleanClause.Occur.MUST);
        doSearch(query);
    }



    //抽取代码为方法
    public void doSearch(Query query) throws IOException, ParseException {
        //普通方式查询索引
        //1.初始化索引库对象
        Directory directory = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        //2.创建索引读取工具
        IndexReader indexReader = DirectoryReader.open(directory);
        //3.创建索引搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4.创建解析器对象
        //QueryParser queryParser = new QueryParser("title",new IKAnalyzer());//单字段
        //MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"title","id"},new IKAnalyzer());
        //Query query = queryParser.parse("2");
        //6.执行查询操作 返回topdocs
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        //解析结果
        System.out.println("查询到的记录数："+topDocs.totalHits);
        //获得得分文档数组
        ScoreDoc[] scores = topDocs.scoreDocs;
        for (ScoreDoc score : scores) {
            System.out.println("得分："+score.score);
            System.out.println("编号："+score.doc);
            Document document = indexReader.document(score.doc);
            System.out.println("title:"+document.get("title"));
            System.out.println("id:"+document.get("id"));
        }
    }
}
