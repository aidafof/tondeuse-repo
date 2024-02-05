package com.mowitnow.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstructionTest {


    @Test
    void should_convert_ToInstructionList_when_a_valid_instruction_string_input() {
        String input = "AGD";
        List<Instruction> instructions = Instruction.convertToInstructionList(input);

        assertAll("instructions",
                () -> assertEquals(Instruction.ADVANCE, instructions.get(0)),
                () -> assertEquals(Instruction.LEFT, instructions.get(1)),
                () -> assertEquals(Instruction.RIGHT, instructions.get(2))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "G", "D"})
    void should_convert_to_singleInstruction_when_single_string_instruction_input(String input) {
        List<Instruction> instructions = Instruction.convertToInstructionList(input);
        assertEquals(1, instructions.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"X", "1", "AGDX", " "})
    void Should_Throw_Exception_when_Invalid_instruction_string_input(String input) {
        assertThrows(IllegalArgumentException.class, () -> Instruction.convertToInstructionList(input));
    }

}