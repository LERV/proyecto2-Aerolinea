package com.luis.edward.airlineapp;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by estadm on 7/6/2018.
 */

public class UsersController {
    private static UsersController instanceUsers;


    //Variables para guardar usuario en sesion
    private String id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String profile_picture;
    private String id_flights;
    String record_kilometers;


    //Variable para usar Volley para APIs
    private RequestQueue mRequestQueue;
    private Cache cache;
    private Network network;
    private JsonArrayRequest jsonArrayRequest;


    public String URL_api="https://vuela-tiquicia-airline.herokuapp.com/users";


    //Variable utilizada para el PUT
    String textName;
    String textLastName;
    String textViewEmail;
    String textPassword;




    //Arrelgar y quitar
    private ArrayList<ArrayList> all_json_users = new ArrayList<ArrayList>();
    private ArrayList<String> USER_CREDENTIALS=new ArrayList<>();


    public static UsersController getInstance(){
        if(instanceUsers == null){
            instanceUsers = new UsersController();
        }
        return instanceUsers;
    }


    public ArrayList<ArrayList> getAll_json_users() {
        return all_json_users;
    }

    public ArrayList<String> getUserCredentials() {
        return USER_CREDENTIALS;
    }

    public String impDatos()
    {
        Log.i("Imp","Llammmo funcion");
        String temp="";
        for (String i:USER_CREDENTIALS)
        {
            temp+=i;
        }
        return temp;
    }



    public void setSessionUser(int idActiveUser)
    {

        Log.d("SET","Va a hacer SET");
        id = all_json_users.get(idActiveUser).get(0).toString();
        name = all_json_users.get(idActiveUser).get(1).toString();
        last_name = all_json_users.get(idActiveUser).get(2).toString();
        email = all_json_users.get(idActiveUser).get(3).toString();
        password = all_json_users.get(idActiveUser).get(4).toString();
        Log.d("Password",all_json_users.get(idActiveUser).get(4).toString());
        Log.d("ProfilePicture",all_json_users.get(idActiveUser).get(5).toString());
        //if all_json_users.get(idActiveUser).get(5).toString()
        profile_picture = all_json_users.get(idActiveUser).get(5).toString();
        id_flights = all_json_users.get(idActiveUser).get(6).toString();
        record_kilometers = all_json_users.get(idActiveUser).get(7).toString();

    }

    public static String reverse(String forward) {
        StringBuilder builder = new StringBuilder(forward);
        String reverse = builder.reverse().toString();
        return reverse;
    }

    public void downloadDataFromAPi(File getCacheDir) // Pasar getCacheDir()
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
                URL_api+".json",
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
                                id = user.getString("id");
                                name = user.getString("name");
                                last_name = user.getString("last_name");
                                email = user.getString("email");
                                password = user.getString("password");
                                profile_picture = user.getString("profile_picture");
                                id_flights = user.getString("id_flights");
                                record_kilometers = user.getString("record_kilometers");


                                //Log.d("JSON_VAR:",profile_picture+"pruebaetc");

                                json_user.add(id);
                                json_user.add(name);
                                json_user.add(last_name);
                                json_user.add(email);
                                json_user.add(password);
                                json_user.add(profile_picture);
                                json_user.add(id_flights);
                                json_user.add(record_kilometers);




                                all_json_users.add(json_user);
                                //Actualizar todos los credenciales para el login
                                USER_CREDENTIALS.add(json_user.get(0)+":"+json_user.get(3)+":"+reverse(json_user.get(4)));
                                //USER_Data.add(json_user.get(1)+":"+json_user.get(2)+":"+json_user.get(4)+":"+json_user.get(5)+":"+json_user.get(6));
                                Log.d("USERS-JSON:",USER_CREDENTIALS.get(i));
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Log.d("Error","No pudo entrar al API /users: "+error);
                    }
                }

        );

        SystemClock.sleep(3000);
        // Adding request to request queue
        mRequestQueue.add(jsonArrayRequest);


//-------------------- FIN Bloque para bajar users de API

    }

    public void putUser(Context contexto,String idSelected,String ptextName,String ptextLastName, String ptextViewEmail)
    {
        textName=ptextName;
        textLastName=ptextLastName;
        textViewEmail=ptextViewEmail;


        //---------Hacer PUT al API
        RequestQueue MyRequestQueue = Volley.newRequestQueue(contexto);


        String url = URL_api+idSelected+".json";
        StringRequest MyStringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", textName); //Add the data you'd like to send to the server.
                MyData.put("last_name", textLastName);
                MyData.put("email", textViewEmail);

                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);




        // //---------FIN PUT al API

    }

    public void postUser(Context contexto,String ptextName,String ptextLastName, String ppassword ,String ptextViewEmail)
    {
        textName=ptextName;
        textLastName=ptextLastName;
        textViewEmail=ptextViewEmail;
        textPassword=ppassword;

        RequestQueue MyRequestQueue = Volley.newRequestQueue(contexto);

        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, URL_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error",error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", textName); //Add the data you'd like to send to the server.
                MyData.put("last_name", textLastName);
                MyData.put("password", textPassword);
                MyData.put("email", textViewEmail);

                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

        Log.d("FIN:","TERMINO COD");


//-------------------- FIN Bloque para bajar users de API

    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getId_flights() {
        return id_flights;
    }
}
