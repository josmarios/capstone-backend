/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ic.lps.tutor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.json.JsonParserImpl;
import org.json.JSONObject;

/**
 *
 * @author josmario
 */
@WebServlet(name = "CapstoneServlet", urlPatterns = {"/CapstoneServlet"})
public class CapstoneServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {

            response.setContentType("text/html");
            response.setHeader("Cache-control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "-1");

            String clientOrigin = request.getHeader("origin");
            response.setHeader("Access-Control-Allow-Origin", clientOrigin);
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");

            JSONObject resp = new JSONObject();
            resp.append("success", true);
            out.println(resp.toString());
            
            out.close();
        }
        JSONObject json = new JSONObject(request.getReader().readLine());

        String csvHeader = "startTime, endTime, gender, age, testType, pretest, activity, posttest, pre, post\n";
        String csvLine = json.getLong("startTime") + "," + json.getLong("endTime") + "," + json.getString("gender") + "," + json.getString("age") + "," + json.getString("testType") + "," + json.getInt("pretestPoints") + "," + json.getInt("activityPoints") + "," + json.getInt("posttestPoints") + "," + json.getJSONArray("pre").toString() + "," + json.getJSONArray("post").toString() + "\n";

        File output = new File("responses.csv");

        System.out.println("---------> Path: " + output.getAbsolutePath());

        if (output.exists()) {
            FileWriter fw = new FileWriter(output, true);
            fw.append(csvLine);
            fw.close();
        } else {
            FileWriter fw = new FileWriter(output, true);
            fw.write(csvHeader + csvLine);
            fw.close();
        }

        System.out.println(csvHeader + csvLine);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
