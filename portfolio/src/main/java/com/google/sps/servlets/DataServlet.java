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
import java.util.*;
import java.io.IOException;
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

    String json = convertToJsonUsingGson(ar);

    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

    private String convertToJsonUsingGson(ArrayList<String> ar) {
    Gson gson = new Gson();
    String json = gson.toJson(ar);
    return json;
  }

    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "text-input", "");
    ar.add(text);

    // Respond with the result.
    response.setContentType("text/html;");
    response.getWriter().println(text);

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
