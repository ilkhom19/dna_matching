package com.example.dna_matching;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    public static Integer numberOfQueries;
    public static Integer queryLength;
    public static Integer baseStringLength;
    public static Integer decimal = 256;
    public static Integer primeNumber = 1117;
    public static String baseString;
    public static String query;
    static ArrayList<Integer> hashes = new ArrayList<Integer>();
    static ArrayList<String> queries = new ArrayList<String>();

    @FXML
    private TextField baseString1;

    @FXML
    private TextField queryLength1;

    @FXML
    private TextField numberOfQueries1;

    @FXML
    private TextArea queries1;
    @FXML
    private TextArea result;

    @FXML
    private Button engU;

    @FXML
    void engClick(ActionEvent event) {
        try	{
            baseString = String.valueOf(baseString1.getText());
            numberOfQueries = Integer.parseInt(String.valueOf(numberOfQueries1.getText()));
            queryLength = Integer.parseInt(String.valueOf(queryLength1.getText()));
            baseStringLength = baseString.length();
            queries = new ArrayList<String>(Arrays.asList(String.valueOf(queries1.getText()).split("\n")));

            hashFunction(baseString);

            for (int i = 0; i < numberOfQueries; i++) {
                query = queries.get(i);
                int index =doesDatasetHave(query);
                if (index != -1){
                    result.appendText("The sequence: '" + query + "' found at the position: " + (index+1)+"\n");
                }else {
                    result.appendText("The sequence: '" + query + "' not found! \n");
                }
            }
        } catch (NumberFormatException ex) {
            baseString1.setText("Invalid Input!");
        }
    }

    static void hashFunction(String text){
        int i;
        int totalHash = 0;
        int hash = 1;

        for (i = 0; i < queryLength - 1; i++) {
            hash = (hash * decimal) % primeNumber;
        }

        for (i = 0; i < queryLength; i++){
            totalHash = (decimal * totalHash + text.charAt(i)) % primeNumber;
        }

        hashes.add(totalHash);

        for (i = 0; i < baseStringLength - queryLength; i++) {
            totalHash = (decimal * (totalHash - text.charAt(i) * hash) + text.charAt(i + queryLength)) % primeNumber;

            if (totalHash < 0) {
                totalHash += primeNumber;
            }
            hashes.add(totalHash);
        }
    }


    public static int doesDatasetHave(String query){
        int i, j, localHash = 0;
        for (i = 0; i < queryLength; i++) {
            localHash = (decimal * localHash + query.charAt(i)) % primeNumber;
        }
        for ( i = 0; i < hashes.size(); i++) {
            if( localHash == hashes.get(i) ){

                for (j = 0; j < queryLength; j++){
                    if ( baseString.charAt(i+j) != query.charAt(j) )
                        break;
                }
                if (j == queryLength)
                    return i;
            }
        }
        return  -1;
    }
}