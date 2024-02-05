package com.mowitnow.model;

import com.mowitnow.exception.BusinessException;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public record Lawn(Boundary boundary, List<Mower> listMowers) {

    public static final int MIN_WIDTH = 0;
    public static final int MIN_LENGTH = 0;
    private static final Logger LOGGER = LoggerFactory.getLogger(Lawn.class);

    @Builder
    public Lawn(Boundary boundary, List<Mower> listMowers) {
        this.boundary = boundary;
        this.listMowers = new ArrayList<>(listMowers);
    }
    /**
     * Iterate on <i>listMowers</i> to process the instructions driving each mower
     *
     * @throws BusinessException : Exception raised during instruction processing
     */
    public void monitoreMowers() throws BusinessException {

        for (Mower mower : listMowers) {
            mower.startInstructionSequence(boundary);
        }
    }
}
