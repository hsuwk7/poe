package net.faintedge.poe;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import net.faintedge.poe.skilltree.SkillTree;

import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class SkillTreeParser {

  private static final Gson GSON = new Gson();

  public SkillTree parse(URL url) throws IOException {
    OkHttpClient httpClient = new OkHttpClient();
    Request request = new Request.Builder().url(url).get().build();
    Response response = httpClient.newCall(request).execute();
    return GSON.fromJson(response.body().charStream(), SkillTree.class);
  }

}
