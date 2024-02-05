package com.mowitnow.model;

import com.mowitnow.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class LawnTest {

    @Mock
    private Mower mower1;
    @Mock
    private Mower mower2;
    private Boundary boundary;
    private Lawn lawn;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        boundary = new Boundary(0, 5, 0, 5);
        lawn = Lawn.builder()
                .boundary(boundary)
                .listMowers(List.of(mower1, mower2))
                .build();
    }

    @Test
    void monitoreMowers_should_Call_startInstruction_For_eachMower() throws BusinessException {
        //WHEN
        lawn.monitoreMowers();
        //THEN
        verify(mower1, times(1)).startInstructionSequence(boundary);
        verify(mower2, times(1)).startInstructionSequence(boundary);

    }
}