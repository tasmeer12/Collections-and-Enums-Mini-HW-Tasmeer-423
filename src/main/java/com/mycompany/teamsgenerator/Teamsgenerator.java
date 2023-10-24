package com.mycompany.teamsgenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Teamsgenerator {
    public static void main(String[] args) {
        // Read data from an Excel file
        List<Person> peopleData = readDataFromExcelFile("resources/MOCK_DATA.xlsx");

        // Create 20 teams
        List<Team> teams = createTeams(peopleData, 20, 5);

        // Print the teams to the console
        for (Team team : teams) {
            System.out.println("Team Name: " + team.getName());
            for (Person member : team.getMembers()) {
                System.out.println("Member: " + member.getFirstName() + " " + member.getLastName());
            }
            System.out.println();
        }
    }

    // Read data from an Excel file
    private static List<Person> readDataFromExcelFile(String fileName) {
        List<Person> peopleData = new ArrayList<>();
        try (FileInputStream excelFile = new FileInputStream(fileName);
             Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
            for (Row row : sheet) {
                Cell idCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell firstNameCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell lastNameCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell emailCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                
                

                // Extract values from Excel cells
                int id = (int) getCellValue(idCell);
                String firstName = getStringCellValue(firstNameCell);
                String lastName = getStringCellValue(lastNameCell);
                String email = getStringCellValue(emailCell);
                
                 // Create a Person object and add it to the list

                Person person = new Person(id, firstName, lastName, email);
                peopleData.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return peopleData;
    }

    // Create teams from the loaded data
    private static List<Team> createTeams(List<Person> peopleData, int numTeams, int teamSize) {
        List<Team> teams = new ArrayList<>();
        List<Person> availablePeople = new ArrayList<>(peopleData);

        java.util.Random random = new java.util.Random();
        for (int i = 1; i <= numTeams; i++) {
            String teamName = "Team " + i;
            List<Person> teamMembers = new ArrayList<>();
            for (int j = 0; j < teamSize; j++) {
                if (availablePeople.isEmpty()) {
                    break; // No more available people
                }
                int randomIndex = random.nextInt(availablePeople.size());
                teamMembers.add(availablePeople.remove(randomIndex));
            }
            // Create a Team with a name and its members, and add it to the list of teams
            Team team = new Team(teamName, teamMembers);
            teams.add(team);
        }
        return teams;
    }

    // Helper methods to handle cell types
private static Object getCellValue(Cell cell) {
    switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue();
        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue(); // Handle date values
            }
            return (int) cell.getNumericCellValue();
        default:
            return "";
    }
}

     // Get the string value of a cell
    private static String getStringCellValue(Cell cell) {   
        Object cellValue = getCellValue(cell);
        return cellValue.toString();
    }
}
