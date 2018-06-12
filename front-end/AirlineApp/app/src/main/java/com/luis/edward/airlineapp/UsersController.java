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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by estadm on 7/6/2018.
 */

public class UsersController {
    private static UsersController instanceUsers;


    //Variables para guardar usuario en sesion
    private String idSession;
    private String nameSession;
    private String last_nameSession;
    private String emailSession;
    private String passwordSession;
    private String profile_pictureSession;
    private String id_flightsSession;
    String record_kilometersSession;


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

    Boolean userSessionState=false; //Para ssaber si entro con user


    //Arrelgar y quitar
    private ArrayList<ArrayList> all_json_users = new ArrayList<ArrayList>();
    //private ArrayList<String> USER_CREDENTIALS;


    public static UsersController getInstance(){
        if(instanceUsers == null){
            instanceUsers = new UsersController();
        }
        return instanceUsers;
    }


    public ArrayList<ArrayList> getAll_json_users() {
        return all_json_users;
    }




    private String searchPosfromJson(String pIdActiveUser)
    {
        String posfromJson="-1";
        //nameSession = all_json_users.get(idActiveUser).get(0).toString();
        for (int i=0; i<all_json_users.size();i++) {
            //String[] pieces = usersCtrl.getUserCredentials().get(i).split(":");
            // Log.d("Contrasenna:id",pieces[2]+":"+pieces[0]);
            if (all_json_users.get(i).get(0).toString().equals(pIdActiveUser)) {
                posfromJson=String.valueOf(i);
                break;
                //Devuelve la posicion donde esta el registo del User en session
            }
        }
        return posfromJson;

    }


    public void setSessionUser(String pIdActiveUser)
    {

        //Hacer ciclo para arreglar que se cae cuando se hacer un PUT porque el all_jasn viene con lo dato en
        //direfente orden y entonces el idActive no calza



        int idPositionReg;
        idPositionReg=Integer.parseInt(searchPosfromJson(pIdActiveUser));

        Log.d("SET","Va a hacer SET");
        idSession =  String.valueOf(pIdActiveUser);
        nameSession = all_json_users.get(idPositionReg).get(1).toString();
        last_nameSession = all_json_users.get(idPositionReg).get(2).toString();
        emailSession = all_json_users.get(idPositionReg).get(3).toString();
        passwordSession = all_json_users.get(idPositionReg).get(4).toString();
        Log.d("Password",all_json_users.get(idPositionReg).get(4).toString());
        Log.d("ProfilePicture",all_json_users.get(idPositionReg).get(5).toString());
        //if all_json_users.get(idPositionReg).get(5).toString()
        profile_pictureSession = all_json_users.get(idPositionReg).get(5).toString();
        id_flightsSession = all_json_users.get(idPositionReg).get(6).toString();
        record_kilometersSession = all_json_users.get(idPositionReg).get(7).toString();

    }

    public static String reverse(String forward) {
        StringBuilder builder = new StringBuilder(forward);
        String reverse = builder.reverse().toString();
        return reverse;
    }

    public void downloadDataFromAPi(File getCacheDir) // Pasar getCacheDir()
    {

        //Arrelgar y quitar

        //USER_CREDENTIALS=new ArrayList<>();
                //USER_CREDENTIALS.add("");
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
                                String id = user.getString("id");
                                String name = user.getString("name");
                                String last_name = user.getString("last_name");
                                String email = user.getString("email");
                                String password = user.getString("password");
                                String profile_picture = user.getString("profile_picture");
                                String id_flights = user.getString("id_flights");
                                String record_kilometers = user.getString("record_kilometers");


                                //Log.d("JSON_VAR:",profile_picture+"pruebaetc");

                                json_user.add(id);
                                json_user.add(name);
                                json_user.add(last_name);
                                json_user.add(email);
                                String tempPassword=reverse(password);
                                json_user.add(tempPassword);
                                json_user.add(profile_picture);
                                json_user.add(id_flights);
                                json_user.add(record_kilometers);




                                all_json_users.add(json_user);
                                //Actualizar todos los credenciales para el login
                                //USER_CREDENTIALS.add(json_user.get(0)+":"+json_user.get(3)+":"+json_user.get(4));
                                //USER_Data.add(json_user.get(1)+":"+json_user.get(2)+":"+json_user.get(4)+":"+json_user.get(5)+":"+json_user.get(6));
                                //Log.d("USERS-JSON:",USER_CREDENTIALS.get(i));
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
        //return true;
    }
    /*public void downloadDataFromAPi2(File getCacheDir) // Pasar getCacheDir()
    {

        //Arrelgar y quitar

        //USER_CREDENTIALS=new ArrayList<>();
        //USER_CREDENTIALS.add("");
        //--------------------Bloque para bajar users de API

        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(URL_api, new JSONObject(), future, future);
        mRequestQueue.add(request);

        try {
            JSONObject response = future.get(); // this will block

            // Loop through the array elements
            for(int i=0;i<response.length();i++){
                // Get current json object
                JSONObject user = response.getJSONObject(i);
                //lista donde sera guardada la info
                ArrayList<String> json_user = new ArrayList<String>();
                String id = user.getString("id");
                String name = user.getString("name");
                String last_name = user.getString("last_name");
                String email = user.getString("email");
                String password = user.getString("password");
                String profile_picture = user.getString("profile_picture");
                String id_flights = user.getString("id_flights");
                String record_kilometers = user.getString("record_kilometers");


                //Log.d("JSON_VAR:",profile_picture+"pruebaetc");

                json_user.add(id);
                json_user.add(name);
                json_user.add(last_name);
                json_user.add(email);
                String tempPassword=reverse(password);
                json_user.add(tempPassword);
                json_user.add(profile_picture);
                json_user.add(id_flights);
                json_user.add(record_kilometers);

        } catch (InterruptedException e) {
            // exception handling
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


//-------------------- FIN Bloque para bajar users de API
        //return true;
    }*/
    public void putUserIdFlight(Context contexto, final String pIdCFlight)
    {



        //---------Hacer PUT al API
        RequestQueue MyRequestQueue = Volley.newRequestQueue(contexto);


        String url = URL_api+"/"+idSession;
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
                if (!id_flightsSession.equals("null")){
                id_flightsSession=id_flightsSession+":"+pIdCFlight;
                MyData.put("id_flights",id_flightsSession);}
                else
                    {
                        id_flightsSession=pIdCFlight;
                        MyData.put("id_flights",id_flightsSession);
                    }

                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);




        // //---------FIN PUT al API

    }

    public void putUser(Context contexto, String idSelected, final String ptextName, final String ptextLastName, final String ptextViewEmail, final String ppassword)
    {



        //---------Hacer PUT al API
        RequestQueue MyRequestQueue = Volley.newRequestQueue(contexto);


        String url = URL_api+"/"+idSelected;
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
                MyData.put("name", ptextName); //Add the data you'd like to send to the server.
                MyData.put("last_name", ptextLastName);
                MyData.put("email", ptextViewEmail);

                Log.d("mouse",ppassword);
                MyData.put("password",ppassword);

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


    public String getIdSession() {
        return idSession;
    }

    public String getName() {
        return nameSession;
    }

    public String getLast_name() {
        return last_nameSession;
    }

    public String getEmail() {
        return emailSession;
    }

    public String getPassword() {

        return passwordSession;
    }

    public String getProfile_picture() {
        return profile_pictureSession;
    }

    public String getId_flights() {
        return id_flightsSession;

    }
    public Boolean getUserSessionState() {
        return userSessionState;
    }
    public void setUserSessionState(Boolean pUserSessionState) {
        userSessionState=pUserSessionState;
    }

}
