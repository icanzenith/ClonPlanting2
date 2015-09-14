package sugarcaneselection.thaib.org.clonplanting2.util;

import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.Transformer;
import com.google.gson.Gson;

/**
 * Created by Jitpakorn on 1/7/15 AD.
 */
public class GsonTransformer implements Transformer {

    public GsonTransformer() {
    }

    @Override
    public <T> T transform(String url, Class<T> type, String encoding,
                           byte[] data, AjaxStatus status) {
        Gson g = new Gson();
        return g.fromJson(new String(data), type);
    }
}

