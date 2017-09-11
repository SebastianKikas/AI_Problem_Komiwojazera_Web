package model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian Kikas
 */
public class Chromosome {

    private List<Integer> places;

    public Chromosome() {
        places = new ArrayList();
    }

    public Chromosome(int firstPlace) {
        places = new ArrayList();
        places.add(firstPlace);
    }

    public Chromosome(List<Integer> newPlaces) {
        places = newPlaces;
    }

    public List<Integer> getListPlaces() {
        return places;
    }

    public int getPlace(int i) {
        return places.get(i);
    }

    public void setListPlaces(List<Integer> newPlaces) {
        places = newPlaces;
    }

    public void setNewPlace(int newPlace) {
        places.add(newPlace);
    }

    public void changeOldPlace(int newValue, int whichPlace) {
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i) == newValue) {
                places.set(i, places.get(whichPlace));
                places.set(whichPlace, newValue);
            }
        }
    }

    public Boolean isThisValue(int checkValue) {
        for (int place : places) {
            if (place == checkValue) {
                return true;
            }
        }
        return false;
    }

    public boolean compareTo(Chromosome chromosome) {
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i) != chromosome.getPlace(i)) {
                return false;
            }
        }
        return true;
    }
}
