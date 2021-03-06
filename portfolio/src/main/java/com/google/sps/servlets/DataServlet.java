// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.Task;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    private ArrayList<String> ar=new ArrayList<String>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    //String msg="Hello Aastha!";
    
    /*ar.add("Nice work");
    ar.add("Welldone");
    ar.add("Bravo young lady");*/


    Query query = new Query("Task").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Task> tasks = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      long id = entity.getKey().getId();
      String title = (String) entity.getProperty("title");
      long timestamp = (long) entity.getProperty("timestamp");
      String email = (String) entity.getProperty("email");
    
      Task task = new Task(id, title, timestamp,email);
      tasks.add(task);
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(tasks));




    
    /*String json = convertToJsonUsingGson(ar);

    response.setContentType("application/json;");

    response.getWriter().println(json);*/

    
  }

    private String convertToJsonUsingGson(ArrayList<String> ar) {
    Gson gson = new Gson();
    String json = gson.toJson(ar);
    return json;
  }

    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    response.setContentType("text/html;");
    PrintWriter out = response.getWriter();
    UserService userService = UserServiceFactory.getUserService();
    String email = "";        

    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/");
      return;
    }
    if (userService.isUserLoggedIn()){
            email = userService.getCurrentUser().getEmail();
    }
      
        
    // Get the input from the form.
    String title = getParameter(request,"new-comment","");
    //ar.add(text);

    // Respond with the result.
   // response.setContentType("text/html;");
   // response.getWriter().println(text);

    long timestamp = System.currentTimeMillis();

    Entity taskEntity = new Entity("Task");
    taskEntity.setProperty("title", title);
    taskEntity.setProperty("timestamp", timestamp);
    taskEntity.setProperty("email",email);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }
  
  /**
   * @return the request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String comment, String defaultValue) {
    String value = request.getParameter(comment);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

}
