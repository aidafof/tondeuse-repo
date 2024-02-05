package com.mowitnow.service;

import com.mowitnow.exception.BusinessException;
import com.mowitnow.exception.ErrorCode;

import java.util.List;

/**
 * Service class handle logic validation of input data
 */
public class ValidationService {

    private static final String UPPER_COORDINATE_PATTERN = "\\d+ \\d+";
    private static final String POSITION_ORIENTATION_PATTERN = "\\d+ \\d+ [NESW]";
    private static final String INSTRUCTIONS_PATTERN = "[GAD]+";// check pattern

    /**
     * lawn data format validation rule
     * @param lawnData : lawn data
     * @throws BusinessException : Exception thrown if validation rule fails
     */
    public void validateLawnData(String lawnData) throws BusinessException {
        if (lawnData.isEmpty()) {
            throw new BusinessException(ErrorCode.MISSING_LAWN_DATA.getLabel());
        }
        if (!lawnData.matches(UPPER_COORDINATE_PATTERN)) {
            throw new BusinessException((ErrorCode.INVALID_COORDINATES.getLabel()).formatted(lawnData));
        }
    }

    /**
     * mower data format validation rules
     * @param mowerData : mower data list
     * @throws BusinessException : exception thrown if validation rule fails
     */
    public void validateMowerData(List<String> mowerData) throws BusinessException {
        // check if the list after remving first line empty
        if (mowerData.isEmpty()) {
            throw new BusinessException(ErrorCode.MISSING_MOWER_DATA.getLabel());
        }
        // list size must bean even number
        if (mowerData.size() % 2 != 0){
            throw new BusinessException((ErrorCode.INVALID_LINE_NUMBER.getLabel()).formatted(mowerData.size()));
        }

        //check Position and orientations line format
        for (int i = 0; i < mowerData.size(); i += 2) {
            String positionOrientation = mowerData.get(i);
            if (!positionOrientation.matches(POSITION_ORIENTATION_PATTERN)) {
                throw new BusinessException((ErrorCode.INVALID_POSITION_FORMAT.getLabel()).formatted(positionOrientation));
            }
            //validate instruction line format next line
            if (i + 1 < mowerData.size()) {
                String instruction = mowerData.get(i + 1);
                if (!instruction.matches(INSTRUCTIONS_PATTERN)) {
                    throw new BusinessException((ErrorCode.INVALID_INSTRUCTION_FORMAT.getLabel()).formatted(instruction));
                }
            }
        }
    }
}
