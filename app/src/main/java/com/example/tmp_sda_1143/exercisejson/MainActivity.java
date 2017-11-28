package com.example.tmp_sda_1143.exercisejson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private String content = getContentString();
    private JSONObject colorJsonObj = new JSONObject();
    private JSONArray colorJsonArr;
    private ArrayList<Color> colorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentToJson(content);
        jsonToList();
    }

    /**
     * Serializes the given string to JSON.
     * @param str The string to serialize into JSON
     * @return JSONObject
     */
    public JSONObject contentToJson(String str) {
        try {
            colorJsonObj = (JSONObject) new JSONTokener(str).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return colorJsonObj;
    }

    /**
     * Deserializes JSON to a list.
     * @return A list
     */
    public ArrayList<Color> jsonToList() {
        try {
            colorsList = new ArrayList<>();
            colorJsonArr = colorJsonObj.getJSONArray("colors");
            for (int i = 0; i < colorJsonArr.length(); i++) {
                JSONObject color = (JSONObject) colorJsonArr.get(i);

                JSONObject code = color.getJSONObject("code");

                JSONArray rgbaJson = code.getJSONArray("rgba");
                int[] rgba = new int[4];
                rgba[0] = rgbaJson.getInt(0);
                rgba[1] = rgbaJson.getInt(1);
                rgba[2] = rgbaJson.getInt(2);
                rgba[3] = rgbaJson.getInt(3);

                String hex = code.getString("hex");

                if (!color.has("type")) {
                    Color c = new Color(color.getString("color"), color.getString("category"),
                            new Code(rgba, hex));
                    colorsList.add(c);
                } else {
                    Color c = new Color(color.getString("color"), color.getString("category"),
                            color.getString("type"), new Code(rgba, hex));
                    colorsList.add(c);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return colorsList;
    }

    /**
     * Processes the JSON data and writes into the textView the number of colors
     * having green component equal to 255.
     */
    public void count(View view) {
        int count = 0;
        jsonToList();
        for (Color color : colorsList) {
            if (color.code.rgba[1] == 255) {
                count++;
            }
        }

        TextView input = (TextView) findViewById(R.id.count);
        input.setText("" + count);
    }

    /**
     * Processes the JSON data and writes into the textView a string
     * consisting of the concatenation of the color field (i.e. black)
     * of the colors having green component equal to 255.
     */
    public void list(View view) {
        String list = "";
        for (Color color : colorsList) {
            if (color.code.rgba[1] == 255) {
                list = list + color.colorString + " ";
            }
        }

        TextView input = (TextView) findViewById(R.id.list);
        input.setText(list);
    }

    /**
     * Modifies the JSON data, adding a new color
     * (color: orange, category: hue, rgba: 255,165,0,1, hex: #FA0),
     * then serializes the JSON data to a string and writes it into the textView.
     */
    public void modify(View view) {
        try {
            // Create a new color
            int[] arr = {255, 165, 0, 1};
            Color newColor = new Color("orange", "hue", new Code(arr, "#FA0"));

            // Convert the new color to JSON
            JSONObject colorJSON = new JSONObject();
            colorJSON.put("color", newColor.colorString);
            colorJSON.put("category", newColor.category);
            JSONObject code = new JSONObject();
            code.put("rgba", newColor.code.rgba);
            code.put("hex", newColor.code.hex);
            colorJSON.put("code", code);

            // Add the new color in JSON format to the storage
            colorJsonArr.put(colorJSON);

            // Update the string containing JSON
            content = colorJsonObj.toString(4);

            // Write the obtained string to the textView
            TextView input = (TextView) findViewById(R.id.modify);
            input.setText(content);
            input.setMovementMethod(new ScrollingMovementMethod());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Define the content of the string that stores JSON.
     * @return the string containing JSON
     */
    public String getContentString() {
        this.content = "{\n" +
                "  \"colors\": [\n" +
                "    {\n" +
                "      \"color\": \"black\",\n" +
                "      \"category\": \"hue\",\n" +
                "      \"type\": \"primary\",\n" +
                "      \"code\": {\n" +
                "        \"rgba\": [255,255,255,1],\n" +
                "        \"hex\": \"#000\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"color\": \"white\",\n" +
                "      \"category\": \"value\",\n" +
                "      \"code\": {\n" +
                "        \"rgba\": [0,0,0,1],\n" +
                "        \"hex\": \"#FFF\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"color\": \"red\",\n" +
                "      \"category\": \"hue\",\n" +
                "      \"type\": \"primary\",\n" +
                "      \"code\": {\n" +
                "        \"rgba\": [255,0,0,1],\n" +
                "        \"hex\": \"#FF0\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"color\": \"blue\",\n" +
                "      \"category\": \"hue\",\n" +
                "      \"type\": \"primary\",\n" +
                "      \"code\": {\n" +
                "        \"rgba\": [0,0,255,1],\n" +
                "        \"hex\": \"#00F\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"color\": \"yellow\",\n" +
                "      \"category\": \"hue\",\n" +
                "      \"type\": \"primary\",\n" +
                "      \"code\": {\n" +
                "        \"rgba\": [255,255,0,1],\n" +
                "        \"hex\": \"#FF0\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"color\": \"green\",\n" +
                "      \"category\": \"hue\",\n" +
                "      \"type\": \"secondary\",\n" +
                "      \"code\": {\n" +
                "        \"rgba\": [0,255,0,1],\n" +
                "        \"hex\": \"#0F0\"\n" +
                "      }\n" +
                "    },\n" +
                "  ]\n" +
                "}\n";

        return content;
    }
}
