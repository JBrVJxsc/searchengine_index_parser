package me.xuzh;

import me.xuzh.classes.Doc;
import me.xuzh.classes.Stemmer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Check parameter.
        if (args.length < 1) {
            System.out.println("No file name provided.");
            System.exit(-1);
        }

        // Check file.
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("Could not find: " + args[0] + ".");
            System.exit(-1);
        }

        // Read the file.
        System.out.println("Reading the file...");
        // Use a list to save all the Doc objects.
        List<Doc> docList = new ArrayList<Doc>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            Doc doc = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(".I")) {
                    // When we meet ".I", we start with a new Doc object.
                    doc = new Doc();
                    doc.setI(line.split(" ")[1]);
                } else if (line.equals(".U")) {
                    doc.setU(br.readLine());
                } else if (line.equals(".S")) {
                    doc.setS(br.readLine());
                } else if (line.equals(".M")) {
                    doc.setM(br.readLine());
                } else if (line.equals(".T")) {
                    doc.setT(br.readLine());
                } else if (line.equals(".P")) {
                    doc.setP(br.readLine());
                } else if (line.equals(".W")) {
                    doc.setW(br.readLine());
                } else if (line.equals(".A")) {
                    doc.setA(br.readLine());
                    // When we meet ".A", that means we have collected all information a Doc object needs.
                    // So just add this object to the document list.
                    docList.add(doc);
                }
            }
        } catch (Exception e) {
            System.out.println("Reading caught an exception:");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        System.out.println(docList.size() + " documents has been found.");

        // Indexing.
        System.out.println("Indexing...");
        indexing(docList);
        System.out.println("Indexing has been done.");
    }

    public static void indexing(List<Doc> docList) {
        String indexPath = "index";
        IndexWriter writer = null;
        try {
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(OpenMode.CREATE);
            writer = new IndexWriter(dir, iwc);
            for (Doc doc : docList) {
                indexDoc(writer, doc);
            }

            writer.forceMerge(1);
            writer.close();
        } catch (Exception e) {
            System.out.println("Indexing caught an exception:");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    static void indexDoc(IndexWriter writer, Doc doc) throws IOException {
        try {
            Document document = new Document();

            Field field_I = new StringField("I", doc.getI(), Field.Store.YES);
            Field field_U = new TextField("U", doc.getU(), Field.Store.YES);
            Field field_S = new TextField("S", doc.getS(), Field.Store.YES);
            Field field_P = new TextField("P", doc.getP(), Field.Store.YES);
            Field field_A = new TextField("A", doc.getA(), Field.Store.YES);
            String field = doc.getT() + " " + doc.getM() + " " + doc.getW();
            field = new Stemmer().stem(field);
            Field field_T = new TextField("T", field, Field.Store.YES);

            document.add(field_I);
            document.add(field_U);
            document.add(field_S);
            document.add(field_P);
            document.add(field_A);
            document.add(field_T);

            writer.addDocument(document);
        } catch (Exception e) {
            System.out.println("Dealing with document caught an exception:");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}