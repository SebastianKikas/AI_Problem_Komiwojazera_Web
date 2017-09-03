
import java.util.ArrayList;
import java.util.List;
import model.Chromosome;
import model.Individual;
import model.ParametersInvalidOrTooLittleException;
import model.Trial;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sebastian Kikas
 */
public class TestTrial {

    @Test
    public void testFindBeterList() throws ParametersInvalidOrTooLittleException {
        int[][] testData
                = {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}};
        Trial testTrial = new Trial();
        for (int i = 0; i < 100; i++) {
            testTrial.findBeterList(testData);
        }
        int sizeList = testTrial.getBestIndividualsList().size();
        int sizeChromosome = testTrial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size();
        Chromosome testChromosomeOne, testChromosomeTwo;
        Individual testIndividual;
        Individual testBestIndividual = testTrial.getBestIndividualsList().get(0);
        for (int i = 0; i < sizeList; i++) {
            testChromosomeOne = testTrial.getBestIndividualsList().get(i).getChromosome();
            for (int j = 0; j < sizeList; j++) {
                if (i != j) {
                    if (testTrial.getBestIndividualsList().get(i).equals(testTrial.getBestIndividualsList().get(j))) {
                        fail("Te chrommosomy są takie same -1 " + i + " " + testChromosomeOne.getListPlaces().toString());

                    }
//                    testChromosomeTwo = testTrial.getBestIndividualsList().get(j).getChromosome();
//                    int k = 0;
//                    while (testChromosomeOne.getPlace(k) == testChromosomeTwo.getPlace(k)) {
//                        k++;
//                        if (k == sizeChromosome) {
//                            fail("Te chrommosomy są takie same-2 " + i + " " + testChromosomeOne.getListPlaces().toString()
//                                    + " " + j + " " + testChromosomeTwo.getListPlaces().toString());
//
//                        }
//                    }
                }

            }

            testIndividual = testTrial.getBestIndividualsList().get(i);
            if (testIndividual.getDistanceAll() < testBestIndividual.getDistanceAll()) {
                fail("Piersza na liście nie ma najniższej wartości");
            }
            if ((i + 1) < sizeList) {
                if (testIndividual.getDistanceAll() > testTrial.getBestIndividualsList().get(i + 1).getDistanceAll()) {
                    fail("Kolejne wartości nie są coraz wyższe " + testIndividual.getDistanceAll() + " "
                            + testTrial.getBestIndividualsList().get(i + 1).getDistanceAll());
                }

            }
        }
    }

    @Test
    public void testMakeMutation() throws ParametersInvalidOrTooLittleException {
        int[][] testData = {{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}};
        Trial testTrial = new Trial();
        testTrial.setData(testData);
        testTrial.makeFirstList();
        Individual testIndividual = testTrial.getBestIndividualsList().get(0);
        Chromosome testChromosome = new Chromosome();
        for (int i = 0; i < testTrial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size(); i++) {
            testChromosome.setNewPlace(testIndividual.getChromosome().getPlace(i));
        }

        int howManyPlaces = 6;
        testTrial.makeMutation(testIndividual, howManyPlaces);

        for (int i = 0; i < testTrial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size(); i++) {
            for (int j = 0; j < testTrial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size(); j++) {
                if (i != j) {
                    if (testTrial.getBestIndividualsList().get(0).getChromosome().getPlace(i)
                            == testTrial.getBestIndividualsList().get(0).getChromosome().getPlace(j)) {
                        fail("Te miejsca w chromosomie są takie same - makeMutation- " + j + " " + i);

                    }
                }
            }
        }
        for (int i = 0; i < testTrial.getBestIndividualsList().size(); i++) {
            for (int j = 0; j < testTrial.getBestIndividualsList().size(); j++) {
                if (testTrial.getBestIndividualsList().get(i).equals(testTrial.getBestIndividualsList().get(j))) {
                    fail("Te chrommosomy są takie same - makeMutation " + testChromosome.getListPlaces().toString() + " " + testIndividual.getChromosome().getListPlaces().toString());
                }
            }
        }
    }

    @Test
    public void testMakeCross() {
        int[][] testData = {{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}};
        Trial testTrial = new Trial();
        testTrial.setData(testData);
        testTrial.makeFirstList();
        Individual testIndividual = testTrial.getBestIndividualsList().get(0);
        Chromosome testChromosome = new Chromosome();
        for (int i = 0; i < testTrial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size(); i++) {
            testChromosome.setNewPlace(testIndividual.getChromosome().getPlace(i));
        }
        int howManyPlaces = 6;
        int whichPath = 5;
        testTrial.makeCross(testIndividual, whichPath, howManyPlaces);

        for (int i = 0; i < testTrial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size(); i++) {
            if (testTrial.getBestIndividualsList().get(i).equals(testTrial.getBestIndividualsList().get(0)) && i != 0) {
                fail("Te chrommosomy są takie same - makeCross -3 " + i);
            }
            for (int j = 0; j < testTrial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size(); j++) {
                if (i != j) {
                    if (testTrial.getBestIndividualsList().get(0).getChromosome().getPlace(i)
                            == testTrial.getBestIndividualsList().get(0).getChromosome().getPlace(j)) {
                        fail("Te miejsca w chromosomie są takie same - makeCross-1 " + j + " " + i);

                    }
                    if (testTrial.getBestIndividualsList().get(5).getChromosome().getPlace(i)
                            == testTrial.getBestIndividualsList().get(5).getChromosome().getPlace(j)) {
                        fail("Te miejsca w chromosomie są takie same - makeCross-2 " + j + " " + i);
                    }
                }
            }
        }
    }

    @Test
    public void testMakeFirstList() {
        int[][] testData = {{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}};
        Trial testTrial = new Trial();
        testTrial.setData(testData);
        testTrial.makeFirstList();
        int sizeList = testTrial.getBestIndividualsList().size();
        int sizeChromosome = testTrial.getBestIndividualsList().get(0).getChromosome().getListPlaces().size();
        Chromosome testChromosomeOne, testChromosomeTwo;
        Individual testIndividual;
        Individual testBestIndividual = testTrial.getBestIndividualsList().get(0);
        for (int i = 0; i < sizeList; i++) {
            testChromosomeOne = testTrial.getBestIndividualsList().get(i).getChromosome();
            for (int j = 0; j < sizeList; j++) {
                if (i != j) {
                    testChromosomeTwo = testTrial.getBestIndividualsList().get(j).getChromosome();
                    int k = 0;
                    while (testChromosomeOne.getPlace(k) == testChromosomeTwo.getPlace(k)) {
                        k++;
                        if (k == sizeChromosome) {
                            fail("Te chrommosomy są takie same " + i + " " + j);
                        }
                    }

                }
            }
            testIndividual = testTrial.getBestIndividualsList().get(i);
            if (testIndividual.getDistanceAll() < testBestIndividual.getDistanceAll()) {
                fail("Piersza na liście nie ma najniższej wartości");
            }
            if ((i + 1) < sizeList) {
                if (testIndividual.getDistanceAll() > testTrial.getBestIndividualsList().get(i + 1).getDistanceAll()) {
                    fail("Kolejne wartości nie są coraz wyższe " + i);
                }

            }
        }
    }
}
