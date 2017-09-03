
import model.Chromosome;
import model.Individual;
import model.ParametersInvalidOrTooLittleException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sebastian Kikas
 */
public class TestIndividual {

    @Test
    public void testCreatFirstChromosom() {
        int[][] testDistances = {{1, 2, 3, 4}, {2, 2, 2, 2}, {1, 1, 2, 2}, {2, 2, 1, 3}, {4, 2, 4, 2}};
        Individual testIndividual = new Individual();
        testIndividual.creatFirstChromosom(testDistances);
        int sumTest = testDistances[0][0] + testDistances[1][1] + testDistances[2][2]
                + testDistances[3][3] + testDistances[4][0];
        assertEquals("Dystanse są różne: ", testIndividual.getDistanceAll(), sumTest);

        Chromosome testChromosome = new Chromosome();
        List<Integer> testList = new ArrayList();
        for (int i = 0; i < testDistances.length; i++) {
            testList.add(i);
        }
        testChromosome.setListPlaces(testList);
        assertEquals("Te chromosomy się różnią ", testIndividual.getChromosome().getListPlaces(),
                testChromosome.getListPlaces());

    }

    @Test
    public void testCreatNextChromosom() {
        int[][] testDistances = {{1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}};
        int testSum = 0;
        Individual testIndividual = new Individual();
        testIndividual.creatNextChromosom(testDistances);
        for (int i = 0; i < testIndividual.getChromosome().getListPlaces().size(); i++) {

            if ((i + 1) < testIndividual.getChromosome().getListPlaces().size()) {
                if (testIndividual.getChromosome().getPlace(i + 1) > testIndividual.getChromosome().getPlace(i)) {
                    testSum += testDistances[testIndividual.getChromosome().getPlace(i)][testIndividual.getChromosome().getPlace(i + 1) - 1];
                } else {
                    testSum += testDistances[testIndividual.getChromosome().getPlace(i)][testIndividual.getChromosome().getPlace(i + 1)];
                }
            } else {
                testSum += testDistances[testIndividual.getChromosome().getPlace(i)][testIndividual.getChromosome().getPlace(0)];
            }
            int temp = testIndividual.getChromosome().getPlace(i);
            for (int j = 0; j < testIndividual.getChromosome().getListPlaces().size(); j++) {
                if (j != i) {
                    if (testIndividual.getChromosome().getPlace(j) == temp) {
                        fail("Objekt wystąpił więcej niż jeden raz w chromosomie: " + j);
                    }
                }
            }
        }

        assertEquals("Błędna długość trasy", testSum, testIndividual.getDistanceAll());
    }

    @Test
    public void testCross() {
        int testBegin = 3;
        int testEnd = 4;
        List<Integer> testList = new ArrayList();
        testList.add(1);
        testList.add(2);
        int[][] testDistances = {{1, 2, 3, 4}, {2, 2, 2, 2}, {1, 1, 2, 2}, {2, 2, 1, 3}, {4, 2, 4, 2}};
        Individual testIndividual = new Individual();
        testIndividual.creatFirstChromosom(testDistances);
        testIndividual.cross(testBegin, testEnd, testList);
        assertEquals("Krzyżówka jest inna niż testowa wartość End", testIndividual.getChromosome().getPlace(testEnd), 2);
        assertEquals("Krzyżówka jest inna niż testowa wartość Begin", testIndividual.getChromosome().getPlace(testBegin), 1);

        for (int i = 0; i < testIndividual.getChromosome().getListPlaces().size(); i++) {

            int temp = testIndividual.getChromosome().getPlace(i);
            for (int j = 0; j < testIndividual.getChromosome().getListPlaces().size(); j++) {
                if (j != i) {
                    if (testIndividual.getChromosome().getPlace(j) == temp) {
                        fail("Objekt, pokrzyżówce, wystąpił więcej niż jeden raz w chromosomie: " + j);
                    }
                }
            }
        }
    }

    @Test
    public void testMutation() throws ParametersInvalidOrTooLittleException {
        int[][] testDistances = {{1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}};
        Individual testIndividual = new Individual();
        testIndividual.creatNextChromosom(testDistances);

        int whichPlace = 0;
        int newPlace = 1;
        testIndividual.mutation(whichPlace, newPlace);
        assertEquals("Błąd w mutacji pierwszej wartości", newPlace, testIndividual.getChromosome().getPlace(whichPlace));

        whichPlace = 4;
        newPlace = 2;
        testIndividual.mutation(whichPlace, newPlace);
        assertEquals("Błąd w mutacji pierwszej wartości", newPlace, testIndividual.getChromosome().getPlace(whichPlace));

        for (int i = 0; i < testIndividual.getChromosome().getListPlaces().size(); i++) {

            int temp = testIndividual.getChromosome().getPlace(i);
            for (int j = 0; j < testIndividual.getChromosome().getListPlaces().size(); j++) {
                if (j != i) {
                    if (testIndividual.getChromosome().getPlace(j) == temp) {
                        fail("Objekt, po mutacji, wystąpił więcej niż jeden raz w chromosomie: " + j);
                    }
                }
            }
        }

    }

    @Test(expected = Exception.class)
    public void testMutationException1() throws ParametersInvalidOrTooLittleException {

        int[][] testDistances = {{1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}};
        Individual testIndividual = new Individual();
        testIndividual.creatNextChromosom(testDistances);

        int whichPlace = 5;
        int newPlace = 0;
        testIndividual.mutation(whichPlace, newPlace);
        fail("Should have thrown an exception");
    }

    @Test(expected = Exception.class)
    public void testMutationException2() throws ParametersInvalidOrTooLittleException {

        int[][] testDistances = {{1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}};
        Individual testIndividual = new Individual();
        testIndividual.creatNextChromosom(testDistances);
        int whichPlace = 2;
        int newPlace = -1;
        testIndividual.mutation(whichPlace, newPlace);
        fail("Should have thrown an exception");
    }
}
