package com.example.fetchrewards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{

    private RecyclerView recyclerView;
    private List<Item> itemList;
    private  RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        fetchData();
    }

    private void fetchData() {
        String url = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            parseData(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        Log.e("Volley Error", error.toString());
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void parseData(JSONArray jsonArray) throws JSONException {
        Map<Integer, List<Item>> groupedItems = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int listId = jsonObject.optInt("listId");
            String name = jsonObject.optString("name");

            if (!name.equals("null") && !name.isEmpty()) {
                Item item = new Item(jsonObject.optInt("id"), listId, name);

                if (!groupedItems.containsKey(listId)) {
                    groupedItems.put(listId, new ArrayList<>());
                }

                groupedItems.get(listId).add(item);
            }
        }

        // Sort the items based on listId and then by name
        List<Integer> sortedListIds = new ArrayList<>(groupedItems.keySet());
        Collections.sort(sortedListIds);

        itemList.clear();
        for (int listId : sortedListIds) {
            List<Item> items = groupedItems.get(listId);
            if (items != null) {
                items.sort(Comparator.comparing(Item::getId));
                itemList.addAll(items);
            }
        }

        // Set up RecyclerView adapter
        ItemAdapter itemAdapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Item selectedItem = itemList.get(position);

        // Start ItemDetailsActivity and pass the selected item
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra("ID", String.valueOf(selectedItem.getId()));
        intent.putExtra("ListID", String.valueOf(selectedItem.getListId()));
        intent.putExtra("Name", String.valueOf(selectedItem.getName()));
        startActivity(intent);
//        overridePendingTransition(R.anim.zoo, R.anim.slide_out_left);
    }
}

