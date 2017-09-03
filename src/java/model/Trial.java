package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Sebastian Kikas
 */
public class Trial {

    private List<Individual> bestIndividualsList;
    private List<Individual> currentIndividualsList;
    private List<Individual> usedIndividuals;
    private int[][] data;

    public Trial() {
        this.bestIndividualsList = new ArrayList();
        this.currentIndividualsList = new ArrayList();
        this.usedIndividuals = new ArrayList();
    }

    public Trial(List<Individual> bestIndividualsList, List<Individual> currentIndividualsList, List<Individual> usedIndividuals) {
        this.bestIndividualsList = bestIndividualsList;
        this.currentIndividualsList = currentIndividualsList;
        this.usedIndividuals = usedIndividuals;
    }

    public List<Individual> getBestIndividualsList() {
        return bestIndividualsList;
    }

    public void setBestIndividualsList(List<Individual> bestIndividualsList) {
        this.bestIndividualsList = bestIndividualsList;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public List<Individual> getCurrentIndividualsList() {
        return currentIndividualsList;
    }

    public void setCurrentIndividualsList(List<Individual> currentIndividualsList) {
        this.currentIndividualsList = currentIndividualsList;
    }

    public void makeFirstList() {
        Individual indiv = new Individual();
        indiv.creatFirstChromosom(data);
        usedIndividuals.add(indiv);
        currentIndividualsList.add(indiv);
        for (int i = 0; i < 24; i++) {
            indiv = new Individual();
            boolean is;
            do {
                is = false;
                indiv.getChromosome().getListPlaces().clear();
                indiv.setDistanceAll(0);
                indiv.creatNextChromosom(data);
                for (int j = 0; j < usedIndividuals.size(); j++) {
                    if (indiv.getChromosome().compareTo(usedIndividuals.get(j).getChromosome())) {
                        is = true;
                    }
                }
            } while (is);

            usedIndividuals.add(indiv);
            currentIndividualsList.add(indiv);
        }
        Collections.sort(currentIndividualsList);
        bestIndividualsList.addAll(currentIndividualsList);
    }

    private int checkIfWas(List<Integer> tempTable, int tested) {

        if (!tempTable.isEmpty()) {
            while (tempTable.contains(tested)) {
                if (tested != 10) {
                    tested++;
                } else {
                    tested = 2;
                }
            }
        }
        return tested;
    }

    public void makeCross(Individual newIndividual, int secondPath, int howManyPlaces) {

        Random generator = new Random();
        int beginCross = generator.nextInt(howManyPlaces);
        int howLongCross;
        if (generator.nextBoolean() || beginCross == (howManyPlaces - 1)) {
            howLongCross = 1;
        } else if (generator.nextBoolean() || beginCross == (howManyPlaces - 2)) {
            howLongCross = 2;
        } else if (generator.nextBoolean() || beginCross == (howManyPlaces - 3)) {
            howLongCross = 3;
        } else {
            howLongCross = 4;
        }
        List<Integer> tempListGenes = new ArrayList();
        tempListGenes.addAll(bestIndividualsList.get(secondPath)
                .getChromosome().getListPlaces());

        List<Integer> tempListGenesChange = new ArrayList();
        for (int j = beginCross; j < (beginCross + howLongCross); j++) {
            tempListGenesChange.add(tempListGenes.get(j));
        }
        newIndividual.cross(beginCross, (beginCross + (howLongCross - 1)), tempListGenesChange);
        while (currentIndividualsList.contains(newIndividual)) {
            makeCross(newIndividual, secondPath, howManyPlaces);
        }

    }

    public void makeMutation(Individual newIndividual, int howManyPlaces) throws ParametersInvalidOrTooLittleException {
        Random generator = new Random();
        int howManyMutation;
        if (generator.nextBoolean()) {
            howManyMutation = (generator.nextInt(howManyPlaces)+2) / 2;
        } else {
            howManyMutation = 1;
        }
        //ile mutacji w pojedyńczym osobniku, 
        for (int j = 0; j < howManyMutation; j++) {
            int k=0;
            do {
                newIndividual.mutation(generator.nextInt(howManyPlaces), generator.nextInt(howManyPlaces));
                k++;
                if (k>10){
                    newIndividual.getChromosome().getListPlaces().clear();
                    newIndividual.setDistanceAll(0);
                    newIndividual.creatNextChromosom(data);
                    k=0;
                }
            } while (usedIndividuals.contains(newIndividual));
        }
    }

    public void findBeterList(int[][] data) throws ParametersInvalidOrTooLittleException {

        this.data=data;
        if (bestIndividualsList.isEmpty()) {
            makeFirstList();
        } else {
            /*
            Lista najlepszych z 20 elementów, 2 najlepszych zostaje, 10 najgorszych odpada
            3-10 mutacje i krzyrzówki, dodajemy 5 całkiem nowych, 4 krzyżówki i mutacje najlepszych 5,
            4 mutacje i krzyrzówki najlepszej 5 z losowymi
             */
            currentIndividualsList = new ArrayList();
            currentIndividualsList.add(bestIndividualsList.get(0));
            currentIndividualsList.add(bestIndividualsList.get(1));
            Random generator = new Random();
            List<Integer> tempTable = new ArrayList();                 //które już wykorzystane
            Individual newIndividual;
            int howManyPlaces = data.length;                        // Ile miejsc sprawdzamy
            for (int i = 0; i < 8; i++) {
                int firstPath = generator.nextInt(8) + 2;      //losowa pozycja z listy z przedziału 3-10
                firstPath = checkIfWas(tempTable, firstPath);
                tempTable.add(firstPath);
                int secondPath = generator.nextInt(8) + 2;
//                secondPath = checkIfWas(tempTable, secondPath);
//                tempTable.add(secondPath);
                newIndividual = new Individual(bestIndividualsList.get(firstPath).getChromosome(), bestIndividualsList.get(firstPath).getDistanceAll());
                makeCross(newIndividual, secondPath, howManyPlaces);    //krzyżówka
                if (generator.nextBoolean()) {  //mutacja
                    int temp = generator.nextInt(8) + 2;      //losowa pozycja z listy z przedziału 3-10
//                    temp = checkIfWas(tempTable, temp);        //sprawdzamy czy już była lokalizacja wykorzystywana i zwracamy taką która jeszcze nie była
                    makeMutation(newIndividual, howManyPlaces);
                }
                usedIndividuals.add(newIndividual);
                currentIndividualsList.add(newIndividual);
            }
            for (int i = 0; i < 4; i++) {        //mutacje i krzyrzówki najlepszych 2

                if (generator.nextBoolean()) {
                    newIndividual = new Individual(bestIndividualsList.get(0).getChromosome(), bestIndividualsList.get(0).getDistanceAll());
                    makeCross(newIndividual, 1, howManyPlaces);
                } else {

                    newIndividual = new Individual(bestIndividualsList.get(1).getChromosome(), bestIndividualsList.get(1).getDistanceAll());
                    makeCross(newIndividual, 0, howManyPlaces);
                }
                if (generator.nextBoolean()) {      //mutacja
                    if (generator.nextBoolean()) {
                        makeMutation(newIndividual, howManyPlaces);
                    } else {
                        makeMutation(newIndividual,howManyPlaces);
                    }
                }
                usedIndividuals.add(newIndividual);
                currentIndividualsList.add(newIndividual);
            }
            for (int i = 0; i < 6; i++) {
                int temp = generator.nextInt(23) + 2;
                if (generator.nextBoolean()) {
                    newIndividual = new Individual(bestIndividualsList.get(0).getChromosome(), bestIndividualsList.get(0).getDistanceAll());
                    makeCross(newIndividual, temp, howManyPlaces);
                    if (generator.nextBoolean()) {
                        makeMutation(newIndividual, howManyPlaces);
                    }
                } else {

                    newIndividual = new Individual(bestIndividualsList.get(1).getChromosome(), bestIndividualsList.get(1).getDistanceAll());
                    makeCross(newIndividual, temp, howManyPlaces);
                    if (generator.nextBoolean()) {
                        makeMutation(newIndividual, howManyPlaces);
                    }
                }
                usedIndividuals.add(newIndividual);
                currentIndividualsList.add(newIndividual);
            }
            for (int i = 0; i < 5; i++) {
                newIndividual = new Individual();
                do {
                    newIndividual.creatNextChromosom(data);
                } while (usedIndividuals.contains(newIndividual));
                usedIndividuals.add(newIndividual);
                currentIndividualsList.add(newIndividual);
            }

        }
        Collections.sort(currentIndividualsList);
        Individual indivCur, indivBest;
        for (int i = 0; i < currentIndividualsList.size(); i++) {
            indivCur = currentIndividualsList.get(i);
            for (int j = 0; j < bestIndividualsList.size(); j++) {
                indivBest = bestIndividualsList.get(j);
                if (indivCur.getDistanceAll() == indivBest.getDistanceAll()) {
                    break;
                } else if (indivCur.getDistanceAll() < indivBest.getDistanceAll()) {
                    bestIndividualsList.set(j, indivCur);
                    break;
                }
            }
        }
        Collections.sort(bestIndividualsList);
    }
}
