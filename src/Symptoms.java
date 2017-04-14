import java.util.*;
public class Symptoms {
    public static void main(String[] args) {
        //User enters three syptoms.
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter three integers from the given list, "
                + "representing the car's symptoms.");
        
        int[] symptomCodes = {input.nextInt(), input.nextInt(), input.nextInt()};
        //The following are what the symptom codes are translated to be.
        //1: Car Pulls
        //2: Check Engine Light
        //3: Cracking Tires
        //4: Difficulty Starting
        //5: Engine Overheating
        //6: Engine Sounds
        //7: Exhaust Smoke
        //8: Leak Under Car
        //9: Low Gas Mileage
        //10: Low Tire Pressure
        //11: Poor A/C
        //12: Punctured Tire
        //13: Screeching Brakes
        //14: Smell in Cabin
        
        //Have the user enter the mileage of the car.
        System.out.println("Please enter the mileage of the car.");
        //Round mileage to the nearest thousand.
        int mileage = (input.nextInt() + 500) / 1000 * 1000;
        
        //Have the mileage parsed into codes.
        char[] mileageCodes = parseMileage(mileage);
        
        //Use the symptoms and mileage to get solutions.
        String[] solutions = getSolutions(symptomCodes, mileageCodes);
        
        //Print the solutions.
        for(int i = 0; i < 5; i++) {
            System.out.println((i+1)+": "+solutions[i]);
        }
    }
    
    public static char[] parseMileage(int mileage) {
        //The following are the mileage codes.
        //a: Is %100k
        //b: Is %60k
        //c: Is %50k
        //d: Is %30k
        //e: Is %25k
        //f: Is Not %25k
        //g: Is %5k
        //h: Is 100k
        //i: Is 70k
        //j: is Not 70k
        //k: Is 40k
        //l: Is Not 40k
        
        char[] codes = new char[12];
        int index = 0;
        
        if (mileage % 100000 == 0) {
            codes[index] = 'a';
            index++;
        }
        
        if (mileage % 60000 == 0) {
            codes[index] = 'b';
            index++;
        }
        
        if (mileage % 50000 == 0) {
            codes[index] = 'c';
            index++;
        }
        
        if (mileage % 30000 == 0) {
            codes[index] = 'd';
            index++;
        }
        
        if (mileage % 25000 == 0) {
            codes[index] = 'e';
            index++;
        } else {
            codes[index] = 'f';
            index++;
        }
        
        if (mileage % 5000 == 0) {
            codes[index] = 'g';
            index++;
        }
        
        if (mileage == 100000) {
            codes[index] = 'h';
            index++;
        }
        
        if (mileage == 70000) {
            codes[index] = 'i';
            index++;
        } else {
            codes[index] = 'j';
            index++;
        }
        
        if (mileage == 40000) {
            codes[index] = 'k';
            index++;
        } else {
            codes[index] = 'l';
            index++;
        }
        
        return codes;
    }
    
    public static String[] getSolutions(int[] symptomCodes, char[] mileageCodes) {
        Solution RT = new Solution("Replace Tires");
        Solution PT = new Solution("Patch Tires");
        Solution AAT = new Solution("Add Air to Tires");
        Solution OC = new Solution("Oil Change");
        Solution RBP = new Solution("Replace Brake Pads");
        Solution CTF = new Solution("Change Transmission Fluid");
        Solution CC = new Solution("Change Coolant");
        Solution CBF = new Solution("Change Brake Fluid");
        Solution CPSF = new Solution("Change Power Steering Fluid");
        Solution RTB = new Solution("Replace Timing Belt");
        Solution RDB = new Solution("Replace Drive Belt");
        Solution RFF = new Solution("Replace Fuel Filter");
        Solution RAF = new Solution("Replace Air Filter");
        Solution RSP = new Solution("Replace Spark Plugs");
        Solution RB = new Solution("Replace Battery");
        Solution RA = new Solution("Replace Altenator");
        
        //Increment the number of matches for each possible solution if it 
        //corresponds to the given symptom code.
        for(int i = 0; i < symptomCodes.length; i++) {
            int code = symptomCodes[i];
            switch(code) {
                case 1:
                    RBP.numMatches++;
                    CBF.numMatches++;
                    CPSF.numMatches++;
                    RDB.numMatches++;
                    RSP.numMatches++;
                    break;
                case 2:
                    OC.numMatches++;
                    CTF.numMatches++;
                    CBF.numMatches++;
                    RFF.numMatches++;
                    RB.numMatches++;
                    RA.numMatches++;
                    break;
                case 3:
                    RT.numMatches++;
                    PT.numMatches++;
                    break;
                case 4:
                    RSP.numMatches++;
                    RB.numMatches++;
                    RA.numMatches++;
                    break;
                case 5:
                    CC.numMatches++;
                    break;
                case 6:
                    OC.numMatches++;
                    CTF.numMatches++;
                    CPSF.numMatches++;
                    RTB.numMatches++;
                    RDB.numMatches++;
                    RAF.numMatches++;
                    RSP.numMatches++;
                    RA.numMatches++;
                    break;
                case 7:
                    OC.numMatches++;
                    break;
                case 8:
                    CC.numMatches++;
                    RTB.numMatches++;
                    break;
                case 9:
                    RT.numMatches++;
                    PT.numMatches++;
                    AAT.numMatches++;
                    RFF.numMatches++;
                    RAF.numMatches++;
                    RSP.numMatches++;
                    break;
                case 10:
                    RT.numMatches++;
                    AAT.numMatches++;
                    break;
                case 11:
                    RDB.numMatches++;
                    RAF.numMatches++;
                    break;
                case 12:
                    RT.numMatches++;
                    PT.numMatches++;
                    break;
                case 13:
                    RBP.numMatches++;
                    CBF.numMatches++;
                    break;
                case 14:
                    OC.numMatches++;
                    CTF.numMatches++;
                    CC.numMatches++;
                    RA.numMatches++;
                    break;
            }
        }
         
        //Increment the number of matches for each possible solution if it 
        //corresponds to the given mileage code.
        for(int i = 0; i < mileageCodes.length; i++) {
            char code = mileageCodes[i];
            switch(code) {
                case 'a':
                    RA.numMatches++;
                    break;
                case 'b':
                    RTB.numMatches++;
                    RDB.numMatches++;
                    RSP.numMatches++;
                    RB.numMatches++;
                    break;
                case 'c':
                    RBP.numMatches++;
                    CTF.numMatches++;
                    CC.numMatches++;
                    CBF.numMatches++;
                    CPSF.numMatches++;
                    break;
                case 'd':
                    RFF.numMatches++;
                    RAF.numMatches++;
                    break;
                case 'e':
                    RT.numMatches++;
                    break;
                case 'f':
                    OC.numMatches++;
                    break;
                case 'g':
                    RSP.numMatches++;
                    break;
                case 'h':
                    RT.numMatches++;
                    RBP.numMatches++;
                    CTF.numMatches++;
                    CC.numMatches++;
                    RTB.numMatches++;
                    RDB.numMatches++;
                    RSP.numMatches++;
                    break;
                case 'i':
                    RT.numMatches++;
                    RBP.numMatches++;
                    CTF.numMatches++;
                    RFF.numMatches++;
                    break;
                case 'j':
                    PT.numMatches++;
                    AAT.numMatches++;
                    break;
                case 'k':
                    PT.numMatches++;
                    AAT.numMatches++;
                    break;
                case 'l':
                    PT.numMatches++;
                    AAT.numMatches++;
                    break;
            }
        }
        
        //Create an arraylist of all the possible solutions.
        ArrayList<Solution> possibleSolutions = new ArrayList<Solution>();
        possibleSolutions.add(RT);
        possibleSolutions.add(PT);
        possibleSolutions.add(AAT);
        possibleSolutions.add(OC);
        possibleSolutions.add(RBP);
        possibleSolutions.add(CTF);
        possibleSolutions.add(CC);
        possibleSolutions.add(CBF);
        possibleSolutions.add(CPSF);
        possibleSolutions.add(RTB);
        possibleSolutions.add(RDB);
        possibleSolutions.add(RFF);
        possibleSolutions.add(RAF);
        possibleSolutions.add(RSP);
        possibleSolutions.add(RB);
        possibleSolutions.add(RA);
        
        //The solutions with the greatest number of matches are the best, so 
        //sort all the solutions by the numMatches value (this is smallest to 
        //largest).
        Collections.sort(possibleSolutions, new Comparator<Solution>() {
            @Override
            public int compare (Solution s1, Solution s2) {
                return Integer.compare(s1.numMatches, s2.numMatches);
            }
        });
        
        //Create and return an array for the 5 solutions with the largest 
        //numMatches value.
        String[] finalSolutions = new String[5];
        finalSolutions[0] = possibleSolutions.get(15).name;
        finalSolutions[1] = possibleSolutions.get(14).name;
        finalSolutions[2] = possibleSolutions.get(13).name;
        finalSolutions[3] = possibleSolutions.get(12).name;
        finalSolutions[4] = possibleSolutions.get(11).name;
        
        return finalSolutions;
    }
}

class Solution {
    String name;
    int numMatches;
    public Solution(String name) {
        this.name = name;
        this.numMatches = 0;
    }
}