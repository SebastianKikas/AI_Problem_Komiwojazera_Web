/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.maps.model.LatLng;
import conrtoler.AI_Problem_Komiwojazera_Controler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ParametersInvalidOrTooLittleException;
import model.Trial;

/**
 * Servlet used to calculate the LCM and GCD
 *
 * @author Sebastian Kikas
 * @version 5.0
 *
 */
@WebServlet(urlPatterns = {"/ServletAI"})
public class ServletAI extends HttpServlet {

    /**
     * conter is used to calculate the amount of calculations made
     */
    int conter = 0;

    private AI_Problem_Komiwojazera_Controler controler;
    private List<Double> bestResults;
    private List<Double> everageListBest;
    private Trial trial;
    private String[] places;

    public ServletAI() {
        controler = new AI_Problem_Komiwojazera_Controler();
        bestResults = new ArrayList();
        everageListBest = new ArrayList();
        trial = new Trial();
         
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<style>table, th, td {");
//            out.println("border: 1px solid black;");
//            out.println("border-collapse: collapse;");
//            out.println("}");
//            out.println("</style>");
//            out.println("<title>Servlet ServletCalc</title>");
//            out.println("</head>");
//            out.println("<body>");
            int j=0;
            out.println("<p>Punkty Wybrane: </p>");
            for (String place: places){
            out.println("<p>Punkt docelowy nr "+(j++)+": "+place+"</p>");
            }
            out.println("<p>Trasa najlepsza</p>");
            for (int i = 0; i < trial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size(); i++) {
                out.print("<p>Miejsce nr " + (i + 1) + " -> <b>" + places[trial.getBestIndividualsList().get(0).getChromosome().getPlace(i)] + "</b></p>");
            }
            out.println("<div></div>");
            out.println("<table style='width:70%'>");
            out.println("<tr>");
            out.println("<th>Lp.</th>");
            out.println("<th>Najlepsze wyniki</th>");
            out.println("<th>Srednia z listy osobnikow</th>");
            out.println("</tr>");
            for (int i = 0; i < bestResults.size(); i++) {
                out.println("<tr><td>" + (i + 1) + ".</td><td> " + (bestResults.get(i) / 1000) + " km</td><td> " + (everageListBest.get(i) / 1000) + " km</td></tr>");
            }

            out.println("</table>");
//            out.println("</body>");
//            out.println("</html>");
        }
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        try {

            if (request.getParameter("markers") == null) {
                throw new ParametersInvalidOrTooLittleException("Nie podałeś miejsc przez które ma prowadzić droga");
            } else {

                String paramS = request.getParameter("markers");

                if (!request.getParameter("markers").isEmpty()) {
                    String[] paramA = paramS.split("\\)");

                    LatLng[] tabLatLng = new LatLng[paramA.length];
                    double lat, lng;
                    int k = 0;
                    for (String paramA1 : paramA) {

                        String[] temp = paramA1.split("\\(");
                        String[] numbersString = temp[1].split(", ");
                        lat = Double.parseDouble(numbersString[0]);
                        lng = Double.parseDouble(numbersString[1]);
                        tabLatLng[k] = new LatLng(lat, lng);
                        k++;
                    }

                    int[][] data = controler.getNewData(tabLatLng, tabLatLng);
                    places=controler.getMatrix().originAddresses;
                    for (int i = 0; i < 100; i++) {
                        trial.findBeterList(data);

                        bestResults.add((double) trial.getBestIndividualsList().get(0).getDistanceAll());
                        double temp = 0;
                        for (int j = 0; j < trial.getBestIndividualsList().size(); j++) {
                            temp += trial.getBestIndividualsList().get(j).getDistanceAll();
                        }
                        temp = temp / trial.getBestIndividualsList().size();
                        everageListBest.add(temp);

                    }
                }

//                out.write("Najlepsze wyniki   Srednia z listy");
//                for (int i = 0; i < 100; i++) {
//                    out.print("   " +(bestResults.get(i) / 1000) + " km            ");
//                    if (i > 0 && bestResults.get(i) > bestResults.get(i - 1)) {
//                        out.println("UWAGA!!!");
//                    }
//                    out.println((everageListBest.get(i) / 1000) + " km");
//                    if (i > 0 && everageListBest.get(i) > everageListBest.get(i - 1)) {
//                        out.println("UWAGA!!!");
//                    }
//
//                }
                processRequest(request, response);
            }
        } catch (NumberFormatException e) {
            out.println("It isn't a number");
        } catch (ParametersInvalidOrTooLittleException ex) {
            Logger.getLogger(ServletAI.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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
