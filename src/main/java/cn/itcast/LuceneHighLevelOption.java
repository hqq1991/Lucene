package cn.itcast;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.File;
import java.io.IOException;

/**
 * Created by hqq on 2017/6/1.
 */
public class LuceneHighLevelOption {

    //lucene的高级操作
    @Test
    public void highLight() throws IOException, ParseException, InvalidTokenOffsetsException {
        //对搜索出来的符合条件的内容高亮显示
        Directory dir = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        //2.创建索引读取工具
        IndexReader indexReader = DirectoryReader.open(dir);
        //3.创建索引搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4.创建解析器对象
        //QueryParser queryParser = new QueryParser("title",new IKAnalyzer());//单字段
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"title","id"},new IKAnalyzer());
        Query query = queryParser.parse("谷歌Facebook");
        //6.执行查询操作 返回topdocs
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
        //准备高亮工具的初始化
        Formatter formatter= new SimpleHTMLFormatter("<em>","</em>");
        Scorer fragmentScorer= new QueryScorer(query);
        Highlighter h1 = new Highlighter(formatter, fragmentScorer);
        //解析结果
        System.out.println("查询到的记录数："+topDocs.totalHits);
        //获得得分文档数组
        ScoreDoc[] scores = topDocs.scoreDocs;
        for (ScoreDoc score : scores) {
            System.out.println("得分："+score.score);
            System.out.println("编号："+score.doc);
            //读取工具通过文档编号获得文档对象
            Document document = indexReader.document(score.doc);
            //对符合条件的内容高亮输出
            String bestFragment = h1.getBestFragment(new IKAnalyzer(), "title", document.get("title"));
            System.out.println("高亮内容"+bestFragment);

            System.out.println("id:"+document.get("id"));
        }
    }

    @Test
    public void testSortQuery() throws Exception {
        //排序查询  对查出来的内容进行排序
        Directory dir = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"title","id"},new IKAnalyzer());
        Query query = queryParser.parse("谷歌");
        Sort sort = new Sort(new SortField("id", SortField.Type.STRING));
        TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE,sort);//默认false正向排序
        System.out.println("查询到的记录数："+topDocs.totalHits);
        //获得得分文档数组
        ScoreDoc[] scores = topDocs.scoreDocs;
        for (ScoreDoc score : scores) {
            System.out.println("得分："+score.score);
            System.out.println("编号："+score.doc);
            Document document = reader.document(score.doc);
            System.out.println("title:"+document.get("title"));
            System.out.println("id:"+document.get("id"));
        }
    }

    @Test
    public void testPageQuery() throws Exception {
        //分页查询
        int pageSize=2;//每页记录数
        int pageNum=2;//查询的页码
        int startIndex = pageSize*(pageNum-1);
        int endIndex = startIndex+pageSize;
        //排序查询  对查出来的内容进行排序
        Directory dir = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"title","id"},new IKAnalyzer());
        Query query = queryParser.parse("谷歌");
        Sort sort = new Sort(new SortField("id", SortField.Type.STRING));
        TopDocs topDocs = indexSearcher.search(query, endIndex,sort);//默认false正向排序  查询末尾索引数量的记录数
        System.out.println("查询到的记录数："+topDocs.totalHits);
        //获得得分文档数组
        ScoreDoc[] scores = topDocs.scoreDocs;
        for (int i=startIndex;i<endIndex;i++) {
            System.out.println("得分："+scores[i].score);
            System.out.println("编号："+scores[i].doc);
            Document document = reader.document(scores[i].doc);
            System.out.println("title:"+document.get("title"));
            System.out.println("id:"+document.get("id"));
        }
    }



}
