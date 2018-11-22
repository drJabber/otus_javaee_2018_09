package rnk.l10.servlets.ws;

import com.google.gson.JsonArray;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoadResult {
    private boolean result;
    private JsonArray value;
}
