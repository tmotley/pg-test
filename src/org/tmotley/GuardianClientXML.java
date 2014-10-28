package org.tmotley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import java.io.StringReader;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

//XMLBeans or similar for XML!

public class GuardianClientXML {

    static String baseUrl = "http://content.guardianapis.com/";
    static String sectionsEndPt = "sections";
    static String apiKey = "ag8jnrk9fx83sp5sr7xfru35";

    public static void main(String[] args) throws Exception{
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("content.guardianapis.com")
                .setPath("/sections")
                .setParameter("api-key", apiKey)
                .setParameter("format", "xml")
                /*.setParameter("btnG", "Google Search")
                .setParameter("aq", "f")
                .setParameter("oq", "")*/
                .build();
        System.out.println("URI is : " + uri.toString());
        HttpGet get = new HttpGet(uri);
        HttpClient httpclient = HttpClients.createDefault();
        HttpResponse resp = httpclient.execute(get);
        HttpEntity entity = resp.getEntity();
       /* ContentType contentType = ContentType.getOrDefault(resp.getEntity());
        Charset charset = contentType.getCharset();*/
        String content = EntityUtils.toString(entity);
        System.out.println("Response is : " + content);
        SAXBuilder sax = new SAXBuilder();
        Document doc = sax.build(new StringReader(content));

        Element root = doc.getRootElement().getChild("results");
        XMLOutputter xout = new XMLOutputter();
        for (Object e : root.getChildren("result")) {
            Element elem = (Element) e;
            System.out.println("Next child " + elem.getAttribute("web-title"));
            xout.output(elem, System.out);
        }
       /* for (Map i: results) {
            System.out.println("Next Result " + i.get("id"));
        }
        System.out.println("which?");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        System.out.println(choice);*/
    }

    static Map printAndStripHeaders(LinkedHashMap<String,Object> json) {
        for (Object k:json.keySet()) {
            System.out.println("Next Key " + k.toString());
        }
        return null;
    }
}

