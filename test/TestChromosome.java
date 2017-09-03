
import model.Chromosome;
import java.util.*;
import org.junit.*;
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
public class TestChromosome {
    
    
    
    @Test
    public void testIsThisValue(){
        List<Integer> testList = new ArrayList();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);
        testList.add(5);
        testList.add(6);
        testList.add(7);
        testList.add(8);
        testList.add(9);
        testList.add(10);
        Chromosome testChromosome=new Chromosome(testList);
        int testInteger =5;
        assertTrue("To nie to samo",testChromosome.isThisValue(testInteger));
        
    }
    
    @Test
    public void testChangeOldPlace(){
        List<Integer> testList = new ArrayList();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        testList.add(4);
        testList.add(5);
        testList.add(6);
        testList.add(7);
        testList.add(8);
        testList.add(9);
        testList.add(10);
        Chromosome testChromosome=new Chromosome(testList);
        testChromosome.changeOldPlace(7, 3);
        assertEquals("Po zmianie są różne wartości",testChromosome.getPlace(3), 7);
        assertEquals("Nie poprawnie się zmieniło",4, testChromosome.getPlace(6));
    }
}
