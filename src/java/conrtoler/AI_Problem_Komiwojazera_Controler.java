package conrtoler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ParametersInvalidOrTooLittleException;
import org.joda.time.DateTime;

/**
 *
 * @author Sebastian Kikas
 */
public class AI_Problem_Komiwojazera_Controler {

    private static final String API_KEY = "AIzaSyDsXsPmvSYL7fHwYXqROXm1YfTS61Z0V30";
//    private static final GeoApiContext context = new GeoApiContext();
    private DistanceMatrix matrix;

    public DistanceMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix(DistanceMatrix matrix) {
        this.matrix = matrix;
    }

    public int[][] getNewData(LatLng[] origins, LatLng[] destinations) {
        // TODO code application logic here

//            int[][] data = new int[10][10];
//            for (int i = 0; i < 10; i++) {
//                data[0][i] = 10 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[1][i] = 9 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[2][i] = 8 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[3][i] = 7 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[4][i] = 6 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[5][i] = 5 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[6][i] = 4 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[7][i] = 3 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[8][i] = 2 * i;
//            }
//            for (int i = 0; i < 10; i++) {
//                data[9][i] = i;
//            }
//            Trial trial = new Trial();
//            List<Integer> bestResults = new ArrayList();
//            List<Double> everageListBest = new ArrayList();
//            for (int i = 0; i < 1000; i++) {
//                trial.findBeterList(data);
//                bestResults.add(trial.getBestIndividualsList().get(0).getDistanceAll());
//                double temp = 0;
//                for (int j = 0; j < trial.getBestIndividualsList().size(); j++) {
//                    temp += trial.getBestIndividualsList().get(j).getDistanceAll();
//                }
//                temp = temp / trial.getBestIndividualsList().size();
//                everageListBest.add(temp);
//            }
//            System.out.println("Najlepsze wyniki   Średnia z listy");
//            for (int i = 0; i < 1000; i++) {
//                System.out.print("   " + bestResults.get(i) + "            ");
//                if (i > 0 && bestResults.get(i) > bestResults.get(i - 1)) {
//                    System.out.println("UWAGA!!!");
//                }
//                System.out.println(everageListBest.get(i));
//                if (i > 0 && everageListBest.get(i) > everageListBest.get(i - 1)) {
//                    System.out.println("UWAGA!!!");
//                }
//
//            }
//        String[] originss = new String[]{"Katowice, Polska", "Tychy, Polska", "Gliwice, Polska", "Częstochowa, Polska", "Wyry, Polska", "Mikołów, Polska", "Sosnowiec, Polska", "Kraków, Polska", "Opole, Polska", "Bielsko-Biała, Polska"};
//        String[] destinationss = new String[]{"Katowice, Polska", "Tychy, Polska", "Gliwice, Polska", "Częstochowa, Polska", "Wyry, Polska", "Mikołów, Polska", "Sosnowiec, Polska", "Kraków, Polska", "Opole, Polska", "Bielsko-Biała, Polska"};
        int[][] data = new int[origins.length][destinations.length];

        
        try {
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(API_KEY)
                    .build();
            if (origins.length > 10) {
                LatLng[] tempOrigins = new LatLng[10];
                int j = 0, n = 0;
                for (int i = 0; i < 10; i++) {
                    tempOrigins[j] = origins[i];
                    j++;
                    if (j == 10) {
                        matrix = DistanceMatrixApi.newRequest(context).origins(tempOrigins).destinations(tempOrigins)
                                .mode(TravelMode.DRIVING).avoid(RouteRestriction.TOLLS)
                                .units(Unit.METRIC).departureTime(new DateTime().plusMinutes(2)).await();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        gson.toJson(matrix);

                        for (int k = 0; k < matrix.originAddresses.length; k++) {
                            for (int m = 0; m < matrix.destinationAddresses.length; m++) {
                                if (k < m) {
                                    data[k][m] = (int) matrix.rows[k].elements[m].distance.inMeters;
                                } else if ((m + 1) < matrix.destinationAddresses.length) {
                                    data[k][m] = (int) matrix.rows[k].elements[m + 1].distance.inMeters;
                                }
                            }
                        }
                    }
                }
            } else {
                matrix = DistanceMatrixApi.newRequest(context).origins(origins).destinations(destinations)
                        .mode(TravelMode.DRIVING).avoid(RouteRestriction.TOLLS)
                        .units(Unit.METRIC).departureTime(new DateTime().plusMinutes(2)).await();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(matrix);

                for (int i = 0; i < matrix.originAddresses.length; i++) {
                    for (int j = 0; j < matrix.destinationAddresses.length; j++) {
                        if (i < j) {
                            data[i][j] = (int) matrix.rows[i].elements[j].distance.inMeters;
                        } else if ((j + 1) < matrix.destinationAddresses.length) {
                            data[i][j] = (int) matrix.rows[i].elements[j + 1].distance.inMeters;
                        }
                    }
                }
            }
            return data;
        } catch (ApiException | InterruptedException | IOException ex) {
            Logger.getLogger(AI_Problem_Komiwojazera_Controler.class.getName()).log(Level.SEVERE, null, ex);

        }
//            System.out.println("Najlepsze wyniki   Średnia z listy");
//            for (int i = 0; i < 100; i++) {
//                System.out.print("   " + (bestResults.get(i)/1000) + " km            ");
//                if (i > 0 && bestResults.get(i) > bestResults.get(i - 1)) {
//                    System.out.println("UWAGA!!!");
//                }
//                System.out.println((everageListBest.get(i)/1000)+" km");
//                if (i > 0 && everageListBest.get(i) > everageListBest.get(i - 1)) {
//                    System.out.println("UWAGA!!!");
//                }
//
//            }
//            for (int i = 0; i < matrix.rows.length; i++) {
//                for (int j = 0; j < matrix.rows[i].elements.length; j++) {
//                    System.out.println(matrix.originAddresses[i] + " do " + matrix.destinationAddresses[j]
//                            + " odległość " + matrix.rows[i].elements[j].distance);
//                }
//            }

//            System.out.println("Najlepsze wyniki   Średnia z listy");
//            for (int i = 0; i < 100; i++) {
//                System.out.print("   " + (bestResults.get(i)/1000) + " km            ");
//                if (i > 0 && bestResults.get(i) > bestResults.get(i - 1)) {
//                    System.out.println("UWAGA!!!");
//                }
//                System.out.println((everageListBest.get(i)/1000)+" km");
//                if (i > 0 && everageListBest.get(i) > everageListBest.get(i - 1)) {
//                    System.out.println("UWAGA!!!");
//                }
//
//            }
//            for (int i = 0; i < matrix.rows.length; i++) {
//                for (int j = 0; j < matrix.rows[i].elements.length; j++) {
//                    System.out.println(matrix.originAddresses[i] + " do " + matrix.destinationAddresses[j]
//                            + " odległość " + matrix.rows[i].elements[j].distance);
//                }
//            }
//            System.out.println("Najlepsze wyniki   Średnia z listy");
//            for (int i = 0; i < 100; i++) {
//                System.out.print("   " + (bestResults.get(i)/1000) + " km            ");
//                if (i > 0 && bestResults.get(i) > bestResults.get(i - 1)) {
//                    System.out.println("UWAGA!!!");
//                }
//                System.out.println((everageListBest.get(i)/1000)+" km");
//                if (i > 0 && everageListBest.get(i) > everageListBest.get(i - 1)) {
//                    System.out.println("UWAGA!!!");
//                }
//
//            }
//            for (int i = 0; i < matrix.rows.length; i++) {
//                for (int j = 0; j < matrix.rows[i].elements.length; j++) {
//                    System.out.println(matrix.originAddresses[i] + " do " + matrix.destinationAddresses[j]
//                            + " odległość " + matrix.rows[i].elements[j].distance);
//                }
//            }
        return data;
    }

}
