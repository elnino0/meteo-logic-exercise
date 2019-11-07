import com.google.common.collect.Ordering;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class TestWordsServer {

    String uri = "http://localhost:8080/myapp/words/all";

    @BeforeClass
    public  void  before(){
        Main.startServer();
    }

    @Test
    public void testNoneExsingWord() throws URISyntaxException, IOException {
        final URIBuilder uribuilder = new URIBuilder(uri);
        String word = "oidism";
        uribuilder.addParameter("word",word);

        HttpGet get = new HttpGet(uribuilder.build());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);

        String body = inputStreamToString(response.getEntity().getContent(),"UTF-8");
        System.out.println(" response -- " + body);
        Assert.assertTrue(!body.contains(word));
        response = httpClient.execute(get);
        body = inputStreamToString(response.getEntity().getContent(),"UTF-8");
        System.out.println(" response -- " + body);

        Assert.assertTrue(body.contains(word));
    }

    @Test
    public void findSuggestion() throws URISyntaxException, IOException {
        final URIBuilder uribuilder = new URIBuilder(uri);
        String word = "bi";
        uribuilder.addParameter("word", word);

        HttpGet get = new HttpGet(uribuilder.build());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);
        String body = inputStreamToString(response.getEntity().getContent(),"UTF-8");
        System.out.println("body -" + body);

        Assert.assertTrue(body.contains(word));
    }

    @Test
    public void findSuggestionFullWord() throws URISyntaxException, IOException {
        final URIBuilder uribuilder = new URIBuilder(uri);
        String word = "pneumonoultramicroscopicsilicovolcanoconiosis";
        uribuilder.addParameter("word", word);

        HttpGet get = new HttpGet(uribuilder.build());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);
        String body = inputStreamToString(response.getEntity().getContent(),"UTF-8");
        System.out.println("body -" + body);
        Assert.assertTrue(body.contains(word));
    }

    @Test
    public void findSuggestionOrder() throws URISyntaxException, IOException {
        final URIBuilder uribuilder = new URIBuilder(uri);
        String word = "abc";
        uribuilder.addParameter("word", word);

        HttpGet get = new HttpGet(uribuilder.build());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);
        String body = inputStreamToString(response.getEntity().getContent(),"UTF-8");
        System.out.println("body -" + body);
        String[] splite = body.split(",");

        Assert.assertTrue(Ordering.natural().isOrdered(Arrays.asList(splite)));
        Assert.assertTrue(body.contains(word));
    }

    private  String inputStreamToString(InputStream stream, String encoding) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(stream, writer, encoding);
        return writer.toString();
    }

}
