package cn.itcast;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

/**
 * Created by hqq on 2017/6/1.
 */
public class LuceneUpdateTest {

    //更新索引
    @Test
    public void LuceneUpdateTest1() throws IOException {
        //添加索引写入器对象
        Directory d = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST,new IKAnalyzer());
        IndexWriter indexWriter=new IndexWriter(d, conf);
        Document doc = new Document();
        doc.add(new StringField("id","9", Field.Store.YES));
        doc.add(new TextField("title","新加入的索引", Field.Store.YES));
        indexWriter.updateDocument(new Term("title","谷歌"),doc);
        indexWriter.commit();//提交
        indexWriter.close();//关闭
    }

    @Test
    public void deleteIndex() throws IOException{
        Directory d = FSDirectory.open(new File("F:\\maventools\\indexDir"));
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST,new IKAnalyzer());
        IndexWriter indexWriter=new IndexWriter(d, conf);
        indexWriter.deleteDocuments(new Term("id","1"));
        indexWriter.commit();//提交
        indexWriter.close();//关闭
    }
}
