/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namegenerator;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author test
 */
public class NameMash {

    private Comparator comp = new Comparator<Name>() {

        @Override
        public int compare(Name first, Name second) {
            return second.getScore() - first.getScore();
        }
    };
    private Scanner sc = new Scanner(System.in);

    public NameMash() {
        sc.useDelimiter("\n");
        ArrayList<Name> nameList = getList();
        ArrayList<Round> roundList = new ArrayList<>();
        Collections.shuffle(nameList);
        //ROUND 1
        System.out.println("//////////ROUND 1 start////////");
        System.out.println("Total names: "+nameList.size());
        roundList = generateRound(nameList);
        Collections.shuffle(nameList);
        nameList = executeRound(nameList, roundList);
        //ROUND 2
        System.out.println("//////////ROUND 2 start////////");
        roundList = generateRound(nameList);
        Collections.shuffle(nameList);
        nameList = executeRound(nameList, roundList);
        //ROUND 3
//        System.out.println("//////////ROUND 3 start////////");
//        roundList = generateRound(nameList);
//        Collections.shuffle(nameList);
//        nameList = executeRound(nameList, roundList);
        //PLAYOFFS
        System.out.println("//////////Playoffs 1 start////////");
        System.out.println("Total names: "+nameList.size());
        nameList = generatePlayoff(nameList);
        if (nameList.size() % 2 != 0) {
            nameList.add(new Name("bye", -1, -1));
        }
        roundList = generateRound(nameList);
        nameList = executePlayoffRound(nameList, roundList);
        System.out.println("//////////Playoffs 2 start////////");
        System.out.println("Total names: "+nameList.size());
        if (nameList.size() % 2 != 0) {
            nameList.add(new Name("bye", -1, -1));
        }
        roundList = generateRound(nameList);
        nameList = executePlayoffRound(nameList, roundList);
        System.out.println("//////////Playoffs 3 start////////");
        System.out.println("Total names: "+nameList.size());
        if (nameList.size() % 2 != 0) {
            nameList.add(new Name("bye", -1, -1));
        }
        roundList = generateRound(nameList);
        nameList = executePlayoffRound(nameList, roundList);
        System.out.println("//////////Playoffs 4 start////////");
        System.out.println("Total names: "+nameList.size());
        if (nameList.size() % 2 != 0) {
            nameList.add(new Name("bye", -1, -1));
        }
        roundList = generateRound(nameList);
        nameList = executePlayoffRound(nameList, roundList);
        System.out.println("//////////Playoffs 5 start////////");
        System.out.println("Total names: "+nameList.size());
        if (nameList.size() % 2 != 0) {
            nameList.add(new Name("bye", -1, -1));
        }
        roundList = generateRound(nameList);
        nameList = executePlayoffRound(nameList, roundList);
//        nameList = generatePlayoff(nameList);

        for (Name name : nameList) {
            System.out.println(name.getName());
        }
//        nameList = executeRound(nameList, roundList);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new NameMash();

        // TODO code application logic here
        /*
         Alogrithm:
         load dataset
         everyone gets base score of 400
         exhibition: randomly match up words
         playoffs: high scoring goes head to head
         low scoring: goes head to head
         a bracket: a group of users at the same level
         round: weeding out a couple of things. 
        
         generate rounds 1
         execute rounds and update scores
         generate round 2
         execute round and update scores
         playoff round eliminate losers
        
         */
    }

    private ArrayList<Name> getList() {
        FileReader read;
        ArrayList<Name> map = new ArrayList<Name>();
        try {
            read = new FileReader("names.txt");
            BufferedReader in = new BufferedReader(read);
            String st = "";
            int stepper = 0;
            while ((st = in.readLine()) != null) {
                Name name = new Name(st.toLowerCase(), 400, stepper);
                map.add(name);
                stepper++;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(NameMash.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NameMash.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    private ArrayList<Round> generateRound(ArrayList<Name> nameList) {
        Stack<Name> names = new Stack<>();
        for (Name name : nameList) {
            names.push(name);
        }
        ArrayList<Round> list = new ArrayList<>();
        while (!names.isEmpty()) {

            Round round = new Round(names.pop().getId(), names.pop().getId());
            list.add(round);
        }
        return list;

    }

    private ArrayList<Name> executeRound(ArrayList<Name> nameList, ArrayList<Round> roundList) {
        ArrayList<Name> updatedList = new ArrayList<>();

        for (Round round : roundList) {
            Name a = getName(nameList, round.a);
            Name b = getName(nameList, round.b);
            //do compare
            System.out.println("1:" + a.getName() + " vs 2:" + b.getName());

            Integer valueOf = Integer.valueOf(sc.next());
            if (valueOf == 1) {
                a.setScore(a.getScore() + 100);
                b.setScore(b.getScore() - 100);
            } else {
                a.setScore(a.getScore() - 100);
                b.setScore(b.getScore() + 100);
            }

            updatedList.add(a);
            updatedList.add(b);
        }
        
        return updatedList;
    }

    private ArrayList<Name> executePlayoffRound(ArrayList<Name> nameList, ArrayList<Round> roundList) {
        ArrayList<Name> updatedList = new ArrayList<>();
        for (Round round : roundList) {
            Name a = getName(nameList, round.a);
            Name b = getName(nameList, round.b);
            System.out.println("1:" + a.getName() + " vs 2:" + b.getName());

            Integer valueOf = Integer.valueOf(sc.next());
            if (valueOf == 1) {
              updatedList.add(a);
            } else {
               updatedList.add(b);
            }
            //do compare
            //System.out.println(a.getName()+" vs "+b.getName());
            
        }
        return updatedList;
    }

    private Name getName(ArrayList<Name> list, int id) {
        for (Name name : list) {
            if (name.getId() == id) {
                return name;
            }
        }
        return null;
    }

    private ArrayList<Name> generatePlayoff(ArrayList<Name> nameList) {
        Collections.sort(nameList, comp);
//        for (Name name : nameList) {
//            System.out.println(name.getName()+","+name.getScore());
//        }
        int cutoff = nameList.size() / 2 - 1;
//        System.out.println("cut off " + cutoff);
//        System.out.println("cut off is " + nameList.get(cutoff).getName());
        ArrayList<Name> champs = new ArrayList<>();
        ArrayList<Name> losers = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            if (i < cutoff) {
                champs.add(nameList.get(i));
            } else {
                losers.add(nameList.get(i));
            }
        }
        ArrayList<Round> one = generateRound(champs);
        ArrayList<Round> two = generateRound(losers);
        ArrayList<Name> winz = executePlayoffRound(champs, one);
        ArrayList<Name> losz = executePlayoffRound(losers, two);
        ArrayList<Name> compbine = new ArrayList<>();
        for (Name name : winz) {
            compbine.add(name);
        }
        for (Name name : losz) {
            compbine.add(name);
        }
        return compbine;
    }

    private class Round {

        private int a;
        private int b;

        public Round(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    private class Name {

        private String name;
        private int score;
        private int id;

        public Name(String name, int score, int id) {
            this.name = name;
            this.score = score;
            this.id = id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the score
         */
        public int getScore() {
            return score;
        }

        /**
         * @param score the score to set
         */
        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return name;
        }

        /**
         * @return the id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(int id) {
            this.id = id;
        }
    }
}