package com.mowitnow.processor;

import com.mowitnow.exception.BusinessException;
import com.mowitnow.model.*;
import com.mowitnow.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FileDataProcessor Class implements the complete process of the input file lines
 */
public class FileDataProcessor implements DataProcessor<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDataProcessor.class);

    private static final String SPACE_SEPARATOR = " ";
    ValidationService dataValidationService = new ValidationService();

    /**
     *  overall process
     * @param fileLines : data retrieved from file reader process
     * @return : String process final result
     * @throws BusinessException Business exception raised
     */
    @Override
    public String process(List<?> fileLines) throws BusinessException {
        //parsing the file data
        LOGGER.info("-> Processing input file lines :");

        LOGGER.info("**Step1 Parsing lines");

        String lawnLine = parseLawnData((List<String>) fileLines);
        //at this step  fileLines list is not empty
            List<String> mowerLines = (List<String>) fileLines.stream()
                    .skip(1)
                    .toList();

        LOGGER.info("**Step2 : Input data Validation");
        dataValidationService.validateLawnData(lawnLine);
        dataValidationService.validateMowerData(mowerLines);

        LOGGER.info("**Step3 : Building Mower and Lawn instances");
        String[] lawnData = lawnLine.split((SPACE_SEPARATOR));//convert line into array with coordinates
        List<Mower> listMowers = parseMowerData((List<String>) fileLines);
        Lawn lawn = initLawn(lawnData, listMowers);

        LOGGER.info("** Step4: Execute Mowers Instructions");
        lawn.monitoreMowers();
        LOGGER.info("** Display Lawn mowing final Result");

        String result =  displayResult(lawn);
        LOGGER.info("** Lawn mowing final Result :%s".formatted(result));
        return result;
    }

    /**
     * Return lawn data from the parsing of the input file
     * @param fileLines : lines retreived from the input file
     */
   private String parseLawnData(List<String> fileLines) {
       return fileLines.get(0);
   }

    /**
     * Return list of Mowers
     * @param fileLines : lines retrieved the input file parsing
     * @return : List<Mower>
     */
    private List<Mower> parseMowerData(List<String> fileLines) {
        List<Mower> listMowers = new ArrayList<>();

        Iterator<String> iterator = fileLines.iterator();
        if (iterator.hasNext()) {
            iterator.next(); // Skip the first line containing the lawn data
        }
        while (iterator.hasNext()) {
            String positionLine = iterator.next();
            String instructionLine = iterator.hasNext() ? iterator.next():"";

            String[] positionData = positionLine.split(SPACE_SEPARATOR);

            Mower mower = buildMower(positionData[0], positionData[1], positionData[2], instructionLine);
            listMowers.add(mower);
        }
        return listMowers;
    }

    /**
     * Build a new instance of Mower and set up properties values
     * @param abs : position coordinate X
     * @param ord position coordinate Y
     * @param codeOrientation: orientation data
     * @param instructionLine : instruction data line
     * @return : Mower instance initialized
     */
    private Mower buildMower(String abs, String ord, String codeOrientation, String instructionLine) {
        Position position = new Position(Integer.parseInt(abs), Integer.parseInt(ord));
        return Mower.builder()
                .position(position)
                .orientation(Orientation.valueOf(codeOrientation))
                .instructionsQueue(new ArrayDeque<>(convertInstructions(instructionLine)))
                .build();
    }
    /**
     * convert the set of instructions strings into a list of Instructionsinstances
     * @param instructionST : instructions string
     * @return list<Instruction> : the list of Instructions
     */
    private List<Instruction> convertInstructions(String instructionST) {
        return Instruction.convertToInstructionList(instructionST);
    }

    /**
     * Build an instance of Lawn and set up the properties
     * @param lawnData : lawn line data
     * @param listMowers : list of Mowers
     * @return Lawn : instance
     */
    private Lawn initLawn(String[] lawnData, List<Mower> listMowers) {
        return Lawn.builder()
                .boundary(new Boundary(Lawn.MIN_WIDTH,
                            Integer.parseInt(lawnData[0]),
                            Lawn.MIN_LENGTH,
                            Integer.parseInt(lawnData[1])))
                .listMowers(listMowers)
                .build();
    }

    /**
     * Diplays in the output the final result of the mowing process
     * @param lawn: the lawn retrieved from the inoput data
     * @return : String the result to diplay
     */
    public String displayResult (Lawn lawn) {
            return lawn.listMowers().stream()
                    .map(Mower::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
    }

}
