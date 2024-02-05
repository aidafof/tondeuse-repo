package com.mowitnow.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


class MowerTest {

    @Mock
    private Boundary mockBoundary;

    private Mower mower = new Mower();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void moveForward_when_Instruction_is_advance() throws Exception {
        //GIVEN
        when(mockBoundary.isOutOfBounds(anyInt(), anyInt())).thenReturn(false);

        initMower(new Position(4, 4), Orientation.N, List.of(Instruction.ADVANCE));
        Position expectedPosition = new Position(4, 5);
        //WHEN
        mower.startInstructionSequence(mockBoundary);
        //ASSERT
        assertEquals(expectedPosition, mower.getPosition());
        assertEquals(Orientation.N, mower.getOrientation());
    }

    @Test
    void turnLeft_when_Instruction_is_turnLeft() throws Exception {
        //GIVEN
        when(mockBoundary.isOutOfBounds(anyInt(), anyInt())).thenReturn(false);

        initMower(new Position(4, 4), Orientation.W, List.of(Instruction.LEFT));
        Position expectedPosition = new Position(4, 4);
        //WHEN
        mower.startInstructionSequence(mockBoundary);
        //ASSERT
        assertEquals(expectedPosition, mower.getPosition());
        assertEquals(Orientation.S, mower.getOrientation());
    }

    @Test
    void turnRight_when_Instruction_is_turnRight() throws Exception {
        //GIVEN
        when(mockBoundary.isOutOfBounds(anyInt(), anyInt())).thenReturn(false);

        initMower(new Position(4, 4), Orientation.S, List.of(Instruction.RIGHT));
        Position expectedPosition = new Position(4, 4);
        //WHEN
        mower.startInstructionSequence(mockBoundary);
        //ASSERT
        assertEquals(expectedPosition, mower.getPosition());
        assertEquals(Orientation.W, mower.getOrientation());
    }

    /**
     * Parameterized test with a list of instructions provided as a csvSource,
     * arguments ot the unit test
     *
     * @param startX             : starting position x
     * @param startY             : starting position y
     * @param startOrientation   : starting orientation
     * @param instructionsSt     moving instructions
     * @param finalX             : final expected position x
     * @param finalY             : final expected position y
     * @param finalOrientationSt : final orientation
     * @throws Exception : exception raised
     */

    @ParameterizedTest(name = "test Mower movements when executing a list of instructions")
    @CsvSource({
            "1, 2, N, AAAD, 1, 5, E",
            "1, 2, N, GAGAGAGAA, 1, 3, N",
            "3, 3, E, AADAADADDA, 5 ,1, E"
    })
    void should_move_mower_when_provide_aListOf_instruction(
            String startX, String startY, String startOrientation,
            String instructionsSt, String finalX, String finalY, String finalOrientationSt)
            throws Exception {

        int initialX = Integer.parseInt(startX.trim());
        int initialY = Integer.parseInt(startY.trim());
        int expectedX = Integer.parseInt(finalX.trim());
        int expectedY = Integer.parseInt(finalY.trim());

        List<Instruction> instructions = parseInstructions(instructionsSt);

        Position initialPosition = new Position(initialX, initialY);
        Position expectedPosition = new Position(expectedX, expectedY);
        Orientation initialOrientation = Orientation.valueOf(startOrientation);
        Orientation expectedOrientation = Orientation.valueOf(finalOrientationSt);

        initMower(initialPosition, initialOrientation, instructions);
        mower.startInstructionSequence(mockBoundary);
        assertEquals(expectedPosition, mower.getPosition());
        assertEquals(expectedOrientation, mower.getOrientation());
    }

    /**
     * convert a string of instructions into a list of Instruction instances
     *
     * @param instructionsString : input string of instructions
     * @return List<Instruction> : output list of Instructions enum instances
     */
    private List<Instruction> parseInstructions(String instructionsString) {
        return Instruction.convertToInstructionList(instructionsString);
    }

    /**
     * Initialize mower properties values
     *
     * @param position     : initial position
     * @param orientation  : initial orientation
     * @param instructions : list of instructions
     */
    private void initMower(Position position, Orientation orientation, List<Instruction> instructions) {
        mower = Mower.builder()
                .position(position)
                .orientation(orientation)
                .instructionsQueue(new ArrayDeque<>(instructions))
                .build();
    }

}