package com.mowitnow.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
public enum Instruction {
    ADVANCE('A', "ADVANCE"),
    LEFT('G',"TURN_LEFT"),
    RIGHT('D', "TURN_RIGHT");
    private char code;
    private String label;

    Instruction(char pCode, String pLabel){
        this.code = pCode;
        this.label = pLabel;
    }

    /**
     *  convert the set of instructions strings into a list of Instructionsinstances
     * @param inputString
     * @return
     */
    public static List<Instruction> convertToInstructionList(String inputString) {
        return inputString.chars()
                .mapToObj(c -> (char) c)
                .map(c -> switch (c) {
                        case 'A'-> Instruction.ADVANCE;
                        case 'G'-> Instruction.LEFT;
                        case 'D'-> Instruction.RIGHT;
                        default -> throw new IllegalArgumentException("Invalid instruction code: " + c);
                })
                .toList();
    }
}
