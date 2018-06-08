package com.luis.edward.airlineapp;

import android.os.SystemClock;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by estadm on 7/6/2018.
 */

public class FlightsController {
    private static FlightsController instanceUsers;

    RequestQueue mRequestQueue;
    Cache cache;
    Network network;
    JsonArrayRequest jsonArrayRequest;

    //Arrelgar y quitar
    static ArrayList<ArrayList> all_json_users = new ArrayList<ArrayList>();
    private static ArrayList<String> USER_CREDENTIALS=new ArrayList<>();

    private FlightsController(){}

    public static FlightsController getInstance(){
        if(instanceUsers == null){
            instanceUsers = new FlightsController();
        }
        return instanceUsers;
    }

    private void downloadDataFromAPi(File getCacheDir) // Pasar getCacheDir()
    {
        //--------------------Bloque para bajar users de API

        //request_json(activityName);
        //Instantiate the cache
        cache = new DiskBasedCache(getCacheDir, 1024 * 1024); // 1MB cap

        //Set up the network to use HttpURLConnection as the HTTP client.
        network = new BasicNetwork(new HurlStack());
        //Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        //Start the queue
        mRequestQueue.start();
        // Initialize a new JsonArrayRequest instance
        jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                //ip de la maquina, cel y compu deben estar en misma red
                "https://beach-finder.herokuapp.com/users.json",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        // Process the JSON
                        Log.d("mop",response.toString());
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject user = response.getJSONObject(i);
                                //lista donde sera guardada la info
                                ArrayList<String> json_user = new ArrayList<String>();
                                String id = user.getString("id");
                                String name = user.getString("name");
                                String last_name = user.getString("last_name");
                                String nationality = user.getString("nationality");
                                String profile_picture = user.getString("profile_picture");
                                String phone_number = user.getString("phone_number");
                                String email = user.getString("email");
                                String password = user.getString("password");
                                String location = user.getString("location");

                                json_user.add(id);
                                json_user.add(name);
                                json_user.add(last_name);
                                json_user.add(nationality);
                                json_user.add(profile_picture);
                                json_user.add(phone_number);
                                json_user.add(email);
                                json_user.add(password);
                                json_user.add(location);

                                all_json_users.add(json_user);
                                //Actualizar todos los credenciales para el login
                                USER_CREDENTIALS.add(json_user.get(6)+":"+json_user.get(7));
                                //USER_Data.add(json_user.get(1)+":"+json_user.get(2)+":"+json_user.get(4)+":"+json_user.get(5)+":"+json_user.get(6));
                                Log.d("USERS-JSON:",USER_CREDENTIALS.get(i));
                            }
                            /*//Actualizar todos los credenciales
                            int cont=0;
                            for (ArrayList<ArrayList<>> userTemp :all_json_users)
                            {
                                USER_CREDENTIALS[cont]=userTemp.get(6)+":"+userTemp.get(7);
                                Log.d("USERS-JSON:",USER_CREDENTIALS[cont]);
                                cont++;
                            }*/
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("Error","No pudo entrar al API /users");
                    }
                }

        );

        SystemClock.sleep(3000);
        // Adding request to request queue
        mRequestQueue.add(jsonArrayRequest);




//-------------------- FIN Bloque para bajar users de API

    }


}
