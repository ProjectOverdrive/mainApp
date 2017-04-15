import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Symptoms {
    //These are the input fields for the Symptoms Checker UI.
    private JComboBox symptomsInput1;
    private JComboBox symptomsInput2;
    private JComboBox symptomsInput3;
    private JTextField mileageInput;
        
    //This method creates a label for the symptoms input.
    public JLabel createSymptomsLabel() {
        JLabel symptomsLabel = new JLabel("Select Symptoms:");
        symptomsLabel.setBounds(25, 11, 300, 36);
        symptomsLabel.setFont(new Font(symptomsLabel.getFont().getName(), Font.PLAIN, 24));
        return symptomsLabel;
    }
    
    //This method creates the combo box for the first symptom.
    public JComboBox createInputBox1() {
        String[] symptoms = {"", "Car Pulls", "Check Engine Light", 
            "Cracking Tires", "Difficulty Starting", "Engine Overheating", 
            "Engine Sounds", "Exhaust Smoke", "Leak Under Car", "Low Gas "
                + "Mileage", "Low Tire Pressure", "Poor A/C", "Punctured Tire", 
                "Screeching Brakes", "Smell in Cabin"};
        symptomsInput1 = new JComboBox(symptoms);
        symptomsInput1.setBounds(25, 80, 300, 32);
        return symptomsInput1;
    }
    
    //This method creates the combo box for the second symptom.
    public JComboBox createInputBox2() {
        String[] symptoms = {"", "Car Pulls", "Check Engine Light", 
            "Cracking Tires", "Difficulty Starting", "Engine Overheating", 
            "Engine Sounds", "Exhaust Smoke", "Leak Under Car", "Low Gas "
                + "Mileage", "Low Tire Pressure", "Poor A/C", "Punctured Tire", 
                "Screeching Brakes", "Smell in Cabin"};
        symptomsInput2 = new JComboBox(symptoms);
        symptomsInput2.setBounds(25, 215, 300, 32);
        return symptomsInput2;
    }
    
    //This method creates the combo box for the third symptom.
    public JComboBox createInputBox3() {
        String[] symptoms = {"", "Car Pulls", "Check Engine Light", 
            "Cracking Tires", "Difficulty Starting", "Engine Overheating", 
            "Engine Sounds", "Exhaust Smoke", "Leak Under Car", "Low Gas "
                + "Mileage", "Low Tire Pressure", "Poor A/C", "Punctured Tire", 
                "Screeching Brakes", "Smell in Cabin"};
        symptomsInput3 = new JComboBox(symptoms);
        symptomsInput3.setBounds(25, 350, 300, 32);
        return symptomsInput3;
    }
    
    //This method creates a label for the mileage input.
    public JLabel createMileageLabel() {
        JLabel mileageLabel = new JLabel("Enter Mileage:");
        mileageLabel.setBounds(850, 11, 300, 36);
        mileageLabel.setFont(new Font(mileageLabel.getFont().getName(), Font.PLAIN, 24));
        return mileageLabel;
    }
    
    //This method creates the input field for mileage.
    public JTextField createMileageInput() {
        mileageInput = new JTextField();
        mileageInput.setText("Mileage");
	mileageInput.setBounds(850, 80, 300, 36);
	return mileageInput;
    }
    
    //This method creates the submit button for the whole form.
    public JButton createSubmitButton() {
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(850, 350, 300, 36);
        
        submitButton.addMouseListener(new MouseAdapter() {
            //First, the inputs should be validated, and if all required fields
            //are present, a window will open with the solutions.
            @Override
            public void mousePressed(MouseEvent e) {
                //If the symptoms and mileage inputs are correct, the solutions
                //will display. Otherwise, a respective error will be displayed.
                if (validateSymptomsInput()) {
                    if (validateMileageInput()) {
                        createSolutionsWindow();
                    } else {
                        createMileageErrorWindow();
                    }
                } else {
                    createSymptomsErrorWindow();
                }
            }
        });
        return submitButton;
    }
    
    //This method validates the symptoms input by making sure that at least 
    //one combo box has a non-null input.
    private boolean validateSymptomsInput() {
        int count = 0;
        if (!symptomsInput1.getSelectedItem().toString().equals("")) {
            count++;
        }
        if (!symptomsInput2.getSelectedItem().toString().equals("")) {
            count++;
        }
        if (!symptomsInput3.getSelectedItem().toString().equals("")) {
            count++;
        }
        //If count is zero, there is NOT at least one input for symptoms, so we
        //return false. Otherwise we return true.
        if (count == 0) {
            return false;
        } else {
            return true;
        }
    }
    
    //This method creates an error window if there are no symptoms inputs.
    private void createSymptomsErrorWindow() {
        //This initializes the symptoms error window.
        JFrame symptomsErrorFrame = new JFrame();
        symptomsErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        symptomsErrorFrame.setResizable(false);
        symptomsErrorFrame.setTitle("Error");
        symptomsErrorFrame.setBounds(100, 100, 350, 200);
        symptomsErrorFrame.getContentPane().setLayout(null);
        
        //This label displays the text of the error message.
        JLabel errorMessage = new JLabel("Please select at least one symptom.");
        errorMessage.setBounds(60, 50, 250, 32);
        symptomsErrorFrame.getContentPane().add(errorMessage);
        
        //This initializes the ok button.
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        symptomsErrorFrame.getContentPane().add(closeErrorMessageButton);
        
        //Pressing this button will close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                symptomsErrorFrame.dispose();
            }
	});
        
        symptomsErrorFrame.setVisible(true);
    }
    
    //This method validates the mileage input by verifying that it is not null
    //and that it contains numerical values.
    private boolean validateMileageInput() {
        if (mileageInput.getText().equals("Mileage") || 
                mileageInput.getText().equals("")) {
            return false;
        }
        int count = 0;
        //Loop through the string and increment count when a numeric character
        //is found.
        for(char c : mileageInput.getText().toCharArray()) {
            if(Character.isDigit(c)) {
                count++;
            }
        }
        //If a numeric character is not found, count will be zero and we return
        //false becuase the number is not numeric.
        if (count == 0) {
            return false;
        }
        //Otherwise return true.
        return true;
    }
    
    //This method creates an error window if the mileage input is not appropriate.
    private void createMileageErrorWindow() {
        //This initializes the mileage error window.
        JFrame mileageErrorFrame = new JFrame();
        mileageErrorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mileageErrorFrame.setResizable(false);
        mileageErrorFrame.setTitle("Error");
        mileageErrorFrame.setBounds(100, 100, 350, 200);
        mileageErrorFrame.getContentPane().setLayout(null);
        
        //This label displays the text of the error message.
        JLabel errorMessage = new JLabel("Please enter a numeric value for mileage.");
        errorMessage.setBounds(60, 50, 250, 32);
        mileageErrorFrame.getContentPane().add(errorMessage);
        
        //This initializes the ok button.
        JButton closeErrorMessageButton = new JButton("OK");
        closeErrorMessageButton.setBounds(115, 100, 90, 28);
        mileageErrorFrame.getContentPane().add(closeErrorMessageButton);
        
        //Pressing this button will close the error window.
        closeErrorMessageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                mileageErrorFrame.dispose();
            }
	});
        
        mileageErrorFrame.setVisible(true);
    }
    
    //This method creates the window for displaying the solutions
    private void createSolutionsWindow() {
        //This initializes the frame.
        JFrame solutionsFrame = new JFrame();
        solutionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        solutionsFrame.setResizable(false);
        solutionsFrame.setTitle("Symptoms Checker Solutions");
        solutionsFrame.setBounds(100, 100, 475, 315);
        solutionsFrame.getContentPane().setLayout(null);
        
        //TODO: fix error
        //Parse the mileage input as an integer.
        int mileage = Integer.getInteger(mileageInput.getText());
        //Round mileage to the nearest thousand.
        mileage = (mileage + 500) / 1000 * 1000;
        //Parse mileage into corresponding codes.
        char[] mileageCodes = parseMileage(mileage);
        
        //Parse symptom inputs into codes.
        int[] symptomCodes = parseSymptoms();
        
        String[] solutions = getSolutions(symptomCodes, mileageCodes);
        
        //This displays solution one.
        JLabel solutions1 = new JLabel("1: " + solutions[0]);
        solutions1.setBounds(10, 11, 203, 32);
	solutionsFrame.getContentPane().add(solutions1);
        
        //This displays solution two.
        JLabel solutions2 = new JLabel("2: " + solutions[1]);
        solutions2.setBounds(10, 53, 203, 32);
	solutionsFrame.getContentPane().add(solutions2);
        
        //This displays solution three.
        JLabel solutions3 = new JLabel("3: " + solutions[2]);
        solutions3.setBounds(10, 95, 203, 32);
	solutionsFrame.getContentPane().add(solutions3);
        
        //This displays solution four.
        JLabel solutions4 = new JLabel("4: " + solutions[3]);
        solutions4.setBounds(10, 137, 203, 32);
	solutionsFrame.getContentPane().add(solutions4);
        
        //This displays solution five.
        JLabel solutions5 = new JLabel("5: " + solutions[4]);
        solutions5.setBounds(10, 179, 203, 32);
	solutionsFrame.getContentPane().add(solutions5);
        
        //This initializes the close button.
        JButton closeSolutionsButton = new JButton("Close");
        closeSolutionsButton.setBounds(223, 221, 203, 32);
        solutionsFrame.getContentPane().add(closeSolutionsButton);
        
        //Pressing this button will close the solutions window.
        closeSolutionsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                solutionsFrame.dispose();
            }
	});
        
        solutionsFrame.setVisible(true);
    }
    
    //TODO: actually parse symptoms
    //This method parses the input symptoms to their corresponding codes.
    private int[] parseSymptoms() {
        int[] solutionCodes = {1, 2, 3};
        return solutionCodes;
    }
    
    //This method parses the mileage input into its corresponding codes.
    private char[] parseMileage(int mileage) {
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