package cn.itcast;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hqq on 2017/6/1.
 */
public class LuceneCreate {

   /* @Test
    public void indexTest() throws IOException {
        //目标：在指定目录创建索引
        //1.创建文档对象
        Document document = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document.add(new StringField("id","1", Field.Store.YES));
        document.add(new TextField("title","谷歌地图之父跳槽FaceBook", Field.Store.YES));
        //document.add(new TextField("title","谷歌地图之父跳槽FaceBook,喜闻乐见", Field.Store.YES));
        //3.创建目录对象，指定索引库的存放位置；FSDirectory文件系统；RAMDirectory内存
        Directory directory = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        //4.创建分词器对象
        Analyzer analyzer = new StandardAnalyzer();
        //5.创建索引写入器配置对象，第一个参数版本VerSion.LATEST,第一个参数分词器
        IndexWriterConfig confi = new IndexWriterConfig(Version.LATEST,analyzer);
        //设置打开方式  追加  覆盖
        confi.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        //6.创建索引写入器
        //参数：目录  配置对象
        IndexWriter indexWriter = new IndexWriter(directory, confi);
        //7.向索引库写入文档对象
        indexWriter.addDocument(document);
        //8.提交 关闭
        indexWriter.commit();
        indexWriter.close();
    }*/



    @Test
    public void indexTest2() throws IOException {
        //目标：在指定目录创建索量创建文档对象及索引
        List<Document> list = new ArrayList<>();
        //1.创建文档对象
        Document document1 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document1.add(new StringField("id","1", Field.Store.YES));
        document1.add(new TextField("title","谷歌地图之父跳槽FaceBook,来到了传智播客,学习代码，技术大牛碉堡了啊", Field.Store.YES));
        list.add(document1);

        Document document2 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document2.add(new StringField("id","2", Field.Store.YES));
        TextField field = new TextField("title", "谷歌地图之父加盟FaceBook", Field.Store.YES);
        //作弊  设置激励因子  让该条记录评分变高  排名靠前
        field.setBoost(10f);
        document2.add(field);


        list.add(document2);

        Document document3 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document3.add(new StringField("id","3", Field.Store.YES));
        document3.add(new TextField("title","谷歌地图创始人拉斯离开谷歌加盟Facebook", Field.Store.YES));
        list.add(document3);

        Document document4 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document4.add(new StringField("id","4", Field.Store.YES));
        document4.add(new TextField("title","谷歌地图之父跳槽Facebook与Wave项目取消有关", Field.Store.YES));
        list.add(document4);

        Document document5 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document5.add(new StringField("id","5", Field.Store.YES));
        document5.add(new TextField("title","谷歌地图之父拉斯加盟社交网站Facebook", Field.Store.YES));
        list.add(document5);



        //3.创建目录对象，指定索引库的存放位置；FSDirectory文件系统；RAMDirectory内存
        Directory directory = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        //4.创建分词器对象
       // Analyzer analyzer = new StandardAnalyzer();
        IKAnalyzer analyzer = new IKAnalyzer();
        //5.创建索引写入器配置对象，第一个参数版本VerSion.LATEST,第一个参数分词器
        IndexWriterConfig confi = new IndexWriterConfig(Version.LATEST,analyzer);
        //设置打开方式  追加  覆盖
        confi.setOpenMode(IndexWriterConfig.OpenMode.CREATE);//覆盖
        //6.创建索引写入器
        //参数：目录  配置对象
        IndexWriter indexWriter = new IndexWriter(directory, confi);
        //7.向索引库写入文档对象
        indexWriter.addDocuments(list);
        //8.提交 关闭
        indexWriter.commit();
        indexWriter.close();
    }

    /*@Test
    public void indexTest3() throws IOException {
        //目标：在指定目录创建索量创建文档对象及索引
        List<Document> list = new ArrayList<>();
        //1.创建文档对象
        Document document1 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document1.add(new IntField("id",1, Field.Store.YES));
        document1.add(new TextField("title","谷歌地图之父跳槽FaceBook,来到了传智播客,学习代码，技术大牛碉堡了啊", Field.Store.YES));
        list.add(document1);

        Document document2 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document2.add(new IntField("id",2, Field.Store.YES));
        document2.add(new TextField("title","谷歌地图之父加盟FaceBook", Field.Store.YES));
        list.add(document2);

        Document document3 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document3.add(new IntField("id",3, Field.Store.YES));
        document3.add(new TextField("title","谷歌地图创始人拉斯离开谷歌加盟Facebook", Field.Store.YES));
        list.add(document3);

        Document document4 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document4.add(new IntField("id",4, Field.Store.YES));
        document4.add(new TextField("title","谷歌地图之父跳槽Facebook与Wave项目取消有关", Field.Store.YES));
        list.add(document4);

        Document document5 = new Document();
        //2.向文档添加字段
        // 添加字段，参数Field是一个接口，要new实现类的对象(StringField, TextField)
        // StringField的实例化需要3个参数：1-字段名，2-字段值，3-是否保存到文档，Store.YES存储，NO不存储
        document5.add(new IntField("id",5, Field.Store.YES));
        document5.add(new TextField("title","谷歌地图之父拉斯加盟社交网站Facebook", Field.Store.YES));
        list.add(document5);



        //3.创建目录对象，指定索引库的存放位置；FSDirectory文件系统；RAMDirectory内存
        Directory directory = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        //4.创建分词器对象
        // Analyzer analyzer = new StandardAnalyzer();
        IKAnalyzer analyzer = new IKAnalyzer();
        //5.创建索引写入器配置对象，第一个参数版本VerSion.LATEST,第一个参数分词器
        IndexWriterConfig confi = new IndexWriterConfig(Version.LATEST,analyzer);
        //设置打开方式  追加  覆盖
        confi.setOpenMode(IndexWriterConfig.OpenMode.CREATE);//覆盖
        //6.创建索引写入器
        //参数：目录  配置对象
        IndexWriter indexWriter = new IndexWriter(directory, confi);
        //7.向索引库写入文档对象
        indexWriter.addDocuments(list);
        //8.提交 关闭
        indexWriter.commit();
        indexWriter.close();
    }*/



}
