package restServer;


import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/words")
public class RestRequests {
    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSuggestion(@QueryParam("word") String word) {
        List<String> words = sreachEngine.sreachEngine.getInstance().findSuggentendsOrAdd(word,5);
        String outcome = "";
        for(String str : words){
            outcome+=str + ",";
        }
        return  outcome;
    }
}
