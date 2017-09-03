package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Sebastian Kikas
 */
public class Individual implements Comparable<Individual> {

    private Chromosome chromosome;
    private int distanceAll;

    public Individual() {
        chromosome = new Chromosome();
    }

    public Individual(Chromosome chromosome, int distanceAll) {
        this.chromosome = new Chromosome(chromosome.getListPlaces());
        this.distanceAll = distanceAll;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public void setChromosome(Chromosome chromosome) {
        this.chromosome = chromosome;
    }

    public int getDistanceAll() {
        return distanceAll;
    }

    public void setDistanceAll(int distanceAll) {
        this.distanceAll = distanceAll;
    }

    public void mutation(int whichPlace, int newPlace) throws ParametersInvalidOrTooLittleException {
        if (whichPlace < 0 || newPlace < 0) {
            throw new ParametersInvalidOrTooLittleException("A number less than to 0");
        } else {
            int temp = chromosome.getListPlaces().get(whichPlace);
            chromosome.getListPlaces().set(whichPlace, newPlace);
            for (int i = 0; i < chromosome.getListPlaces().size(); i++) {
                if ((chromosome.getListPlaces().get(i) == newPlace) && (i != whichPlace)) {
                    chromosome.getListPlaces().set(i, temp);
                    break;
                }
            }

        }
    }

    public void cross(int beginChange, int endChange, List<Integer> newPlaces) {
        int j = 0;
        for (int i = beginChange; i <= endChange; i++) {
            chromosome.getListPlaces().set(i, newPlaces.get(j));
            j++;
        }
        List<Integer> tempPlaces = new ArrayList();
        for (int i = 0; i < chromosome.getListPlaces().size(); i++) {
            if (!chromosome.getListPlaces().contains(i)) {
                tempPlaces.add(i);
            }
        }

        for (int i = 0; i < chromosome.getListPlaces().size(); i++) {
            if (i < beginChange || i > endChange) {
                for (int k = 0; k < newPlaces.size(); k++) {
                    if ((int) chromosome.getListPlaces().get(i) == (int) newPlaces.get(k)) {
                        chromosome.getListPlaces().set(i, tempPlaces.remove(0));
                    }
                }
            }
        }
    }

    public void creatFirstChromosom(int[][] distances) {

        for (int i = 0; i < distances.length; i++) {
            chromosome.setNewPlace(i);
            if ((i + 1) != distances.length) {
                distanceAll += distances[i][i];
            } else {
                distanceAll += distances[i][0];
            }
        }
    }

    public void creatNextChromosom(int[][] distances) {

        Random generator = new Random();
        List<Integer> checkList = new ArrayList();
        int temp = 0;
        int beforeTemp = 0;
        checkList.add(temp);
        chromosome.setNewPlace(temp);
        for (int i = 1; i < distances.length; i++) {
            beforeTemp = temp;
            do {
                if (checkList.size() <= (distances.length / 2)) {
                    temp = generator.nextInt(distances.length);
                } else {
                    if ((temp + 1) < distances.length) {
                        temp++;
                    } else {
                        temp = 0;
                    }
                }

            } while (checkList.contains(temp));
            checkList.add(temp);

            chromosome.setNewPlace(temp);
            if (temp > beforeTemp) {

                distanceAll += distances[beforeTemp][temp - 1];
            } else {
                distanceAll += distances[beforeTemp][temp];
            }

        }
        distanceAll += distances[temp][0];

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.chromosome);
        hash = 43 * hash + this.distanceAll;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Individual other = (Individual) obj;
        if (this.distanceAll != other.distanceAll) {
            return false;
        }

        for (int i = 0; i < this.getChromosome().getListPlaces().size(); i++) {

            if (this.chromosome.getPlace(i) != other.getChromosome().getPlace(i)) {
                return false;
            } else if ((i + 1) == this.getChromosome().getListPlaces().size()) {
                if (this.distanceAll == other.distanceAll) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int compareTo(Individual o) {
        //return this.distanceAll.compareTo(o.distanceAll);
        return this.distanceAll > o.distanceAll ? 1 : -1;
    }
}
