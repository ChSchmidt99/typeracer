package requests;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * Custom GSON TypeAdapter, allows {@link Request} interface to be encoded and decoded.
 */
public class RequestInterfaceAdapter implements JsonSerializer<Request>, JsonDeserializer<Request> {

  private static final String CLASSNAME = "CLASSNAME";
  private static final String DATA = "DATA";

  @Override
  public JsonElement serialize(Request src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty(CLASSNAME, src.getClass().getName());
    jsonObject.add(DATA, context.serialize(src));
    return jsonObject;
  }

  @Override
  public Request deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
          throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    JsonPrimitive primitive = jsonObject.get(CLASSNAME).getAsJsonPrimitive();
    Class objectClass = getObjectClass(primitive.toString());
    return context.deserialize(jsonObject.get(DATA), objectClass);
  }

  private Class getObjectClass(String name) {
    try {
      return Class.forName(name);
    } catch (ClassNotFoundException e) {
      throw new JsonParseException(e.getMessage());
    }
  }

}
