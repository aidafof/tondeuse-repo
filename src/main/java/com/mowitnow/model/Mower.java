package com.mowitnow.model;


import com.mowitnow.exception.BusinessException;
import com.mowitnow.exception.ErrorCode;
import lombok.*;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.StringJoiner;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mower {
    @Getter
    @Setter
    private Position position;
    @Getter
    @Setter
    private Orientation orientation;
    @Getter
    private Queue<Instruction> instructionsQueue;

    /**
     * Set Mower properties
     * @param position : position value
     * @param orientation : orientation value
     * @param instructions : instruction value
     */
    @Builder
    public  Mower(Position position, Orientation orientation, List<Instruction> instructions){
        this.position = position;
        this.orientation= orientation;
        this.instructionsQueue= new ArrayDeque<>(instructions);
    }

    /**
     * start sequential processing of the instructions queue
     * @param lawnBoundary : lawn boudaries constraints
     * @throws BusinessException  raised
     */
    public void startInstructionSequence(Boundary lawnBoundary) throws BusinessException {
        while (!instructionsQueue.isEmpty()) {
            Instruction instruction = instructionsQueue.poll(); // to retrieve and remove the head value
            executeInstruction(instruction, lawnBoundary);
        }
    }

    /**
     * Execute a single instruction : update position and orientation
     * @param instruction : instruction to execute
     * @param boundary : lawn boundary constraints
     * @throws BusinessException raised
     */
     private void executeInstruction(Instruction instruction, Boundary boundary) throws BusinessException {
        switch (instruction.getCode()) {
            case 'A' -> moveForward (boundary);
            case 'G' -> turnLeft();
            case 'D' -> turnRight();
            default -> throw new IllegalArgumentException(MessageFormat.format(ErrorCode.INVALID_INSTRUCTION.getLabel() , instruction.getCode()));
        }
    }
    /**
     * Update position value based on orientation and boundaries
     * @param boundary : lawn boundary constraints
     * @throws BusinessException raised
     */
    private void moveForward( Boundary boundary) throws BusinessException {
        Position nextPosition = null;
        switch (this.orientation) {
            case N->  nextPosition = position.moveToNorth(boundary);
            case E -> nextPosition = position.moveToEast(boundary);
            case S->  nextPosition = position.moveToSouth( boundary);
            case W -> nextPosition = position.moveToWest( boundary);
            default -> throw new BusinessException( MessageFormat.format(ErrorCode.INVALID_ORIENTATION.getLabel(),orientation.name())  );
        }
        if (nextPosition!= null ){
            this.position = nextPosition;
        }
    }
    /**
     *Turn the mower in the left direction based on current orientation
     */
    private void turnLeft(){
        this.orientation = Orientation.MAP_TURN_LEFT.get(orientation.name());
    }
    /**
     *Turn the mower in the right direction based on current orientation
     */
    private void turnRight(){
        this.orientation = Orientation.MAP_TURN_RIGHT.get(orientation.name());
    }
    @Override
    public String toString() {
        return new StringJoiner(" ")
                .add(String.valueOf(position.getX()))
                .add(String.valueOf(position.getY()))
                .add(orientation.name()).toString();
    }

}
